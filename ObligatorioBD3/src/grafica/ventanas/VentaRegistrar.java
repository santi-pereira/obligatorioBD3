package grafica.ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import grafica.controladores.ControladorVentaRegistrar;
import javax.swing.JButton;

public class VentaRegistrar extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	ControladorVentaRegistrar controlador;
	
	private JTextField textField_codigo;
	private JTextField textField_unidades;
	private JTextField textField_cliente;

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
		setBounds(100, 100, 463, 246);
		getContentPane().setLayout(null);
		
		controlador = new ControladorVentaRegistrar(this);
		
		JLabel labelCodigo = new JLabel("Codigo Producto:");
		labelCodigo.setBounds(10, 38, 117, 14);
		getContentPane().add(labelCodigo);
		
		JLabel labelUnidades = new JLabel("Unidades:");
		labelUnidades.setBounds(10, 64, 77, 16);
		getContentPane().add(labelUnidades);
		
		JLabel labelCliente = new JLabel("Cliente:");
		labelCliente.setBounds(10, 92, 65, 16);
		getContentPane().add(labelCliente);
		
		textField_codigo = new JTextField();
		textField_codigo.setColumns(10);
		textField_codigo.setBounds(146, 32, 287, 26);
		getContentPane().add(textField_codigo);
		
		textField_unidades = new JTextField();
		textField_unidades.setColumns(10);
		textField_unidades.setBounds(146, 59, 287, 26);
		getContentPane().add(textField_unidades);
		
		textField_unidades.addKeyListener(new java.awt.event.KeyAdapter() {
		    @Override
		    public void keyTyped(java.awt.event.KeyEvent e) {
		        char c = e.getKeyChar();
		        if (!Character.isDigit(c) && c != '\b') {
		            e.consume();
		        }
		    }
		});
		
		textField_cliente = new JTextField();
		textField_cliente.setColumns(10);
		textField_cliente.setBounds(146, 87, 287, 26);
		getContentPane().add(textField_cliente);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(187, 170, 117, 29);
		getContentPane().add(btnCancelar);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {registrarVenta();}
		});
		btnRegistrar.setBounds(316, 170, 117, 29);
		getContentPane().add(btnRegistrar);

	}
	
	
	public void registrarVenta()
	{
		
		if (textField_codigo.getText().isEmpty()){
			mostrarError("Debe indicar el codigo del producto.");
		} else if (textField_unidades.getText().isEmpty()) {
			mostrarError("Debe indicar la cantidad de unidades.");
		} else if(textField_cliente.getText().isEmpty()) {
			mostrarError("Debe indicar el cliente.");
		} else {
			String codigo = textField_codigo.getText();
			int unidades = Integer.parseInt(textField_unidades.getText());
			String cliente = textField_cliente.getText();
			
			if(controlador.CrearVenta(codigo, unidades, cliente)) {
				JOptionPane.showMessageDialog(this, "Se registro la venta de forma exitosa");
			}
		}
	}
		
	
	public void mostrarError(String mensaje)
	{
		JOptionPane.showMessageDialog(this, "Error al registrar la venta: \n" + mensaje);
	}
	
}
