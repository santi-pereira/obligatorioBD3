package logica;

import java.rmi.RemoteException;
import java.util.List;

import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.valueObjects.VOProducto;

public class run {

	public static void main (String[] args)
	{
		Fachada fac = null;
		try {
			fac = new Fachada();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (excepcionErrorPersistencia e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			
			fac.altaProducto(new VOProducto("prodPrueba", "Producto de Prueba", 100));
			
			/*List<VOProducto> lista = fac.listadoProductos();
		
			for (VOProducto voProducto : lista) {
				System.out.println(voProducto.getCodigo());
			}*/
		
		}catch(excepcionErrorPersistencia ex)
		{
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exceptionExisteCodigoProducto e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
}
