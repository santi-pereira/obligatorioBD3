package persistencia.daos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import logica.Venta;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOVenta;
import logica.valueObjects.VOVentaTotal;
import poolConexiones.IConexion;

public class DAOVentasArchivo implements IDAOVentas {

	private String codProd;

	private String getRuta() {
		//devuelve la ruta de la carpeta
		return "./";
	}

	public DAOVentasArchivo(String codProd) {
		this.codProd = codProd;
	}

	@Override
	public void insback(Venta venta, IConexion iConexion) throws excepcionErrorPersistencia{
		/*
		 * try (FileWriter writer = new FileWriter(getRuta() + "PROD-" +
		 * producto.getCodigo() + ".txt")) { writer.write(producto.getCodigo());
		 * writer.write(producto.getNombre()); writer.write(producto.getPrecio()); }
		 * catch (IOException e) { throw new
		 * excepcionErrorPersistencia("Error en la persistencia."); }
		 */
	}
	
	@Override
	public int largo(IConexion iConexion) throws excepcionErrorPersistencia {
		return 0;
	}
	
	@Override
	public Venta Kesimo(int numVenta, IConexion iConexion) throws excepcionErrorPersistencia {
		return null;
	}

	@Override
	public void borrarVenta(IConexion iConexion) throws excepcionErrorPersistencia {
		
	}

	@Override
	public List<VOVentaTotal> listarVentas(IConexion iConexion) throws excepcionErrorPersistencia{
		return null;
	}

	@Override
	public double totalRecaudado(IConexion iConexion) throws excepcionErrorPersistencia {
		return 0;
	}
}
