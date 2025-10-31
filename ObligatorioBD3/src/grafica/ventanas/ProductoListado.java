package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import grafica.controladores.ControladorProductosLista;
import logica.valueObjects.VOProducto;

public class ProductoListado extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	
	private ControladorProductosLista controlador;

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
		setLayout(new BorderLayout());
		

		
		controlador = new ControladorProductosLista(this);
		
		JButton btnNewButton = new JButton("Cerrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(303, 219, 117, 29);
		getContentPane().add(btnNewButton);
		
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");

		table = new JTable(modelo);
		
		table.setBounds(6, 6, 414, 201);
		table.setShowGrid(true); 
		table.setGridColor(Color.GRAY); 
		table.getTableHeader().setReorderingAllowed(true);
		
		
		JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);
		
		
		List<VOProducto> lista = controlador.obtenerProductos();
		
		for (VOProducto voProducto : lista) {
			modelo.addRow(new Object[] {voProducto.getCodigo(), voProducto.getNombre(), voProducto.getPrecio()});
		}
	}
	
	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error al registrar el producto: \n" + mensaje);
	}
}
