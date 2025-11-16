package logica.valueObjects;

import java.io.Serializable;

public class VOProducto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String codigo;
	String nombre;
	int precio;

	public VOProducto(String codigo, String nombre, int precio) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPrecio() {
		return precio;
	}
}
