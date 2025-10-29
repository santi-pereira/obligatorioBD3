import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearBD {
	
	public static void main (String[] args)
	{
		create();
		cargarDatos();
	}

	public static void cargarDatos()
	{
		try {
			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			
			String url = "jdbc:mysql://37.60.229.230:3306/";
	        String user = "ude";
	        String password = "Inforopinfo1@";

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			Connection con = DriverManager.getConnection(url, user, password);
			
			Statement stmt = con.createStatement();

			stmt.executeUpdate("USE Ejercicio2");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Ventas (número INT, codProd VARCHAR(45), unidades INT, cliente VARCHAR(45))");
			
			System.out.println("Se creo todo");

			stmt.close();
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void create() {
		try {

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			
			String url = "jdbc:mysql://37.60.229.230:3306";
	        String user = "ude";
	        String password = "Inforopinfo1@";

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			Connection con = DriverManager.getConnection(url, user, password);
			
			Statement stmCreateBD = con.createStatement();
			stmCreateBD.executeUpdate("Create Database IF NOT EXISTS Ejercicio2");
			stmCreateBD.close();

			Statement stmt = con.createStatement();

			//stmt.executeUpdate("CREATE DATABASE Escuela");
			stmt.executeUpdate("USE Ejercicio2");

			stmt.executeUpdate("CREATE TABLE  IF NOT EXISTS Productos(código VARCHAR(45), nombre VARCHAR(45), precio INT)");

			stmt.executeUpdate("CREATE TABLE  IF NOT EXISTS Ventas (número INT, codProd VARCHAR(45), unidades INT, cliente VARCHAR(45))");

			stmt.executeUpdate("create database ObligatorioBD3");
			stmt.executeUpdate("USE ObligatorioBD3");

			
			//Agregar la primary key
			stmt.executeUpdate("CREATE TABLE Productos(código VARCHAR(45), nombre VARCHAR(45), precio INT)");
			stmt.executeUpdate("CREATE TABLE Ventas (número INT, codProd VARCHAR(45), unidades INT, cliente VARCHAR(45))");

			System.out.println("Se creo todo");

			stmt.close();
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
