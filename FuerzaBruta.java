import java.util.ArrayList;

/*
 * Clase que implementa el algoritmo de fuerza bruta para resolver un problema de colocación de piezas en un tablero.
 * @autor Melany Jirón Díaz
 * @version 1.0
 */
public class FuerzaBruta {
    private ArrayList<Pieza> piezas;
    private Tablero tablero;
    private int tamaño;

    private boolean solucionEncontrada;

    /**
     * Constructor de la clase FuerzaBruta.
     * @param piezas Lista de piezas a colocar en el tablero.
     * @param tamaño Tamaño del tablero (tamaño x tamaño).
     * Inicialización de las variables y el tablero.
     */
    public FuerzaBruta(ArrayList<Pieza> piezas, int tamaño) {
        this.piezas = piezas;
        this.tamaño = tamaño;
        this.tablero = new Tablero(tamaño);
        this.solucionEncontrada = false;
    }

    /**
     * Método que inicia el proceso de resolución del problema utilizando fuerza bruta.
     * Mide el tiempo de ejecución y muestra estadísticas al finalizar, las distintas mediciones.
     * @return true si se encuentra una solución, false en caso contrario.
     */
    public boolean resolver() {
        long inicio = System.nanoTime();

        boolean resultado = backtracking(0);

        long fin = System.nanoTime();
        long elapsedNanos = fin - inicio;
        double duracionSeg = elapsedNanos / 1_000_000_000.0; // Duración en segundos
        double duracionMs = elapsedNanos / 1_000_000.0; // Duración en milisegundos

        System.out.println("====== Fuerza Bruta ======");
        System.out.println("Solución encontrada: " + resultado);
        System.out.println("Duración: " + String.format("%.3f s (%d ms)", duracionSeg, Math.round(duracionMs)));
        System.out.println("Alternativas exploradas: " + tablero.getAlternativas());
        System.out.println("Comparaciones: " + tablero.getComparaciones());
        System.out.println("Asignaciones: " + tablero.getAsignaciones());
        System.out.println("Podas realizadas: " + tablero.getPodas());
        System.out.println("Instrucciones ejecutadas: " + tablero.getInstrucciones());
        System.out.println("=========================");

        if (resultado) {
            System.out.println("Tablero solución:");
            tablero.imprimirTablero();
        }

        return resultado;
    }

    /**
     * Método recursivo que implementa el algoritmo de backtracking para colocar las piezas en el tablero.
     * @param pos Posición actual en el tablero (de 0 a tamaño*tamaño - 1).
     * @return true si se encuentra una solución, false en caso contrario.
     */
    private boolean backtracking(int pos) {

        // Caso base: si se han colocado todas las piezas
        if (pos == tamaño * tamaño) {
            solucionEncontrada = true;
            return true;
        }

        int fila = pos / tamaño;
        int columna = pos % tamaño;

        for (Pieza pieza : piezas) {
            if (!pieza.isUsada()) {
                tablero.incrementarAlternativas();
                if (tablero.encaja(fila, columna, pieza)) {
                    tablero.colocarPieza(fila, columna, pieza);
                    if (backtracking(pos + 1)) {
                        return true;
                    }
                    tablero.quitarPieza(fila, columna);
                }
            }
        }
        return false;
    }
}