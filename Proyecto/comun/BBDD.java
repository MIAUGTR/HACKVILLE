package comun;

import java.io.Serializable;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import clientes.Cliente;
import conjuntoGenerico.ConjuntoGenerico;
import excepciones.*;
import factura.Factura;
import llamada.Llamada;
import tarifa.*;
import vista.*;

@SuppressWarnings("unused")
public class BBDD implements Serializable{
	
	private static final long serialVersionUID = -8096089473480533669L;
	private TreeMap<String, Cliente> clientesBD;
	private Vista vista;
	
	/*Constructor*/
	public BBDD(){
		clientesBD = new TreeMap<String, Cliente>();
	}
	
	/*MÉTODOS VIEJOS*/
	
	public TreeMap<String, Cliente> getClientesBD(){
		return this.clientesBD;
	}
	
	public void setClientesBD(TreeMap<String, Cliente> clientesBD){
		this.clientesBD=clientesBD;
	}
	
	/*Añadir clientes*/
	public void setCliente(Cliente cliente) throws ClienteExistente{
		if(!containsCliente(cliente.getNif())){
			clientesBD.put(cliente.getNif(), cliente);
		}else
			throw new ClienteExistente(cliente.getNif());
	}
	
	/*Eliminar cliente*/
	 public void removeCliente(String nif) throws NoExisteCliente{
		 if(containsCliente(nif)){
			 clientesBD.remove(nif);
		 }else
			 throw new NoExisteCliente(nif);
	 }
	
	 /*Añade una tarifa*/
	 public void setTarifa(String nif, Tarifa tarifa) throws NoExisteCliente{
		 Cliente cliente = (Cliente) getCliente(nif);
		 cliente.setTarifa(tarifa);
		 clientesBD.put(nif, cliente);
	 }
	 
	 /*Devuelve tarifa*/
	 public Tarifa getTarifa(String nif) throws NoExisteCliente{
		 return ((Cliente) getCliente(nif)).getTarifa();
	 }
	 
	 /*Cambiar tarifa*/
	public void updateTarifa(String dni, Tarifa tarifa) throws NoExisteCliente{
		if(containsCliente(dni)){
			setTarifa(dni, tarifa);
		}else
			throw new NoExisteCliente(dni);
	}
	
	/*Devuelve cliente*/
	public Cliente getCliente(String nif) throws NoExisteCliente{
		if(containsCliente(nif)){
			return clientesBD.get(nif);
		}else
			throw new NoExisteCliente(nif);
	}
	
	/*Devuelve la base de datos de clientes*/
	public ArrayList<Cliente> getClientes() throws NoExisteCliente {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		for(String dni : clientesBD.keySet()){
			clientes.add((Cliente) getCliente(dni));
	 	}
		
		return clientes;
	}
	 
	/*Dar de alta una llamada*/
	public void addLlamada(String dni, String numero, Double duracion, Calendar fecha) throws NoExisteCliente{
		if(containsCliente(dni)){
			Llamada llamada = new Llamada(numero, duracion, fecha);
			((Cliente) getCliente(dni)).setLlamadas(llamada);
		}
	}
	
	/*Emite factura*/
	public void emitFactura(String dni, Calendar fechaI, Calendar fechaF) throws NoExisteCliente, PeriodoIncorrecto{
		
		ArrayList<Llamada> llamadas = new ArrayList<Llamada>();
		Cliente cli = getCliente(dni);
		if(fechaI.before(fechaF)){
			for(Llamada llamada : cli.getLlamadas()){
				if(llamada.getFecha().after(fechaI) && llamada.getFecha().before(fechaF)){
					llamadas.add(llamada);
				}
			}
			
			double importe = 0.0;
			Tarifa tarifa = cli.getTarifa();
			for(Llamada llam : llamadas){
				importe += tarifa.getCosteLlamada(llam);
			}
			Cliente cliente = ((Cliente) getCliente(dni));
			Factura factura = new Factura(tarifa, fechaI, fechaF, llamadas, importe, cliente.getFactura().size());
			cliente.setFactura(factura);	
		}else{
			throw new PeriodoIncorrecto();
		}
	}
	
	/*Devuelve facturas*/
	public ArrayList<Factura> muestraFacturasCliente(String dni) throws NoExisteCliente{
		Cliente cliente = (Cliente) getCliente(dni);
		return cliente.getFactura();
	}
	
