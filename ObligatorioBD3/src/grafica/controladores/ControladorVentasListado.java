package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import grafica.ventanas.VentasListado;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionNoExisteProducto;
import logica.valueObjects.VOVentaTotal;

public class ControladorVentasListado extends ConexionRMI{

	private VentasListado ventana;

	private boolean conectado;

	public ControladorVentasListado(VentasListado v) {

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

	public List<VOVentaTotal> listadoVentas(String codP) {
		List<VOVentaTotal> resultado = new ArrayList<>();
	    
		if (conectado) {
			try {
		        resultado = super.iFac.listadoVentas(codP); 
		    } catch (excepcionErrorPersistencia e) {
		        ventana.mostrarError("Error con el acceso a los datos.");
		    } catch (exceptionNoExisteProducto e) {
		        ventana.mostrarError("No existe un producto con ese código.");
		    } catch (RemoteException e) {
		        ventana.mostrarError("Error de conexión con el servidor.");
		    }
		} else {
			ventana.mostrarError("Error con la conexion a la los datos.");
		}
	    return resultado; 

	}

}
