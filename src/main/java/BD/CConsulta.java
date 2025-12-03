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
 * @author Juan David
 * 
 * Clase que define como se va a agregar un nuevo registro
 * de consulta en la base de datos, se prepara el Statement 
 * con la consulta, despues de extraer todos los datos necesarios
 * 
 */


public class CConsulta {
    public void AgregarConsulta(String documento, String cie10_id, String observaciones, int order_id, int servicio_id) {
        
        ConexionBD objConn = new ConexionBD();

        try {
            
            //Primero se extrae el ID del paciente al que se le crea la consulta, mediante su documento
            String consultaPaciente = "SELECT id FROM pacientes WHERE documento = ?";
            PreparedStatement psPaciente = objConn.ConectarBase().prepareStatement(consultaPaciente);
            
            //Pasamos parametros
            psPaciente.setString(1, documento);

            ResultSet rs = psPaciente.executeQuery();

            int paciente_id; 
            
            if (rs.next()) {
                paciente_id = rs.getInt("id");
            } else {
                JOptionPane.showMessageDialog(null, "No existe un paciente con ese documento");
                return; 
            }
            
            //Segundo se extrae el ID del cie10 para agregarlo a la consulta, mediante su codigo
            String consultaCie10 = "SELECT id FROM cie10 WHERE codigo = ?";
            PreparedStatement psCie10 = objConn.ConectarBase().prepareStatement(consultaCie10);
            psCie10.setString(1, cie10_id);

            ResultSet rsc = psCie10.executeQuery();

            int ciee10_id; 
            
            if (rsc.next()) {
                ciee10_id = rsc.getInt("id");
            } else {
                JOptionPane.showMessageDialog(null, "No existe un cie10");
                return; 
            }
            
            //Por ultimo se hace la consulta de insert para agregar la nueva consulta
            String consultaInsert = "INSERT INTO consultas (paciente_id, cie10_id, observaciones, orden_id, servicio_id, usuario_id) VALUES (?,?,?,?,?,?)";

            PreparedStatement psInsert = objConn.ConectarBase().prepareStatement(consultaInsert);

            psInsert.setInt(1, paciente_id);
            psInsert.setInt(2, ciee10_id);
            psInsert.setString(3, observaciones);
            psInsert.setInt(4, order_id);
            psInsert.setInt(5, servicio_id);
            psInsert.setInt(6, USesion.usuarioId); //El USesion.usuarioId se refiere al usuario loggeado

            psInsert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Consulta creada correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear consulta: " + e.getMessage());
        }

    }
}
