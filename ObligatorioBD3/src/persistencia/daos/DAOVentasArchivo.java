package persistencia.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import logica.Venta;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOVentaTotal;
import poolConexiones.IConexion;

public class DAOVentasArchivo implements IDAOVentas {
	
	private String codProd;
    private static final String BASE_DIR = "persistenciaArchivo";
    private static final String FILE_PREFIX = "VENTA-";
    private static final String FILE_SUFFIX = ".txt";

    //constructor 
    public DAOVentasArchivo(String codProd) {
        this.codProd = codProd;
        crearDirectorioBase();
    }

    // --- MÉTODOS AUXILIARES PRIVADOS ---

    private void crearDirectorioBase() {
    	File dir = new File(BASE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
	}

	// Genera el nombre de archivo según la convención: VENTA-Pul001-1.txt
    private String getFileName(int numVenta) {
        return FILE_PREFIX + this.codProd + "-" + numVenta + FILE_SUFFIX;
    }

    // --- Obtener archivo en el directorio correcto ---
    private File getFile(int numVenta) {
        return new File(BASE_DIR, getFileName(numVenta));
    }

    // Persiste los datos de una Venta en un archivo
    private void escribirVentaEnArchivo(Venta venta) throws excepcionErrorPersistencia {
        File file = getFile(venta.getNumero());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.valueOf(venta.getNumero())); 
            bw.newLine();
            bw.write(this.codProd);
            bw.newLine();
            bw.write(String.valueOf(venta.getUnidades()));
            bw.newLine();
            bw.write(venta.getCliente());
            bw.newLine();
        } catch (IOException e) {
            throw new excepcionErrorPersistencia("Error al escribir la venta en el archivo: " + file.getAbsolutePath());
        }
    }

    // Lee los datos de una Venta desde un archivo
    private Venta leerVentaDesdeArchivo(int numVenta) throws excepcionErrorPersistencia {
        File file = getFile(numVenta);
        
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int numero = Integer.parseInt(br.readLine().trim());         
            br.readLine(); 
            int unidades = Integer.parseInt(br.readLine().trim());     
            String cliente = br.readLine().trim();                      

            return new Venta(numero, unidades, cliente); 

        } catch (IOException | NumberFormatException e) {
            throw new excepcionErrorPersistencia("Error al leer la venta del archivo: " + file.getAbsolutePath());
        }
    }

    // Obtiene el siguiente numero de venta
    private int getNextNumeroVenta() {
        int maxNum = 0;
        File currentDir = new File(BASE_DIR); 
        File[] files = currentDir.listFiles((dir, name) -> 
            name.startsWith(FILE_PREFIX + this.codProd + "-") && name.endsWith(FILE_SUFFIX)
        );
        if (files != null) {
            for (File file : files) {
                try {
                    String name = file.getName();
                    String numPart = name.substring(name.lastIndexOf('-') + 1, name.lastIndexOf(FILE_SUFFIX));
                    int num = Integer.parseInt(numPart);
                    if (num > maxNum) {
                        maxNum = num;
                    }
                } catch (Exception e) {
                    // Ignorar archivos mal formados para evitar fallos
                }
            }
        }
        return maxNum + 1;
    }

    
    
    // -------------------------------------

    @Override
    public void insback(Venta venta, IConexion iConexion) throws excepcionErrorPersistencia {
        int nuevoNum = getNextNumeroVenta(); 
        venta.setNumero(nuevoNum);
        escribirVentaEnArchivo(venta);
    }

    @Override
    public int largo(IConexion iConexion) throws excepcionErrorPersistencia {
        return getNextNumeroVenta() - 1; 
    }

    @Override
    public Venta Kesimo(int numVenta, IConexion iConexion) throws excepcionErrorPersistencia {
        return leerVentaDesdeArchivo(numVenta);
    }

    @Override
    public void borrarVenta(IConexion iConexion) throws excepcionErrorPersistencia {  
        File currentDir = new File(BASE_DIR);
        File[] files = currentDir.listFiles((dir, name) -> 
            name.startsWith(FILE_PREFIX + this.codProd + "-") && name.endsWith(FILE_SUFFIX)
        );

        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    throw new excepcionErrorPersistencia("Fallo al borrar el archivo de venta: " + file.getName());
                }
            }
        }
    }

    @Override
    public List<VOVentaTotal> listarVentas(IConexion iConexion) throws excepcionErrorPersistencia {
        List<VOVentaTotal> list = new LinkedList<>();
        int maxVenta = getNextNumeroVenta() - 1;

        for (int i = 1; i <= maxVenta; i++) {
            Venta venta = leerVentaDesdeArchivo(i);
            if (venta != null) {
                list.add(new VOVentaTotal(
                    venta.getUnidades(), 
                    venta.getCliente(), 
                    venta.getNumero(), 
                    this.codProd
                ));
            }
        }
        return list;
    }

    @Override
    public double totalRecaudado(IConexion iConexion) throws excepcionErrorPersistencia { 
        double totalUnidadesVendidas = 0;
        int maxVenta = getNextNumeroVenta() - 1;

        for (int i = 1; i <= maxVenta; i++) {
            Venta venta = leerVentaDesdeArchivo(i);
            if (venta != null) {
                totalUnidadesVendidas += venta.getUnidades();
            }
        }
        return totalUnidadesVendidas;
    }
}
