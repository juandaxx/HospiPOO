/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package BD;
import Backend.DatosOrden;
import java.sql.ResultSet;
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;


/**
 *
 * @author Miguel Angel
 * 
 * Clase que define como se van a traer las ordenes
 * en la base de datos
 * 
 */

public class TOrdenes {
    
    //Traemos todos los registros de las ordenes, sin exepciones
    public ResultSet obtenerOrdenes() {
        try {
            ConexionBD objConn = new ConexionBD();

            //Se prepara la consulta con los JOINs para las tablas lookups
            String consulta =
                "SELECT " +
                "o.id AS idOrden, " +
                "o.Fecha_hora AS fecha, " +
                "p.Nombre AS pacienteNombre, " +
                "p.documento AS documento, " +
                "s.id AS codServicio, " +
                "s.Nombre AS nomServicio, " +
                "u.Nombre AS creador, " +
                "r.Nombre AS cargo, " +
                "t.Nombre AS tipo, " +
                "e.Nombre AS estado " +
                "FROM ordenes o " +
                "LEFT JOIN pacientes p ON o.paciente_id = p.id " +
                "LEFT JOIN servicios s ON o.servicio_id = s.id " +
                "LEFT JOIN usuarios u ON o.user_id = u.id " +
                "LEFT JOIN roles r ON u.rol_id = r.id " +
                "LEFT JOIN tipos t ON o.tipo_id = t.id " +
                "LEFT JOIN estados e ON o.estado_id = e.id";
        
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            return ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ordenes: " + e.getMessage());
            return null;
        }
    }
        
    //Ahora traemos los registros de ordenes de un paciente mediante su documento
    public ResultSet obtenerOrdenesPorDocumento(String documento) {
        try {
            ConexionBD objConn = new ConexionBD();

            //De igual manera preparamos la consulta pero ahora filtramos por documento
            String consulta =
                "SELECT " +
                "s.id AS codServicio, " +
                "u.Nombre AS creador, " +
                "p.documento AS documento, " +
                "t.Nombre AS tipo, " +
                "e.Nombre AS estado, " +
                "o.Fecha_hora AS fecha " +
                "FROM ordenes o " +
                "LEFT JOIN pacientes p ON o.paciente_id = p.id " +
                "LEFT JOIN servicios s ON o.servicio_id = s.id " +
                "LEFT JOIN usuarios u ON o.user_id = u.id " +
                "LEFT JOIN tipos t ON o.tipo_id = t.id " +
                "LEFT JOIN estados e ON o.estado_id = e.id " +
                "WHERE p.documento = ?";
            
            PreparedStatement ps = objConn.ConectarBase().prepareStatement(consulta);
            
            //Pasamos el parametro
            ps.setString(1, documento);
            return ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener órdenes del paciente: " + e.getMessage());
            return null;
        }
    }
    
    //Obtenemos los datos de las ordenes para descargar PDF
    public DatosOrden obtenerDatosOrden(int idOrden) {
        
        //Creamos un nuevo modelo de datos de ordenes
        DatosOrden d = new DatosOrden();

        //Preparamos la consulta
        String sql =
            "SELECT o.id, o.Fecha_hora, " +
            "       p.nombre AS paciente_nombre, " +
            "       p.Documento AS documento, " +
            "       s.nombre AS servicio_nombre, " +
            "       t.Nombre AS tipo_nombre, " +
            "       e.Nombre AS estado_nombre, " +
            "       u.nombre AS usuario_nombre, " +
            "       r.Nombre AS cargo " +
            "FROM ordenes o " +
            "JOIN pacientes p ON p.id = o.paciente_id " +
            "JOIN servicios s ON s.id = o.servicio_id " +
            "JOIN tipos t ON t.id = o.tipo_id " +
            "JOIN estados e ON e.id = o.estado_id " +
            "JOIN usuarios u ON u.id = o.user_id " +
            "JOIN roles r ON r.id = u.Rol_id " +
            "WHERE o.id = ?";

        try {
            ConexionBD c = new ConexionBD();
            Connection conn = c.ConectarBase();

            PreparedStatement ps = conn.prepareStatement(sql);
            
            //Pasamos el parametro
            ps.setInt(1, idOrden);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                //Guardamos los datos
                d.id = rs.getInt("id");
                d.fechaHora = rs.getString("Fecha_hora");
                d.nombrePaciente = rs.getString("paciente_nombre");
                d.documento = rs.getString("documento");
                d.nombreServicio = rs.getString("servicio_nombre");
                d.tipo = rs.getString("tipo_nombre");
                d.estado = rs.getString("estado_nombre");
                d.nombreUsuario = rs.getString("usuario_nombre");
                d.cargo = rs.getString("cargo");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error obteniendo datos de la orden: " + ex.getMessage());
        }

        return d;
    }
}

