package tarifa;

import java.io.Serializable;
import java.util.Calendar;

import llamada.Llamada;

public class Finde extends Especial implements Serializable{
	
	private static final long serialVersionUID = -6202661265171921025L;
	private int dia;
	private static double GRATIS = 0.0;
	
	public Finde(Tarifa tarifa, double coste, int dia){
		super(tarifa, coste);
		this.dia = dia;
	}
	
	public int getDia(){
		return dia;
	}
	
	public double getGRATIS(){
		return GRATIS;
	}
	
	@Override
	public double getCosteLlamada(Llamada llamada){
		if(llamada.getFecha().get(Calendar.DAY_OF_WEEK) == getDia()){
			double precio = getTarifa().getCosteLlamada(llamada);
			precio = Math.min(precio, getGRATIS() * llamada.getDuracion());
			setTarifa(getGRATIS());
			return precio;
		}else{
			return getTarifa().getCosteLlamada(llamada);
		}
	}
}
