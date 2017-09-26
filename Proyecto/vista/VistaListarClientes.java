package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import comun.BBDD;
import controlador.Controlador;
import excepciones.NoExisteCliente;
import clientes.Cliente;

public class VistaListarClientes implements Vista, Serializable{
	
	private static final long serialVersionUID = 5625549298483885506L;
	@SuppressWarnings("unused")
	private BBDD baseDatos;
	private Controlador controlador;
	private JDialog ventana, emergente;
	
	public VistaListarClientes(JFrame padre) throws NoExisteCliente {
		ventana = new JDialog(padre, "Listar clientes", false);
		ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public void listarClientes(ArrayList<Cliente> clientes) throws NoExisteCliente{
		ArrayList<Cliente> listaClientes = clientes;
		emergente = new JDialog(ventana, true);
		
		JPanel res = new JPanel();
		res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
			
		StringBuilder cadena = new StringBuilder();
		if(listaClientes.isEmpty())
			cadena.append("No hay clientes en la base de datos.");
		else{
			cadena.append("Clientes:\n\n");
			for(Cliente cliente : listaClientes) {
				cadena.append(cliente.toString());
			}
		}
		
		JTextArea datos = new JTextArea(cadena.toString());
		res.add(datos);
		
		JPanel aceptar = new JPanel();
		JButton bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(new Cancelar());
		aceptar.add(bAceptar);
		
		res.add(aceptar);
		
		emergente.getContentPane().add(res);
		emergente.pack();
		emergente.setResizable(false);
		emergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		emergente.setVisible(true);
		
		
	}
	
	private class Cancelar implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ventana.dispose();
		}
	}
	
	public void setModelo(BBDD baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void ejecutar() throws NoExisteCliente {
		ventana.setVisible(true);
		controlador.getClientes((VistaListarClientes) getVista());
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
