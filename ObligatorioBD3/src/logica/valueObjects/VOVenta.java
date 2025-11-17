package logica.valueObjects;

import java.io.Serializable;

public class VOVenta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int unidades;
	String Cliente;

	public VOVenta() {
	}

	public VOVenta(int unidades, String cliente) {
		super();
		this.unidades = unidades;
		Cliente = cliente;
	}

	public int getUnidades() {
		return unidades;
	}

	public String getCliente() {
		return Cliente;
	}
}
