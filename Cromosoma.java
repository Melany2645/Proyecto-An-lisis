/**
 * Clase que representa un Cromosoma dentro del algoritmo genético.
 * @autor Jeremy Montero
 * @version 1.0
 */
public class Cromosoma {

    private Pieza[][] genes;
    private int tamaño;
    private int fitness;

    /**
     * Constructor que recibe una matriz de piezas y construye el cromosoma.
     * @param genes Matriz de piezas que representa el rompecabezas.
     * @param tamaño representa el tamaño.
     */
    public Cromosoma(Pieza[][] genes) {
        this.genes = genes;
        this.tamaño = genes.length;
        calcularFitness();
    }

    /**
     * Método que calcula la aptitud (fitness) del cromosoma.
     * El fitness corresponde al número de lados que coinciden correctamente
     * entre piezas adyacentes en el tablero.
     */
    public void calcularFitness() {
        fitness = 0;

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {

                // Comparación horizontal
                if (j < tamaño - 1) {
                    if (genes[i][j].getDerecha() == genes[i][j + 1].getIzquierda()) {
                        fitness++;
                    }
                }

                // Comparación vertical
                if (i < tamaño - 1) {
                    if (genes[i][j].getAbajo() == genes[i + 1][j].getArriba()) {
                        fitness++;
                    }
                }
            }
        }
    }

    /**
     * Obtiene el valor de fitness del cromosoma.
     * @return Fitness calculado.
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * Obtiene la matriz de genes del cromosoma.
     * @return Matriz de piezas.
     */
    public Pieza[][] getGenes() {
        return genes;
    }

    /**
     * Imprime el cromosoma en formato de tablero.
     */
    public void imprimir() {
        for (int i = 0; i < tamaño; i++) {
            for (int linea = 0; linea < 3; linea++) {
                for (int j = 0; j < tamaño; j++) {
                    System.out.print(genes[i][j].visualizacionLineas()[linea] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("Fitness: " + fitness);
    }

}