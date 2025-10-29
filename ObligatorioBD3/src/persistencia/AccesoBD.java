package persistencia;

import java.sql.SQLException;
import java.util.Properties;

import logica.excepciones.excepcionErrorPersistencia;

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
				
				try (InputStream input = AccesoBD.class.getClassLoader().getResourceAsStream("config.properties");) {
					props.load(input);
				} catch (FileNotFoundException exception) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				} catch (IOException exception) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
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

	/*
	 *
	 * public void crearProducto(Connection conn, String codigo, String nombre, int
	 * precio) throws SQLException { String query =
	 * this.consultas.insertarProducto(); PreparedStatement prs =
	 * conn.prepareStatement(query);
	 * 
	 * prs.setString(1, codigo); prs.setString(2, nombre); prs.setInt(3, precio);
	 * 
	 * prs.execute();
	 * 
	 * prs.close();
	 * 
	 * }
	 * 
	 * public void bajaProductoVentas (Connection conn, String codigo)throws
	 * SQLException { String query = this.consultas.bajaProducto();
	 * PreparedStatement prs = conn.prepareStatement(query);
	 * 
	 * prs.setString(1, codigo); prs.execute();
	 * 
	 * prs.close(); }
	 * 
	 * public void bajarVentasByCodProd(Connection conn, String codigo)throws
	 * SQLException { String query = this.consultas.bajarVentasByCodigoProducto();
	 * PreparedStatement prs = conn.prepareStatement(query);
	 * 
	 * prs.setString(1, codigo); prs.execute();
	 * 
	 * prs.close(); }
	 * 
	 * public int getCantidadVentasByProducto (Connection conn, String codigo)
	 * throws SQLException { int cantidad = 0;
	 * 
	 * String query = this.consultas.cantidadVentasByCodigoProducto();
	 * PreparedStatement prs = conn.prepareStatement(query);
	 * 
	 * prs.setString(1, codigo);
	 * 
	 * ResultSet rs = prs.executeQuery(); if(rs.next()) cantidad =
	 * rs.getInt("cantidad");
	 * 
	 * prs.close();
	 * 
	 * return cantidad; }
	 * 
	 * public void crearVenta(Connection conn, int numero, String codProd, int
	 * unidades, String Cliente) throws SQLException { String query =
	 * this.consultas.insertarVenta(); PreparedStatement prs =
	 * conn.prepareStatement(query);
	 * 
	 * prs.setInt(1, numero); prs.setString(2, codProd); prs.setInt(3, unidades);
	 * prs.setString(4, Cliente);
	 * 
	 * prs.execute();
	 * 
	 * prs.close();
	 * 
	 * }
	 * 
	 * public List<VOProducto> listaProductos (Connection conn) throws SQLException
	 * { List<VOProducto> v = new ArrayList<VOProducto>();
	 * 
	 * String query = this.consultas.obtenerProductos(); PreparedStatement prs =
	 * conn.prepareStatement(query);
	 * 
	 * 
	 * ResultSet rs = prs.executeQuery();
	 * 
	 * while(rs.next()) { // codigo, precio, nombre
	 * 
	 * String cod = rs.getString("codigo"); int precio = rs.getInt("precio"); String
	 * nombre = rs.getString("nombre");
	 * 
	 * 
	 * v.add(new VOProducto(cod, nombre, precio)); }
	 * 
	 * prs.close();
	 * 
	 * return v; }
	 * 
	 * public List<VOVentaTotal> listaVentas (Connection conn, String codP) throws
	 * SQLException { List<VOVentaTotal> v = new ArrayList<VOVentaTotal>();
	 * 
	 * String query = this.consultas.obtenerVentasByCodigoP(); PreparedStatement prs
	 * = conn.prepareStatement(query);
	 * 
	 * prs.setString(1, codP);
	 * 
	 * ResultSet rs = prs.executeQuery();
	 * 
	 * while(rs.next()) { // codigo, precio, nombre
	 * 
	 * int numero = rs.getInt("numero"); String cod = rs.getString("codProd"); int
	 * unidades = rs.getInt("unidades"); String cliente = rs.getString("Cliente");
	 * 
	 * 
	 * v.add(new VOVentaTotal(unidades, cliente, numero, cod)); }
	 * 
	 * prs.close();
	 * 
	 * return v; }
	 * 
	 * // TODO: implementar este metodo. public VOProducto
	 * productoMasUnidadesVendidas(Connection conn) throws SQLException { return
	 * null; }
	 */
}
