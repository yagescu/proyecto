package src.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Artista implements Serializable {
    private static int contadorId = 1;
    private int id;
    private String nombre;
    private String nacionalidad;
    private List<ObraDeArte> obrasDeArte = new ArrayList<ObraDeArte>();

    public Artista(String nombre, String nacionalidad) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void addObrasDeArte(ObraDeArte obrasDeArte) {
        this.obrasDeArte.add(obrasDeArte);
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ObraDeArte> getObrasDeArte() {
        return obrasDeArte;
    }

    public ObraDeArte obraExistente(int idObra) {
        for (ObraDeArte obraDeArte : obrasDeArte) {
            if (obraDeArte.getId() == idObra) {
                return obraDeArte;
            }
        }
        return null;
    }
}