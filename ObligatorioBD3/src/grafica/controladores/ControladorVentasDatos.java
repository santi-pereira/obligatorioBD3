package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import grafica.ventanas.VentasDatos;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionNoExisteProducto;
import logica.excepciones.exceptionNoExisteVenta;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOVenta;

public class ControladorVentasDatos extends ConexionRMI {

	private VentasDatos ventana;
	private boolean conectado;
	
	
	public ControladorVentasDatos(VentasDatos v) {
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
	
	public VOVenta obtenerDatos(String codigo, int numero) {
		VOVenta voVenta = null;

		if (conectado) {
			try {
				voVenta = super.iFac.datosVenta(codigo, numero);
			} catch (RemoteException e) {
				ventana.mostrarError("Error con la conexion a la los datos.");
			} catch (excepcionErrorPersistencia e) {
				ventana.mostrarError("Error con el acceso a los datos.");
			} catch (exceptionNoExisteVenta e) {
				ventana.mostrarError("No existe la venta.");
			} catch (exceptionNoExisteProducto e) {
				ventana.mostrarError("No existe un producto con ese codigo.");
			}
		} else {
			ventana.mostrarError("Error con la conexion a la los datos.");
		}

		return voVenta;
		}
}