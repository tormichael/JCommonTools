package JCommonTools.Param;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.GBC;
import JCommonTools.TreeListCellRenderer;
import JCommonTools.TreeListModel;

public class dPage extends JDialog 
{
	private Page _page;
	private Page _owner;
	private boolean _isNew;
	
	private ResourceBundle _bnd;
	private String _prefPath;
	private boolean _isResultOk;
	
	private JComboBox	_cbtParent;
	private JTextField	_txfTitle;
	private JTextField	_txfPrefNode;
	private JTextField	_txfIns;
	
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
	
	public Page getNNOwner() 
	{
		return _owner;
	}
	public void setNNowner(Page aOwner)
	{
		_owner = aOwner;
		_cbtParent.setSelectedItem(aOwner);
	}
	
	public dPage(Page aNode, TreeModel aTreeModel)
	{
		_page = aNode;
		_isResultOk = false;
		
		if (_page == null)
			_page = new Page();
		
		//_isNew = _node.IsNew();
		
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		this.setTitle(_bnd.getString("BookParam.Text.dPage"));
		this.setModal(true);
		//this.setModalityType(ModalityType.TOOLKIT_MODAL);
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnl =new JPanel(gbl);
		// first row
		JLabel lbl = new JLabel(_bnd.getString("BookParam.Label.Parent"));
		gbl.setConstraints(lbl, new GBC(0,0).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_cbtParent = new JComboBox(new TreeListModel(aTreeModel));
		gbl.setConstraints(_cbtParent, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
		pnl.add(_cbtParent);
		// next row
		lbl = new JLabel(_bnd.getString("BookParam.Label.Title"));
		gbl.setConstraints(lbl, new GBC(0,1).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_txfTitle = new JTextField();
		gbl.setConstraints(_txfTitle, new GBC(1,1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
		pnl.add(_txfTitle);
		// next row
		lbl = new JLabel(_bnd.getString("BookParam.Label.PrefsNode"));
		gbl.setConstraints(lbl, new GBC(0,2).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_txfPrefNode = new JTextField();
		gbl.setConstraints(_txfPrefNode, new GBC(1,2).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
		pnl.add(_txfPrefNode);
		// next row
		lbl = new JLabel(_bnd.getString("BookParam.Label.Inset"));
		gbl.setConstraints(lbl, new GBC(0,3).setIns(2).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_txfIns = new JTextField();
		gbl.setConstraints(_txfIns, new GBC(1,3).setIns(2).setFill(GBC.HORIZONTAL).setWeight(0.5, 0.0));
		pnl.add(_txfIns);
		
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
		_cbtParent.setSelectedItem(_page.getParent());
		
		_txfTitle.setText(_page.getTitle());
		_txfPrefNode.setText(_page.getPrefsNode());
		_txfIns.setText(CC.STR_EMPTY + _page.getInset());

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
			
			_owner = (Page)_cbtParent.getSelectedItem();
			
			if (_owner != null)
			{
				_page.setTitle(_txfTitle.getText());
				_page.setPrefsNode(_txfPrefNode.getText());
				_page.setInset(Integer.parseInt(_txfIns.getText()));
				//_node.setOwner(owner);
			
				/*if (_isNew)
				{
					owner.getNodes().add(_node);
				}
				else if (!_owner.equals(owner))
				{
					_owner.getNodes().remove(_node);
					owner.getNodes().add(_node);
				}*/
				_isResultOk = true;
				SaveProgramPreference();
				dPage.this.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(dPage.this, _bnd.getString("BookParam.Message.OwnerUndefined")); 
			}
		}
	};

	Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dPage.this.setVisible(false);
		}
	};
	

}
