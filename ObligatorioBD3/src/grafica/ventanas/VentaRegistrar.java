package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class VentaRegistrar extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentaRegistrar frame = new VentaRegistrar(true, true, true, true);
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
	public VentaRegistrar(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Registrar Venta", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 450, 300);

	}

}
