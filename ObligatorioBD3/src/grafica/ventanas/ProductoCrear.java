package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class ProductoCrear extends JInternalFrame {

	private static final long serialVersionUID = 1L;

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

	}

}
