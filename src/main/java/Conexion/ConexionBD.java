/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


/**
 *
 * @author Miguel Angel
 * 
 * Esta clase es una de las mas importantes, aqui
 * definimos la conexion a la base de datos, en este
 * caso es una conexion a una base local
 */

public class ConexionBD {
    
    //Atributos de conexion
    Connection conexion = null;
    String usuario = "root";
    String passw = "";
    String bd = "HospiPOO";
    String ip = "localhost";
    String puerto = "3306";
    
    //Cadena necesaria para la conexion
    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;
    
    public Connection ConectarBase(){
        try{
            
            //Se utiliza driver para la conexion
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(cadena, usuario, passw);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "No");
        }
        
        return conexion;
    }
    
}

