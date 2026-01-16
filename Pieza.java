/**
 * Clase que representa una pieza del tablero con valores en sus cuatro lados.
 * @autor Melany Jirón Díaz
 * @version 1.0
 */
public class Pieza {
  
    private int arriba;
    private int derecha;
    private int abajo;
    private int izquierda;

    private boolean usada;

    /**
     * Constructor de la clase Pieza.
     * @param arriba Valor del lado superior.
     * @param derecha Valor del lado derecho.
     * @param abajo Valor del lado inferior.
     * @param izquierda Valor del lado izquierdo.
     */
    public Pieza(int arriba, int derecha, int abajo, int izquierda) {
        this.arriba = arriba;
        this.derecha = derecha;
        this.abajo = abajo;
        this.izquierda = izquierda;
        this.usada = false;
    }

    // Getters y Setter
    public int getArriba() {
        return arriba;
    }

    public int getDerecha() {
        return derecha;
    }

    public int getAbajo() {
        return abajo;
    }

    public int getIzquierda() {
        return izquierda;
    }

    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    // Representación visual en 3 líneas para impresión en el tablero
    /*
     * @return Array de Strings con la representación visual de la pieza.
     */
    public String[] visualizacionLineas() {
        String arri = String.format("%2d", arriba);
        String dere = String.format("%2d", derecha);
        String aba = String.format("%2d", abajo);
        String izql = String.format("%2d", izquierda);
        String center = usada ? "*" : " ";
        String top = String.format("  %2s   ", arri);
        String middle = String.format("%2s %s %2s", izql, center, dere);
        String bottom = String.format("  %2s   ", aba);
        return new String[] { top, middle, bottom };
    }

    @Override
    public String toString() {
        return "Pieza{" + "arriba=" + arriba + ", derecha=" + derecha +
                ", abajo=" + abajo + ", izquierda=" + izquierda + ", usada=" + usada +
                '}';
    }
}