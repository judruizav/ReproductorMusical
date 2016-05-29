/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */

public class Genero {
    
    private String nombre;
    private int numCanciones;
    private ArrayList<Cancion> canciones;
    private ArrayList<Album> albumes; 

    public Genero(String nombre, ArrayList<Cancion> canciones, ArrayList<Album> albumes) {
        this.nombre = nombre;
        this.canciones = canciones;
        this.albumes = albumes;
    }

    public int getNumCanciones() {
        return numCanciones;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(ArrayList<Album> albumes) {
        this.albumes = albumes;
    }
    
    public void setNumCanciones(){
        this.numCanciones=this.canciones.size();
    }
    
    
}
