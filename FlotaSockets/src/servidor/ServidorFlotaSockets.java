package servidor;

import java.io.*;
import java.net.*;
import comun.MySocketStream;

public class ServidorFlotaSockets {
	
	public static void main(String[] args){
		int puertoServidor = 15; //Puerto por defecto
		String mensaje;
		
		try{
			ServerSocket socketConexion = new ServerSocket(puertoServidor);
			System.out.println("El servidor está en funcionamiento y listo");
			while(true){//acepta conexiones ininterrumpidamente
				MySocketStream socketDatos = new MySocketStream(socketConexion.accept());
				System.out.println("Conexión aceptada");
				Thread hilo = new Thread(new HiloServidorFlota(socketDatos));
				hilo.start();
			}
		}catch (Exception e){
			
		}
	}
}
