package logica;

import java.util.List;

import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOVentaTotal;
import persistencia.daos.IDAOVentas;
import persistencia.fabricas.FabricaManager;
import poolConexiones.IConexion;

public class Producto {
	private String codigo;
    private String nombre;
    private int precio;
    private IDAOVentas secuencia;   // relación 1 a 1 con el DAO

	public Producto(String codigo, String nombre, int precio) throws excepcionErrorPersistencia {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.secuencia = FabricaManager.getFabrica().crearDAOVentas(this.codigo);
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
	
	public int cantidadVentas(IConexion iConexion) throws excepcionErrorPersistencia {
		return this.secuencia.largo(iConexion);
	}
    
    public void agregarVenta(Venta venta, IConexion iConexion) throws excepcionErrorPersistencia {
    	this.secuencia.insback(venta, iConexion);
    }
    
    public Venta obtenerVenta(int numVenta, IConexion iConexion) throws excepcionErrorPersistencia {
    	return this.secuencia.Kesimo(numVenta, iConexion);
    }
    
    public void borrarVentas(IConexion iConexion) throws excepcionErrorPersistencia {
    	this.secuencia.borrarVenta(iConexion);
    }
    
    public List<VOVentaTotal> listarVentas(IConexion iConexion) throws excepcionErrorPersistencia {
    	return this.secuencia.listarVentas(iConexion);
    }
    
    public double totalRecaudado(IConexion iConexion) throws excepcionErrorPersistencia {
    	return this.secuencia.totalRecaudado(iConexion) * this.precio;
    }
}
