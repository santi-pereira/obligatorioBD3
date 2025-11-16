package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import grafica.controladores.ControladorProductoEliminar;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductoEliminar extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	ControladorProductoEliminar controlador;
		
	private JTextField textFieldCodigoProducto;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductoEliminar frame = new ProductoEliminar(true, true, true, true);
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
	public ProductoEliminar(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Eliminar Producto", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 385, 143);
		getContentPane().setLayout(null);
		 controlador = new ControladorProductoEliminar(this);
		
		JLabel lblNewLabel = new JLabel("Código producto:");
		lblNewLabel.setBounds(21, 25, 91, 13);
		getContentPane().add(lblNewLabel);
		
		textFieldCodigoProducto = new JTextField();
		textFieldCodigoProducto.setBounds(122, 22, 157, 19);
		getContentPane().add(textFieldCodigoProducto);
		textFieldCodigoProducto.setColumns(10);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {eliminarProducto();}
		});
		btnEliminar.setBounds(194, 64, 85, 21);
		getContentPane().add(btnEliminar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(99, 64, 85, 21);
		getContentPane().add(btnCancelar);

	}
	
	public void eliminarProducto()
	{
		
		if(!textFieldCodigoProducto.getText().isEmpty())
		{
			String cod = textFieldCodigoProducto.getText();
			
		
				if(controlador.EliminarProducto(cod))
				{
					int opcion = JOptionPane.showConfirmDialog(
					        this,
					        "Se borró el producto de forma exitosa",
					        "Información",
					        JOptionPane.DEFAULT_OPTION,
					        JOptionPane.INFORMATION_MESSAGE
					);
					if (opcion == JOptionPane.OK_OPTION) {
					    limpiarCampos();
					}
				}
			
			}else
			{
				mostrarError("Se tiene que indicar el código del producto.");
			}
		
		
	}
	
	private void limpiarCampos() {
		textFieldCodigoProducto.setText("");
	    
		textFieldCodigoProducto.requestFocus(); // vuelve a enfocar el primer campo
	}

	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error al borrar el producto: \n" + mensaje);
	}
	
}
