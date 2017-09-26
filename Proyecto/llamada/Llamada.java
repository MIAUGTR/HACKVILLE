package llamada;

import java.io.Serializable;
import java.util.Calendar;
import interfaz.TieneFecha;

public class Llamada implements Serializable, TieneFecha {
	
	private static final long serialVersionUID = 6900560467240296899L;
	private String numTelefono;
	private Calendar fecha;
	private double duracion;
	private int horaInicio, horaFinal;
	
	/*Constructor*/
	public Llamada (String numTelefono, double duracion, Calendar fecha){
		this.setNumero(numTelefono);
		this.setDuracion(duracion);
		this.setFecha(fecha);
	}
	
	/*Getters y Setters*/
	public String getNumero(){
		return numTelefono;
	}
	
	public void setNumero(String numero){
		this.numTelefono = numero;
	}
	
	public Calendar getFecha(){
		return fecha;
	}
	
	public void setFecha(Calendar fecha){
		this.fecha = fecha;
	}
	
	public int getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public int getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(int horaFinal) {
		this.horaFinal = horaFinal;
	}

	
	public double getDuracion(){
		return duracion;
	}
	
	public void setDuracion(Double duracion){
		this.duracion = duracion;
	}
	
	/*Formato para la fecha*/
	public String mostrarFecha(Calendar fecha){
		if (fecha != null)
			return fecha.get(Calendar.DATE) + "/" + (fecha.get(Calendar.MONTH)) + "/" + fecha.get(Calendar.YEAR);
		return "";
	}
	
	
	public String toString(){
		return "\tNúmero llamado: " + getNumero() + "\n\tFecha llamada: " + mostrarFecha(getFecha()) +
		"\n\tDuración: " + getDuracion() + " minutos\n\n";
	}
}