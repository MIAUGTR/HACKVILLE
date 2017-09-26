package factorias;

import tarifa.FranjaHoraria;
import tarifa.Tarifa;
import tarifa.TarifaEspecial;

public class FactoriaEspecial implements InterfaceFactoriaEspecial{
	
	public Tarifa dameTarifa(TarifaEspecial opcion, Tarifa tarifa){
		
		double ESPECIAL = 0.05;
		int [] HORAINICIO = {8, 16, 22};
		int [] HORAFINAL = {12, 20, 6};
		
		switch(opcion){
			case MAÑANA:
				tarifa = new FranjaHoraria(tarifa, ESPECIAL, HORAINICIO[0], HORAFINAL[0]);
				break;
			case TARDE:
				tarifa = new FranjaHoraria(tarifa, ESPECIAL, HORAINICIO[1], HORAFINAL[1]);
				break;
			case NOCHE:
				tarifa = new FranjaHoraria(tarifa, ESPECIAL, HORAINICIO[2], HORAFINAL[2]);
				break;
			default:
				break;
		}
		return tarifa;
	}
	
}
