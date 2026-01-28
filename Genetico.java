import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Clase que implementa el Algoritmo Genético para resolver el rompecabezas.
 * Incluye impresión de padres, hijo y mutación UNA sola vez para mostrar el proceso,
 * además de métricas empíricas:
 * comparaciones, asignaciones e instrucciones.
 *
 * @autor Jeremy Montero
 * @version 1.2
 */
public class Genetico {

    // Población actual de cromosomas
    private Poblacion poblacion;

    // Parámetros del problema
    private int tamañoTablero;
    private int poblacionInicial;
    private int hijosGenerados;
    private int maxGeneraciones;
    private double probMutacion;

    // Generador de números aleatorios
    private Random random;

    // Contadores empíricos
    private long comparaciones;
    private long asignaciones;
    private long instrucciones;
    
    // Para mostrar padres/hijos solo una vez
    private boolean yaMostroProceso = false;
    private int contadorHijosMostrados = 0;

    /**
     * Constructor simplificado.
     * Calcula la población según el tamaño del tablero.
     */
    public Genetico(int tamañoTablero, ArrayList<Pieza> piezasBase) {
        int poblacionInicial = calcularPoblacionInicial(tamañoTablero);
        this(
            tamañoTablero,
            poblacionInicial,  // Población según tamaño
            10,                 // Máximo de generaciones
            0.75,               // Probabilidad de mutación (aumentada para mejor visualización)
            piezasBase
        );
    }
    
    /**
     * Calcula la población inicial según el tamaño del tablero.
     */
    private static int calcularPoblacionInicial(int tamaño) {
        switch(tamaño) {
            case 3:  return 3;
            case 5:  return 5;
            case 10: return 10;
            case 15: return 15;
            case 30: return 30;
            case 60: return 30;
            case 100: return 30;
            default: return Math.min(tamaño * tamaño, 100);
        }
    }

    /**
     * Constructor completo.
     */
    public Genetico(int tamañoTablero, int poblacionInicial, int maxGeneraciones,
                    double probMutacion, ArrayList<Pieza> piezasBase) {

        this.tamañoTablero = tamañoTablero;
        this.poblacionInicial = poblacionInicial;
        this.hijosGenerados = calcularHijos(tamañoTablero);
        this.maxGeneraciones = maxGeneraciones;
        this.probMutacion = probMutacion;
        this.random = new Random();

        this.comparaciones = 0;
        this.asignaciones = 0;
        this.instrucciones = 0;

        this.poblacion = new Poblacion(poblacionInicial, tamañoTablero, piezasBase);
    }
    
    /**
     * Calcula la cantidad de hijos a generar según el tamaño.
     */
    private static int calcularHijos(int tamaño) {
        switch(tamaño) {
            case 3:  return 6;
            case 5:  return 10;
            case 10: return 20;
            case 15: return 30;
            case 30: return 60;
            case 60: return 60;
            case 100: return 60;
            default: return Math.min(tamaño * tamaño * 2, 200);
        }
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

            poblacion.ordenarPorFitness();
            Cromosoma mejor = poblacion.getPoblacion().get(0);

            comparaciones++;
            if (mejor.getFitness() == maxFitness) {

                long fin = System.nanoTime();
                imprimirResultados(gen, mejor, true, inicio, memoriaInicial, runtime);
                return;
            }

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

            // Detectar si hay cromosomas duplicados en la población actual
            boolean hayDuplicados = detectarDuplicados(poblacion.getPoblacion());

            // Generar el resto de la población (hijos)
            int hijosAGenerar = hijosGenerados;
            int contadorHijos = 0;
            int idIndividuo = 1;
            while (nuevaPoblacion.size() < poblacionInicial + hijosAGenerar) {

                Cromosoma padre1 = seleccionarPadre();
                Cromosoma padre2 = seleccionarPadre();

                Cromosoma hijo = cruzar(padre1, padre2);

                // Solo aplicar mutación si hay poblaciones duplicadas
                if (hayDuplicados) {
                    mutar(hijo, idIndividuo);
                    idIndividuo++;
                }

                nuevaPoblacion.add(hijo);
                asignaciones++;
            }

            // Seleccionar los mejores candidatos de la nueva población
            // para convertirse en la población de la siguiente generación
            poblacion.setPoblacion(seleccionarMejores(nuevaPoblacion, poblacionInicial));
        }

