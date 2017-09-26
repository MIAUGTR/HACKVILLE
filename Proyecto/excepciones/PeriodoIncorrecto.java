package excepciones;

import java.io.Serializable;

public class PeriodoIncorrecto extends Exception implements Serializable{

	private static final long serialVersionUID = -497962052227399661L;

	public PeriodoIncorrecto() {
		super("La fecha fin no puede ser anterior a la de inicio. \n\n");
	}

}
