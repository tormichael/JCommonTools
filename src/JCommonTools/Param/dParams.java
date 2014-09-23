package JCommonTools.Param;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.w3c.dom.views.AbstractView;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.Convert;
import JCommonTools.TableTools;
import JCommonTools.RefBook.fRefBook;

public class dParams extends JDialog 
{
	private ArrayList<Param> _params;
	private JTable _tab;
	private JTextArea _desc;
	private JSplitPane _spp;

	private ResourceBundle _bnd;
	private boolean _isResultOk;
	private String _prefPath;

	public boolean isResultOk()
	{
		return _isResultOk;
	}
	
	public String getPreferencePath() 
	{
		return _prefPath;
	}
	public void setPreferencePath(String _prefPath) 
	{
		this._prefPath = _prefPath;
	}

	public dParams(ArrayList<Param> aParams)
	{
		_prefPath = null;
		_isResultOk = false;
		_params = aParams;
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		
		this.setModal(true);
	
		_tab = new JTable(new tmParams(aParams));
		_tab.setRowHeight(_tab.getFont().getSize() + 3);
		_tab.getTableHeader().setPreferredSize(new Dimension(
				_tab.getTableHeader().getPreferredSize().width, 
				_tab.getTableHeader().getPreferredSize().height*2
		));
		//_tab.setRowHeight(-1, _tab.getFont().getSize()*2 + 6);
		_desc = new JTextArea();
		_spp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				new JScrollPane(_tab), 
				new JScrollPane(_desc)
		);
		this.add(_spp, BorderLayout.CENTER);
		
		JPanel pnlButton = new JPanel();
		pnlButton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JButton btnOk = new JButton(actOk);
		btnOk.setText(_bnd.getString("Button.Ok"));
		pnlButton.add(btnOk);
		JPanel pnlSep = new JPanel();
		pnlSep.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		pnlButton.add(pnlSep);
		JButton btnCancel = new JButton(actCancel);
		btnCancel.setText(_bnd.getString("Button.Cancel"));
		pnlButton.add(btnCancel);
		JPanel pnlButton2 = new JPanel(new BorderLayout());
		pnlButton2.add(pnlButton, BorderLayout.EAST);
		this.add(pnlButton2, BorderLayout.SOUTH);
	
		JComboBox<String> cboType = new JComboBox<String>();	
		cboType.addItem("String");
		cboType.addItem("Integer");
		cboType.addItem("Date");
		cboType.addItem("Boolean");
		_tab.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(cboType));
		
		JComboBox<String> cboSwCtrlName = new JComboBox<String>();
		cboSwCtrlName.addItem("JTextField");
		cboSwCtrlName.addItem("JTextArea");
		cboSwCtrlName.addItem("JFormattedTextField");
		cboSwCtrlName.addItem("JPasswordField");
		cboSwCtrlName.addItem("JSpinner");
		cboSwCtrlName.addItem("JComboBox<E>");
		cboSwCtrlName.addItem("JCheckBox");
		cboSwCtrlName.addItem("JToggleButton");
		cboSwCtrlName.addItem("JRadioButton");
		cboSwCtrlName.addItem("JSlider");
		cboSwCtrlName.addItem("JFileChooser");
		cboSwCtrlName.addItem("JColorChooser");
		_tab.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(cboSwCtrlName));
	
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowOpened(WindowEvent e) 
			{
				LoadProgramPreference ();
				super.windowOpened(e);
			}

		});
	}
	
	private void LoadProgramPreference()
	{
		if (_prefPath == null)
		{
			this.setSize(300, 300);
			return;
		}
		
		Preferences node = Preferences.userRoot().node(_prefPath);
		AsRegister.LoadWindowLocation(node, this);
		AsRegister.LoadWindowSize(node, this);
		_spp.setDividerLocation(node.getInt("SplitPane", 200));
		TableTools.SetColumnsWidthFromString(_tab, node.get("TabColWidth_Connection", CC.STR_EMPTY));
	}
	
	private void SaveProgramPreference()
	{
		if (_prefPath == null)
			return;
		
		Preferences node = Preferences.userRoot().node(_prefPath);
		AsRegister.SaveWindowLocation(node, this);
		AsRegister.SaveWindowSize(node, this);
		node.putInt("SplitPane", _spp.getDividerLocation());
		node.put("TabColWidth_Connection", TableTools.GetColumnsWidthAsString(_tab));
	}

	Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			if (true)
			{
				_isResultOk = true;
				SaveProgramPreference();
				dParams.this.setVisible(false);
			}
