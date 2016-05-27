/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Julian
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] array= new String[3];
        array[0]= "No hay conexion a internet";
        array[1]= "No hay conexion a internet";
        array[2]= "No hay conexion a internet";
        String artista="";
        for(int i=0; i<3 ; i++){
            String temp= array[i].replace(" ", "");
            temp= temp.toLowerCase();
            artista+=(temp);
        }
        System.out.println(artista);
    }
}
