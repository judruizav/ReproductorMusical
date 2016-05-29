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
public class Artista {
    private String nombre;
    private String reseña;
    private ArrayList<Album> albumes;
    private int numeroCanciones;

    public Artista(String nombre) {
        this.nombre = nombre;
        this.albumes = new ArrayList<Album>();
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public String getNombre() {
        return nombre;
    }

    public String getReseña() {
        return reseña;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }
    
    public int GetNumCancionesAlbum(){
        int numeroCanciones=0;
        for(int i=0; i<this.albumes.size(); i++){
            numeroCanciones += this.albumes.get(i).getCanciones().size();
        }
        return numeroCanciones;
    }
}
