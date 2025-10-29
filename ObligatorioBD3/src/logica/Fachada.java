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
import persistencia.daos.DAOVentas;

public class Fachada {

	DAOProductos daoProductos;
	DAOVentas daoVentas;

	public Fachada() {
		daoProductos = new DAOProductos();
		daoVentas = new DAOVentas();
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
				DAOProductos daoProductos = new DAOProductos();
				Producto producto = new Producto(VoP.getCodigo(), VoP.getNombre(), VoP.getPrecio());
				daoProductos.insert(producto);
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
			
			if(existProd) {
				daoProductos.delete(codP);
				daoVentas.borrarVentasByProducto(codP);
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
			// TODO: cambiar la implementacion
			// si queremos algo sobre la venta de un producto se debe acceder desde el
			// producto
			// si queremos algo sobre un producto directamente se debe acceder desde el
			// daoProducto
			// El DAOVentas no representa “todas las ventas del sistema”, sino las ventas
			// asociadas a un solo producto.
			// Por eso, el único que debería usarlo directamente es el propio Producto.
			/*
			 * int codigo = acc.getCantidadVentasByProducto(con, codP); acc.crearVenta(con,
			 * codigo, codP, voV.getUnidades(), voV.getCliente());
			 */
		}

	}

	/*
	 * El método datosVenta devuelve la cantidad de unidades y el cliente
	 * correspondiente a una venta, dados su número y el código del producto que le
	 * corresponde (chequeando que el código exista y tenga una venta con ese
	 * número).
	 * 
	 */

	public VOVenta datosVenta(String codP, int numV) throws exceptionNoExisteVenta, excepcionErrorPersistencia {
		VOVenta resp = null;
		// TODO: cambiar la implementacion
		// si queremos algo sobre la venta de un producto se debe acceder desde el
		// producto
		// si queremos algo sobre un producto directamente se debe acceder desde el
		// daoProducto
		// El DAOVentas no representa “todas las ventas del sistema”, sino las ventas
		// asociadas a un solo producto.
		// Por eso, el único que debería usarlo directamente es el propio Producto.
		/*
		 * resp = acc.getDatosVenta(con, codP, numV); if (resp == null) { throw new
		 * exceptionNoExisteVenta("No existe una venta con el codigo y numero indicado"
		 * ); }
		 */

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
			
			resp = daoProductos.listarProductos();
			
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

	public List<VOVentaTotal> listadoVentas(String codProd) throws excepcionErrorPersistencia {
		List<VOVentaTotal> list = null;
		// TODO: cambiar la implementacion
		// si queremos algo sobre la venta de un producto se debe acceder desde el
		// producto
		// si queremos algo sobre un producto directamente se debe acceder desde el
		// daoProducto
		// El DAOVentas no representa “todas las ventas del sistema”, sino las ventas
		// asociadas a un solo producto.
		// Por eso, el único que debería usarlo directamente es el propio Producto.
		/*
		 * list = acc.listaVentas(con, codProd);
		 */

		return list;
	}

	public VOProducto productoMasUnidadesVendidas() throws excepcionErrorPersistencia {
		VOProducto voProducto = null;
		// TODO: cambiar la implementacion
		// si queremos algo sobre la venta de un producto se debe acceder desde el
		// producto
		// si queremos algo sobre un producto directamente se debe acceder desde el
		// daoProducto
		// El DAOVentas no representa “todas las ventas del sistema”, sino las ventas
		// asociadas a un solo producto.
		// Por eso, el único que debería usarlo directamente es el propio Producto.
		/* voProducto = acc.productoMasUnidadesVendidas(con); */

		return voProducto;
	}
}
