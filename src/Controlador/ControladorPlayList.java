/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.CrearPlayList;
import Modelo.Servicio;
import Modelo.Usuario;
import Modelo.exception.PlaylistException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Julian
 */
public class ControladorPlayList implements ActionListener{
    private CrearPlayList crearPlayList;
    private Servicio servicio;
    private Usuario usuario;

    public ControladorPlayList(Servicio servicio) {
        this.servicio = servicio;
        this.crearPlayList= new CrearPlayList();
        this.crearPlayList.getjButton1().addActionListener(this);
 
    }
    
    public void start(){
        this.crearPlayList.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        String bandera= null;
        String nombrePlayList= this.crearPlayList.getjTextField1().getText();
        String cancionesAgregar= this.crearPlayList.getjTextPane1().getText();
        ArrayList<String> cancionesPlayList= new ArrayList<String>();
        if(cancionesAgregar.contains(";")){
            String[] nombresArray= cancionesAgregar.split(";");
            for(int i=0; i<nombresArray.length; i++){
                cancionesPlayList.add(nombresArray[i]);
            }
        }else{
            cancionesPlayList.add(cancionesAgregar);    
        }
        try {
            this.servicio.crearPlayList(nombrePlayList, this.usuario, cancionesPlayList);
        } catch (PlaylistException ex) {
            bandera= ex.getMessage();
            JOptionPane.showMessageDialog(crearPlayList, bandera);
        }
        if(bandera==null){
            JOptionPane.showMessageDialog(null, "PlayList creada con exito");
            this.crearPlayList.setVisible(false);
        }
        
    }
    
}
