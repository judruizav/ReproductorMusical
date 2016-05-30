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
public class Usuario {
  private String nombreReal;
  private String nombreDeUsuario;
  private String correo;
  private String contraseña;
  private ArrayList<Album> albumes;
  private ArrayList<Playlist> playlists;
  private BibliotecaMusical biblioteca;
  private boolean sesionIniciada;

    public Usuario(String nombreReal, String nombreDeUsuario, String correo, String contraseña, ArrayList<Album> albumes) {
        this.nombreReal = nombreReal;
        this.nombreDeUsuario = nombreDeUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
        this.albumes = albumes;
        this.playlists = new ArrayList<Playlist>();
        this.sesionIniciada=false;
    }

    public boolean isSesionIniciada() {
        return sesionIniciada;
    }

    public void setSesionIniciada(boolean sesionIniciada) {
        this.sesionIniciada = sesionIniciada;
    }
    
    

    public void setBiblioteca(BibliotecaMusical biblioteca) {
        this.biblioteca = biblioteca;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public BibliotecaMusical getBiblioteca() {
        return biblioteca;
    }
}
