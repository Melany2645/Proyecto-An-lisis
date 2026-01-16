import java.util.ArrayList;
import java.util.Random;

/*
 * Clase que genera piezas para un tablero de tamaño dado con valores aleatorios.
 * Asegura que las piezas generadas tengan una solución válida.
 * @autor Melany Jirón Díaz
 * @version 1.0
 */
public class Piezas {
    private ArrayList<Pieza> piezas;
    private int tamaño;
    private int valorMaximo;
    private Random random;

    /**
     * Constructor de la clase Piezas.
     * @param tamaño Tamaño del tablero (tamaño x tamaño).
     * @param valorMaximo Valor máximo para los lados de las piezas.
     * Inicializa la lista de piezas y genera las piezas con solución.
     */
    public Piezas(int tamaño, int valorMaximo) {
        this.tamaño = tamaño;
        this.valorMaximo = valorMaximo;
        this.piezas = new ArrayList<>();
        this.random = new Random();

        piezasConSolucion();
    }

    /*
     * Método que genera piezas asegurando que haya una solución válida para el tablero.
     * Cada pieza se crea de manera que sus lados coincidan con las piezas adyacentes.
     */
    private void piezasConSolucion() {

        Pieza[][] tablero = new Pieza[tamaño][tamaño];

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {

                int arriba;
                int izquierda;

                if (i == 0) {
                    arriba = random.nextInt(valorMaximo + 1);
                } else {
                    arriba = tablero[i - 1][j].getAbajo();
                }

                if (j == 0) {
                    izquierda = random.nextInt(valorMaximo + 1);
                } else {
                    izquierda = tablero[i][j - 1].getDerecha();
                }

                int derecha = random.nextInt(valorMaximo + 1);
                int abajo = random.nextInt(valorMaximo + 1);

                tablero[i][j] = new Pieza(arriba, derecha, abajo, izquierda);
            }
        }
        
        // Agregar las piezas del tablero a la lista de piezas
        for (int i = 0; i < tamaño; i ++) {
            for (int j = 0; j < tamaño; j ++) {
                piezas.add(tablero[i][j]);
            }
        }
        // Desordenar las piezas
        desordenarPiezas();
    }

    /* 
     * Método para desordenar las piezas generadas aleatoriamente.
     */
    private void desordenarPiezas() {
        for (int i = piezas.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Pieza temp = piezas.get(i);
            piezas.set(i, piezas.get(j));
            piezas.set(j, temp);
        }
    }

    // Método para obtener la lista de piezas
    // Getters
    public ArrayList<Pieza> getPiezas() {
        return piezas;
    }

    public int getCantidadPiezas() {
        return piezas.size();
    }

}
