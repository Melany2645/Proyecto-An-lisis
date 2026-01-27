import java.util.ArrayList;
/* 
 * Clase que implementa el algoritmo de fuerza bruta para resolver un problema de colocación de piezas en un tablero.
 * Utiliza clases auxiliares:
 * - Tablero: Representa el tablero y maneja la colocación de piezas.
 * - Pieza: Representa una pieza con sus características.
 * 
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
        Runtime runtime= Runtime.getRuntime();
        runtime.gc();
        long MemoriaInicial = runtime.totalMemory() - runtime.freeMemory();

        long inicio = System.nanoTime();

        boolean resultado = backtracking(0);

        long fin = System.nanoTime();
        
        long MemoriaFinal = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = MemoriaFinal - MemoriaInicial;
        
        double tiempo = (fin - inicio) / 1_000_000_000.0; // Tiempo en segundos

        System.out.println("====== Fuerza Bruta ======");
        System.out.println("Solución encontrada: " + resultado);
        System.out.println("Duración: " + String.format("%.3f s", tiempo));
        System.out.println("Memoria usada: " + (memoriaUsada / 1024) + " KB");
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
                if (tablero.encaja(fila, columna, pieza)) { // Poda 
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