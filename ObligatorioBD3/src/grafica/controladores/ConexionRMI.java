package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import logica.IFachada;


public class ConexionRMI {

	protected IFachada iFac = null;
	
	private String ipServ = "";
	private String puerto = "";
	
	
	
	public ConexionRMI()
	{
	}
	
	protected boolean Conectar() throws MalformedURLException, RemoteException, NotBoundException
	{
		boolean resp = false;
		try
		{ 
			Properties p = new Properties();
			String nomArchProperties = "./resources/config.properties";
			p.load(new FileInputStream(nomArchProperties));
			ipServ = p.getProperty("ipServidor");
			puerto = p.getProperty("puertoServidor"); 

		}
		catch (IOException e)
		{ 
			e.printStackTrace(); 
		}
		

		
		iFac = (IFachada) Naming.lookup("//"+ipServ+":"+puerto+"/fachada");
		resp = true;

		
		return resp;
	}
	
}
