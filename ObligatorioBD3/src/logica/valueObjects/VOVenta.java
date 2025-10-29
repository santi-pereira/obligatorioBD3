package logica.valueObjects;

public class VOVenta {
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
