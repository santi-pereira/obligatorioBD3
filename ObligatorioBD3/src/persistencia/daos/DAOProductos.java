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
import persistencia.consultas.Consultas;
import poolConexiones.Conexion;
import poolConexiones.IConexion;

public class DAOProductos implements IDAOProductos {
	Consultas consultas;

	public DAOProductos() {
		consultas = new Consultas();
	}

	@Override
	public boolean member(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		boolean existe = false;
		PreparedStatement pStmt = null;

		try {
			Connection connection = ((Conexion)iConexion).getConnection();
			pStmt = connection.prepareStatement(consultas.obtenerProducto()); 
		    pStmt.setString(1, codP);
		    ResultSet resultSet = pStmt.executeQuery();
	    	if (resultSet.next()) {
				existe = true;
			}
	    	resultSet.close();
	    	pStmt.close();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} finally {
			if (pStmt != null)
				try {
					pStmt.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}
		return existe;
	}

	@Override
	public void insert(Producto producto, IConexion iConexion) throws excepcionErrorPersistencia {
		PreparedStatement pStmt = null;
		try {
			Connection connection = ((Conexion)iConexion).getConnection();
			pStmt = connection.prepareStatement(consultas.insertarProducto());
			// (codigo, nombre, precio)
		    pStmt.setString(1, producto.getCodigo());
		    pStmt.setString(2, producto.getNombre());
		    pStmt.setInt(3, producto.getPrecio());
		    pStmt.executeUpdate();
	    	pStmt.close();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} finally {
			if (pStmt != null)
				try {
					pStmt.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}
	}

	@Override
	public Producto find(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		Producto producto = null;
		PreparedStatement pStmt = null;
		 
		try {
			Connection connection = ((Conexion)iConexion).getConnection();
			pStmt = connection.prepareStatement(consultas.obtenerProducto());
			pStmt.setString(1, codP);
		    try (ResultSet resultSet = pStmt.executeQuery()) {
		    	if (resultSet.next()) {
					String codigo = resultSet.getString("codigo");
					String nombre = resultSet.getString("nombre");
					int precio = resultSet.getInt("precio");
					producto = new Producto(codigo, nombre, precio);
				}
		    }
		    pStmt.close();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} finally {
			if (pStmt != null)
				try {
					pStmt.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}
		return producto;
	}

	@Override
	public void delete(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		PreparedStatement prs = null;
		try {
			Connection connection = ((Conexion)iConexion).getConnection();
			String query = consultas.bajaProducto();

			prs = connection.prepareStatement(query);

			prs.setString(1, codP);
			prs.executeUpdate();

			prs.close();

		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} finally {
			if (prs != null)
				try {
					prs.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}

	}

	@Override
	public boolean esVacio(IConexion iConexion) throws excepcionErrorPersistencia {
		Statement stmt = null;
		boolean esVacio = true;
		try {
			Connection connection = ((Conexion)iConexion).getConnection();
			stmt = connection.createStatement();
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
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}
		
		return esVacio;
	}

	@Override
	public List<VOProducto> listarProductos(IConexion iConexion) throws excepcionErrorPersistencia {

		List<VOProducto> resp = new ArrayList<VOProducto>();
		PreparedStatement prs = null;
		ResultSet rs = null;

		try {
			Connection connection = ((Conexion)iConexion).getConnection();

			String query = this.consultas.obtenerProductos();
			prs = connection.prepareStatement(query);

			rs = prs.executeQuery();

			while (rs.next()) { 

				String cod = rs.getString("codigo");
				int precio = rs.getInt("precio");
				String nombre = rs.getString("nombre");

				resp.add(new VOProducto(cod, nombre, precio));
			}

			prs.close();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} finally {
			if (prs != null)
				try {
					prs.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}

		return resp;
	}

	@Override
	public VOProdVentas productoMasVendido(IConexion iConexion) throws excepcionErrorPersistencia {
		VOProdVentas voProdVentas = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			Connection connection = ((Conexion)iConexion).getConnection();
			stmt = connection.createStatement();
			resultSet = stmt.executeQuery(consultas.obtenerProductoMasUnidadesVendidas());
			if (resultSet.next()) { // String codigo, String nombre, int precio, int unidadesVendidas
	    		String codigo = resultSet.getString("codigo");
	    		String nombre = resultSet.getString("nombre");
	    		int precio = resultSet.getInt("precio");
	    		int unidadesVendidas = resultSet.getInt("total_unidades");
	    		voProdVentas = new VOProdVentas(codigo, nombre, precio, unidadesVendidas);
			}
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}

		return voProdVentas;
	}
}
