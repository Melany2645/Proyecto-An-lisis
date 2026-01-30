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

        // Datos de entrada de prueba
        int tamaño = 100; // Tamaño del tablero (3x3, 5x5, 10x10, ect.)
        int valorMaximo = 9; 

        System.out.println("===== Proyecto Análisis de Algoritmos =====");
        System.out.println("Resolución de un rompecabezas mediante distintos algoritmos.");
        System.out.println(" - Fuerza Bruta");
        System.out.println(" - Avance Rápido");
        System.out.println(" - Algoritmo Genético");
        System.out.println();
        System.out.println( "Tamaño del rompecabezas: " + tamaño + "x" + tamaño);
        System.out.println( "Rango de valores: 0 a " + valorMaximo);
        System.out.println("==================================================");
        System.out.println();

        // Generación de las piezas asegurando que exista solución válida
        Piezas generador = new Piezas(tamaño, valorMaximo);
        ArrayList<Pieza> piezas = generador.getPiezas();

        // Piezas quemadas, primer tamaño 3x3, rango de 0...9
        ArrayList<Pieza> piezasQuemadas09 = new ArrayList<>();
        piezasQuemadas09.add(new Pieza(3, 1, 2, 4));
        piezasQuemadas09.add(new Pieza(6, 0, 5, 1));
        piezasQuemadas09.add(new Pieza(1, 0, 3, 2));
        piezasQuemadas09.add(new Pieza(2, 8, 6, 0));
        piezasQuemadas09.add(new Pieza(7, 4, 3, 1));
        piezasQuemadas09.add(new Pieza(1, 5, 4, 0));
        piezasQuemadas09.add(new Pieza(4, 2, 7, 5));
        piezasQuemadas09.add(new Pieza(4, 3, 1, 5));
        piezasQuemadas09.add(new Pieza(0, 8, 2, 3));

        // Piezas quemadas, primer tamaño 3x3, rango de 0...15
        ArrayList<Pieza> piezasQuemadas015 = new ArrayList<>();
        piezasQuemadas015.add(new Pieza(14, 9, 2, 12));
        piezasQuemadas015.add(new Pieza(2, 6, 8, 10));
        piezasQuemadas015.add(new Pieza(15, 10, 9, 3));
        piezasQuemadas015.add(new Pieza(9, 11, 13, 3));
        piezasQuemadas015.add(new Pieza(8, 12, 1, 6));
        piezasQuemadas015.add(new Pieza(13, 12, 14, 6));
        piezasQuemadas015.add(new Pieza(1, 3, 2, 10));
        piezasQuemadas015.add(new Pieza(7, 6, 3, 2));
        piezasQuemadas015.add(new Pieza(9, 10, 7, 6));


        if (tamaño == 3 & valorMaximo == 9) {
            System.out.println("Piezas quemadas en el primer tamaño 3x3, rango 0...9");
            piezas = piezasQuemadas09;
        } else if (tamaño == 3 & valorMaximo == 15) {
            System.out.println("Piezas quemadas en el primer tamaño 3x3, rango 0...15");
            piezas = piezasQuemadas015;
        } else {
            System.out.println("Piezas generadas (desordenadas) para los distintos tamaños");
        }
        System.out.println("\n");

        // Visualización inicial del rompecabezas desordenado
        System.out.println("Rompecabezas desordenado:");
        Tablero t = new Tablero(tamaño);
        t.imprimirRompecabezasDesordenado(piezas);
        System.out.println();

        if (tamaño <= 5) {
            System.out.println("Resolviendo con Fuerza Bruta...\n");
            FuerzaBruta fb = new FuerzaBruta(piezas, tamaño);
            fb.resolver();
        }
        if (tamaño <= 5) {    
            System.out.println("Resolviendo con Avance Rápido...\n");
            reinicializarPiezas(piezas);
            AvanceRapido ar = new AvanceRapido(piezas, tamaño);
            ar.solucionAR();
        }
        System.out.println("Resolviendo con Algoritmo Genético...\n");
        reinicializarPiezas(piezas);
        Genetico g = new Genetico(tamaño, piezas);
        g.ejecutar();
    }
}
