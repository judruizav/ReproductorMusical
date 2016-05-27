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
public class BibliotecaMusical {
    private Usuario usuario;
    private ArrayList<Album> albumes;
    private ArrayList<Playlist> playlists;

    public BibliotecaMusical(Usuario usuario) {
        this.usuario = usuario;
        this.albumes = usuario.getAlbumes();
        this.playlists = usuario.getPlaylists();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }    
}
