package logica;

import java.util.List;

import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.excepciones.exceptionNoExisteProducto;
import logica.excepciones.exceptionNoExisteVenta;
import logica.valueObjects.VOProducto;
import logica.valueObjects.VOVenta;
import logica.valueObjects.VOVentaTotal;
import persistencia.daos.DAOProductos;

public class Fachada {

	DAOProductos daoProducto;

	public Fachada() {
		daoProducto = new DAOProductos();
	}

	private boolean existeProducto(String codP) throws excepcionErrorPersistencia {
		DAOProductos daoProductos = new DAOProductos();
		return daoProductos.member(codP);
	}
 
	public void altaProducto(VOProducto VoP) throws exceptionExisteCodigoProducto, excepcionErrorPersistencia {

		try {
			if (this.existeProducto(VoP.getCodigo()))
				throw new exceptionExisteCodigoProducto("Ya existe un producto con el codigo indicado.");
			else {
				Producto producto = new Producto(VoP.getCodigo(), VoP.getNombre(), VoP.getPrecio());
				this.daoProducto.insert(producto);
			}
		} catch (excepcionErrorPersistencia e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		}
	}

	public void bajaProducto(String codP) throws exceptionNoExisteProducto, excepcionErrorPersistencia {

		boolean existProd = false; 
		boolean errorPersistencia = false;
		String msgError = "";
		
		try {
			existProd = this.existeProducto(codP);
			Producto producto = this.daoProducto.find(codP);
			
			if(existProd) {
				producto.borrarVentas();
				this.daoProducto.delete(codP);
			}
		} catch (Exception e) {
			errorPersistencia = true;
			msgError = "Error de acceso a los datos.";
		}finally {
			
			if(existProd)
			{
				throw new exceptionNoExisteProducto("El producto " + codP + " no existe.");
			}
			
			if(errorPersistencia)
			{
				throw new excepcionErrorPersistencia(msgError);
			}
			
		}

	}

	/*
	 * El método registroVenta registra una nueva venta, chequeando que el código
	 * del producto correspondiente exista. Se asignará automáticamente a la nueva
	 * venta el número siguiente al de la última venta registrada hasta el momento
	 * para ese producto (si es la primera, tendrá el número 1).
	 */
	public void registroVenta(String codP, VOVenta voV) throws exceptionNoExisteProducto, excepcionErrorPersistencia {
		if (!this.existeProducto(codP))
			throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
		else {

			Producto producto = this.daoProducto.find(codP);
			int numVenta = producto.cantidadVentas() + 1;
			Venta venta = new Venta(numVenta, voV.getUnidades(), voV.getCliente());
			producto.agregarVenta(venta);
		}
	}

	/*
	 * El método datosVenta devuelve la cantidad de unidades y el cliente
	 * correspondiente a una venta, dados su número y el código del producto que le
	 * corresponde (chequeando que el código exista y tenga una venta con ese
	 * número).
	 * 
	 * */
	public VOVenta datosVenta(String codP, int numV) throws exceptionNoExisteVenta, excepcionErrorPersistencia, exceptionNoExisteProducto
	{
		VOVenta resp = null;
		if (!this.existeProducto(codP)) {
			throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
		}

		Producto producto = this.daoProducto.find(codP);
		Venta venta = producto.obtenerVenta(numV);
		if (venta == null) {
			throw new exceptionNoExisteVenta("No existe una venta con el codigo y numero indicado");
		}
		resp = new VOVenta(venta.getUnidades(), venta.getCliente());

		return resp;
	}

	/**
	 * El método listadoProductos devuelve un listado de todos los productos,
	 * ordenado por código.
	 * 
	 */

	public List<VOProducto> listadoProductos() throws excepcionErrorPersistencia {
		List<VOProducto> resp = null;
		boolean errPer = false;
		String msgError = "";
		try {
			
			resp = this.daoProducto.listarProductos();
			
		}catch(Exception e)
		{
			errPer = true;
			msgError ="Error de acceso a los datos.";
		}finally {
			if(errPer)
			{
				throw new excepcionErrorPersistencia(msgError);
			}
		}

		return resp;
	}

	public List<VOVentaTotal> listadoVentas(String codProd) throws excepcionErrorPersistencia, exceptionNoExisteProducto {
		if (!this.existeProducto(codProd)) {
			throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
		}
		Producto producto = this.daoProducto.find(codProd);
		
		return producto.listarVentas();
	}

	public VOProducto productoMasUnidadesVendidas() throws excepcionErrorPersistencia, exceptionNoExisteProducto {
		boolean esVacio = this.daoProducto.esVacio();
		if (esVacio) {
			throw new exceptionNoExisteProducto("No existe ningun producto registrado en el sistema.");
		}

		return this.daoProducto.productoMasVendido();
	}
	
	public double totalRecaudadoPorVentas(String codProd) throws exceptionNoExisteProducto, excepcionErrorPersistencia {
		if (!this.existeProducto(codProd)) {
			throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
		}
		Producto producto = this.daoProducto.find(codProd);
		return producto.totalRecaudado();
	}
}
