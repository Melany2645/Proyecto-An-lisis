import java.util.ArrayList;

/**
 * Clase auxiliar que analiza y mantiene información sobre las posiciones
 * del tablero y sus restricciones. Optimiza el cálculo de opciones disponibles
 * para implementar eficientemente la heurística MRV.
 * 
 * @author Melany Jirón Díaz
 * @version 1.0
 */
public class AnalisisTablero {
    
    private Tablero tablero;
    private ArrayList<Pieza> piezas;
    private ValidadorPosicion validador;
    private int tamaño;
    
    /**
     * Constructor que inicializa el análisis del tablero.
     * @param tablero Tablero a analizar.
     * @param piezas Lista de piezas disponibles.
     * @param tamaño Tamaño del tablero (tamaño x tamaño).
     */
    public AnalisisTablero(Tablero tablero, ArrayList<Pieza> piezas, int tamaño) {
        this.tablero = tablero;
        this.piezas = piezas;
        this.tamaño = tamaño;
        this.validador = new ValidadorPosicion(tablero);
    }
    
    /**
     * Busca la posición más restringida del tablero (heurística MRV).
     * Retorna la posición vacía con el menor número de opciones válidas.
     * @return PosicionRestringida con la posición más restringida, o null si el tablero está lleno.
     */
    public PosicionRestringida buscarPosicionMasRestringida() {
        PosicionRestringida mejor = null;
        int minOpciones = Integer.MAX_VALUE;
        
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                // Solo evaluar posiciones vacías
                if (tablero.getPieza(i, j) == null) {
                    int opciones = contarOpcionesDisponibles(i, j);
                    
                    if (opciones < minOpciones) {
                        minOpciones = opciones;
                        mejor = new PosicionRestringida(i, j, opciones);
                        
                        // Poda: si una posición no tiene opciones, no hay solución posible
                        if (minOpciones == 0) {
                            tablero.incrementarPodas();
                            return mejor;
                        }
                    }
                }
            }
        }
        
        return mejor;
    }
    
    /**
     * Verifica si el tablero está completamente lleno.
     * @return true si todas las posiciones tienen piezas, false en caso contrario.
     */
    public boolean estaTableroLleno() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (tablero.getPieza(i, j) == null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Cuenta cuántas piezas no usadas pueden colocarse en una posición dada.
     * Itera sobre las piezas disponibles y verifica si encajan.
     * @param fila Fila de la posición a evaluar.
     * @param columna Columna de la posición a evaluar.
     * @return Número de piezas que encajan en esa posición.
     */
    public int contarOpcionesDisponibles(int fila, int columna) {
        int count = 0;
        
        for (Pieza pieza : piezas) {
            if (!pieza.isUsada() && validador.encaja(fila, columna, pieza)) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Obtiene todas las posiciones vacías del tablero ordenadas por restricción (MRV).
     * Las posiciones con menos opciones aparecen primero.
     * 
     * @return ArrayList de PosicionRestringida ordenadas de menor a mayor restricción.
     */
    public ArrayList<PosicionRestringida> obtenerPosicionesOrdenadas() {
        ArrayList<PosicionRestringida> posiciones = new ArrayList<>();
        
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (tablero.getPieza(i, j) == null) {
                    int opciones = contarOpcionesDisponibles(i, j);
                    posiciones.add(new PosicionRestringida(i, j, opciones));
                }
            }
        }
        
        // Ordenar de menor a mayor
        posiciones.sort((p1, p2) -> Integer.compare(p1.getOpcionesDisponibles(), p2.getOpcionesDisponibles()));
        
        return posiciones;
    }
    
    /**
     * Verifica si una pieza encaja en una posición específica.
     * Utiliza el validador encapsulado.
     * 
     * @param fila Fila de la posición.
     * @param columna Columna de la posición.
     * @param pieza Pieza a validar.
     * @return true si la pieza encaja, false en caso contrario.
     */
    public boolean encaja(int fila, int columna, Pieza pieza) {
        return validador.encaja(fila, columna, pieza);
    }
}
