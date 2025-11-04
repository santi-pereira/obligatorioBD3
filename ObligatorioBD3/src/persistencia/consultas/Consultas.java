package persistencia.consultas;

public class Consultas {
	public Consultas()
	{
		
	}

	public String insertarProducto()
	{
		return "INSERT into Productos (codigo, nombre, precio) VALUES (?, ?, ?)";
	}

	public String obtenerProducto()
	{
		return "SELECT * FROM Productos WHERE codigo = ?";
	}

	public String bajaProducto()
	{
		return "DELETE FROM Productos WHERE codigo = ?";		
	}

	public String insertarVenta()
	{
		//Ventas (número INT, codProd VARCHAR(45), unidades INT, cliente VARCHAR(45))
		return "INSERT into Ventas (numero, codProd, unidades, cliente) VALUES (?, ?, ?, ?)";		
	}

	public String bajarVentasByCodigoProducto()
	{
		return "DELETE FROM Ventas WHERE codProd = ?";		
	}

	public String cantidadVentasByCodigoProducto()
	{
		return "SELECT COUNT(*) AS cantidad FROM Ventas WHERE codProd = ?";
	}

	public String obtenerDatosVenta()
	{
		return "SELECT numero, codProd, unidades, cliente FROM Ventas WHERE codProd = ? AND numero = ?";
	}

	public String obtenerProductos()
	{
		return "SELECT * FROM Productos ORDER BY codigo";	
	}

	public String obtenerVentasByCodigoP()
	{
		return "SELECT * FROM Ventas WHERE codProd = ? ORDER BY codProd";	
	}
	
	public String obtenerTotalRecaudadoVentas()
	{
		return "SELECT SUM(unidades) as total FROM Ventas v WHERE codProd = ? ;";
	}
	
	public String obtenerTotalProductos()
	{
		return "SELECT COUNT(*) as total FROM Productos p;";
	}
	
	public String obtenerProductoMasUnidadesVendidas() {
		return "SELECT p.codigo, p.nombre, p.precio, SUM(v.unidades) AS total_unidades "
				+ "FROM Productos p "
				+ "JOIN Ventas v ON p.codigo = v.codProd "
				+ "GROUP BY p.codigo, p.nombre, p.precio "
				+ "ORDER BY total_unidades DESC "
				+ "LIMIT 1 ;";
	}
}
