/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

/**
 *
 * @author Juan David
 * 
 * Esta clase define el modelo que va a tener los usuarios
 * definiendo los getters y los setters necesarios para los 
 * datos de usuarios
 * 
 */

public class ModeloUsuario {
    
    //Definimos atributos del usuario
    int id;
    int Especialidad;
    int Edad;
    String Documento;
    String Telefono;
    int Rol_id;
    String Nombre;
    String Contraseña;
    String Email;

    //Definimos Getters y Setters
    public int getId() {
        return id;
    }

    public int getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(int Especialidad) {
        this.Especialidad = Especialidad;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String Documento) {
        this.Documento = Documento;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public int getRol_id() {
        return Rol_id;
    }

    public void setRol_id(int Rol_id) {
        this.Rol_id = Rol_id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
}
