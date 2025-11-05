package persistencia.daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
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
		boolean vacio = true;
		
		File carpeta = new File(getRuta());

        if (carpeta.exists() && carpeta.isDirectory()) {
            
            FilenameFilter filtro = (dir, nombre) -> nombre.toLowerCase().startsWith("PROD-");

            File[] archivos = carpeta.listFiles(filtro);

            if (archivos != null && archivos.length > 0) {
                vacio = false;
            }
        } else {
            throw new excepcionErrorPersistencia("Error en persistencia.");
        }
        
        return vacio;
	}

	@Override
	public List<VOProducto> listarProductos(IConexion iConexion) throws excepcionErrorPersistencia {
		List<VOProducto> resp = new ArrayList<VOProducto>();
		
		File carpeta = new File(getRuta());
		
		if (carpeta.exists() && carpeta.isDirectory()) {
            FilenameFilter filtro = (dir, nombre) -> nombre.toLowerCase().startsWith("PROD-");

            File[] archivos = carpeta.listFiles(filtro);

            if (archivos != null && archivos.length > 0) {
                
                for (File archivo : archivos) {
                	Producto p = find(archivo.getName().replace("PROD-", "").replace(".txt", ""), iConexion);
                	resp.add(new VOProducto(p.getCodigo(), p.getNombre(), p.getPrecio()));
                }
            } 
        } else {
            throw new excepcionErrorPersistencia("Error de persistencia.");
        }
		
		return resp;
	}

	@Override
	public VOProdVentas productoMasVendido(IConexion iConexion) throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		return null;
	}

}
