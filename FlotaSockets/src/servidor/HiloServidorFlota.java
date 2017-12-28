package servidor;

import java.io.*;
import comun.MySocketStream;
import partida.Partida;

public class HiloServidorFlota implements Runnable{
	static final int salida = 0;
	MySocketStream socketDatos;
	private Partida partida;
	private int filas;
	private int columnas;
	
	HiloServidorFlota(MySocketStream socketDatos) {
		this.socketDatos = socketDatos;
	}
	
	public void run(){
		boolean salir = false;
		String mensaje;
		int operacion;
		try{
			while(!salir){
				mensaje = socketDatos.recibeMensaje();
				operacion = Integer.parseInt(mensaje.split("#")[0]);
				System.out.println("Mensaje recibido: " + mensaje);
				switch(operacion){
					case 0:
						/** Se cierra el socket y la sesión del cliente*/
						System.out.println("Fin de la sesión");
						socketDatos.close();
						salir = true;
						break;
					case 1:
						this.filas = Integer.parseInt(mensaje.split("#")[1]);
						this.columnas = Integer.parseInt(mensaje.split("#")[2]);
						int nBarcos = Integer.parseInt(mensaje.split("#")[3]);
						this.partida = new Partida( filas, columnas, nBarcos);
						break;
					case 2:
						this.filas = Integer.parseInt(mensaje.split("#")[1]);
						this.columnas = Integer.parseInt(mensaje.split("#")[2]);
						mensaje = partida.pruebaCasilla(filas, columnas);
						this.socketDatos.enviaMensaje(mensaje);
						break;
					case 3:
						int idBarco = Integer.parseInt(mensaje.split("#")[1]);
						mensaje = partida.getBarco(idBarco);
						this.socketDatos.enviaMensaje(mensaje);
						break;
					case 4:
						mensaje = Integer.toString(partida.getSolucion().length);
						this.socketDatos.enviaMensaje(mensaje);
						break;
				}
			}
		}catch(Exception e){
			System.out.println("Falla " + e);
		}
	}
}
