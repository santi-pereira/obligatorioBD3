package poolConexiones;

import java.sql.Connection;

public class Conexion implements IConexion {
	private Connection connection;

    public Conexion(Connection con) {
        this.connection = con;
    }

	public Connection getConnection() {
		return this.connection;
	}
}
