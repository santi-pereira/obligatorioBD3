package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import grafica.ventanas.VentasTotalRecaudado;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionNoExisteProducto;

public class ControladorVentasTotalRecaudado extends ConexionRMI{

	private VentasTotalRecaudado ventana;

	private boolean conectado;

	public ControladorVentasTotalRecaudado(VentasTotalRecaudado v) {

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

	public double totalRecaudadoPorVentas(String codP) {
		double total = 0;

		if (conectado) {
			try {
				total = super.iFac.totalRecaudadoPorVentas(codP);
			} catch (RemoteException e) {
				ventana.mostrarError("Error con la conexion a la los datos.");
			} catch (excepcionErrorPersistencia e) {
				ventana.mostrarError("Error con el acceso a los datos.");
			} catch (exceptionNoExisteProducto e) {
				ventana.mostrarError("No existe un producto con ese codigo.");
			}
		} else {
			ventana.mostrarError("Error con la conexion a los datos.");
		}

		return total;

	}
}
