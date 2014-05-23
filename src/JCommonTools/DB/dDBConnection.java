package JCommonTools.DB;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.*;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.GBC;

public class dDBConnection extends JDialog 
{
	private DBWork _wdb;
	private DBConnectionParam _srcDBParam;
	
	private ResourceBundle _bnd;
	private JComboBox<DBDriver> _cboDriver;
	private JButton _btnNewDriver;
	private JButton _btnEditDriver;
	private JTextField _txtHost;
	private JTextField _txtPort;
	private JTextField _txtDBName;
	private JCheckBox _chkIsIntegratedAuth;
	private JTextField _txtUserName;
	//private JPasswordField _txtUserPassword;
	private JTextField _txtUserPassword;
	private JTextArea _txtInformation;
	private JButton _btnOk;
	private JButton _btnTest;
	private JButton _btnCancel;

	private boolean _isResultOk;
	
	private AsRegister _reg;
	private String _regPath;
	
	public void setPreferencesPath(String aRefPath)
	{
		if (aRefPath != null && aRefPath.length() > 0)
		{
			_reg = new AsRegister();
			_regPath = aRefPath+"/DBConnection" ;
			_reg.set_node(Preferences.userRoot().node(_regPath));
			_reg.LoadWindowSize(this);
			_reg.LoadWindowLocation(this);
			
			addWindowListener(new WindowAdapter() 
			{
				@Override
				public void windowClosing(WindowEvent e) {
					_reg.SaveWindowSize(dDBConnection.this);
					_reg.SaveWindowLocation(dDBConnection.this);
					super.windowClosing(e);
				}
			});
			
		}
	}
	
	public boolean isResultOk()
	{
		return _isResultOk;
	}
	
	public dDBConnection(DBWork aWDB)
	{
		_wdb = aWDB;
		_reg = null;
		_regPath = null;
		_isResultOk = false;
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);
		
		this.setTitle(_bnd.getString("dDBConnection.Text"));
		
		GridBagLayout gblDB = new GridBagLayout();
		JPanel pnlDB =new JPanel(gblDB);
		
			GridBagLayout gblDriver = new GridBagLayout();
			JPanel pnlDriver =new JPanel(gblDriver);
			pnlDriver.setBorder(BorderFactory.createBevelBorder(1));
			gblDB.setConstraints(pnlDriver, new GBC(0,0).setIns(2).setGridSpan(2, 1).setFill(GBC.BOTH).setWeight(1, 0));
			
