import java.util.ArrayList;

/*
 * Clase que implementa el algoritmo de Avance Rápido para resolver el rompecabezas.
 * @autor Jeremy Montero
 * @version 1.0
 */
public class AvanceRapido {

    private ArrayList<Pieza> piezas;
    private Tablero tablero;
    private int tamaño;

    /**
     * Constructor de la clase AvanceRapido.
     * @param piezas Lista de piezas que se utilizarán para resolver el rompecabezas.
     * @param tamaño Tamaño del tablero (tamaño x tamaño).
     * Inicializa la estructura del tablero y las piezas disponibles.
     */
    public AvanceRapido(ArrayList<Pieza> piezas, int tamaño) {
        this.piezas = piezas;
        this.tamaño = tamaño;
        this.tablero = new Tablero(tamaño);
    }

    /**
     * Método que inicia la resolución del rompecabezas utilizando el algoritmo
     * de Avance Rápido.
     * @return true si se encuentra una solución, false en caso contrario.
     */
    public boolean resolver() {
        long inicio = System.nanoTime();

        boolean resultado = backtracking();

        long fin = System.nanoTime();
        double tiempo = (fin - inicio) / 1_000_000_000.0;

        System.out.println("====== Avance Rápido ======");
        System.out.println("Solución encontrada: " + resultado);
        System.out.println("Tiempo: " + String.format("%.3f s", tiempo));
        System.out.println("Alternativas exploradas: " + tablero.getAlternativas());
        System.out.println("Comparaciones: " + tablero.getComparaciones());
        System.out.println("Asignaciones: " + tablero.getAsignaciones());
        System.out.println("Podas realizadas: " + tablero.getPodas());
        System.out.println("Instrucciones ejecutadas: " + tablero.getInstrucciones());
        System.out.println("==========================");

        if (resultado) {
            System.out.println("Tablero solución:");
            tablero.imprimirTablero();
        }
        return resultado;
    }

    /**
     * Método recursivo que implementa el algoritmo de backtracking utilizando
     * la heurística de Avance Rápido.
     * @return true si se logra completar el tablero correctamente, false en caso contrario.
     */
    private boolean backtracking() {
        Posicion pos = buscarMejorPosicion();
        if (pos == null) {
            return true;
        }
        for (Pieza pieza : piezas) {
            if (!pieza.isUsada()) {
                tablero.incrementarAlternativas();

                if (tablero.encaja(pos.fila, pos.columna, pieza)) {
                    tablero.colocarPieza(pos.fila, pos.columna, pieza);

                    if (backtracking()) {
                        return true;
                    }
                    tablero.quitarPieza(pos.fila, pos.columna);
                }
            }
        }
        return false;
    }

    /**
     * Método que busca la posición más restringida del tablero.
     * @return Un objeto Posicion con la fila y columna más restringida.
     */
    private Posicion buscarMejorPosicion() {
        Posicion mejor = null;
        int minOpciones = Integer.MAX_VALUE;

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {

                // Solo se evalúan las posiciones vacías
                if (tablero.getPieza(i, j) == null) {
                    int opciones = contarOpciones(i, j);

                    if (opciones < minOpciones) {
                        minOpciones = opciones;
                        mejor = new Posicion(i, j);
                    }

                    // Poda fuerte: si una posición no tiene ninguna opción válida
                    // no tiene sentido seguir explorando
                    if (minOpciones == 0) {
                        return mejor;
                    }
                }
            }
        }
        return mejor;
    }

    /**
     * Método que cuenta cuántas piezas no usadas pueden colocarse en una posición dada.
     * @param fila Fila de la posición a evaluar.
     * @param columna Columna de la posición a evaluar.
     * @return Cantidad de piezas que encajan en esa posición.
     */
    private int contarOpciones(int fila, int columna) {
        int count = 0;

        for (Pieza pieza : piezas) {
            if (!pieza.isUsada()) {
                if (tablero.encaja(fila, columna, pieza)) {
                    count++;
                }
            }
        }
        return count;
    }
}
