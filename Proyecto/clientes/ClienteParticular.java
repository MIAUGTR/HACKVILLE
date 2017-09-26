package clientes;
import java.io.Serializable;

import clientes.Cliente;
import direccion.Direccion;
import tarifa.*;

public class ClienteParticular extends Cliente implements Serializable {
	
	private static final long serialVersionUID = 6141855041055445606L;
	private String apellidos;
	Cliente cliente;
	
	/*Constructor por defecto*/
	public ClienteParticular(){
		super();
	}
	
	/*Getter*/
	public String getApellidos(){
		return apellidos;
	}
	public void setApellidos(String apellidos){
		this.apellidos = apellidos;
	}
		
	/*Método para asignar los atributos al objeto cliente particular*/
	public void anyadirParticular(String dni, String nombre, String apellidos, Direccion direccion, String mail, Tarifa tarifa, TarifaEspecial franja, TarifaFinDe dia){
		this.apellidos = apellidos;
		String nombreApellidos = nombre + " " + apellidos; 
		anyadirCliente(dni, nombreApellidos, direccion, mail, tarifa, franja, dia);
	}
	
}