//			else
//			{
//				JOptionPane.showMessageDialog(dParams.this, _bnd.getString("BookParam.Message.OwnerUndefined")); 
//			}
		}
	};

	Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dParams.this.setVisible(false);
		}
	};
	
	class tmParams extends AbstractTableModel
	{
		private ArrayList<Param> _params;
		//private JComboBox<String> _cboType;
		//private JComboBox<String> _cboSwCtrlName;
	
		public tmParams(ArrayList<Param> aPRMS) //, JComboBox<String> aCboType, JComboBox<String> aCboSwCtrlName)
		{
			_params= aPRMS;
			//_cboType = aCboType;
			//_cboSwCtrlName = aCboSwCtrlName;
		}
		
		@Override
		public int getColumnCount() 
		{
			return 7;
		}

		@Override
		public int getRowCount() 
		{
			return _params != null ? _params.size() + 1 : 1;
		}

		@Override
		public String getColumnName(int columnIndex) 
		{
			String ret = super.getColumnName(columnIndex);
			ResourceBundle bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
			
			switch (columnIndex){
				case 0:
					ret = bnd.getString("BookParam.Table.Params.ColName.Number");
					break;
				case 1:
					ret = bnd.getString("BookParam.Table.Params.ColName.PrefsKey");
					break;
				case 2:
					ret = bnd.getString("BookParam.Table.Params.ColName.Title");
					break;
				case 3:
					ret = bnd.getString("BookParam.Table.Params.ColName.Type");
					break;
				case 4:
					ret = bnd.getString("BookParam.Table.Params.ColName.swCtrlName");
					break;
				case 5:
					ret = bnd.getString("BookParam.Table.Params.ColName.swCtrlOpt");
					break;
				case 6:
					ret = bnd.getString("BookParam.Table.Params.ColName.swCtrlRestriction");
					break;
			}
			return ret;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) 
		{
			Object ret = null;
			if (_params != null && _params.size() > rowIndex)
			{
				switch (columnIndex){
					case 0:
						ret = _params.get(rowIndex).getNumber();
						break;
					case 1:
						ret = _params.get(rowIndex).getPrefsKey();
						break;
					case 2:
						ret = _params.get(rowIndex).getTitle();
						break;
					case 3:
						ret = _params.get(rowIndex).getType();
						break;
					case 4:
						ret = _params.get(rowIndex).getSwingCtrlName();
						break;
					case 5:
						ret = _params.get(rowIndex).getSwingCtrlOptional();
						break;
					case 6:
						ret = _params.get(rowIndex).getSwingCtrlRestriction();
						break;
				}				
				
			}
			return ret;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			//return super.isCellEditable(rowIndex, columnIndex);
			return true;
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
		{
			if (rowIndex == _params.size())
			{
				_params.add(new Param());
			}
			
			switch (columnIndex)
			{
				case 0:
					_params.get(rowIndex).setNumber(Integer.parseInt(aValue.toString()));
					break;
				case 1:
					_params.get(rowIndex).setPrefsKey(aValue.toString());
					break;
				case 2:
					_params.get(rowIndex).setTitle(aValue.toString());
					break;
				case 3:
					_params.get(rowIndex).setType(aValue.toString());
					break;
				case 4:
					_params.get(rowIndex).setSwingCtrlName(aValue.toString());
					break;
				case 5:
					_params.get(rowIndex).setSwingCtrlOptional(aValue.toString());
					break;
				case 6:
					_params.get(rowIndex).setSwingCtrlRestriction(aValue.toString());
					break;
			}
		}
}
}
