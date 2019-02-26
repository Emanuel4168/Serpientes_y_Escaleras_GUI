package views;

import javax.swing.*;
import structures.*;
import routines.*;
import java.awt.*;
import java.awt.Event.*;

public class MainFrame extends JFrame{
	
	private static final String [] IMAGE_NAMES = {"caballo.jpg","camaleon.jpg","delfin.jpg","elefante.jpg","gato.jpg","leon.jpg",
			"murcielago.jpg","perro.jpg","pulpo.jpg","serpiente.jpg"};

	private JPanel board, header;
	private JButton btnStart,btnRestart,btnNextPlayer;
	private JLabel lblTotalPlayers, lblCurrentPlayer, lblDiceResult;
	private ListaDBL<Casilla> logicalBoard = new ListaDBL();
	int nJugadores, dado, ganador = 0;
	private NodoDBL<Casilla> []  jugadores = new NodoDBL [nJugadores];
	
	
	private MainFrame() {
		super("Serpientes y Escaleras");
		lblTotalPlayers = new JLabel("Jugadores: "); 
		lblCurrentPlayer = new JLabel("Jugadores Actual: ");
		lblDiceResult = new JLabel("Dado: ");
		
		nJugadores = Rutinas.nextInt(2, 10);
		createLogicalBoard();
		
		createHeader();
		createBoard();
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
			logicalBoard.InsertaFin(new Casilla(i,'N',null));
		
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
		return true;
	}

	private void createHeader() {
		header = new JPanel();
		btnStart = new JButton("Iniciar juego");
		btnRestart = new JButton("Reiniciar juego");
		btnNextPlayer = new JButton("Siguiente tiro");
		
		header.add(btnStart);
		header.add(btnRestart);
		header.add(btnNextPlayer);
		
		lblTotalPlayers.setText(lblTotalPlayers.getText()+" "+this.nJugadores); 
		lblCurrentPlayer.setText(lblCurrentPlayer.getText());;
		lblDiceResult.setText(lblDiceResult.getText()+" "+this.dado);
		
		header.add(lblTotalPlayers);
		header.add(lblCurrentPlayer);
		header.add(lblDiceResult);
		
		add(header);
	}
	
	private void createBoard() {
		board = new JPanel();
	}
	
}
