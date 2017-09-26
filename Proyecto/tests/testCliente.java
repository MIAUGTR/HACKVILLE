package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clientes.Cliente;
import clientes.ClienteEmpresa;
import direccion.Direccion;
import llamada.Llamada;
import tarifa.Basica;
import tarifa.Tarifa;
import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;
import factura.Factura;


public class testCliente {
	Cliente cli;
	Tarifa tarifa;
	TarifaEspecial especial;
	TarifaFinDe finde;
	Direccion direccion;
	
	@Before
	public void init() {
		tarifa = new Basica(0.10);
		direccion = new Direccion();
		direccion.direccionNueva("Borriol", "Castellon", "12006");
		cli = new ClienteEmpresa();
		cli.anyadirCliente("20956181F", "Sergiu", direccion, "al315697@uji.es", tarifa, especial, finde);
	}
	
	@Test
	public void testSetLlamada() {
		Llamada l1 = new Llamada("633849375", 2.00, new GregorianCalendar());
		Llamada l2 = new Llamada("642867549", 3.00, new GregorianCalendar());
		Llamada l3 = new Llamada("615259478", 3.00, new GregorianCalendar());

		cli.setLlamadas(l1);
		cli.setLlamadas(l2);
		cli.setLlamadas(l3);

		assertEquals(3, cli.getLlamadas().size(), 0);
	}
	
	@Test
	public void testSetFactura() {
		ArrayList<Llamada> llam = new ArrayList<Llamada>();
		llam.add(new Llamada("633849375", 2.00, new GregorianCalendar()));
		llam.add(new Llamada("642867549", 3.00, new GregorianCalendar()));
		llam.add(new Llamada("615259478", 3.00, new GregorianCalendar()));
		Factura f1 = new Factura(tarifa, new GregorianCalendar(), new GregorianCalendar(), llam, 1, 0);

		cli.setFactura(f1);

		assertEquals(1, cli.getFactura().size(), 0);
	}
	
	@Test
	public void testSetTarifa() {
		tarifa.setTarifa(4.00);
		cli.setTarifa(tarifa);
		assertEquals(4.00, cli.getTarifa().dameTarifa(), 0);
	}
	
	@After
	public void finish() {
		cli = null;
	}

}
