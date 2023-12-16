package Vista;

import Modelo.Exposicion;

import java.util.List;

public class ExposicionView {

    public void mostrarExposiciones(List<Exposicion> exposiciones) {
        System.out.println("\nLista de Exposiciones:");
        for (Exposicion exposicion : exposiciones) {
            System.out.println("ID: " + exposicion.getId() + ", Nombre: " + exposicion.getNombre() + ", Fecha: " + exposicion.getFecha() + exposicion.getObras());
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
