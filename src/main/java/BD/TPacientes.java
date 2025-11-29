/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import Conexion.ConexionBD;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebastian
 * 
 * Clase que define como se van a traer los 
 * registros de pacientes de la base de datos
 * 
 */

public class TPacientes {
    
    //Traemos todos los pacientes registrados
    public List<Object[]> obtenerPacientes() {
        ConexionBD objConn = new ConexionBD();
        
        //Creamos una lista para guardar los pacientes, ya que el dataSet no funciona 
        List<Object[]> lista = new ArrayList<>();

        //Preparamos la consulta
        String consulta = "SELECT * FROM pacientes";

        try (PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                
                //Guardamos los datos extraidos de la base en la lista
                lista.add(new Object[]{
                    rs.getString("Nombre"),
                    rs.getString("Documento"),
                    rs.getString("Telefono"),
                    rs.getString("Correo"),
                    rs.getString("Direccion"),
                    rs.getInt("Edad")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener pacientes: " + e.getMessage());
        }

        return lista;
    }
    
    //Traemos un paciente en espefico mediante su documento
    public Object[] buscarPacientePorDocumento(String documento) {
        ConexionBD conn = new ConexionBD();
        
        //Preparamos la consulta filtrando por documento
        String sql = "SELECT * FROM pacientes WHERE Documento = ?";

        try (PreparedStatement ps = conn.ConectarBase().prepareStatement(sql)) {
            
            //Pasamos el parametro a la consulta
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Object[]{
                    
                    //Guardamos los datos arrojados por la consulta
                    rs.getString("Nombre"),
                    rs.getString("Documento"),
                    rs.getString("Telefono"),
                    rs.getString("Correo"),
                    rs.getString("Direccion"),
                    rs.getInt("Edad")
                };
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar: " + e.getMessage());
        }

        return null; // no encontrado
    }


}
