package controlador;

import comun.BBDD;
import excepciones.ClienteExistente;
import excepciones.NoExisteCliente;
import excepciones.NoExisteFactura;
import excepciones.PeriodoIncorrecto;
import vista.*;

public interface Controlador {
	void setVista(Vista vista);
	void setModelo(BBDD modelo);
	void addCliente(VistaNuevoCliente vista) throws ClienteExistente;
	void removeCliente(VistaBorrarCliente vista);
	void updateTarifa(VistaModificarTarifa vista) throws NoExisteCliente;
	void getCliente(VistaMostrarCliente vista);
	void getClientes(VistaListarClientes vista);
	void addLlamada(VistaNuevaLlamada vista) throws NoExisteCliente;
	void getLlamadas(VistaListarLlamadas vista);
	void emitFactura(VistaEmitirFactura vista) throws NoExisteCliente, PeriodoIncorrecto;
	void showFactura(VistaMostrarFactura vista) throws NoExisteCliente, NoExisteFactura;
	void showFacturas(VistaListarFacturas vista) throws NoExisteCliente;
	void showClientesFechas(VistaClientesFechas vista) throws NoExisteCliente;
	void showLlamadasClienteFechas(VistaLlamadasFechas vista) throws NoExisteCliente;
	void showFacturasClienteFechas(VistaFacturasFechas vista) throws NoExisteCliente;
	
}
