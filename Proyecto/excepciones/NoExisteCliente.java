package excepciones;

import java.io.Serializable;

public class NoExisteCliente extends Exception implements Serializable{
	
	private static final long serialVersionUID = -7235224645396822944L;

	public NoExisteCliente(String dni) {
		super("\nEl cliente con DNI/NIF " + dni + " no existe en la base de datos.\n\n");
	}
}
