package menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import factorias.*;
import clientes.*;
import comun.BBDD;
import conjuntoGenerico.ConjuntoGenerico;
import direccion.Direccion;
import entradaSalida.EntradaSalida;
import tarifa.Basica;
import tarifa.Tarifa;
import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;
import excepciones.*;

import factura.Factura;
import llamada.Llamada;

public class Menu implements Serializable{
	
	private static final long serialVersionUID = 6723625954422432425L;
	private boolean salir = false;
	private EntradaSalida entSal;
	private BBDD base = new BBDD();
	private String dni;
	private static double tarifaB;
	
	/*Constructor menu*/
	public Menu(){
		entSal = new EntradaSalida();
		tarifaB = 0.10;
	}
	
	/*Ejecutador de opciones*/
	public void ejecutaOpcion(Opciones opcion) throws PeriodoIncorrecto{
		switch(opcion){
			case SALIR:
				entSal.muestra("\n\t**** SALIENDO *****");
				salir = true;
				break;
			case ALTA_CLIENTE:
				addCliente();
				break;
			case BORRA_CLIENTE:
				removeCliente();
				break;
			case CAMBIA_TARIFA_CLIENTE:
				updateTarifa();
				break;
			case RECUPERA_CLIENTE:
				getCliente();
				break;
			case MOSTRAR_CLIENTES:
				getClientes();
				break;
			case ALTA_LLAMADA:
				addLlamada();
				break;
			case LLAMADAS_CLIENTE:
				getLlamada();
				break;
			case EMITIR_FACTURA:
				emitFactura();
				break;
			case MOSTRAR_FACTURA:
				showFactura();
				break;
			case MOSTRAR_FACTURAS:
				showFacturas();
				break;
			case CLIENTES_ALTA_FECHAS:
				showClientesFechas();
				break;
			case LLAMADAS_CLIENTE_FECHAS:
				showLlamadasClienteFechas();
				break;
			case FACTURAS_CLIENTE_FECHAS:
				showFacturasClienteFechas();
				break;
			default:
				break;
		}
		guardarDatos();
	}

	/*Ejecutador principal*/
	public void ejecuta() throws PeriodoIncorrecto{
		cargarDatos();
		entSal.muestra("\t------ AGENDA TELEFÓNICA -----\n\n");
		while(salir != true){
			entSal.muestra(Opciones.getOpciones());
			try{
				ejecutaOpcion(Opciones.getOpcion(entSal.opcionesLee()));
			}catch(IndexOutOfBoundsException e){
				entSal.muestra("\nElige una opción válida. \n");
				ejecuta();
			}
		}
	}
	
	/*Función de añadir cliente*/
	private void addCliente(){
		entSal.muestra("¿Quieres añadir un cliente particular o empresa?\n\n");
		entSal.muestra(OpClientes.getOpciones());
		OpClientes opcion = OpClientes.getOpcion(entSal.opcionesLee());
		InterfaceFactoriaClientes factoria = new FactoriaClientes();
		Cliente cliente = factoria.getClientes(opcion);
		
		String apellidos;
		if(cliente instanceof ClienteParticular){
			entSal.muestra("\n----- Cliente Particular -----\n");
			apellidos = entSal.pideApellidos();
			((ClienteParticular) cliente).setApellidos(apellidos);
			pideDatosClienteYAñade(cliente);
		}else{
			entSal.muestra("\n----- Cliente Empresa -----\n");
			pideDatosClienteYAñade(cliente);
		}
			
	 }
		 
	 /*Pide datos al cliente y lo añade en la base de datos.*/
	private void pideDatosClienteYAñade(Cliente cliente) {
		dni = entSal.pideDNI();
		cliente.setNif(dni);
		String nombre = entSal.pideNombre();
		cliente.setNombre(nombre);
		entSal.muestra("Dirección cliente: ");
		String poblacion = entSal.pidePoblacion();
		String provincia = entSal.pideProvincia();
		String codPostal = entSal.pideCodPostal();
		Direccion direccion = new Direccion();
		direccion.direccionNueva(poblacion, provincia, codPostal);
		cliente.setDireccion(direccion);
		String mail = entSal.pideMail();
		cliente.setMail(mail);
		cliente.setFechaAlta(Calendar.getInstance());
		Tarifa tarifa = new Basica(tarifaB);
		
		TarifaEspecial franjaE = entSal.franjaHoraria();
		InterfaceFactoriaEspecial franja = new FactoriaEspecial();
		tarifa = franja.dameTarifa(franjaE, tarifa);
		TarifaFinDe tFinde = entSal.eligeFinde();
		InterfaceFactoriaFinde finde = new FactoriaFinde();
		tarifa = finde.dameTarifa(tFinde, tarifa);
		cliente.setTarifa(tarifa);
		
		cliente.setFranjaHoraria(franjaE);
		cliente.setDiaFinde(tFinde);

		try{
			base.setCliente(cliente);
			entSal.muestra("Cliente creado con éxito.\n\n");
		}catch(ClienteExistente ex){
			entSal.muestra(ex.getMessage());	
		}	
	}
	 
