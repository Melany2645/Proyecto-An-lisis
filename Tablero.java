/*
 * Clase que representa un tablero para colocar piezas.
 * @autor Melany Jirón Díaz
 */
public class Tablero {

    private Pieza[][] tablero; 
    private int tamaño;

    //Contadores
    private long comparaciones;
    private long asignaciones;
    private long alternativas;
    private long podas;
    private long instrucciones;

    /**
     * Constructor de la clase Tablero.
     * @param tamaño Tamaño del tablero (tamaño x tamaño).
     * Inicialización del tablero y los contadores.
     */
    public Tablero(int tamaño) {
        this.tamaño = tamaño;
        this.tablero = new Pieza[tamaño][tamaño];

        // Medición empírica
        this.comparaciones = 0;
        this.asignaciones = 0;
        this.alternativas = 0;
        this.podas = 0;
        this.instrucciones = 0;
    }

    /**
     * Método para colocar una pieza en el tablero.
     * @param fila Fila donde se colocará la pieza.
     * @param columna Columna donde se colocará la pieza.
     * @param pieza Pieza a colocar.
     */
    public void colocarPieza(int fila, int columna, Pieza pieza) {
        tablero[fila][columna] = pieza;
        asignaciones++;

        pieza.setUsada(true);
        asignaciones++;
    }

    /**
     * Método para quitar una pieza del tablero.
     * @param fila Fila de la pieza a quitar.
     * @param columna Columna de la pieza a quitar.
     */
    public void quitarPieza(int fila, int columna) {
        Pieza pieza = tablero[fila][columna];
        asignaciones++;

        if (pieza != null) {
            pieza.setUsada(false);
            asignaciones++;
            tablero[fila][columna] = null;
            asignaciones++;
        }
    }

    /**
     * Método para verificar si una pieza encaja en una posición dada del tablero.
     * @param fila Fila donde se quiere colocar la pieza.
     * @param columna Columna donde se quiere colocar la pieza.
     * @param pieza Pieza a verificar.
     * @return true si la pieza encaja, false en caso contrario.
     */
    public boolean encaja(int fila, int columna, Pieza pieza) {
        // Verificar en sentido vertical
        if (fila > 0 && tablero[fila - 1][columna] != null) {
            comparaciones++;
            Pieza piezaArriba = tablero[fila - 1][columna];
            if (piezaArriba.getAbajo() != pieza.getArriba()) {
                podas++;
                return false;
                
            }
        }

        // Verificar en sentido horizontal
        if (columna > 0 && tablero[fila][columna - 1] != null) {
            comparaciones++;
            Pieza piezaIzquierda = tablero[fila][columna - 1];
                if (piezaIzquierda.getDerecha() != pieza.getIzquierda()) {
                    podas++;
                    return false;
                }
        }
        return true;

    }
    /**
     * Método para incrementar el contador de alternativas exploradas.
     */
    public void incrementarAlternativas() {
        alternativas++;
    }

    // Getters para los contadores
    public long getComparaciones() {
        return comparaciones;
    }
    public long getAsignaciones() {
        return asignaciones;
    }
    public long getAlternativas() {
        return alternativas;
    }
    public long getPodas() {
        return podas;
    }
    public long getInstrucciones() {
        instrucciones = comparaciones + asignaciones;
        return instrucciones;
    }

    /**
     * Método para imprimir el tablero en la consola.
     * Representación visual de las piezas y espacios vacíos.
     */
    public void imprimirTablero() {
        String vacioSuperior = "  --   ";
        String vacioMedio = String.format("%2s %s %2s", "--", " ", "--");
        String vacioInferior = "  --   ";

        for (int i = 0; i < tamaño; i++) { // Filas
            StringBuilder lineaSuperior = new StringBuilder();
            StringBuilder lineaMedia = new StringBuilder();
            StringBuilder lineaInferior = new StringBuilder();

            for (int j = 0; j < tamaño; j++) { // Columnas
                if (tablero[i][j] != null) {
                    String[] lineasPieza = tablero[i][j].toVisualLines();
                    lineaSuperior.append(lineasPieza[0]).append(" ");
                    lineaMedia.append(lineasPieza[1]).append(" ");
                    lineaInferior.append(lineasPieza[2]).append(" ");
                } else {
                    lineaSuperior.append(vacioSuperior).append(" ");
                    lineaMedia.append(vacioMedio).append(" ");
                    lineaInferior.append(vacioInferior).append(" ");
                }
            }

            System.out.println(lineaSuperior.toString());
            System.out.println(lineaMedia.toString());
            System.out.println(lineaInferior.toString());
            System.out.println(); // línea en blanco entre filas, el orden más claro
        }
    }
}
