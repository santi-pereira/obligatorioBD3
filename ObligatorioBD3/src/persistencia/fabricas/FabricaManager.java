package persistencia.fabricas;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import logica.excepciones.excepcionErrorPersistencia;

public class FabricaManager {
	private static FabricaAbstracta fabrica;

    // Carga la fábrica que se especifique en el archivo config.properties
    public static FabricaAbstracta getFabrica() throws excepcionErrorPersistencia {
    	if (fabrica == null) {
            try {

                Properties p = new Properties();
                p.load(new FileInputStream("././resources/config.properties"));
                String claseFabrica = p.getProperty("fabrica");
                fabrica = (FabricaAbstracta) Class.forName(claseFabrica).getDeclaredConstructor().newInstance();
            } catch (IOException e) {
                throw new excepcionErrorPersistencia("Error al leer el archivo de configuración de persistencia.");
            } catch (ClassNotFoundException e) {
                throw new excepcionErrorPersistencia("No se encontró la clase de la fábrica especificada.");
            } catch (ReflectiveOperationException e) {
                throw new excepcionErrorPersistencia("Error al instanciar la fábrica de persistencia.");
            }
        }

        return fabrica;
    }
}
