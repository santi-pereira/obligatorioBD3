package grafica;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;
import logica.valueObjects.*;

import logica.IFachada;
import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.excepciones.exceptionNoExisteProducto;
import logica.excepciones.exceptionNoExisteVenta;

public class Prueba {
	
	
	public static void main(String[] args) {
		
		
		String ipServ = "";
		String puerto = "";
		
		try
		{ 
			
			
			Properties p = new Properties();
			String nomArchProperties = "././resources/config.properties";
			p.load(new FileInputStream(nomArchProperties));
			ipServ = p.getProperty("ipServidor");
			puerto = p.getProperty("puertoServidor");

		}
		catch (IOException e)
		{ e.printStackTrace(); }
		
		
		try
		{ 
			
			
			
			
			IFachada fachada = (IFachada) Naming.lookup("//"+ipServ+":"+puerto+"/fachada");
			
	
				try {
					VOProducto test = new VOProducto ("A01", "Producto1", 10);
	
					System.out.println("INICIO //  (Req 5) Prueba");
					fachada.altaProducto(test);
//					System.out.println(test.getUnidades());
//					System.out.println(test.getCliente());
//					fachada.listadoProductos().forEach((VOProducto) -> {
//						System.out.println(" " + VOProducto.getCodigo());
//						System.out.println(" " + VOProducto.getNombre());
//						System.out.println(" " + VOProducto.getPrecio());
//					});
//					
////					fachada.bajaProducto("A111");
//					
				

				} catch (excepcionErrorPersistencia e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (exceptionExisteCodigoProducto e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

		}
		catch (MalformedURLException e) 
		{
			System.out.println("No se establecio la conexion correctamente. MalformedURLException");
		}
		catch (RemoteException e)
		{
			System.out.println("No se establecio la conexion correctamente. RemoteException");
		}
		catch (NotBoundException e)
		{
			System.out.println("No se establecio la conexion correctamente. NotBoundException");
		}
		
	}
}