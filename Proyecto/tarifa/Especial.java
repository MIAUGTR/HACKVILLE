package tarifa;

import java.io.Serializable;
import llamada.Llamada;

public abstract class Especial extends Tarifa implements Serializable{

	private static final long serialVersionUID = 1757088950643450855L;
	private Tarifa tarifa;
	
	public Especial(Tarifa tarifa, double coste) {
		super(coste);
		this.tarifa = tarifa;
	}
	
	public Tarifa getTarifa(){
		return tarifa;
	}
	
	public abstract double getCosteLlamada(Llamada llamada);
	
}
