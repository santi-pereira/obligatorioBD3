package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
