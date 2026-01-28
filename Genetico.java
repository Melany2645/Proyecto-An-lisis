import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que implementa el Algoritmo Genético para resolver el rompecabezas.
 * Mide métricas empíricas como:
 * comparaciones, asignaciones e instrucciones.
 * 
 * Además, muestra un único ejemplo completo del proceso genético:
 * Padre 1, Padre 2 y el Hijo generado.
 * 
 * @autor Jeremy Montero
 * @version 1.2
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

    // Variables para guardar UN solo ejemplo de cruce
    private Cromosoma ejemploPadre1 = null;
    private Cromosoma ejemploPadre2 = null;
    private Cromosoma ejemploHijo = null;

    /**
     * Constructor simplificado.
     * Usa parámetros por defecto.
     */
    public Genetico(int tamañoTablero, ArrayList<Pieza> piezasBase) {
        this(
            tamañoTablero,
            150,     // Tamaño de población
            2000,    // Máximo de generaciones
            0.08,    // Probabilidad de mutación
            piezasBase
        );
    }

    /**
     * Constructor completo.
     */
    public Genetico(int tamañoTablero, int tamañoPoblacion, int maxGeneraciones,
                    double probMutacion, ArrayList<Pieza> piezasBase) {

        this.tamañoTablero = tamañoTablero;
        this.tamañoPoblacion = tamañoPoblacion;
        this.maxGeneraciones = maxGeneraciones;
        this.probMutacion = probMutacion;
        this.random = new Random();

        this.comparaciones = 0;
        this.asignaciones = 0;
        this.instrucciones = 0;

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
     * Ejecuta el algoritmo genético completo.
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

            // Ordenar población por fitness
            poblacion.ordenarPorFitness();
            Cromosoma mejor = poblacion.getPoblacion().get(0);

            comparaciones++;
            // Si se encuentra solución perfecta
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

                imprimirEjemploCruce();
                imprimirTop3();
                return;
            }

            // Guardar el mejor global
            comparaciones++;
            if (mejorGlobal == null || mejor.getFitness() > mejorGlobal.getFitness()) {
                mejorGlobal = mejor;
                asignaciones++;
            }

            ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<>();

            // Elitismo: conservar los dos mejores
            nuevaPoblacion.add(poblacion.getPoblacion().get(0));
            asignaciones++;
            nuevaPoblacion.add(poblacion.getPoblacion().get(1));
            asignaciones++;

            // Generar el resto de la población
            while (nuevaPoblacion.size() < tamañoPoblacion) {

                Cromosoma padre1 = seleccionarPadre();
                Cromosoma padre2 = seleccionarPadre();

                Cromosoma hijo = cruzar(padre1, padre2);
                mutar(hijo);

                // Guardar SOLO el primer cruce como ejemplo
                if (ejemploHijo == null) {
                    ejemploPadre1 = padre1;
                    ejemploPadre2 = padre2;
                    ejemploHijo = hijo;
                }

                nuevaPoblacion.add(hijo);
                asignaciones++;
            }

            poblacion.setPoblacion(nuevaPoblacion);
        }

        // Si no se encontró solución perfecta
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

        imprimirEjemploCruce();
        imprimirTop3();
    }

    /**
     * Imprime un solo ejemplo del cruce genético.
     */
    private void imprimirEjemploCruce() {
        if (ejemploHijo != null) {
            System.out.println("\n====== Ejemplo de Cruce Genético ======");
            System.out.println("Padre 1:");
            ejemploPadre1.imprimir();

            System.out.println("Padre 2:");
            ejemploPadre2.imprimir();

            System.out.println("Hijo generado:");
            ejemploHijo.imprimir();
            System.out.println("======================================");
        }
    }

    /**
     * Imprime los 3 mejores cromosomas finales.
     */
    private void imprimirTop3() {
        poblacion.ordenarPorFitness();

        System.out.println("\n--- Top 3 mejores individuos finales ---");
        for (int i = 0; i < 3 && i < poblacion.getPoblacion().size(); i++) {
            Cromosoma c = poblacion.getPoblacion().get(i);
            System.out.println("Individuo " + (i + 1) + " -> Fitness: " + c.getFitness());
        }
        System.out.println("---------------------------------------");
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
