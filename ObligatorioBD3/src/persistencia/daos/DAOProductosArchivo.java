package persistencia.daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import poolConexiones.IConexion;
import poolConexiones.PoolConexionesArchivo;

public class DAOProductosArchivo implements IDAOProductos {

	private PoolConexionesArchivo pool;
	    
	private String getRuta() {
		//devuelve la ruta de la carpeta
		return "./";

	}
	
	private int obtenerCantidadVendidaDeArchivo(String codProducto) {
	    int total = 0;

	    File carpeta = new File(getRuta());
	    if (!carpeta.exists() || !carpeta.isDirectory()) {
	        return 0;
	    }

	    FilenameFilter filtroVentas = (dir, nombre) ->
	            nombre.startsWith("VENTA-" + codProducto + "-") && nombre.endsWith(".txt");

	    File[] archivosVenta = carpeta.listFiles(filtroVentas);

	    if (archivosVenta == null)
	        return 0;

	    for (File ventaFile : archivosVenta) {
	        try (BufferedReader br = new BufferedReader(new FileReader(ventaFile))) {
	            String numVentaStr = br.readLine();
	            String codArchivo = br.readLine();
	            String unidadesStr = br.readLine();
	            br.readLine();   

	            if (codProducto.equalsIgnoreCase(codArchivo) && unidadesStr != null) {
	                int unidades = Integer.parseInt(unidadesStr.trim());
	                total += unidades;
	            }
	        } catch (IOException | NumberFormatException e) {
	            System.err.println("Error leyendo " + ventaFile.getName() + ": " + e.getMessage());
	        }
	    }

	    return total;
	}
	
    public DAOProductosArchivo() {
        this.pool = new PoolConexionesArchivo();
    }

    @Override
	public boolean member(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
	boolean existe = false;
	IConexion conexion = null;

		try {
			conexion = pool.obtenerConexion(false);
			
			File file = new File(getRuta() + "PROD-" + codP + ".txt");
			if (file.exists()) {
				existe = true;
			}
		} catch (Exception e) {
			throw new excepcionErrorPersistencia ("Error en persistencia");
		} finally {
			if (conexion != null) {
				pool.liberarConexion(conexion);
			}
		}

		return existe;
	}

	@Override
	public void insert(Producto producto, IConexion iConexion) throws excepcionErrorPersistencia {
    IConexion conexion = null;
    
        try {
            conexion = pool.obtenerConexion(true);
            try (FileWriter writer = new FileWriter(getRuta() + "PROD-" + producto.getCodigo() + ".txt")) {
                writer.write(producto.getCodigo() + System.lineSeparator());
                writer.write(producto.getNombre() + System.lineSeparator());
                writer.write(String.valueOf(producto.getPrecio()));
            }
        } catch (IOException e) {
            throw new excepcionErrorPersistencia("Error en la persistencia.");
        } finally {
            if (conexion != null) {
                pool.liberarConexion(conexion);
            }
        }
    }

	@Override
	public Producto find(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
	Producto prod = null;
	IConexion conexion = null;
	        
	        try {
	            conexion = pool.obtenerConexion(false);
	            try (BufferedReader br = new BufferedReader(new FileReader(getRuta() + "PROD-" + codP + ".txt"))) {
	                String codigo = br.readLine();
	                String nombre = br.readLine();
	                int precio = Integer.parseInt(br.readLine());
	                prod = new Producto(codigo, nombre, precio);
	            }
	        } catch (IOException e) {
	            throw new excepcionErrorPersistencia("Error en la persistencia.");
	        } finally {
	            if (conexion != null) {
	                pool.liberarConexion(conexion);
	            }
	        }
	        return prod;
	    }

	@Override
	public void delete(String codP, IConexion iConexion) throws excepcionErrorPersistencia {
    IConexion conexion = null;
    
        try {
            conexion = pool.obtenerConexion(true);
            
            File file = new File(getRuta() + "PROD-" + codP + ".txt");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new excepcionErrorPersistencia("Error en persistencia.");
        } finally {
            if (conexion != null) {
                pool.liberarConexion(conexion);
            }
        }
    }

	@Override
	public boolean esVacio(IConexion iConexion) throws excepcionErrorPersistencia {
    boolean vacio = true;
    IConexion conexion = null;
        try {
            conexion = pool.obtenerConexion(false);
            File carpeta = new File(getRuta());
            FilenameFilter filtro = (dir, nombre) -> nombre.toLowerCase().startsWith("prod-");
            File[] archivos = carpeta.listFiles(filtro);
            vacio = (archivos == null || archivos.length == 0);
        } catch (Exception e) {
            throw new excepcionErrorPersistencia("Error en persistencia.");
        } finally {
            if (conexion != null) {
                pool.liberarConexion(conexion);
            }
        }
        return vacio;
    }

	@Override
	public List<VOProducto> listarProductos(IConexion iConexion) throws excepcionErrorPersistencia {
		List<VOProducto> resp = new ArrayList<VOProducto>();
		
		IConexion conexion = null;

	    try {
	        conexion = pool.obtenerConexion(false);

	        File carpeta = new File(getRuta());
	        if (carpeta.exists() && carpeta.isDirectory()) {
	            FilenameFilter filtro = (dir, nombre) -> nombre.toLowerCase().startsWith("prod-");
	            File[] archivos = carpeta.listFiles(filtro);

	            if (archivos != null && archivos.length > 0) {
	                for (File archivo : archivos) {
	                    String cod = archivo.getName()
	                                        .replace("PROD-", "")
	                                        .replace(".txt", "");
	                    Producto p = find(cod, conexion);
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
	    } finally {
	        if (conexion != null)
	            pool.liberarConexion(conexion);
	    }

	    return resp;
	}

	@Override
	public VOProdVentas productoMasVendido(IConexion iConexion) throws excepcionErrorPersistencia {
	    IConexion conexion = null;
	    VOProdVentas masVendido = null;

	    try {
	        conexion = pool.obtenerConexion(false);

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

	            int cantidadVendida = obtenerCantidadVendidaDeArchivo(codProducto);

	            if (cantidadVendida > maxCantidadVendida) {
	                maxCantidadVendida = cantidadVendida;
	                productoMasVendido = find(codProducto, conexion);
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
	    } finally {
	        if (conexion != null) {
	            pool.liberarConexion(conexion);
	        }
	    }

	    return masVendido;
	}

}
