package JCommonTools.Param;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import JCommonTools.AsRegister;
import JCommonTools.CC;

public class fBookParam extends JDialog
{
	public final static String BOOKPARAM_FILE_EXTENSION = "bpr";

	private BookParam _bp;
	private ResourceBundle _bnd;
	
	private JTree _tree;
	private DefaultTreeModel _trm;
	private pnlParams _params;
	private JSplitPane _pnlBP;

	private String _prefPath;
	private String _currFN;
	
	public String getCurrentFileName()
	{
		return _currFN;
	}
	
	public void setBookParam(BookParam aBP)
	{
		this._bp = aBP;
		_trm = new DefaultTreeModel(this._bp.getBookPage());
		_tree.setModel(_trm);
	}
	
	public String getPreferencePath() {
		return _prefPath;
	}
	public void setAppPreferencePath(String _prefPath) {
		this._prefPath = _prefPath + "/BookParam";
	}

	public fBookParam(BookParam aBP)
	{
		_bp = aBP;
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		_currFN = null;

		//this.setModalExclusionType(ModalExclusionType.);
		this.setModal(true);
		
		_tree = new JTree();
		setBookParam(aBP);
		_tree.setRootVisible(false);
		_params = new pnlParams();
		
		_pnlBP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(_tree), _params);
		add(_pnlBP, BorderLayout.CENTER);
		
		JPanel pnlButton = new JPanel(new BorderLayout());
		JButton btnExport = new JButton(actExport);
		btnExport.setText(_bnd.getString("Button.Export"));
		JButton btnImport = new JButton(actImport);
		btnImport.setText(_bnd.getString("Button.Import"));
		JButton btnOk = new JButton(actOk);
		btnOk.setText(_bnd.getString("Button.Ok"));
		JButton btnCancel = new JButton(actCancel);
		btnCancel.setText(_bnd.getString("Button.Cancel"));
		JPanel pnlBtnIn = new JPanel(new BorderLayout());
		pnlBtnIn.add(btnImport, BorderLayout.WEST);
		pnlBtnIn.add(btnOk, BorderLayout.EAST);
		pnlButton.add(btnExport, BorderLayout.WEST);
		pnlButton.add(pnlBtnIn, BorderLayout.CENTER);
		pnlButton.add(btnCancel, BorderLayout.EAST);
		add(pnlButton, BorderLayout.SOUTH);
		pnlButton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pnlBtnIn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		
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
		//AsRegister.LoadFrameStateSizeLocation(node, this);
		AsRegister.LoadWindowLocation(node, this);
		AsRegister.LoadWindowSize(node, this);
		_pnlBP.setDividerLocation(node.getInt("SplitBP", 200));
	}
	
	private void SaveProgramPreference()
	{
		if (_prefPath == null)
			return;
		
		Preferences node = Preferences.userRoot().node(_prefPath);
		//AsRegister.SaveFrameStateSizeLocation(node, this);
		AsRegister.SaveWindowLocation(node, this);
		AsRegister.SaveWindowSize(node, this);

		node.putInt("SplitBP", _pnlBP.getDividerLocation());
	}
	
	public void setEditable(boolean aFlg)
	{
		JToolBar _bar = new JToolBar();
		this.add(_bar, BorderLayout.NORTH);
		_bar.add(actPageAdd);
		_bar.add(actPageEdit);
		_bar.add(actPageRemove);
		_bar.addSeparator();
		_bar.add(actParamsEdit);
	}

	public Action actPageAdd = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Page pg = new Page();
			dPage dlg = new dPage(pg, _trm);
			TreePath tp = _tree.getSelectionPath();
			Page currNode = null;
			if (tp != null)
			{
				if (tp.getParentPath() == null)
					dlg.setNNowner((Page)tp.getLastPathComponent());
				else
					dlg.setNNowner((Page)tp.getParentPath().getLastPathComponent());
				currNode = (Page)tp.getLastPathComponent();
			}
			dlg.setPreferencePath(_prefPath + "/dEdit");
			dlg.setVisible(true);
			if (dlg.isResultOk())
			{
				Page owner =  dlg.getNNOwner();
				_trm.insertNodeInto(
						pg, 
						owner, 
						currNode == null ? 
								owner.getPages().size() : 
								currNode.getParent() == null || currNode.equals(owner) ?
									0	:
									((Page)currNode.getParent()).getPages().indexOf(currNode)+1
				);
				_tree.setSelectionPath(new TreePath(_trm.getPathToRoot(pg)));
			}
		}
	};
	public Action actPageEdit = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};
	public Action actPageRemove = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};

	public Action actParamsEdit = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			TreePath tp = _tree.getSelectionPath();
			if (tp != null)
			{
				Page pg = (Page)tp.getLastPathComponent();
				if (pg != null)
				{
					dParams dlg = new dParams(pg.getParameters());
					dlg.setPreferencePath(_prefPath + "/BookParam/dParams");
					dlg.setVisible(true);
				}
			}
		}
	};
	
	public Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			//if (_currFN != null && _currFN.length() > 0)
			//	_bp.Save(_currFN);
			
			//recursive save value all parameters to the preferences:
				
		}
	};
	
	private void _saveVal(Page aPG)
	{
		
		for(Param prm : aPG.getParameters())
		{
			if (prm.getPrefsKey() != null && prm.getPrefsKey().length()>0)
			{
				Preferences node = Preferences.userRoot().node(aPG.getPrefsNode());
				if (prm.getTitle() == null)
				{
					//node.put(prm.getPrefsKey(), get);
				}
			}
		}
	}
	
	public Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};
	
	public Action actExport = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_setCurrentProjectFileName();
			if (_currFN != null && _currFN.length() > 0)
				_bp.Save(_currFN);
		}
	};
	
	public Action actImport = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_setCurrentProjectFileName();
			if (_currFN != null && _currFN.length() > 0)
				setBookParam(BookParam.Load(_currFN));
		}
	};
	
	private void _setCurrentProjectFileName()
	{
		JFileChooser fDlg = new JFileChooser();
		FileNameExtensionFilter fnf = new FileNameExtensionFilter(_bnd.getString("BookParam.Text.FileChooser"), BOOKPARAM_FILE_EXTENSION );
		fDlg.setFileFilter(fnf);
		fDlg.setFileFilter(fnf);
		if (_currFN != null && _currFN.length() > 0)
			fDlg.setCurrentDirectory(new File(_currFN));
		
		if (fDlg.showDialog(fBookParam.this, _bnd.getString("BookParam.Text.FileChooser")) == JFileChooser.APPROVE_OPTION)
		{
			_currFN = fDlg.getSelectedFile().getPath();
			if (!_currFN.toLowerCase().endsWith("."+BOOKPARAM_FILE_EXTENSION))
				_currFN += "." + BOOKPARAM_FILE_EXTENSION;
		}
		else
		{
			_currFN = null;
		}
	}
}


