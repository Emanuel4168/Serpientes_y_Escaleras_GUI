package structures;


public class Casilla{
	public int noCasilla;
	public char tipoCasilla;
	public NodoDBL<Casilla> nuevaPosicion;
	public String baseImage;
	
	public Casilla(int nc, char tc, NodoDBL<Casilla> p, String baseImage) {
		noCasilla = nc;
		tipoCasilla = tc;
		nuevaPosicion = p;
		this.baseImage = baseImage;
	}
}
