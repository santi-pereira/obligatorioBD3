package logicaPresentacion.valueObjects;

public class VOProdVentas extends VOProducto{
	
	int unidadesVendidas;
	
	

	

	public VOProdVentas(String codigo, String nombre, int precio, int unidadesVendidas) {
		super(codigo, nombre, precio);
		this.unidadesVendidas = unidadesVendidas;
		// TODO Auto-generated constructor stub
	}





	public int getUnidadesVendidas() {
		return unidadesVendidas;
	}
	
	

}
