package vista;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
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

import comun.BBDD;
import controlador.Controlador;
import excepciones.ClienteExistente;
import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;

public class VistaNuevoCliente implements Vista, Serializable{
	
	private static final long serialVersionUID = -1405482869826754274L;
	@SuppressWarnings("unused")
	private BBDD baseDatos;
	private Controlador controlador;
	private JDialog ventana;
	private Container contenedor;
	private JPanel 	panel;
	private JRadioButton empresa, particular, defecto, mañana, tarde, noche, sabado, domingo;
	private JTextField NIF, nombre, primerApellido,
				segundoApellido, poblacion, 
				codPos, provincia, mail;
	
	public VistaNuevoCliente(JFrame padre) {
		this.ventana = new JDialog(padre, "Alta Cliente", true);
		contenedor = ventana.getContentPane();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelDatosEntrada());
		panel.add(panelTarifa());
		panel.add(panelFinDe());
		panel.add(panelAceptarCancelar());
		
		contenedor.add(panel);
		
		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	private Component panelDatosEntrada() {
		JPanel general = new JPanel();
		JPanel tipo = new JPanel();
		tipo.setLayout(new BoxLayout(tipo, BoxLayout.Y_AXIS));
		
		empresa = new JRadioButton("Empresa");
		empresa.addActionListener(new eleccion());
		particular = new JRadioButton("Particular");
		particular.addActionListener(new eleccion());
		empresa.setSelected(true);
		
		ButtonGroup tipoCliente = new ButtonGroup();
		tipoCliente.add(empresa);
		tipoCliente.add(particular);
		
		tipo.add(empresa);
		tipo.add(particular);
		
		general.add(tipo);
		
		JPanel data = new JPanel();
		data.setLayout(new BoxLayout(data, BoxLayout.Y_AXIS));
		
		JLabel nif = new JLabel("NIF:");
		NIF = new JTextField(10);
		
		data.add(nif);
		data.add(NIF);
		general.add(data);
		
		JLabel Nombre = new JLabel("Nombre:");
		nombre = new JTextField(20);
				
		data.add(Nombre);
		data.add(nombre);
		
		JLabel PrimerApellido = new JLabel("Primer apellido:");
		primerApellido = new JTextField(20);
		primerApellido.setEnabled(false);
		
		data.add(PrimerApellido);
		data.add(primerApellido);
		
		JLabel SegundoApellido = new JLabel("Segundo apellido:");
		segundoApellido = new JTextField(20);
		segundoApellido.setEnabled(false);
		
		data.add(SegundoApellido);
		data.add(segundoApellido);
		
		JLabel Poblacion = new JLabel("Población:");
		poblacion = new JTextField(20);
		
		data.add(Poblacion);
		data.add(poblacion);
		
		JLabel CodPos = new JLabel("Código postal:");
		codPos = new JTextField(10);
		
		data.add(CodPos);
		data.add(codPos);
		
		JLabel Provincia = new JLabel("Provincia:");
		provincia = new JTextField(10);
		
		data.add(Provincia);
		data.add(provincia);
				
		JLabel Mail = new JLabel("Correo electrónico:");
		mail = new JTextField(35);
		
		data.add(Mail);
		data.add(mail);

		general.add(data);
		
		return general;
	}
	
	private Component panelTarifa(){
		
		JPanel precios = new JPanel(new FlowLayout());
		JLabel tarifa = new JLabel("Tarifa:");
		JPanel franja = new JPanel();
		franja.setLayout(new BoxLayout(franja, BoxLayout.Y_AXIS));
		
		defecto = new JRadioButton("Defecto: 0,15 €/min\n");
		defecto.setSelected(true);
		JLabel tarifa1 = new JLabel("Franjas especiales a 0,05€/min :");
		mañana = new JRadioButton("Horario mañana (08:00 - 12:00) ");
		tarde = new JRadioButton("Horario tarde (16:00 - 20:00)");
		noche = new JRadioButton("Horario noche (22:00 - 06:00)");
		
		ButtonGroup tipoTarifa = new ButtonGroup();
		tipoTarifa.add(defecto);
		tipoTarifa.add(mañana);
		tipoTarifa.add(tarde);
		tipoTarifa.add(noche);
		
		franja.add(tarifa);
		franja.add(defecto);
		franja.add(tarifa1);
		franja.add(mañana);
		franja.add(tarde);
		franja.add(noche);
		
		precios.add(franja);
		return precios;
	}
	
	private Component panelFinDe(){
		JPanel finde = new JPanel(new FlowLayout());
		JLabel tarifa = new JLabel("Tarifa Fin de semana (Gratis el día elegido):");
		JPanel franja = new JPanel();
		franja.setLayout(new BoxLayout(franja, BoxLayout.Y_AXIS));
		
		sabado = new JRadioButton("Sábado");
		sabado.setSelected(true);
		domingo = new JRadioButton("Domingo");
		
		ButtonGroup tipoTarifa = new ButtonGroup();
		tipoTarifa.add(sabado);
		tipoTarifa.add(domingo);
		
		franja.add(tarifa);
		franja.add(sabado);
		franja.add(domingo);
		
		finde.add(franja);
		return finde;
	}
	
	private Component panelAceptarCancelar(){
		JPanel aceptarCancelar = new JPanel(new FlowLayout());
		JButton aceptar = new JButton("Aceptar");
		JButton cancelar = new JButton("Cancelar");
		
		aceptar.addActionListener(new Aceptar());
		cancelar.addActionListener(new Cancelar());
		
		aceptarCancelar.add(aceptar);
		aceptarCancelar.add(cancelar);
		
		return aceptarCancelar;
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

	
	private class eleccion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(particular.isSelected()) {
				primerApellido.setEnabled(true);
				segundoApellido.setEnabled(true);
			} else {
				primerApellido.setEnabled(false);
				segundoApellido.setEnabled(false);
			}
		}
	}
	private class Aceptar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controlador.addCliente((VistaNuevoCliente) getVista());
			} catch (ClienteExistente e1) {
			}
		}
	}
	
	private class Cancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ventana.dispose();
		}
	}
	
	public void resultadoAnyadir(boolean resultado) {
		JDialog Resultado = new JDialog(ventana, true);
		JPanel res = new JPanel();
		res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
		
		JPanel Mensaje = new JPanel();
		JLabel mensaje;
		if(resultado) {
			mensaje = new JLabel("Cliente creado con éxito.");
		} else {
			mensaje = new JLabel("El cliente ya existe.");			
		}
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
	
	public String getTipoCliente() {
		if(empresa.isSelected()) {
			return "Empresa";
		} else {
			return "Particular";
		}
	}
	
	public String getNIF() {
		return NIF.getText();
	}

	public String getNombre() {
		return nombre.getText();
	}

	public String getApellidos() {
		return primerApellido.getText() + " " + segundoApellido.getText();
	}

	public String getPoblacion() {
		return poblacion.getText();
	}

	public String getCodPos() {
		return codPos.getText();
	}

	public String getProvincia() {
		return provincia.getText();
	}

	public String getMail() {
		return mail.getText();
	}
	
	@Override
	public void ejecutar() {
		ventana.setVisible(true);
	}

	public void setModelo(BBDD baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	@Override
	public Vista getVista() {
		return this;
	}

}
