package JCommonTools.RefBook;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import JCommonTools.AsRegister;
import JCommonTools.CC;


public class fRefBook extends JFrame 
{
	public final static String REFBOOK_FILE_EXTENSION = "rfb";
	private ResourceBundle _bnd;
	private RefBook _rb;
	private JToolBar _bar;
	private JTree _tree;
	private DefaultTreeModel _trm;
	//private TreeModel _trm;
	private JLabel _sbiMain;

	private String _currFN;
	private String _prevFN;
	
	private String _prefPath;
	
	public String getPreferencePath() {
		return _prefPath;
	}
	public void setPreferencePath(String _prefPath) {
		this._prefPath = _prefPath;
	}

	public String getCurrentFileName()
	{
		return _currFN;
	}
	public void setCurrentFileName(String aFN)
	{
		_currFN = aFN;
	}
	
	public JToolBar getCommandBar()
	{
		return _bar;
	}
	
	public RefBook getRefBook() 
	{
		return _rb;
	}
	public void setRefBook(RefBook _rb) 
	{
		this._rb = _rb;
		_trm = new DefaultTreeModel(this._rb.mRBNodes);
		//_trm = new trmRefBook(this._rb);
		_tree.setModel(_trm);
	}
	
	public void setStatusText(String aText)
	{
		_sbiMain.setText(aText);
	}
	
	public fRefBook(RefBook aRB)
	{
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		_rb = aRB;
		_prefPath = null;

		
		Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(szScreen.width/3, szScreen.height/3);
		setLocation((int)(szScreen.width/2 - this.getWidth()/2), (int)(szScreen.height/2-this.getHeight()/2));
		
		/**
		 *   T O O L S   B A R
		 */
		_bar = new JToolBar();
		add(_bar, BorderLayout.NORTH);
		_bar.add(ActLoad);
		_bar.add(ActSave);
		_bar.addSeparator();
		_bar.add(ActNew);
		_bar.add(ActEdit);
		_bar.add(ActDelete);
		_bar.addSeparator();
		_bar.add(ActRefresh);
		
		_tree = new JTree();
		setRefBook(aRB);
		add(new JScrollPane(_tree), BorderLayout.CENTER);
		
		
		/**
		 * S T A T U S   B A R
		 */
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createRaisedBevelBorder());
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(statusBar, BorderLayout.SOUTH);
		_sbiMain = new JLabel();
		_sbiMain.setText("Welcome!");
		_sbiMain.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.add(_sbiMain);
		
