package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class VentasListado extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentasListado frame = new VentasListado(true, true, true, true);
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
	public VentasListado(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Listado de Ventas", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 450, 300);

	}

}
