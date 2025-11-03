package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import grafica.controladores.ControladorVentasTotalRecaudado;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentasTotalRecaudado extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textCodigoProducto;
	
	ControladorVentasTotalRecaudado controlador;

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
		setBounds(100, 100, 450, 254);
		getContentPane().setLayout(null);
		
		controlador = new ControladorVentasTotalRecaudado(this);
		
		JLabel lblCodProducto = new JLabel("Código producto:");
		lblCodProducto.setBounds(48, 40, 107, 13);
		getContentPane().add(lblCodProducto);
		
		textCodigoProducto = new JTextField();
		textCodigoProducto.setBounds(180, 37, 180, 19);
		getContentPane().add(textCodigoProducto);
		textCodigoProducto.setColumns(10);
		
		JLabel lblTotalTexto = new JLabel("Total recaudado de ventas:");
		lblTotalTexto.setBounds(48, 124, 144, 13);
		getContentPane().add(lblTotalTexto);
		
		JLabel lblTotalVtasRecaudado = new JLabel("0");
		lblTotalVtasRecaudado.setBounds(205, 124, 45, 13);
		getContentPane().add(lblTotalVtasRecaudado);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(180, 83, 85, 21);
		getContentPane().add(btnCancelar);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codP = textCodigoProducto.getText();
				double total = 0;
					if (textCodigoProducto.getText().isEmpty()){
						mostrarError("Debe indicar el codigo del producto.");
					}
					else {
						total = controlador.totalRecaudadoPorVentas(codP);
						lblTotalVtasRecaudado.setText(String.valueOf(total));
					}
			}
		});
		btnAplicar.setBounds(275, 83, 85, 21);
		getContentPane().add(btnAplicar);

	}
	
	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error: \n" + mensaje);
	}
}
