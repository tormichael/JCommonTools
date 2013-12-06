package JCommonTools.RefBook;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import JCommonTools.AsRegister;
import JCommonTools.CC;


public class fRefBook extends JFrame 
{
	private ResourceBundle _bnd;
	private RefBook _rb;

	private JTree _tree;
	private TreeModel _trm;
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

	
	public RefBook getRefBook() 
	{
		return _rb;
	}
	public void setRefBook(RefBook _rb) 
	{
		this._rb = _rb;
	}
	
	public fRefBook(RefBook aRB)
	{
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		_rb = aRB;
		_prefPath = null;

		/**
		 *   T O O L S   B A R
		 */
		JToolBar bar = new JToolBar();
		add(bar, BorderLayout.NORTH);
		bar.add(ActLoad);
		bar.add(ActSave);
		bar.addSeparator();
		bar.add(ActNew);
		bar.add(ActEdit);
		bar.add(ActDelete);

		
		_trm = new trmRefBook(_rb);
		_tree = new JTree(_trm);
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
			
		}
	};
	public Action ActSave = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_currFN == null || _currFN.length() == 0)
				_setCurrentProjectFileName();
			
			if (_currFN != null && _currFN.length() > 0)
			{
				_rb.Save(_currFN);
			}
		}
	};
	public Action ActNew = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dRefBookNode dlg = new dRefBookNode(new rbNode(), _trm);
			dlg.setPreferencePath(_prefPath + "/dEdit");
			dlg.setVisible(true);
			
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
				dRefBookNode dlg = new dRefBookNode((rbNode)tp.getLastPathComponent(), _trm);
				dlg.setPreferencePath(_prefPath + "/dEdit");
				dlg.setVisible(true);
			}
		}
	};
	public Action ActDelete = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
		}
	};

	private void _setCurrentProjectFileName()
	{
		JFileChooser fDlg = new JFileChooser();
		FileNameExtensionFilter fnf = new FileNameExtensionFilter(_bnd.getString("RefBook.Text.FileChooser"), "rfb");
		fDlg.setFileFilter(fnf);
		if (_currFN != null && _currFN.length() > 0)
			fDlg.setCurrentDirectory(new File(_currFN));
		else if (_prevFN != null && _prevFN.length() > 0)
			fDlg.setCurrentDirectory(new File(_prevFN));
		
		if (fDlg.showDialog(this, _bnd.getString("RefBook.Text.FileChooser")) == JFileChooser.APPROVE_OPTION)
		{
			_currFN = fDlg.getSelectedFile().getPath();
			//String[] ss = fDlg.getSelectedFile().
			//fnf.getExtensions();
		}
		else
		{
			_currFN = null;
		}
	}
	
	
}
