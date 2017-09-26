package clientes;

import java.util.GregorianCalendar;
import direccion.Direccion;
import factura.Factura;
import interfaz.TieneFecha;
import llamada.Llamada;
import tarifa.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Cliente implements Serializable, TieneFecha{
	
	private static final long serialVersionUID = -3839600597260972914L;
	private Tarifa tarifa;
	private String nombre;
	private String nif;
	private Direccion direccion;
	private String mail;
	private Calendar fechaAlta;
	private ArrayList<Llamada> llamadas;
	private ArrayList<Factura> facturas;
	private TarifaEspecial franjaHoraria;
	private TarifaFinDe diaFinde;
	
	/*Constructor por defecto*/
	@SuppressWarnings("static-access")
	public Cliente(){
		fechaAlta = new GregorianCalendar().getInstance();
		llamadas = new ArrayList<Llamada>();
		facturas = new ArrayList<Factura>();
	}
	
	/*Getters y Setters*/
	public Tarifa getTarifa(){
		return tarifa;
	}
	
	public void setTarifa(Tarifa tarifa){
		this.tarifa = tarifa;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public String getNif(){
		return nif;
	}
	
	public void setNif(String nif){
		this.nif = nif;
	}
	
	public Direccion getDireccion(){
		return direccion;
	}
	
	public void setDireccion(Direccion direccion){
		this.direccion = direccion;
	}
	
	public String getMail(){
		return mail;
	}
	
	public void setMail(String mail){
		this.mail = mail;
	}
	
	public Calendar getFecha(){
		return fechaAlta;
	}
	
	public void setFechaAlta(Calendar fechaAlta){
		this.fechaAlta = fechaAlta;
	}
	
	public ArrayList<Llamada> getLlamadas(){
		return llamadas;
	}
	
	public void setLlamadas(Llamada llamada){
		this.llamadas.add(llamada);
	}
	
	public ArrayList<Factura> getFactura(){
		return facturas;
	}
	
	public void setFactura(Factura factura){
		this.facturas.add(factura);
	}
	
	public void setFranjaHoraria(TarifaEspecial franja){
		this.franjaHoraria = franja;
	}
	
	public TarifaEspecial getFranjaHoraria(){
		return franjaHoraria;
	}
	
	public void setDiaFinde(TarifaFinDe dia){
		this.diaFinde = dia;
	}
	
	public TarifaFinDe getDiaFinde(){
		return this.diaFinde;
	}
	
	/*Función para dar formato a las fechas*/
	public String mostrarFecha(Calendar fecha){
		return fecha.get(Calendar.DATE) + "/" + (fecha.get(Calendar.MONTH)+1) + "/" + fecha.get(Calendar.YEAR);
	}
	
	/*Método que añade los atributos al objeto cliente*/
	public void anyadirCliente(String dni, String nombre, Direccion direccion, String mail, Tarifa tarifa, TarifaEspecial franja, TarifaFinDe diaFinde){
		this.setNif(dni);
		this.setNombre(nombre);
		this.setDireccion(direccion);
		this.setMail(mail);
		this.setTarifa(tarifa);
		this.setFranjaHoraria(franja);
		this.setDiaFinde(diaFinde);
		this.tarifa = tarifa;
		fechaAlta = new GregorianCalendar();
		llamadas = new ArrayList<Llamada>();
		facturas = new ArrayList<Factura>();
	}
	
	public String toString(){
		return "Nombre: " + getNombre() + "\nDNI: " + getNif() + getDireccion() + "\nMail: " 
		+ getMail() + "\nTarifas:\n\tTarifa básica: 0.15€/min\n\tTarifa especial: 0.05€/min\n\tTarifa fin de semana: 0.00€/min" 
		+ "\nFecha de alta: " + mostrarFecha(getFecha()) + "\nFranja horaria: " + getFranjaHoraria().toString() 
		+ "\nDía del fin de semana gratis: " + getDiaFinde() + "\n\n";
		
	}
	
}
