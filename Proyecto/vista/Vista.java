package vista;

import comun.BBDD;
import controlador.Controlador;
import excepciones.NoExisteCliente;

public interface Vista {
	void ejecutar() throws NoExisteCliente;
	void setControlador(Controlador controlador);
	void setModelo(BBDD baseDatos);
	Vista getVista();
}