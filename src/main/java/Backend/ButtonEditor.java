/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;

/**
 *
 * @author Juan David
 * 
 * Esta clase coloca un botón dentro de cada fila de una tabla, 
 * y cuando ese botón se presiona, obtiene el ID de esa fila y 
 * llama a un método que genera un PDF.
 * 
 */

public class ButtonEditor extends DefaultCellEditor {

    protected JButton btn;
    private boolean clicked;
    private int row;
    private JTable table;
    private PDFCallback callback;

    public interface PDFCallback {
        void onPDFRequest(int id);
    }

    public ButtonEditor(JTextField txt, PDFCallback callback) {
        super(txt);
        this.callback = callback;

        btn = new JButton("🖨️"); //Icono del boton
        btn.setOpaque(true);

        btn.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int col) {

        this.table = table;
        this.row = row;
        clicked = true;

        return btn;
    }

    @Override
    public Object getCellEditorValue() {

        if (clicked) {
            try {
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                callback.onPDFRequest(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        clicked = false;
        return "";
    }
}


