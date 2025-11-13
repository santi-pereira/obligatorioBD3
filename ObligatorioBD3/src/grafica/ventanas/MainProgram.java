package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;

public class MainProgram {

	private JFrame frame;
    private JDesktopPane desktopPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainProgram window = new MainProgram();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainProgram() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 840, 603);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Productos");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Nuevo");
		mntmNewMenuItem.addActionListener(e -> abrirVentanaInterna(new ProductoCrear(true, true, true, true)));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Eliminar");
		mntmNewMenuItem_1.addActionListener(e -> abrirVentanaInterna(new ProductoEliminar(true, true, true, true)));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Listado");
		mntmNewMenuItem_2.addActionListener(e -> abrirVentanaInterna(new ProductoListado(true, true, true, true)));
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Producto más vendido");
		mntmNewMenuItem_3.addActionListener(e -> abrirVentanaInterna(new ProductoMasVendido(true, true, true, true)));
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("Ventas");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Registrar");
		mntmNewMenuItem_4.addActionListener(e -> abrirVentanaInterna(new VentaRegistrar(true, true, true, true)));
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Datos de una venta");
		mntmNewMenuItem_5.addActionListener(e -> abrirVentanaInterna(new VentasDatos(true, true, true, true)));
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Listado");
		mntmNewMenuItem_6.addActionListener(e -> abrirVentanaInterna(new VentasListado(true, true, true, true)));
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Total Recaudado");
		mntmNewMenuItem_7.addActionListener(e -> abrirVentanaInterna(new VentasTotalRecaudado(true, true, true, true)));
		
		mnNewMenu_1.add(mntmNewMenuItem_7);
		
		desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		
	}
	
	
	private void abrirVentanaInterna(JInternalFrame frame) {
		
		
        //frame.setSize(300, 200);
        frame.setVisible(true);
        
        desktopPane.add(frame);
        
        //frame.pack();
        
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

}
