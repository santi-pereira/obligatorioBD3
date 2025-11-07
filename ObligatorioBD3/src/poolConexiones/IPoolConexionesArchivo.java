package poolConexiones;

public interface IPoolConexionesArchivo {

	public IConexion obtenerConexion(boolean modifica);
    public void liberarConexion(IConexion con);
}
