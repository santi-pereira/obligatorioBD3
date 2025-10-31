package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import grafica.ventanas.ProductoMasVendido;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionNoExisteProducto;
import logica.valueObjects.VOProdVentas;

public class ControladorProductoMasVendido extends ConexionRMI{
	private ProductoMasVendido ventana;

	private boolean conectado;

	public ControladorProductoMasVendido(ProductoMasVendido v) {

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

	public VOProdVentas obtenerProductos() {
		VOProdVentas producto = null;

		if (conectado) {
			try {
				producto = super.iFac.productoMasUnidadesVendidas();
			} catch (RemoteException e) {
				ventana.mostrarError("Error con la conexion a la los datos.");
			} catch (excepcionErrorPersistencia e) {
				ventana.mostrarError("Error con el acceso a los datos.");
			} catch (exceptionNoExisteProducto e) {
				ventana.mostrarError("No hay productos registrados en el sistema.");
			}
		} else {
			ventana.mostrarError("Error con la conexion a la los datos.");
		}

		return producto;

	}

}
