package logica.excepciones;

public class exceptionNoExisteProducto extends Exception{
	
	private static final long serialVersionUID = 1L;

	public exceptionNoExisteProducto(String message) {
        super(message); 
    }

}
