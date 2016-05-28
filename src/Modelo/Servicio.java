/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.exception.BuscarException;
import Modelo.exception.PlaylistException;
import Modelo.exception.UsuarioException;
import java.io.*;
import java.net.*;
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
    
    public Playlist buscarPlaylist(String nombre, Usuario usuario){
        Playlist playlistEncontrada = null;
        for(int i=0; i<usuario.getPlaylists().size(); i++){
            if(nombre.equals(usuario.getPlaylists().get(i).getNombre())){
                playlistEncontrada = usuario.getPlaylists().get(i);
            }
        }
        return playlistEncontrada;
    }
    
    public void cargarAlbum(String nombreAlbum, Genero genero) throws FileNotFoundException{
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<BibliotecaMusical> bibliotecas = new ArrayList<BibliotecaMusical>();    
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album = new Album(nombreAlbum, genero, artistas, usuarios, bibliotecas);
        this.reproductorMusical.getAlbumes().add(album);
    }
    
    public void cargarCancion(String nombreArchiv, String nombre, Genero genero, ArrayList<String> nombresArtista, String nombreAlbum) throws BuscarException, FileNotFoundException{
        ArrayList<Playlist> playlists= new ArrayList<Playlist>();
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album= buscarAlbum(nombreAlbum); 
        for(int i=0; i<nombresArtista.size(); i++){
            Artista artista= new Artista(nombresArtista.get(i));
            artistas.add(artista);
        }
        if(album==null){
            cargarAlbum(nombreAlbum, genero);
        }
        Cancion cancion= new Cancion(nombreArchiv, nombre, genero, artistas, album, playlists);
        album.getCanciones().add(cancion);
    }
    
    public void crearUsuario(String nombreReal, String nombreDeUsuario, String correo, String contraseña) throws UsuarioException{    
        ArrayList<Album> albumes= new ArrayList<Album>();
        Usuario usuario= new Usuario(nombreReal, nombreDeUsuario, correo, contraseña, albumes);
        BibliotecaMusical biblioteca= new BibliotecaMusical(usuario);
        usuario.setBiblioteca(biblioteca);
        this.reproductorMusical.getUsuarios().add(usuario);
        if(!correo.contains("@")){
            throw new UsuarioException ("El formato del correo es incorrecto");
        }else{
            correo.split("@");
            if(!correo.split("@")[1].contains(".co")){
                throw new UsuarioException ("El formato del correo es incorrecto");
            }
        }
        if(buscarUsuario(nombreDeUsuario)!=null){
            throw new UsuarioException("El nombre de usuario ya existe");
        }
    }
    
    public void crearPlayList(String nombre, Usuario usuario) throws PlaylistException{
        ArrayList<Cancion> canciones= new ArrayList<Cancion>();
        Playlist playlist= new Playlist(nombre , canciones, usuario);
        usuario.getPlaylists().add(playlist);
        if(buscarPlaylist(nombre,usuario)!=null){
            throw new PlaylistException("El nombre de la Playlist ya existe");
        }
    }
    
    //URL cliente-servidor
    public void completarInfoAlbum(String direccion, Album album) throws IOException{
        URL url= new URL(direccion);
        InputStream is= null;
        try{
            URLConnection openConnection= url.openConnection();
            is= openConnection.getInputStream();
            InputStreamReader reader= new InputStreamReader(is);
            BufferedReader bf= new BufferedReader(reader);
            String temp;
            while((temp= bf.readLine())!= null){
                if(temp.contains("Genero")){
                 
                }
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());       
        }
        if(is!=null){
            is.close();
        }
    }
    
    public void completarInfoCancion(String direccion, Cancion cancion) throws IOException{
        URL url= new URL(direccion);
        InputStream is= null;
        try{
            URLConnection openConnection= url.openConnection();
            is= openConnection.getInputStream();
            InputStreamReader reader= new InputStreamReader(is);
            BufferedReader bf= new BufferedReader(reader);
            String temp;
            while((temp= bf.readLine())!= null){
                if(temp.contains("Genero")){
                 
                }
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());       
        }
        if(is!=null){
            is.close();
        }    
    }
    
    //Descripcion artista
    public String urlBuscarArtista(String urlBasico, Artista artista){
      String artistaBuscar=artista.getNombre().replace(" ", "-");
      artistaBuscar= artistaBuscar.toLowerCase();
      String urlBuscarArtista= urlBasico + "/" + artistaBuscar + "/biografia";
      return urlBuscarArtista;
    }
    
    public void obtenerReseña(String direccion, Artista artista) throws IOException{
       String direccionBuscar= urlBuscarArtista(direccion, artista);
       URL url= new URL(direccionBuscar);
       FileWriter fw= null;
       InputStream is= null;
       try{
         URLConnection urlConnection= url.openConnection();
         is= urlConnection.getInputStream();
         fw= new FileWriter(new File("Reseña" + artista.getNombre() + ".txt"));
         InputStreamReader isr= new InputStreamReader(is);
         BufferedReader bf= new BufferedReader(isr);
         BufferedWriter bw= new BufferedWriter(fw);
         String temp;
         bw.write(artista.getNombre().toUpperCase());
         bw.newLine();
         bw.flush();
         while((temp=bf.readLine())!= null){
           if(temp.contains("biografia-texto")){
              while(!(temp=bf.readLine()).equals("<br>")&&!temp.contains("margin-top:20px'")){
                bw.write(temp.trim());
                bw.newLine();
                bw.flush();
              }
          }    
         }
         bw.write("Reseña tomada de " + direccionBuscar);
       }catch(IOException ex){
           System.out.println(ex.getMessage());
       }finally{
         if(is!=null){
             is.close();
         }
         if(fw!=null){
             fw.close();
         }
       }
       artista.setReseña("Reseña" + artista.getNombre() + ".txt");
    }
    
    //Letra cancion
    public String urlBuscarLetra(String urlBasico, Cancion cancion){
        String artista= "";
        for(int i=0; i<cancion.getArtistas().size(); i++){ 
            String temp= cancion.getArtistas().get(i).getNombre().replace(" ", "");
            temp= temp.toLowerCase();
            artista+=temp;  
        }
        String urlBuscar= urlBasico + "/" + artista + "/" + cancion.getNombre() + ".htlm";
        return urlBuscar;    
    }
    
    public String urlBuscarLetraTraducida(String urlBasico, Cancion cancion){
        String artista= "";    
        for(int i=0; i<cancion.getArtistas().size(); i++){
            String temp= cancion.getArtistas().get(i).getNombre();
            temp = temp.replace(" ", "-");
            artista+=(temp+"-");
        }
        String nombreCancion= cancion.getNombre().replace(" ", "-");
        String urlBuscarLetra= urlBasico + "/" + artista + "/" + nombreCancion;
        return urlBuscarLetra;
    }
    
    public void escribirLetra(String direccion, Cancion cancion) throws IOException{
        String direccionLetra= urlBuscarLetra(direccion, cancion);
        URL url= new URL(direccionLetra);
        InputStream is= null;
        FileWriter fw= null;
        try{
            URLConnection openConnection= url.openConnection();
            is= openConnection.getInputStream();
            InputStreamReader reader= new InputStreamReader(is);
            BufferedReader bf= new BufferedReader(reader);
            fw= new FileWriter(new File(cancion.getNombre() + "Letra.txt"));
            BufferedWriter bw= new BufferedWriter(fw);
            String temp;
            bw.write(cancion.getNombre().toUpperCase());
            bw.flush();
            bw.newLine();
            while((temp= bf.readLine())!= null){
                if(temp.contains("<!-- Usage of azlyrics.com content by any third-party lyrics provider is prohibited by our licensing agreement. Sorry about that. -->")){
                    while(!(temp= bf.readLine()).equals("<br><br>")){
                        if(!temp.equals("<br>")){    
                            bw.write(temp);
                            bw.newLine();
                            bw.flush();
                        }else{
                            bw.newLine();
                        }       
                    }
                }     
            }
            bw.write("Letra tomada de" + direccionLetra);
            bw.newLine();
            bw.flush();
        }catch(IOException ex){
            System.out.println(ex.getMessage());       
        }
        if(is!=null){
            is.close();
        }
        if(fw!= null){
            fw.close();
        }
        cancion.setLetra(cancion.getNombre() + "Letra.txt");
    }
    
    public void escribirLetraTraducida(String direccion, Cancion cancion) throws IOException{
        String direccionBuscar= urlBuscarLetraTraducida(direccion, cancion);
        URL url= new URL(direccionBuscar);
        InputStream is= null;
        FileWriter fw= null;
        try{
            URLConnection openConnection= url.openConnection();
            is= openConnection.getInputStream();
            InputStreamReader reader= new InputStreamReader(is);
            BufferedReader bf= new BufferedReader(reader);
            fw= new FileWriter(new File(cancion.getNombre() + "Letra traducida.txt"));
            BufferedWriter bw= new BufferedWriter(fw);
            String temp;
            bw.write(cancion.getNombre().toUpperCase());
            bw.flush();
            bw.newLine();
            while((temp= bf.readLine())!=null){
                if(temp.contains("<div class=\"lyric-container\" style=\"min-height: 630px\">")){
                    while(!((temp= bf.readLine()).contains("<div style=\"margin-top: 10px\">"))){
                        if(!temp.equals("<br />")){    
                            bw.write(temp);
                            bw.newLine();
                            bw.flush();
                        }else{
                            bw.newLine();
                        }
                    }  
                }    
            }
            bw.write("Letra tomada de " + direccionBuscar);
            bw.newLine();
            bw.flush();
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        cancion.setLetraTraduc(cancion.getNombre() + "Letra traducida.txt");
    }
    
    public void obtenerLetra(Cancion cancion){
        //@TODO poner en text pane interfaz
    }
    
    //Recomendar canciones
    public int contarGeneros(String genero, ArrayList<Cancion> canciones){
        int c=0;
        for(int i=0; i<canciones.size(); i++){
            if(canciones.get(i).getGenero().equals(genero)){
                c++;    
            }      
        }
        return c;
    }
    
    public ArrayList<Genero> generos(ArrayList<Cancion> canciones){
        ArrayList<Genero> generos = new ArrayList<Genero>();
        for(int i=0;i<canciones.size();i++){
            generos.add(canciones.get(i).getGenero());
        }
        return generos;
    }
    
}
