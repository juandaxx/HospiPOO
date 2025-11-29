/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Backend;

import Conexion.ConexionBD;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;

/**
 *
 * @author Miguel Angel
 * 
 * Esta clase se encarga cargar los datos de las 
 * tablas lookup de la base de datos, se usa para 
 * cargar los JComboBox del Frontend
 * 
 */

public class Cargador {
    
    //Definimos el objeto del conexion a la base
    private ConexionBD objConn;

    public Cargador() {
        objConn = new ConexionBD();
        objConn.ConectarBase();
    }
    
    //Carga los roles definidos en la base y los carga a un JComboBox
    public void cargarRoles(JComboBox<String> combo) {
        try {
            Statement st = objConn.ConectarBase().createStatement();
            
            //Preparamos la consulta
            ResultSet rs = st.executeQuery("SELECT nombre FROM roles");

            combo.removeAllItems(); //Limpia el combo

            while (rs.next()) {
                combo.addItem(rs.getString("nombre")); //Agrega cada rol
            }

        } catch (Exception e) {
            System.out.println("Error al cargar roles: " + e.getMessage());
        }
    }
    
    //Carga las especialidades definidas en la base y las carga a un JComboBox
    public void cargarEspecialidades(JComboBox<String> combo) {
        try {
            Statement st = objConn.ConectarBase().createStatement();
            
            //Prepara la consulta
            ResultSet rs = st.executeQuery("SELECT nombre FROM especialidades");

            combo.removeAllItems(); // Limpia el combo

            while (rs.next()) {
                combo.addItem(rs.getString("nombre")); // Agrega cada especialidad
            }

        } catch (Exception e) {
            System.out.println("Error al cargar especialidades: " + e.getMessage());
        }
    }
    
    //Carga los pacientes y los carga a un JComboBox
    public void cargarPacientes(JComboBox<String> combo) {
        try {
            Statement st = objConn.ConectarBase().createStatement();
            
            //Prepara la consulta
            ResultSet rs = st.executeQuery("SELECT nombre, documento FROM pacientes");

            combo.removeAllItems(); // Limpia el combo

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String documento = rs.getString("documento");
                combo.addItem(nombre + " - " + documento); //Se agrega al combo con nombre y documento
            }

        } catch (Exception e) {
            System.out.println("Error al cargar pacientes: " + e.getMessage());
        }
    }
    
    //Carga los servicios definidos en la base y los carga a un JComboBox
    public void cargarServicios(JComboBox<String> combo) {
        try {
            Statement st = objConn.ConectarBase().createStatement();
            
            //Prepara la consulta
            ResultSet rs = st.executeQuery("SELECT nombre FROM servicios");

            combo.removeAllItems(); // Limpia el combo

            while (rs.next()) {
                combo.addItem(rs.getString("nombre")); //Agrega cada servicio
            }

        } catch (Exception e) {
            System.out.println("Error al cargar servicios: " + e.getMessage());
        }
    }
    
    //Carga los tipos de ordenes definidos en la base y las carga a un JComboBox
    public void cargarTipos(JComboBox<String> combo) {
        try {
            Statement st = objConn.ConectarBase().createStatement();
            
            //Prepara la consulta
            ResultSet rs = st.executeQuery("SELECT nombre FROM tipos");

            combo.removeAllItems(); // Limpia el combo

            while (rs.next()) {
                combo.addItem(rs.getString("nombre")); //Agrega cada tipo
            }

        } catch (Exception e) {
            System.out.println("Error al cargar tipos: " + e.getMessage());
        }
    }
     
    //Carga los estados de ordenes definidos en la base y las carga a un JComboBox
    public void cargarEstados(JComboBox<String> combo) {
        try {
            Statement st = objConn.ConectarBase().createStatement();
            
            //Prepara la consulta
            ResultSet rs = st.executeQuery("SELECT nombre FROM estados");

            combo.removeAllItems(); //Limpia el combo

            while (rs.next()) {
                combo.addItem(rs.getString("nombre")); //Agrega cada estado
            }

        } catch (Exception e) {
            System.out.println("Error al cargar estados: " + e.getMessage());
        }
    }
    
    //Carga los cie10 definidos en la base y las carga a un JComboBox
    public void cargarCie10(JComboBox<String> combo, String texto) {
        
        /**
        * Los Cie10 son las enfermedas registradas, en total, hay 12.000 registros,
        * para evitar que el programa se relentize, se le pide al usuario que escriba que
        * enfermedad quiere buscar y con esto, se hace una consulta para ver que registro 
        * concuerda con lo que el usuario escribe
        */
        try {
            Statement st = objConn.ConectarBase().createStatement();

            //Se preprara la consulta para buscar la enfermedad
            String sql = "SELECT codigo, nombre FROM cie10 "
                       + "WHERE nombre LIKE '" + texto + "%' "
                       + "   OR codigo LIKE '" + texto + "%' "
                       + "LIMIT 15";

            ResultSet rs = st.executeQuery(sql);

            combo.removeAllItems(); //Limpia las opciones

            while (rs.next()) {
                combo.addItem(rs.getString("codigo") + " - " + rs.getString("nombre")); //Se agrega codigo y nombre
            }

            combo.showPopup(); //Muestra sugerencias

        } catch (Exception e) {
            System.out.println("Error al cargar dinámicamente: " + e.getMessage());
        }
    }
    
    //Carga las ordenes y las carga a un JComboBox
    public void cargarOrdenes(JComboBox<String> combo) {
        try {
            Statement st = objConn.ConectarBase().createStatement();
            
            //Prepara la consulta
            ResultSet rs = st.executeQuery("SELECT id FROM ordenes");

            combo.removeAllItems(); //Limpia el comb

            while (rs.next()) {
                combo.addItem(rs.getString("id")); //Agrega orden
            }

        } catch (Exception e) {
            System.out.println("Error al cargar roles: " + e.getMessage());
        }
    }
    

}