        // No se encontró solución perfecta
        long fin = System.nanoTime();
        imprimirResultadosFinales(mejorGlobal, inicio, memoriaInicial, runtime);
    }

    /**
     * Imprime resultados cuando hay solución perfecta.
     */
    private void imprimirResultados(int gen, Cromosoma mejor, boolean perfecta,
                                    long inicio, long memoriaInicial, Runtime runtime) {

        long fin = System.nanoTime();
        long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaFinal - memoriaInicial;
        double tiempo = (fin - inicio) / 1_000_000_000.0;

        System.out.println("====== Algoritmo Genético ======");
        System.out.println("Solución perfecta encontrada: " + perfecta);
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

        imprimirTop3();
    }

    /**
     * Imprime resultados finales si no se encontró solución perfecta.
     */
    private void imprimirResultadosFinales(Cromosoma mejorGlobal,
                                           long inicio, long memoriaInicial, Runtime runtime) {

        long fin = System.nanoTime();
        long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaFinal - memoriaInicial;
        double tiempo = (fin - inicio) / 1_000_000_000.0;

        System.out.println("====== Algoritmo Genético ======");
        System.out.println("No se encontró solución perfecta.");
        System.out.println("Se muestra la mejor aproximación.");
        System.out.println("Fitness alcanzado: " + mejorGlobal.getFitness());
        System.out.println("Duración: " + String.format("%.3f s", tiempo));
        System.out.println("Memoria usada: " + (memoriaUsada / 1024) + " KB");
        System.out.println("Comparaciones: " + getComparaciones());
        System.out.println("Asignaciones: " + getAsignaciones());
        System.out.println("Instrucciones: " + getInstrucciones());
        System.out.println("===============================");
        System.out.println("Mejor tablero encontrado:");
        mejorGlobal.imprimir();

        imprimirTop3();
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
     * Cruce válido: PMX (Partially Mapped Crossover) adaptado para matrices 2D.
     * Garantiza que no hay piezas duplicadas ni faltantes.
     * Muestra puntuaciones de padres e hijo.
     */
    private Cromosoma cruzar(Cromosoma p1, Cromosoma p2) {
        Pieza[][] genes = new Pieza[tamañoTablero][tamañoTablero];
        boolean[][] usado = new boolean[tamañoTablero][tamañoTablero];
        
        // Convertir matrices 2D a arrays 1D para facilitar el cruce
        Pieza[] arr1 = matrizA1D(p1.getGenes());
        Pieza[] arr2 = matrizA1D(p2.getGenes());
        Pieza[] hijo = new Pieza[arr1.length];
        
        // Seleccionar dos puntos de corte aleatorios
        int punto1 = random.nextInt(arr1.length);
        int punto2 = random.nextInt(arr1.length);
        if (punto1 > punto2) {
            int temp = punto1;
            punto1 = punto2;
            punto2 = temp;
        }
        
        // Copiar segmento del padre 1
        boolean[] enSegmento = new boolean[arr1.length];
        for (int i = punto1; i < punto2; i++) {
            hijo[i] = arr1[i].clonar();
            enSegmento[arr1[i].hashCode() % arr1.length] = true;
            asignaciones++;
        }
        
        // Llenar con piezas del padre 2 que no estén duplicadas
        int posicion = 0;
        for (int i = 0; i < arr2.length; i++) {
            comparaciones++;
            if (posicion >= punto1 && posicion < punto2) {
                posicion++;
            }
            
            // Verificar que la pieza no esté ya en el hijo
            boolean existe = false;
            for (int j = punto1; j < punto2; j++) {
                comparaciones++;
                if (hijo[j] != null && hijo[j].hashCode() == arr2[i].hashCode()) {
                    existe = true;
                    break;
                }
            }
            
            if (!existe && posicion < arr2.length) {
                hijo[posicion] = arr2[i].clonar();
                posicion++;
                asignaciones++;
            }
        }
        
        // Convertir array 1D de vuelta a matriz 2D
        int index = 0;
        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                genes[i][j] = hijo[index++];
                asignaciones++;
            }
        }
        
        Cromosoma hijoGenerado = new Cromosoma(genes);
        
        // Mostrar información del cruce (dos hijos como demostración)
        if (!yaMostroProceso) {
            System.out.println("\n======= DEMOSTRACIÓN DEL PROCESO GENÉTICO =======");
        }
        
        if (contadorHijosMostrados < 2) {
            contadorHijosMostrados++;
            System.out.println("\n--- HIJO #" + contadorHijosMostrados + " ---");
            System.out.println("PADRE 1 (Fitness: " + p1.getFitness() + "):");
            p1.imprimir();
            System.out.println("\nPADRE 2 (Fitness: " + p2.getFitness() + "):");
            p2.imprimir();
            System.out.println("\nHIJO #" + contadorHijosMostrados + " DESPUÉS DEL CRUCE (Fitness: " + hijoGenerado.getFitness() + "):");
            hijoGenerado.imprimir();
            
            if (contadorHijosMostrados == 2) {
                System.out.println("===========================================");
                yaMostroProceso = true;
            }
        }
        
        return hijoGenerado;
    }
    
    /**
     * Convierte una matriz 2D a un array 1D.
     */
    private Pieza[] matrizA1D(Pieza[][] matriz) {
        Pieza[] array = new Pieza[tamañoTablero * tamañoTablero];
        int index = 0;
        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                array[index++] = matriz[i][j];
            }
        }
        return array;
    }

    /**
     * Mutación: intercambio de dos piezas.
     * Solo acepta la mutación si el fitness mejora o se mantiene igual.
     * Si el fitness empeora, descarta el cambio.
     * Muestra todas las mutaciones intentadas con el ID del individuo y las piezas.
     */
    private void mutar(Cromosoma cromosoma, int idIndividuo) {

        comparaciones++;
        if (random.nextDouble() < probMutacion) {

            int f1 = random.nextInt(tamañoTablero);
            int c1 = random.nextInt(tamañoTablero);
            int f2 = random.nextInt(tamañoTablero);
            int c2 = random.nextInt(tamañoTablero);

            int fitnessAntes = cromosoma.getFitness();

            // Guardar las piezas originales para restaurar si es necesario
            Pieza original1 = cromosoma.getGenes()[f1][c1];
            Pieza original2 = cromosoma.getGenes()[f2][c2];
            
            // Guardar valores de las piezas para mostrar
            String pieza1Info = original1.getArriba() + "-" + original1.getDerecha() + "-" + 
                               original1.getAbajo() + "-" + original1.getIzquierda();
            String pieza2Info = original2.getArriba() + "-" + original2.getDerecha() + "-" + 
                               original2.getAbajo() + "-" + original2.getIzquierda();

            // Realizar el intercambio
            Pieza temp = cromosoma.getGenes()[f1][c1];
            asignaciones++;

            cromosoma.getGenes()[f1][c1] = cromosoma.getGenes()[f2][c2];
            asignaciones++;

            cromosoma.getGenes()[f2][c2] = temp;
            asignaciones++;

            // Recalcular fitness después de la mutación
            cromosoma.calcularFitness();
            asignaciones++;

            int fitnessDespues = cromosoma.getFitness();

            // Mostrar información de la mutación con ID del individuo y piezas
            System.out.println("\n[MUTACIÓN INTENTADA - INDIVIDUO #" + idIndividuo + "]");
            System.out.println("  Posiciones: (" + f1 + "," + c1 + ") <-> (" + f2 + "," + c2 + ")");
            System.out.println("  Piezas: [" + pieza1Info + "] <-> [" + pieza2Info + "]");
            System.out.println("  Fitness ANTES: " + fitnessAntes + " | Fitness DESPUÉS: " + fitnessDespues);

            // Si el fitness empeora, descartar el cambio
            if (fitnessDespues < fitnessAntes) {
                cromosoma.getGenes()[f1][c1] = original1;
                asignaciones++;
                cromosoma.getGenes()[f2][c2] = original2;
                asignaciones++;
                cromosoma.calcularFitness();
                asignaciones++;
                System.out.println("  No mejora ");
            } else if (fitnessDespues > fitnessAntes) {
                System.out.println("  Si mejora");
            } else {
                System.out.println("  No hay cambios");
            }
        }
    }
    
    /**
     * Detecta si hay cromosomas duplicados en la población.
     * Dos cromosomas son iguales si tienen el mismo fitness y la misma configuración.
     */
    private boolean detectarDuplicados(ArrayList<Cromosoma> poblacion) {
        for (int i = 0; i < poblacion.size(); i++) {
            for (int j = i + 1; j < poblacion.size(); j++) {
                comparaciones++;
                if (sonIguales(poblacion.get(i), poblacion.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Verifica si dos cromosomas son idénticos.
     */
    private boolean sonIguales(Cromosoma c1, Cromosoma c2) {
        Pieza[][] genes1 = c1.getGenes();
        Pieza[][] genes2 = c2.getGenes();
        
        comparaciones++;
        if (c1.getFitness() != c2.getFitness()) {
            return false;
        }
        
        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                comparaciones++;
                if (genes1[i][j].getArriba() != genes2[i][j].getArriba() ||
                    genes1[i][j].getDerecha() != genes2[i][j].getDerecha() ||
                    genes1[i][j].getAbajo() != genes2[i][j].getAbajo() ||
                    genes1[i][j].getIzquierda() != genes2[i][j].getIzquierda()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Selecciona los N mejores cromosomas de una población.
     * Ordena por fitness y retorna los mejores N individuos.
     */
    private ArrayList<Cromosoma> seleccionarMejores(ArrayList<Cromosoma> poblacion, int cantidad) {
        // Ordenar por fitness descendente
        ArrayList<Cromosoma> ordenada = new ArrayList<>(poblacion);
        Collections.sort(ordenada, new Comparator<Cromosoma>() {
            @Override
            public int compare(Cromosoma c1, Cromosoma c2) {
                comparaciones++;
                return Integer.compare(c2.getFitness(), c1.getFitness());
            }
        });
        
        // Retornar solo los mejores N
        ArrayList<Cromosoma> mejores = new ArrayList<>();
        for (int i = 0; i < cantidad && i < ordenada.size(); i++) {
            mejores.add(ordenada.get(i));
            asignaciones++;
        }
        
        return mejores;
    }
}
