package logica;

import java.util.List;

import logica.excepciones.excepcionErrorPersistencia;
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
	    this.secuencia = new DAOVentas(codigo);
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
	
	public int cantidadVentas() throws excepcionErrorPersistencia {
		return this.secuencia.largo();
	}
    
    public void agregarVenta(Venta venta) throws excepcionErrorPersistencia {
    	this.secuencia.insback(venta);
    }
    
    public Venta obtenerVenta(int numVenta) throws excepcionErrorPersistencia {
    	return this.secuencia.Kesimo(numVenta);
    }
    
    public void borrarVentas() {
    	this.secuencia.borrarVenta();
    }
    
    public List<VOVentaTotal> listarVentas() throws excepcionErrorPersistencia {
    	return this.secuencia.listarVentas();
    }
    
    public double totalRecaudado() throws excepcionErrorPersistencia {
    	return this.secuencia.totalRecaudado() * this.precio;
    }
}
