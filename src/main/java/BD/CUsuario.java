/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import Conexion.ConexionBD;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;


/**
 *
 * @author Miguel Angel
 * 
 * Esta clase define como se registra un usuario nuevo
 * al sistema, validando si es admin o doctor
 * 
 */

public class CUsuario {
    
     public void AgregarUsuarios(String nombre, String telefono, int edad, String documento, Integer especialidad, int rol_id, String email, String contrasena) {
        
        //Preparamos la consulta para insertar un nuevo usuario
        ConexionBD objConn = new ConexionBD();
        String consulta = "INSERT INTO usuarios (Nombre, Telefono, Edad, Documento, Especialidad_id, Rol_id, Email, Contraseña) values(?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            
            //Se le pasan los parametros
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setInt(3, edad);
            ps.setString(4, documento);
            
            //Si la especialidad llega null quiere decir que es un Admin
            if (especialidad == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                
                //Si no, se le pasa la especialidad seleccionada
                ps.setInt(5, especialidad);
            }
            ps.setInt(6, rol_id);
            ps.setString(7, email);
            ps.setString(8, contrasena);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario creado");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear usuario: " + e.getMessage());
        }
    }
}
