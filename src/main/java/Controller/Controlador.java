/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BD.CConsulta;
import BD.COrden;
import BD.CPaciente;
import BD.CUsuario;
import BD.Reportes;
import BD.TConsultas;
import BD.TOrdenes;
import BD.VOrden;
import BD.VUsuario;
import Backend.DatosConsulta;
import Backend.DatosOrden;
import Backend.USesion;
import Conexion.ConexionBD;
import Exceptions.CamposVaciosException;
import Exceptions.CorreoInvalidoException;
import Exceptions.DatoInvalidoException;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan David, Miguel Angel y Sebastian
 * 
 *Esta clase es el intermediador entre el Front y los demas
 *servios del sistema, aqui se analizan datos, se validan y se 
 *pasan a las demas clases para utilizarlos
 * 
 */

public class Controlador {
    
    Reportes BD = new Reportes();
    private ConexionBD conexionBD = new ConexionBD();

    //Validamos los datos de un usario para agregarlo
    public boolean AgregarUsuarios(JTextField TxfNombre, JTextField TxfTelefono, JTextField TxfEdad, JTextField TxfDocumento, JComboBox<String> CmbEspecialidad, JComboBox<String> CmbRol, JTextField TxfCorreo, JPasswordField TxfContra) {

        try {

            //Extraemos el texto de cada parametro
            String nombre = TxfNombre.getText().trim();
            String telefono = TxfTelefono.getText().trim();
            String edadStr = TxfEdad.getText().trim();
            String documento = TxfDocumento.getText().trim();
            String correo = TxfCorreo.getText().trim();
            String contra = new String(TxfContra.getPassword()); //Esto ya que la contraseña se pasa de tipo Password
            int rol = CmbRol.getSelectedIndex() + 1;
            Integer especialidad = null;

            //Validamos si el usuario es Admin y si debe tener especialidad
            if (rol != 1) {
                int index = CmbEspecialidad.getSelectedIndex();
                if (index >= 0) {
                    especialidad = index + 1;
                } else {
                    throw new CamposVaciosException("Debe seleccionar una especialidad.");
                }
            }

            //Validamos campos vacios
            if (nombre.isEmpty() || telefono.isEmpty() || edadStr.isEmpty() || documento.isEmpty() || correo.isEmpty() || contra.isEmpty()){

                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Todos los campos deben estar completos.");
            }

            //Validamos si el correo tiene el formato adecuado
            if (!correo.contains("@") || !correo.contains(".")) {

                //Creamos una nueva excepcion en caso de error
                throw new CorreoInvalidoException("El correo electrónico no es válido.");
            }

            //Validamos que la edad sea un numero
            int edad;
            try {
                edad = Integer.parseInt(edadStr);
            } catch (NumberFormatException e) {

                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("La edad debe ser un número entero.");
            }

            //Validamos que la edad este dentro de un rango adecuado
            if (edad >= 100 || edad <= 18) {

                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("La edad no es valida.");
            }

            //Validamos que el telefono sea numerico y tenga minimo 8 caracteres
            if (!telefono.matches("\\d+") || telefono.length() < 8) {

                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("El teléfono solo debe contener números y debe tener minimo 8 digitos.");
            }

            //Validamos que el documento sea numerico y tenga minimo 8 caracteres
            if (!documento.matches("\\d+") || documento.length() < 8) {

                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("El documento solo debe contener números y debe ser minimo de 8 digitos.");
            }

            CUsuario BD = new CUsuario();

            //Pasamos los datos validados para crear usuario
            BD.AgregarUsuarios(nombre, telefono, edad, documento, especialidad, rol, correo, contra);

            return true;

        } 
        catch (CamposVaciosException | CorreoInvalidoException | DatoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false; // ❌ Error de validación
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            return false; // ❌ Error inesperado
        }
    }

