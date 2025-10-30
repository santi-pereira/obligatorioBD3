package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import grafica.controladores.ControladorProductoCrear;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;

public class ProductoCrear extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	ControladorProductoCrear controlador;
	
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JFormattedTextField txtPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductoCrear frame = new ProductoCrear(true, true, true, true);
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
	public ProductoCrear(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Crear Producto", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		controlador = new ControladorProductoCrear(this);
		
		JLabel lblNewLabel = new JLabel("Código:");
		lblNewLabel.setBounds(6, 41, 61, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(6, 69, 61, 16);
		getContentPane().add(lblNewLabel_1);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(79, 36, 341, 26);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(79, 64, 341, 26);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPrecio = new JFormattedTextField(NumberFormat.getIntegerInstance());
		
		txtPrecio.setBounds(79, 92, 341, 26);
		getContentPane().add(txtPrecio);
		txtPrecio.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Precio:");
		lblNewLabel_2.setBounds(6, 97, 61, 16);
		getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {registrarProducto();}
		});
		btnNewButton.setBounds(303, 219, 117, 29);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(174, 219, 117, 29);
		getContentPane().add(btnNewButton_1);

	}
	
	public void registrarProducto()
	{
		
		if(!txtPrecio.getText().isEmpty())
		{
			String cod = txtCodigo.getText();
			String nombre = txtNombre.getText();
			
			int precio = Integer.parseInt(txtPrecio.getText());
			
			if(controlador.CrearProducto(cod, nombre, precio))
			{
				JOptionPane.showMessageDialog(this, "Se registro el producto de forma exitosa");
			}
			
		}else
		{
			mostrarError("Se tiene que indicar el precio del producto.");
		}
		
		
	}
	
	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error al registrar el producto: \n" + mensaje);
	}
	
	
}
