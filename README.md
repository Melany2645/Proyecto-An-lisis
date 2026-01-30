# Proyecto: Resolución de Rompecabezas – Análisis de Algoritmos

## Participantes
- Jeremy Montero 2025095891
- Melany Jirón 2025087103

Este proyecto implementa y compara tres enfoques algorítmicos para resolver un rompecabezas cuadrado de piezas:

- **Fuerza Bruta**
- **Avance Rápido (heurístico)**
- **Algoritmo Genético**

El objetivo es analizar el rendimiento y comportamiento de cada algoritmo desde un punto de vista empírico, midiendo tiempo de ejecución, memoria utilizada y número de operaciones (comparaciones, asignaciones e instrucciones).

## Funcionamiento general

1. Se genera un conjunto de piezas con valores aleatorios.
2. El rompecabezas se muestra inicialmente desordenado.
3. El usuario selecciona:
   - El algoritmo a utilizar.
   - El tamaño del tablero.
   - El valor máximo de las piezas.
4. El algoritmo elegido intenta resolver el rompecabezas:
   - **Fuerza Bruta** busca todas las combinaciones posibles.
   - **Avance Rápido** usa decisiones heurísticas.
   - **Algoritmo Genético** utiliza población, cruce y mutación.
5. Se muestra:
   - El tablero solución (o la mejor aproximación).
   - Métricas de rendimiento.
   - Resultados finales (Top 3 en el genético).

## Cómo ejecutar el proyecto

1. Compila todos los archivos Java:

```bash
javac *.java
```

2. Ejecuta el programa principal:

```bash
java MainGeneral
```

3. Sigue las instrucciones en consola para seleccionar el algoritmo y los parámetros del problema.

## Notas

- `MainGeneral` centraliza la ejecución del proyecto.
- Los parámetros del algoritmo genético usan valores por defecto para evitar errores de entrada.
- El proyecto está orientado al análisis de algoritmos, no a la optimización gráfica.
