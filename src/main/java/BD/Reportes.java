/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package BD;

import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Sebastian
 * 
 * Clase que define se van a agregar los datos
 * de la tabla de historias clinicas y agrega los datos
 * a esta tabla, las historias clinicas es el conjunto de
 * consultas y ordenes de un paciente
 * 
 */

public class Reportes {

    //Funcion que es llamada desde el controlador, devuelve el conteo de una tabla de la base de datos
    public static int consultarConteo(String sql) {
        try (Connection con = new ConexionBD().ConectarBase();
                
            //Recibe una consulta desde el controlador y devuelve la cantidad de registros
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //Con esta funcion, cargamos los datos a las tablas de historias clinicas
    public static TableModel cargarTabla(String sql) { //Devuelve un TableModel
        try (Connection con = new ConexionBD().ConectarBase();
                
            //Recibe una consulta desde el controlador
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            //Crea un objeto de modelo de tabla
            DefaultTableModel modelo = new DefaultTableModel();

            // Columnas
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnName(i));
            }

            // Filas
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            return modelo;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DefaultTableModel();
    }

}
