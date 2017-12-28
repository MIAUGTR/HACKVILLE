package cliente;

import java.io.IOException;

import comun.MySocketStream;

public class AuxiliarClienteFlota {

	private MySocketStream miSocket;
	
	public AuxiliarClienteFlota(String nombreMaquina, int puertoServidor) throws IOException{
			miSocket = new MySocketStream(nombreMaquina, puertoServidor);
			System.out.println("Conexión realizada con el servidor " + nombreMaquina
					+ " en el puerto " + puertoServidor);
	}
	
	public void enviaPeticion(String peticion) throws Exception{
		miSocket.enviaMensaje(peticion);
	}
	
	public String recibeRespuesta() throws Exception{
		String respuesta = miSocket.recibeMensaje();
		return respuesta;
	}
	
}
