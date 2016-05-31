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
import javax.swing.JOptionPane;
/**
 *
 * @author Julian
 */
public class ControladorIniciarSesion implements ActionListener{
    private IniciarSesioon iniciarSesionFrame;
    private Servicio servicio;
    private Usuario usuario;

    public ControladorIniciarSesion(Servicio servicio) {
        this.servicio = servicio;
        this.iniciarSesionFrame= new IniciarSesioon();
        this.iniciarSesionFrame.getjButton1().addActionListener(this); 
    }

    public void start(){
        this.iniciarSesionFrame.setVisible(true);
    }
     
    @Override
    public void actionPerformed(ActionEvent ae){
        String nombreUsuario= this.iniciarSesionFrame.getjTextField1().getText();
        String clave= this.iniciarSesionFrame.getjPasswordField1().getText();
        String bandera= null;
        try{        
            Usuario usuarioIniciar= this.servicio.iniciarSesion(nombreUsuario, clave);
            this.usuario= usuarioIniciar;
        }catch(UsuarioException ex){
          bandera= ex.getMessage();
          JOptionPane.showMessageDialog(iniciarSesionFrame, bandera);
        }
        if(bandera==null){
          JOptionPane.showMessageDialog(null, "Bienvenido(a) " + nombreUsuario);
          this.iniciarSesionFrame.setVisible(false);
        }
        
    }  
    


    
}
