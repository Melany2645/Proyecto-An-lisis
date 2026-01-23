import java.util.ArrayList;

public class AvanceRapido {

    private ArrayList<Pieza> piezas;
    private Tablero tablero;
    private int tamaño;

    public AvanceRapido(ArrayList<Pieza> piezas, int tamaño) {
        this.piezas = piezas;
        this.tamaño = tamaño;
        this.tablero = new Tablero(tamaño);
    }

    public boolean resolver() {
        long inicio = System.nanoTime();

        boolean resultado = backtracking();

        long fin = System.nanoTime();
        double tiempo = (fin - inicio) / 1_000_000_000.0;

        System.out.println("====== Avance Rápido ======");
        System.out.println("Solución encontrada: " + resultado);
        System.out.println("Tiempo: " + String.format("%.3f s", tiempo));
        System.out.println("Alternativas: " + tablero.getAlternativas());
        System.out.println("Comparaciones: " + tablero.getComparaciones());
        System.out.println("Asignaciones: " + tablero.getAsignaciones());
        System.out.println("Podas: " + tablero.getPodas());
        System.out.println("Instrucciones: " + tablero.getInstrucciones());
        System.out.println("==========================");

        if (resultado) {
            tablero.imprimirTablero();
        }

        return resultado;
    }

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

    private Posicion buscarMejorPosicion() {
        Posicion mejor = null;
        int minOpciones = Integer.MAX_VALUE;

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {

                // Solo posiciones vacías
                if (tablero.getPieza(i, j) == null) {
                    int opciones = contarOpciones(i, j);

                    if (opciones < minOpciones) {
                        minOpciones = opciones;
                        mejor = new Posicion(i, j);
                    }

                    if (minOpciones == 0) {
                        return mejor;
                    }
                }
            }
        }
        return mejor;
    }

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
