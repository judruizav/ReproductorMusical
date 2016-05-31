/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.CargarCancion;
import Modelo.Servicio;
import Modelo.exception.BuscarException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
/**
 *
 * @author Julian
 */
public class ControladorCargarCancion implements ActionListener{
    private CargarCancion cargarCancionFrame;
    private Servicio servicio;

    public ControladorCargarCancion(Servicio servicio) {
        this.servicio = servicio;
        this.cargarCancionFrame= new CargarCancion();
        this.cargarCancionFrame.getjButton1().addActionListener(this);
    }
    
    public void start(){
      this.cargarCancionFrame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String bandera=null;
        String nombreArchiv= this.cargarCancionFrame.getjTextField1().getText();
        String nombreCancion= this.cargarCancionFrame.getjTextField2().getText();
        String nombreAlbum= this.cargarCancionFrame.getjTextField3().getText();
        String artistas= this.cargarCancionFrame.getjTextField4().getText();
        ArrayList<String> artistasCancion= new ArrayList<String>();
        if(artistas.contains(";")){
            String[] artistasArray= artistas.split(";");
            for(int i=0; i<artistasArray.length; i++){
                artistasCancion.add(artistasArray[i]);
            }
        }else{
            artistasCancion.add(artistas);
        }
        try {
            this.servicio.cargarCancion(nombreArchiv, nombreCancion, artistasCancion, nombreAlbum);
        } catch (BuscarException | LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            bandera= ex.getMessage();
            JOptionPane.showMessageDialog(cargarCancionFrame, bandera);
        }
        if(bandera==null){
            JOptionPane.showMessageDialog(null, "La cancion " + nombreCancion + " ha sido agregada correctamente");
        }
        
    }
    
}
