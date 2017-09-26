package direccion;

import java.io.Serializable;

public class Direccion implements Serializable{
	
	private static final long serialVersionUID = -1839729404612134068L;
	private String codPostal;
	private String provincia;
	private String poblacion;
	
	/*Constructor por defecto*/
	public Direccion(){	
	}
	
	/*Getters y setters*/
	public String getCodPostal(){
		return codPostal;
	}
	
	public void setCodPostal(String codPostal){
		this.codPostal = codPostal;
	}
	
	public String getProvincia(){
		return provincia;
	}
	
	public void setProvincia(String provincia){
		this.provincia = provincia;
	}
	
	public String getPoblacion(){
		return poblacion;
	}
	
	public void setPoblacion(String poblacion){
		this.poblacion = poblacion;
	}
	
	/*Método para asignar los atributos al objeto dirección*/
	public void direccionNueva(String poblacion, String provincia, String codPostal){
		this.setPoblacion(poblacion);
		this.setProvincia(provincia);
		this.setCodPostal(codPostal);
	}
	
	public String toString(){
		return "\nPoblación: " + getPoblacion() + "\nProvincia: " + getProvincia() + 
		"\nCódigo Postal: " + getCodPostal();
	}
}
