package conjuntoGenerico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import interfaz.TieneFecha;

public class ConjuntoGenerico<T extends TieneFecha> implements Serializable{

	private static final long serialVersionUID = 6045689936693437183L;

	/*Constructor por defecto*/
	public ConjuntoGenerico() {
		super();
	}
	
	/*Devuelve un conjunto genérico con los datos que hay entre dos fechas*/
	public Collection<T> getConjunto(Collection<T> conjunto, Calendar inicio, Calendar fin) {
		Collection<T> conjuntoGenerico = new ArrayList<T>();
		for(T elem : conjunto){
			if(elem.getFecha().compareTo(inicio) >= 0 && elem.getFecha().compareTo(fin) <= 0){
				conjuntoGenerico.add(elem);
			}
		}
		return conjuntoGenerico;
	}
	
}
