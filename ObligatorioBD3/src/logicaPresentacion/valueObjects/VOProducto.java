package logicaPresentacion.valueObjects;

public class VOProducto {
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
