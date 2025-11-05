package persistencia.daos;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import poolConexiones.IConexion;

public class DAOProductosArchivo implements IDAOProductos{
	
	private String getRuta()
	{
		return "./";
		
	}
	
	public boolean member(String codP, IConexion iConexion) throws excepcionErrorPersistencia
	{
		boolean existe = false;
		String error = "";
		
		try {
			//Se va a hacer: 
			//ConexionAArchivo connection = ((ConexionAArchivo)iConexion).getConnection();
			
			File file = new File(getRuta() + "PROD-" + codP + ".txt");
			if(file.exists())
			{
				existe = true;
			}
		}catch(Exception e)
		{
			error = "Error en persistencia";
		}finally{
			if(!error.equals(""))
			{
				throw new excepcionErrorPersistencia(error);
			}
		}
			
			
		return existe;
	}

	@Override
	public void insert(Producto producto, IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Producto find(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean esVacio(IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<VOProducto> listarProductos(IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VOProdVentas productoMasVendido(IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		return null;
	}

}
