package Modelo;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class ObraDeArteModel {
    private List<ObraDeArte> obrasDeArte = new ArrayList<>();
    // private String rutaArchivo = "src/Ficheros/obrasDeArte.dat";
//private static final String ULTIMO_ID_FILE = "src/Ficheros/ultimoId_ObraDeArte.dat";
    private int ultimoIdAsignado;
    static String url = "mongodb://localhost:27017";
/*    public ObraDeArteModel() {
        this.obrasDeArte = cargarObrasDeArte();
        this.ultimoIdAsignado = calcularUltimoId();

        // Si no hay obras de arte, comenzamos desde el ID 1
        if (ultimoIdAsignado == 0) {
            ultimoIdAsignado = 1;
        }
    }*/

    public List<ObraDeArte> getObrasDeArte() {
        return obrasDeArte;
    }

    public void agregarObraDeArte(ObraDeArte obraDeArte) {

     /*   if (obraDeArte.getArtista() != null) {
            obraDeArte.setId(ultimoIdAsignado);
            obrasDeArte.add(obraDeArte);
            guardarObrasDeArte();
            ultimoIdAsignado++;
            //  guardarUltimoId();
        } else {
            System.out.println("Artista no encontrado.");
        }*/
        obraDeArte.setId(ultimoIdAsignado);
        obrasDeArte.add(obraDeArte);
        guardarObrasDeArte();
        ultimoIdAsignado++;
    }

    public static List<ObraDeArte> verObraDeArte() {
        List<ObraDeArte> obrasDeArte = new ArrayList<>();
        ObraDeArte obraDeArte = null;
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("ObraDeArte");

            FindIterable<Document> iterDoc = collection.find();
            for (Document d : iterDoc) {
                Gson g = new Gson();
                String obraJSON = d.toJson();
                obraDeArte = g.fromJson(obraJSON, ObraDeArte.class);
                obrasDeArte.add(obraDeArte);
            }

        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al coger id");
        }
        return obrasDeArte;
    }


    public void eliminarObraDeArte(int id) {
       /* obrasDeArte.removeIf(obra -> obra.getId() == id);
        guardarObrasDeArte();*/

        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("ObrasDeArte");

            collection.deleteOne(Filters.eq("id", id));

        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al eliminar obra de arte.");
        }
    }

    public void editarObraDeArte(int id, ObraDeArte oda, Artista nuevoArtista) {
   /*     for (ObraDeArte obra : obrasDeArte) {
            if (obra.getId() == id) {
                obra = oda;
                obra.setArtista(nuevoArtista);
                break;
            }
        }
        guardarObrasDeArte();*/

        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("ObrasDeArte");

            // Filtrar por la ID de la obra de arte
            Document filter = new Document("id", oda.getId());

            // Crear un documento con los campos a actualizar en la obra de arte
            Document content = new Document()
                    .append("titulo", oda.getTitulo())
                    .append("anyo", oda.getAnyo());

            // Si se proporciona un nuevo artista, actualizar también la información del artista
            if (nuevoArtista != null) {
                // Crear un subdocumento con la información del nuevo artista
                Document artistaDoc = new Document()
                        .append("id", nuevoArtista.getId())
                        .append("nombre", nuevoArtista.getNombre())
                        .append("nacionalidad", nuevoArtista.getNacionalidad());

                // Agregar el subdocumento del artista al documento de la obra de arte
                content.append("artista", artistaDoc);
            }

            // Crear el documento de actualización con los campos a establecer
            Document update = new Document("$set", content);

            // Actualizar la obra de arte en la base de datos
            collection.updateOne(filter, update);

        } catch (MongoException e) {
            // En caso de error, manejar la excepción
            System.out.println("Error al editar obra de arte");
        }
    }

    public boolean obraExiste(int id) {
        Document document1 = null;
        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("ObrasDeArte");

            document1 = collection.find(eq("id", id)).first();


        } catch (MongoException e) {
            // En caso de error, se devuelve 0
            System.out.println("Error al eliminar obra de arte");
        }
        if (document1 == null) {
            return false;
        } else
            return true;
    }

    private void guardarObrasDeArte() {
       /* try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(obrasDeArte);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //     oos.close();  El cierre del ObjectOutputStream se manejará automáticamente al salir del bloque try.*/

        try (MongoClient mongo = MongoClients.create(url)) {
            MongoDatabase database = mongo.getDatabase("ProyectoTema5");
            MongoCollection<Document> collection = database.getCollection("ObrasDeArte");

            for (ObraDeArte oda : obrasDeArte) {
                Document document = collection.find(eq("titulo", oda.getTitulo())).first();
                if (document == null) {
                    while (true) {
                        Document document1 = collection.find(eq("id", oda.getId())).first();
                        if (document1 != null)
                            oda.setId(oda.getId() + 1);
                        else break;
                    }
                    List<Document> events = new ArrayList<>();
                    events.add(new Document()
                            .append("id", oda.getId())
                            .append("titulo", oda.getTitulo())
                            .append("anyo", oda.getAnyo())
                    );

                    // Obtener información del artista asociado a la obra de arte
                    Artista artista = oda.getArtista();
                    Document artistaDoc = new Document()
                            .append("id", artista.getId())
                            .append("nombre", artista.getNombre())
                            .append("nacionalidad", artista.getNacionalidad());

                    // Crear un documento para la obra de arte
                    Document obraDoc = new Document()
                            .append("id", oda.getId())
                            .append("titulo", oda.getTitulo())
                            .append("anyo", oda.getAnyo())
                            .append("artista", artistaDoc);

                    // Insertar la obra de arte en la colección
                    collection.insertOne(obraDoc);

                    System.out.println("Obra de arte '" + oda.getTitulo() + "' añadida.");
                } else {
                    System.out.println("Obra de arte '" + oda.getTitulo() + "' ya añadida.");
                }
            }
        } catch (MongoException e) {
            System.out.println("Error al guardar obra de arte");
        }
    }

/*    private List<ObraDeArte> cargarObrasDeArte() {
        if (new File(rutaArchivo).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
                return (List<ObraDeArte>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }*/

    /*private int calcularUltimoId() {
        // Se carga el último ID desde el archivo
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ULTIMO_ID_FILE))) {
            return ois.readInt();
        } catch (IOException e) {
            // En caso de error, se devuelve 0
            return 0;
        }
    }*/

/*    private void guardarUltimoId() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ULTIMO_ID_FILE))) {
            oos.writeInt(ultimoIdAsignado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public ObraDeArte obtenerObraDeArtePorId(int id) {
        for (ObraDeArte obraDeArte : obrasDeArte) {
            if (obraDeArte.getId() == id) {
                return obraDeArte;
            }
        }
        return null;
    }
}
