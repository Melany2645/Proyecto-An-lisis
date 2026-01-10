import java.util.ArrayList;

public class FuerzaBruta {
    private ArrayList<Pieza> piezas;
    private Tablero tablero;
    private int tamaño;

    private boolean solucionEncontrada;

    public FuerzaBruta(ArrayList<Pieza> piezas, int tamaño) {
        this.piezas = piezas;
        this.tamaño = tamaño;
        this.tablero = new Tablero(tamaño);
        this.solucionEncontrada = false;
    }

    public boolean resolver() {
        long inicio = System.nanoTime();

        boolean resultado = backtracking(0);

        long fin = System.nanoTime();
        double duracion = (fin - inicio) / 1_000_000.0; // Duración en milisegundos

        System.out.println("====== Fuerza Bruta ======");
        System.out.println("Solución encontrada: " + resultado);
        System.out.println("Duración: " + duracion + " ms");
        System.out.println("Llamadas recursivas: " + tablero.getLlamadasRecursivas());
        System.out.println("Comparaciones: " + tablero.getComparaciones());
        System.out.println("Asignaciones: " + tablero.getAsignaciones());
        System.out.println("Podas realizadas: " + tablero.getPodas());

        if (resultado) {
            System.out.println("Tablero solución:");
            tablero.imprimirTablero();
        }

        return resultado;
    }

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
                tablero.incrementarRecursivas();
                if (tablero.encaja(fila, columna, pieza)) {
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