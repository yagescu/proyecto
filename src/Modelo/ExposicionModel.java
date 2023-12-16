package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExposicionModel {

    private List<Exposicion> exposiciones;
    private String rutaArchivo = "src/Ficheros/exposiciones.dat";
    private static final String ULTIMO_ID_FILE = "src/Ficheros/ultimoId_Exposicion.dat";
    private int ultimoIdAsignado;

    public ExposicionModel() {
        this.exposiciones = cargarExposiciones();
        this.ultimoIdAsignado = calcularUltimoId();

        // Si no hay exposiciones, comenzamos desde el ID 1
        if (ultimoIdAsignado == 0) {
            ultimoIdAsignado = 1;
        }
    }

    public List<Exposicion> getExposiciones() {
        return exposiciones;
    }

    public void agregarExposicion(Exposicion exposicion) {
        exposicion.setId(ultimoIdAsignado);
        exposiciones.add(exposicion);
        guardarExposiciones();
        ultimoIdAsignado++;
        guardarUltimoId();
    }

    public void eliminarExposicion(int id) {
        exposiciones.removeIf(exposicion -> exposicion.getId() == id);
        guardarExposiciones();
    }

    public void editarExposicion(int id, Exposicion exp) {
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getId() == id) {
                exposicion = exp;
                break;
            }
        }
        guardarExposiciones();
    }

    public boolean exposicionExiste(int id) {
        return exposiciones.stream().anyMatch(exposicion -> exposicion.getId() == id);
    }

    private void guardarExposiciones() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(exposiciones);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //     oos.close();  El cierre del ObjectOutputStream se manejará automáticamente al salir del bloque try.

    }

    private List<Exposicion> cargarExposiciones() {
        if (new File(rutaArchivo).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
                return (List<Exposicion>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private int calcularUltimoId() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ULTIMO_ID_FILE))) {
            return ois.readInt();
        } catch (IOException e) {
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

    public Exposicion obtenerExposicionPorId(int id) {
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getId() == id) {
                return exposicion;
            }
        }
        return null;
    }
}
