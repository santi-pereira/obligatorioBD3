package poolConexiones;


public class PoolConexionesArchivo implements IPoolConexionesArchivo {

	private int cantLectores;
    private boolean escribiendo;
    
    private static final int maxLect = 10; // es agregado, para que no sea infinito el monitor

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
    public synchronized void liberarConexion(IConexion con) {
    	
    	this.cantLectores--;
		if (cantLectores == 0) {
			notify();
		}
    }

	
}
