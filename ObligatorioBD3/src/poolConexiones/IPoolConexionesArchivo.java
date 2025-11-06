package poolConexiones;

import logica.excepciones.excepcionErrorPersistencia;

public class IPoolConexionesArchivo {

	public IConexion obtenerConexion(boolean modifica) throws excepcionErrorPersistencia;
    public void liberarConexion(IConexion con, boolean ok) throws excepcionErrorPersistencia;
}
