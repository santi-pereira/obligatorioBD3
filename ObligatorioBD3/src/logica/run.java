package logica;

import java.util.List;

import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProducto;

public class run {

	public static void main (String[] args)
	{
		Fachada fac = new Fachada();
		
		try
		{
			List<VOProducto> lista = fac.listadoProductos();
		
			for (VOProducto voProducto : lista) {
				System.out.println(voProducto.getCodigo());
			}
		
		}catch(excepcionErrorPersistencia ex)
		{
			
			
		}
	
	}
	
	
}
