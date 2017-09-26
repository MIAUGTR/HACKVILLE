package controlador;

import java.io.Serializable;
import java.util.Calendar;

import clientes.*;
import comun.BBDD;
import direccion.Direccion;
import excepciones.ClienteExistente;
import excepciones.NoExisteCliente;
import excepciones.NoExisteFactura;
import excepciones.PeriodoIncorrecto;
import factorias.FactoriaClientes;
import factorias.FactoriaEspecial;
import factorias.FactoriaFinde;
import factorias.InterfaceFactoriaClientes;
import factorias.InterfaceFactoriaEspecial;
import factorias.InterfaceFactoriaFinde;
import tarifa.Basica;
import tarifa.Tarifa;
import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;
import vista.*;

@SuppressWarnings("unused")
public class ImplementacionControlador implements Controlador, Serializable{
	
	private static final long serialVersionUID = 2381030683676423588L;
	private BBDD modelo;
	private Vista vista;
	private static double tarifaB = 0.15;
	
	public ImplementacionControlador(){
		super();
	}
	
	public void setVista(Vista vista){
		this.vista = vista;
	}
	
	public void setModelo(BBDD modelo){
		this.modelo = modelo;
	}
	
	public void addCliente(VistaNuevoCliente vista) throws ClienteExistente{
		
		InterfaceFactoriaClientes factoria = new FactoriaClientes();
		Cliente cliente = factoria.getCliente(vista.getTipoCliente());
		cliente.setNif(vista.getNIF());
		cliente.setNombre(vista.getNombre());
		
		if(cliente instanceof ClienteParticular) {
			((ClienteParticular) cliente).setApellidos(vista.getApellidos());
		}
		
		Direccion direccion = new Direccion();
		direccion.setCodPostal(vista.getCodPos());
		direccion.setPoblacion(vista.getPoblacion());
		direccion.setProvincia(vista.getProvincia());
		cliente.setDireccion(direccion);
		cliente.setMail(vista.getMail());
		cliente.setFechaAlta(Calendar.getInstance());
		
		Tarifa tarifa = new Basica(tarifaB);
		
		TarifaEspecial franjaE = vista.especial();
		InterfaceFactoriaEspecial franja = new FactoriaEspecial();
		tarifa = franja.dameTarifa(franjaE, tarifa);
		
		TarifaFinDe tFinde = vista.finDe();
		InterfaceFactoriaFinde finde = new FactoriaFinde();
		tarifa = finde.dameTarifa(tFinde, tarifa);
		cliente.setTarifa(tarifa);
		
		cliente.setFranjaHoraria(franjaE);
		cliente.setDiaFinde(tFinde);
		
		modelo.setCliente(cliente, vista);
	}


	@Override
	public void removeCliente(VistaBorrarCliente vista ) {
		try {
			modelo.removeCliente(vista.getNIF(), vista);
		} catch (NoExisteCliente e) {
		}
	}

	@Override
	public void updateTarifa(VistaModificarTarifa vista) throws NoExisteCliente {
		
		Tarifa tarifa = new Basica(tarifaB);
		TarifaEspecial franjaE = vista.especial();
		InterfaceFactoriaEspecial franja = new FactoriaEspecial();
		tarifa = franja.dameTarifa(franjaE, tarifa);
		
		TarifaFinDe tFinde = vista.finDe();
		InterfaceFactoriaFinde finde = new FactoriaFinde();
		tarifa = finde.dameTarifa(tFinde, tarifa);
		
		modelo.updateTarifa(vista.getNIF(), franjaE, tFinde, vista);

	}

	@Override
	public void getCliente(VistaMostrarCliente vista) {
		try{
			modelo.getCliente(vista.getNIF());
		}catch (NoExisteCliente e){
		}
	}

	@Override
	public void getClientes(VistaListarClientes vista) {
		try {
			modelo.getClientes(vista);
		} catch (NoExisteCliente e) {
		}
	}

	@Override
	public void addLlamada(VistaNuevaLlamada vista) throws NoExisteCliente {
		modelo.addLlamada(vista.getNIF(), vista.getNumero(), vista.getDuracion(), vista.getFecha(), vista.getHoraI(), vista.getHoraF(), vista);
	}

	@Override
	public void getLlamadas(VistaListarLlamadas vista) {
		modelo.getLlamada(vista.getNIF(), vista);
	}

	@Override
	public void emitFactura(VistaEmitirFactura vista) throws NoExisteCliente, PeriodoIncorrecto {
		modelo.emitFactura(vista.getNIF(), vista.getFechaInicio(), vista.getFechaFin(), vista);
	}

	@Override
	public void showFactura(VistaMostrarFactura vista) throws NoExisteCliente, NoExisteFactura {
		modelo.muestraFactura(vista.getNIF(), vista.getCodigo(), vista);
	}

	@Override
	public void showFacturas(VistaListarFacturas vista) throws NoExisteCliente {
		modelo.muestraFacturasCliente(vista.getNIF(), vista);
	}

	@Override
	public void showClientesFechas(VistaClientesFechas vista) throws NoExisteCliente {
		modelo.clientesFechas(vista);
	}

	@Override
	public void showLlamadasClienteFechas(VistaLlamadasFechas vista) throws NoExisteCliente {
		modelo.llamadasFechas(vista);
	}

	@Override
	public void showFacturasClienteFechas(VistaFacturasFechas vista) throws NoExisteCliente {
		modelo.facturasFechas(vista);
	}

	
}