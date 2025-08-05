import java.io.*;
import java.util.*;

public class FileManager {

    // Excepcion personalizada para manejar archivos no encontrados
    static class ArchivoNoEncontradoException extends Exception {
        public ArchivoNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }

    // Excepcion personalizada para manejar la creación de un archivo que ya existe
    static class ArchivoYaExisteException extends Exception {
        public ArchivoYaExisteException(String mensaje) {
            super(mensaje);
        }
    }

    // Metodo para verificar la existencia de un archivo
    public static void verificarArchivo(String nombreArchivo) throws ArchivoNoEncontradoException {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            throw new ArchivoNoEncontradoException("El archivo no existe.");
        }
    }

    // Metodo para crear un archivo
    public static void crearArchivo(String nombreArchivo) throws ArchivoYaExisteException {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            throw new ArchivoYaExisteException("El archivo ya existe.");
        } else {
            try {
                archivo.createNewFile();
                System.out.println("Archivo creado exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }
        }
    }

    // Metodo para agregar una nueva línea de texto a un archivo existente
    public static void agregarLinea(String nombreArchivo, String texto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(texto);
            writer.newLine();
            System.out.println("Línea agregada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Metodo para leer y mostrar el contenido de un archivo
    public static void mostrarContenido(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int contador = 1;
            while ((linea = reader.readLine()) != null) {
                System.out.println(contador + ": " + linea);
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Metodo para leer y mostrar una línea específica del archivo
    public static void mostrarLineaEspecifica(String nombreArchivo, int numeroLinea) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int contador = 1;
            while ((linea = reader.readLine()) != null) {
                if (contador == numeroLinea) {
                    System.out.println("Línea " + numeroLinea + ": " + linea);
                    return;
                }
                contador++;
            }
            System.out.println("La línea especificada no existe.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Metodo main con interfaz por consola
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo:");
        String nombreArchivo = scanner.nextLine();

        try {
            verificarArchivo(nombreArchivo);
            System.out.println("El archivo ya existe.");
        } catch (ArchivoNoEncontradoException e) {
            System.out.println(e.getMessage());
            try {
                crearArchivo(nombreArchivo);
            } catch (ArchivoYaExisteException ex) {
                System.out.println(ex.getMessage());
            }
        }

        int opcion;
        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Agregar nueva línea");
            System.out.println("2. Mostrar contenido del archivo");
            System.out.println("3. Mostrar línea específica");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el texto a agregar: ");
                    String texto = scanner.nextLine();
                    agregarLinea(nombreArchivo, texto);
                    break;
                case 2:
                    mostrarContenido(nombreArchivo);
                    break;
                case 3:
                    System.out.print("Ingrese el número de línea que desea ver: ");
                    int numLinea = scanner.nextInt();
                    mostrarLineaEspecifica(nombreArchivo, numLinea);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}