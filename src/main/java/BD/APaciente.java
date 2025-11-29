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
 * @author Juan David
 * 
 * Clase que define como se van a actualizar los 
 * datos de algun paciente, se crea un Statement
 * con una consulta que hace update a la base
 * 
 */


public class APaciente {
    public boolean actualizarPaciente(String nombre, String documento, String telefono, String correo, String direccion, int edad) {

        ConexionBD conn = new ConexionBD();
        String sql = "UPDATE pacientes SET Nombre=?, Telefono=?, Correo=?, Direccion=?, Edad=? WHERE Documento=?";

        try (PreparedStatement ps = conn.ConectarBase().prepareStatement(sql)) {
            
            //Pasamos los parametros para el update
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, direccion);
            ps.setInt(5, edad);
            ps.setString(6, documento);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
            return false;
        }
    }

}
