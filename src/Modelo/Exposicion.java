package src.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exposicion implements Serializable {
    private static int contadorId = 1;
    private int id;
    private String nombre;
    private String fecha;
    private List<ObraDeArte> obras = new ArrayList<ObraDeArte>();

    public Exposicion(String nombre, String fecha, List<ObraDeArte> obras) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.fecha = fecha;
        this.obras = obras;
    }

    public Exposicion(String nombre, String fecha) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ObraDeArte> getObras() {
        return obras;
    }

    public void setObras(List<ObraDeArte> obras) {
        this.obras = obras;
    }

    public void adddObra(ObraDeArte obra) {
        this.obras.add(obra);
    }


    @Override
    public String toString() {
        return "Exposicion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", obras=" + obras +
                '}';
    }
}