package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import clientes.Cliente;
import clientes.ClienteEmpresa;
import direccion.Direccion;
import excepciones.*;
import factura.Factura;
import llamada.Llamada;
import comun.BBDD;
import conjuntoGenerico.ConjuntoGenerico;
import tarifa.Basica;
import tarifa.Tarifa;
import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;

import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testBBDD {
	
	private static BBDD base;
	private static Cliente cliente;
	Direccion direccion;
	Tarifa tarifa;
	TarifaEspecial especial;
	TarifaFinDe finde;
	Llamada llamada;
	ArrayList<Factura> lfac;
	
	@Before
	public void init() throws ClienteExistente {
		base = new BBDD();
		direccion = new Direccion();
		direccion.direccionNueva("Borriol", "Castellón", "12006");
		tarifa = new Basica(0.10);
		cliente = new ClienteEmpresa();
		cliente.anyadirCliente("25486974T", "Andrea", direccion, "sexylady@hotmail.com", tarifa, especial, finde);
		base.setCliente(cliente);
		cliente.anyadirCliente("20956181F", "Sergiu", direccion, "al315697@uji.es", tarifa, especial, finde);
		base.setCliente(cliente);
		llamada = new Llamada("654987321", 26, new GregorianCalendar(2017, 3, 27));
	}

	@Test
	public void ManejoClientesBBDD() throws ClienteExistente, NoExisteCliente{
		try{
			base.setCliente(cliente);
			
		}catch (ClienteExistente ex){
			ex.getMessage();
		}
		assertEquals(2, base.getClientes().size(), 0);
		assertEquals(base.containsCliente(cliente.getNif()), true);
	}
	
	@Test
	public void ManejoTarifasBBDD() throws NoExisteCliente{
		try{
			tarifa = new Basica(0.5);
			base.setTarifa(cliente.getNif(), tarifa);
		}
		catch(NoExisteCliente ex){
			ex.getMessage();
		}
		assertEquals(base.getTarifa(cliente.getNif()), tarifa);
	}
	
	@Test
	public void ManejoFacturasBBDD() throws NoExisteCliente, NoExisteFactura, PeriodoIncorrecto{
			try {
				base.emitFactura("20956181F", new GregorianCalendar(2017, 3, 26), new GregorianCalendar(2017, 3, 28));
				lfac = base.muestraFacturasCliente("20956181F");
			} catch (NoExisteCliente e) {
				e.printStackTrace();
			}
			assertEquals(1,lfac.size(),0);
			}
	
	@Test
	public void testMostrarFactura() throws NoExisteCliente, PeriodoIncorrecto {
		base.addLlamada("20956181F", "657842369", 1.20, new GregorianCalendar(2017, 4, 12));
		base.addLlamada("20956181F", "666666666", 1.30, new GregorianCalendar(2017, 5, 25));
		Calendar fechaInicio = new GregorianCalendar(2017, 4, 12);
		Calendar fechaFin = new GregorianCalendar(2017, 5, 25);
		base.emitFactura("20956181F", fechaInicio, fechaFin);
		Calendar fecha = GregorianCalendar.getInstance();

		assertEquals(base.muestraFacturasCliente("20956181F").toString(),
				"[\nCódigo factura: " + 0 + "\t\tFecha emision: " + fecha.get(Calendar.DATE) + "/"
						+ (fecha.get(Calendar.MONTH) + 1) + "/" + fecha.get(Calendar.YEAR) + 
				"\nPeriodo facturacion:     " + "12" + "/"
				+ 5 + "/" + 2017  + " - " + "25" + "/"
				+ 6 + "/" + 2017  + "\n\nLlamadas realizadas: \n\nImporte total: " + 0.0+ 
				"€\n\n-----------------------------------------------------------\n\n]");


	}
	


	@Test
	public void ManejoLlamadasBBDD() throws NoExisteCliente {
		base.addLlamada("20956181F", "632157815", 2.00, new GregorianCalendar());
		assertEquals(1, ((Cliente) base.getCliente("20956181F")).getLlamadas().size(), 0);
		try{
			base.addLlamada("20943349W", "611857984", 3.00, new GregorianCalendar());
		}catch(NoExisteCliente ex){
			fail("El cliente con DNI/NIF 20943349W no existe en la base de datos.");
		}
	}
	
	@Test
	public void testBorrarCliente() throws NoExisteCliente {
		try {
			base.removeCliente("25486974T");
			assertEquals(1, base.getClientes().size(), 0);
			base.removeCliente("20956181F");
			assertEquals(0, base.getClientes().size(), 0);
		} catch (NoExisteCliente ex) {
			ex.getMessage();
		}
	}
	
	
	@Test
	public void clientesEntreFechas() throws PeriodoIncorrecto, NoExisteCliente{
		
		Calendar fechaInicio = new GregorianCalendar();
		fechaInicio = Calendar.getInstance();
		Calendar fechaFin = new GregorianCalendar(2017, 5, 30);
		
		ConjuntoGenerico<Cliente> conjunto = new ConjuntoGenerico<Cliente>();
		Collection<Cliente> clientes;
		clientes = conjunto.getConjunto(base.clientesFechas(), fechaInicio, fechaFin);
		
		assertEquals(2, clientes.size(), 0);
		
	}
	
	
	@Test
	public void llamadasClienteEntreFechas() throws PeriodoIncorrecto, NoExisteCliente, ClienteExistente{
		
		cliente.anyadirCliente("20943349W", "Sergiu", direccion, "al315697@uji.es", tarifa, especial, finde);
		base.setCliente(cliente);
		base.addLlamada("20943349W", "611857984", 3.00, new GregorianCalendar());
		
		Calendar fechaInicio = new GregorianCalendar(2017, 3, 20);
		Calendar fechaFin = new GregorianCalendar(2017, 3, 29);
		
		ConjuntoGenerico<Llamada> conjunto = new ConjuntoGenerico<Llamada>();
		Collection<Llamada> llamadas;
		llamadas = conjunto.getConjunto(base.llamadasFechas("20943349W"), fechaInicio, fechaFin);
		
		assertEquals(1, llamadas.size(), 0);
	}
	
	
	
	@After
	public void finish() {
		base = null;

	}
}