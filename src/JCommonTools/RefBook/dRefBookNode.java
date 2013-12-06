package JCommonTools.RefBook;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.ComboTree;
import JCommonTools.GBC;
import JCommonTools.TreeListCellRenderer;
import JCommonTools.TreeListModel;
import JCommonTools.DB.DBDriver;
import JCommonTools.DB.dDBDriver;

public class dRefBookNode extends JDialog 
{
	private rbNode _node; 
	
	private ResourceBundle _bnd;
	private String _prefPath;
	private boolean _isResultOk;
	
	private JComboBox	_cbtParent;
	private JTextField	_txfName;
	private JTextField	_txfAlias;
	private JTextField	_txfID;
	private JTextField	_txfCodeRef;
	
	public String getPreferencePath() 
	{
		return _prefPath;
	}
	public void setPreferencePath(String _prefPath) 
	{
		this._prefPath = _prefPath;
	}
	
	public boolean isResultOk()
	{
		return _isResultOk;
	}
	
	public dRefBookNode(rbNode aNode, TreeModel aTreeModel)
	{
		_node = aNode;
		_isResultOk = false;
		
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		this.setTitle(_bnd.getString("RefBook.Text.dRefBook"));
		this.setModal(true);
		//this.setModalityType(ModalityType.TOOLKIT_MODAL);
		this.setSize(300, 300);
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnl =new JPanel(gbl);
		// first row
		JLabel lbl = new JLabel(_bnd.getString("RefBook.Label.Parent"));
		gbl.setConstraints(lbl, new GBC(0,0).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_cbtParent = new JComboBox(new TreeListModel(aTreeModel));
		gbl.setConstraints(_cbtParent, new GBC(1,0).setIns(2).setGridSpan(3, 1).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
		pnl.add(_cbtParent);
		// next row
		lbl = new JLabel(_bnd.getString("RefBook.Label.Name"));
		gbl.setConstraints(lbl, new GBC(0,1).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_txfName = new JTextField();
		gbl.setConstraints(_txfName, new GBC(1,1).setIns(2).setGridSpan(3, 1).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
		pnl.add(_txfName);
		// next row
		lbl = new JLabel(_bnd.getString("RefBook.Label.Alias"));
		gbl.setConstraints(lbl, new GBC(0,2).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_txfAlias = new JTextField();
		gbl.setConstraints(_txfAlias, new GBC(1,2).setIns(2).setGridSpan(3, 1).setFill(GBC.HORIZONTAL));
		pnl.add(_txfAlias);
		// next row
		lbl = new JLabel(_bnd.getString("RefBook.Label.ID"));
		gbl.setConstraints(lbl, new GBC(0,3).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_txfID = new JTextField();
		gbl.setConstraints(_txfID, new GBC(1,3).setIns(2).setFill(GBC.HORIZONTAL).setWeight(0.5, 0.0));
		pnl.add(_txfID);
		JButton btn = new JButton(actNextID);
		gbl.setConstraints(btn, new GBC(2,3).setIns(2).setFill(GBC.BOTH));
		pnl.add(btn);
		_txfCodeRef = new JTextField();
		gbl.setConstraints(_txfCodeRef, new GBC(3,3).setIns(2).setFill(GBC.HORIZONTAL).setWeight(0.5, 0.0));
		pnl.add(_txfCodeRef);
		
		
		this.add(pnl, BorderLayout.NORTH);
		
		this.add(new JPanel(), BorderLayout.CENTER);
		
		JPanel pnlButton = new JPanel();
		JButton _btnOk = new JButton(actOk);
		_btnOk.setText(_bnd.getString("Button.Ok"));
		pnlButton.add(_btnOk);
		JButton _btnCancel = new JButton(actCancel);
		_btnCancel.setText(_bnd.getString("Button.Cancel"));
		pnlButton.add(_btnCancel);
		this.add(pnlButton, BorderLayout.SOUTH);

		JTree tree = new JTree(aTreeModel);
		_cbtParent.setRenderer(new TreeListCellRenderer(aTreeModel, tree.getCellRenderer()));
		
		//_cbtParent.setSelectedItem(anObject);
		_txfName.setText(_node.getName());
		_txfAlias.setText(_node.getAlias());
		_txfID.setText(CC.STR_EMPTY + _node.getId());
		
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowActivated(WindowEvent e) 
			{
				LoadProgramPreference ();
				super.windowActivated(e);
			}
			
			@Override
			public void windowClosing(WindowEvent e) 
			{
				SaveProgramPreference();
				super.windowClosing(e);
			}
		});
	}

	private void LoadProgramPreference()
	{
		if (_prefPath == null)
			return;
		
		Preferences node = Preferences.userRoot().node(_prefPath);
		AsRegister.LoadWindowLocation(node, this);
		AsRegister.LoadWindowSize(node, this);
	}
	
	private void SaveProgramPreference()
	{
		if (_prefPath == null)
			return;
		
		Preferences node = Preferences.userRoot().node(_prefPath);
		AsRegister.SaveWindowLocation(node, this);
		AsRegister.SaveWindowSize(node, this);
	}

	Action actSelectedParent = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			//SelectTable((TreePath) e.getSource(), _cboFldBase, true);
			//_tabF2F.setRowHeight(_cboFldBase.getPreferredSize().height);
		}
	};

	
	Action actNextID = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};
	
	Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_node.setName(_txfName.getText());
			_node.setAlias(_txfAlias.getText());
			_node.setId(Integer.parseInt(_txfID.getText()));
			_isResultOk = true;
			dRefBookNode.this.setVisible(false);
		}
	};

	Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dRefBookNode.this.setVisible(false);
		}
	};
	
}
