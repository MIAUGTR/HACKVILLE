package entradaSalida;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;

public class EntradaSalida implements Serializable{
	
	private static final long serialVersionUID = -4612755976076274452L;
	private Scanner sc = new Scanner(System.in);
	
	/*Constructor por defecto*/
	public EntradaSalida(){
		super();
	}
	
	/*TODOS LOS MÉTODOS IMPRIMEN Y GUARDAN DATOS 
	 * PARA DESPUÉS PASARLOS AL PROGRAMA PRINCIPAL.
	 * CADA MÉTODO HACE LO QUE SU NOMBRE INDICA*/
	
	/*Método que lee opciones*/
	public int opcionesLee(){
		muestra("Elige una opción (número): ");
		return sc.nextInt();
	}
	
	/*Método que muestra mensajes por pantalla*/
	public void muestra(String mensaje){
		System.out.print(mensaje);
	}
	
	public String pideNombre(){
		muestra("Nombre del cliente: ");
		return sc.next();
	}
	
	public String pideApellidos(){
		muestra("Apellidos del cliente: ");
		return sc.next();
	}
	
	public String pideDNI(){
		muestra("DNI/CIF del cliente: ");
		return sc.next().toUpperCase();
	}
	
	public String pidePoblacion(){
		muestra("\n\tPoblación del cliente: ");
		return sc.next();
	}
	
	public String pideProvincia(){
		muestra("\tProvincia del cliente: ");
		return sc.next();
	}
	
	public String pideCodPostal(){
		muestra("\tCodigo Postal del cliente: ");
		return sc.next();
	}
	
	public String pideMail(){
		muestra("Correo electrónico del cliente: ");
		return sc.next();
	}
	
	public String pideTarifa(){
		muestra("Dime la tarifa: ");
		return sc.next();
	}
	
	public TarifaFinDe eligeFinde(){
		muestra("Oferta fin de semana:\n\n" + TarifaFinDe.getOpciones());
		muestra("\nElige un día para la oferta del fin de semana: ");
		int opcion = sc.nextInt();
		TarifaFinDe dia = null;
		try{
			dia = TarifaFinDe.getOpcion(opcion);
			muestra("Día elegido: " + dia.toString() + "\n");
		}catch(IndexOutOfBoundsException ex){
			muestra("No es una opción válida.  \n");
			eligeFinde();
		}
		return dia;
	}
	
	public TarifaEspecial franjaHoraria(){
		muestra("Franjas horarias:\n\n" + TarifaEspecial.getOpciones());
		muestra("\nElige una franja horaria: ");
		int opcion = sc.nextInt();
		TarifaEspecial franja = null;
		try{
			franja = TarifaEspecial.getOpcion(opcion);
			muestra("Franja horaria elegida: " + franja.toString() + "\n");
		}catch(IndexOutOfBoundsException e){
			muestra("No es una opción válida.\n");
			franjaHoraria();
		}
		return franja;
	}
	
	public String pideTelefono(){
		muestra("Dime el número de teléfono llamado: ");
		return sc.next();
	}
	
	public Double pideDuracion(){
		muestra("Duración de la llamada: ");
		String dato = sc.next();
		double dur = 0.0;
		try{
			dur = Double.parseDouble(dato);
		}catch(InputMismatchException e){
			muestra("Tiene que ser un numero real.\n");
			pideDuracion();
		}catch(NumberFormatException e){
			muestra("Tiene que ser un numero real.\n");
			pideDuracion();
		}
		return dur;
	}
	
	public int[] pideFecha(){
		muestra("Fecha de la llamada (dd/mm/aaaa): ");
		String sFecha = sc.next();
		String [] fechaS = sFecha.split("/");
		int[] fechaI = new int[3];
		try{
			fechaI[0] = Integer.parseInt(fechaS[0]);
			fechaI[1] = Integer.parseInt(fechaS[1])-1;
			fechaI[2] = Integer.parseInt(fechaS[2]);
		}catch(ArrayIndexOutOfBoundsException ex){
			muestra("Fecha no válida\n");
			pideFecha();
		}
		return fechaI;
	}
	
	public int[] pideHora(){
		muestra("HH:MM (formato 24 horas): ");
		String sHora = sc.next();
		String [] vHora = sHora.split(":");
		int [] hora = new int[2];
		try{
			hora[0] = Integer.parseInt(vHora[0]);
			hora[1] = Integer.parseInt(vHora[1]);
		}catch(ArrayIndexOutOfBoundsException ex){
			muestra("Hora no válida\n");
			pideHora();
		}
		muestra("\n");
		return hora;
	}
	
	public int[] pideFechaI(){
		muestra("Fecha inicio (dd/mm/aaaa): ");
		String sFecha = sc.next();
		String [] fechaS = sFecha.split("/");
		int[] fechaI = new int[3];
		try{
			fechaI[0] = Integer.parseInt(fechaS[0]);
			fechaI[1] = Integer.parseInt(fechaS[1])-1;
			fechaI[2] = Integer.parseInt(fechaS[2]);
		}catch(ArrayIndexOutOfBoundsException ex){
			muestra("Fecha no válida\n");
			pideFechaI();
		}
		return fechaI;
	}
	
	public int[] pideFechaF(){
		muestra("Fecha fin (dd/mm/aaaa): ");
		String sFecha = sc.next();
		String [] fechaS = sFecha.split("/");
		int[] fechaI = new int[3];
		try{
			fechaI[0] = Integer.parseInt(fechaS[0]);
			fechaI[1] = Integer.parseInt(fechaS[1])-1;
			fechaI[2] = Integer.parseInt(fechaS[2]);
		}catch(ArrayIndexOutOfBoundsException ex){
			muestra("Fecha no válida\n");
			pideFechaF();
		}
		return fechaI;
	}
	
	public int pideCodFac(){
		muestra("Dime el número de factura: ");
		int codfac = 0;
		try{
			codfac = sc.nextInt();
		}catch(InputMismatchException e){
			muestra("Tiene que ser un entero. \n");
			pideCodFac();
		}
		return codfac;
	}
	
}