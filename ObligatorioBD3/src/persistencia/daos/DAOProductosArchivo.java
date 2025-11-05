package persistencia.daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import poolConexiones.IConexion;

public class DAOProductosArchivo implements IDAOProductos {

	private String getRuta() {
		//devuelve la ruta de la carpeta
		return "./";

	}

	public boolean member(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		boolean existe = false;
		String error = "";

		try {
			// Se va a hacer:
			// ConexionAArchivo connection = ((ConexionAArchivo)iConexion).getConnection();

			File file = new File(getRuta() + "PROD-" + codP + ".txt");
			if (file.exists()) {
				existe = true;
			}
		} catch (Exception e) {
			error = "Error en persistencia";
		} finally {
			if (!error.equals("")) {
				throw new excepcionErrorPersistencia(error);
			}
		}

		return existe;
	}

	@Override
	public void insert(Producto producto, IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub

		// Se va a hacer:
		// ConexionAArchivo connection = ((ConexionAArchivo)iConexion).getConnection();

		try (FileWriter writer = new FileWriter(getRuta() + "PROD-" + producto.getCodigo() + ".txt")) {
			writer.write(producto.getCodigo());
			writer.write(producto.getNombre());
			writer.write(producto.getPrecio());
		} catch (IOException e) {
			throw new excepcionErrorPersistencia("Error en la persistencia.");
		}

	}

	@Override
	public Producto find(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		Producto prod = null;

		try (BufferedReader br = new BufferedReader(new FileReader(getRuta() + "PROD-" + codP + ".txt"))) {
			String linea;
			int contador = 0;

			String codigo = "";
			int precio = -1;
			String nombre = "";

			while ((linea = br.readLine()) != null) {
				contador++;

				switch (contador) {
				case 1:
					codigo = linea;
					break;
				case 2:
					nombre = linea;
					break;
				case 3:
					precio = Integer.parseInt(linea);
					break;
				}
			}

			prod = new Producto(codigo, nombre, precio);

		} catch (IOException e) {
			throw new excepcionErrorPersistencia("Error en la persistencia.");
		}
		return prod;
	}

	@Override
	public void delete(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
		
		try {
			// Se va a hacer:
			// ConexionAArchivo connection = ((ConexionAArchivo)iConexion).getConnection();

			File file = new File(getRuta() + "PROD-" + codP + ".txt");
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			throw new excepcionErrorPersistencia( "Error en persistencia");
		} 

		

	}

	@Override
	public boolean esVacio(IConexion iConexion) throws excepcionErrorPersistencia {
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
