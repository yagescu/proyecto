package src.Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ObraDeArteModel {
    private List<ObraDeArte> obrasDeArte;
    private String rutaArchivo = "src/Ficheros/obrasDeArte.dat";
    private static final String ULTIMO_ID_FILE = "src/Ficheros/ultimoId_ObraDeArte.dat";
    private int ultimoIdAsignado;

    public ObraDeArteModel() {
        this.obrasDeArte = cargarObrasDeArte();
        this.ultimoIdAsignado = calcularUltimoId();

        // Si no hay obras de arte, comenzamos desde el ID 1
        if (ultimoIdAsignado == 0) {
            ultimoIdAsignado = 1;
        }
    }

    public List<ObraDeArte> getObrasDeArte() {
        return obrasDeArte;
    }

    public void agregarObraDeArte(ObraDeArte obraDeArte) {

        if (obraDeArte.getArtista() != null) {
            obraDeArte.setId(ultimoIdAsignado);
            obrasDeArte.add(obraDeArte);
            guardarObrasDeArte();
            ultimoIdAsignado++;
            guardarUltimoId();
        } else {
            System.out.println("Artista no encontrado.");
        }
    }

    public void eliminarObraDeArte(int id) {
        obrasDeArte.removeIf(obra -> obra.getId() == id);
        guardarObrasDeArte();
    }

    public void editarObraDeArte(int id, ObraDeArte oda, Artista nuevoArtista) {
        for (ObraDeArte obra : obrasDeArte) {
            if (obra.getId() == id) {
                obra = oda;
                obra.setArtista(nuevoArtista);
                break;
            }
        }
        guardarObrasDeArte();
    }

    public boolean obraExiste(int id) {
        return obrasDeArte.stream().anyMatch(obra -> obra.getId() == id);
    }

    private void guardarObrasDeArte() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(obrasDeArte);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //     oos.close();  El cierre del ObjectOutputStream se manejará automáticamente al salir del bloque try.
    }

    private List<ObraDeArte> cargarObrasDeArte() {
        if (new File(rutaArchivo).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
                return (List<ObraDeArte>) ois.readObject();
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

    public ObraDeArte obtenerObraDeArtePorId(int id) {
        for (ObraDeArte obraDeArte : obrasDeArte) {
            if (obraDeArte.getId() == id) {
                return obraDeArte;
            }
        }
        return null;
    }
}
