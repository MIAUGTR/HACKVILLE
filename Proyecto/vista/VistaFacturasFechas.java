package vista;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

import factura.Factura;
import comun.BBDD;
import controlador.Controlador;
import excepciones.NoExisteCliente;

public class VistaFacturasFechas implements Vista, Serializable{

	private static final long serialVersionUID = -3262683278612954095L;
	private BBDD baseDatos;
	private Controlador controlador;
	private JDialog ventana, emergente;
	private Container contenedor;
	private JPanel panel;
	private JTextField NIF, fechaI, fechaF;
	private JButton aceptar, cancelar, comprobar;
	
	
	public VistaFacturasFechas(JFrame padre) {
		ventana = new JDialog(padre, "Facturas de un cliente entre dos fechas", true);
		contenedor = ventana.getContentPane();
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(panelPideNif());
		panel.add(panelFechas());
		panel.add(panelAceptarCancelar());
		
		contenedor.add(panel);
		
		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private Component panelPideNif(){
		JPanel panelBusqueda = new JPanel();
		
		JLabel nif = new JLabel("NIF:");
		NIF = new JTextField(10);
		comprobar = new JButton("Comprobar");
		comprobar.addActionListener(new Comprobar());
		
		panelBusqueda.add(nif);
		panelBusqueda.add(NIF);
		panelBusqueda.add(comprobar);
		return panelBusqueda;
	}
	
	private Component panelFechas(){
		JPanel panelBusqueda = new JPanel();
		
		JLabel fInicio = new JLabel("Fecha Inicio: ");
		fechaI = new JTextField("DD/MM/YYYY", 10);
		fechaI.addCaretListener(new Pulsado());

		JLabel fFin = new JLabel("Fecha Fin: ");
		fechaF = new JTextField("DD/MM/YYYY", 10);
		fechaF.addCaretListener(new Pulsado());
		
		panelBusqueda.add(fInicio);
		panelBusqueda.add(fechaI);
		panelBusqueda.add(fFin);
		panelBusqueda.add(fechaF);
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
	
	private class Comprobar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(baseDatos.containsCliente(getNIF())) {
				aceptar.setEnabled(true);
			}
		}
	}

	private class Pulsado implements CaretListener {
		@Override
		public void caretUpdate(CaretEvent e) {
			JTextComponent texto = ((JTextComponent) e.getSource());
			if(texto.getText().equals("DD/MM/YYYY"))
					texto.setText("");
		}
	}
	
	public void listarFacturasFechas(Collection<Factura> facturas) {
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
	
	private class Aceptar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controlador.showFacturasClienteFechas((VistaFacturasFechas) getVista());
			} catch (NoExisteCliente e1) {
				noExiste();
			}
		}
	}

	public void resultadoNo(){
		
		JDialog resultado = new JDialog(ventana, true);
		JPanel general = new JPanel();
		general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
		
		JPanel mensaje = new JPanel();
		JLabel lMensaje;

		
		lMensaje = new JLabel("No existen facturas entre esas fechas.");
		
		
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
	
	private class Cancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventana.dispose();
		}
	}

	public void setModelo(BBDD baseDatos) {
		this.baseDatos = baseDatos;
	}

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public String getNIF(){
		return NIF.getText();
	}

	public Calendar getFechaI(){
		String [] fecha = fechaI.getText().split("/");
		Calendar fFin = new GregorianCalendar(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[0]));
		return fFin;
		
	}

	public Calendar getFechaF(){
		String [] fecha = fechaF.getText().split("/");
		Calendar fInicio = new GregorianCalendar(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[0]));
		return fInicio;
		
	}
	
	public void ejecutar() {
		ventana.setVisible(true);
	}
	
	@Override
	public Vista getVista() {
		return this;
	}

}