    //Validamos que los datos inngresados para iniciar sesion sean adecuados
    public Boolean ValidarUsuario(JTextField TxfUsuario, JPasswordField TxfContra) {
        try {
            
            //Extraemos el texto de cada parametro
            String usuario = TxfUsuario.getText().trim();
            String contra = new String(TxfContra.getPassword()).trim();

            //Validamos campos vacios
            if (usuario.isEmpty() || contra.isEmpty()) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Debe ingresar usuario y contraseña.");
            }

            //Validamos que el documento sea numero
            if (!usuario.matches("\\d+")) {
                
                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("El documento solo debe contener números.");
            }
            
            VUsuario BD = new VUsuario();
            
            //Pasamos los datos validados para buscar el usuario
            boolean existe = BD.ValidarUsuario(usuario, contra);
            return existe;

        } catch (CamposVaciosException | DatoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            return false;
        }
    }
    
    //Validamos los datos para agregar un paciente
    public void AgregarPaciente(JTextField TxfNombre, JTextField TxfTelefono, JTextField TxfEdad, JTextField TxfDocumento, JTextField TxfCorreo, JTextField TxfDireccion) {

        try {
            
            //Extraemos el texto de cada parametro
            String nombre = TxfNombre.getText().trim();
            String telefono = TxfTelefono.getText().trim();
            String edadStr = TxfEdad.getText().trim();
            String documento = TxfDocumento.getText().trim();
            String correo = TxfCorreo.getText().trim();
            String direccion = TxfDireccion.getText().trim();

            //Validamos campos vacios
            if (nombre.isEmpty() || telefono.isEmpty() || edadStr.isEmpty() || documento.isEmpty() || correo.isEmpty() || direccion.isEmpty()) {

                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Todos los campos deben estar completos.");
            }

            //Validamos que el documento sea numerico y que tenga minimo 8 caracteres
            if (!documento.matches("\\d+") || documento.length() < 8) {
                
                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("El documento solo debe contener números y debe tener minimo 8 digitos.");
            }

            //Validamos que el telefono sea numerico y que tenga minimo 8 caracteres
            if (!telefono.matches("\\d+") || telefono.length() < 8) {
                
                //Extraemos el texto de cada parametro
                throw new DatoInvalidoException("El teléfono solo debe contener números y debe tener minimo 8 digitos.");
            }

            //Validamos que la edad sea un numero
            int edad;
            try {
                edad = Integer.parseInt(edadStr);
            } catch (NumberFormatException e) {
                
                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("La edad debe ser un número entero.");
            }

            //Validamos que la edad este en un rango adecuado
            if (edad < 0 || edad > 100) {
                
                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("La edad debe estar entre 0 y 120 años.");
            }

            //Validamos que el correo tenga un formato adecuado
            if (!correo.contains("@") || !correo.contains(".")) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CorreoInvalidoException("El correo electrónico no es válido.");
            }
            
            CPaciente bd = new CPaciente();
            
            //Despues de validar los datos, los pasamos para crear un paciente nuevo
            bd.AgregarPaciente(nombre, telefono, edad, documento, correo, direccion);

        } 
        catch (CamposVaciosException | CorreoInvalidoException | DatoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        }
    }
    
    //Cargamos los datos a la tabla de ordenes
    public void cargarOrdenes(JTable Tbl) {
        DefaultTableModel model = (DefaultTableModel) Tbl.getModel();
        model.setRowCount(0); // limpiar tabla

        //Creamos un objeto que traera los datos de ordenes
        TOrdenes torder = new TOrdenes();
        ResultSet rs = torder.obtenerOrdenes();

        try {
            while (rs != null && rs.next()) {
                model.addRow(new Object[]{
                    
                    //Agregamos los datos a la tabla del front
                    rs.getInt("idOrden"),
                    rs.getString("fecha"),
                    rs.getString("pacienteNombre"),
                    rs.getString("documento"),
                    rs.getInt("codServicio"),
                    rs.getString("nomServicio"),
                    rs.getString("creador"),
                    rs.getString("cargo"),
                    rs.getString("tipo"),
                    rs.getString("estado")    
                });

            }
        } catch (Exception e) {
            System.out.println("Error al cargar ordenes: " + e.getMessage());
        }
    }   
    
    //Validamos los datos para agregar una nueva orden
    public void agregarOrden(JComboBox<String> paciente_id, JComboBox<String> service_id, JComboBox<String> tipo_id, JComboBox<String> estado_id) {

        try {
            //Validamos que haya una paciente seleccionado
            if (paciente_id.getSelectedIndex() == -1) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Debe seleccionar un paciente.");
            }

            String seleccionado = (String) paciente_id.getSelectedItem();
            //Validamos que el paciente seleccionado tenga formato adecuado
            if (!seleccionado.contains(" - ")) {
                
                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("El formato del paciente es incorrecto.");
            }

            String documento = seleccionado.split(" - ")[1].trim();
            //Validamos que el documento extraido sea adecuado
            if (!documento.matches("\\d+")) {
                
                //Creamos una nueva excepcion en caso de error
                throw new DatoInvalidoException("El documento del paciente no es válido.");
            }

            //Validamos que el usuario haya seleccionado un servicio
            if (service_id.getSelectedIndex() == -1) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Debe seleccionar un servicio.");
            }
            int ser_id = service_id.getSelectedIndex() + 1;
            
            //Validamos que haya un usuario loggeado
            if (USesion.usuarioId <= 0) {
                
                //Creamos una nueva excepcion en caso de error
                throw new Exception("No se ha detectado un usuario logueado.");
            }
            int usr_id = USesion.usuarioId;

            //Validamos que se haya seleccionado un tipo de orden
            if (tipo_id.getSelectedIndex() == -1) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Debe seleccionar un tipo de orden.");
            }
            int tip_id = tipo_id.getSelectedIndex() + 1;

            //Validamos que se haya seleccionado un estado de orden
            if (estado_id.getSelectedIndex() == -1) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Debe seleccionar un estado.");
            }
            int est_id = estado_id.getSelectedIndex() + 1;

            COrden BD = new COrden();
            
            //Despues de validar los datos, se crea una nueva orden
            BD.AgregarOrden(documento, ser_id, usr_id, tip_id, est_id);

        } catch (CamposVaciosException | DatoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), 
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error inesperado: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Cargamos los datos a la tabla de consultas
    public void cargarConsultas(JTable tbl) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0); // limpiar tabla

        //Creamos un objeto que traera los datos de consultas
        TConsultas tconsultas = new TConsultas();
        ResultSet rs = null;

        try {
            rs = tconsultas.obtenerConsultas();

            //Validamos que se hayan recibido consultas
            if (rs == null) {
                System.out.println("No se recibieron datos desde obtenerConsultas().");
                return;
            }

            while (rs.next()) {
                model.addRow(new Object[]{
                    
                    //Se agregan los datos a la tabla de consultas
                    rs.getInt("id"),
                    rs.getString("pacienteNombre"),
                    rs.getString("documento"),
                    rs.getString("cie10Nombre"),
                    rs.getString("observaciones"),
                    rs.getString("fechaConsulta"),
                    rs.getInt("idOrden"),
                    rs.getString("especialidadNombre")
                });
            }

        } catch (Exception e) {
            System.out.println("Error al cargar consultas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar ResultSet: " + ex.getMessage());
            }
        }
    }
    
    //Validamos los datos para agregar una nueva consulta
    public void agregarConsulta(JComboBox<String> paciente_id, JComboBox<String> cie10_id, String observaciones, JComboBox<String> order_id, JComboBox<String> servicio_id) {
                          
            try {
                
                //Validamos que se haya seleccionado un paciente
                if (paciente_id.getSelectedIndex() == -1) {
                    
                    //Creamos una nueva excepcion en caso de error
                    throw new CamposVaciosException("Debe seleccionar un paciente.");
                }

                String seleccionado = (String) paciente_id.getSelectedItem();

                //Validamos que el formato de paciente sea adecuado
                if (!seleccionado.contains(" - ")) {
                    
                    //Creamos una nueva excepcion en caso de error
                    throw new DatoInvalidoException("El formato del paciente es incorrecto.");
                }

                String documento = seleccionado.split(" - ")[1].trim();

                //Validamos que se haya seleccionado un Cie10
                if (cie10_id.getSelectedIndex() == -1) {
                    
                    //Creamos una nueva excepcion en caso de error
                    throw new CamposVaciosException("Debe seleccionar un código CIE10.");
                }

                //Extraemos el codigo de Cie10 para buscarlo en la base de datos
                String seleccionadoCie10 = (String) cie10_id.getSelectedItem();
                String cie10_codigo = seleccionadoCie10.split(" - ")[0].trim();

                //Validamos que se haya seleccionado una orden
                if (order_id.getSelectedIndex() == -1) {
                    
                    //Creamos una nueva excepcion en caso de error
                    throw new CamposVaciosException("Debe seleccionar una orden asociada.");
                }
                int ord_id = order_id.getSelectedIndex() + 1;
                
                //Validamos que se haya seleccionado un servicio
                if (servicio_id.getSelectedIndex() == -1) {
                    
                    //Creamos una nueva excepcion en caso de error
                    throw new CamposVaciosException("Debe seleccionar un servicio.");
                }
                int serv_id = servicio_id.getSelectedIndex() + 1;

                CConsulta BD = new CConsulta();
                
                //Una vez validamos los datos, los pasamos para crear una nueva consulta
                BD.AgregarConsulta(documento, cie10_codigo, observaciones, ord_id, serv_id);

              

            } catch (CamposVaciosException | DatoInvalidoException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error inesperado: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    //Validamos que el paciente tenga una orden asociada
    public Boolean ValidarOrdenes(JComboBox<String> cmbPacientes, JComboBox<String> cmbOrdenes) {
        try {
            
            //Extraemos el texto de cada parametro
            String pacienteStr = (String) cmbPacientes.getSelectedItem();
            String ordenStr = (String) cmbOrdenes.getSelectedItem();
            int ordenId = Integer.parseInt(ordenStr);
            String documento = pacienteStr.split(" - ")[1].trim();

            //Validamos que el paciente tenga una orden
            VOrden BD = new VOrden();
            boolean valido = BD.OrdenEsDelPaciente(documento, ordenId);

            return valido;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    //Traemos las ordenes de un paciente en especifico para cargarlas en las historias clinicas
    public String[] cargarOrdenesPorDocumento(JComboBox<String> cmbPacientes, JTable TblOrdenes){
        try {
            
            //Extraemos el paciente seleccionado
            String seleccionado = (String) cmbPacientes.getSelectedItem();

            //Validamos que se haya seleccionado un paciente
            if (seleccionado == null || seleccionado.isEmpty()) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Seleccione un paciente.");
            }

            //Dividimos el formato de paciente para extraer el documento
            String[] partes = seleccionado.split(" - ");
            String documento = partes[1].trim(); //Documento del paciente

            //Cargamos las ordenes del paciente mediante el documento
            TOrdenes torden = new TOrdenes();
            ResultSet rs = torden.obtenerOrdenesPorDocumento(documento);

            DefaultTableModel model = (DefaultTableModel) TblOrdenes.getModel();
            model.setRowCount(0); //Limpiamos la tabla

            while (rs.next()) {
                model.addRow(new Object[]{
                    
                    //Los cargamos en la tabla de historias clinicas
                    rs.getInt("codServicio"),
                    rs.getString("creador"),
                    rs.getString("documento"),
                    rs.getString("tipo"),
                    rs.getString("estado"),
                    rs.getString("fecha")
                });
            }

            return partes;

        } catch (CamposVaciosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error buscando historia: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    } 
    
    //Traemos las consultas de un paciente en especifico para cargarlas en las historias clinicas
    public void cargarConsultasPorDocumento(JComboBox<String> cmbPacientes, JTable TblConsultas) {
        try {
            
            //Extraemos el paciente seleccionado
            String seleccionado = (String) cmbPacientes.getSelectedItem();

            //Validamos que se haya seleccionado un paciente
            if (seleccionado == null || seleccionado.isEmpty()) {
                
                //Creamos una nueva excepcion en caso de error
                throw new CamposVaciosException("Seleccione un paciente.");
            }

            //Dividimos el formato de paciente para extraer el documento
            String[] partes = seleccionado.split(" - ");
            String documento = partes[1].trim();

            //Cargamos las consultas del paciente mediante el documento
            TConsultas tconsultas = new TConsultas();
            ResultSet rs = tconsultas.obtenerConsultasPorDocumento(documento);

            DefaultTableModel model = (DefaultTableModel) TblConsultas.getModel();
            model.setRowCount(0); //Limpiamos la tabla

            while (rs.next()) {
                model.addRow(new Object[]{
                    //Los cargamos en la tabla de historias clinicas
                    rs.getString("cie10Nombre"),
                    rs.getString("observaciones"),
                    rs.getInt("orden_id"),          
                    rs.getString("fecha")         
                });
            }

            if (rs != null) rs.close();

        } catch (CamposVaciosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error cargando consultas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Contamos la cantidad de usuarios registrados en el sistema para el dashboard
    public int contarUsuarios() {
        
        //Pasamos la consulta
        return BD.consultarConteo("SELECT COUNT(*) FROM usuarios");
    }

    //Contamos la cantidad de pacientes registrados en el sistema para el dashboard
    public int contarPacientes() {
        
        //Pasamos la consulta
        return BD.consultarConteo("SELECT COUNT(*) FROM pacientes");
    }

    //Contamos la cantidad de historias clinicas registradas en el sistema para el dashboard
    public int contarHistorias() {
        
        //Pasamos la consulta
        return BD.consultarConteo("SELECT COUNT(*) FROM pacientes");
    }

    //Contamos la cantidad de ordenes registradas en el sistema para el dashboard
    public int contarOrdenes() {
        
        //Pasamos la consulta
        return BD.consultarConteo("SELECT COUNT(*) FROM ordenes where tipo_id = 1");
    }

    //Contamos la cantidad de remisiones registradas en el sistema para el dashboard
    public int contarRemisiones() {
        
        //Pasamos la consulta
        return BD.consultarConteo("SELECT COUNT(*) FROM ordenes WHERE tipo_id = 2");
    }

    //Cargamos la tabla de resumen de consultas por especialidad para el dashboard
    public javax.swing.table.TableModel consultasPorEspecialidad() {
        
        //Pasamos la consulta
        return BD.cargarTabla(
            "SELECT COALESCE(e.nombre, 'Sin servicio') AS servicios, COUNT(c.id) AS total " +
            "FROM consultas c " +
            "LEFT JOIN servicios e ON c.servicio_id = e.id " +
            "GROUP BY e.nombre"
        );
    }
    
    //Cargamos los datos de las ordenes para crear PDF
    public DatosOrden obtenerDatosOrden(int idOrden) {
        
        //Creamos un objeto de traer ordenes y obtenemos los datos de las ordenes
        TOrdenes datos = new TOrdenes();
        DatosOrden d = datos.obtenerDatosOrden(idOrden);
        return d;
    }
    
    //Cargamos los datos de las consultas para crear PDF
    public DatosConsulta obtenerDatosConsulta(int idConsulta) {
        
        //Creamos un objeto de traer consultas y obtenemos los datos de las consultas
        TConsultas datos = new TConsultas();
        DatosConsulta d = datos.obtenerDatosConsulta(idConsulta);
        return d;
    }

}

