import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que implementa el Algoritmo Genético para resolver el rompecabezas.
 * Además de buscar una solución óptima, mide métricas empíricas como:
 * comparaciones, asignaciones e instrucciones, al igual que Fuerza Bruta
 * y Avance Rápido, para poder comparar el rendimiento de los tres métodos.
 * 
 * @autor Jeremy Montero
 * @version 1.0
 */
public class Genetico {

    // Población actual de cromosomas
    private Poblacion poblacion;

    // Parámetros del problema
    private int tamañoTablero;
    private int tamañoPoblacion;
    private int maxGeneraciones;
    private double probMutacion;

    // Generador de números aleatorios
    private Random random;

    // Contadores empíricos
    private long comparaciones;
    private long asignaciones;
    private long instrucciones;

    /**
     * Constructor simplificado.
     * Usa parámetros por defecto para el algoritmo genético.
     * No se le pide nada al usuario.
     */
    public Genetico(int tamañoTablero, ArrayList<Pieza> piezasBase) {
        this(
            tamañoTablero,
            150,     // Tamaño de la población
            2000,    // Máximo de generaciones
            0.08,    // Probabilidad de mutación
            piezasBase
        );
    }

    /**
     * Constructor completo del algoritmo genético.
     * Inicializa parámetros, contadores y genera la población inicial.
     * 
     * @param tamañoTablero   Tamaño del tablero (n x n).
     * @param tamañoPoblacion Tamaño de la población.
     * @param maxGeneraciones Número máximo de generaciones.
     * @param probMutacion    Probabilidad de mutación.
     * @param piezasBase      Lista base de piezas.
     */
    public Genetico(int tamañoTablero, int tamañoPoblacion, int maxGeneraciones,
                    double probMutacion, ArrayList<Pieza> piezasBase) {

        this.tamañoTablero = tamañoTablero;
        this.tamañoPoblacion = tamañoPoblacion;
        this.maxGeneraciones = maxGeneraciones;
        this.probMutacion = probMutacion;
        this.random = new Random();

        // Inicialización de contadores empíricos
        this.comparaciones = 0;
        this.asignaciones = 0;
        this.instrucciones = 0;

        // Creación de la población inicial
        this.poblacion = new Poblacion(tamañoPoblacion, tamañoTablero, piezasBase);
    }

    public long getComparaciones() {
        return comparaciones;
    }

    public long getAsignaciones() {
        return asignaciones;
    }

    public long getInstrucciones() {
        instrucciones = comparaciones + asignaciones;
        return instrucciones;
    }

    /**
     * Ejecuta el algoritmo genético.
     * Busca una solución perfecta; si no la encuentra, muestra
     * la mejor solución aproximada alcanzada.
     */
    public void ejecutar() {

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
        
        long inicio = System.nanoTime();

        // Fitness máximo posible del tablero
        int maxFitness = (tamañoTablero * (tamañoTablero - 1)) * 2;

        Cromosoma mejorGlobal = null;

        for (int gen = 0; gen < maxGeneraciones; gen++) {

            poblacion.ordenarPorFitness();
            Cromosoma mejor = poblacion.getPoblacion().get(0);

            comparaciones++;
            if (mejor.getFitness() == maxFitness) {
                long fin = System.nanoTime();
                long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
                long memoriaUsada = memoriaFinal - memoriaInicial;
                
                double tiempo = (fin - inicio) / 1_000_000_000.0;

                System.out.println("====== Algoritmo Genético ======");
                System.out.println("Solución perfecta encontrada: true");
                System.out.println("Generación: " + gen);
                System.out.println("Fitness: " + mejor.getFitness());
                System.out.println("Duración: " + String.format("%.3f s", tiempo));
                System.out.println("Memoria usada: " + (memoriaUsada / 1024) + " KB");
                System.out.println("Comparaciones: " + getComparaciones());
                System.out.println("Asignaciones: " + getAsignaciones());
                System.out.println("Instrucciones: " + getInstrucciones());
                System.out.println("===============================");
                System.out.println("Tablero solución:");
                mejor.imprimir();
                return;
            }

            comparaciones++;
            if (mejorGlobal == null || mejor.getFitness() > mejorGlobal.getFitness()) {
                mejorGlobal = mejor;
                asignaciones++;
            }

            ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<>();

            // Elitismo
            nuevaPoblacion.add(poblacion.getPoblacion().get(0));
            asignaciones++;
            nuevaPoblacion.add(poblacion.getPoblacion().get(1));
            asignaciones++;

            while (nuevaPoblacion.size() < tamañoPoblacion) {

                Cromosoma padre1 = seleccionarPadre();
                Cromosoma padre2 = seleccionarPadre();

                Cromosoma hijo = cruzar(padre1, padre2);
                mutar(hijo);

                nuevaPoblacion.add(hijo);
                asignaciones++;
            }

            poblacion.setPoblacion(nuevaPoblacion);
        }

        long fin = System.nanoTime();
        long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaFinal - memoriaInicial;
        
        double tiempo = (fin - inicio) / 1_000_000_000.0;

        System.out.println("====== Algoritmo Genético ======");
        System.out.println("No se encontró solución perfecta.");
        System.out.println("Se muestra la mejor solución aproximada.");
        System.out.println("Fitness alcanzado: " + mejorGlobal.getFitness());
        System.out.println("Fitness máximo posible: " + maxFitness);
        System.out.println("Duración: " + String.format("%.3f s", tiempo));
        System.out.println("Memoria usada: " + (memoriaUsada / 1024) + " KB");
        System.out.println("Comparaciones: " + getComparaciones());
        System.out.println("Asignaciones: " + getAsignaciones());
        System.out.println("Instrucciones: " + getInstrucciones());
        System.out.println("===============================");
        System.out.println("Mejor tablero encontrado:");
        mejorGlobal.imprimir();
    }

    /**
     * Selección por torneo.
     */
    private Cromosoma seleccionarPadre() {
        int k = 3;
        Cromosoma mejor = null;

        for (int i = 0; i < k; i++) {
            int index = random.nextInt(poblacion.getPoblacion().size());
            Cromosoma candidato = poblacion.getPoblacion().get(index);

            comparaciones++;
            if (mejor == null || candidato.getFitness() > mejor.getFitness()) {
                mejor = candidato;
                asignaciones++;
            }
        }
        return mejor;
    }

    /**
     * Cruce por filas.
     */
    private Cromosoma cruzar(Cromosoma p1, Cromosoma p2) {

        Pieza[][] genes = new Pieza[tamañoTablero][tamañoTablero];
        int puntoCorte = random.nextInt(tamañoTablero);

        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                if (i < puntoCorte) {
                    genes[i][j] = p1.getGenes()[i][j].clonar();
                } else {
                    genes[i][j] = p2.getGenes()[i][j].clonar();
                }
                asignaciones++;
            }
        }
        return new Cromosoma(genes);
    }

    /**
     * Mutación: intercambio de dos piezas.
     */
    private void mutar(Cromosoma cromosoma) {

        comparaciones++;
        if (random.nextDouble() < probMutacion) {

            int f1 = random.nextInt(tamañoTablero);
            int c1 = random.nextInt(tamañoTablero);
            int f2 = random.nextInt(tamañoTablero);
            int c2 = random.nextInt(tamañoTablero);

            Pieza temp = cromosoma.getGenes()[f1][c1];
            asignaciones++;

            cromosoma.getGenes()[f1][c1] = cromosoma.getGenes()[f2][c2];
            asignaciones++;

            cromosoma.getGenes()[f2][c2] = temp;
            asignaciones++;
        }
    }
}