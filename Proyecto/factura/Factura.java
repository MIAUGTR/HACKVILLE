package factura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import interfaz.TieneFecha;
import llamada.Llamada;
import tarifa.Tarifa;

public class Factura implements Serializable, TieneFecha{

	private static final long serialVersionUID = -5360602652373495683L;
	private int codFactura;
	private Tarifa tarifa;
	private Calendar fechaEmision;
	private Calendar inicioFact;
	private Calendar finFact;
	private double importe;
	private ArrayList<Llamada> llamadas;
	
	/*Constructor por defecto*/
	public Factura(){	
	}
	
	/*Método para asignar los atributos al objeto factura*/
	public Factura(Tarifa tarifa, Calendar fechaI, Calendar fechaF, ArrayList<Llamada> llamadas, double importe, int numFac){
		this.tarifa = tarifa;
		this.fechaEmision = GregorianCalendar.getInstance();
		this.inicioFact = fechaI;
		this.finFact = fechaF;
		this.llamadas = llamadas;
		this.importe = importe;
		this.codFactura = numFac;
	}
	
	/*Getters y Setters*/
	public int getCodFactura(){
		return codFactura;
	}
	
	public void setCodFactura(int codFactura){
		this.codFactura = codFactura;
	}
	
	public Calendar getFecha(){
		return fechaEmision;
	}
	
	public Calendar getFechaI(){
		return inicioFact;
	}
	
	public Calendar getFechaF(){
		return finFact;
	}
	
	public double getImporte(){
		return importe;
	}
	
	public Tarifa getTarifa(){
		return tarifa;
	}
	
	public ArrayList<Llamada> getLlamadas(){
		return llamadas;
	}
	
	/*Formato para mostrar las llamadas*/
	public String muestraLlamadas(){
		StringBuilder vuelta = new StringBuilder();
		for(Llamada llam : getLlamadas()){
			vuelta.append(llam.toString());
			vuelta.append("\n");
		}
		return vuelta.toString();
	}
	
	/*Formato para mostrar las fechas*/
	public String mostrarFecha(Calendar fecha){
		return fecha.get(Calendar.DATE) + "/" + (fecha.get(Calendar.MONTH)+1) + "/" + fecha.get(Calendar.YEAR);
	}
	
	public String toString(){
		return "Código factura: " + getCodFactura() + "\t\tFecha emision: " + mostrarFecha(getFecha()) + 
		"\nPeriodo facturacion:     " + mostrarFecha(getFechaI()) + " - " + mostrarFecha(getFechaF()) + "\n\nLlamadas realizadas: \n" + muestraLlamadas() + "\nImporte total: " + getImporte()+ "€\n\n-----------------------------------------------------------\n\n";
	}
}