	/*Muestra factura*/
	public Factura muestraFactura(String dni, int codfac) throws NoExisteCliente, NoExisteFactura{
		Cliente cliente = (Cliente) getCliente(dni);
		try{
			Factura fact = cliente.getFactura().get(codfac);
			if(fact != null){
				return fact;
			}else
				throw new NoExisteFactura(codfac);
		}catch(IndexOutOfBoundsException ex){
			throw new IndexOutOfBoundsException();
		}
	}
	
	/*Devuelve lista con clientes*/
	public ArrayList<Cliente> clientesFechas() throws NoExisteCliente{
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
		for(String dni : clientesBD.keySet()){
			clientes.add(((Cliente) getCliente(dni)));
		}
		return clientes;	
	}
	
	/*Devuelve lista con llamadas*/
	public ArrayList<Llamada> llamadasFechas(String dni) throws NoExisteCliente{
		Cliente cliente = (Cliente) getCliente(dni);
		ArrayList<Llamada> llamadas = cliente.getLlamadas();
		return llamadas;
	}
	
	/*Devuelve lista con facturas*/
	public ArrayList<Factura> facturasFechas(String dni) throws NoExisteCliente{
		Cliente cliente = (Cliente) getCliente(dni);
		ArrayList<Factura> facturas = cliente.getFactura();
		return facturas;
	}
	
	
	
	/*MÉTODOS NUEVOS*/
	/*Añadir clientes*/
	public void setCliente(Cliente cliente, VistaNuevoCliente vista) throws ClienteExistente{
		if(!containsCliente(cliente.getNif())){
			clientesBD.put(cliente.getNif(), cliente);
			vista.resultadoAnyadir(true);
		}else
			vista.resultadoAnyadir(false);
			throw new ClienteExistente(cliente.getNif());
	}
	
	/*Eliminar cliente*/
	 public void removeCliente(String nif, VistaBorrarCliente vista) throws NoExisteCliente{
		 if(containsCliente(nif)){
			 clientesBD.remove(nif);
			 vista.resultadoBorrar();
		 }else
			 throw new NoExisteCliente(nif);
	 }
	 
	 /*Cambiar tarifa*/
		public void updateTarifa(String dni, TarifaEspecial franja, TarifaFinDe finde, VistaModificarTarifa vista) throws NoExisteCliente{
			if(containsCliente(dni)){
				Cliente cliente = (Cliente) getCliente(dni);
				cliente.setFranjaHoraria(franja);
				cliente.setDiaFinde(finde);
				clientesBD.put(dni, cliente);
				vista.resultadoModificar();
			}else
				throw new NoExisteCliente(dni);
		}
	
	/*Devuelve la base de datos de clientes*/
	public void getClientes(VistaListarClientes vista) throws NoExisteCliente {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		for(String dni : clientesBD.keySet()){
			clientes.add((Cliente) getCliente(dni));
	 	}
		
		vista.listarClientes(clientes);
	}
	
	/*Dar de alta una llamada*/
	public void addLlamada(String dni, String numero, Double duracion, Calendar fecha, int inicio, int fin,  VistaNuevaLlamada vista) throws NoExisteCliente{
		if(containsCliente(dni)){
			Llamada llamada = new Llamada(numero, duracion, fecha);
			llamada.setHoraInicio(inicio);
			llamada.setHoraFinal(fin);
			((Cliente) getCliente(dni)).setLlamadas(llamada);
			vista.resultado();
		}
	}
		
	/*Muestra las llamadas*/
	public void getLlamada(String nif, VistaListarLlamadas vista){
		Cliente cliente;
		try {
			cliente = getCliente(nif);
			ArrayList<Llamada> llamadas = cliente.getLlamadas();
			if (!llamadas.isEmpty())
				vista.listarLlamadas(llamadas);
			else
				vista.noLlamada();
		} catch (NoExisteCliente ex) {
		}
	}
	
