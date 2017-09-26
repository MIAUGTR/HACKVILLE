package tarifa;

import java.io.Serializable;

import llamada.Llamada;

public class FranjaHoraria extends Especial implements Serializable{
	
	private static final long serialVersionUID = 6674633100849774996L;
	private int inicio;
	private int fin;
	private static double ESPECIAL = 0.05;

	public FranjaHoraria(Tarifa tarifa, double coste, int inicio, int fin) {
		super(tarifa, coste);
		this.inicio = inicio;
		this.fin = fin;
	}
	
	public int getInicio(){
		return inicio;
	}
	
	public int getFin(){
		return fin;
	}

	public double getESPECIAL(){
		return ESPECIAL;
	}
	
	@Override
	public double getCosteLlamada(Llamada llamada){
		if(llamada.getHoraInicio() >= getInicio() && (llamada.getHoraFinal() <= getFin() || llamada.getHoraFinal() <= getFin()+24)){
			double precio = getTarifa().getCosteLlamada(llamada);
			precio = Math.min(precio, getESPECIAL() * llamada.getDuracion());
			setTarifa(getESPECIAL());
			return precio;
		}else{
			return getTarifa().getCosteLlamada(llamada);
		}
	}
}
