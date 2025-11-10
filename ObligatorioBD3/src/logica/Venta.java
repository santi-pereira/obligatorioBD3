package logica;

public class Venta {
	private int numero;
	private int unidades;
	private String cliente;
	public Venta(int numero, int unidades, String cliente) {
		super();
		this.numero = numero;
		this.unidades = unidades;
		this.cliente = cliente;
	}
	public int getNumero() {
		return numero;
	}
	public int getUnidades() {
		return unidades;
	}
	public String getCliente() {
		return cliente;
	}
	public void setNumero(int numero) {
        this.numero = numero;
    }
}
