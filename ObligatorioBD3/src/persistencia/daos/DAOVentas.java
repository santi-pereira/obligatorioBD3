package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import logica.Venta;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOVentaTotal;
import persistencia.consultas.Consultas;
import poolConexiones.IConexion;

public class DAOVentas {

	private Consultas consultas;
	private String codProd;

	public DAOVentas(String codProd) {
		super();
		this.consultas = new Consultas();
		this.codProd = codProd;
	}
	
	public void insback(Venta venta, IConexion iConexion) throws excepcionErrorPersistencia {
		PreparedStatement pStmt = null;
		try {
			Connection connection = iConexion.getConnection();
			pStmt = connection.prepareStatement(this.consultas.insertarVenta());
			pStmt.setInt(1, venta.getNumero());
			pStmt.setString(2, this.codProd);
			pStmt.setInt(3, venta.getUnidades());
			pStmt.setString(4, venta.getCliente());
			pStmt.executeUpdate();
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
	
	public int largo(IConexion iConexion) throws excepcionErrorPersistencia {
		int largo = 0;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			Connection connection = iConexion.getConnection();
			pStmt = connection.prepareStatement(this.consultas.cantidadVentasByCodigoProducto());
		    pStmt.setString(1, this.codProd);
		    resultSet = pStmt.executeQuery();
		    	if (resultSet.next()) {
		    		largo = resultSet.getInt("cantidad"); 
				} 
			} catch (SQLException e) {
				throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
			} finally {
				if (pStmt != null)
					try {
						pStmt.close();
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

		return largo;
	}
	
	public Venta Kesimo(int numVenta, IConexion iConexion) throws excepcionErrorPersistencia {
		Venta venta = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			Connection connection = iConexion.getConnection();
			pStmt = connection.prepareStatement(this.consultas.obtenerDatosVenta());
		    pStmt.setInt(1, numVenta);
			resultSet = pStmt.executeQuery();
	    	if (resultSet.next()) {
	    		int cantidad = resultSet.getInt("unidades"); 
	    		String cliente = resultSet.getString("cliente"); 
	    		venta = new Venta(numVenta, cantidad, cliente);
			}
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}  finally {
			if (pStmt != null)
				try {
					pStmt.close();
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
		
		return venta;
	}

	public void borrarVenta(IConexion iConexion) throws excepcionErrorPersistencia {  
		PreparedStatement prs = null;
		try {
			Connection connection = iConexion.getConnection();

			String query = this.consultas.bajarVentasByCodigoProducto();
			prs = connection.prepareStatement(query);

			prs.setString(1, this.codProd);
			prs.executeUpdate();
		} catch (SQLException ex) {
			throw new excepcionErrorPersistencia("Error en la persistencia de los datos");
		} finally {
			if (prs != null)
				try {
					prs.close();
				} catch (SQLException e) {
					throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
				}
		}
	}
	
	public List<VOVentaTotal> listarVentas(IConexion iConexion) throws excepcionErrorPersistencia {
		List<VOVentaTotal> list = new LinkedList<VOVentaTotal>();
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			Connection connection = iConexion.getConnection();
			pStmt = connection.prepareStatement(this.consultas.obtenerVentasByCodigoP());
		    pStmt.setString(1, this.codProd);
		    resultSet = pStmt.executeQuery();
	    	while (resultSet.next()) {
	    		int numero = resultSet.getInt("numero");
	    		String cod = resultSet.getString("codProd"); 
	    		int unidades = resultSet.getInt("unidades"); 
	    		String cliente = resultSet.getString("Cliente");
	    		list.add(new VOVentaTotal(unidades, cliente, numero, cod));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}  finally {
			if (pStmt != null)
				try {
					pStmt.close();
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

		 return list;
	}
	
	public double totalRecaudado(IConexion iConexion) throws excepcionErrorPersistencia { // obtenerTotalRecaudadoVentas
		double totalUnidadesVendidas = 0;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		try {
			Connection connection = iConexion.getConnection();
			pStmt = connection.prepareStatement(this.consultas.obtenerTotalRecaudadoVentas());
		    pStmt.setString(1, this.codProd);
		    resultSet = pStmt.executeQuery();
	    	if (resultSet.next()) {
	    		totalUnidadesVendidas = resultSet.getInt("total");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}  finally {
			if (pStmt != null)
				try {
					pStmt.close();
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

		return totalUnidadesVendidas;
	}
}
