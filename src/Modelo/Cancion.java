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
public class Cancion {
    private String nombreArchivo;
    private String nombre;
    private ArrayList<Artista> artistas;
    private Album album;
    private String año;
    private String genero;
    private int bpm;
    private String letra;
    private String letraTraduc;
    private ArrayList<Playlist> playlists;

    public Cancion(String nombreArchivo, String nombre, ArrayList<Artista> artistas, Album album, ArrayList<Playlist> playlists) {
        this.nombreArchivo = nombreArchivo;
        this.nombre = nombre;
        this.artistas = artistas;
        this.album = album;
        this.playlists = playlists;
        this.genero = this.album.getGenero();
        this.año= this.album.getAño();
        
    }
    
    public void setBpm(int bpm){
      this.bpm = bpm;   
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public void setLetraTraduc(String letraTraduc) {
        this.letraTraduc = letraTraduc;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Artista> getArtistas() {
        return artistas;
    }

    public Album getAlbum() {
        return album;
    }

    public String getAño() {
        return año;
    }

    public String getGenero() {
        return genero;
    }

    public int getBmp() {
        return bpm;
    }

    public String getLetra() {
        return letra;
    }

    public String getLetraTraduc() {
        return letraTraduc;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }   
}