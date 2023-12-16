package Controlador;

import Modelo.Artista;
import Modelo.ArtistaModel;

import Modelo.ObraDeArte;
import Modelo.ObraDeArteModel;
import Vista.ObraDeArteView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ObraDeArteController {

    public ObraDeArteModel modelo;
    public ObraDeArteView vista;
    public ArtistaModel artistaModel;
    private Scanner scanner;

    public ObraDeArteController(ObraDeArteModel modelo, ObraDeArteView vista, ArtistaModel artistaModel) {
        this.modelo = modelo;
        this.vista = vista;
        this.artistaModel = artistaModel;
        this.scanner = new Scanner(System.in);
    }

    public void agregarObraDeArte(ObraDeArte obraDeArte) {

        modelo.agregarObraDeArte(obraDeArte);
        vista.mostrarMensaje("Se ha agregado una nueva obra de arte.");

        // Mostrar las obras de arte despues de agregar una nueva
        vista.mostrarObrasDeArte(modelo.verObraDeArte());
    }

    public void eliminarObraDeArte() {
        vista.mostrarObrasDeArte(modelo.verObraDeArte());

        // Solicitar al usuario que ingrese el ID de la obra de arte que desea eliminar
        System.out.print("Ingrese el ID de la obra de arte que desea eliminar: ");
        while (true) {
            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                // Verificar si la obra con el ID proporcionado existe
                boolean obraExiste = modelo.obraExiste(id);
                if (obraExiste) {
                    modelo.eliminarObraDeArte(id);
                    vista.mostrarMensaje("Se ha eliminado obra de arte.");
                    vista.mostrarObrasDeArte(modelo.verObraDeArte());
                } else {
                    System.out.println("Error: Obra de arte no encontrado.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.print("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo:");
                scanner.nextLine();
            }
        }
    }

    public void editarObraDeArte(ArtistaController controladorArtista) {
        vista.mostrarObrasDeArte(modelo.verObraDeArte());
        System.out.println();

        // Solicitar al usuario que ingrese el ID de la obra de arte que desea editar
        System.out.print("Ingrese el ID de la obra de arte que desea editar: ");

        int id = 0;

        while (true) {
            try {
                String input = scanner.nextLine();
                id = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo.");
                System.out.print("Ingrese el ID de la obra de arte que desea editar: ");
            }
        }

        // Verificar si la obra de arte con el ID proporcionado existe
        boolean obraEncontrada = modelo.obraExiste(id);

        if (obraEncontrada) {
            ObraDeArte obraDeArteActual = modelo.obtenerObraDeArtePorId(id);

            System.out.println("Titulo: " + obraDeArteActual.getTitulo());
            System.out.print("Ingrese el nuevo título de la obra de arte (o pulse 0 para no cambiar): ");
            String nuevoTitulo = scanner.nextLine();

            if (!nuevoTitulo.equals("0")) {
                obraDeArteActual.setTitulo(nuevoTitulo);
            }

            System.out.println("Año: " + obraDeArteActual.getAnyo());
            System.out.print("Ingrese el nuevo año de la obra de arte (o pulse 0 para no cambiar): ");

            int nuevoAnyo = 0;

            while (true) {
                try {
                    String input = scanner.nextLine();
                    nuevoAnyo = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo.");
                    System.out.print("Ingrese el nuevo año de la obra de arte (o pulse 0 para no cambiar): ");
                }
            }

            if (nuevoAnyo != 0) {
                obraDeArteActual.setAnyo(nuevoAnyo);
            }

            // Mostrar la lista de artistas para que el usuario elija uno nuevo
            controladorArtista.modelo.obtenerListaDeArtistasComoCadenas();
            int nuevoIdArtista;

            // Pedir al usuario que ingrese el ID del nuevo artista hasta que ingrese un ID válido
            while (true) {
                System.out.print("Ingrese el ID del nuevo artista para la obra de arte (o pulse 0 para no cambiar): ");

                try {
                    String input = scanner.nextLine();
                    nuevoIdArtista = Integer.parseInt(input);

                    // Verificar si el ID del nuevo artista es válido
                    if (nuevoIdArtista == 0 || controladorArtista.modelo.artistaExiste(nuevoIdArtista)) {
                        break;
                    } else {
                        System.out.println("Error: El ID del artista no es válido. Por favor, inténtalo de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo.");
                }
            }

            // Obtener el objeto Artista correspondiente al ID proporcionado
            Artista nuevoArtista = (nuevoIdArtista != 0) ? controladorArtista.modelo.obtenerArtistaPorId(nuevoIdArtista) : null;

            // Llamar al método editarObraDeArte con los nuevos datos
            modelo.editarObraDeArte(id, obraDeArteActual, nuevoArtista);
            System.out.println("Obra de arte editada correctamente.");
            vista.mostrarObrasDeArte(modelo.verObraDeArte());
        } else {
            System.out.println("Obra de arte no encontrada.");
        }
    }
}
