package logica;

import java.util.List;

import logica.valueObjects.VOVentaTotal;
import persistencia.daos.DAOVentas;

public class Producto {
	private String codigo;
    private String nombre;
    private int precio;
    private DAOVentas secuencia;   // relación 1 a 1 con el DAO

	public Producto(String codigo, String nombre, int precio) {
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
    
    public void agregarVenta(Venta venta) {
    	// TODO: implementar
    }
    
    public Venta obtenerVenta(int numVenta) {
    	// TODO: implementar
		return null;
    }
    
    public void borrarVentas() {
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
