package persistencia.daos;

import java.util.List;

import logica.Venta;
import logica.valueObjects.VOVentaTotal;
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
	
	public Venta Kesimo(int numVenta) {
		// TODO: implementar
		return null;
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
