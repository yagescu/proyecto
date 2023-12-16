package src.Controlador;

import com.thoughtworks.xstream.XStream;
import src.Modelo.*;
import src.Vista.ExposicionView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExposicionController {

    private ExposicionModel modelo;
    public ExposicionView vista;
    private ArtistaController controladorArtista;
    private ObraDeArteController controladorObraDeArte;
    private Scanner scanner;

    public ExposicionController(ExposicionModel modelo, ExposicionView vistaExposicion, ArtistaController controladorArtista, ObraDeArteController controladorObraDeArte) {
        this.modelo = modelo;
        this.vista = vistaExposicion;
        this.controladorArtista = controladorArtista;
        this.controladorObraDeArte = controladorObraDeArte;
        this.scanner = new Scanner(System.in);
    }

    public void crearExposicion(Exposicion exposicion) {
        modelo.agregarExposicion(exposicion);
        System.out.println("Exposición creada correctamente.");

        // Mostrar las expo despus de agregar una nueva
        vista.mostrarExposiciones(modelo.getExposiciones());
    }

    public void verExposiciones() {
        vista.mostrarExposiciones(modelo.getExposiciones());
    }

    public void eliminarExposicion() {
        // mostrar exposiciones
        vista.mostrarExposiciones(modelo.getExposiciones());

        // Solicitar al usuario que ingrese el ID de la exposición que desea eliminar
        int id;
        while (true) {
            try {
                System.out.print("Ingrese el ID de la exposición que desea eliminar: ");
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo.");
                scanner.nextLine();
            }
        }

        // Verificar si la exposición con el ID proporcionado existe
        boolean exposicionEncontrada = modelo.exposicionExiste(id);

        if (exposicionEncontrada) {
            // Existe, eliminar la exposición
            modelo.eliminarExposicion(id);
            System.out.println("Exposición eliminada correctamente.");
            vista.mostrarExposiciones(modelo.getExposiciones());
        } else {
            // No existe, mostrar un mensaje de error
            System.out.println("No hay una exposición con el ID proporcionado. Por favor, inténtalo de nuevo.");
        }
    }

    public void editarExposicion() {
        vista.mostrarExposiciones(modelo.getExposiciones());

        // Solicitar al usuario que ingrese el ID de la exposicion que desea editar
        int id;
        while (true) {
            try {
                System.out.print("Ingrese el ID de la exposición que desea editar: ");
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero. Por favor, inténtalo de nuevo.");
                scanner.nextLine();
            }
        }

        // Verificar si la exposicion con el ID proporcionado existe
        boolean exposicionEncontrada = modelo.exposicionExiste(id);

        if (exposicionEncontrada) {
            Exposicion exposicionActual = modelo.obtenerExposicionPorId(id);

            System.out.println("Nombre: " + exposicionActual.getNombre());
            System.out.print("Ingrese el nuevo nombre de la exposición (o pulse 0 para no cambiar): ");
            String nuevoNombre = scanner.nextLine();

            if (!nuevoNombre.equals("0")) {
                exposicionActual.setNombre(nuevoNombre);
            }

            System.out.println("Fecha: " + exposicionActual.getFecha());
            System.out.print("Ingrese la nueva fecha de la exposición (o pulse 0 para no cambiar): ");
            String nuevaFecha = scanner.nextLine();

            if (!nuevaFecha.equals("0")) {
                exposicionActual.setFecha(nuevaFecha);
            }

            System.out.println("¿Desea editar el artista existente? (Sí/No): ");
            String editarArtista = scanner.nextLine().toLowerCase();

            Artista artista = exposicionActual.getObras().get(0).getArtista();
            if (editarArtista.equalsIgnoreCase("si")) {
                Artista nuevoArtista = null;
                do {
                    controladorArtista.modelo.obtenerListaDeArtistasComoCadenas();
                    System.out.print("Ingrese el ID del artista para la exposición (o pulse 0 para no cambiar): ");
                    int nuevoIdArtista = scanner.nextInt();
                    scanner.nextLine();

                    nuevoArtista = controladorArtista.modelo.obtenerArtistaPorId(nuevoIdArtista);

                    if (nuevoArtista != null) {
                        artista = nuevoArtista;
                        exposicionActual.setObras(new ArrayList<>());
                    }
                } while (nuevoArtista == null);
            }

            controladorArtista.modelo.mostrarObrasDeArtista(artista.getObrasDeArte());

            System.out.print("Ingrese el ID de la obra para agregar a la exposición (o pulse 0 para no cambiar): ");
            int idObra;
            while ((idObra = scanner.nextInt()) != 0) {
                ObraDeArte obraSeleccionada = artista.obraExistente(idObra);

                if (obraSeleccionada != null) {
                    exposicionActual.adddObra(obraSeleccionada);
                    System.out.println("Obra agregada a la exposición.");
                } else {
                    System.out.println("Obra no encontrada.");
                }
                System.out.print("Ingrese el ID de la siguiente obra para agregar a la exposición (o pulse 0 para no cambiar): ");
            }

            // Editar la exposicion en el modelo de exposiciones
            modelo.editarExposicion(id, exposicionActual);
            System.out.println("Exposición editada correctamente.");
        } else {
            System.out.println("Exposición no encontrada.");
        }
    }

    public void ExportarExposicionesAXML() {
        try {
            XStream xstream = new XStream();

            //Insertamos los objetos en el XML
            xstream.toXML(modelo.getExposiciones(), new FileOutputStream("src/Ficheros/exposiciones.xml"));
            System.out.println("Creado fichero XML....");
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelo.getExposiciones();
    }
}