package JCommonTools.DB;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.GBC;

public class dDBDriver extends JDialog 
{
	private DBDriver _drv; 
	
	private ResourceBundle _bnd;
	private JTextField _txtTitle;
	private JTextField _txtPath;
	private JTextField _txtClassName;
	private JTextField _txtPrefix;
	private JTextField _txtSuffix;

	private boolean _isResultOk;
	
	private AsRegister _reg;
	
	public boolean isResultOk()
	{
		return _isResultOk;
	}
	
	public void setPreferencesPath(String aRefPath)
	{
		if (aRefPath != null && aRefPath.length() > 0)
		{
			_reg = new AsRegister();
			_reg.set_node(Preferences.userRoot().node(aRefPath+"/DBDriver" ));
			_reg.LoadWindowSize(this);
			_reg.LoadWindowLocation(this);
			
			addWindowListener(new WindowAdapter() 
			{
				@Override
				public void windowClosing(WindowEvent e) 
				{
					_savePreferences();
					super.windowClosing(e);
				}
			});
		}
	}

	public dDBDriver(DBDriver aDrv)
	{
		_drv = aDrv;
		_isResultOk = false;
		
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		this.setTitle(_bnd.getString("dDBDriver.Text"));
		this.setModal(true);
		this.setSize(300, 100);
		
		GridBagLayout gblDrv = new GridBagLayout();
		JPanel pnlDrv =new JPanel(gblDrv);

			JLabel lblTitle = new JLabel(_bnd.getString("dDBDriver.Label.Title"));
			gblDrv.setConstraints(lblTitle, new GBC(0,0).setIns(2).setAnchor(GBC.EAST));
			pnlDrv.add(lblTitle);
			_txtTitle = new JTextField();
			gblDrv.setConstraints(_txtTitle, new GBC(1,0).setIns(2).setGridSpan(2, 1) .setFill(GBC.HORIZONTAL));
			pnlDrv.add(_txtTitle);
		
			JLabel lblPath = new JLabel(_bnd.getString("dDBDriver.Label.Path"));
			gblDrv.setConstraints(lblPath, new GBC(0,1).setIns(2).setAnchor(GBC.EAST));
			pnlDrv.add(lblPath);
			_txtPath = new JTextField();
			gblDrv.setConstraints(_txtPath, new GBC(1,1).setFill(GBC.HORIZONTAL).setWeight(1,0));
			pnlDrv.add(_txtPath);
			JButton cmdBrowser = new JButton("...");
			cmdBrowser.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fDlg = new JFileChooser();
					fDlg.setFileFilter(new FileNameExtensionFilter("Java library", "jar"));
					if (_txtPath.getText().length() > 0)
						fDlg.setCurrentDirectory(new File(_txtPath.getText()));
					if (fDlg.showDialog(dDBDriver.this, _bnd.getString("dDBDriver.Text.FileChooser.Driver")) == JFileChooser.APPROVE_OPTION)
						_txtPath.setText(fDlg.getSelectedFile().getPath());
				}
			});
			gblDrv.setConstraints(cmdBrowser, new GBC(2,1));
			pnlDrv.add(cmdBrowser);

			JLabel lblClassName = new JLabel(_bnd.getString("dDBDriver.Label.ClassName"));
			gblDrv.setConstraints(lblClassName, new GBC(0,2).setIns(2).setAnchor(GBC.EAST));
			pnlDrv.add(lblClassName);
			_txtClassName = new JTextField();
			gblDrv.setConstraints(_txtClassName, new GBC(1,2).setIns(2).setGridSpan(2, 1) .setFill(GBC.HORIZONTAL));
			pnlDrv.add(_txtClassName);
			
			JLabel lblPrefix = new JLabel(_bnd.getString("dDBDriver.Label.Prefix"));
			gblDrv.setConstraints(lblPrefix, new GBC(0,3).setIns(2).setAnchor(GBC.EAST));
			pnlDrv.add(lblPrefix);
			_txtPrefix = new JTextField();
			gblDrv.setConstraints(_txtPrefix, new GBC(1,3).setIns(2).setGridSpan(2, 1) .setFill(GBC.HORIZONTAL));
			pnlDrv.add(_txtPrefix);
			
			JLabel lbSuffix = new JLabel(_bnd.getString("dDBDriver.Label.Suffix"));
			gblDrv.setConstraints(lbSuffix, new GBC(0,4).setIns(2).setAnchor(GBC.EAST));
			pnlDrv.add(lbSuffix);
			_txtSuffix = new JTextField();
			gblDrv.setConstraints(_txtSuffix, new GBC(1,4).setIns(2).setGridSpan(2, 1) .setFill(GBC.HORIZONTAL));
			pnlDrv.add(_txtSuffix);
			
		this.add(pnlDrv, BorderLayout.NORTH);
		
		this.add(new JPanel(), BorderLayout.CENTER);
		
		JPanel pnlButton = new JPanel();
		JButton _btnOk = new JButton(actOk);
		_btnOk.setText(_bnd.getString("Button.Ok"));
		pnlButton.add(_btnOk);
		JButton _btnCancel = new JButton(actCancel);
		_btnCancel.setText(_bnd.getString("Button.Cancel"));
		pnlButton.add(_btnCancel);
		this.add(pnlButton, BorderLayout.SOUTH);

		_txtTitle.setText(_drv.Name);
		_txtPath.setText(_drv.Path);
		_txtClassName.setText(_drv.ClassName);
		_txtPrefix.setText(_drv.PrefixConnectionURL);
		_txtSuffix.setText(_drv.SuffixConnectionURL);
	}
	
	Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_drv.Name = _txtTitle.getText();
			_drv.Path = _txtPath.getText();
			_drv.ClassName = _txtClassName.getText();
			_drv.PrefixConnectionURL = _txtPrefix.getText();
			_drv.SuffixConnectionURL = _txtSuffix.getText();
			_isResultOk = true;
			_savePreferences();
			 dDBDriver.this.setVisible(false);
		}
	};

	Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			 dDBDriver.this.setVisible(false);
		}
	};
	
	private void _savePreferences()
	{
		dDBDriver.this._reg.SaveWindowSize(dDBDriver.this);
		dDBDriver.this._reg.SaveWindowLocation(dDBDriver.this);
	}
}
