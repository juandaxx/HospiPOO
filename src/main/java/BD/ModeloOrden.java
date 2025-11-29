/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

/**
 *
 * @author Juan David
 * 
 * Esta clase define el modelo que va a tener las ordenes
 * definiendo los getters y los setters necesarios para los 
 * datos de ordenes
 * 
 */

public class ModeloOrden {
    
    //Definimos atributos de las ordenes
    int id;
    String Fecha;
    int paciente_id;
    int servicio_id;
    int usuario_id;
    int tipo_id;
    int estado_id;

    //Definimos getters y setters
    public int getId() {
        return id;
    }

    public String getFecha() {
        return Fecha;
    }


    public int getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(int paciente_id) {
        this.paciente_id = paciente_id;
    }

    public int getServicio_id() {
        return servicio_id;
    }

    public void setServicio_id(int servicio_id) {
        this.servicio_id = servicio_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(int tipo_id) {
        this.tipo_id = tipo_id;
    }

    public int getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }
    
}
