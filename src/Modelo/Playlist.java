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
public class Playlist {
  private String nombre;
  private ArrayList<Cancion> canciones;
  private Usuario usuario;

    public Playlist(String nombre, ArrayList<Cancion> canciones, Usuario usuario) {
        this.nombre = nombre;
        this.canciones = canciones;
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }
  
    
}
