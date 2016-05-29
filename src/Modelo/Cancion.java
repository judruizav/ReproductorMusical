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
    private Genero genero;
    private int bpm;
    private String letra;
    private String letraTraduc;
    private ArrayList<Playlist> playlists;

    public Cancion(String nombreArchivo, String nombre, Genero genero, ArrayList<Artista> artistas, Album album, ArrayList<Playlist> playlists) {
        this.nombreArchivo = nombreArchivo;
        this.nombre = nombre;
        this.genero = genero;
        this.artistas = artistas;
        this.album = album;
        this.playlists = playlists;
        this.genero = this.album.getGenero();
        this.año= this.album.getAño();
        
    }
    
    public Cancion(String nombre, ArrayList<Artista> artistas, Artista artista){
      this.nombre= nombre;
      artistas.add(artista);
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(ArrayList<Artista> artistas) {
        this.artistas = artistas;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
        genero.getCanciones().add(this);
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getLetraTraduc() {
        return letraTraduc;
    }

    public void setLetraTraduc(String letraTraduc) {
        this.letraTraduc = letraTraduc;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }
}
