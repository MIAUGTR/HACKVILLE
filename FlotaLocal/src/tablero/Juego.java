package tablero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Juego {

	/**
	 * Implementa el juego 'Hundir la flota' mediante una interfaz gráfica (GUI)
	 */

	/** Parametros por defecto de una partida */
	public static final int NUMFILAS=8, NUMCOLUMNAS=8, NUMBARCOS=6;

	private GuiTablero guiTablero = null;			// El juego se encarga de crear y modificar la interfaz gráfica
	private Partida partida = null;                 // Objeto con los datos de la partida en juego
	
	/** Parametros para el menu */
	private JMenuBar barraMenu;						//Crea la barra del menu
	private JMenu menu;								//Crea el menu
	private JMenuItem nuevaPartida, solucion, salir; //Crea las opciones del menu
	
	/** Atributos de la partida guardados en el juego para simplificar su implementación */
	private int quedan = NUMBARCOS, disparos = 0;

	/**
	 * Programa principal. Crea y lanza un nuevo juego
	 * @param args
	 */
	public static void main(String[] args) {
		Juego juego = new Juego();
		juego.ejecuta();
	} // end main

	/**
	 * Lanza una nueva hebra que crea la primera partida y dibuja la interfaz grafica: tablero
	 */
	private void ejecuta() {
		// Instancia la primera partida
		partida = new Partida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				guiTablero = new GuiTablero(NUMFILAS, NUMCOLUMNAS);
				guiTablero.dibujaTablero();
			}
		});
	} // end ejecuta

	/******************************************************************************************/
	/*********************  CLASE INTERNA GuiTablero   ****************************************/
	/******************************************************************************************/
	private class GuiTablero {

		private int numFilas, numColumnas;

		private JFrame frame = null;        // Tablero de juego
		private JLabel estado = null;       // Texto en el panel de estado
		private JButton buttons[][] = null; // Botones asociados a las casillas de la partida

		/**
         * Constructor de una tablero dadas sus dimensiones
         */
		GuiTablero(int numFilas, int numColumnas) {
			this.numFilas = numFilas;
			this.numColumnas = numColumnas;
			frame = new JFrame("Hundir la Flota");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}

		/**
		 * Dibuja el tablero de juego y crea la partida inicial
		 */
		public void dibujaTablero() {
			anyadeMenu();
			anyadeGrid(numFilas, numColumnas);		
			anyadePanelEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);		
			frame.setSize(300, 300);
			frame.setVisible(true);	
		} // end dibujaTablero

		/**
		 * Anyade el menu de opciones del juego y le asocia un escuchador
		 */
		private void anyadeMenu() {

			barraMenu = new JMenuBar();	//Inicializa la barra de menu
			frame.setJMenuBar(barraMenu);	//Añade el menu a la ventana
			menu = new JMenu("Opciones");	//Inicializa el menú en sí
			barraMenu.add(menu);	//Se añade el menu a la barra de menu
			
			nuevaPartida = new JMenuItem("Nueva Partida"); 	//Se instancian las opciones del menu y se añaden sus 
			nuevaPartida.addActionListener(new MenuListener()); //respectivos listeners y se añaden al menu
			menu.add(nuevaPartida);
			solucion = new JMenuItem("Mostrar Solución");
			solucion.addActionListener(new MenuListener());
			menu.add(solucion);
			salir = new JMenuItem ( "Salir");
			salir.addActionListener(new MenuListener());
			menu.add(salir);
			
			
		} // end anyadeMenu

		/**
		 * Anyade el panel con las casillas del mar y sus etiquetas.
		 * Cada casilla sera un boton con su correspondiente escuchador
		 * @param nf	numero de filas
		 * @param nc	numero de columnas
		 */
		private void anyadeGrid(int nf, int nc) {

			JPanel panelBotones = new JPanel();		//Creamos un panel con un diseño en altura con una unidad más y una unidad en cada lateral
			panelBotones.setLayout(new GridLayout(nf + 1, nc + 2, 0, 0));
			
			panelBotones.add(new JLabel(""));	//Añadimos los números al panel
			for(int i = 0; i < nf; i++){
				panelBotones.add(new JLabel(Integer.toString(i + 1), JLabel.CENTER));
			}
			panelBotones.add(new JLabel(""));
			
			/** Asignamos el tamaño a la matriz de botones, añadimos al panel las letras creadas a partir del codigo ASCII de los caracteres. Por cada pasada del for se añaden los botones al panel.
			 * Finalmente se añade otra etiqueta con las letras al final del panel.
			 */
			buttons = new JButton[nf][nc];		
			String letras = "";
			for(int i = 0; i < nf; i++){
				letras = String.valueOf((char)(65 + i)); 
				panelBotones.add(new JLabel(letras, JLabel.CENTER));
				for( int j = 0; j < nc; j++){
					JButton boton = new JButton();
					boton.putClientProperty("FILA", i); //A cada botón le asignamos la fila y la columna en la que se encuentran
					boton.putClientProperty("COLUMNA", j);
					boton.addActionListener(new ButtonListener());
					buttons[i][j] = boton;
					panelBotones.add(buttons[i][j]);	
				}
				panelBotones.add(new JLabel(letras, JLabel.CENTER));
			}

			frame.getContentPane().add(panelBotones, BorderLayout.CENTER);
			
		} // end anyadeGrid


		/**
		 * Anyade el panel de estado al tablero
		 * @param cadena	cadena inicial del panel de estado
		 */
		private void anyadePanelEstado(String cadena) {	
			JPanel panelEstado = new JPanel();
			estado = new JLabel(cadena);
			panelEstado.add(estado);
			// El panel de estado queda en la posición SOUTH del frame
			frame.getContentPane().add(panelEstado, BorderLayout.SOUTH);
		} // end anyadePanel Estado

		/**
		 * Cambia la cadena mostrada en el panel de estado
		 * @param cadenaEstado	nuevo estado
		 */
		public void cambiaEstado(String cadenaEstado) {
			estado.setText(cadenaEstado);
		} // end cambiaEstado

		/**
		 * Muestra la solucion de la partida y marca la partida como finalizada
		 */
		public void muestraSolucion() {
            
			/** Pintamos todos los botones en CYAN y los que pertenecen a algún barco se pintan en rojo usando el método pintaBarcoHundido*/
			for(int i = 0; i < NUMFILAS; i++) {
				for(int j = 0; j < NUMCOLUMNAS; j++) {
					JButton b = buttons[i][j];
					pintaBoton(b, Color.CYAN);
					b.setEnabled(false);
				}
			}
			String sol[] = partida.getSolucion();
			for (String item : sol){
				guiTablero.pintaBarcoHundido(item);
			}
			
			
			
		} // end muestraSolucion


		/**
		 * Pinta un barco como hundido en el tablero
		 * @param cadenaBarco	cadena con los datos del barco codifificados como
		 *                      "filaInicial#columnaInicial#orientacion#tamanyo"
		 */
		public void pintaBarcoHundido(String cadenaBarco) {
            
			/** Restamos los barcos que quedan. Sacamos los datos del barco hundido y pintamos los botones según la orientación 
			 * que tiene el barco*/
			quedan--;
			String barco [] = cadenaBarco.split("#");
    		int filIni = Integer.parseInt(barco[0]);
    		int colIni = Integer.parseInt(barco[1]);
    		char orient = barco[2].charAt(0);
    		int tam = Integer.parseInt(barco[3]);
    		
			JButton boton = buttons[filIni][colIni];
    		if(orient == 'H'){
    			for (int i = 0; i < tam; i++){
    				boton = buttons[filIni][colIni + i];
    				pintaBoton(boton, Color.RED);
    			}
    		}else{
    			for (int i = 0; i < tam; i++){
    				boton = buttons[filIni + i][colIni];
    				pintaBoton(boton, Color.RED);
    			}
    		}
			
		} // end pintaBarcoHundido

		/**
		 * Pinta un botón de un color dado
		 * @param b			boton a pintar
		 * @param color		color a usar
		 */
		public void pintaBoton(JButton b, Color color) {
			b.setBackground(color);
			// El siguiente código solo es necesario en Mac OS X
			b.setOpaque(true);
			b.setBorderPainted(false);
		} // end pintaBoton

		/**
		 * Limpia las casillas del tablero pintándolas del gris por defecto
		 */
		public void limpiaTablero() {
			for (int i = 0; i < numFilas; i++) {
				for (int j = 0; j < numColumnas; j++) {
					buttons[i][j].setBackground(null);
					buttons[i][j].setOpaque(true);
					buttons[i][j].setBorderPainted(true);
				}
			}
		} // end limpiaTablero

		/**
		 * 	Destruye y libera la memoria de todos los componentes del frame
		 */
		public void liberaRecursos() {
			frame.dispose();
		} // end liberaRecursos


	} // end class GuiTablero

	/******************************************************************************************/
	/*********************  CLASE INTERNA MenuListener ****************************************/
	/******************************************************************************************/

	/**
	 * Clase interna que escucha el menu de Opciones del tablero
	 * 
	 */
	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
            
			if(e.getSource() == nuevaPartida){	//Crea una nueva instancia de juego
				guiTablero.liberaRecursos();
				guiTablero.limpiaTablero();
				disparos = 0;
				quedan = NUMBARCOS;
				ejecuta();
				
			
			}
			
			if(e.getSource() == solucion){	//Muestra la solucion
				guiTablero.muestraSolucion();
			}
			
			if(e.getSource() == salir){	//Sale del juego
				guiTablero.liberaRecursos();
			}
			
		} // end actionPerformed

	} // end class MenuListener



	/******************************************************************************************/
	/*********************  CLASE INTERNA ButtonListener **************************************/
	/******************************************************************************************/
	/**
	 * Clase interna que escucha cada uno de los botones del tablero
	 * Para poder identificar el boton que ha generado el evento se pueden usar las propiedades
	 * de los componentes, apoyandose en los metodos putClientProperty y getClientProperty
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
            
			if (quedan != 0){	/**Si quedan barcos pinta los botones*/
				disparos++;
				JButton listener = (JButton) e.getSource();
				/**Sacamos la posición del botón dentro de la matriz buttons*/
				int fila = (int) listener.getClientProperty("FILA");	
				int columna = (int) listener.getClientProperty("COLUMNA");
				int estado = partida.pruebaCasilla(fila, columna);	//Asignamos el valor retornado por pruebaCasilla

				/** Todos los botones pulsados cambian al color y se desactivan*/
				if (estado == partida.AGUA){
					guiTablero.pintaBoton(listener, Color.CYAN);
				}else if (estado == partida.TOCADO){
					guiTablero.pintaBoton(listener, Color.ORANGE);
				}else if (estado == partida.HUNDIDO){
					
					//No hace nada
				}else{
					String cadenaBarco = partida.getBarco(estado);
					guiTablero.pintaBarcoHundido(cadenaBarco);
					
				}
				guiTablero.cambiaEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);
				if (quedan == 0) {
					guiTablero.cambiaEstado("GAME OVER en " + disparos + " disparos");
				}
			}
			
		
        } // end actionPerformed

	} // end class ButtonListener



} // end class Juego
