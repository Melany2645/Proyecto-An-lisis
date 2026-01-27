import java.util.ArrayList;

/**
 * Clase que implementa el algoritmo MRV(Heucarística)
 * para resolver el rompecabezas de forma eficiente.
 * 
 * Utiliza clases auxiliares:
 * - AnalisisTablero: Gestiona el análisis de restricciones
 * - ValidadorPosicion: Valida si una pieza encaja
 * - PosicionRestringida: Representa una posición con su información de restricción
 * 
 * @author Jeremy Montero y Melany Jirón Díaz
 * @version 2.0
 */
public class AvanceRapido {

    private ArrayList<Pieza> piezas;
    private Tablero tablero;
    private int tamaño;
    private AnalisisTablero analisis;

    /**
     * Constructor de la clase AvanceRapido.
     * 
     * @param piezas Lista de piezas que se utilizarán para resolver el rompecabezas.
     * @param tamaño Tamaño del tablero (tamaño x tamaño).
     */
    public AvanceRapido(ArrayList<Pieza> piezas, int tamaño) {
        this.piezas = piezas;
        this.tamaño = tamaño;
        this.tablero = new Tablero(tamaño);
        this.analisis = new AnalisisTablero(tablero, piezas, tamaño);
    }

    /**
     * Método que inicia la resolución del rompecabezas utilizando la heurística MRV.
     * Mide el tiempo de ejecución y reporta estadísticas detalladas.
     * @return true si se encuentra una solución, false en caso contrario.
     */
    public boolean solucionAR() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
        
        long inicio = System.nanoTime();

        boolean resultado = backtrackingMRV();

        long fin = System.nanoTime();
        
        long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaFinal - memoriaInicial;
        
        double tiempo = (fin - inicio) / 1_000_000_000.0;

        System.out.println("====== Heurística MRV (Minimum Remaining Values) ======");
        System.out.println("Solución encontrada: " + resultado);
        System.out.println("Duración: " + String.format("%.3f s", tiempo));
        System.out.println("Memoria usada: " + (memoriaUsada / 1024) + " KB");
        //System.out.println("Alternativas exploradas: " + tablero.getAlternativas());
        System.out.println("Comparaciones: " + tablero.getComparaciones());
        System.out.println("Asignaciones: " + tablero.getAsignaciones());
        //System.out.println("Podas realizadas: " + tablero.getPodas());
        System.out.println("Instrucciones ejecutadas: " + tablero.getInstrucciones());
        System.out.println("============================================");

        if (resultado) {
            System.out.println("Tablero solución:");
            tablero.imprimirTablero();
        }
        return resultado;
    }

    /**
     * Método recursivo que implementa el algoritmo de backtracking con heurística MRV.
     * Selecciona la posición más restringida (con menos opciones) y prueba colocar piezas en ella.
     * @return true si se logra completar el tablero correctamente, false en caso contrario.
     */
    private boolean backtrackingMRV() {
        // Verificar si el tablero está completamente lleno (caso base)
        if (analisis.estaTableroLleno()) {
            return true;
        }
        
        // Buscar la posición más restringida
        PosicionRestringida posicion = analisis.buscarPosicionMasRestringida();
        
        // Si no hay posición, significa que no hay posiciones vacías (tablero lleno)
        // Este caso ya fue manejado arriba, pero este es un checkpoint adicional
        if (posicion == null) {
            return false;
        }
        
        // Si la posición tiene 0 opciones, se detectó un conflicto
        if (posicion.getOpcionesDisponibles() == 0) {
            return false;
        }
        
        // Intentar colocar cada pieza disponible en esta posición
        for (Pieza pieza : piezas) {
            if (!pieza.isUsada()) {
                tablero.incrementarAlternativas(); // Alternativas

                // Validar si la pieza encaja
                if (analisis.encaja(posicion.getFila(), posicion.getColumna(), pieza)) { // Comparaciones
                    // Colocar la pieza y continuar recursivamente
                    tablero.colocarPieza(posicion.getFila(), posicion.getColumna(), pieza); // Asignaciones

                    if (backtrackingMRV()) {
                        return true;
                    }
                    
                    // Backtrack: quitar la pieza
                    tablero.quitarPieza(posicion.getFila(), posicion.getColumna()); // Asignaciones
                }
            }
        }
        
        return false;
    }
}