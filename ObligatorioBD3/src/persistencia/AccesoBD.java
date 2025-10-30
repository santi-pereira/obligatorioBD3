package persistencia;

import java.sql.SQLException;
import java.util.Properties;

import logica.excepciones.excepcionErrorPersistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class AccesoBD {
	private static Connection connection = null;
	
	public AccesoBD() {
	}
	
	public static Connection instanciarConexion() throws excepcionErrorPersistencia {
		try {
			if (connection == null || connection.isClosed()) {
				Properties props = new Properties();
				
				/*try (InputStream input = AccesoBD.class.getClassLoader().getResourceAsStream("././resources/config.properties");) {
					
					
					props.load(input);
				} catch (FileNotFoundException exception) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				} catch (IOException exception) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}*/
				try {

					String nomArchProperties = "././resources/config.properties";
					props.load(new FileInputStream(nomArchProperties));
					

				} catch (IOException e) {
					e.printStackTrace();
				}

				String driver = props.getProperty("driver");
				String url = props.getProperty("url");
				String user = props.getProperty("user");
				String password = props.getProperty("password");
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e1) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
				try {
					connection = DriverManager.getConnection(url, user, password);
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
			}
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} catch (excepcionErrorPersistencia e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}

		return connection;
	}

	// no se necesita cerrar en cada metodo que accede a DB, puede usarse cuando se cierra la aplicacion
	public static void cerrarConexion() throws excepcionErrorPersistencia {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException sqlE) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}
	}
}
