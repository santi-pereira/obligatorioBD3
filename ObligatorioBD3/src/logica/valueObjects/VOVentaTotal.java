package logica.valueObjects;

public class VOVentaTotal extends VOVenta{
	int numero;
	String codProd;
	
	public VOVentaTotal() {
		// TODO Auto-generated constructor stub
	}
	

	public VOVentaTotal(int unidades, String cliente, int numero, String codProd) {
		super(unidades, cliente);
		// TODO Auto-generated constructor stub
		
		this.numero = numero;
		this.codProd = codProd;
	}



	public int getNumero() {
		return numero;
	}
	public String getCodProd() {
		return codProd;
	}
	
	
}
