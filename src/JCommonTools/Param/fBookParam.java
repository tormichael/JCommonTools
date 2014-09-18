package JCommonTools.Param;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import JCommonTools.AsRegister;
import JCommonTools.CC;

public class fBookParam extends JFrame 
{
	private BookParam _bp;
	private ResourceBundle _bnd;
	
	private JTree _tree;
	private pnlParams _params;
	private JSplitPane _pnlBP;

	private String _prefPath;
	
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
		
		_tree = new JTree();
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
	}
	
	private void SaveProgramPreference()
	{
		if (_prefPath == null)
			return;
		
		Preferences node = Preferences.userRoot().node(_prefPath);
		AsRegister.SaveFrameStateSizeLocation(node, this);
	}
	
	public Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};
	
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
		}
	};
	
	public Action actImport = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};
	
	
}


