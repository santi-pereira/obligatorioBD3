package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import grafica.controladores.ControladorProductoMasVendido;
import logica.valueObjects.VOProdVentas;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductoMasVendido extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	ControladorProductoMasVendido controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductoMasVendido frame = new ProductoMasVendido(true, true, true, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductoMasVendido(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Producto más vendido", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		controlador = new ControladorProductoMasVendido(this);
		
		JLabel lblNewLabel_2 = new JLabel("Precio:");
		lblNewLabel_2.setBounds(6, 96, 123, 16);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("Código:");
		lblNewLabel.setBounds(6, 40, 61, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(6, 68, 61, 16);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Unidades Vendidas:");
		lblNewLabel_3.setBounds(6, 124, 123, 16);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblCodigo = new JLabel("...");
		lblCodigo.setBounds(155, 40, 265, 16);
		getContentPane().add(lblCodigo);
		
		JLabel lblNombre = new JLabel("...");
		lblNombre.setBounds(155, 68, 265, 16);
		getContentPane().add(lblNombre);
		
		JLabel lblPrecio = new JLabel("0");
		lblPrecio.setBounds(155, 96, 265, 16);
		getContentPane().add(lblPrecio);
		
		JLabel lblUnidadesVendidas = new JLabel("0");
		lblUnidadesVendidas.setBounds(155, 124, 265, 16);
		getContentPane().add(lblUnidadesVendidas);
		
		JButton btnNewButton = new JButton("Cerrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(303, 219, 117, 29);
		getContentPane().add(btnNewButton);
		
		VOProdVentas dato = controlador.obtenerProductos();
		
		if(dato != null)
		{
			lblCodigo.setText(dato.getCodigo());
			lblPrecio.setText(String.valueOf(dato.getPrecio()));
			lblNombre.setText(dato.getNombre());
			lblUnidadesVendidas.setText(String.valueOf(dato.getUnidadesVendidas()));
		}

	}
	
	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error: \n" + mensaje);
	}
}
