package Vista;


import Modelo.ObraDeArte;

import java.util.List;

public class ObraDeArteView {
    public void mostrarObrasDeArte(List<ObraDeArte> obrasDeArte) {
        System.out.println("\nLista de Obras de Arte:");
        for (ObraDeArte obra : obrasDeArte) {
            System.out.println("ID: " + obra.getId() + ", Titulo: " + obra.getTitulo() + ", Añyo: " + obra.getAnyo() +
                    ", Artista: " + obra.getArtista().getNombre());
        }
    }

    public void mostrarMensaje(String s) {
        System.out.println(s);
    }

}
