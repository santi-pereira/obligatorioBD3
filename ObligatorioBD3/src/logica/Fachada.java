package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.excepciones.exceptionNoExisteProducto;
import logica.excepciones.exceptionNoExisteVenta;
import logica.valueObjects.VOProducto;
import logica.valueObjects.VOVenta;
import persistencia.AccesoBD;

public class Fachada {

	private String url = "jdbc:mysql://37.60.229.230:3306/Ejercicio2";
	private String user = "ude";
	private String password = "Inforopinfo1@";

	public Fachada() {

	}

	public void altaProducto(VOProducto VoP) throws exceptionExisteCodigoProducto, excepcionErrorPersistencia {
		Connection con = null;

		try {

			AccesoBD acc = new AccesoBD();

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			con = DriverManager.getConnection(url, user, password);

			if (acc.existeProducto(con, VoP.getCodigo()))
				throw new exceptionExisteCodigoProducto("Ya existe un producto con el codigo indicado.");
			else
				acc.crearProducto(con, VoP.getCodigo(), VoP.getNombre(), VoP.getPrecio());

			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
	}

	public void bajaProducto(String codP) throws exceptionNoExisteProducto, excepcionErrorPersistencia {
		Connection con = null;

		try {

			AccesoBD acc = new AccesoBD();

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			con = DriverManager.getConnection(url, user, password);

			if (!acc.existeProducto(con, codP))
				throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
			else {
				acc.bajaProductoVentas(con, codP);
				acc.bajarVentasByCodProd(con, codP);
			}

		
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
	}

	/*
	 * El método registroVenta registra una nueva venta, chequeando que el código
	 * del producto correspondiente exista. Se asignará automáticamente a la nueva
	 * venta el número siguiente al de la última venta registrada hasta el momento
	 * para ese producto (si es la primera, tendrá el número 1).
	 */

	public void registroVenta(String codP, VOVenta voV) throws exceptionNoExisteProducto, excepcionErrorPersistencia {

		Connection con = null;

		try {

			AccesoBD acc = new AccesoBD();

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			con = DriverManager.getConnection(url, user, password);

			if (!acc.existeProducto(con, codP))
				throw new exceptionNoExisteProducto("No existe el producto con el codigo indicado.");
			else {
				int codigo = acc.getCantidadVentasByProducto(con, codP);
				acc.crearVenta(con, codigo, codP, voV.getUnidades(), voV.getCliente());
			}

			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}

	}
	
	/*
	 * El método datosVenta devuelve la cantidad de unidades y el cliente correspondiente a una venta,
		dados su número y el código del producto que le corresponde (chequeando que el código exista y
		tenga una venta con ese número).
	 * 
	 * */
	
	public VOVenta datosVenta(String codP, int numV) throws exceptionNoExisteVenta, excepcionErrorPersistencia
	{
		Connection con = null;
		VOVenta resp = null;

		try {

			AccesoBD acc = new AccesoBD();

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			con = DriverManager.getConnection(url, user, password);

			resp = acc.getDatosVenta(con, codP, numV);
			if (resp == null)
			{
				throw new exceptionNoExisteVenta("No existe una venta con el codigo y numero indicado");
			}
			

			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		
		return resp;
		
	}
	
	
	/**
	 * El método listadoProductos devuelve un listado de todos los productos, ordenado por código.
	 * 
	 * */
	
	public List<VOProducto> listadoProductos()throws excepcionErrorPersistencia
	{
		Connection con = null;
		List<VOProducto> resp = null;

		try {

			AccesoBD acc = new AccesoBD();

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			con = DriverManager.getConnection(url, user, password);

			resp = acc.listaProductos(con);
			
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		
		return resp;
	}
}
