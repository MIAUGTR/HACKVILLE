package factorias;

import clientes.*;
import menu.OpClientes;

public class FactoriaClientes implements InterfaceFactoriaClientes{
	public FactoriaClientes(){
		super();
	}
	
	public Cliente getClientes(OpClientes opcion){
		Cliente cliente = null;
		switch(opcion){
		case CLIENTE_PARTICULAR:
			cliente = new ClienteParticular();
			break;
		case CLIENTE_EMPRESA:
			cliente = new ClienteEmpresa();
			break;
		default:
			break;
		}
		return cliente;
	}

	@Override
	public Cliente getCliente(String string) {
		Cliente cliente;
		if (string == "Particular")
			cliente = new ClienteParticular();
		else
			cliente = new ClienteEmpresa();
		return cliente;
	}
}
