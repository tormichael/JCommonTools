package JCommonTools.Dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import JCommonTools.CC;
import JCommonTools.GBC;
import JCommonTools.JPanelWI;
import JCommonTools.DB.dDBDriver;

public class dPassword extends JDialog 
{
	private boolean _isPutNewPassword;
	private String _password; 
	
	private ResourceBundle _bnd;
	
	private JLabel _lblText;
	private JLabel _lblPassword;
	private JLabel _lblPasswordRepeat;
	private JTextField _txtPassword;
	private JTextField _txtPasswordRepeat;
	private JCheckBox _chkPasswordShow;
	
	private void _isShowPasswod(Boolean aIsShowPassword)
	{
		String savePassword = null;
		if (_txtPassword instanceof JTextField)
			savePassword = _txtPassword.getText();
		String savePasswordRepeat = null;
		if (_txtPasswordRepeat instanceof JTextField)
			savePasswordRepeat = _txtPasswordRepeat.getText();

		if (aIsShowPassword)
		{
			//(( JPasswordField)_txtPassword).
			_txtPassword  = new JTextField(16);
			if (_isPutNewPassword)
				_txtPasswordRepeat = new JTextField(16);
		}
		else
		{
			_txtPassword  = new JPasswordField(16);
			if (_isPutNewPassword)
				_txtPasswordRepeat = new JPasswordField(16);
		}

		if (savePassword != null)
			_txtPassword.setText(savePassword);
		if (savePasswordRepeat != null)
			_txtPasswordRepeat.setText(savePasswordRepeat);
	}
	
	public String getPassword()
	{
		return _password;
	}
	
	public dPassword(String aTitle, Boolean aIsPutNewPassword)
	{
		_isPutNewPassword = aIsPutNewPassword;
		_password = null;
		
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);

		this.setModal(true);
		this.setLayout(new BorderLayout());
		Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(szScreen.width/3, szScreen.height/3);
		setLocation((int)(szScreen.width/2 - this.getWidth()/2), (int)(szScreen.height/2-this.getHeight()/2));
		//this.setLocationByPlatform(true);
		if (aTitle != null && aTitle.length() > 0)
			this.setTitle(aTitle);
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel _pnl = new JPanel(gbl);
			// row - 0
			_lblText = new JLabel();
			gbl.setConstraints(_lblText, new GBC(0,0).setIns(15).setGridSpan(3, 1).setFill(GBC.HORIZONTAL));
			_pnl.add(_lblText);
			// row - 3
			_chkPasswordShow = new JCheckBox();
			gbl.setConstraints(_chkPasswordShow, new GBC(0,3).setIns(15).setGridSpan(3, 1).setFill(GBC.HORIZONTAL));
			_pnl.add(_chkPasswordShow);
			_isShowPasswod(false);
			// row - 1
			JLabel lbl = new JLabel();
			gbl.setConstraints(lbl, new GBC(0,1).setIns(15));
			_pnl.add(lbl);
			_lblPassword = new JLabel();
			gbl.setConstraints(_lblPassword, new GBC(1,1).setIns(15));
			_pnl.add(_lblPassword);
			gbl.setConstraints(_txtPassword, new GBC(2,1).setIns(15));
			_pnl.add(_txtPassword);
			if (_txtPasswordRepeat != null)
			{
				// row - 2
				lbl = new JLabel();
				gbl.setConstraints(lbl, new GBC(0,2).setIns(15));
				_pnl.add(lbl);
				_lblPasswordRepeat = new JLabel();
				gbl.setConstraints(_lblPasswordRepeat, new GBC(1,2).setIns(15));
				_pnl.add(_lblPasswordRepeat);
				gbl.setConstraints(_txtPasswordRepeat, new GBC(2,2).setIns(15));
				_pnl.add(_txtPasswordRepeat);
			}
		this.add(_pnl, BorderLayout.CENTER);
			
		JPanel pnlButton = new JPanel();
		JButton _btnOk = new JButton(actOk);
		_btnOk.setText(_bnd.getString("Button.Ok"));
		pnlButton.add(_btnOk);
		JButton _btnCancel = new JButton(actCancel);
		_btnCancel.setText(_bnd.getString("Button.Cancel"));
		pnlButton.add(_btnCancel);
		this.add(pnlButton, BorderLayout.SOUTH);
		
		_lblText.setText(_bnd.getString("dPassword.Label.Text"));
		_lblPassword.setText(_bnd.getString("dPassword.Label.Password"));
		if (_lblPasswordRepeat != null)
			_lblPasswordRepeat.setText(_bnd.getString("dPassword.Label.PasswordRepeat"));
		_chkPasswordShow.setText(_bnd.getString("dPassword.CheckBox.ShowPassword"));
		
		_chkPasswordShow.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				_isShowPasswod(_chkPasswordShow.isSelected());
			}
		});
		//_chkPasswordShow.setSelected(false);
	}

	Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_isPutNewPassword &&  !_txtPassword.getText().equals(_txtPasswordRepeat.getText()))
			{
				_lblText.setText(_bnd.getString("dPassword.Message.TwoPasswordNotEqual"));
			}
			else
			{
				if (_chkPasswordShow.isSelected())
				{
					_password = _txtPassword.getText();
				}
				else
				{
					_password = new String((( JPasswordField)_txtPassword).getPassword());
				}
				dPassword.this.setVisible(false);
			}
				
		}
	};
	
	Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			 dPassword.this.setVisible(false);
			 _password = null;
		}
	};
}
