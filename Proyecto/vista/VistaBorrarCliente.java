package vista;


import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import comun.BBDD;
import controlador.Controlador;

public class VistaBorrarCliente implements Vista, Serializable{

	private static final long serialVersionUID = -8464568300718918811L;
	private Controlador controlador;
	private BBDD baseDatos;
	private JDialog ventana;
	private Container contenedor;
	private JPanel panel;
	private JTextField NIF;
	private JButton aceptar, cancelar, comprobar;
	
	public VistaBorrarCliente(JFrame padre) {
		ventana = new JDialog(padre, "Borrar cliente", true);
		contenedor = ventana.getContentPane();
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(panelBusqueda());
		panel.add(panelAceptarCancelar());
		
		contenedor.add(panel);
		
		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private Component panelBusqueda() {
		JPanel panelBusqueda = new JPanel();
		
		JLabel nif = new JLabel("NIF:");
		NIF = new JTextField(10);
		NIF.addCaretListener(new Modificado());
		comprobar = new JButton("Comprobar");
		comprobar.addActionListener(new Comprobar());
		
		panelBusqueda.add(nif);
		panelBusqueda.add(NIF);
		panelBusqueda.add(comprobar);
		return panelBusqueda;
	}	
	
	private Component panelAceptarCancelar() {
		JPanel panelAceptarCancelar = new JPanel();
		panelAceptarCancelar.setLayout(new FlowLayout());
		
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Aceptar());
		aceptar.setEnabled(false);
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new Cancelar());
		
		panelAceptarCancelar.add(aceptar);
		panelAceptarCancelar.add(cancelar);
	
		return panelAceptarCancelar;
	}
	
	private class Modificado implements CaretListener {
		@Override
		public void caretUpdate(CaretEvent arg0) {
			if(aceptar.isEnabled())
				aceptar.setEnabled(false);
		}
	}
	
	private class Comprobar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(baseDatos.containsCliente(getNIF())) {
				aceptar.setEnabled(true);
			}
		}
	}
	
	private class Aceptar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controlador.removeCliente((VistaBorrarCliente) getVista());
		}
	}
	
	private class Cancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventana.dispose();
		}
	}
	
	public void resultadoBorrar() {
		JDialog Resultado = new JDialog(ventana, true);
		JPanel res = new JPanel();
		res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
		
		JPanel Mensaje = new JPanel();
		JLabel mensaje;
		mensaje = new JLabel("Cliente borrado con éxito.");

		Mensaje.add(mensaje);
		res.add(Mensaje);
		
		JPanel aceptar = new JPanel();
		JButton Aceptar = new JButton("Aceptar");
		Aceptar.addActionListener(new Cancelar());
		aceptar.add(Aceptar);
		res.add(aceptar);
		
		Resultado.getContentPane().add(res);
		
		Resultado.pack();
		Resultado.setResizable(false);
		Resultado.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Resultado.setVisible(true);
	}
	
	public String getNIF() {
		return NIF.getText();
	}
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void setModelo(BBDD baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void ejecutar() {
		ventana.setVisible(true);
	}

	@Override
	public Vista getVista() {
		return this;
	}

}
