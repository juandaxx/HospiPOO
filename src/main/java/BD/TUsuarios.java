/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import Conexion.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan David
 * 
 * Clase que define como vamos a traer los datos
 * de los usuarios registramos en el sistema
 * 
 */

public class TUsuarios {
    
    //Traemos todos los usuarios del sistema
    public ResultSet obtenerUsuarios() {
        ConexionBD objConn = new ConexionBD();
        
        //Preparamos la consulta que trae todos los datos de la base
        String consulta = 
            "SELECT u.Nombre, u.Telefono, u.Edad, u.Documento, " +
            "r.Nombre AS Rol, e.Nombre AS Especialidad, u.Email " +
            "FROM usuarios u " +
            "LEFT JOIN especialidades e ON u.Especialidad_id = e.id " +
            "LEFT JOIN roles r ON u.Rol_id = r.id";
    
        try {
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            return ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios: " + e.getMessage());
            return null;
        }
    }

}
