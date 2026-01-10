import java.util.ArrayList;
import java.util.Random;

public class Piezas {
    private ArrayList<Pieza> piezas;
    private int tamaño;
    private int valorMaximo;
    private Random random;

    public Piezas(int tamaño, int valorMaximo) {
        this.tamaño = tamaño;
        this.valorMaximo = valorMaximo;
        this.piezas = new ArrayList<>();
        this.random = new Random();

        piezasConSolucion();
    }

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

    private void desordenarPiezas() {
        for (int i = piezas.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Pieza temp = piezas.get(i);
            piezas.set(i, piezas.get(j));
            piezas.set(j, temp);
        }
    }

    // Método para obtener la lista de piezas
    // Revisar, no olvidar
    public ArrayList<Pieza> getPiezas() {
        return piezas;
    }

    public int getCantidadPiezas() {
        return piezas.size();
    }

}
