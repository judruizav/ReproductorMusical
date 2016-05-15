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
public class Servicio {
    private ReproductorMusical reproductorMusical;
    
    public Servicio(){
      this.reproductorMusical= new ReproductorMusical();    
    }
    
    public Usuario buscarUsuario(String buscar){
        Usuario usuarioEncontrado= null;
        for(int i=0; i < this.reproductorMusical.getUsuarios().size(); i++){
          if(this.reproductorMusical.getUsuarios().get(i).getNombreReal().equals(buscar)||
             this.reproductorMusical.getUsuarios().get(i).getNombreDeUsuario().equals(buscar)||
             this.reproductorMusical.getUsuarios().get(i).getCorreo().equals(buscar)){
              usuarioEncontrado=this.reproductorMusical.getUsuarios().get(i);
          }
        }
        return usuarioEncontrado;
    }
    
    public Album buscarAlbum(String nombre){
      Album albumEncontrado= null;
      for(int i=0; i<this.reproductorMusical.getAlbumes().size(); i++){
        if(this.reproductorMusical.getAlbumes().get(i).getNombre().equals(nombre)){
          albumEncontrado= this.reproductorMusical.getAlbumes().get(i);    
        }    
      }
      return albumEncontrado;          
    }
    
    public Cancion buscarCancion(String nombre){
      Cancion cancionEncontrada= null;
      for(int i=0; i<this.reproductorMusical.getAlbumes().size(); i++){
        for(int j=0; j<this.reproductorMusical.getAlbumes().get(i).getCanciones().size(); j++){
          if(this.reproductorMusical.getAlbumes().get(i).getCanciones().get(j).getNombre().equals(nombre)||
             this.reproductorMusical.getAlbumes().get(i).getCanciones().get(j).getNombreArchivo().equals(nombre)){
            cancionEncontrada= this.reproductorMusical.getAlbumes().get(i).getCanciones().get(j);      
          }
        }    
      }
      return cancionEncontrada;
    }
    
    public void cargarAlbum(String nombreAlbum){
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<BibliotecaMusical> bibliotecas = new ArrayList<BibliotecaMusical>();    
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album = new Album(nombreAlbum, artistas, usuarios, bibliotecas);
        this.reproductorMusical.getAlbumes().add(album);
    }
    
    public void cargarCancion(String nombreArchiv, String nombre, ArrayList<String> nombresArtista, String nombreAlbum){
      ArrayList<Playlist> playlists= new ArrayList<Playlist>();
      ArrayList<Artista> artistas= new ArrayList<Artista>();
      Album album= buscarAlbum(nombreAlbum); 
      for(int i=0; i<nombresArtista.size(); i++){
        Artista artista= new Artista(nombresArtista.get(i));
        artistas.add(artista);
      }
      if(album==null){
        cargarAlbum(nombreAlbum);
      }
      Cancion cancion= new Cancion(nombreArchiv, nombre, artistas, album, playlists);
      album.getCanciones().add(cancion);
    }
    
    public void crearUsuario(String nombreReal, String nombreDeUsuario, String correo, String contraseña){    
      ArrayList<Album> albumes= new ArrayList<Album>();
      Usuario usuario= new Usuario(nombreReal, nombreDeUsuario, correo, contraseña, albumes);
      BibliotecaMusical biblioteca= new BibliotecaMusical(usuario);
      usuario.setBiblioteca(biblioteca);
      this.reproductorMusical.getUsuarios().add(usuario);
    }
    
    public void crearPlayList(String nombre, Usuario usuario){
      ArrayList<Cancion> canciones= new ArrayList<Cancion>();
      Playlist playlist= new Playlist(nombre , canciones, usuario);
      usuario.getPlaylists().add(playlist);
    }
    
}
