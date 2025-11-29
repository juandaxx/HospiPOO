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
 * Esta clase define como se va a agregar un cliente al sistema
 * define como sera el insert en la base de datos
 * 
 */

public class CPaciente {
   public void AgregarPaciente(String nombre, String telefono, int edad, String documento, String email, String direccion) {
        
       //Preparamos la consulta para agregar un paciente
        ConexionBD objConn = new ConexionBD();
        String consulta = "INSERT INTO pacientes (Nombre, Documento, Telefono, Correo, Direccion, Edad) values(?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            
            ps.setString(1, nombre);
            ps.setString(2, documento);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, direccion);
            ps.setInt(6, edad);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Paciente creado");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear paciente: " + e.getMessage());
        }
    }
 
}
