package persistencia.fabricas;

import logica.excepciones.excepcionErrorPersistencia;
import persistencia.daos.DAOProductosArchivo;
import persistencia.daos.IDAOProductos;
import persistencia.daos.IDAOVentas;
import poolConexiones.IPoolConexiones;

public class FabricaArchivo implements FabricaAbstracta {

	@Override
	public IDAOProductos crearDAOProductos() {
		return new DAOProductosArchivo();
	}

	@Override
	public IDAOVentas crearDAOVentas(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPoolConexiones crearPoolConexiones() throws excepcionErrorPersistencia {
		// TODO Auto-generated method stub
		return null;
	}

}
