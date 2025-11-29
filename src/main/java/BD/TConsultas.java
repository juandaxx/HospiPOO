/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import Backend.DatosConsulta;
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Angel
 * 
 * Clase que define como se van a traer las consultas
 * desde la base de datos
 * 
 */

public class TConsultas {
    
    //Obtiene todas las consultas, sin excepciones
    public ResultSet obtenerConsultas() {
        ConexionBD objConn = new ConexionBD();
        
        //Se prepara la consulta con JOINs a las tablas lookups
        String consulta =
            "SELECT " +
            "co.id AS id, " +
            "p.nombre AS pacienteNombre, " +
            "p.documento AS documento, " +
            "ci.nombre AS cie10Nombre, " +
            "co.observaciones AS observaciones, " +
            "co.fecha AS fechaConsulta, " +
            "o.id AS idOrden, " +
            "e.nombre AS especialidadNombre "+
            "FROM consultas co " +
            "LEFT JOIN pacientes p ON co.paciente_id = p.id " +
            "LEFT JOIN cie10 ci ON co.cie10_id = ci.id " +
            "LEFT JOIN ordenes o ON co.orden_id = o.id " +
            "LEFT JOIN servicios e ON co.servicio_id = e.id";

        try {
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            return ps.executeQuery();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al obtener consultas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }
    
    //Obtiene las consultas asociadas a un paciente por medio del documento
    public ResultSet obtenerConsultasPorDocumento(String documento) {
        ConexionBD objConn = new ConexionBD();

        //De igual manera se prepara la consulta con JOINs para las tablas lookups
        String consulta =
            "SELECT " +
            "c.fecha AS fecha, " +
            "cie.Nombre AS cie10Nombre, " +
            "c.observaciones, " +
            "c.orden_id " +
            "FROM consultas c " +
            "LEFT JOIN pacientes p ON c.paciente_id = p.id " +
            "LEFT JOIN cie10 cie ON c.cie10_id = cie.id " +
            "WHERE p.documento = ?"; //Filtramos por documento

        try {
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            
            //Pasamos el parametro de documento
            ps.setString(1, documento);
            return ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener consultas del paciente: " + e.getMessage());
            return null;
        }
    }
    
    //Obtenemos los datos de las consultas para crear un PDF
    public DatosConsulta obtenerDatosConsulta(int idConsulta) {
        
        //Creamos un objeto del modelo de consulta
        DatosConsulta d = new DatosConsulta();

        //Preparamos la consulta
        String sql =
            "SELECT c.id, c.fecha, c.observaciones, " +
            "       p.nombre AS paciente_nombre, " +
            "       p.Documento AS documento, " +
            "       cie.nombre AS cie10, " +
            "       s.nombre AS especialidad, " +
            "       o.id AS orden_id, " +
            "       u.nombre AS usuario, " +
            "       r.nombre AS cargo " +
            "FROM consultas c " +
            "JOIN pacientes p ON p.id = c.paciente_id " +
            "JOIN cie10 cie ON cie.id = c.cie10_id " +
            "LEFT JOIN servicios s ON s.id = c.servicio_id " +
            "LEFT JOIN ordenes o ON o.id = c.orden_id " +
            "LEFT JOIN usuarios u ON u.id = c.usuario_id " +
            "LEFT JOIN roles r ON r.id = u.rol_id " +
            "WHERE c.id = ?";

        try {
            ConexionBD cdb = new ConexionBD();
            Connection conn = cdb.ConectarBase();

            PreparedStatement ps = conn.prepareStatement(sql);
            
            //Pasamos el parametro
            ps.setInt(1, idConsulta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                //Guardamos los datos
                d.id = rs.getInt("id");
                d.fecha = rs.getString("fecha");
                d.observaciones = rs.getString("observaciones");
                d.paciente = rs.getString("paciente_nombre");
                d.documento = rs.getString("documento");
                d.cie10 = rs.getString("cie10");
                d.especialidad = rs.getString("especialidad");
                d.ordenId = rs.getInt("orden_id");
                d.usuario = rs.getString("usuario");
                d.cargo = rs.getString("cargo");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error obteniendo datos consulta: " + ex.getMessage());
        }

        return d;
    }

}
