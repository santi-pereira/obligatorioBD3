
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {

	public void create() {
		try {
			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			
			String url = "jdbc:mysql://37.60.229.230:3306/Escuela";
	        String user = "ude";
	        String password = "Inforopinfo1@";

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			Connection con = DriverManager.getConnection(url, user, password);

			Statement stmt = con.createStatement();

			//stmt.executeUpdate("CREATE DATABASE Escuela");
			stmt.executeUpdate("USE Escuela");

			stmt.executeUpdate("CREATE TABLE Personas(" + "cedula INT PRIMARY KEY," + "nombre VARCHAR(45),"
					+ "apellido VARCHAR(45))");

			stmt.executeUpdate("CREATE TABLE Maestras(" + "cedula INT PRIMARY KEY," + "grupo VARCHAR(45),"
					+ "FOREIGN KEY (cedula) REFERENCES Personas(cedula))");

			stmt.executeUpdate("CREATE TABLE Alumnos (" + "cedula INT PRIMARY KEY," + "cedulaMaestra INT,"
					+ "FOREIGN KEY (cedula) REFERENCES Personas(cedula),"
					+ "FOREIGN KEY (cedulaMaestra) REFERENCES Maestras(cedula))");

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
