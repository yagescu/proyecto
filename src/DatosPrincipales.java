import Modelo.*;

import java.util.ArrayList;
import java.util.List;

public class DatosPrincipales {

    public static void main(String[] args) {

        ObraDeArteModel obraDeArteModel = new ObraDeArteModel();
        ArtistaModel artistaModel = new ArtistaModel();
        ExposicionModel exposicionModel = new ExposicionModel();

        // Agregar algunos artistas, obras y exposiciones de ejemplo si la lista está vacía

        Artista artista1 = new Artista("Leonardo da Vinci", "Italiana");
        Artista artista2 = new Artista("Vincent van Gogh", "Neerlandesa");
        Artista artista3 = new Artista("Pablo Picasso", "Española");
        Artista artista4 = new Artista("Salvador Dali", "Española");

        ObraDeArte obra1 = new ObraDeArte("La Gioconda", 1506, artista1);
        ObraDeArte obra2 = new ObraDeArte("La Noche Estrellada", 1889, artista2);
        ObraDeArte obra3 = new ObraDeArte("Guernica", 1937, artista3);
        ObraDeArte obra4 = new ObraDeArte("La Persistencia de la Memoria", 1931, artista4);

        List<ObraDeArte> obrasExposicion1 = new ArrayList<>();
        obrasExposicion1.add(obra1);
        obrasExposicion1.add(obra2);

        List<ObraDeArte> obrasExposicion2 = new ArrayList<>();
        obrasExposicion2.add(obra3);
        obrasExposicion2.add(obra4);

        Exposicion exposicion1 = new Exposicion("Exposicion1", "24/11/2023", obrasExposicion1);
        Exposicion exposicion2 = new Exposicion("Exposicion2", "28/11/2023", obrasExposicion2);

        artistaModel.agregarArtista(artista1);
        artistaModel.agregarArtista(artista2);
        artistaModel.agregarArtista(artista3);
        artistaModel.agregarArtista(artista4);

        obraDeArteModel.agregarObraDeArte(obra1);
        obraDeArteModel.agregarObraDeArte(obra2);
        obraDeArteModel.agregarObraDeArte(obra3);
        obraDeArteModel.agregarObraDeArte(obra4);

        exposicionModel.agregarExposicion(exposicion1);
        exposicionModel.agregarExposicion(exposicion2);
    }
}
