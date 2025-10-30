package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import grafica.ventanas.ProductoListado;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProducto;

public class ControladorProductosLista extends ConexionRMI {

	private ProductoListado ventana;

	private boolean conectado;

	public ControladorProductosLista(ProductoListado v) {

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

	public List<VOProducto> obtenerProductos() {
		List<VOProducto> lista = null;

		if (conectado) {
			try {
				lista = super.iFac.listadoProductos();
			} catch (RemoteException e) {
				ventana.mostrarError("Error con la conexion a la los datos.");
			} catch (excepcionErrorPersistencia e) {
				ventana.mostrarError("Error con el acceso a los datos.");
			}
		} else {
			ventana.mostrarError("Error con la conexion a la los datos.");
		}

		return lista;

	}

}
