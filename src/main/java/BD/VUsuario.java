/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import Backend.USesion;
import Conexion.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebastian
 * 
 * Esta clase es la que permite el inicio de 
 * sesion de un usuario, valida si el documento
 * y contraseña ingresada exista en la base de
 * datos
 * 
 */

public class VUsuario {
    
    //Verificamos si las credenciales son correctas
    public Boolean ValidarUsuario(String documento, String contraseña) {
        
        //Variable auxiliar que nos dira si existe o no
        Boolean Resultado = false;
        ConexionBD objConn = new ConexionBD();
        
        //Preparamos la consulta
        String consulta = "SELECT id, nombre, rol_id FROM usuarios WHERE Documento = ? AND Contraseña = ?";

        try {
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            
            //Pasamos parametros
            ps.setString(1, documento);
            ps.setString(2, contraseña);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                //Existe al menos un registro, guardamos algunos datos en el objeto de USesion para usarlos despues
                USesion.usuarioId = rs.getInt("id");
                USesion.usuarioNombre = rs.getString("nombre");
                USesion.usuarioRol = rs.getInt("rol_id");
                
                JOptionPane.showMessageDialog(null, "Credenciales correctas");
                
                //Decimos que el usuario esta registrado
                Resultado = true;
            } else {
                JOptionPane.showMessageDialog(null, "Documento o contraseña incorrectos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar: " + e.getMessage());
        }
        
        return Resultado;
    }

}
