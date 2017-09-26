package vista;

import comun.BBDD;
import controlador.Controlador;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import excepciones.NoExisteCliente;
import clientes.Cliente;

public class VistaMostrarCliente implements Vista, Serializable{
	
	private static final long serialVersionUID = 1879854609961155917L;
	private BBDD baseDatos;
	@SuppressWarnings("unused")
	private Controlador controlador;
	private JDialog ventana, emergente;
	private Container contenedor;
	private JPanel panel;
	private JTextField NIF;
	private JButton	comprobar, cancelar;
	
	public VistaMostrarCliente(JFrame padre) {
		ventana = new JDialog(padre, "Mostrar cliente", true);
		contenedor = ventana.getContentPane();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelComprobarNIF());
		panel.add(panelAceptarCancelar());
		contenedor.add(panel);
		
		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private Component panelComprobarNIF() {
		JPanel comprobarNIF = new JPanel();
	
		JLabel nif = new JLabel("Introduzca un NIF:");
		NIF = new JTextField(10);
		
		comprobarNIF.add(nif);
		comprobarNIF.add(NIF);
		
		return comprobarNIF;
	}

	private Component panelAceptarCancelar() {
		JPanel jpAceptarCancelar = new JPanel();
		comprobar = new JButton("Aceptar");
		comprobar.addActionListener(new Comprobar());
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new Cancelar());
		
		jpAceptarCancelar.add(comprobar);
		jpAceptarCancelar.add(cancelar);
		
		return jpAceptarCancelar;
		}
	
	private class Comprobar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
				if(baseDatos.containsCliente(getNIF())) {
					Cliente cliente;
					try {
						cliente = baseDatos.getCliente(getNIF());
						mostrarCliente(cliente);
					} catch (NoExisteCliente e1) {
					}
					
				} else {
					mostrarNoEncontrado("ningún cliente");
				}
			}
	
		private void mostrarCliente(Cliente cliente) {
			emergente = new JDialog(ventana, true);
			
			JPanel res = new JPanel();
			res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
			
			JPanel datos = new JPanel();
			StringBuilder cadena = new StringBuilder();
			cadena.append(cliente.toString());
			
					
			JTextArea resultado = new JTextArea(cadena.toString());
			datos.add(resultado);
			
			JPanel aceptar = new JPanel();
			JButton Aceptar = new JButton("Aceptar");
			Aceptar.addActionListener(new Cancelar());
			aceptar.add(Aceptar);
			
			res.add(datos);
			res.add(aceptar);
			
			emergente.getContentPane().add(res);
			
			emergente.pack();
			emergente.setResizable(false);
			emergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			emergente.setVisible(true);
		}
		
		private void mostrarNoEncontrado(String aviso) {
			emergente = new JDialog(ventana, true);
			JPanel res = new JPanel();
			res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
			
			JPanel datos = new JPanel();
			JLabel mensaje = new JLabel("No se ha encontrado " + aviso + " con el NIF indicado");
			datos.add(mensaje);
			
			JPanel aceptar = new JPanel();
			JButton Aceptar = new JButton("Aceptar");
			Aceptar.addActionListener(new Cancelar());
			aceptar.add(Aceptar);
			
			res.add(datos);
			res.add(aceptar);
			emergente.getContentPane().add(res);
			
			emergente.pack();
			emergente.setResizable(false);
			emergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			emergente.setVisible(true);
		}
	}
	
	private class Cancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventana.dispose();
		}
	}
		
	public String getNIF() {
		return NIF.getText();
	}

	public void setModelo(BBDD baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void ejecutar() {
		ventana.setVisible(true);
	}

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	@Override
	public Vista getVista() {
		return this;
	}

}
