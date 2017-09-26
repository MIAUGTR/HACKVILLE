package vista;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

import comun.BBDD;
import controlador.Controlador;
import excepciones.NoExisteCliente;

public class VistaNuevaLlamada implements Vista, Serializable{
	
	private static final long serialVersionUID = -1095965637228210578L;
	private BBDD baseDatos;
	private Controlador controlador;
	private JDialog ventana;
	private Container contenedor;
	private JPanel panel;
	private JTextField NIF, Numero, HoraInicial, HoraFin, FECHA;
	private JButton comprobar, aceptar, cancelar;
	
	public VistaNuevaLlamada(JFrame padre) {
		ventana = new JDialog(padre, "Nueva llamada", true);
		contenedor = ventana.getContentPane();
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(panelComprobarNIF());
		panel.add(panelEntradaDatos());
		panel.add(panelFechaLlamada());
		panel.add(panelAceptarCancelar());
		
		contenedor.add(panel);
		
		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private Component panelComprobarNIF() {
		JPanel comprobarNIF = new JPanel();
		
		JLabel nif = new JLabel("NIF:");
		NIF = new JTextField(10);
		NIF.addCaretListener(new Modificado());
		comprobar = new JButton("Comprobar");
		comprobar.addActionListener(new Comprobar());
		
		comprobarNIF.add(nif);
		comprobarNIF.add(NIF);
		comprobarNIF.add(comprobar);
		
		return comprobarNIF;
	}
	
	private Component panelFechaLlamada(){
		JPanel datos = new JPanel();
		JLabel fecha = new JLabel("Fecha realización llamada: ");
		FECHA = new JTextField("DD/MM/YYYY", 10);
		FECHA.addCaretListener(new Pulsado());
		
		datos.add(fecha);
		datos.add(FECHA);
		
		return datos;
		
	}
	
	private Component panelEntradaDatos() {
		JPanel datos = new JPanel();
		JPanel basico = new JPanel();
		basico.setLayout(new BoxLayout(basico, BoxLayout.Y_AXIS));
		
		JLabel num = new JLabel("Número llamado:");
		Numero = new JTextField(10);
		
		basico.add(num);
		basico.add(Numero);
		datos.add(basico);
		
		JPanel tiempo = new JPanel();
		JPanel tiempoInicial = new JPanel();
		tiempoInicial.setLayout(new BoxLayout(tiempoInicial, BoxLayout.Y_AXIS));
		JPanel tiempoFinal = new JPanel();
		tiempoFinal.setLayout(new BoxLayout(tiempoFinal, BoxLayout.Y_AXIS));
	
		JLabel horaInicio = new JLabel("Hora inicio:");
		HoraInicial = new JTextField("HH:MM", 10);
		HoraInicial.addCaretListener(new Pulsado());
		JLabel horaFinal = new JLabel("Hora final:");
		HoraFin = new JTextField("HH:MM", 10);
		HoraFin.addCaretListener(new Pulsado());
		
		tiempoInicial.add(horaInicio);
		tiempoInicial.add(HoraInicial);
		tiempoFinal.add(horaFinal);
		tiempoFinal.add(HoraFin);
		
		tiempo.add(tiempoInicial);
		tiempo.add(tiempoFinal);
		
		datos.add(tiempo);
		
		return datos;
	}
	
	private Component panelAceptarCancelar() {
		JPanel aceptarCancelar = new JPanel();
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Aceptar());
		aceptar.setEnabled(false);
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new Cancelar());
		
		aceptarCancelar.add(aceptar);
		aceptarCancelar.add(cancelar);
		return aceptarCancelar;
	}
	
	public void resultado() {
		JDialog resultado = new JDialog(ventana, true);
		JPanel general = new JPanel();
		general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
		
		JPanel mensaje = new JPanel();
		JLabel res;
		res = new JLabel("Llamada dada de alta con éxito.");
		
		mensaje.add(res);
		general.add(mensaje);
		
		JPanel Aceptar = new JPanel();
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Cancelar());
		Aceptar.add(aceptar);
		general.add(Aceptar);
		
		resultado.getContentPane().add(general);
		
		resultado.pack();
		resultado.setResizable(false);
		resultado.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		resultado.setVisible(true);
	}
	
	public double getDuracion(){
		
		double duracion = 0.0;
		String[] inicio = getHoraInicio().split(":");
		double horaI = Double.parseDouble(inicio[0]);
		double minutosI = Double.parseDouble(inicio[1]);
		
		String[] fin = getHoraFin().split(":");
		double horaF = Double.parseDouble(fin[0]);
		double minutosF = Double.parseDouble(fin[1]);
		
		duracion = minutosF - minutosI;
		
		if (horaI != horaF)
			duracion += 60 * (horaF-horaI);
		
		return duracion;
	}
	
	public Calendar getFecha(){
		String [] fecha = FECHA.getText().split("/");		
		Calendar Fecha = new GregorianCalendar(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[0]));
		return Fecha;
	}
	
	public int getHoraI(){
		String[] inicio = getHoraInicio().split(":");
		return Integer.parseInt(inicio[0]);
	}
	
	public int getHoraF(){
		String[] fin = getHoraFin().split(":");
		return Integer.parseInt(fin[0]);
	}
	
	private class Modificado implements CaretListener {
		@Override
		public void caretUpdate(CaretEvent arg0) {
			if(aceptar.isEnabled())
				aceptar.setEnabled(false);
		}
	}
	
	private class Pulsado implements CaretListener {
		@Override
		public void caretUpdate(CaretEvent e) {
			JTextComponent texto = ((JTextComponent) e.getSource());
			if(texto.getText().equals("HH:MM") || texto.getText().equals("DD/MM/YYYY"))
				texto.setText(""); 
		}
	}
	
	private class Aceptar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controlador.addLlamada((VistaNuevaLlamada) getVista());
			} catch (NoExisteCliente e1) {
			}
		}
	}
	
	private class Cancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventana.dispose();
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
	
	public String getNIF() {
		return NIF.getText();
	}
	
	public String getNumero() {
		return Numero.getText();
	}
	
	public String getHoraInicio() {
		return HoraInicial.getText();
	}

	public String getHoraFin() {
		return HoraFin.getText();
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
