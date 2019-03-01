package structures;

import javax.swing.JLabel;

public class Casilla extends JLabel{
	public int noCasilla;
	public char tipoCasilla;
	public NodoDBL<Casilla> nuevaPosicion;
	public String baseImage;
	public ColaCir<String> players;
	
	public Casilla(int nc, char tc, NodoDBL<Casilla> p, String baseImage) {
		noCasilla = nc;
		tipoCasilla = tc;
		nuevaPosicion = p;
		this.baseImage = baseImage;
		players = new ColaCir<String>();
	}
}
