package excepciones;

import java.io.Serializable;

public class ClienteExistente extends Exception implements Serializable{

	private static final long serialVersionUID = 3492745640936045114L;

	public ClienteExistente(String dni) {
		super("\nEl cliente con DNI/NIF " + dni + " ya existe en la Base de Datos.\n\n");
		
	}

}