	/*Emite factura*/
	public void emitFactura(String dni, Calendar fechaI, Calendar fechaF, VistaEmitirFactura vista) throws NoExisteCliente, PeriodoIncorrecto{
		
		ArrayList<Llamada> llamadas = new ArrayList<Llamada>();
		Cliente cli = getCliente(dni);
		if(fechaI.before(fechaF)){
			for(Llamada llamada : cli.getLlamadas()){
				if(llamada.getFecha().after(fechaI) && llamada.getFecha().before(fechaF)){
					llamadas.add(llamada);
				}
			}
			
			double importe = 0.0;
			Tarifa tarifa = cli.getTarifa();
			for(Llamada llam : llamadas){
				importe += tarifa.getCosteLlamada(llam);
			}
			Cliente cliente = ((Cliente) getCliente(dni));
			Factura factura = new Factura(tarifa, fechaI, fechaF, llamadas, importe, cliente.getFactura().size());
			cliente.setFactura(factura);
			vista.resultado();
		}else{
			throw new PeriodoIncorrecto();
		}
	}
	
	/*Muestra factura*/
	public void muestraFactura(String dni, int codfac, VistaMostrarFactura vista) throws NoExisteCliente, NoExisteFactura{
		Cliente cliente = (Cliente) getCliente(dni);
		try{
			Factura fact = cliente.getFactura().get(codfac);
			if(fact != null){
				vista.mostrarFactura(fact);
			}else
				throw new NoExisteFactura(codfac);
		}catch(IndexOutOfBoundsException ex){
			throw new IndexOutOfBoundsException();
		}
	}
	
	/*Devuelve facturas*/
	public void muestraFacturasCliente(String dni, VistaListarFacturas vista) throws NoExisteCliente{
		Cliente cliente = (Cliente) getCliente(dni);
		ArrayList<Factura> facturas = cliente.getFactura();
		if(!facturas.isEmpty())
			vista.listarFacturas(facturas);
		else
			vista.noFacturas();
		
	}
	
	/*Devuelve lista con clientes*/
	public void clientesFechas(VistaClientesFechas vista) throws NoExisteCliente{

		Calendar fechaInicio = vista.getFechaI();
		Calendar fechaFin = vista.getFechaF();
		
		if(fechaFin.compareTo(fechaInicio) < 0)
			System.out.println(new PeriodoIncorrecto().getMessage());
		
		ConjuntoGenerico<Cliente> conjunto = new ConjuntoGenerico<Cliente>();
		Collection<Cliente> clientes;
		try {
			clientes = conjunto.getConjunto(clientesFechas(), fechaInicio, fechaFin);
			if(!clientes.isEmpty()){
				vista.listarClientesFechas(clientes);
			}else
				vista.resultadoNo();
		} catch (NoExisteCliente ex) {
			System.out.println(ex.getMessage());
		}	
	}
	
	/*Devuelve lista con llamadas*/
	public void llamadasFechas(VistaLlamadasFechas vista) throws NoExisteCliente{
		
		Calendar fechaInicio = vista.getFechaI();
		Calendar fechaFin = vista.getFechaF();
		
		if(fechaFin.compareTo(fechaInicio) < 0)
			System.out.println(new PeriodoIncorrecto().getMessage());
		
		ConjuntoGenerico<Llamada> conjunto = new ConjuntoGenerico<Llamada>();
		Collection<Llamada> llamadas;
		try {
			llamadas = conjunto.getConjunto(llamadasFechas(vista.getNIF()), fechaInicio, fechaFin);
			if(!llamadas.isEmpty()){
				vista.listarLlamadasFechas(llamadas);
			}else
				vista.resultadoNo();
		} catch (NoExisteCliente ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/*Devuelve lista con facturas*/
	public void facturasFechas(VistaFacturasFechas vista) throws NoExisteCliente{

		Calendar fechaInicio = vista.getFechaI();
		Calendar fechaFin = vista.getFechaF();

		if(fechaFin.compareTo(fechaInicio) < 0)
			System.out.println(new PeriodoIncorrecto().getMessage());
		
		ConjuntoGenerico<Factura> conjunto = new ConjuntoGenerico<Factura>();
		Collection<Factura> facturas;
		try {
			facturas = conjunto.getConjunto(facturasFechas(vista.getNIF()), fechaInicio, fechaFin);
			if(!facturas.isEmpty()){
				vista.listarFacturasFechas(facturas);
			}else
				vista.resultadoNo();
		} catch (NoExisteCliente ex) {
			System.out.println(ex.getMessage());
		}
	}
	
/*------------------------------------------------------------------------------------*/
	
	public void setVista(Vista vista) {
		this.vista = vista;
		
	}
	
	/*Método que verifica si la base de datos contiene o no un cliente*/
	public boolean containsCliente(String nif){
		return clientesBD.containsKey(nif);
	}
}
