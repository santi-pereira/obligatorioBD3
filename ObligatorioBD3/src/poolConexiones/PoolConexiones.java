package poolConexiones;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import logica.excepciones.excepcionErrorPersistencia;

public class PoolConexiones implements IPoolConexiones {
	private String driver, url, user, password;
    private int nivelTransaccionalidad;
    private Conexion[] conexiones;
    private int tamanio, creadas, tope;

    public PoolConexiones() throws excepcionErrorPersistencia {
    	try {
			Properties props = new Properties();

			String nomArchProperties = "././resources/config.properties";
			props.load(new FileInputStream(nomArchProperties));
			this.driver = props.getProperty("driver");
			this.url = props.getProperty("url");
			this.user = props.getProperty("user");
			this.password = props.getProperty("password");
			this.tamanio = Integer.parseInt(props.getProperty("tamanioPool"));
	        this.nivelTransaccionalidad = Connection.TRANSACTION_SERIALIZABLE;
			Class.forName(this.driver);
            conexiones = new Conexion[tamanio];
            creadas = 0;
            tope = 0;
		} catch (IOException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia al inicializar el pool.");
		} catch (ClassNotFoundException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia al inicializar el pool.");
		}
    }

    @Override
    public synchronized IConexion obtenerConexion(boolean modifica) throws excepcionErrorPersistencia {
        try {
            while (tope == 0 && creadas == tamanio) {
                // esperamos hasta que haya una conexion disponible
                wait();
            }
            Conexion conexion;
            if (tope > 0) {
                tope--;
                conexion = conexiones[tope];
            } else {
                Connection connection = DriverManager.getConnection(url, user, password);
                connection.setTransactionIsolation(nivelTransaccionalidad);
                connection.setAutoCommit(false);
                conexion = new Conexion(connection);
                creadas++;
            }
            return conexion;
        } catch (Exception e) {
            // Add this line to see the real error in the server console!
            e.printStackTrace(); 
            throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
        }
    }

    @Override
    public synchronized void liberarConexion(IConexion con, boolean ok) throws excepcionErrorPersistencia {
        try {
            Connection connection = ((Conexion)con).getConnection();
            if (ok) {
                connection.commit();
            } else {
                connection.rollback();
            }
            conexiones[tope] = (Conexion) con;
            tope++;
            notify(); // despierta a los hilos que esten bloqueados
        } catch (Exception e) {
            e.printStackTrace();
            throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
        }
    }
}
