package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductoListado extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductoListado frame = new ProductoListado(true, true, true, true);
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
	public ProductoListado(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Listado de Productos", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Cerrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(303, 219, 117, 29);
		getContentPane().add(btnNewButton);

	}
	
	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error al registrar el producto: \n" + mensaje);
	}
}
