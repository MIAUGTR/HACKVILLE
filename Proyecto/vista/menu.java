package vista;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.JButton;

import comun.BBDD;
import controlador.Controlador;
import excepciones.NoExisteCliente;


public class menu implements Vista, Serializable {

	private static final long serialVersionUID = 3539840373904307454L;
	private Controlador controlador;
	private BBDD baseDatos;
	private JFrame ventana;

	private Container contenedor;
	private JPanel panel;
	
	public menu(){
		ventana = new JFrame("Agenda telefónica");
		contenedor = ventana.getContentPane();
		panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2, 10, 10));
	}
	@Override
	public void ejecutar() {
		opciones();
		contenedor.add(panel);

		ventana.pack();
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		
		try {
			cargar();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("No existe el fichero.");
		}
	}
	
	private void cargar() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ObjectInputStream entrada = null;
		try{
			try{
				BBDD base;
				FileInputStream fEntrada = new FileInputStream("agenda.bin");
				entrada = new ObjectInputStream(fEntrada);
				base = (BBDD) entrada.readObject();
				baseDatos.setClientesBD(base.getClientesBD());
			}finally{
				if(entrada != null) 
					entrada.close();
			}
		}catch(FileNotFoundException e){
			System.out.println("Fichero inexistente. Se crea una nueva agenda.\n\n");
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void guardar() throws FileNotFoundException, IOException, NoExisteCliente {
		
		ObjectOutputStream salida = null;
		try{
			try{
				FileOutputStream fSalida = new FileOutputStream("agenda.bin");
				salida = new ObjectOutputStream(fSalida);
				salida.writeObject(baseDatos);
			}finally{
				salida.close();
			}
		}catch(FileNotFoundException e){
			System.out.println("El fichero no existe");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	
	private void opciones() {
		JButton op1 = new JButton("Añadir cliente");
		op1.addActionListener(new NuevoCliente());
		panel.add(op1);
		JButton op2 = new JButton("Borrar cliente" );
		op2.addActionListener(new BorrarCliente());
		panel.add(op2);
		JButton op3 = new JButton("Modificar tarifa" );
		op3.addActionListener(new ModificarTarifa());
		panel.add(op3);
		JButton op4 = new JButton("Mostrar datos de un cliente" );
		op4.addActionListener(new GetCliente());
		panel.add(op4);
		JButton op5 = new JButton("Mostrar clientes" );
		op5.addActionListener(new ListarClientes());
		panel.add(op5);
		JButton op6 = new JButton("Añadir llamada");
		op6.addActionListener(new NuevaLlamada());
		panel.add(op6);
		JButton op7= new JButton("Mostrar llamadas" );
		op7.addActionListener(new ListarLlamadas());
		panel.add(op7);
		JButton op8 = new JButton("Emitir factura" );
		op8.addActionListener(new EmitirFactura());
		panel.add(op8);
		JButton op9 = new JButton("Mostrar datos de una factura" );
		op9.addActionListener(new GetFactura());
		panel.add(op9);
		JButton op10 = new JButton("Mostrar facturas" );
		op10.addActionListener(new ListarFacturas());
		panel.add(op10);
		JButton op11 = new JButton("Mostrar clientes entre 2 fechas" );
		op11.addActionListener(new ClientesFechas());
		panel.add(op11);
		JButton op12 = new JButton("Mostrar llamadas de un cliente entre 2 fechas" );
		op12.addActionListener(new LlamadasFechas());
		panel.add(op12);
		JButton op13 = new JButton("Mostrar facturas de un cliente entre 2 fechas" );
		op13.addActionListener(new FacturasFechas());
		panel.add(op13);
		JButton op14 = new JButton("Salir" );
		op14.addActionListener(new Cerrar());
		panel.add(op14);	
	}
	
	private class NuevoCliente implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaNuevoCliente nuevoCliente = new VistaNuevoCliente(ventana);
			nuevoCliente.setControlador(controlador);
			nuevoCliente.setModelo(baseDatos);
			nuevoCliente.ejecutar();
		}
	}
	
	private class BorrarCliente implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaBorrarCliente borrarCliente = new VistaBorrarCliente(ventana);
			borrarCliente.setControlador(controlador);
			borrarCliente.setModelo(baseDatos);
			borrarCliente.ejecutar();
		}
	}
	
	private class ModificarTarifa implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaModificarTarifa modificarTarifa= new VistaModificarTarifa(ventana);
			modificarTarifa.setControlador(controlador);
			modificarTarifa.setModelo(baseDatos);
			modificarTarifa.ejecutar();
		}
	}
	
	private class GetCliente implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaMostrarCliente mostrarCliente = new VistaMostrarCliente(ventana);
			mostrarCliente.setModelo(baseDatos);
			mostrarCliente.ejecutar();
		}
	}
	
	private class ListarClientes implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaListarClientes listarClientes;
			try {
				listarClientes = new VistaListarClientes(ventana);
				listarClientes.setControlador(controlador);
				listarClientes.setModelo(baseDatos);
				try {
					listarClientes.ejecutar();
				} catch (NoExisteCliente e1) {
				}
			} catch (NoExisteCliente e2) {
			}
		
		}
	}
	
	private class NuevaLlamada implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaNuevaLlamada nuevaLlamada = new VistaNuevaLlamada(ventana);
			nuevaLlamada.setControlador(controlador);
			nuevaLlamada.setModelo(baseDatos);
			nuevaLlamada.ejecutar();
		}
	}
	
	private class ListarLlamadas implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaListarLlamadas listarLlamadas= new VistaListarLlamadas(ventana);
			listarLlamadas.setControlador(controlador);
			listarLlamadas.setModelo(baseDatos);
			listarLlamadas.ejecutar();
		}
	}
	
	private class EmitirFactura implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaEmitirFactura emitirFactura = new VistaEmitirFactura(ventana);
			emitirFactura.setControlador(controlador);
			emitirFactura.setModelo(baseDatos);
			emitirFactura.ejecutar();
		}
	}
	
	private class GetFactura implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaMostrarFactura mostrarFactura = new VistaMostrarFactura(ventana);
			mostrarFactura.setControlador(controlador);
			mostrarFactura.setModelo(baseDatos);
			mostrarFactura.ejecutar();
		}
	}
	
	private class ListarFacturas implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaListarFacturas listarFacturas= new VistaListarFacturas(ventana);
			listarFacturas.setControlador(controlador);
			listarFacturas.setModelo(baseDatos);
			listarFacturas.ejecutar();
		}
	}
	
	private class ClientesFechas implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaClientesFechas clientesFechas = new VistaClientesFechas(ventana);
			clientesFechas.setControlador(controlador);
			clientesFechas.setModelo(baseDatos);
			clientesFechas.ejecutar();
		}
	}
	
	private class LlamadasFechas implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaLlamadasFechas llamadasFechas = new VistaLlamadasFechas(ventana);
			llamadasFechas.setControlador(controlador);
			llamadasFechas.setModelo(baseDatos);
			llamadasFechas.ejecutar();
		}
	}
	
	private class FacturasFechas implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			VistaFacturasFechas facturasFechas = new VistaFacturasFechas(ventana);
			facturasFechas.setControlador(controlador);
			facturasFechas.setModelo(baseDatos);
			facturasFechas.ejecutar();
		}
	}
	
	private class Cerrar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				guardar();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (NoExisteCliente e1) {
				e1.printStackTrace();
			}
			ventana.dispose();
		}
	}

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	@Override
	public void setModelo(BBDD baseDatos) {
		this.baseDatos = baseDatos;
	}

	@Override
	public Vista getVista() {
		return this;
	}
	
}
