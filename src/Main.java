import Controlador.ArtistaController;
import Controlador.ExposicionController;
import Modelo.*;
import Vista.ArtistaView;
import Controlador.ObraDeArteController;
import Vista.ExposicionView;
import Vista.ObraDeArteView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArtistaModel modeloArtista = new ArtistaModel();
        ArtistaView vistaArtista = new ArtistaView();
        ArtistaController controladorArtista = new ArtistaController(modeloArtista, vistaArtista);

        ObraDeArteModel modeloObraDeArte = new ObraDeArteModel();
        ObraDeArteView vistaObraDeArte = new ObraDeArteView();
        ObraDeArteController controladorObraDeArte = new ObraDeArteController(modeloObraDeArte, vistaObraDeArte, modeloArtista);

        ExposicionModel modeloExposicion = new ExposicionModel();
        ExposicionView vistaExposicion = new ExposicionView();
        ExposicionController controladorExposicion = new ExposicionController(modeloExposicion, vistaExposicion, controladorArtista, controladorObraDeArte);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelecciona una opción:");
            System.out.println("1) Artistas");
            System.out.println("2) Obra de Arte");
            System.out.println("3) Exposicion");
            System.out.println("4) Salir");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("Seleccionaste Artistas");
                        manejarArtistas(controladorArtista, scanner);
                        break;
                    case 2:

                        System.out.println("Seleccionaste Obra de Arte");
                        manejarObraDeArte(controladorArtista, controladorObraDeArte, scanner);
                        break;
                    case 3:
                        System.out.println("Seleccionaste Exposicion");
                        manejarExposiciones(controladorExposicion, controladorArtista, scanner);
                        break;
                    case 4:
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo:");
                scanner.nextLine();
            }
        }
    }

    private static void manejarArtistas(ArtistaController controlador, Scanner scanner) {

        while (true) {
            // Menú de Artistas
            System.out.println("\nMenú de Artistas:");
            System.out.println("1) Agregar Artista");
            System.out.println("2) Ver Artistas");
            System.out.println("3) Eliminar Artista");
            System.out.println("4) Editar Artista");
            System.out.println("5) Volver al Menú Principal");

            try {
                int opcionArtistas = scanner.nextInt();

                switch (opcionArtistas) {
                    case 1:
                        while (true) {
                            System.out.print("Ingrese el nombre del artista: ");
                            String nombre = scanner.next();

                            //en nombre y nacionalidad queremos que solo se introduzca las letras
                            if (!isValidString(nombre)) {
                                System.out.println("Error: Por favor, ingresa solo letras en el nombre.");
                                continue;
                            }
                            while (true) {
                                System.out.print("Ingrese la nacionalidad del artista: ");
                                String nacionalidad = scanner.next();

                                if (!isValidString(nacionalidad)) {
                                    System.out.println("Error: Por favor, ingresa solo letras en la nacionalidad.");
                                    continue;
                                }
                                controlador.agregarArtista(nombre, nacionalidad);
                                break;
                            }
                            break;
                        }

                    case 2:
                        // Mostrar los artistas del sistema
                        controlador.vista.mostrarArtistas(ArtistaModel.verArtistas());
                        break;
                    case 3:
                        controlador.eliminarArtista();
                        break;
                    case 4:
                        controlador.editarArtista();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo.");
                scanner.nextLine();
            }
        }
    }

    private static boolean isValidString(String input) {
        return input.matches("[a-zA-Z]+");
    }

    private static void manejarObraDeArte(ArtistaController controladorArtista, ObraDeArteController controlador, Scanner scanner) {
        while (true) {
            // Menú de Obra de Arte
            System.out.println("\nMenú de Obras de Arte:");
            System.out.println("1) Agregar obra de arte");
            System.out.println("2) Ver obras de arte");
            System.out.println("3) Eliminar obra de arte");
            System.out.println("4) Editar obra de arte");
            System.out.println("5) Volver al Menú Principal");

            int opcionObras = scanner.nextInt();

            switch (opcionObras) {
                case 1:
                    // Mostrar la lista de artistas antes de agregar una nueva obra de arte
                   controladorArtista.modelo.obtenerListaDeArtistasComoCadenas();
                  //  controladorArtista.modelo.verArtistas();
                    System.out.print("Ingrese el ID del artista para la nueva obra de arte: ");
                    int idArtista = scanner.nextInt();
                    scanner.nextLine();

                    // Obtener el objeto Artista correspondiente al ID proporcionado
                    Artista artista = controladorArtista.modelo.obtenerArtistaPorId(idArtista);

                    if (artista != null) {
                        System.out.print("Ingrese el título de la obra de arte: ");
                        String titulo = scanner.nextLine();

                        System.out.print("Ingrese el año de la obra de arte: ");
                        int anyo = scanner.nextInt();
                        scanner.nextLine();

                        ObraDeArte obraDeArte = new ObraDeArte(titulo, anyo, artista);
                        controlador.agregarObraDeArte(obraDeArte);

                    } else {
                        System.out.println("Artista no encontrado.");
                    }
                    break;
                case 2:
                    // Mostrar las obras de arte del sistema
                    controlador.vista.mostrarObrasDeArte(ObraDeArteModel.verObraDeArte());
                    break;
                case 3:
                    controlador.eliminarObraDeArte();
                    break;
                case 4:
                    controlador.editarObraDeArte(controladorArtista);
                    break;
                case 5:
                    scanner.nextLine();
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static void manejarExposiciones(ExposicionController controladorExposicion, ArtistaController controladorArtista, Scanner scanner) {
        while (true) {
            // Menú de Exposiciones
            System.out.println("\nMenú de Exposiciones:");
            System.out.println("1) Crear Exposición");
            System.out.println("2) Ver Exposiciones");
            System.out.println("3) Eliminar Exposición");
            System.out.println("4) Editar Exposición");
            System.out.println("5) Exportar Exposicones a XML");
            System.out.println("6) Volver al Menú Principal");

            int opcionExposicion = scanner.nextInt();
            scanner.nextLine();

            switch (opcionExposicion) {
                case 1:
                    // Solicitar datos de la exposición
                    System.out.print("Ingrese el nombre de la exposicion: ");
                    String nombreExposicion = scanner.nextLine();

                    System.out.print("Ingrese la fecha de la exposicion: ");
                    String fechaExposicion = scanner.nextLine();


                    // Crear una nueva instancia de Exposicion con los datos proporcionados
                    Exposicion nuevaExposicion = new Exposicion(nombreExposicion, fechaExposicion);

                    //Comprobar que seleccionan artista existente
                    Artista artistaSeleccionado = null;
                    do {
                        // Mostrar la lista de artistas para que el usuario elija uno
                        controladorArtista.modelo.obtenerListaDeArtistasComoCadenas();
                        System.out.print("Ingrese el ID del artista para la exposicion: ");
                        int idArtista = scanner.nextInt();
                        scanner.nextLine();

                        // Obtener el objeto Artista correspondiente al ID proporcionado
                        artistaSeleccionado = controladorArtista.modelo.obtenerArtistaPorId(idArtista);
                    } while (artistaSeleccionado == null);

                    // Mostrar las obras del artista seleccionado
                    controladorArtista.modelo.mostrarObrasDeArtista(artistaSeleccionado.getObrasDeArte());

                    // Solicitar al usuario que elija las obras que desea incluir en la exposición
                    System.out.print("Ingrese el ID de la obra para agregar a la exposicion (0 para finalizar): ");
                    int idObra;
                    while ((idObra = scanner.nextInt()) != 0) {
                        // Obtener la obra correspondiente al ID proporcionado
                        ObraDeArte obraSeleccionada = controladorArtista.modelo.obtenerObraDeArtistaPorId(idObra, artistaSeleccionado);

                        if (obraSeleccionada != null) {
                            // Agregar la obra a la exposición
                            nuevaExposicion.adddObra(obraSeleccionada);
                            System.out.println("Obra agregada a la exposicion.");
                        } else {
                            System.out.println("Obra no encontrada.");
                        }

                        // Solicitar la siguiente obra o finalizar
                        System.out.print("Ingrese el ID de la siguiente obra para agregar a la exposicion (0 para finalizar): ");
                    }

                    controladorExposicion.crearExposicion(nuevaExposicion);
                    break;
                case 2:
                    controladorExposicion.verExposiciones();
                    break;
                case 3:
                    controladorExposicion.eliminarExposicion();
                    break;
                case 4:
                    controladorExposicion.editarExposicion();
                    break;
                case 5:
                    // controladorExposicion.ExportarExposicionesAXML();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
            }
        }
    }
}
