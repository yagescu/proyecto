package src.Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistaModel {
    private List<Artista> artistas;
    private String rutaArchivo = "src/Ficheros/artistas.dat";
    private static final String ULTIMO_ID_FILE = "src/Ficheros/ultimoId_Artista.dat";
    private int ultimoIdAsignado;

    public ArtistaModel() {
        this.artistas = cargarArtistas();
        this.ultimoIdAsignado = calcularUltimoId();

        // Si no hay artistas, comenzamos desde el ID 1
        if (ultimoIdAsignado == 0) {
            ultimoIdAsignado = 1;
        }
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public void obtenerListaDeArtistasComoCadenas() {

        for (Artista artista : artistas) {
            System.out.println(("ID: " + artista.getId() + ", Nombre: " + artista.getNombre() + ", Nacionalidad: " + artista.getNacionalidad()));
        }
    }

    public Artista obtenerArtistaPorId(int id) {
        for (Artista artista : artistas) {
            if (artista.getId() == id) {
                return artista;
            }
        }
        return null;
    }

    public void agregarArtista(Artista artista) {
        artista.setId(ultimoIdAsignado);
        artistas.add(artista);
        guardarArtistas();
        ultimoIdAsignado++;
        guardarUltimoId();
    }

    public void eliminarArtista(int id) {
        artistas.removeIf(artista -> artista.getId() == id);
        guardarArtistas();
    }

    public void editarArtista(int id, Artista art) {
        for (Artista artista : artistas) {
            if (artista.getId() == id) {
                artista = art;
                break;
            }
        }
        guardarArtistas();
    }

    public boolean artistaExiste(int id) {
        return artistas.stream().anyMatch(artista -> artista.getId() == id);
    }

    private void guardarArtistas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(artistas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //     oos.close();  El cierre del ObjectOutputStream se manejara automáticamente al salir del bloque try.
    }

    private List<Artista> cargarArtistas() {
        if (new File(rutaArchivo).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
                return (List<Artista>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private int calcularUltimoId() {
        // Se carga el último ID desde el archivo
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ULTIMO_ID_FILE))) {
            return ois.readInt();
        } catch (IOException e) {
            // En caso de error, se devuelve 0
            return 0;
        }
    }

    private void guardarUltimoId() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ULTIMO_ID_FILE))) {
            oos.writeInt(ultimoIdAsignado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarObrasDeArtista(List<ObraDeArte> obrasDeArte) {
        if (obrasDeArte == null || obrasDeArte.isEmpty()) {
            System.out.println("El artista no tiene obras de arte asignadas.");
        } else {
            System.out.println("Obras del artista seleccionado:");

            for (ObraDeArte obra : obrasDeArte) {
                System.out.println("ID: " + obra.getId() + ", Título: " + obra.getTitulo());
            }
        }
    }

    public ObraDeArte obtenerObraDeArtistaPorId(int idObra, Artista artistaSeleccionado) {
        // Obtener la lista de obras del artista
        List<ObraDeArte> obrasDelArtista = artistaSeleccionado.getObrasDeArte();

        // Buscar la obra con el ID proporcionado
        for (ObraDeArte obra : obrasDelArtista) {
            if (obra.getId() == idObra) {
                return obra;
            }
        }
        return null;
    }
}

