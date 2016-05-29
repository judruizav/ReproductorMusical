/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Julian
 */
public class Album {
    private String nombre;
    private Genero genero;
    private String año;
    private String imagenArchiv;
    private ArrayList<Cancion> canciones;
    private ArrayList<Artista> artistas;
    private ArrayList<Usuario> usuarios;
    private ArrayList<BibliotecaMusical> bibliotecas;

    public Album(String nombre, ArrayList<Artista> artistas, ArrayList<Usuario> usuarios, ArrayList<BibliotecaMusical> bibliotecas) {
        this.nombre = nombre;
        this.artistas = artistas;
        this.usuarios = usuarios;
        this.bibliotecas = bibliotecas;
        this.canciones= new ArrayList<Cancion>();    
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setAño(String año) {
        this.año = año;
    }
    
    public void setImagenArchiv(String imagen){
        this.imagenArchiv= imagen;    
    }

    public String getNombre() {
        return nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getAño() {
        return año;
    }

    public String getImagenArchiv() {
        return imagenArchiv;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public ArrayList<Artista> getArtistas() {
        return artistas;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<BibliotecaMusical> getBibliotecas() {
        return bibliotecas;
    }
}
