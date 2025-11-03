package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import grafica.ventanas.ProductoEliminar;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionNoExisteProducto;

public class ControladorProductoEliminar extends ConexionRMI {

	private ProductoEliminar ventana;

	private boolean conectado;

	public ControladorProductoEliminar(ProductoEliminar v) {

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
	
	public boolean EliminarProducto(String codigo)
	{
		
		boolean resp = false;
		
		if (conectado) {
			try {
                super.iFac.bajaProducto(codigo);
                resp = true;				
				
			} catch (RuntimeException e) {
				ventana.mostrarError("Error Runtime, fallo algo del sistema.");
			} catch (RemoteException e) {
				ventana.mostrarError("Problemas de conexion al servidor"); 
			} catch (excepcionErrorPersistencia e) {
				ventana.mostrarError("Existe un problema con el acceso a los datos");
			} catch (exceptionNoExisteProducto e) {
				ventana.mostrarError("No existe el producto con este codigo");
				e.printStackTrace();
			}

		}
		
		return resp;
	}
	
}
