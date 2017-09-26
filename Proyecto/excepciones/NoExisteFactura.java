package excepciones;

import java.io.Serializable;

public class NoExisteFactura extends Exception implements Serializable{

	private static final long serialVersionUID = -1577864019010811507L;

	public NoExisteFactura(int num){
		super("\nLa factura con código " + num + " no existe.\n\n");
	}
}
