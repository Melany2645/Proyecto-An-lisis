public class Tablero {

    private Pieza[][] tablero;
    private int tamaño;

    //Contadores
    private long comparaciones;
    private long asignaciones;
    private long llamadasRecursivas;
    private long podas;

    public Tablero(int tamaño) {
        this.tamaño = tamaño;
        this.tablero = new Pieza[tamaño][tamaño];

        // Medición empírica
        this.comparaciones = 0;
        this.asignaciones = 0;
        this.llamadasRecursivas = 0;
        this.podas = 0;
    }

    public void colocarPieza(int fila, int columna, Pieza pieza) {
        tablero[fila][columna] = pieza;
        asignaciones++;

        pieza.setUsada(true);
        asignaciones++;
    }

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

    public boolean encaja(int fila, int columna, Pieza pieza) {
        // Ver arriba
        if (fila > 0 && tablero[fila - 1][columna] != null) {
            comparaciones++;
            Pieza piezaArriba = tablero[fila - 1][columna];
            if (piezaArriba.getAbajo() != pieza.getArriba()) {
                podas++;
                return false;
                
            }
        }

        // Ver con la izquierda
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
    public void incrementarRecursivas() {
        llamadasRecursivas++;
    }

    // Getters para los contadores
    public long getComparaciones() {
        return comparaciones;
    }
    public long getAsignaciones() {
        return asignaciones;
    }
    public long getLlamadasRecursivas() {
        return llamadasRecursivas;
    }
    public long getPodas() {
        return podas;
    }

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
