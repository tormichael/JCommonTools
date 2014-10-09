package JCommonTools.RefBook;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class RBComboBoxCellEdit extends DefaultCellEditor
{
	JComboBox<rbNode> _cbo;
	public RBComboBoxCellEdit (JComboBox<rbNode> aCbo)
	{
		super(aCbo);
		_cbo = aCbo;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row, int column) 
	{
		DefaultComboBoxModel<rbNode> cbm = (DefaultComboBoxModel<rbNode>)_cbo.getModel();
		cbm.setSelectedItem(fRefBook.FindRBNodeByNameInComboBoxModel(cbm, value.toString()));
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
}