				JLabel lblDriver = new JLabel(_bnd.getString("dDBConnection.Label.Driver"));
				gblDriver.setConstraints(lblDriver, new GBC(0,0).setIns(2).setAnchor(GBC.EAST));
				pnlDriver.add(lblDriver);
				_cboDriver = new JComboBox<DBDriver>();
				gblDriver.setConstraints(_cboDriver, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1, 0));
				pnlDriver.add(_cboDriver);
				_btnNewDriver = new JButton(actNewDriver);
				_btnNewDriver.setText(_bnd.getString("dDBConnection.Button.DriverNew"));
				gblDriver.setConstraints(_btnNewDriver, new GBC(2,0).setIns(2).setAnchor(GBC.CENTER));
				pnlDriver.add(_btnNewDriver);
				_btnEditDriver = new JButton(actEditDriver);
				_btnEditDriver.setText(_bnd.getString("dDBConnection.Button.DriverEdit"));
				gblDriver.setConstraints(_btnEditDriver, new GBC(3,0).setIns(2).setAnchor(GBC.CENTER));
				pnlDriver.add(_btnEditDriver);
				
			pnlDB.add(pnlDriver);
		
			JLabel lblHost = new JLabel(_bnd.getString("dDBConnection.Label.Host"));
			gblDB.setConstraints(lblHost, new GBC(0,1).setIns(2).setAnchor(GBC.EAST));
			pnlDB.add(lblHost);
			_txtHost = new JTextField();
			gblDB.setConstraints(_txtHost, new GBC(1,1).setIns(2).setFill(GBC.HORIZONTAL));
			pnlDB.add(_txtHost);
			
			JLabel lblPort = new JLabel(_bnd.getString("dDBConnection.Label.Port"));
			gblDB.setConstraints(lblPort, new GBC(0,2).setIns(2).setAnchor(GBC.EAST));
			pnlDB.add(lblPort);
			_txtPort = new JTextField();
			gblDB.setConstraints(_txtPort, new GBC(1,2).setIns(2).setFill(GBC.HORIZONTAL));
			pnlDB.add(_txtPort);
			
			JLabel lblDBName = new JLabel(_bnd.getString("dDBConnection.Label.DBName"));
			gblDB.setConstraints(lblDBName, new GBC(0,3).setIns(2).setAnchor(GBC.EAST));
			pnlDB.add(lblDBName);
			_txtDBName = new JTextField();
			gblDB.setConstraints(_txtDBName, new GBC(1,3).setIns(2).setFill(GBC.HORIZONTAL));
			pnlDB.add(_txtDBName);
			
			GridBagLayout gblUser = new GridBagLayout();
			JPanel pnlUser = new JPanel(gblUser);
			pnlUser.setBorder(BorderFactory.createTitledBorder(_bnd.getString("dDBConnection.BorderText.Identification")));
			gblDB.setConstraints(pnlUser, new GBC(0,4).setIns(2).setGridSpan(2, 1).setFill(GBC.BOTH).setWeight(1, 0));
			
				_chkIsIntegratedAuth = new JCheckBox(_bnd.getString("dDBConnection.CheckBox.IsIntegated"));
				gblUser.setConstraints(_chkIsIntegratedAuth, new GBC(0,0).setGridSpan(2, 1).setAnchor(GBC.WEST));
				pnlUser.add(_chkIsIntegratedAuth);
			
				JLabel lblUserName = new JLabel(_bnd.getString("dDBConnection.Label.User"));
				gblUser.setConstraints(lblUserName, new GBC(0,1).setIns(2).setAnchor(GBC.EAST));
				pnlUser.add(lblUserName);
				_txtUserName = new JTextField();
				gblUser.setConstraints(_txtUserName, new GBC(1,1).setIns(2).setFill(GBC.HORIZONTAL));
				pnlUser.add(_txtUserName);

				JLabel lblUserPassword = new JLabel(_bnd.getString("dDBConnection.Label.Password"));
				gblUser.setConstraints(lblUserPassword, new GBC(0,2).setIns(2).setAnchor(GBC.EAST));
				pnlUser.add(lblUserPassword);
				_txtUserPassword = new JPasswordField();
				gblUser.setConstraints(_txtUserPassword, new GBC(1,2).setIns(2).setFill(GBC.HORIZONTAL));
				pnlUser.add(_txtUserPassword);
				
			pnlDB.add(pnlUser);
			
		this.add(pnlDB, BorderLayout.NORTH);
						
		JPanel pnlConnectionURL = new JPanel(new BorderLayout());
		pnlConnectionURL.setBorder(BorderFactory.createTitledBorder(_bnd.getString("dDBConnection.BorderText.Info")));
		_txtInformation = new JTextArea();
		pnlConnectionURL.add(new JScrollPane(_txtInformation), BorderLayout.CENTER);
		this.add(pnlConnectionURL, BorderLayout.CENTER);
		
		JPanel pnlButton = new JPanel();
		_btnOk = new JButton(actOk);
		_btnOk.setText(_bnd.getString("Button.Ok"));
		pnlButton.add(_btnOk);
		_btnTest = new JButton(actTestConnection);
		_btnTest.setText(_bnd.getString("dDBConnection.Button.TestConnection"));
		pnlButton.add(_btnTest);
		_btnCancel = new JButton(actCancel);
		_btnCancel.setText(_bnd.getString("Button.Cancel"));
		pnlButton.add(_btnCancel);
		this.add(pnlButton, BorderLayout.SOUTH);
		
		if (_wdb != null)
		{
			_cboDriver.addItem(_wdb.getDBConnParam().Driver);

			_txtHost.setText(_wdb.getDBConnParam().Host);
			_txtPort.setText( _wdb.getDBConnParam().Port+CC.STR_EMPTY );
			_txtDBName.setText( _wdb.getDBConnParam().DBName);

			_txtUserName.setText( _wdb.getDBConnParam().UserName);
			_txtUserPassword.setText( _wdb.getDBConnParam().Pwd);
			
			try{ _srcDBParam = (DBConnectionParam) _wdb.getDBConnParam().clone(); }
			catch (Exception ex) {}
		}
		
	}

	Action actNewDriver = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			DBDriver drv = new DBDriver();
			dDBDriver dlg = new dDBDriver(drv);
			if (_regPath != null)
				dlg.setPreferencesPath(_regPath);
			dlg.setVisible(true);
			if (dlg.isResultOk())
			{
				_cboDriver.addItem(drv);
				_cboDriver.setSelectedItem(drv);
			}
		}
	};

	Action actEditDriver = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int si = _cboDriver.getSelectedIndex(); 
			if (si >= 0)
			{
				dDBDriver dlg = new dDBDriver(_cboDriver.getItemAt(si));
				if (_regPath != null)
					dlg.setPreferencesPath(_regPath);
				dlg.setVisible(true);
				if (dlg.isResultOk())
				{
					_cboDriver.setSelectedIndex(si);
				}
			}
		}
	};

	Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_setValue2DBWork())
			{
				_isResultOk = true;
				dDBConnection.this.setVisible(false);
			}
		}
	};

	Action actTestConnection = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_setValue2DBWork())
			{
				Connection cn = null;
				try
				{
					cn = _wdb.getConn();
					_txtInformation.setText(
							_bnd.getString("dDBConnection.Message.OkConnection")
							+ CC.NEW_LINE
							+ _bnd.getString("dDBConnection.Message.ConnectionURL")
							+ CC.NEW_LINE
							+ _wdb.getConnectionURL()
					);
				}
				catch (Exception ex)
				{
					_txtInformation.setText(ex.toString());
				}
				finally
				{
					try 
					{
						if (cn != null && !cn.isClosed())
							cn.close();
					}
					catch (Exception ex)
					{
						_txtInformation.setText(_txtInformation.getText() + CC.NEW_LINE + ex.getMessage());
					}
				}
			}
		}
	};

	Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_wdb.setDBConnParam(_srcDBParam);
			_isResultOk = false;
			dDBConnection.this.setVisible(false);
		}
	};

	private boolean _setValue2DBWork()
	{
		int si = _cboDriver.getSelectedIndex(); 
		DBConnectionParam pdb = _wdb.getDBConnParam();
		boolean ret = false;
		if (si >= 0)
		{
			pdb.Driver = _cboDriver.getItemAt(si);

			pdb.Host = _txtHost.getText();
			if (_txtPort.getText().length()>0)
				pdb.Port = Integer.parseInt(_txtPort.getText());
			pdb.DBName = _txtDBName.getText();

			pdb.UserName = _txtUserName.getText();
			//pdb.Pwd = _txtUserPassword.getPassword();
			pdb.Pwd = _txtUserPassword.getText();

			_wdb.setDBConnParam(pdb);

			ret = _wdb.IsDBConnectionParamDefined();
		}
		
		if (!ret)
			_txtInformation.setText(_bnd.getString("dDBConnection.Error.UndefinedDriver"));
		
		return ret;
	}
}
