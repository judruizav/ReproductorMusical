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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Genero cargarGenero(String nombre){
      ArrayList<Cancion> canciones= new ArrayList<Cancion>();
      ArrayList<Album> albumes= new ArrayList<Album>();
      Genero genero= new Genero(nombre, canciones, albumes);
      return genero;
    }
    
    public void cargarAlbum(String nombreAlbum){
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<BibliotecaMusical> bibliotecas = new ArrayList<BibliotecaMusical>();    
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album = new Album(nombreAlbum, artistas, usuarios, bibliotecas);
        try {
            completarInfoAlbum("https://www.quedeletras.com", album);
        } catch (IOException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.reproductorMusical.getAlbumes().add(album);
    }
    
    public void cargarAlbumGenero(String nombreAlbum, String nombreGenero) throws FileNotFoundException{
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<BibliotecaMusical> bibliotecas = new ArrayList<BibliotecaMusical>();    
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album = new Album(nombreAlbum, artistas, usuarios, bibliotecas);
        Genero genero= cargarGenero(nombreGenero);
        album.setGenero(genero);
        this.reproductorMusical.getAlbumes().add(album);
    }
    
    
    public void cargarCancion(String nombreArchiv, String nombre, ArrayList<String> nombresArtista, String nombreAlbum) throws BuscarException, FileNotFoundException{
        ArrayList<Playlist> playlists= new ArrayList<Playlist>();
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album= buscarAlbum(nombreAlbum); 
        for(int i=0; i<nombresArtista.size(); i++){
            Artista artista= new Artista(nombresArtista.get(i));
            artistas.add(artista);
        }
        if(album==null){
            cargarAlbum(nombreAlbum);
            album= buscarAlbum(nombreAlbum);   
        }
        Cancion cancion= new Cancion(nombreArchiv, nombre, album.getGenero(), artistas, album, playlists);
        try {
            escribirLetra("http://www.azlyrics.com", cancion);
            obtenerBpmCancion("https://songbpm.com",cancion);
        } catch (IOException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        album.getGenero().getCanciones().add(cancion);
        album.getGenero().setNumCanciones();
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
    //Completar info album
    public String urlInfoAlbum(String urlBasico, Album album){
      String artistas="";
      for(int i=0; i<album.getArtistas().size(); i++){
        String temp= album.getArtistas().get(i).getNombre().toLowerCase();
        artistas+=temp.replace(" ", "-");
      }
      String albumBuscar= urlBasico + "/discografia-" + artistas + "/discography-" + artistas + ".html";
      return albumBuscar;     
    }
    
    public void completarInfoAlbum(String direccion, Album album) throws IOException{
      String direccionBuscarAlbum= urlInfoAlbum(direccion,album);
      URL url= new URL(direccionBuscarAlbum);
      InputStream is= null;
      try{
        URLConnection urlConnection= url.openConnection();
        is= urlConnection.getInputStream();
        InputStreamReader isr= new InputStreamReader(is);
        BufferedReader bf= new BufferedReader(isr);
        String temp;
        String[] nombreAlbum= album.getNombre().split(" ");
        while((temp=bf.readLine())!=null){
            if(temp.contains(nombreAlbum[0])&&temp.contains(nombreAlbum[1])){
                if((temp= bf.readLine()).contains("</p>")&&!temp.contains("A&ntilde")){
                String[] tempArray= temp.split("-");
                String año= tempArray[1].trim();
                String[] generoArray= tempArray[2].trim().split("<");
                String genero= generoArray[0].trim();
                album.setAño(año);
                Genero generoAlbum= cargarGenero(genero);
                album.setGenero(generoAlbum);
                }
            }
        }
      }catch(IOException ex){
          System.out.println(ex.getMessage());
      }finally{
        if(is!=null){
          is.close();
        }    
      }
    }
    //Obtener bpm cancion
    public String urlBuscarCancion(String urlBasico, Cancion cancion){
      String artistas="";
      for(int i=0; i<cancion.getArtistas().size(); i++){
        String temp= cancion.getArtistas().get(i).getNombre().replace(" ", "+").toLowerCase();
        artistas+=temp;
      }
      String nombreCancion= cancion.getNombre().replace(" ", "+").toLowerCase();
      String urlBuscarCancion= urlBasico + "/" + artistas + "/" + nombreCancion;
      return urlBuscarCancion;
    }
    
    public void obtenerBpmCancion(String direccion, Cancion cancion) throws IOException{
      String direccionBuscar= urlBuscarCancion(direccion, cancion);
      InputStream is= null;
      try{
        URL url= new URL(direccionBuscar);
        URLConnection openConnection = url.openConnection();
        is= openConnection.getInputStream();
        InputStreamReader reader= new InputStreamReader(is);
        BufferedReader bf= new BufferedReader(reader);
        String temp;
        while((temp= bf.readLine())!= null){
            try{
            if(temp.contains("search-results")){
                    while(!(temp=bf.readLine()).contains("BPM</div>")){
                        if(temp.contains("</div>")){
                            String[] tempArray= temp.split(">");
                            String[] bpmArray= tempArray[1].split("<");
                            String bpm= bpmArray[0];
                            cancion.setBpm(Integer.parseInt(bpm));
                        }
                    }        
            }
            }catch(ArrayIndexOutOfBoundsException ex){
                System.out.println("Cancion no encontrada D:");
            }
        }      
      }catch(IOException ex){
        System.out.println(ex.getMessage());
      }finally{
        if(is!=null){
            is.close();
        }    
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
        InputStream is= null;
        FileWriter fw= null;
        try{
            URL url= new URL(direccionBuscar);
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
    public ArrayList<Genero> obtenerGeneros(ArrayList<Cancion> canciones){
        ArrayList<Genero> generos = new ArrayList<Genero>();
        for(int i=0;i<canciones.size();i++){
            if(!generos.contains(canciones.get(i).getGenero())){
              generos.add(canciones.get(i).getGenero());    
            }
        }
        return generos;
    }
    
    /*public ArrayList<Genero> organizarGeneros(ArrayList<Genero> generos){
      for(int i=0; i<(generos.size()-1); i++){
        for(int j=i+1; i<generos.size(); i++){
            if(generos.get(i).getNumCanciones()<generos.get(j).getNumCanciones()){
              Genero temp= generos.get(i);
              generos.remove(i);
              generos.add(i, generos.get(j));
              generos.add(j, temp);
            }
        }      
      }
      return generos;
    }*/
 
    public String urlBuscarGenero(String urlBasico,Genero genero){
      String nombreGenero= genero.getNombre().replace(" ", "-").toLowerCase();
      String urlBuscarGenero= urlBasico+ "/"+ nombreGenero + "/";
      return urlBuscarGenero;
    }
    
    public Cancion cargarCancionRecomendada(String nombre, String nombreArtista){
      ArrayList<Artista> artistas= new ArrayList<Artista>();
      Artista artista= new Artista(nombreArtista);
      Cancion cancion= new Cancion(nombre,artistas, artista);
      return cancion;    
    }
    
    public ArrayList<Cancion> obtenerCancionesRecomendadas(String direccion, Genero genero) throws IOException{
      String direccionBuscarGenero= urlBuscarGenero(direccion, genero);
      ArrayList<Cancion> cancionesRecomendadas= new ArrayList<Cancion>();
      InputStream is= null;
      try{
        URL url= new URL(direccionBuscarGenero);
        URLConnection openConnection = url.openConnection();    
        is= openConnection.getInputStream();
        InputStreamReader reader= new InputStreamReader(is);
        BufferedReader bf= new BufferedReader(reader);
        String temp;
        while((temp= bf.readLine())!= null){
          if(temp.contains("><a href=")&&temp.contains("song")){  
          String[] tempArray= temp.split("><a href=");
          String[] infoArray= tempArray[1].split("itemprop");
          if(infoArray[0].contains("cancion")){
            String[] cancionArray= infoArray[0].split("/");
            Cancion cancionRecomendada= cargarCancionRecomendada(cancionArray[4].replace("-", " "), cancionArray[2].replace("-", " "));
            cancionesRecomendadas.add(cancionRecomendada);
          }
          }
        }
      }catch(IOException ex){
          System.out.println(ex.getMessage());
      }finally{
        if(is!=null){
          is.close();
        }    
      }
      return cancionesRecomendadas;
    }
    
}
