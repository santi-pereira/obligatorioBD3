package logica;

import java.util.List;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.excepciones.exceptionNoExisteProducto;
import logica.excepciones.exceptionNoExisteVenta;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import logica.valueObjects.VOVenta;
import logica.valueObjects.VOVentaTotal;
import persistencia.daos.DAOProductos;
import poolConexiones.IConexion;
import poolConexiones.IPoolConexiones;
import poolConexiones.PoolConexiones;

public class Fachada extends UnicastRemoteObject implements IFachada {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPoolConexiones ipool;
	
	DAOProductos daoProducto;

	public Fachada() throws RemoteException, excepcionErrorPersistencia {
		ipool = new PoolConexiones();
		daoProducto = new DAOProductos();
	}

	private boolean existeProducto(String codP, IConexion iConexion) throws RemoteException, excepcionErrorPersistencia {
		return daoProducto.member(codP, iConexion);
	}
 
	public void altaProducto(VOProducto VoP) throws RemoteException, exceptionExisteCodigoProducto, excepcionErrorPersistencia {
		IConexion iConexion = null;
		boolean altaProdOK = false;
		try {
			iConexion = ipool.obtenerConexion(true);
			if (this.existeProducto(VoP.getCodigo(), iConexion))
				throw new exceptionExisteCodigoProducto("Ya existe un producto con el codigo indicado.");

			Producto producto = new Producto(VoP.getCodigo(), VoP.getNombre(), VoP.getPrecio());
			this.daoProducto.insert(producto, iConexion);
			altaProdOK = true;
		} catch (excepcionErrorPersistencia e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			if (iConexion != null) {
				ipool.liberarConexion(iConexion, altaProdOK);
	        }
		}
	}

	public void bajaProducto(String codP) throws RemoteException, exceptionNoExisteProducto, excepcionErrorPersistencia {
		IConexion iConexion = null;
		boolean existProd = false; 
		boolean errorPersistencia = false;
		String msgError = "";
		
		try {
			iConexion = ipool.obtenerConexion(true);
			existProd = this.existeProducto(codP, iConexion);
			Producto producto = this.daoProducto.find(codP, iConexion);

			if(existProd) {
				producto.borrarVentas(iConexion);
				this.daoProducto.delete(codP, iConexion);
			}
		} catch (Exception e) {
			errorPersistencia = true;
			msgError = "Error de acceso a los datos.";
		}finally {
			if(!existProd)
			{
				if (iConexion != null)
					ipool.liberarConexion(iConexion, false);
				throw new exceptionNoExisteProducto("El producto " + codP + " no existe.");
			}

			if(errorPersistencia)
			{
				if (iConexion != null)
					ipool.liberarConexion(iConexion, false);
				throw new excepcionErrorPersistencia(msgError);
			}
			if (iConexion != null)
				ipool.liberarConexion(iConexion, true);
		}

	}

	/*
	 * El método registroVenta registra una nueva venta, chequeando que el código
	 * del producto correspondiente exista. Se asignará automáticamente a la nueva
	 * venta el número siguiente al de la última venta registrada hasta el momento
	 * para ese producto (si es la primera, tendrá el número 1).
	 */
	public void registroVenta(String codP, VOVenta voV) throws RemoteException, exceptionNoExisteProducto, excepcionErrorPersistencia {
		IConexion iConexion = null;
		try {
			iConexion = ipool.obtenerConexion(true);
			if (!this.existeProducto(codP, iConexion)) {
				ipool.liberarConexion(iConexion, false);
				throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
			} else {
				Producto producto = this.daoProducto.find(codP, iConexion);
				int numVenta = producto.cantidadVentas(iConexion) + 1;
				Venta venta = new Venta(numVenta, voV.getUnidades(), voV.getCliente());
				producto.agregarVenta(venta, iConexion);
				ipool.liberarConexion(iConexion, true);
			}
		} catch (excepcionErrorPersistencia ePersistencia) {
			if (iConexion != null) {
				ipool.liberarConexion(iConexion, false);
				throw new excepcionErrorPersistencia("Error con la persistencia");
			}
		}
	}

