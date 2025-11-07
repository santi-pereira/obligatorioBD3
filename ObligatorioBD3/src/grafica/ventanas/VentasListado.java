package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
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
		lblCodProd.setBounds(6, 33, 140, 13);
		getContentPane().add(lblCodProd);
		
		textCodProd = new JTextField();
		textCodProd.setBounds(143, 29, 164, 19);
		getContentPane().add(textCodProd);
		textCodProd.setColumns(10);
		
		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.setBounds(335, 227, 85, 21);
		getContentPane().add(btnCancelar);
		
		JButton btnListarVentas = new JButton("Listar Ventas");
		btnListarVentas.setBounds(324, 30, 96, 21);
		btnListarVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 DefaultTableModel model = (DefaultTableModel) table.getModel();

			        model.setRowCount(0);

			        String codigo = textCodProd.getText();
			        List<VOVentaTotal> lista = controlador.listadoVentas(codigo);

			        if (lista != null) {
			            for (VOVentaTotal voVenta : lista) {
			                Object[] rowData = { String.valueOf(voVenta.getNumero()), voVenta.getUnidades(), voVenta.getCliente() };
			                model.addRow(rowData);
			            }
			        }
		}});
		
		getContentPane().add(btnListarVentas);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Numero","Unidades", "Cliente"
			}
		));
		
		table.getColumnModel().getColumn(1).setResizable(false);
		table.setShowGrid(true); 
		table.setGridColor(Color.GRAY); 
		table.getTableHeader().setReorderingAllowed(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(6, 56, 414, 159);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		//scrollPane.setColumnHeaderView(table);

	
	}

	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error al listar ventas: \n" + mensaje);
	}
}
