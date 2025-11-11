package poolConexiones;

import logica.excepciones.excepcionErrorPersistencia;

public class PoolConexionesArchivo implements IPoolConexiones {

	private int cantLectores;
    private boolean escribiendo;
    
    private static final int maxLect = 10;

    public PoolConexionesArchivo () {
		this.cantLectores = 0;
		this.escribiendo = false;
		
    }
	@Override
    public synchronized IConexion obtenerConexion(boolean modifica) {
    		 
    	IConexion conexion = new ConexionArchivo(modifica);
    	if (!modifica) {
            while (this.escribiendo || this.cantLectores >= PoolConexionesArchivo.maxLect) {
    			try {
    				wait();
    			} catch (InterruptedException e) { 
    				Thread.currentThread().interrupt();
    		}
    		this.cantLectores++;   
            }            
    	}
    	else {
    		while(this.escribiendo || this.cantLectores > 0) {
    			try {
    				wait();
    			} catch(InterruptedException e) {
    				Thread.currentThread().interrupt();
   					
    			} 
    			
    		}
    		this.escribiendo = true;
    	}
    	return conexion;
    }

	@Override
	public synchronized void liberarConexion(IConexion con, boolean ok) throws excepcionErrorPersistencia {
		boolean eraEscritor = false;
        if (con instanceof ConexionArchivo) {
            eraEscritor = ((ConexionArchivo) con).getConnection();
        }
        if (eraEscritor) {
            escribiendo = false;
            notifyAll();
        } else {
            cantLectores--;
            if (cantLectores == 0) {
                notifyAll();
            }
        }
    }	
}
