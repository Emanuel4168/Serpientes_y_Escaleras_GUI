package structures;


public class Casilla {
	public int noCasilla;
	public char tipoCasilla;
	public NodoDBL<Casilla> nuevaPosicion;
	
	public Casilla(int nc, char tc, NodoDBL<Casilla> p) {
		noCasilla = nc;
		tipoCasilla = tc;
		nuevaPosicion = p;
	}
}
