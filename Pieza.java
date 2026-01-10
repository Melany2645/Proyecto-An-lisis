public class Pieza {
  
    private int arriba;
    private int derecha;
    private int abajo;
    private int izquierda;

    private boolean usada;

    public Pieza(int arriba, int derecha, int abajo, int izquierda) {
        this.arriba = arriba;
        this.derecha = derecha;
        this.abajo = abajo;
        this.izquierda = izquierda;
        this.usada = false;
    }

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
    public String[] toVisualLines() {
        String a = String.format("%2d", arriba);
        String d = String.format("%2d", derecha);
        String b = String.format("%2d", abajo);
        String l = String.format("%2d", izquierda);
        String center = usada ? "*" : " ";
        String top = String.format("  %2s   ", a);
        String middle = String.format("%2s %s %2s", l, center, d);
        String bottom = String.format("  %2s   ", b);
        return new String[] { top, middle, bottom };
    }

    @Override
    public String toString() {
        return "Pieza{" + "arriba=" + arriba + ", derecha=" + derecha +
                ", abajo=" + abajo + ", izquierda=" + izquierda + ", usada=" + usada +
                '}';
    }
}