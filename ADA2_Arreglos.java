import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ADA2_Arreglos {

    // Constantes del sistema
    private static final int TOTAL_MATERIAS = 10000;
    private static final String[] MATERIAS = {
        "Matemáticas", "Español", "Historia", "Ciencias", "Inglés", "Programación"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Usamos ArrayList para poder agregar más alumnos dinámicamente
        ArrayList<double[]> listaAlumnos = new ArrayList<>();

        // 1. Inicializar con 500 alumnos con calificaciones aleatorias
        inicializarAlumnos(listaAlumnos, 10000);

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ DE GESTIÓN ESCOLAR (JAVA) ---");
            System.out.println("Total de alumnos registrados: " + listaAlumnos.size());
            System.out.println("1. Mostrar lista general de alumnos (Tabla)");
            System.out.println("2. Buscar alumno por ID");
            System.out.println("3. Agregar nuevo alumno");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción (1-4): ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("¿Desea ver la lista completa de " + listaAlumnos.size() + " alumnos? (s/n): ");
                    String respuesta = scanner.nextLine().toLowerCase();
                    if (respuesta.equals("s")) {
                        mostrarTablaAlumnos(listaAlumnos, listaAlumnos.size());
                    } else {
                        mostrarTablaAlumnos(listaAlumnos, 20); // Muestra los primeros 20 por defecto
                    }
                    break;

                case "2":
                    System.out.print("Ingrese ID del alumno a buscar (1-" + listaAlumnos.size() + "): ");
                    try {
                        int idBuscar = Integer.parseInt(scanner.nextLine());
                        buscarAlumno(listaAlumnos, idBuscar);
                    } catch (NumberFormatException e) {
                        System.out.println("\n Error: Ingrese un número entero válido.");
                    }
                    break;

                case "3":
                    agregarAlumno(listaAlumnos, scanner);
                    break;

                case "4":
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;

                default:
                    System.out.println("\n Opción no válida. Intente de nuevo.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Inicializa la matriz/lista con una cantidad de alumnos dada y notas aleatorias (5.0 a 10.0).
     */
    private static void inicializarAlumnos(ArrayList<double[]> lista, int cantidadAlumnos) {
        Random random = new Random();
        for (int i = 0; i < cantidadAlumnos; i++) {
            double[] notas = new double[TOTAL_MATERIAS];
            for (int j = 0; j < TOTAL_MATERIAS; j++) {
                // Genera nota aleatoria entre 5.0 y 10.0 redondeada a 1 decimal
                double nota = 5.0 + (10.0 - 5.0) * random.nextDouble();
                notas[j] = Math.round(nota * 10.0) / 10.0;
            }
            lista.add(notas);
        }
    }

    /**
     * Muestra a los alumnos y sus materias alineados en formato de tabla.
     */
    private static void mostrarTablaAlumnos(ArrayList<double[]> lista, int limite) {
        System.out.println("\n" + "=".repeat(95));
        System.out.printf("%-12s", "ID ALUMNO");
        for (String materia : MATERIAS) {
            System.out.printf("%-13s", materia);
        }
        System.out.printf("%-10s%n", "PROMEDIO");
        System.out.println("=".repeat(95));

        int cantidadAMostrar = Math.min(lista.size(), limite);

        for (int i = 0; i < cantidadAMostrar; i++) {
            double[] notas = lista.get(i);
            double suma = 0;
            for (double nota : notas) {
                suma += nota;
            }
            double promedio = suma / TOTAL_MATERIAS;

            System.out.printf("Alumno %-5d ", (i + 1));
            for (double nota : notas) {
                System.out.printf("%-13.1f", nota);
            }
            System.out.printf("%-10.2f%n", promedio);
        }

        System.out.println("=".repeat(95));
        if (limite < lista.size()) {
            System.out.println("(* Mostrando los primeros " + limite + " de " + lista.size() + " alumnos *)\n");
        }
    }

    /**
     * Busca y muestra la información detallada de un alumno por ID.
     */
    private static void buscarAlumno(ArrayList<double[]> lista, int idAlumno) {
        int indice = idAlumno - 1;
        if (indice >= 0 && indice < lista.size()) {
            double[] notas = lista.get(indice);
            double suma = 0;

            System.out.println("\n--- INFORMACIÓN DEL ALUMNO " + idAlumno + " ---");
            for (int i = 0; i < TOTAL_MATERIAS; i++) {
                System.out.printf("  • %-13s: %.1f%n", MATERIAS[i], notas[i]);
                suma += notas[i];
            }
            double promedio = suma / TOTAL_MATERIAS;
            System.out.printf("  • Promedio general: %.2f%n%n", promedio);
        } else {
            System.out.println("\n Error: El ID del alumno debe estar entre 1 y " + lista.size() + ".\n");
        }
    }

    /**
     * Pide por consola las calificaciones de un nuevo alumno y lo agrega a la lista.
     */
    private static void agregarAlumno(ArrayList<double[]> lista, Scanner scanner) {
        int nuevoId = lista.size() + 1;
        System.out.println("\n--- AGREGAR NUEVO ALUMNO (ID asignado: " + nuevoId + ") ---");
        double[] nuevasNotas = new double[TOTAL_MATERIAS];

        for (int i = 0; i < TOTAL_MATERIAS; i++) {
            while (true) {
                try {
                    System.out.print("Ingrese calificación para " + MATERIAS[i] + " (0.0 - 10.0): ");
                    double nota = Double.parseDouble(scanner.nextLine());
                    if (nota >= 0.0 && nota <= 10.0) {
                        nuevasNotas[i] = Math.round(nota * 10.0) / 10.0;
                        break;
                    } else {
                        System.out.println(" La calificación debe estar entre 0.0 y 10.0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Ingrese un número decimal válido.");
                }
            }
        }

        lista.add(nuevasNotas);
        System.out.println("\n Alumno " + nuevoId + " agregado con éxito.\n");
    }
}