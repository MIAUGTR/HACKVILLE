package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hamcrest.core.Is;
import org.junit.Test;
import llamada.Llamada;
import tarifa.Basica;
import tarifa.Finde;
import tarifa.FranjaHoraria;
import tarifa.Tarifa;

public class testDecorador {	
	@Test
	public void testTarifaDecorada() {
	Llamada llam = new Llamada("963582147", 15.00, new GregorianCalendar(2017, 3, 27));
	Llamada llam2 = new Llamada("987654321", 20.00, new GregorianCalendar(2017,4,5));
	Tarifa basica = new Basica(0.10);
	Tarifa franja= new FranjaHoraria(basica, 0.05, llam.getFecha().get(Calendar.HOUR_OF_DAY), llam.getFecha().get(Calendar.HOUR_OF_DAY));
	Tarifa finde= new Finde(basica, 0.0, 6);
	
	
	assertEquals(1.5, basica.getCosteLlamada(llam), 0) ;
	assertEquals(0.75, franja.getCosteLlamada(llam), 0) ;
	assertThat(finde.getCosteLlamada(llam)==0.0, Is.is(false));
	assertEquals(0.0, finde.getCosteLlamada(llam2), 0); 
	}
}
