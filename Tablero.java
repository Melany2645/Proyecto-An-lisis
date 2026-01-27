import java.util.ArrayList;

/*
 * Clase que representa un tablero para colocar piezas.
 * @autor Melany Jirón Díaz
 * @version 1.0
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
     * Y marca la pieza como usada.
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

    /**
     * Método para incrementar el contador de comparaciones realizadas.
     */
    public void incrementarComparaciones() {
        comparaciones++;
    }

    /**
     * Método para incrementar el contador de podas realizadas.
     */
    public void incrementarPodas() {
        podas++;
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

    // Probar
    /**
     * Método para imprimir el rompecabezas desordenado.
     * Coloca las piezas en orden secuencial para visualización.
     * @param piezas Lista de piezas a mostrar.
     */
    public void imprimirRompecabezasDesordenado(ArrayList<Pieza> piezas) {
        // Crear una copia del tablero para mostrar el rompecabezas desordenado
        Pieza[][] tableroTemp = new Pieza[tamaño][tamaño];
        
        // Colocar las piezas en orden secuencial
        int indice = 0;
        for (int i = 0; i < tamaño && indice < piezas.size(); i++) {
            for (int j = 0; j < tamaño && indice < piezas.size(); j++) {
                tableroTemp[i][j] = piezas.get(indice);
                indice++;
            }
        }
        
        // Mostrar el tablero
        for (int i = 0; i < tamaño; i++) {
            for (int linea = 0; linea < 3; linea++) {
                for (int j = 0; j < tamaño; j++) {
                    if (tableroTemp[i][j] != null) {
                        System.out.print(tableroTemp[i][j].visualizacionLineas()[linea] + " ");
                    } else {
                        imprimirVacio(linea);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Método para imprimir el tablero en la consola.
     * Representación visual de las piezas y espacios vacíos.
     */
    public void imprimirTablero() {

        for (int i = 0; i < tamaño; i++) {

            for (int linea = 0; linea < 3; linea++) {

                for (int j = 0; j < tamaño; j++) {

                    if (tablero[i][j] != null) {
                        System.out.print(tablero[i][j].visualizacionLineas()[linea] + " ");
                    } else {
                        imprimirVacio(linea);
                    }
                }

                System.out.println();
            }

            System.out.println();
        }
    }

    /**
     * Método auxiliar para imprimir una representación visual de una posición vacía en el tablero.
     * @param linea Línea actual (0, 1 o 2) para determinar qué parte de la pieza vacía imprimir.
     */
    private void imprimirVacio(int linea) {

        if (linea == 0 || linea == 2) {
            System.out.print("  --   ");
        } else {
            System.out.print("--     -- ");
        }
    }


    /**
     * Obtiene la pieza que se encuentra en una posición específica del tablero.
     * @return La pieza ubicada en esa posición del tablero. 
     */
    public Pieza getPieza(int fila, int columna) {
        return tablero[fila][columna];
    }
}
