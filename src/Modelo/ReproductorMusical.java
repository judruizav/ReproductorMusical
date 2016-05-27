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
public class ReproductorMusical {
    private ArrayList<BibliotecaMusical> bibliotecas;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Album> albumes;
    
    public ReproductorMusical(){
      this.bibliotecas= new ArrayList<BibliotecaMusical>();
      this.usuarios= new ArrayList<Usuario>();
      this.albumes= new ArrayList<Album>();
      
    }

    public ArrayList<BibliotecaMusical> getBibliotecas() {
        return bibliotecas;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }
}
