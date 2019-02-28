package views;

import javax.swing.*;
import structures.*;
import routines.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener{
	
	private static final String [] IMAGE_NAMES = {"caballo.jpg","camaleon.jpg","delfin.jpg","elefante.jpg","gato.jpg","leon.jpg",
			"murcielago.jpg","perro.jpg","pulpo.jpg","persona.jpg"};

	private JPanel board, header;
	private JButton btnStart,btnRestart,btnNextPlayer;
	private JLabel lblTotalPlayers, lblCurrentPlayer, lblDiceResult;
	private ListaDBL<Casilla> logicalBoard = new ListaDBL();
	int nJugadores, dado, ganador = 0, jugadorActual=0;
	private NodoDBL<Casilla> []  jugadores = new NodoDBL [nJugadores];
	private boolean avanza = true;
	
	
	private MainFrame() {
		super("Serpientes y Escaleras");
		lblTotalPlayers = new JLabel("Jugadores: "); 
		lblCurrentPlayer = new JLabel("Jugador Actual: ");
		lblDiceResult = new JLabel("Dado: ");
		nJugadores = Rutinas.nextInt(2, 10);
		jugadores = new NodoDBL [nJugadores];
		createLogicalBoard();
		
		createHeader();
		createBoard();
		addListenrers();
		setSize(500,500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String [] a) {
		new MainFrame();
	}
	
	private void createLogicalBoard() {
		for(int i = 1; i <= 100; i++) 
			logicalBoard.InsertaFin(new Casilla(i,'N',null,"casilla.png"));
		
		for(byte i =0; i<5; i++)
			if(!crearEscalera())
				i-=1;
		
		for(byte i =0; i<5; i++)
			if(!crearSerpiente())
				i-=1;
	}
	
	private boolean crearSerpiente() {
		NodoDBL<Casilla> inicio = logicalBoard.getFrente();
		NodoDBL<Casilla> fin;
		int casillaInicio = Rutinas.nextInt(30, 95);
		int avance = Rutinas.nextInt(5,20);
		for(byte i =0; i < casillaInicio; inicio=inicio.getSig(), i++);
		if(inicio.Info.tipoCasilla != 'N')
			return false;
		fin = inicio;
		
		for(byte i =0; i < avance; i++) 
			fin =  fin.getAnt();
		
		
		if(fin.Info.tipoCasilla != 'N')
			return false;
		
		inicio.Info.tipoCasilla = 'S';
		inicio.Info.nuevaPosicion = fin;
		fin.Info.tipoCasilla = 'T';
		inicio.Info.baseImage = "serpienteInicio.jpg";
		fin.Info.baseImage = "serpienteFin.jpg";
		return true;
	}

	private boolean crearEscalera() {
		NodoDBL<Casilla> inicio = logicalBoard.getFrente();
		NodoDBL<Casilla> fin;
		int casillaInicio = Rutinas.nextInt(15, 70);
		int avance = Rutinas.nextInt(5,20);
		for(byte i =0; i < casillaInicio; inicio=inicio.getSig(), i++);
		if(inicio.Info.tipoCasilla != 'N')
			return false;
		fin = inicio;
		
		for(byte i =0; i < avance;  i++) 
			fin = fin.getSig();
		
		if(fin.Info.tipoCasilla != 'N')
			return false;
		
		inicio.Info.tipoCasilla = 'E';
		inicio.Info.nuevaPosicion = fin;
		fin.Info.tipoCasilla = 'T';
		inicio.Info.baseImage = "escaleraInicio.jpg";
		fin.Info.baseImage = "escaleraFin.jpg";
		return true;
	}

	private void createHeader() {
		header = new JPanel(new GridLayout(0,3));
		btnStart = new JButton("Iniciar juego");
		btnRestart = new JButton("Reiniciar juego");
		btnNextPlayer = new JButton("Siguiente tiro");
		btnRestart.setEnabled(false);
		btnNextPlayer.setEnabled(false);
		
		header.add(btnStart);
		header.add(btnRestart);
		header.add(btnNextPlayer);
		
		lblTotalPlayers.setText(lblTotalPlayers.getText()+" "+this.nJugadores); 
		lblCurrentPlayer.setText(lblCurrentPlayer.getText());
		lblDiceResult.setText(lblDiceResult.getText()+" "+this.dado);
		
		header.add(lblTotalPlayers);
		header.add(lblCurrentPlayer);
		header.add(lblDiceResult);
		
		add(header,BorderLayout.NORTH);
	}
	
	private void createBoard() {
		board = new JPanel(new GridLayout(10,10));
		NodoDBL<Casilla> iterator = logicalBoard.getFrente(),iterSnake  = logicalBoard.getFrente(),iterStair,aux;
		for(int i = 0; i < logicalBoard.Length(); i++) {
			iterator.Info.setIcon(Rutinas.changeSize(iterator.Info.baseImage, 40, 40)); 
			board.add(iterator.Info);
			iterator = iterator.getSig();
		}
		add(board,BorderLayout.CENTER);
	}
	
	private void addListenrers() {
		btnStart.addActionListener(this);
		btnRestart.addActionListener(this);
		btnNextPlayer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnOnClick =  (JButton) e.getSource();
		
		if(btnOnClick == btnStart) {
			btnNextPlayer.setEnabled(true);
			return;
		}
		if(btnOnClick == btnRestart) {
			logicalBoard = null;
			logicalBoard = new ListaDBL<Casilla>();
			createLogicalBoard();
			createBoard();
			update(getGraphics());
			btnRestart.setEnabled(false);
			btnNextPlayer.setEnabled(false);
			
			return;
		}
		if(btnOnClick == btnNextPlayer) {
			if(jugadorActual >= nJugadores)
				jugadorActual = 0;
			Casilla casillaInicio;
			String imagenInicio = null;
			dado = Rutinas.nextInt(2,12);
			lblDiceResult.setText(lblDiceResult.getText().substring(0,6)+dado);
			lblCurrentPlayer.setText(lblCurrentPlayer.getText().substring(0,16)+(jugadorActual+1));
			ImageIcon playerIcon = Rutinas.changeSize(IMAGE_NAMES[jugadorActual], 40, 40);
			
			if(jugadores[jugadorActual] == null) {
				jugadores[jugadorActual] = logicalBoard.getFrente();
				dado--;
			}//else
				//casillas[0].setIcon(Rutinas.changeSize(jugadores[jugadorActual].Info.baseImage, 40, 40));
			imagenInicio = jugadores[jugadorActual].Info.baseImage;
			casillaInicio = jugadores[jugadorActual].Info;
			for(byte j = 0; j < dado; j++) {
				if(jugadores[jugadorActual].getSig() == null)
					avanza = false;
				jugadores[jugadorActual] = avanza? jugadores[jugadorActual].getSig():jugadores[jugadorActual].getAnt();
			}
			jugadores[jugadorActual].Info.setIcon(playerIcon);
			casillaInicio.setIcon(Rutinas.changeSize(imagenInicio,40,40));
			
			imagenInicio = jugadores[jugadorActual].Info.baseImage;
			casillaInicio = jugadores[jugadorActual].Info;
		
//			try {
//				Thread.sleep(300);
//			}catch(Exception ex) {}
			
			if(jugadores[jugadorActual].Info.tipoCasilla == 'E') {
				jugadores[jugadorActual] = jugadores[jugadorActual].Info.nuevaPosicion;
				try {
					Thread.sleep(1000);
				}catch(Exception ex) {}
				jugadores[jugadorActual].Info.setIcon(playerIcon);
				casillaInicio.setIcon(Rutinas.changeSize(imagenInicio,40,40));
			}
			
			if(jugadores[jugadorActual].Info.tipoCasilla == 'S') {
				jugadores[jugadorActual] = jugadores[jugadorActual].Info.nuevaPosicion;
				try {
					Thread.sleep(1000);
				}catch(Exception ex) {}
				jugadores[jugadorActual].Info.setIcon(playerIcon);
				casillaInicio.setIcon(Rutinas.changeSize(imagenInicio,40,40));
			}
			
			if(jugadores[jugadorActual].Info.noCasilla == 100) {
				ganador = jugadorActual;
				finDelJuego();
			}
			
			avanza = true;
			jugadorActual++;
			return;
		}
	}

	private void finDelJuego() {
		btnNextPlayer.setEnabled(false);
		btnRestart.setEnabled(true);
		JOptionPane.showMessageDialog(null,"el ganador es: "+IMAGE_NAMES[ganador].substring(0,IMAGE_NAMES[ganador].length()-4));
	}
		
	
}
	
