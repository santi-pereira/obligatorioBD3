package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import grafica.ventanas.VentaRegistrar;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionNoExisteProducto;
import logica.valueObjects.VOVenta;

public class ControladorVentaRegistrar extends ConexionRMI {

	private VentaRegistrar ventana;
	private boolean conectado;
	
	
	public ControladorVentaRegistrar(VentaRegistrar v) {
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
	
	public boolean CrearVenta(String codigo, int unidades, String cliente) {
		
		boolean resp = false;
		
		if (conectado) {
			try {
                VOVenta voVenta = new VOVenta(unidades, cliente);
                super.iFac.registroVenta(codigo, voVenta);
                
				resp = true;
				
			} catch (RuntimeException e) {
				ventana.mostrarError("Error Runtime, fallo algo del sistema.");
			} catch (RemoteException e) {
				ventana.mostrarError("Problemas de conexion al servidor"); 
			} catch (exceptionNoExisteProducto e) {
				ventana.mostrarError("Ya existe un producto con el codigo ingresado."); 
			} catch (excepcionErrorPersistencia e) {
				ventana.mostrarError("Existe un problema con el acceso a los datos");
			}
		}
		return resp;
	}

}
