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

        //Empirica 
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
        String nullTop = "  --   ";
        String nullMiddle = String.format("%2s %s %2s", "--", " ", "--");
        String nullBottom = "  --   ";

        for (int i = 0; i < tamaño; i++) {
            StringBuilder line0 = new StringBuilder();
            StringBuilder line1 = new StringBuilder();
            StringBuilder line2 = new StringBuilder();

            for (int j = 0; j < tamaño; j++) {
                if (tablero[i][j] != null) {
                    String[] lines = tablero[i][j].toVisualLines();
                    line0.append(lines[0]).append(" ");
                    line1.append(lines[1]).append(" ");
                    line2.append(lines[2]).append(" ");
                } else {
                    line0.append(nullTop).append(" ");
                    line1.append(nullMiddle).append(" ");
                    line2.append(nullBottom).append(" ");
                }
            }

            System.out.println(line0.toString());
            System.out.println(line1.toString());
            System.out.println(line2.toString());
            System.out.println(); // línea en blanco entre filas
        }
    }
}