	/*Elimina cliente*/
	private void removeCliente(){
		dni = entSal.pideDNI();
		try{
			base.removeCliente(dni);
			entSal.muestra("Cliente borrado con éxito.\n\n");
		}catch(NoExisteCliente ex){
			entSal.muestra(ex.getMessage());
		}
	}
	
	/*Modifica tarifa*/
	private void updateTarifa(){
		dni = entSal.pideDNI();
		double tarifa = Double.parseDouble(entSal.pideTarifa());
		Tarifa tarifaT = new Basica(tarifa);
		try{
			base.updateTarifa(dni, tarifaT);
			entSal.muestra("Tarifa cambiada a " + tarifa + "€/min\n\n");
		}catch(NoExisteCliente ex){
			entSal.muestra(ex.getMessage());
		}
	}
	
	/*Muestra cliente según DNI/NIF*/
	private void getCliente(){
		dni = entSal.pideDNI();
		Object c;
		try {
			c = base.getCliente(dni);
			entSal.muestra(c.toString());
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());;
		}
	}
	
	/*Muestra todos los clientes*/
	private void getClientes(){
		ArrayList<Cliente> cl;
		try {
			cl = base.getClientes();
			if(cl.isEmpty()){
				entSal.muestra("No hay clientes en la base de datos.\n\n");
			}else{
				for(Cliente c : cl)
					entSal.muestra(c.toString());
			}
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		}
		
	}
	
	/*Añade una llamada*/
	private void addLlamada(){
		dni = entSal.pideDNI();
		String nTelefono = entSal.pideTelefono();
		double duracion = entSal.pideDuracion();
		int[] fechaV = entSal.pideFecha();
		int[] horaV = entSal.pideHora();
		Calendar fecha = new GregorianCalendar(fechaV[2], fechaV[1], fechaV[0], horaV[0], horaV[1]);
		try {
			base.addLlamada(dni, nTelefono, duracion, fecha);
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		}
	}
	
	/*Muestra las llamadas*/
	private void getLlamada(){
		dni = entSal.pideDNI();
		Cliente cliente;
		try {
			cliente = (Cliente) base.getCliente(dni);
			String llamadas = cliente.getLlamadas().toString();
			entSal.muestra("\n\tCliente: " + cliente.getNombre() + llamadas.substring(1, llamadas.length()-1));
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		}
		
	}
	
	/*Emite un factura*/
	private void emitFactura(){
		dni = entSal.pideDNI();
		int[] fechaI = entSal.pideFechaI();
		int[] fechaF = entSal.pideFechaF();
		Calendar fechaInicio = new GregorianCalendar(fechaI[2], fechaI[1], fechaI[0]);
		Calendar fechaFin = new GregorianCalendar(fechaF[2], fechaF[1], fechaF[0]);
		entSal.muestra("\n");
		try {
			base.emitFactura(dni, fechaInicio, fechaFin);
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		} catch (PeriodoIncorrecto pI){
			entSal.muestra(pI.getMessage());
		}
	}
	
	/*Muestra una factura según DNI/NIF y código de factura*/
	private void showFactura(){
		dni = entSal.pideDNI();
		int codfac = entSal.pideCodFac();
		Factura f;
		try {
			f = base.muestraFactura(dni, codfac);
			entSal.muestra(f.toString());
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		} catch (NoExisteFactura ex){
			entSal.muestra(ex.getMessage());
		} catch (IndexOutOfBoundsException ex){
			entSal.muestra("No hay facturas para este cliente\n\n");
		}
	}
	
	/*Muestra todas las facturas*/
	private void showFacturas(){
		dni = entSal.pideDNI();
		try {
			String facturas = base.muestraFacturasCliente(dni).toString();
			facturas = facturas.substring(1, facturas.length()-1);
			entSal.muestra(facturas);
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());;
		}
	}
	
	/*Carga los datos de la agenda desde un fichero*/
	private void cargarDatos(){
		ObjectInputStream entrada = null;
		try{
			try{
				FileInputStream fEntrada = new FileInputStream("agenda.bin");
				entrada = new ObjectInputStream(fEntrada);
				base = (BBDD) entrada.readObject();
			}finally{
				if(entrada != null) 
					entrada.close();
			}
		}catch(FileNotFoundException e){
			entSal.muestra("Fichero inexistente. Se crea una nueva agenda.\n\n");
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/*Guarda los datos de la agenda en un fichero*/
	private void guardarDatos(){
		ObjectOutputStream salida = null;
		try{
			try{
				FileOutputStream fSalida = new FileOutputStream("agenda.bin");
				salida = new ObjectOutputStream(fSalida);
				salida.writeObject(base);
			}finally{
				salida.close();
			}
		}catch(FileNotFoundException e){
			entSal.muestra("No se encuentra el fichero. \n");
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/*Muestra clientes entre dos fechas*/
	private void showClientesFechas() throws PeriodoIncorrecto {
		int[] fechaI = entSal.pideFechaI();
		int[] fechaF = entSal.pideFechaF();
		Calendar fechaInicio = new GregorianCalendar(fechaI[2], fechaI[1], fechaI[0]);
		Calendar fechaFin = new GregorianCalendar(fechaF[2], fechaF[1], fechaF[0]);
		
		if(fechaFin.compareTo(fechaInicio) < 0)
			entSal.muestra(new PeriodoIncorrecto().getMessage());
		
		ConjuntoGenerico<Cliente> conjunto = new ConjuntoGenerico<Cliente>();
		Collection<Cliente> clientes;
		try {
			clientes = conjunto.getConjunto(base.clientesFechas(), fechaInicio, fechaFin);
			if(!clientes.isEmpty()){
				for(Cliente cliente : clientes)
					entSal.muestra(cliente.toString());
			}else
				entSal.muestra("No hay clientes dados de alta entre las fechas que buscas.\n\n");
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		}
	}

	/*Muestra las llamadas de un cliente entre dos fechas*/
	private void showLlamadasClienteFechas() {
		int[] fechaI = entSal.pideFechaI();
		int[] fechaF = entSal.pideFechaF();
		Calendar fechaInicio = new GregorianCalendar(fechaI[2], fechaI[1], fechaI[0]);
		Calendar fechaFin = new GregorianCalendar(fechaF[2], fechaF[1], fechaF[0]);
		
		if(fechaFin.compareTo(fechaInicio) < 0)
			entSal.muestra(new PeriodoIncorrecto().getMessage());
		
		ConjuntoGenerico<Llamada> conjunto = new ConjuntoGenerico<Llamada>();
		Collection<Llamada> llamadas;
		try {
			llamadas = conjunto.getConjunto(base.llamadasFechas(entSal.pideDNI()), fechaInicio, fechaFin);
			if(!llamadas.isEmpty()){
				for(Llamada llamada : llamadas){
					entSal.muestra(llamada.toString());
				}
			}else
				entSal.muestra("El cliente que buscas no ha realizado ninguna llamada entre las fechas buscadas.\n\n");
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		}
	}

	/*Muestra las facturas de un cliente entre dos fechas*/
	private void showFacturasClienteFechas() throws PeriodoIncorrecto {
		int[] fechaI = entSal.pideFechaI();
		int[] fechaF = entSal.pideFechaF();
		Calendar fechaInicio = new GregorianCalendar(fechaI[2], fechaI[1], fechaI[0]);
		Calendar fechaFin = new GregorianCalendar(fechaF[2], fechaF[1], fechaF[0]);
		
		if(fechaFin.compareTo(fechaInicio) < 0)
			entSal.muestra(new PeriodoIncorrecto().getMessage());
		
		ConjuntoGenerico<Factura> conjunto = new ConjuntoGenerico<Factura>();
		Collection<Factura> facturas;
		try {
			facturas = conjunto.getConjunto(base.facturasFechas(entSal.pideDNI()), fechaInicio, fechaFin);
			if(!facturas.isEmpty()){
				for(Factura factura : facturas){
					entSal.muestra(factura.toString());
				}
			}else
				entSal.muestra("El cliente no tiene facturas para las fechas que buscas.\n\n");
		} catch (NoExisteCliente ex) {
			entSal.muestra(ex.getMessage());
		}
	}
	
}
