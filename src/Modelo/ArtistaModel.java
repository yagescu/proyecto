package Modelo;

import com.google.gson.Gson;
import com.mongodb.DocumentToDBRefTransformer;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ArtistaModel {
    private List<Artista> artistas=new ArrayList<>();


   // private String rutaArchivo = "src/Ficheros/artistas.dat";
  //  private static final String ULTIMO_ID_FILE = "src/Ficheros/ultimoId_Artista.dat";
    private int ultimoIdAsignado;
    static String url = "mongodb://localhost:27017";

 /*   public ArtistaModel() {
        this.artistas = cargarArtistas();
        this.ultimoIdAsignado = calcularUltimoId();

        // Si no hay artistas, comenzamos desde el ID 1
        if (ultimoIdAsignado == 0) {
            ultimoIdAsignado = 1;
        }
    }*/

    public List<Artista> getArtistas() {
        return artistas;
    }

    public void obtenerListaDeArtistasComoCadenas() {

        for (Artista artista : artistas) {
            System.out.println(("ID: " + artista.getId() + ", Nombre: " + artista.getNombre() + ", Nacionalidad: " + artista.getNacionalidad()));
        }
    }

    public Artista obtenerArtistaPorId(int id) {
        Artista artista = null;
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("Artistas");

            Document document = collection.find(eq("id", id)).first();

            if (document != null) {
                Gson g = new Gson();
                String artistaJSON = document.toJson();
                artista = g.fromJson(artistaJSON, Artista.class);
            }

        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al editar artista");
        }
        return artista;
    }

    public void agregarArtista(Artista artista) {
        artista.setId(ultimoIdAsignado);
        artistas.add(artista);
        guardarArtistas();
        ultimoIdAsignado++;
     //   guardarUltimoId();
    }

    public void eliminarArtista(int id) {
    /*    artistas.removeIf(artista -> artista.getId() == id);
        guardarArtistas();*/
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("Artistas");

            collection.deleteOne(Filters.eq("id", id));

        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al eliminar artista");
        }

    }

    public void editarArtista(int id, Artista art) {
       /*for (Artista artista : artistas) {
            if (artista.getId() == id) {
                artista = art;
                break;
            }
        }
        guardarArtistas();*/
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("Artistas");
            Document filter = new Document("id", art.getId());
            Document content = new Document()
                    .append("nombre", art.getNombre())
                    .append("nacionalidad", art.getNacionalidad());
            Document update = new Document("$set", content);
            collection.updateOne(filter, update);


        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al editar artista");
        }

    }

    public boolean artistaExiste(int id) {
        Document document1 = null;
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("Artistas");

            document1 = collection.find(eq("id", id)).first();


        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al eliminar artista");
        }
        if (document1 == null) {
            return false;
        } else
            return true;
    }

    private void guardarArtistas() {

        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("Artistas");

            for (Artista art : artistas) {
                Document document = collection.find(eq("nombre", art.getNombre())).first();
                if (document == null) {
                    while (true) {
                        Document document1 = collection.find(eq("id", art.getId())).first();
                        if (document1 != null)
                            art.setId(art.getId() + 1);
                        else break;
                    }
                    List<Document> events = new ArrayList<>();
                    for (ObraDeArte obr : art.getObrasDeArte()) {
                        events.add(new Document()
                                .append("id", obr.getId())
                                .append("titulo", obr.getTitulo())
                                .append("anyo", obr.getAnyo())
                        );
                    }
                    InsertOneResult result = collection.insertOne(new Document()
                            .append("id", art.getId())
                            .append("nombre", art.getNombre())
                            .append("nacionalidad", art.getNacionalidad())
                            .append("obra de arte", events)
                    );
                    System.out.println("Artista" + art.getNombre() + " añadido.");
                } else {
                    System.out.println("Artista" + art.getNombre() + " ya añadido.");
                }
            }


        } catch (MongoException e) {
            System.out.println("Error al guardar artista");
        }
        //     oos.close();  El cierre del ObjectOutputStream se manejara automáticamente al salir del bloque try.
    }

/*
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
*/

    private int calcularUltimoId() {
        // Se carga el último ID desde el archivo
        Artista artista = null;
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("Artistas");

            FindIterable<Document> iterDoc = collection.find();
            for (Document d : iterDoc) {
                Gson g = new Gson();
                String artistaJSON = d.toJson();
                artista = g.fromJson(artistaJSON, Artista.class);
            }

        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al coger id");
        }
        if (artista != null)
            return artista.getId();
        else
            return 0;
    }

    private void guardarUltimoId() {

        /*try (MongoClient mongo = MongoClients.create(url)) {
            //oos.writeInt(ultimoIdAsignado);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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


    public static List<Artista> verArtistas() {
        List<Artista> artistas = new ArrayList<>();
        Artista artista = null;
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("Artistas");

            FindIterable<Document> iterDoc = collection.find();
            for (Document d : iterDoc) {
                Gson g = new Gson();
                String artistaJSON = d.toJson();
                artista = g.fromJson(artistaJSON, Artista.class);
                artistas.add(artista);
            }

        } catch (MongoException e) {

            System.out.println("Error al coger id");
        }
        return artistas;
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

