package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorVentasListado;
import logica.valueObjects.VOVentaTotal;

public class VentasListado extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textCodProd;
	private JTable table;
	
	private ControladorVentasListado controlador;

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
		getContentPane().setLayout(null);
		
		controlador = new ControladorVentasListado(this);
		
		JLabel lblCodProd = new JLabel("Código de producto:");
		lblCodProd.setBounds(54, 32, 110, 13);
		getContentPane().add(lblCodProd);
		
		textCodProd = new JTextField();
		textCodProd.setBounds(191, 29, 198, 19);
		getContentPane().add(textCodProd);
		textCodProd.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(191, 80, 85, 21);
		getContentPane().add(btnCancelar);
		
		JButton btnListarVentas = new JButton("Listar Ventas");
		btnListarVentas.setBounds(293, 80, 96, 21);
		btnListarVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 DefaultTableModel model = (DefaultTableModel) table.getModel();

			        model.setRowCount(0);

			        String codigo = textCodProd.getText();
			        List<VOVentaTotal> lista = controlador.listadoVentas(codigo);

			        if (lista != null) {
			            for (VOVentaTotal voVenta : lista) {
			                Object[] rowData = { voVenta.getUnidades(), voVenta.getCliente() };
			                model.addRow(rowData);
			            }
			        }
		}});
		
		getContentPane().add(btnListarVentas);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 126, 414, 134);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Unidades", "Cliente"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(1).setResizable(false);
		scrollPane.setColumnHeaderView(table);

	
	}

	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error al registrar el producto: \n" + mensaje);
	}
}
