/**
 * Clase auxiliar que representa una posición del tablero con información
 * sobre su nivel de restricción (número de opciones disponibles).
 * Utilizada por la heurística MRV (Minimum Remaining Values).
 * 
 * @author Melany Jirón Díaz
 * @version 1.0
 */
public class PosicionRestringida {
    
    private int fila;
    private int columna;
    private int opcionesDisponibles;
    
    /**
     * Constructor que crea una posición con su información de restricción.
     * @param fila Fila de la posición.
     * @param columna Columna de la posición.
     * @param opcionesDisponibles Número de piezas que pueden colocarse en esta posición.
     */
    public PosicionRestringida(int fila, int columna, int opcionesDisponibles) {
        this.fila = fila;
        this.columna = columna;
        this.opcionesDisponibles = opcionesDisponibles;
    }
    
    /**
     * Obtiene la fila de la posición.
     * @return Número de fila.
     */
    public int getFila() {
        return fila;
    }
    
    /**
     * Obtiene la columna de la posición.
     * @return Número de columna.
     */
    public int getColumna() {
        return columna;
    }
    
    /**
     * Obtiene el número de piezas que pueden colocarse en esta posición.
     * @return Número de opciones disponibles.
     */
    public int getOpcionesDisponibles() {
        return opcionesDisponibles;
    }
    
    /**
     * Actualiza el número de opciones disponibles.
     * @param opcionesDisponibles Nuevo número de opciones.
     */
    public void setOpcionesDisponibles(int opcionesDisponibles) {
        this.opcionesDisponibles = opcionesDisponibles;
    }
    
    @Override
    public String toString() {
        return "Pos[" + fila + "," + columna + "]=" + opcionesDisponibles + " opciones";
    }
}
