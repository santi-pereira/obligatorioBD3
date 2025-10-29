package logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import logica.excepciones.excepcionErrorPersistencia;
import logica.excepciones.exceptionExisteCodigoProducto;
import logica.excepciones.exceptionNoExisteProducto;
import logica.excepciones.exceptionNoExisteVenta;
import logica.valueObjects.VOProducto;
import logica.valueObjects.VOVenta;
import logica.valueObjects.VOVentaTotal;
import persistencia.AccesoBD;
import persistencia.daos.DAOProductos;

public class Fachada {

	private String driver;
	private String url;
	private String user;
	private String password;

	public Fachada() throws excepcionErrorPersistencia {
		Properties props = new Properties();
		
		try (InputStream input = Fachada.class.getClassLoader().getResourceAsStream("config.properties");) {
			props.load(input);
		} catch (FileNotFoundException exception) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		} catch (IOException exception) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}

		this.driver = props.getProperty("driver");
		this.url = props.getProperty("url");
		this.user = props.getProperty("user");
		this.password = props.getProperty("password");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			throw new excepcionErrorPersistencia("Ocurrio un error de persistencia.");
		}
	}

	public void altaProducto(VOProducto VoP) throws exceptionExisteCodigoProducto, excepcionErrorPersistencia {
		DAOProductos daoProductos = new DAOProductos();

		try {
			if (daoProductos.member(VoP.getCodigo()))
				throw new exceptionExisteCodigoProducto("Ya existe un producto con el codigo indicado.");
			else {
				Producto producto = new Producto(VoP.getCodigo(), VoP.getNombre(), VoP.getPrecio());
				daoProductos.insert(producto);
			}
		} catch (excepcionErrorPersistencia e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
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
			con = DriverManager.getConnection(this.url, this.user, this.password);

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
				throw new excepcionErrorPersistencia("Error con la persistencia");
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
			con = DriverManager.getConnection(this.url, this.user, this.password);

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
				throw new excepcionErrorPersistencia("Error con la persistencia");
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
			con = DriverManager.getConnection(this.url, this.user, this.password);

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
				throw new excepcionErrorPersistencia("Error con la persistencia");
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
			con = DriverManager.getConnection(this.url, this.user, this.password);

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
				throw new excepcionErrorPersistencia("Error con la persistencia");
			}
		}
		
		return resp;
	}

	public List<VOVentaTotal> listadoVentas(String codProd) throws excepcionErrorPersistencia {
		List<VOVentaTotal> list = null;
		Connection con = null;
		try {

			AccesoBD acc = new AccesoBD();

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			con = DriverManager.getConnection(this.url, this.user, this.password);

			list = acc.listaVentas(con, codProd);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new excepcionErrorPersistencia("Error con la persistencia");
			}
		}
		
		return list;
	}
	
	public VOProducto productoMasUnidadesVendidas() throws excepcionErrorPersistencia {
		VOProducto voProducto = null;Connection con = null;
		try {

			AccesoBD acc = new AccesoBD();

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			con = DriverManager.getConnection(this.url, this.user, this.password);

			voProducto = acc.productoMasUnidadesVendidas(con);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new excepcionErrorPersistencia("Error con la persistencia");
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new excepcionErrorPersistencia("Error con la persistencia");
			}
		}
		
		return voProducto;
	}
}
