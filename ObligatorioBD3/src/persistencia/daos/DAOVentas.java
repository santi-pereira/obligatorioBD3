package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	public DAOVentas() {
		super();

		this.consultas = new Consultas();
	}

	public void insback(Venta venta) {
		// TODO: implementar
	}

	public int largo() {
		// TODO: implementar
		return 0;
	}

	public Venta Kesimo(int numVenta) {
		// TODO: implementar
		return null;
	}

	public void borrarVenta() {
		// TODO: implementar
	}

	public void borrarVentasByProducto(String codProducto) throws excepcionErrorPersistencia{
		try {
			Connection connection = AccesoBD.instanciarConexion();

			String query = this.consultas.bajarVentasByCodigoProducto();
			PreparedStatement prs = connection.prepareStatement(query);

			prs.setString(1, codProducto);
			prs.execute();

			prs.close();
		} catch (SQLException ex) {
			throw new excepcionErrorPersistencia("Error en la persistencia de los datos");
		}

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
