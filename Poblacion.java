import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase que representa una población de cromosomas para el algoritmo genético.
 * Se encarga de generar la población inicial, ordenarla por fitness y seleccionar
 * los mejores individuos.
 * @autor Jeremy Montero
 * @version 1.0
 */
public class Poblacion {

    private ArrayList<Cromosoma> poblacion;
    private int tamañoPoblacion;
    private int tamañoTablero;

    /**
     * Constructor de la población.
     * @param tamañoPoblacion Número de cromosomas en la población.
     * @param tamañoTablero   Tamaño del tablero (n x n).
     * @param piezasBase      Lista base de piezas para generar los cromosomas.
     */
    public Poblacion(int tamañoPoblacion, int tamañoTablero, ArrayList<Pieza> piezasBase) {
        this.tamañoPoblacion = tamañoPoblacion;
        this.tamañoTablero = tamañoTablero;
        this.poblacion = new ArrayList<>();
        generarPoblacionInicial(piezasBase);
    }

    /**
     * Genera la población inicial de manera aleatoria.
     * Cada cromosoma utiliza todas las piezas, sin repetirlas.
     * @param piezasBase Lista base de piezas.
     */
    private void generarPoblacionInicial(ArrayList<Pieza> piezasBase) {
        for (int i = 0; i < tamañoPoblacion; i++) {
            ArrayList<Pieza> copia = copiarPiezas(piezasBase);
            Collections.shuffle(copia);

            Pieza[][] genes = new Pieza[tamañoTablero][tamañoTablero];
            int index = 0;

            for (int f = 0; f < tamañoTablero; f++) {
                for (int c = 0; c < tamañoTablero; c++) {
                    genes[f][c] = copia.get(index++);
                }
            }

            Cromosoma cromosoma = new Cromosoma(genes);
            poblacion.add(cromosoma);
        }
    }

    /**
     * Copia profunda de la lista de piezas.
     * Cada cromosoma tendrá sus propias piezas independientes.
     * @param originales Lista original de piezas.
     * @return Nueva lista con copias de las piezas.
     */
    private ArrayList<Pieza> copiarPiezas(ArrayList<Pieza> originales) {
        ArrayList<Pieza> copia = new ArrayList<>();

        for (Pieza p : originales) {
            copia.add(p.clonar()); // Necesitas el método clonar() en Pieza
        }

        return copia;
    }

    /**
     * Ordena la población de mayor a menor fitness.
     */
    public void ordenarPorFitness() {
        Collections.sort(poblacion, new Comparator<Cromosoma>() {
            @Override
            public int compare(Cromosoma c1, Cromosoma c2) {
                return Integer.compare(c2.getFitness(), c1.getFitness());
            }
        });
    }

    /**
     * Obtiene los mejores N cromosomas de la población.
     * @param n Cantidad de cromosomas a obtener.
     * @return Lista de los mejores cromosomas.
     */
    public ArrayList<Cromosoma> getMejores(int n) {
        ordenarPorFitness();
        ArrayList<Cromosoma> mejores = new ArrayList<>();

        for (int i = 0; i < n && i < poblacion.size(); i++) {
            mejores.add(poblacion.get(i));
        }

        return mejores;
    }

    /**
     * Obtiene la población completa.
     * @return Lista de cromosomas.
     */
    public ArrayList<Cromosoma> getPoblacion() {
        return poblacion;
    }

    /**
     * Reemplaza la población actual por una nueva.
     * @param nuevaPoblacion Nueva lista de cromosomas.
     */
    public void setPoblacion(ArrayList<Cromosoma> nuevaPoblacion) {
        this.poblacion = nuevaPoblacion;
    }

    /**
     * Imprime la información general de la población.
     */
    public void imprimirResumen() {
        ordenarPorFitness();
        System.out.println("===== Población =====");
        System.out.println("Tamaño población: " + tamañoPoblacion);
        System.out.println("Mejor fitness: " + poblacion.get(0).getFitness());
        System.out.println("Peor fitness: " + poblacion.get(poblacion.size() - 1).getFitness());
        System.out.println("=====================");
    }
}