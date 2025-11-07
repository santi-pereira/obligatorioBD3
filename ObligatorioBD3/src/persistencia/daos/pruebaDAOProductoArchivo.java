package persistencia.daos;

import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;

public class pruebaDAOProductoArchivo {

	
	public static void main(String args[]) {
		
		DAOProductosArchivo dao = new DAOProductosArchivo();
		
		try {
//			dao.insert(new Producto("cod111", "Pulgas", 0), null);
			
			System.out.println("Obtengo el producto: cod111");
			
			Producto p = dao.find("cod111", null);
			System.out.println("Nombre: " + p.getNombre());
			System.out.println("Codigo: " + p.getCodigo());
			System.out.println("Precio: " + p.getPrecio());
			
			System.out.println("");
			if(dao.esVacio(null))
			{
				System.out.println("Hay productos ingresados");
			}else {
				System.out.println("NO hay productos ingresados");
			}
			
		} catch (excepcionErrorPersistencia e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
