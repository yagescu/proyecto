package src.Vista;

import src.Modelo.Artista;

import java.util.List;

public class ArtistaView {
    public void mostrarArtistas(List<Artista> artistas) {
        System.out.println("\nLista de Artistas:");
        for (Artista artista : artistas) {
            System.out.println("ID: " + artista.getId() + ", Nombre: " + artista.getNombre() +
                    ", Nacionalidad: " + artista.getNacionalidad());
        }
    }

    public void mostrarMensaje(String s) {
        System.out.println(s);
    }
}
