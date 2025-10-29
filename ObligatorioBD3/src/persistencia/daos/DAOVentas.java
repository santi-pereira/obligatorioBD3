package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import logica.Venta;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOVentaTotal;
import persistencia.AccesoBD;
import persistencia.consultas.Consultas;

public class DAOVentas {

	private Consultas consultas;
	private String codProd;

	public DAOVentas(String codProd) {
		super();
		this.codProd = codProd;
		this.consultas = new Consultas();
	}
	
	public void insback(Venta venta) throws excepcionErrorPersistencia {
		try (Connection connection = AccesoBD.instanciarConexion();
				PreparedStatement pStmt = connection.prepareStatement(this.consultas.insertarVenta())) {
			pStmt.setInt(1, venta.getNumero());
			pStmt.setString(2, this.codProd);
			pStmt.setInt(3, venta.getUnidades());
			pStmt.setString(4, venta.getCliente());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}
	}
	
	public int largo() throws excepcionErrorPersistencia {
		int largo = 0;
		try (Connection connection = AccesoBD.instanciarConexion();
				PreparedStatement pStmt = connection.prepareStatement(this.consultas.cantidadVentasByCodigoProducto())) {
			    pStmt.setString(1, this.codProd);
			    try (ResultSet resultSet = pStmt.executeQuery()) {
			    	if (resultSet.next()) {
			    		largo = resultSet.getInt("cantidad"); 
					}
			    } 
			} catch (SQLException e) {
				throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
			}
		return largo;
	}
	
	public Venta Kesimo(int numVenta) throws excepcionErrorPersistencia {
		Venta venta = null;
		try (Connection connection = AccesoBD.instanciarConexion();
				PreparedStatement pStmt = connection.prepareStatement(this.consultas.obtenerDatosVenta())) {
			    pStmt.setInt(1, numVenta);
			    try (ResultSet resultSet = pStmt.executeQuery()) {
			    	if (resultSet.next()) {
			    		int cantidad = resultSet.getInt("unidades"); 
			    		String cliente = resultSet.getString("cliente"); 
			    		venta = new Venta(numVenta, cantidad, cliente);
					}
			    } 
			} catch (SQLException e) {
				throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
			}
		
		return venta;
	}
	
	public void borrarVenta() {
		// TODO: implementar
	}
	
	public List<VOVentaTotal> listarVentas() {
		// TODO: implementar
		return null;
	}
	
	public double totalRecaudado() {
		// TODO: implementar
		return 0;
	}
}
