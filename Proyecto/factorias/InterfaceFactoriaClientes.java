package factorias;

import clientes.Cliente;
import menu.OpClientes;

public interface InterfaceFactoriaClientes {
	Cliente getCliente(String string);
	Cliente getClientes(OpClientes opcion);

}
