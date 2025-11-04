package persistencia.daos;

import java.util.List;

import logica.Venta;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOVentaTotal;
import poolConexiones.IConexion;

public interface IDAOVentas {
	public void insback(Venta venta, IConexion iConexion) throws excepcionErrorPersistencia;
	public int largo(IConexion iConexion) throws excepcionErrorPersistencia;
	public Venta Kesimo(int numVenta, IConexion iConexion) throws excepcionErrorPersistencia;
	public void borrarVenta(IConexion iConexion) throws excepcionErrorPersistencia;
	public List<VOVentaTotal> listarVentas(IConexion iConexion) throws excepcionErrorPersistencia;
	public double totalRecaudado(IConexion iConexion) throws excepcionErrorPersistencia;
}
