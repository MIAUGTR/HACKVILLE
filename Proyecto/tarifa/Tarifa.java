package tarifa;

import java.io.Serializable;

import llamada.Llamada;

public abstract class Tarifa implements Serializable{
	
	private static final long serialVersionUID = -6733214451447336267L;
	private double tarifa;
	
	/*Constructor por defecto*/
	public Tarifa(){
		
	}
	
	public Tarifa(double tarifa){
		this.tarifa = tarifa;
	}
	
	/*Getter y Setter*/
	public double dameTarifa(){
		return tarifa;
	}
	
	public void setTarifa(double tarifa){
		this.tarifa = tarifa;
	}
	
	public String toString(){
		return dameTarifa() + "";
	}
	
	public abstract double getCosteLlamada(Llamada llamada);
}
