package poolConexiones;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import logica.excepciones.excepcionErrorPersistencia;

public class PoolConexionesArchivo implements IPoolConexiones {

	private int cantLectores;
    private boolean escribiendo;
    
    private static final int maxLect = 10; // es agregado, para que no sea infinito el monitor

    public PoolConexionesArchivo () {
		this.cantLectores = 0;
		this.escribiendo = false;
		
    }

    public synchronized IConexion obtenerConexion(boolean modifica) {
    		 
    	IConexion conexion = new ConexionArchivo(modifica);
    	if ((ConexionArchivo)(conexion).getConnection()) {
            while (this.escribiendo || this.cantLectores >= PoolConexionesArchivo.maxLect) {
    			try {
    				wait();
    			} catch (InterruptedException e) { 
    				Thread.currentThread().interrupt();
    		}
    		this.cantLectores++;   
            }
            return conexion;
  
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
