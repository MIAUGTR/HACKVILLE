package vista;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
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
import excepciones.PeriodoIncorrecto;

public class VistaEmitirFactura implements Vista, Serializable{
	
	private static final long serialVersionUID = -8827575870427428189L;
	private Controlador controlador;
	private BBDD baseDatos;
	private JDialog ventana;
	private Container contenedor;
	private JPanel panel;
	private JTextField NIF, FechaInicio, FechaFin;
	private JButton comprobar, aceptar, cancelar;
	
	public VistaEmitirFactura(JFrame padre) {
		ventana = new JDialog(padre, "Emitir factura", true);
		contenedor = ventana.getContentPane();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelComprobarNIF());
		panel.add(panelEntradaDatos());
		panel.add(panelAceptarCancelar());
		
		contenedor.add(panel);
		
		ventana.pack();
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setResizable(false);
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
	
	private Component panelEntradaDatos() {
		JPanel datos = new JPanel();
		datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));
		
		JPanel fechaInicio = new JPanel();
		fechaInicio.setLayout(new BoxLayout(fechaInicio, BoxLayout.Y_AXIS));
		
		JLabel inicio = new JLabel("Fecha inicio del periodo: ");
		fechaInicio.add(inicio);
		FechaInicio = new JTextField("DD/MM/YYYY", 20);
		FechaInicio.addCaretListener(new Pulsado());
		fechaInicio.add(FechaInicio);
		
		JPanel fechaFin = new JPanel();
		fechaFin.setLayout(new BoxLayout(fechaFin, BoxLayout.Y_AXIS));
		
		JLabel fin = new JLabel("Fecha finalización del periodo: ");
		fechaFin.add(fin);
		FechaFin = new JTextField("DD/MM/YYYY", 20);
		FechaFin.addCaretListener(new Pulsado());
		fechaFin.add(FechaFin);
		
		datos.add(fechaInicio);
		datos.add(fechaFin);
		return datos;
	}
	
	private Component panelAceptarCancelar() {
		JPanel AceptarCancelar = new JPanel(new FlowLayout());
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new Aceptar());
		aceptar.setEnabled(false);
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new Cancelar());
		
		AceptarCancelar.add(aceptar);
		AceptarCancelar.add(cancelar);
		
		return AceptarCancelar;
	}
	
	public void resultado() {
		JDialog resultado = new JDialog(ventana, true);
		JPanel general = new JPanel();
		general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
		
		JPanel mensaje = new JPanel();
		JLabel mens;
		mens = new JLabel("Factura emitida con éxito.");
		
		mensaje.add(mens);
		general.add(mensaje);
		
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
			if(texto.getText().equals("DD/MM/YYYY")) {
				texto.setText("");
			}
		}
	}
	
	private class Aceptar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controlador.emitFactura((VistaEmitirFactura) getVista());
			} catch (NoExisteCliente e1) {
			} catch (PeriodoIncorrecto e1) {
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
	
	public Calendar getFechaInicio() {
		String[] fechaInicio = FechaInicio.getText().split("/");
		Calendar fechaI = new GregorianCalendar(Integer.parseInt(fechaInicio[2]), Integer.parseInt(fechaInicio[1])-1, Integer.parseInt(fechaInicio[0]));
		return fechaI;
	}
	
	public Calendar getFechaFin() {
		String[] fechaInicio = FechaFin.getText().split("/");
		Calendar fechaI = new GregorianCalendar(Integer.parseInt(fechaInicio[2]), Integer.parseInt(fechaInicio[1])-1, Integer.parseInt(fechaInicio[0]));
		return fechaI;
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
