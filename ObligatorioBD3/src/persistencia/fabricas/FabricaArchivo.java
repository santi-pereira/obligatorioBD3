package persistencia.fabricas;

import logica.excepciones.excepcionErrorPersistencia;
import persistencia.daos.DAOProductosArchivo;
import persistencia.daos.DAOVentasArchivo;
import persistencia.daos.IDAOProductos;
import persistencia.daos.IDAOVentas;
import poolConexiones.IPoolConexiones;
import poolConexiones.PoolConexionesArchivo;

public class FabricaArchivo implements FabricaAbstracta {

	@Override
	public IDAOProductos crearDAOProductos() {
		return new DAOProductosArchivo();
	}

	@Override
	public IDAOVentas crearDAOVentas(String codigo) {
		return new DAOVentasArchivo(codigo);
	}

	@Override
	public IPoolConexiones crearPoolConexiones() throws excepcionErrorPersistencia {
		return new PoolConexionesArchivo();
	}

}
