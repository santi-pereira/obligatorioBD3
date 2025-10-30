package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.excepciones.exceptionNoExisteProducto;
import logica.excepciones.exceptionNoExisteVenta;
import logica.valueObjects.VOProducto;
import logica.valueObjects.VOVenta;
import logica.valueObjects.VOVentaTotal;

public interface IFachada extends Remote{

	public void altaProducto(VOProducto VoP) throws RemoteException, exceptionExisteCodigoProducto, excepcionErrorPersistencia;
	public void bajaProducto(String codP) throws RemoteException, exceptionNoExisteProducto, excepcionErrorPersistencia;
	public void registroVenta(String codP, VOVenta voV) throws RemoteException, exceptionNoExisteProducto, excepcionErrorPersistencia;
	public VOVenta datosVenta(String codP, int numV) throws RemoteException, exceptionNoExisteVenta, excepcionErrorPersistencia, exceptionNoExisteProducto;
	public List<VOProducto> listadoProductos() throws RemoteException, excepcionErrorPersistencia;
	public List<VOVentaTotal> listadoVentas(String codProd) throws RemoteException, excepcionErrorPersistencia, exceptionNoExisteProducto;
	public VOProducto productoMasUnidadesVendidas() throws RemoteException, excepcionErrorPersistencia, exceptionNoExisteProducto;
	public double totalRecaudadoPorVentas(String codProd) throws RemoteException, exceptionNoExisteProducto, excepcionErrorPersistencia;

}
