package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import persistencia.AccesoBD;
import persistencia.consultas.Consultas;

public class DAOProductos {
	Consultas consultas;

	public DAOProductos() {
		consultas = new Consultas();
	}

	public boolean member(String codP) throws excepcionErrorPersistencia {
		boolean existe = false;
 
		try (Connection connection = AccesoBD.instanciarConexion();
				PreparedStatement pStmt = connection.prepareStatement(consultas.obtenerProducto())) {
			    pStmt.setString(1, codP);
			    try (ResultSet resultSet = pStmt.executeQuery()) {
			    	if (resultSet.next()) {
						existe = true;
					}
			    } 
			} catch (SQLException e) {
				throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
			}
		return existe;
	}

	public void insert(Producto producto) throws excepcionErrorPersistencia {
		try (Connection connection = AccesoBD.instanciarConexion();
			PreparedStatement pStmt = connection.prepareStatement(consultas.insertarProducto())) {
			// (codigo, nombre, precio)
		    pStmt.setString(1, producto.getCodigo());
		    pStmt.setString(2, producto.getNombre());
		    pStmt.setInt(3, producto.getPrecio());
		    pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}
	}

	public Producto find(String codP) throws excepcionErrorPersistencia {
		Producto producto = null;
		 
		try (Connection connection = AccesoBD.instanciarConexion();
			PreparedStatement pStmt = connection.prepareStatement(consultas.obtenerProducto())) {
			pStmt.setString(1, codP);
		    try (ResultSet resultSet = pStmt.executeQuery()) {
		    	if (resultSet.next()) {
					String codigo = resultSet.getString("codigo");
					String nombre = resultSet.getString("nombre");
					int precio = resultSet.getInt("precio");
					producto = new Producto(codigo, nombre, precio);
				}
		    } 
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}
		return producto;
	}

	public void delete(String codP) throws excepcionErrorPersistencia {

		try {
			Connection connection = AccesoBD.instanciarConexion();
			String query = consultas.bajaProducto();

			PreparedStatement prs = connection.prepareStatement(query);

			prs.setString(1, codP);
			prs.executeUpdate();

			prs.close();

		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}

	}

	public boolean esVacio() throws excepcionErrorPersistencia {
		boolean esVacio = true;
		try (Connection connection = AccesoBD.instanciarConexion();
			Statement stmt = connection.createStatement()) {
		    try (ResultSet resultSet = stmt.executeQuery(consultas.obtenerTotalProductos())) {
		    	if (resultSet.next()) {
		    		int total = resultSet.getInt("total");
		    		if (total > 0) {
			    		esVacio = false;
		    		}
				}
		    } 
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}
		
		return esVacio;
	}

	public List<VOProducto> listarProductos() throws excepcionErrorPersistencia {

		List<VOProducto> resp = new ArrayList<VOProducto>();

		try {
			Connection connection = AccesoBD.instanciarConexion();

			String query = this.consultas.obtenerProductos();
			PreparedStatement prs = connection.prepareStatement(query);

			ResultSet rs = prs.executeQuery();

			while (rs.next()) { 

				String cod = rs.getString("codigo");
				int precio = rs.getInt("precio");
				String nombre = rs.getString("nombre");

				resp.add(new VOProducto(cod, nombre, precio));
			}

			prs.close();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}

		return resp;
	}

	public VOProdVentas productoMasVendido() throws excepcionErrorPersistencia {
		VOProdVentas voProdVentas = null;
		try (Connection connection = AccesoBD.instanciarConexion();
			Statement stmt = connection.createStatement()) {
		    try (ResultSet resultSet = stmt.executeQuery(consultas.obtenerProductoMasUnidadesVendidas())) {
		    	if (resultSet.next()) { // String codigo, String nombre, int precio, int unidadesVendidas
		    		String codigo = resultSet.getString("codigo");
		    		String nombre = resultSet.getString("nombre");
		    		int precio = resultSet.getInt("precio");
		    		int unidadesVendidas = resultSet.getInt("total_unidades");
		    		voProdVentas = new VOProdVentas(codigo, nombre, precio, unidadesVendidas);
				}
		    } 
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}

		return voProdVentas;
	}
}
