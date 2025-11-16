package persistencia.daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import logica.valueObjects.VOVentaTotal;
import poolConexiones.IConexion;

public class DAOProductosArchivo implements IDAOProductos {
	    
	private String getRuta() {
		String carpeta = "persistenciaArchivo";

	    try {
	        Path dir = Paths.get(carpeta);

	        // Crea la carpeta y subcarpetas si no existen
	        if (!Files.exists(dir)) {
	            Files.createDirectories(dir);
	        }

	        // Devuelve ruta absoluta
	        return dir.toAbsolutePath().toString() + File.separator;

	    } catch (IOException e) {
	        throw new RuntimeException("No se pudo crear la carpeta de persistencia", e);
	    }
	}
	
	
    public DAOProductosArchivo() {
    }

    @Override
	public boolean member(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
	boolean existe = false;

		try {
			
			File file = new File(getRuta() + "PROD-" + codP + ".txt");
			if (file.exists()) {
				existe = true;
			}
		} catch (Exception e) {
			throw new excepcionErrorPersistencia ("Error en persistencia");
		} 

		return existe;
	}

	@Override
	public void insert(Producto producto, IConexion iConexion) throws excepcionErrorPersistencia {
    
        try {
            try (FileWriter writer = new FileWriter(getRuta() + "PROD-" + producto.getCodigo() + ".txt")) {
                writer.write(producto.getCodigo() + System.lineSeparator());
                writer.write(producto.getNombre() + System.lineSeparator());
                writer.write(String.valueOf(producto.getPrecio()));
            }
        } catch (IOException e) {
            throw new excepcionErrorPersistencia("Error en la persistencia.");
        }
    }

	@Override
	public Producto find(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
	Producto prod = null;
	        
	        try {
	            try (BufferedReader br = new BufferedReader(new FileReader(getRuta() + "PROD-" + codP + ".txt"))) {
	                String codigo = br.readLine();
	                String nombre = br.readLine();
	                int precio = Integer.parseInt(br.readLine());
	                prod = new Producto(codigo, nombre, precio);
	            }
	        } catch (IOException e) {
	            throw new excepcionErrorPersistencia("Error en la persistencia.");
	        } 
	        return prod;
	    }

	@Override
	public void delete(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
    
        try {
            
            File file = new File(getRuta() + "PROD-" + codP + ".txt");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new excepcionErrorPersistencia("Error en persistencia.");
        } 
    }

	@Override
	public boolean esVacio(IConexion iConexion) throws excepcionErrorPersistencia {
    boolean vacio = true;
        try {
            File carpeta = new File(getRuta());
            FilenameFilter filtro = (dir, nombre) -> nombre.toLowerCase().startsWith("prod-");
            File[] archivos = carpeta.listFiles(filtro);
            vacio = (archivos == null || archivos.length == 0);
        } catch (Exception e) {
            throw new excepcionErrorPersistencia("Error en persistencia.");
        } 
        
        return vacio;
    }

	@Override
	public List<VOProducto> listarProductos(IConexion iConexion) throws excepcionErrorPersistencia {
		List<VOProducto> resp = new ArrayList<VOProducto>();
		
	    try {

	        File carpeta = new File(getRuta());
	        if (carpeta.exists() && carpeta.isDirectory()) {
	            FilenameFilter filtro = (dir, nombre) -> nombre.toLowerCase().startsWith("prod-");
	            File[] archivos = carpeta.listFiles(filtro);

	            if (archivos != null && archivos.length > 0) {
	                for (File archivo : archivos) {
	                    String cod = archivo.getName()
	                                        .replace("PROD-", "")
	                                        .replace(".txt", "");
	                    Producto p = find(cod, iConexion);
	                    if (p != null) {
	                        resp.add(new VOProducto(p.getCodigo(), p.getNombre(), p.getPrecio()));
	                    }
	                }
	            }
	        } else {
	            throw new excepcionErrorPersistencia("Error de persistencia: carpeta no encontrada.");
	        }
	    } catch (Exception e) {
	        throw new excepcionErrorPersistencia("Error al listar productos.");
	    } 

	    return resp;
	}

	@Override
	public VOProdVentas productoMasVendido(IConexion iConexion) throws excepcionErrorPersistencia {
	    VOProdVentas masVendido = null;

	    try {

	        File carpeta = new File(getRuta());
	        if (!carpeta.exists() || !carpeta.isDirectory()) {
	            throw new excepcionErrorPersistencia("Carpeta de datos inexistente.");
	        }

	        FilenameFilter filtroProd = (dir, nombre) -> nombre.startsWith("PROD-") && nombre.endsWith(".txt");
	        File[] archivosProd = carpeta.listFiles(filtroProd);

	        if (archivosProd == null || archivosProd.length == 0) {
	            return null;
	        }

	        int maxCantidadVendida = -1;
	        Producto productoMasVendido = null;

	        for (File archProd : archivosProd) {
	            String codProducto = archProd.getName()
	                    .replace("PROD-", "")
	                    .replace(".txt", "");
	            
	            Producto prod = find(codProducto, iConexion);
	            
	            int cantidadVendida = 0;
	            
	            for (VOVentaTotal venta : prod.listarVentas(iConexion)) {
	            	cantidadVendida += venta.getUnidades();
				}
	            
	          

	            if (cantidadVendida > maxCantidadVendida) {
	                maxCantidadVendida = cantidadVendida;
	                productoMasVendido = find(codProducto, iConexion);
	            }
	        }

	        if (productoMasVendido != null) {
	            masVendido = new VOProdVentas(
	                productoMasVendido.getCodigo(),
	                productoMasVendido.getNombre(),
	                productoMasVendido.getPrecio(),
	                maxCantidadVendida
	            );
	        }

	    } catch (Exception e) {
	        throw new excepcionErrorPersistencia("Error calculando producto más vendido.");
	    } 

	    return masVendido;
	}

}
