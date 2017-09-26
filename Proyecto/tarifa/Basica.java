package tarifa;

import java.io.Serializable;

import llamada.Llamada;

public class Basica extends Tarifa implements Serializable{
	
	private static final long serialVersionUID = -853390211821488230L;

	public Basica(double tarifa) {
		super(tarifa);
	}
	
	@Override
	public double getCosteLlamada(Llamada llamada){
		return super.dameTarifa() * llamada.getDuracion();
	}
}
