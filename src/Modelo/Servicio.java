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
import java.util.Collections;
import java.util.Comparator;
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
    
    public Artista buscarArtista(String nombre){
      Artista artista= null;
      for(int i=0; i<this.reproductorMusical.getAlbumes().size(); i++){
          for(int j=0; j<this.reproductorMusical.getAlbumes().get(i).getArtistas().size(); j++){
            if(this.reproductorMusical.getAlbumes().get(i).getArtistas().get(j).getNombre().equals(nombre)){
              artista= this.reproductorMusical.getAlbumes().get(i).getArtistas().get(j);    
            }      
          }
      }
      return artista;
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
    
    public Artista cargarArtista(String nombreArtista){
      Artista artista= new Artista(nombreArtista);
        try {
            obtenerReseña("http://www.buenascanciones.com", artista);
        } catch (IOException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
      return artista;
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
    
    public void cargarArtistasAlbum(ArrayList<Artista> artistas, Album album){
      for(int i=0; i<artistas.size(); i++){
        album.getArtistas().add(artistas.get(i));
      }    
    }
    
    public void cargarCancion(String nombreArchiv, String nombre, ArrayList<String> nombresArtista, String nombreAlbum) throws BuscarException, FileNotFoundException{
        ArrayList<Playlist> playlists= new ArrayList<Playlist>();
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album= buscarAlbum(nombreAlbum);
        for(int i=0; i<nombresArtista.size(); i++){
            if(buscarArtista(nombresArtista.get(i))==null){
                Artista artista= cargarArtista(nombresArtista.get(i));
                artistas.add(artista);
            }else{
                artistas.add(buscarArtista(nombresArtista.get(i)));      
            }
        }
        if(album==null){
            cargarAlbum(nombreAlbum);
            album= buscarAlbum(nombreAlbum);
            cargarArtistasAlbum(artistas, album);
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
    
    public void cargarCancionUsuario(String nombreArchiv, String nombre, ArrayList<String> nombresArtista, String nombreAlbum, Usuario usuario){
        ArrayList<Playlist> playlists= new ArrayList<Playlist>();
        ArrayList<Artista> artistas= new ArrayList<Artista>();
        Album album= buscarAlbum(nombreAlbum);
        for(int i=0; i<nombresArtista.size(); i++){
            if(buscarArtista(nombresArtista.get(i))==null){
                Artista artista= cargarArtista(nombresArtista.get(i));
                artistas.add(artista);
            }else{
                artistas.add(buscarArtista(nombresArtista.get(i)));      
            }
        }
        if(album==null){
            cargarAlbum(nombreAlbum);
            album= buscarAlbum(nombreAlbum);
            cargarArtistasAlbum(artistas, album);
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
        usuario.getAlbumes().add(album);
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
    
    public void cargarPlayList(String nombre, Usuario usuario){
        ArrayList<Cancion> canciones= new ArrayList<Cancion>();
        Playlist playlist= new Playlist(nombre , canciones, usuario);
        usuario.getPlaylists().add(playlist);
    }
    
    public void crearPlayList(String nombre, Usuario usuario, ArrayList<String> nombresCanciones) throws PlaylistException{
        cargarPlayList(nombre,usuario);
        Playlist playlist= buscarPlaylist(nombre, usuario);
        for(int i=0; i<nombresCanciones.size(); i++){
            Cancion cancion= buscarCancion(nombresCanciones.get(i));
            if(cancion==null){
                throw new PlaylistException("La canción " + nombresCanciones.get(i) + " no existe");    
            }else{
                playlist.getCanciones().add(cancion);
            } 
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
    public Cancion cargarCancionRecomendada(String nombre, String nombreArtista){
      ArrayList<Artista> artistas= new ArrayList<Artista>();
      Artista artista= new Artista(nombreArtista);
      Cancion cancion= new Cancion(nombre,artistas, artista);
      return cancion;    
    }
    
    //Ordenar artista
    public ArrayList<Artista> ArtistaFavorito(ArrayList<Artista> artistas){
        Collections.sort(artistas, new Comparator<Artista>() {
            @Override
            public int compare(Artista a1, Artista a2) {
                return new Integer(a2.getNumeroCanciones()).compareTo(new Integer(a1.getNumeroCanciones()));   
            }
        });
        return artistas;
    }
    
    //Recomendar canciones por artista
    public ArrayList<Artista> obtenerArtistas(ArrayList<Album> albumes){
      ArrayList<Artista> artistas= new ArrayList<Artista>();
      for(int i=0; i<albumes.size(); i++){
        for(int j=0; i<albumes.get(i).getArtistas().size(); j++){
          if(!artistas.contains(albumes.get(i).getArtistas().get(j))){
            artistas.add(albumes.get(i).getArtistas().get(j));
          }    
        }    
      }
      return artistas;
    } 
    
    public String urlBuscarCancionesArtista(String urlBasico, Artista artista){
      String nombreArtista= artista.getNombre().toLowerCase().replace(" ", "-");
      String urlBuscarCancionesArtista= urlBasico + "/" + nombreArtista + "/discografia";
      return urlBuscarCancionesArtista;
    }
    
    public ArrayList<Cancion> obtenerCancionesRecomendadasArtista(String direccion, Artista artista) throws IOException{
      ArrayList<Cancion> cancionesRecomendadas= new ArrayList<Cancion>();
      String direccionBuscarArtista= urlBuscarCancionesArtista(direccion,artista);
      InputStream is= null;
      try{
        URL url= new URL(direccionBuscarArtista);
        URLConnection openConnection = url.openConnection();    
        is= openConnection.getInputStream();
        InputStreamReader reader= new InputStreamReader(is);
        BufferedReader bf= new BufferedReader(reader);
        String temp;
        while((temp= bf.readLine())!= null){
            if(temp.contains("Canciones de este")){
              while(!(temp= bf.readLine()).contains("</div>")){
                  if(temp.contains("<br />")&&!temp.equals("<br />")&&temp.contains(".")){
                     String[] tempArray= temp.trim().split("<");
                     String[] tempCancion= tempArray[0].split(" ");
                     String cancion="";
                     for(int i=0; i<tempCancion.length; i++){
                        cancion+=(tempCancion[i] + " ");
                        Cancion cancionRecomendada= cargarCancionRecomendada(cancion, artista.getNombre());
                        cancionesRecomendadas.add(cancionRecomendada);
                     }
                  }
              }
            }
        }    
      }catch (IOException ex){
        System.out.println(ex.getMessage());    
      }
      return cancionesRecomendadas;
    }

    //Recomendar canciones por genero
    public ArrayList<Genero> obtenerGeneros(ArrayList<Cancion> canciones){
        ArrayList<Genero> generos = new ArrayList<Genero>();
        for(int i=0;i<canciones.size();i++){
            if(!generos.contains(canciones.get(i).getGenero())){
              generos.add(canciones.get(i).getGenero());    
            }
        }
        return generos;
    }
    
    //Ordenar Generos por num de canciones
    public ArrayList<Genero> generoFavorito(ArrayList<Genero> generos){
        Collections.sort(generos, new Comparator<Genero>() {
            @Override
            public int compare(Genero g1, Genero g2) {
                return new Integer(g2.getNumCanciones()).compareTo(new Integer(g1.getNumCanciones()));   
            }
        });
        return generos;
    }
 
    public String urlBuscarGenero(String urlBasico,Genero genero){
      String nombreGenero= genero.getNombre().replace(" ", "-").toLowerCase();
      String urlBuscarGenero= urlBasico+ "/"+ nombreGenero + "/";
      return urlBuscarGenero;
    }
    
    public ArrayList<Cancion> obtenerCancionesRecomendadasGenero(String direccion, Genero genero) throws IOException{
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
    
    //Recomendar Canciones por usuario
    public ArrayList<Cancion> recomendarCancionesUsuarioPorArtista(Usuario usuario, Artista artistaFavorito){ 
      ArrayList<Cancion> cancionesRecomendadasPorArtista= new ArrayList<Cancion>();
      boolean bandera= false;
        try {
            ArrayList<Cancion> cancionesRecomendadas= obtenerCancionesRecomendadasArtista("http://www.buenamusica.com", artistaFavorito);
            for(int i=0; i<cancionesRecomendadas.size(); i++){
                for(int j=0; j<usuario.getAlbumes().size(); j++){
                    for(int h=0; j<usuario.getAlbumes().get(j).getCanciones().size(); h++){
                      bandera|=(usuario.getAlbumes().get(j).getCanciones().get(h).getNombre().equals(cancionesRecomendadas.get(i).getNombre()));    
                    }    
                }
                if(bandera==false){
                    cancionesRecomendadasPorArtista.add(cancionesRecomendadas.get(i));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
      return cancionesRecomendadasPorArtista;    
    }
    
    
    public ArrayList<Cancion> recomendarCancionUsuarioPorGenero(Usuario usuario, Genero generoFavorito){
      ArrayList<Cancion> cancionesRecomendadasPorGenero= new ArrayList<Cancion>();
       boolean bandera= false;
      try {
            ArrayList<Cancion> cancionesRecomendadas= obtenerCancionesRecomendadasGenero("http://www.sonicomusica.net", generoFavorito);
                        for(int i=0; i<cancionesRecomendadas.size(); i++){
                for(int j=0; j<usuario.getAlbumes().size(); j++){
                    for(int h=0; j<usuario.getAlbumes().get(j).getCanciones().size(); h++){
                      bandera|=(usuario.getAlbumes().get(j).getCanciones().get(h).getNombre().equals(cancionesRecomendadas.get(i).getNombre()));    
                    }    
                }
                if(bandera==false){
                    cancionesRecomendadasPorGenero.add(cancionesRecomendadas.get(i));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
      return cancionesRecomendadasPorGenero;
    }
    

}
