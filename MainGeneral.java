import java.util.ArrayList;
import java.util.Scanner;

public class MainGeneral {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Proyecto Análisis de Algoritmos =====");
        System.out.println("Seleccione el algoritmo a utilizar:");
        System.out.println("1. Fuerza Bruta");
        System.out.println("2. Avance Rápido");
        System.out.println("3. Algoritmo Genético");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();

        System.out.print("\nIngrese el tamaño del tablero (n): ");
        int tamaño = sc.nextInt();

        System.out.print("Ingrese el valor máximo de las piezas: ");
        int valorMaximo = sc.nextInt();

        System.out.println();

        // Generación de piezas
        Piezas generador = new Piezas(tamaño, valorMaximo);
        ArrayList<Pieza> piezas = generador.getPiezas();

        // Mostrar rompecabezas desordenado
        System.out.println("Rompecabezas desordenado:");
        Tablero t = new Tablero(tamaño);
        t.imprimirRompecabezasDesordenado(piezas);
        System.out.println();

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