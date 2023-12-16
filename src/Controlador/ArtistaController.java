package Controlador;

import Modelo.Artista;
import Modelo.ArtistaModel;
import Vista.ArtistaView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ArtistaController {

    public ArtistaModel modelo;
    public ArtistaView vista;
    private Scanner scanner;

    public ArtistaController(ArtistaModel modelo, ArtistaView vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.scanner = new Scanner(System.in);
    }

    public void agregarArtista(String nombre, String nacionalidad) {
        modelo.agregarArtista(new Artista(nombre, nacionalidad));
        vista.mostrarMensaje("Se ha agregado un nuevo artista.");
    }

    public void eliminarArtista() {
        vista.mostrarArtistas(modelo.verArtistas());

        // Solicitar al usuario que ingrese el ID de la artista que desea eliminar
        System.out.print("Ingrese el ID del artista que desea eliminar: ");

        while (true) {
            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                // Verificar si la artista con el ID proporcionado existe
                boolean artistaEncontrada = modelo.artistaExiste(id);

                if (artistaEncontrada) {
                    modelo.eliminarArtista(id);
                    vista.mostrarMensaje("Se ha eliminado artista.");
                    vista.mostrarArtistas(modelo.verArtistas());
                } else {
                    System.out.println("Error: Artista no encontrado.");
                }

                break;
            } catch (InputMismatchException e) {
                System.out.print("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo:");
                scanner.nextLine();
            }
        }
    }


    public void editarArtista() {
        vista.mostrarArtistas(modelo.verArtistas());
        System.out.println();

        // Solicitar al usuario que ingrese el ID de la artista que desea editar
        System.out.print("Ingrese el ID del artista que desea editar: ");

        while (true) {
            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                // Verificar si la artista con el ID proporcionado existe
                boolean artistaEncontrada = modelo.artistaExiste(id);

                if (artistaEncontrada) {

                    Artista artistaActual = modelo.obtenerArtistaPorId(id);

                    System.out.println("Nombre: " + artistaActual.getNombre());
                    System.out.print("Ingrese el nuevo nombre de la artista (o pulse 0 para no cambiar): ");
                    String nuevoNombre = scanner.nextLine();

                    if (!nuevoNombre.equals("0")) {
                        artistaActual.setNombre(nuevoNombre);
                    }

                    System.out.println("Nacionalidad: " + artistaActual.getNacionalidad());
                    System.out.print("Ingrese la nueva nacionalidad de la artista (o pulse 0 para no cambiar): ");
                    String nuevaNacionalidad = scanner.nextLine();

                    if (!nuevaNacionalidad.equals("0")) {
                        artistaActual.setNacionalidad(nuevaNacionalidad);
                    }

                    // Llamar al método editarArtista con los nuevos datos
                    modelo.editarArtista(id, artistaActual);
                    System.out.println("Artista editada correctamente.");
                } else {
                    System.out.println("Artista no encontrada.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.print("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo: ");
                scanner.nextLine();
            }
        }
    }
}


