package logica.valueObjects;

import java.io.Serializable;

public class VOProdVentas extends VOProducto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int unidadesVendidas;

	public VOProdVentas(String codigo, String nombre, int precio, int unidadesVendidas) {
		super(codigo, nombre, precio);
		this.unidadesVendidas = unidadesVendidas;
	}

	public int getUnidadesVendidas() {
		return unidadesVendidas;
	}
}
