package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import grafica.ventanas.ProductoCrear;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.valueObjects.VOProducto;

public class ControladorProductoCrear extends ConexionRMI {

	private ProductoCrear ventana;

	private boolean conectado;

	public ControladorProductoCrear(ProductoCrear v) {

		super();
			ventana = v;
		try {
			conectado = Conectar();
		} catch (MalformedURLException e) {
			ventana.mostrarError("Problema de formar la URL");

		} catch (RemoteException e) {
			ventana.mostrarError("Problemas de conexion al servidor");

		} catch (NotBoundException e) {
			ventana.mostrarError("Problema con la direccion del servidor");
		}

	}
	
	public boolean CrearProducto(String codigo, String nombre, int precio)
	{
		
		boolean resp = false;
		
		if (conectado) {
			try {
                VOProducto voProd = new VOProducto(codigo, nombre, precio);
                super.iFac.altaProducto(voProd);
				
				resp = true;
				
			} catch (RuntimeException e) {
				ventana.mostrarError("Error Runtime, fallo algo del sistema.");
			} catch (RemoteException e) {
				ventana.mostrarError("Problemas de conexion al servidor"); 
			} catch (exceptionExisteCodigoProducto e) {
				ventana.mostrarError("Ya existe un producto con el codigo ingresado."); 
			} catch (excepcionErrorPersistencia e) {
				ventana.mostrarError("Existe un problema con el acceso a los datos");
			}

		}
		
		return resp;
	}

}
