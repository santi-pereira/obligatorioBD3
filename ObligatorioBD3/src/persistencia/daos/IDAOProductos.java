package persistencia.daos;

import java.util.List;

import logica.Producto;
import logica.excepciones.excepcionErrorPersistencia;
import logica.valueObjects.VOProdVentas;
import logica.valueObjects.VOProducto;
import poolConexiones.IConexion;

public interface IDAOProductos {
	public boolean member(String codP, IConexion iConexion) throws excepcionErrorPersistencia;
	public void insert(Producto producto, IConexion iConexion) throws excepcionErrorPersistencia;
	public Producto find(String codP, IConexion iConexion) throws excepcionErrorPersistencia;
	public void delete(String codP, IConexion iConexion) throws excepcionErrorPersistencia;
	public boolean esVacio(IConexion iConexion) throws excepcionErrorPersistencia;
	public List<VOProducto> listarProductos(IConexion iConexion) throws excepcionErrorPersistencia;
	public VOProdVentas productoMasVendido(IConexion iConexion) throws excepcionErrorPersistencia;	
}
