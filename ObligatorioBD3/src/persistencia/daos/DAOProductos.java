package persistencia.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import logica.Fachada;
import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import persistencia.consultas.Consultas;

public class DAOProductos {
	private String driver;
	private String url;
	private String user;
	private String password;
	
	public DAOProductos() throws excepcionErrorPersistencia {
		Properties props = new Properties();
		
		try (InputStream input = Fachada.class.getClassLoader().getResourceAsStream("config.properties");) {
			props.load(input);
		} catch (FileNotFoundException exception) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} catch (IOException exception) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}

		this.driver = props.getProperty("driver");
		this.url = props.getProperty("url");
		this.user = props.getProperty("user");
		this.password = props.getProperty("password");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}
	}

	public boolean member(String codP) throws excepcionErrorPersistencia {
		boolean existe = false;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection (this.url,this.user,this.password);
			Consultas consultas = new Consultas(); 
			String query = consultas.obtenerProducto();
			PreparedStatement pStmt = connection.prepareStatement(query);
			pStmt.setString(1, codP);
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				existe = true;
			}

			resultSet.close();

		} catch (SQLException sqlException) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
			}
		}

		return existe;
	}

	public void insert(Producto producto) {
		// TODO: implementar
	}
	
	public Producto find(String codP) {
		// TODO: implementar
		return null;
	}
	
	public void delete(String codP) {
		// TODO: implementar
	}
	
	public boolean esVacio() {
		// TODO: implementar
		return false;
	}
	
	public List<VOProducto> listarProductos() {
		// TODO: implementar
		return null;
	}
	
	public VOProdVentas productoMasVendido() {
		// TODO: implementar
		return null;
	}
}
