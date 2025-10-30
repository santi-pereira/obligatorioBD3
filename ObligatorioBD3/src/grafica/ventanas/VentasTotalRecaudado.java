package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class VentasTotalRecaudado extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentasTotalRecaudado frame = new VentasTotalRecaudado(true, true, true, true);
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
	public VentasTotalRecaudado(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Total Recaudado de las Ventas", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 450, 300);

	}

}
