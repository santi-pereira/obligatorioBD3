package persistencia.fabricas;

import logica.excepciones.excepcionErrorPersistencia;
import persistencia.daos.IDAOProductos;
import persistencia.daos.IDAOVentas;
import poolConexiones.IPoolConexiones;

public interface FabricaAbstracta {
	IDAOProductos crearDAOProductos();
	IDAOVentas crearDAOVentas(String codigo);
	IPoolConexiones crearPoolConexiones() throws excepcionErrorPersistencia;
}
