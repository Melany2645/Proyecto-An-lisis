import java.util.ArrayList;

/************************Datos administrativos****************************
 * Nombre del proyecto: Análisis de Algoritmos - Armado de Rombecabezas
 * Archivo: MainGeneral.java
 * Autor: Melany Jirón y Jeremy Montero
 * Empresa: Instituto Tecnológico de Costa Rica
 * ******************************Descripción*****************************
 * Clase principal para ejecutar los algoritmos en un problema de colocación de piezas en un tablero.
 * ******************************Versión*********************************
 * 1.0 | 2026-30-01 | Melany Jirón Díaz y Jeremy Montero
 ************************************************************************/

/**
 * Clase principal del proyecto.
 * Funciona como punto único de entrada al sistema y permite al usuario:
 *  - Poner ver el armado del rompecabezas desordenado.
 *  - Mediante tres algoritmos para dar la solución al rompecabezas:
 *    - Fuerza Bruta
 *    - Avance Rápido (Heurística MRV)
 *    - Algoritmo Genético
 * Desde aquí se ejecutan Fuerza Bruta, Avance Rápido y el Algoritmo Genético,
 * garantizando que todos trabajen sobre el mismo conjunto de piezas y bajo
 * las mismas condiciones iniciales.
 * @autor Jeremy Montero y Melany Jirón Díaz
 * @version 1.0
 */
public class MainGeneral {
    
    /**
     * Reinicia todas las piezas marcándolas como no usadas.
     * Ya que los 3 algoritmos utilizan la misma lista de piezas.
     * @param piezas Lista de piezas a reinicializar.
     */
    private static void reinicializarPiezas(ArrayList<Pieza> piezas) {
        for (Pieza pieza : piezas) {
            pieza.setUsada(false);
        }
    }

    /**
     * Método principal que inicia la ejecución del programa.
     * Controla la interacción con el usuario y la selección del algoritmo.
     */
    public static void main(String[] args) {

        System.out.println("===== Proyecto Análisis de Algoritmos =====");
        System.out.println("Resolución de un rompecabezas mediante distintos algoritmos:");
        System.out.println("1. Fuerza Bruta");
        System.out.println("2. Avance Rápido");
        System.out.println("3. Algoritmo Genético");
        System.out.println();

        // Datos de entrada de prueba
        int tamaño = 3;
        int valorMaximo = 9; 

        // Generación de las piezas asegurando que exista solución válida
        Piezas generador = new Piezas(tamaño, valorMaximo);
        ArrayList<Pieza> piezas = generador.getPiezas();

        // Piezas quemadas, primer tamaño 3x3
        ArrayList<Pieza> piezasQuemadas = new ArrayList<>();
        piezasQuemadas.add(new Pieza(3, 1, 2, 4));
        piezasQuemadas.add(new Pieza(6, 0, 5, 1));
        piezasQuemadas.add(new Pieza(1, 0, 3, 2));
        piezasQuemadas.add(new Pieza(2, 8, 6, 0));
        piezasQuemadas.add(new Pieza(7, 4, 3, 1));
        piezasQuemadas.add(new Pieza(1, 5, 4, 0));
        piezasQuemadas.add(new Pieza(4, 2, 7, 5));
        piezasQuemadas.add(new Pieza(4, 3, 1, 5));
        piezasQuemadas.add(new Pieza(0, 8, 2, 3));


        if (tamaño == 3) {
            System.out.println("Piezas quemadas en el primer tamaño 3x3");
            piezas = piezasQuemadas;
        } else {
            System.out.println("Piezas generadas (desordenadas) para los distintos tamaños");
        }
        System.out.println("\n");

        // Visualización inicial del rompecabezas desordenado
        System.out.println("Rompecabezas desordenado:");
        Tablero t = new Tablero(tamaño);
        t.imprimirRompecabezasDesordenado(piezas);
        System.out.println();

        System.out.println("Resolviendo con Fuerza Bruta...\n");
        FuerzaBruta fb = new FuerzaBruta(piezas, tamaño);
        fb.resolver();

        System.out.println("Resolviendo con Avance Rápido...\n");
        reinicializarPiezas(piezas);
        AvanceRapido ar = new AvanceRapido(piezas, tamaño);
        ar.solucionAR();

        System.out.println("Resolviendo con Algoritmo Genético...\n");
        reinicializarPiezas(piezas);
        Genetico g = new Genetico(tamaño, piezas);
        g.ejecutar();
    }
}
