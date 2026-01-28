/**
 * Clase auxiliar que valida si una pieza puede colocarse en una posición específica.
 * @author Melany Jirón Díaz
 * @version 1.0
 */
public class ValidadorPosicion {
    
    private Tablero tablero;
    
    /**
     * Constructor que recibe el tablero a validar.
     * @param tablero Tablero donde se realizarán las validaciones.
     */
    public ValidadorPosicion(Tablero tablero) {
        this.tablero = tablero;
    }
    
    /**
     * Valida si una pieza encaja en una posición del tablero.
     * Verifica restricciones con piezas adyacentes (arriba e izquierda).
     * @param fila Fila de la posición.
     * @param columna Columna de la posición.
     * @param pieza Pieza a validar.
     * @return true si la pieza encaja, false en caso contrario.
     */
    public boolean encaja(int fila, int columna, Pieza pieza) {
        // Validar con pieza arriba
        tablero.incrementarComparaciones();
        if (fila > 0 && tablero.getPieza(fila - 1, columna) != null) {
            tablero.incrementarComparaciones();
            Pieza piezaArriba = tablero.getPieza(fila - 1, columna);
            tablero.incrementarAsignaciones();
            if (piezaArriba.getAbajo() != pieza.getArriba()) {
                return false;
            }
        }
        
        // Validar con pieza a la izquierda
        tablero.incrementarComparaciones();
        if (columna > 0 && tablero.getPieza(fila, columna - 1) != null) {
            tablero.incrementarComparaciones();
            Pieza piezaIzquierda = tablero.getPieza(fila, columna - 1);
            tablero.incrementarAsignaciones();
            if (piezaIzquierda.getDerecha() != pieza.getIzquierda()) {
                return false;
            }
        }
        
        return true;
    }
}
