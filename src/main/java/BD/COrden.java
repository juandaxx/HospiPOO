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
 * @author Sebastian
 * 
 * Esta clase define como se van a agregar las ordenes 
 * en la base de datos, se prepara un Statement con los datos necesarios
 * 
 */


public class COrden {
    public void AgregarOrden(String documento, Integer servicio_id, Integer user_id, Integer tipo_id, Integer estado_id) {
        
        ConexionBD objConn = new ConexionBD();

        try {

            //Se hace la consulta para encontrar al paciente mediante su documento
            String consultaPaciente = "SELECT id FROM pacientes WHERE documento = ?";
            PreparedStatement psPaciente = objConn.ConectarBase().prepareStatement(consultaPaciente);
            
            //Se le pasa por parametro
            psPaciente.setString(1, documento);

            ResultSet rs = psPaciente.executeQuery();

            int paciente_id;

            if (rs.next()) {
                paciente_id = rs.getInt("id");
            } else {
                JOptionPane.showMessageDialog(null, "No existe un paciente con ese documento");
                return;
            }

            //Ahora preparamos la consulta para hacer el INSERT en la base de datos
            String consultaInsert = "INSERT INTO ordenes (paciente_id, servicio_id, user_id, tipo_id, estado_id) VALUES (?,?,?,?,?)";

            PreparedStatement psInsert = objConn.ConectarBase().prepareStatement(consultaInsert);

            //Parametros para el insert
            psInsert.setInt(1, paciente_id);
            psInsert.setInt(2, servicio_id);
            psInsert.setInt(3, user_id);
            psInsert.setInt(4, tipo_id);
            psInsert.setInt(5, estado_id);

            psInsert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Orden creada correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear orden: " + e.getMessage());
        }

    }
}
