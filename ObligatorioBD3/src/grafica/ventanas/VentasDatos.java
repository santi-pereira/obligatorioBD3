package grafica.ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;

import grafica.controladores.ControladorVentaRegistrar;
import grafica.controladores.ControladorVentasDatos;
import logica.valueObjects.VOVenta;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Panel;
import javax.swing.JPanel;
import java.awt.Label;

public class VentasDatos extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	ControladorVentasDatos controlador;
	
	private JTextField textField_codigo;
	private JTextField textField_numero;
	private Label output_codigo;
	private Label output_numero;
	private Label output_unidades;
	private Label output_cliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentasDatos frame = new VentasDatos(true, true, true, true);
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
	public VentasDatos(boolean isIcon,boolean isClosed,boolean iconable, boolean closable) {
		super("Datos de una venta", isIcon, isClosed, iconable, closable);
		setBounds(100, 100, 483, 400);
		getContentPane().setLayout(null);
		
		controlador = new ControladorVentasDatos(this);
		
		JLabel labelCodigo = new JLabel("Codigo Producto:");
		labelCodigo.setBounds(20, 39, 103, 14);
		getContentPane().add(labelCodigo);
		
		textField_codigo = new JTextField();
		textField_codigo.setColumns(10);
		textField_codigo.setBounds(121, 33, 287, 26);
		getContentPane().add(textField_codigo);
		
		JLabel labelNumero = new JLabel("Numero de venta:");
		labelNumero.setBounds(20, 80, 103, 14);
		getContentPane().add(labelNumero);
		
		JButton btnMostrar = new JButton("Mostrar datos");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDatos();
				
			}
		});
		btnMostrar.setBounds(260, 126, 117, 29);
		getContentPane().add(btnMostrar);
		
		textField_numero = new JTextField();
		textField_numero.setBounds(121, 77, 272, 20);
		getContentPane().add(textField_numero);
		textField_numero.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(143, 127, 107, 26);
		getContentPane().add(btnCancelar);
		
		JLabel labelOutput_codigo = new JLabel("Codigo Producto:");
		labelOutput_codigo.setBounds(20, 219, 103, 14);
		getContentPane().add(labelOutput_codigo);
		
		JLabel labelOutput_numero = new JLabel("Numero:");
		labelOutput_numero.setBounds(41, 244, 58, 14);
		getContentPane().add(labelOutput_numero);
		
		JLabel labelOutput_unidades = new JLabel("Unidades:");
		labelOutput_unidades.setBounds(41, 269, 58, 14);
		getContentPane().add(labelOutput_unidades);
		
		JLabel labelOutput_cliente = new JLabel("Cliente:");
		labelOutput_cliente.setBounds(41, 294, 58, 14);
		getContentPane().add(labelOutput_cliente);
		
		output_codigo = new Label("");
		output_codigo.setBounds(121, 219, 129, 22);
		getContentPane().add(output_codigo);
		
		output_numero = new Label("");
		output_numero.setBounds(107, 239, 143, 22);
		getContentPane().add(output_numero);
		
		output_unidades = new Label("");
		output_unidades.setBounds(105, 261, 145, 22);
		getContentPane().add(output_unidades);
		
		output_cliente = new Label("");
		output_cliente.setBounds(105, 289, 145, 22);
		getContentPane().add(output_cliente);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}
	
	
	public void mostrarDatos() {
		
		if (textField_codigo.getText().isEmpty()){
			mostrarError("Debe indicar el codigo del producto.");
		} else if (textField_numero.getText().isEmpty()) {
			mostrarError("Debe indicar el numero de venta.");
		}  else {
			String codigo = textField_codigo.getText();
			int numero = Integer.parseInt(textField_numero.getText());
			VOVenta venta = controlador.obtenerDatos(codigo, numero);
			if(venta != null) {
				output_codigo.setText(codigo);
				output_numero.setText(String.valueOf(numero));
				output_unidades.setText(String.valueOf(venta.getUnidades()));
				output_cliente.setText(venta.getCliente());
			}
		   }
	}
		
	
	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, "Error al registrar la venta: \n" + mensaje);
	}
}
