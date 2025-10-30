package logica.valueObjects;

import java.io.Serializable;

public class VOVentaTotal extends VOVenta implements Serializable{
	int numero;
	String codProd;
	
	public VOVentaTotal() {
	}
	

	public VOVentaTotal(int unidades, String cliente, int numero, String codProd) {
		super(unidades, cliente);		
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
