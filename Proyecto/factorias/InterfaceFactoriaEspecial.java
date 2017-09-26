package factorias;

import tarifa.Tarifa;
import tarifa.TarifaEspecial;

public interface InterfaceFactoriaEspecial {
	
	Tarifa dameTarifa(TarifaEspecial opcion, Tarifa tarifa);

}
