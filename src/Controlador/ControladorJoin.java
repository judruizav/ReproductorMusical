/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.Join;
import Modelo.Servicio;
import Modelo.exception.UsuarioException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Julian
 */
public class ControladorJoin implements ActionListener{
    private Join join;
    private Servicio servicio;

    public ControladorJoin(Servicio servicio) {
        this.servicio = servicio;
        this.join= new Join();
        this.join.getjButton2().addActionListener(this);
    }
    
    public void start(){
      this.join.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String nombre= this.join.getjTextField1().getText();
        String nick= this.join.getjTextField2().getText();
        String clave= this.join.getjPasswordField1().getText();
        String correo= this.join.getjTextField3().getText();
        String bandera= null;
        try {
            this.servicio.crearUsuario(nombre, nombre, correo, correo);
        } catch (UsuarioException ex) {
            bandera= ex.getMessage();
            JOptionPane.showMessageDialog(join, bandera);
        }
        if(bandera==null){
            JOptionPane.showMessageDialog(null, "Perfl creado correctamente");
            this.join.setVisible(false);
        }
    }
    
    
}
