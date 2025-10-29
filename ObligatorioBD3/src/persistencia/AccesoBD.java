package persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.valueObjects.VOProducto;
import logica.valueObjects.VOVenta;
import logica.valueObjects.VOVentaTotal;
import persistencia.consultas.Consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccesoBD {

	public boolean existeProducto(Connection conn, String codigo) throws SQLException
	{
		boolean existe = false;
		
		Consultas consultas = new Consultas();
		
		String query = consultas.obtenerProducto();
		PreparedStatement prs = conn.prepareStatement(query);
		
		prs.setString(1, codigo);
		
		if(prs.executeQuery().next())
			existe = true;
		
		prs.close();
		
		return existe;
	}
	
	public void crearProducto(Connection conn, String codigo, String nombre, int precio) throws SQLException
	{
		Consultas consultas = new Consultas();
		
		String query = consultas.insertarProducto();
		PreparedStatement prs = conn.prepareStatement(query);
		
		prs.setString(1, codigo);
		prs.setString(2, nombre);
		prs.setInt(3, precio);
		
		prs.execute();
		
		prs.close();
			
	}
	
	public void bajaProductoVentas (Connection conn, String codigo)throws SQLException
	{
		Consultas consultas = new Consultas();
		
		String query = consultas.bajaProducto();
		PreparedStatement prs = conn.prepareStatement(query);
		
		prs.setString(1, codigo);
		prs.execute();
		
		prs.close();
	}
	
	public void bajarVentasByCodProd(Connection conn, String codigo)throws SQLException
	{
		Consultas consultas = new Consultas();
		
		String query = consultas.bajarVentasByCodigoProducto();
		PreparedStatement prs = conn.prepareStatement(query);
		
		prs.setString(1, codigo);
		prs.execute();
		
		prs.close();
	}
	
	public int getCantidadVentasByProducto (Connection conn, String codigo) throws SQLException
	{
		int cantidad = 0;
		
		Consultas consultas = new Consultas();
		
		String query = consultas.cantidadVentasByCodigoProducto();
		PreparedStatement prs = conn.prepareStatement(query);
		
		prs.setString(1, codigo);
		
		ResultSet rs = prs.executeQuery();
		if(rs.next())
			cantidad = rs.getInt("cantidad");
		
		prs.close();
		
		return cantidad;
	}
	
	public void crearVenta(Connection conn, int numero, String codProd, int unidades, String Cliente) throws SQLException
	{
		Consultas consultas = new Consultas();
		
		String query = consultas.insertarVenta();
		PreparedStatement prs = conn.prepareStatement(query);

		prs.setInt(1, numero);
		prs.setString(2, codProd);
		prs.setInt(3, unidades);
		prs.setString(4, Cliente);
		
		prs.execute();
		
		prs.close();
			
	}
	
	public VOVenta getDatosVenta (Connection conn, String codigo, int numero) throws SQLException
	{
		VOVenta v = null;
		
		Consultas consultas = new Consultas();
		
		String query = consultas.obtenerDatosVenta();
		PreparedStatement prs = conn.prepareStatement(query);
		
		prs.setString(1, codigo);
		prs.setInt(2, numero);
		
		ResultSet rs = prs.executeQuery();
		if(rs.next())
		{
			int un = rs.getInt("unidades");
			String cantidad = rs.getString("cantidad");
			v = new VOVenta(un, cantidad);
		}
		
		prs.close();
		
		return v;
	}
	
	public List<VOProducto> listaProductos (Connection conn) throws SQLException
	{
		List<VOProducto> v = new ArrayList<VOProducto>();
		
		Consultas consultas = new Consultas();
		
		String query = consultas.obtenerProductos();
		PreparedStatement prs = conn.prepareStatement(query);
		

		ResultSet rs = prs.executeQuery();
		
		while(rs.next())
		{ // codigo, precio, nombre
			
			String cod = rs.getString("codigo");
			int precio = rs.getInt("precio");
			String nombre = rs.getString("nombre");
			
			
			v.add(new VOProducto(cod, nombre, precio));
		}
		
		prs.close();
		
		return v;
	}
	
	public List<VOVentaTotal> listaVentas (Connection conn, String codP) throws SQLException
	{
		List<VOVentaTotal> v = new ArrayList<VOVentaTotal>();
		
		Consultas consultas = new Consultas();
		
		String query = consultas.obtenerVentasByCodigoP();
		PreparedStatement prs = conn.prepareStatement(query);
		
		prs.setString(1, codP);

		ResultSet rs = prs.executeQuery();
		
		while(rs.next())
		{ // codigo, precio, nombre
			
			int numero = rs.getInt("numero");
			String cod = rs.getString("codProd");
			int unidades = rs.getInt("unidades");
			String cliente = rs.getString("Cliente");
			
			
			v.add(new VOVentaTotal(unidades, cliente, numero, cod));
		}
		
		prs.close();
		
		return v;
	}
}
