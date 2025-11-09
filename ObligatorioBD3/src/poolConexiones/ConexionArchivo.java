package poolConexiones;

public class ConexionArchivo implements IConexion {

	private boolean modificando;

    public ConexionArchivo (boolean m) {
        this.modificando = m;
        
    }

	public boolean getConnection() {
		return modificando;
	}
}
