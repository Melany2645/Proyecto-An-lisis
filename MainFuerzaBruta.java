import java.util.ArrayList;

public class MainFuerzaBruta {

    public static void main(String []args) {

        // Parámetros del problema, Prueba con tamaño 3 y valores entre 0 y 9
        int tamaño = 5;
        int valorMaximo = 9;

        System.out.println("Iniciando prueba Fuerza Bruta");
        System.out.println("Tamaño del rompecabezas: " + tamaño + "x" + tamaño);
        System.out.println("Rango de valores: 0 a " + valorMaximo);
        System.out.println();

        // Generar piezas
        Piezas generadorPiezas = new Piezas(tamaño, valorMaximo);
        ArrayList<Pieza> piezas = generadorPiezas.getPiezas();


        System.out.println("Piezas generadas (desordenadas):");
        for (Pieza pieza : piezas) {
            System.out.println(pieza + " ");
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