		_tree.addTreeSelectionListener(new TreeSelectionListener() 
		{
			@Override
			public void valueChanged(TreeSelectionEvent e) 
			{
				try
				{
					if (e.getNewLeadSelectionPath() != null
						&& e.getNewLeadSelectionPath().getLastPathComponent() instanceof rbNode)
					{
						rbNode rbn = (rbNode) e.getNewLeadSelectionPath().getLastPathComponent();
						setStatusText(rbn.getId()+ ") "+rbn.getName()+"["+rbn.getAlias()+"]");
					}
				}
				catch (Exception ex)
				{
					setStatusText(ex.getMessage());
				}
			}
		});
		
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
		AsRegister.LoadFrameStateSizeLocation(node, this);
		_prevFN = node.get("PrevOpened", null);
	}
	
	private void SaveProgramPreference()
	{
		if (_prefPath == null)
			return;
		
		Preferences node = Preferences.userRoot().node(_prefPath);
		AsRegister.SaveFrameStateSizeLocation(node, this);
		if (_currFN != null && _currFN.length() > 0)
			node.put("PrevOpened", _currFN);
	}

	public Action ActLoad = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_setCurrentRBFileName();
			if (_currFN != null && _currFN.length() > 0)
				setRefBook(RefBook.Load(_currFN));
			
		}
	};
	public Action ActSave = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_currFN == null || _currFN.length() == 0)
				_setCurrentRBFileName();
			
			if (_currFN != null && _currFN.length() > 0)
			{
				String ret = _rb.Save(_currFN);
				if (ret != null)
				{
					setStatusText(String.format(_bnd.getString("Text.Error"), ret));
				}
				else
				{
					setStatusText(_bnd.getString("Text.Message.SaveSuccessfully"));
				}
			}
		}
	};
	public Action ActNew = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			rbNode node = new rbNode();
			dRefBookNode dlg = new dRefBookNode(node, _trm);
			TreePath tp = _tree.getSelectionPath();
			rbNode currNode = null;
			if (tp != null)
			{
				if (tp.getParentPath() == null)
					dlg.setNNowner((rbNode)tp.getLastPathComponent());
				else
					dlg.setNNowner((rbNode)tp.getParentPath().getLastPathComponent());
				currNode = (rbNode)tp.getLastPathComponent();
			}
			dlg.setPreferencePath(_prefPath + "/dEdit");
			dlg.setVisible(true);
			if (dlg.isResultOk())
			{
				rbNode owner =  dlg.getNNOwner();
				_trm.insertNodeInto(
						node, 
						owner, 
						currNode == null ? 
								owner.getNodes().size() : 
								currNode.getParent() == null || currNode.equals(owner) ?
									0	:
									((rbNode)currNode.getParent()).getNodes().indexOf(currNode)+1
				);
				_tree.setSelectionPath(new TreePath(_trm.getPathToRoot(node)));
			}
		}
	};
	public Action ActEdit = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			TreePath tp = _tree.getSelectionPath();
			if (tp != null)
			{
				//rbNode owner = (rbNode)tp.getParentPath().getLastPathComponent();
				rbNode node = (rbNode)tp.getLastPathComponent();
				dRefBookNode dlg = new dRefBookNode(node, _trm);
				dlg.setPreferencePath(_prefPath + "/dEdit");
				dlg.setVisible(true);
				if (dlg.isResultOk())
				{
					rbNode owner =  dlg.getNNOwner();
					if (node.getParent() != null && !node.getParent().equals(owner))
					{
						_trm.removeNodeFromParent(node);
						_trm.insertNodeInto(node, owner, owner.getNodes().size());
					}
					_trm.nodeChanged(node);
				}
			}
		}
	};
	public Action ActDelete = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			TreePath tp = _tree.getSelectionPath();
			if (tp != null)
			{
				rbNode node = (rbNode)tp.getLastPathComponent();
				if (node.getNodes().size() == 0)
				{
					String deletedNodeName = node.toString();
					if (JOptionPane.showConfirmDialog(
							fRefBook.this, 
							String.format(_bnd.getString("Text.Confirm.Delete.RefBook"), deletedNodeName),
							_bnd.getString("Text.dDeleteRefBook"),
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{
						rbNode owner = (rbNode)node.getParent(); 
						int ind = owner.getNodes().indexOf(node);
						_trm.removeNodeFromParent(node);
						if (owner.getNodes().size() > ind)
							node = owner.getNodes().get(ind);
						else
							node = owner;
						_tree.setSelectionPath(new TreePath(_trm.getPathToRoot(node)));
						setStatusText(String.format(_bnd.getString("Text.Message.DeletedItem"), deletedNodeName));
					}
					
				}
				else
				{
					setStatusText(_bnd.getString("Text.Message.MayDeleteLeafOnly"));
				}
			}
		}
	};

	public Action ActRefresh = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			TreePath tp = _tree.getSelectionPath();
			if (tp != null)
			{
				rbNode node;
				if (tp.getParentPath() != null)
					node = (rbNode)tp.getParentPath().getLastPathComponent();
				else
					node = (rbNode)tp.getLastPathComponent();
				_trm.nodeStructureChanged(node);
				_trm.nodeChanged(node);
			}
		}
	};
	
	private void _setCurrentRBFileName()
	{
		JFileChooser fDlg = new JFileChooser();
		FileNameExtensionFilter fnf = new FileNameExtensionFilter(_bnd.getString("RefBook.Text.FileChooser"), REFBOOK_FILE_EXTENSION );
		fDlg.setFileFilter(fnf);
		if (_currFN != null && _currFN.length() > 0)
			fDlg.setCurrentDirectory(new File(_currFN));
		else if (_prevFN != null && _prevFN.length() > 0)
			fDlg.setCurrentDirectory(new File(_prevFN));
		
		if (fDlg.showDialog(this, _bnd.getString("RefBook.Text.FileChooser")) == JFileChooser.APPROVE_OPTION)
		{
			_currFN = fDlg.getSelectedFile().getPath();
			if (!_currFN.toLowerCase().endsWith("."+REFBOOK_FILE_EXTENSION))
				_currFN += "." + REFBOOK_FILE_EXTENSION;
			//String[] ss = fDlg.getSelectedFile().
			//fnf.getExtensions();
		}
		else
		{
			_currFN = null;
		}
	}
	
	public void SetButtonDefaultText()
	{
		ActLoad.putValue(Action.NAME, _bnd.getString("Menu.File.Load"));
		ActSave.putValue(Action.NAME, _bnd.getString("Menu.File.Save"));
		
		ActNew.putValue(Action.NAME, _bnd.getString("RefBook.Menu.Item.New"));
		ActEdit.putValue(Action.NAME, _bnd.getString("RefBook.Menu.Item.Edit"));
		ActDelete.putValue(Action.NAME, _bnd.getString("RefBook.Menu.Item.Delete"));
		
		ActRefresh.putValue(Action.NAME, _bnd.getString("RefBook.Menu.Refresh"));
		
	}
	
 	public static void LoadComboModel(DefaultComboBoxModel<rbNode> aModCbo, rbNode aRBNode, boolean aIsEmptyItem)
	{
		aModCbo.removeAllElements();
		if (aIsEmptyItem)
			aModCbo.addElement(new rbNode());
		
		if (aRBNode == null)
			return;
		
		for (int ii = 0; ii < aRBNode.getChildCount(); ii++)
			aModCbo.addElement((rbNode)aRBNode.getChildAt(ii));
	}
	
	public static String FindRBNodeByIDInComModel(DefaultComboBoxModel<rbNode> aModCbo, int aID)
	{
		String ret = CC.STR_EMPTY;

		if (aModCbo != null)
			for (int ii = 0; ii < aModCbo.getSize(); ii++)
				if (aID == aModCbo.getElementAt(ii).getId())
					ret = aModCbo.getElementAt(ii).getName();

		return ret;
	}

	public static int FindRBNodeByNameInComModel(DefaultComboBoxModel<rbNode> aModCbo, String aName)
	{
		int ret = 0;

		rbNode rbn =  FindRBNodeByNameInComboBoxModel(aModCbo, aName);
		if (rbn != null)
			ret = rbn.getId();
		
		return ret;
	}

	public static rbNode FindRBNodeByNameInComboBoxModel(DefaultComboBoxModel<rbNode> aModCbo, String aName)
	{
		rbNode ret = null;

		if (aModCbo != null)
			for (int ii = 0; ii < aModCbo.getSize(); ii++)
				if (aName.equals(aModCbo.getElementAt(ii).getName()))
					ret = aModCbo.getElementAt(ii);
		
		return ret;
	}

	public static rbNode FindRBNodeByIDInComboBoxModel(DefaultComboBoxModel<rbNode> aModCbo, int aID)
	{
		rbNode ret = null;

		if (aModCbo != null)
			for (int ii = 0; ii < aModCbo.getSize(); ii++)
				if (aID == aModCbo.getElementAt(ii).getId())
					ret = aModCbo.getElementAt(ii);
		
		return ret;
	}


}
