import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal del proyecto.
 * Funciona como punto único de entrada al sistema y permite al usuario:
 *  - Seleccionar el algoritmo a utilizar.
 *  - Definir el tamaño del tablero.
 *  - Definir el valor máximo de las piezas.
 * Desde aquí se ejecutan Fuerza Bruta, Avance Rápido o el Algoritmo Genético,
 * garantizando que todos trabajen sobre el mismo conjunto de piezas y bajo
 * las mismas condiciones iniciales.
 * @autor Jeremy Montero
 * @version 1.0
 */
public class MainGeneral {

    /**
     * Método principal que inicia la ejecución del programa.
     * Controla la interacción con el usuario y la selección del algoritmo.
     */
    public static void main(String[] args) {

        // Scanner para leer datos ingresados por el usuario
        Scanner sc = new Scanner(System.in);

        // Menú principal del sistema
        System.out.println("===== Proyecto Análisis de Algoritmos =====");
        System.out.println("Seleccione el algoritmo a utilizar:");
        System.out.println("1. Fuerza Bruta");
        System.out.println("2. Avance Rápido");
        System.out.println("3. Algoritmo Genético");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();

        // Lectura del tamaño del tablero
        System.out.print("\nIngrese el tamaño del tablero (n): ");
        int tamaño = sc.nextInt();

        // Lectura del valor máximo permitido en los lados de las piezas
        System.out.print("Ingrese el valor máximo de las piezas: ");
        int valorMaximo = sc.nextInt();

        System.out.println();

        // Generación de las piezas asegurando que exista solución válida
        Piezas generador = new Piezas(tamaño, valorMaximo);
        ArrayList<Pieza> piezas = generador.getPiezas();

        // Visualización inicial del rompecabezas desordenado
        System.out.println("Rompecabezas desordenado:");
        Tablero t = new Tablero(tamaño);
        t.imprimirRompecabezasDesordenado(piezas);
        System.out.println();

        // Selección y ejecución del algoritmo elegido
        switch (opcion) {

            case 1:
                System.out.println("Resolviendo con Fuerza Bruta...\n");
                FuerzaBruta fb = new FuerzaBruta(piezas, tamaño);
                fb.resolver();
                break;

            case 2:
                System.out.println("Resolviendo con Avance Rápido...\n");
                AvanceRapido ar = new AvanceRapido(piezas, tamaño);
                ar.resolver();
                break;

            case 3:
                System.out.println("Resolviendo con Algoritmo Genético...\n");
                Genetico g = new Genetico(tamaño, piezas);
                g.ejecutar();
                break;

            default:
                System.out.println("Opción no válida.");
        }

        sc.close();
    }
}
