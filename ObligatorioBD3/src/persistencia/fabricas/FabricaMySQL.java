package persistencia.fabricas;

import logica.excepciones.excepcionErrorPersistencia;
import persistencia.daos.DAOProductos;
import persistencia.daos.DAOVentas;
import persistencia.daos.IDAOProductos;
import persistencia.daos.IDAOVentas;
import poolConexiones.IPoolConexiones;
import poolConexiones.PoolConexiones;

public class FabricaMySQL implements FabricaAbstracta {

	@Override
	public IDAOProductos crearDAOProductos() {
		return new DAOProductos();
	}

	@Override
	public IDAOVentas crearDAOVentas(String codProd) {
		return new DAOVentas(codProd);
	}

	@Override
	public IPoolConexiones crearPoolConexiones() throws excepcionErrorPersistencia {
		return new PoolConexiones();
	}
}
