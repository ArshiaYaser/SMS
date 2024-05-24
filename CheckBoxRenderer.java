package sms;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
    public CheckBoxRenderer() {
        super();
        setHorizontalAlignment(CENTER); // Center align the checkbox within the cell
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected((value != null && (boolean) value));
        return checkBox;
    }


}
