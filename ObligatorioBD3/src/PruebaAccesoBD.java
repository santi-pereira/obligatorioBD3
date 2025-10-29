import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaAccesoBD {
	public static void main (String[] args)
	{
		//CreateDatabase c = new CreateDatabase();
		
//		c.create();

		try
		{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			String url = "jdbc:mysql://37.60.229.230:3306/Escuela";
			Connection con = DriverManager.getConnection(url, "ude", "Inforopinfo1@");

			String insert = "INSERT INTO Personas VALUES (?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(insert);
			pstmt.setInt(1, 120);
			pstmt.setString(2, "Ana");
			pstmt.setString(3, "Pérez");

			int cant = pstmt.executeUpdate();
			pstmt.close();
			System.out.print("Resultado de " + insert + ": ");
			System.out.println(cant + " filas afectadas");

			Statement stmt = con.createStatement();
			String query = "SELECT * FROM Personas";

			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Resultado de " + query);
			while (rs.next())
			{
				System.out.println("Nombre = " + rs.getString("nombre").trim());
			}
			rs.close();
			stmt.close();

			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		/* -- Personas (tanto maestras como alumnos están en esta tabla)
INSERT INTO Personas VALUES (100, 'María', 'Gómez');
INSERT INTO Personas VALUES (101, 'Ana', 'Pérez');
INSERT INTO Personas VALUES (102, 'Luis', 'Rodríguez');
INSERT INTO Personas VALUES (103, 'Carla', 'Fernández');
INSERT INTO Personas VALUES (104, 'Pedro', 'Martínez');
-- Maestras (referencia a Personas.cedula)
INSERT INTO Maestras VALUES (100, '1A');  -- María Gómez enseña 1A
INSERT INTO Maestras VALUES (101, '2B');  -- Ana Pérez enseña 2B
-- Alumnos (referencia a Personas.cedula y a la Maestra a cargo)
INSERT INTO Alumnos VALUES (102, 100); -- Luis Rodríguez, alumno de María
INSERT INTO Alumnos VALUES (103, 100); -- Carla Fernández, alumna de María
INSERT INTO Alumnos VALUES (104, 101); -- Pedro Martínez, alumno de Ana
		 
		 */
	}
}
