package tests;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import factorias.FactoriaEspecial;
import factorias.FactoriaFinde;
import tarifa.Basica;
import tarifa.Tarifa;
import tarifa.TarifaEspecial;
import tarifa.TarifaFinDe;

public class testFabrica {
	FactoriaEspecial espeFabri;
	FactoriaFinde findeFabri;
	Tarifa tarifa;
	@Before
	public void init() throws Exception {
		 tarifa = new Basica(0.10);
		 espeFabri = new FactoriaEspecial();
		 findeFabri = new FactoriaFinde();
	}
	
	@Test
	public void testFabricaEspecial() {
		Tarifa tarifaNueva = espeFabri.dameTarifa(TarifaEspecial.TARDE, tarifa);
		assertThat(tarifaNueva.toString().contains(0.05+""), Is.is(true));
	}
	
	@Test
	public void testFabricaFinde() {
		Tarifa tarifaNueva = findeFabri.dameTarifa(TarifaFinDe.DOMINGO, tarifa);
		assertThat(tarifaNueva.toString().contains(0.00+""), Is.is(true));
	}
}