	/*
	 * El método datosVenta devuelve la cantidad de unidades y el cliente
	 * correspondiente a una venta, dados su número y el código del producto que le
	 * corresponde (chequeando que el código exista y tenga una venta con ese
	 * número).
	 * 
	 * */
	public VOVenta datosVenta(String codP, int numV) throws RemoteException, exceptionNoExisteVenta, excepcionErrorPersistencia, exceptionNoExisteProducto
	{
		IConexion iConexion = null;
		VOVenta resp = null;
		try {
			iConexion = ipool.obtenerConexion(true);
			if (!this.existeProducto(codP, iConexion)) {
				ipool.liberarConexion(iConexion, false);
				throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
			}

			Producto producto = this.daoProducto.find(codP, iConexion);
			Venta venta = producto.obtenerVenta(numV, iConexion);
			if (venta == null) {
				ipool.liberarConexion(iConexion, false);
				throw new exceptionNoExisteVenta("No existe una venta con el codigo y numero indicado");
			}
			ipool.liberarConexion(iConexion, true);
			resp = new VOVenta(venta.getUnidades(), venta.getCliente());
		} catch (excepcionErrorPersistencia ePersistencia) {
			if (iConexion != null)
				ipool.liberarConexion(iConexion, false);
			throw new excepcionErrorPersistencia("Error con la persistencia");
		}

		return resp;
	}

	/**
	 * El método listadoProductos devuelve un listado de todos los productos,
	 * ordenado por código.
	 * 
	 */

	public List<VOProducto> listadoProductos() throws RemoteException, excepcionErrorPersistencia {
		IConexion iConexion = null;
		List<VOProducto> resp = null;
		boolean errPer = false;
		String msgError = "";
		try {
			iConexion = ipool.obtenerConexion(true);
			resp = this.daoProducto.listarProductos(iConexion);
		}catch(Exception e) {
			errPer = true;
			msgError ="Error de acceso a los datos.";
		} finally {
			if(errPer)
			{
				if (iConexion != null)
					ipool.liberarConexion(iConexion, false);
				throw new excepcionErrorPersistencia(msgError);
			}
			if (iConexion != null)
				ipool.liberarConexion(iConexion, true);
		}

		return resp;
	}

	public List<VOVentaTotal> listadoVentas(String codProd) throws RemoteException, excepcionErrorPersistencia, exceptionNoExisteProducto {
		IConexion iConexion = null;
		Producto producto = null;
		List<VOVentaTotal> list = null;
		try {
			iConexion = ipool.obtenerConexion(true);
			if (!this.existeProducto(codProd, iConexion)) {
				throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
			}
			producto = this.daoProducto.find(codProd, iConexion);
			list = producto.listarVentas(iConexion);
			ipool.liberarConexion(iConexion, true);
		} catch (Exception e) {
			if (iConexion != null)
				ipool.liberarConexion(iConexion, false);
			throw new excepcionErrorPersistencia("Error con la persistencia");
		}

		return list;
	}

	public VOProdVentas productoMasUnidadesVendidas() throws RemoteException, excepcionErrorPersistencia, exceptionNoExisteProducto {
		IConexion iConexion = null;
		VOProdVentas voProdVentas = null;
		try {
			iConexion = ipool.obtenerConexion(true);
			boolean esVacio = this.daoProducto.esVacio(iConexion);
			if (esVacio) {
				throw new exceptionNoExisteProducto("No existe ningun producto registrado en el sistema.");
			}
			voProdVentas = this.daoProducto.productoMasVendido(iConexion);
			ipool.liberarConexion(iConexion, true);
		} catch (Exception ex) {
			if (iConexion != null)
				ipool.liberarConexion(iConexion, false);
			throw new excepcionErrorPersistencia("Error con la persistencia");
		}

		return voProdVentas;
	}
	
	public double totalRecaudadoPorVentas(String codProd) throws RemoteException, exceptionNoExisteProducto, excepcionErrorPersistencia {
		IConexion iConexion = null;
		double totalRecaudado = 0;
		try {
			iConexion = ipool.obtenerConexion(true);
			if (!this.existeProducto(codProd, iConexion)) {
				ipool.liberarConexion(iConexion, false);
				throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
			}
			Producto producto = this.daoProducto.find(codProd, iConexion);
			totalRecaudado = producto.totalRecaudado(iConexion);
			ipool.liberarConexion(iConexion, true);
		} catch (Exception ex) {
			if (iConexion != null)
				ipool.liberarConexion(iConexion, false);
			throw new excepcionErrorPersistencia("Error con la persistencia");
		}

		return totalRecaudado;
	}
}
