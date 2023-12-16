package Modelo;

import java.io.Serializable;


public class ObraDeArte implements Serializable {
    private static int contadorId = 1;
    private int id;
    private String titulo;
    private int anyo;
    private Artista artista;

    public ObraDeArte(String titulo, int anyo, Artista artista) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.anyo = anyo;
        this.artista = artista;
        artista.addObrasDeArte(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
        artista.addObrasDeArte(this);
    }

    @Override
    public String toString() {
        return "ObraDeArte{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anyo=" + anyo +
                ", artista=" + artista +
                '}';
    }
}