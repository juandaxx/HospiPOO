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
 * Clase que valida si un paciente cuenta con una orden
 * previa antes de crear una consulta, para que el paciente
 * pueda acceder a una consulta, debe tener una orden de servicio
 * previamente creada
 * 
 */

public class VOrden {
    
    //Validamos si la orden es del paciente
    public Boolean OrdenEsDelPaciente(String documento, int orden) {
        
        //Definimos una variable auxiliar para saber si es o no es del paciente
        Boolean resultado = false;
        ConexionBD conn = new ConexionBD();

        try {
            
            //Preparamos una consulta para traer el ID del paciente mediante el documento
            String consultaPaciente = "SELECT id FROM pacientes WHERE documento = ?";
            PreparedStatement psPaciente = conn.ConectarBase().prepareStatement(consultaPaciente);
            
            //Pasamos el parametro
            psPaciente.setString(1, documento);

            ResultSet rsPaciente = psPaciente.executeQuery();

            //Guardamos el ID
            int paciente_id;

            if (rsPaciente.next()) {
                paciente_id = rsPaciente.getInt("id");
            } else {
                JOptionPane.showMessageDialog(null,
                    "No existe un paciente con el documento ingresado.");
                return false;
            }

            //Preparamos la consulta que verifica si la orden pertece al paciente
            String sql = "SELECT COUNT(*) AS total FROM ordenes WHERE paciente_id = ? AND id = ?";
            PreparedStatement ps = conn.ConectarBase().prepareStatement(sql);
            
            //Pasamos parametros
            ps.setInt(1, paciente_id);
            ps.setInt(2, orden);

            ResultSet rsOrden = ps.executeQuery();

            //Si el resultado es mayor a 0, quiere decir que hay ordenes asignadas a ese paciente
            if (rsOrden.next() && rsOrden.getInt("total") > 0) {
                resultado = true;
            } else {
                JOptionPane.showMessageDialog(null,
                    "La orden seleccionada no pertenece al paciente o no existe.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error validando orden: " + e.getMessage());
        }

        return resultado;
    }    
}

