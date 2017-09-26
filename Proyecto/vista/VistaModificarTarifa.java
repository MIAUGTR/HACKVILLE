package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import comun.BBDD;
import controlador.Controlador;
import excepciones.NoExisteCliente;
import tarifa.Basica;
import tarifa.Tarifa;
import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;

public class VistaModificarTarifa implements Vista, Serializable{

	private static final long serialVersionUID = -7906659322668830124L;
	private Controlador controlador;
	private BBDD baseDatos;
	private JDialog ventana;
	private Container contenedor;
	private JPanel panel;
	private JTextField NIF, tarifa;
	private JButton comprobar, aceptar, cancelar;
	private JRadioButton mañana, tarde, noche, sabado, domingo;
	
	public VistaModificarTarifa(JFrame padre) {
		ventana = new JDialog(padre, "Modificar tarifa", true);
		contenedor = ventana.getContentPane();
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(panelComprobarNIF());
		panel.add(panelFranjaHoraria(), BorderLayout.WEST);
		panel.add(panelFinDe(), BorderLayout.EAST);
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
	
	private Component panelFranjaHoraria(){
		
		JPanel franjaHoraria = new JPanel();
		
		JLabel franja = new JLabel ("Tarifa a 0,05€/min;");
		
		mañana = new JRadioButton("Mañana (8:00 - 12:00)");
		mañana.setSelected(true);
		tarde = new JRadioButton("Tarde (16:00 - 20:00)");
		noche = new JRadioButton("Noche (22:00 - 06:00)");
		
		ButtonGroup franjaH = new ButtonGroup();
		franjaH.add(mañana);
		franjaH.add(tarde);
		franjaH.add(noche);
		
		franjaHoraria.add(franja);
		franjaHoraria.add(mañana);
		franjaHoraria.add(tarde);
		franjaHoraria.add(noche);
		
		return franjaHoraria;
	}
	
	private Component panelFinDe(){
		
		JPanel FinDe = new JPanel();
		JLabel finde = new JLabel ("Elige el día que quieras gratis:");
		
		sabado = new JRadioButton("Sabado");
		sabado.setSelected(true);
		domingo = new JRadioButton("Domingo");
		
		ButtonGroup dias = new ButtonGroup();
		dias.add(sabado);
		dias.add(domingo);
		
		FinDe.add(finde);
		FinDe.add(sabado);
		FinDe.add(domingo);
		
		return FinDe;
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
			try {
				controlador.updateTarifa((VistaModificarTarifa) getVista());
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
	
	public TarifaEspecial especial() {
		int tipo = 0;
		if(mañana.isSelected())
			tipo = 0;
		else if(tarde.isSelected())
			tipo = 1;
		else if(noche.isSelected())
			tipo = 2;
		return TarifaEspecial.getOpcion(tipo);
	}
	
	public TarifaFinDe finDe(){
		int tipo = 0;
		if(sabado.isSelected())
			tipo = 0;
		else
			tipo = 1;
		return TarifaFinDe.getOpcion(tipo);
	}
	
	public void resultadoModificar() {
		JDialog Resultado = new JDialog(ventana, true);
		JPanel res = new JPanel();
		res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
		
		JPanel Mensaje = new JPanel();
		JLabel mensaje;
		mensaje = new JLabel("Tarifa cambiada.");
		
		Mensaje.add(mensaje);
		res.add(Mensaje);
		
		JPanel Aceptar = new JPanel();
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Cancelar());
		Aceptar.add(aceptar);
		res.add(Aceptar);
		
		Resultado.getContentPane().add(res);
		
		Resultado.pack();
		Resultado.setResizable(false);
		Resultado.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Resultado.setVisible(true);
	}
	
	public String getNIF() {
		return NIF.getText();
	}
	
	public Tarifa getTarifa(){
		String data = tarifa.getText();
		double numDouble=Double.parseDouble(data);
		Tarifa tar = new Basica(numDouble);
		return tar;
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
