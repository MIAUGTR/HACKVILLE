package vista;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

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
import factura.Factura;
import comun.BBDD;

public class VistaListarFacturas implements Vista, Serializable{

	private static final long serialVersionUID = 8886815273137383431L;
	private BBDD baseDatos;
	private Controlador controlador;
	private JDialog ventana, emergente;
	private JPanel panel;
	private JTextField NIF;
	private JButton comprobar, aceptar, cancelar;
	
	public VistaListarFacturas(JFrame padre) {
		ventana = new JDialog(padre, "Listar facturas", true);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelComprobarNIF());
		panel.add(panelAceptarCancelar());		
		
		ventana.getContentPane().add(panel);
		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private Component panelComprobarNIF() {
		JPanel comprobarNIF = new JPanel();
		
		JLabel jlNIF = new JLabel("NIF: ");
		NIF = new JTextField(20);
		NIF.setEnabled(true);
		comprobar = new JButton("Comprobar");
		comprobar.addActionListener(new Comprobar());
		comprobar.setEnabled(true);
		
		comprobarNIF.add(jlNIF);
		comprobarNIF.add(NIF);
		comprobarNIF.add(comprobar);
		
		return comprobarNIF;
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
	
	
	private class Comprobar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(baseDatos.containsCliente(getNIF())){ 
				aceptar.setEnabled(true);
			}else
				noExiste();
		}
	}
	
	private class Aceptar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				controlador.showFacturas((VistaListarFacturas) getVista());
			} catch (NoExisteCliente e) {
			}
		}
	}
		
	public void listarFacturas(ArrayList<Factura> facturas) throws NoExisteCliente {
		emergente = new JDialog(ventana, true);
		JPanel general = new JPanel();
			
		StringBuilder cadena = new StringBuilder();
		cadena.append("Facturas:\n\n");
		for(Factura factura : facturas) {
			cadena.append(factura);
		}
		
		JTextArea datos = new JTextArea(cadena.toString());
		general.add(datos);
		
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Cancelar());
		general.add(aceptar);
		
		emergente.getContentPane().add(general);
		emergente.pack();
		emergente.setResizable(false);
		emergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		emergente.setVisible(true);
		
	}	
	
	public void noExiste(){
		JDialog resultado = new JDialog(ventana, true);
		JPanel general = new JPanel();
		general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
		
		JPanel mensaje = new JPanel();
		JLabel lMensaje;

		
		lMensaje = new JLabel("No existe el cliente buscado.");
		
		
		mensaje.add(lMensaje);
		general.add(lMensaje);
		
		JPanel Aceptar = new JPanel();
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Cancelar());
		Aceptar.add(aceptar);
		general.add(Aceptar);
		
		resultado.getContentPane().add(general);
		
		resultado.pack();
		resultado.setResizable(false);
		resultado.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		resultado.setVisible(true);
	}
	
	public void noFacturas() {
		JDialog resultado = new JDialog(ventana, true);
		JPanel general = new JPanel();
		general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
		
		JPanel mensaje = new JPanel();
		JLabel lMensaje;

		
		lMensaje = new JLabel("Este cliente no tiene facturas registradas");
		
		
		mensaje.add(lMensaje);
		general.add(lMensaje);
		
		JPanel Aceptar = new JPanel();
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Cancelar());
		Aceptar.add(aceptar);
		general.add(Aceptar);
		
		resultado.getContentPane().add(general);
		
		resultado.pack();
		resultado.setResizable(false);
		resultado.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		resultado.setVisible(true);
	}
	
	private class Cancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
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
