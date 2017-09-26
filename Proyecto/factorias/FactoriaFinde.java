package factorias;

import tarifa.Finde;
import tarifa.Tarifa;
import tarifa.TarifaFinDe;

public class FactoriaFinde implements InterfaceFactoriaFinde{

	public Tarifa dameTarifa(TarifaFinDe opcion, Tarifa tarifa){
		double GRATIS = 0.0;
		
		int dia = 0;
		switch(opcion){
			case SABADO:
				dia = 7;
				break;
			case DOMINGO:
				dia = 1;
				break;
			default:
				break;
		}
		tarifa = new Finde(tarifa, GRATIS, dia);
		return tarifa;
	}
}
