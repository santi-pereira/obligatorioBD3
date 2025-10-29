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
	
	public void insback(Venta venta) {
		// TODO: implementar
	}
	
	public int largo() {
		// TODO: implementar
		return 0;
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
