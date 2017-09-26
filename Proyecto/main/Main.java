package main;

import java.io.Serializable;

import javax.swing.SwingUtilities;

import comun.BBDD;
import controlador.Controlador;
import controlador.ImplementacionControlador;
import excepciones.NoExisteCliente;
import vista.Vista;
import vista.menu;

public class Main implements Serializable {

	private static final long serialVersionUID = -8193926418870955482L;

	public static void main(String[] args) throws Exception {
		Controlador controlador = new ImplementacionControlador();
		Vista vista = new menu();
		BBDD baseDatos = new BBDD();
		
		controlador.setModelo(baseDatos);
		controlador.setVista(vista);
		vista.setControlador(controlador);
		vista.setModelo(baseDatos);
		baseDatos.setVista(vista);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					vista.ejecutar();
				} catch (NoExisteCliente e) {
				}
			}
		});
	}
}
