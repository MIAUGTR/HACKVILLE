package comun;

import java.net.*;
import java.io.*;

public class MySocketStream extends Socket{
	private Socket socket;
	private BufferedReader entrada;
	private PrintWriter salida;
	
	public MySocketStream(String maquinaAceptadora, int puertoAceptador) throws SocketException, IOException{
		socket = new Socket(maquinaAceptadora, puertoAceptador);
		establecerFlujos();
	}
	
	public MySocketStream(Socket socket) throws IOException{
		this.socket = socket;
		establecerFlujos();
	}
	
	private void establecerFlujos() throws IOException{
		//lee del socket
		InputStream flujoEntrada = socket.getInputStream();
		entrada = new BufferedReader(new InputStreamReader(flujoEntrada));
		//salida
		OutputStream flujoSalida = socket.getOutputStream();
		salida = new PrintWriter(new OutputStreamWriter(flujoSalida));
	}
	
	public void enviaMensaje(String mensaje) throws IOException{
		salida.println(mensaje);
		salida.flush(); //Se asegura que se escriben todos los datos antes del cierre del socket
	}
	
	public String recibeMensaje() throws IOException{
		String mensaje = entrada.readLine();
		return mensaje;
	}
}
