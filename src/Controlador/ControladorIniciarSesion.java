/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.IniciarSesioon;
import Modelo.Servicio;
import Modelo.Usuario;
import Modelo.exception.UsuarioException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 *
 * @author Julian
 */
public class ControladorIniciarSesion implements ActionListener{
    private IniciarSesioon iniciarSesionFrame;
    private Servicio servicio;

    public ControladorIniciarSesion(IniciarSesioon iniciarSesionFrame, Servicio servicio) {
        this.iniciarSesionFrame = iniciarSesionFrame;
        this.servicio = servicio;
        
    }

    
     
    @Override
    public void actionPerformed(ActionEvent ae){
        String nombreUsuario= this.iniciarSesionFrame.getjTextField1().getText();
        String clave= this.iniciarSesionFrame.getjPasswordField1().getText();
        Usuario usuarioIniciar= this.servicio.buscarUsuario(nombreUsuario);
        if(usuarioIniciar!=null){
          if(usuarioIniciar.getContraseña().equals(clave)){
            usuarioIniciar.setSesionIniciada(true);
          }
        }  
        
    }


    
}
