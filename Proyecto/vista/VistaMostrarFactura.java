package vista;

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

import controlador.Controlador;
import excepciones.NoExisteCliente;
import excepciones.NoExisteFactura;
import factura.Factura;
import comun.BBDD;

public class VistaMostrarFactura implements Vista, Serializable{

	private static final long serialVersionUID = -3271464381677782527L;
	private BBDD baseDatos;
	private Controlador controlador;
	private JDialog ventana, emergente;
	private Container contenedor;
	private JPanel panel;
	private JTextField COD, Nif;
	private JButton comprobar, aceptar, cancelar;
	
	public VistaMostrarFactura(JFrame padre) {
		ventana = new JDialog(padre, "Mostrar factura", true);
		contenedor = ventana.getContentPane();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelPideNIF());
		panel.add(panelPideCod());
		panel.add(panelAceptarCancelar());
		contenedor.add(panel);
		
		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private Component panelPideNIF(){
		JPanel NIF = new JPanel();
		JLabel nif = new JLabel ("Introduce el DNI/NIF del cliente: ");
		
		Nif = new JTextField(10);
		
		comprobar = new JButton("Comprobar");
		comprobar.addActionListener(new ComprobarNIF());
		
		NIF.add(nif);
		NIF.add(Nif);
		NIF.add(comprobar);
		
		return NIF;
	}
	
	private Component panelPideCod() {
		JPanel ComprobarCOD = new JPanel();
		
		JLabel cod = new JLabel("Introduce código factura:");
		COD = new JTextField(10);
		
		ComprobarCOD.add(cod);
		ComprobarCOD.add(COD);
		return ComprobarCOD;
	}
	
	private Component panelAceptarCancelar() {
		JPanel AceptarCancelar = new JPanel();
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Aceptar());
		aceptar.setEnabled(false);
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new Cancelar());
		
		AceptarCancelar.add(aceptar);
		AceptarCancelar.add(cancelar);
		
		return AceptarCancelar;
	}
	
	private class ComprobarNIF implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(baseDatos.containsCliente(getNIF())) {
				aceptar.setEnabled(true);
			}else{
				mostrarNoEncontrado();
			}
		}
	}
		
	public void mostrarFactura(Factura factura) {
		emergente = new JDialog(ventana, true);
		JPanel general = new JPanel();

		StringBuilder cadena = new StringBuilder();
		cadena.append("Factura:\n\n");
		cadena.append(factura.toString());
		
		JTextArea Factura = new JTextArea(cadena.toString());
		general.add(Factura);
		
		JPanel Aceptar = new JPanel();
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Cancelar());
		Aceptar.add(aceptar);
		
		general.add(Aceptar);
		
		emergente.getContentPane().add(general);
		
		emergente.pack();
		emergente.setResizable(false);
		emergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		emergente.setVisible(true);
		
	}
		
	private void mostrarNoEncontrado() {
		emergente = new JDialog(ventana, true);
		JPanel general = new JPanel();
		general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
		
		JPanel mensaje = new JPanel();
		JLabel Mensaje = new JLabel("No se ha encontrado lo que buscas");
		mensaje.add(Mensaje);
		
		JPanel Aceptar = new JPanel();
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Cancelar());
		
		Aceptar.add(aceptar);
		
		general.add(mensaje);
		general.add(Aceptar);
		emergente.getContentPane().add(general);
		
		emergente.pack();
		emergente.setResizable(false);
		emergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		emergente.setVisible(true);
		
	}
		
	private class Aceptar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controlador.showFactura((VistaMostrarFactura) getVista());
			} catch (NoExisteCliente e1) {
				mostrarNoEncontrado();
			}catch(IndexOutOfBoundsException e1){
				mostrarNoEncontrado();
			} catch (NoExisteFactura e1) {
				mostrarNoEncontrado();
			}
		}
	}

	
	private class Cancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventana.dispose();
		}
	}

	public String getNIF(){
		return Nif.getText();
	}
	
	public int getCodigo() {
		return Integer.parseInt(COD.getText());
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
