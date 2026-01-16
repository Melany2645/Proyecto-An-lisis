import java.util.ArrayList;

/*
 * Clase principal para ejecutar el algoritmo de fuerza bruta en un problema de colocación de piezas en un tablero.
 * @autor Melany Jirón Díaz
 * @version 1.0
 */
public class MainFuerzaBruta {

    public static void main(String []args) {

        // Parámetros del problema, Prueba con tamaño 3 y valores entre 0 y 9 o 0 y 15
        int tamaño = 7;
        int valorMaximo = 15;

        System.out.println("Iniciando prueba Fuerza Bruta");
        System.out.println("Tamaño del rompecabezas: " + tamaño + "x" + tamaño);
        System.out.println("Rango de valores: 0 a " + valorMaximo);
        System.out.println();

        // Generar piezas
        Piezas generadorPiezas = new Piezas(tamaño, valorMaximo);
        ArrayList<Pieza> piezas = generadorPiezas.getPiezas();

        // Piezas quemadas, primer tamaño 3x3
        ArrayList<Pieza> piezasQuemadas = new ArrayList<>();
        piezasQuemadas.add(new Pieza(1, 5, 4, 0));
        piezasQuemadas.add(new Pieza(4, 3, 1, 5));
        piezasQuemadas.add(new Pieza(0, 8, 2, 3));
        piezasQuemadas.add(new Pieza(4, 2, 7, 5));
        piezasQuemadas.add(new Pieza(1, 0, 3, 2));
        piezasQuemadas.add(new Pieza(2, 8, 6, 0));
        piezasQuemadas.add(new Pieza(7, 4, 3, 1));
        piezasQuemadas.add(new Pieza(3, 1, 2, 4));
        piezasQuemadas.add(new Pieza(6, 0, 5, 1));


        if (tamaño == 3) {
            System.out.println("Piezas quemadas:");
            for (Pieza pieza : piezasQuemadas) {
                System.out.println(pieza + " ");
            }
            // Usar las piezas quemadas cuando el tamaño sea 3
            piezas = piezasQuemadas;
        } else {
            System.out.println("Piezas generadas (desordenadas):");
            for (Pieza pieza : piezas) {
                System.out.println(pieza + " ");
            }
        }
        System.out.println("\n");

        // Resolver con Fuerza Bruta
        FuerzaBruta fuerzaBruta = new FuerzaBruta(piezas, tamaño);
        boolean solucion = fuerzaBruta.resolver();

        // Resultado
        if (!solucion) {
            System.out.println("No se encontró solución.");
        }
        System.out.println("Proceso finalizado.");
        
    }
}
