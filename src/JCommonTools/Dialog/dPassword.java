package JCommonTools.Dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import JCommonTools.CC;
import JCommonTools.GBC;
import JCommonTools.JPanelWI;
import JCommonTools.DB.dDBDriver;

public class dPassword extends JDialog 
{
	private boolean _isPutNewPassword;
	private String _password; 
	private String _fName;
	
	private ResourceBundle _bnd;
	
	private GridBagLayout _gbl;
	private JPanel _pnl;
	private JTextArea _txtText;
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

		if (_txtPassword != null)
			_pnl.remove(_txtPassword);
		if (_isPutNewPassword && _txtPasswordRepeat != null)
		{
			_pnl.remove(_txtPasswordRepeat);
		}
		
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
		_txtPassword.setMinimumSize(new Dimension(_txtPassword.getPreferredSize().width, _txtPassword.getPreferredSize().height));
		if (_isPutNewPassword)
			_txtPasswordRepeat.setMinimumSize(new Dimension(_txtPasswordRepeat.getPreferredSize().width, _txtPasswordRepeat.getPreferredSize().height));
		
		_gbl.setConstraints(_txtPassword, new GBC(2,1).setIns(15).setWeight(0.7, 0));
		_pnl.add(_txtPassword);
		if (_isPutNewPassword)
		{
			_gbl.setConstraints(_txtPasswordRepeat, new GBC(2,2).setIns(15));
			_pnl.add(_txtPasswordRepeat);			
		}
	
		if (savePassword != null)
			_txtPassword.setText(savePassword);
		if (savePasswordRepeat != null)
			_txtPasswordRepeat.setText(savePasswordRepeat);
		
		validate();
		repaint();
	}
	
	public String getPassword()
	{
		return _password;
	}
	
	public void setFileName(String aFN)
	{
		_fName = aFN;
		if (_fName != null && _fName.length() > 0)
			_txtText.setText(_fName +System.lineSeparator() +System.lineSeparator()+ _bnd.getString("dPassword.Label.Text" ));
	}
	
	public dPassword(String aTitle, Boolean aIsPutNewPassword)
	{
		_isPutNewPassword = aIsPutNewPassword;
		_password = null;
		_fName = null;
		
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);

		this.setModal(true);
		this.setLayout(new BorderLayout());
		Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(szScreen.width/3, szScreen.height/3);
		setLocation((int)(szScreen.width/2 - this.getWidth()/2), (int)(szScreen.height/2-this.getHeight()/2));
		//this.setLocationByPlatform(true);
		if (aTitle != null && aTitle.length() > 0)
			this.setTitle(aTitle);
		this.setResizable(false);
		
		_gbl = new GridBagLayout();
		_pnl = new JPanel(_gbl);
			// row - 0
			_txtText = new JTextArea();
			_txtText.setEditable(false);
			_gbl.setConstraints(_txtText, new GBC(0,0).setIns(15).setGridSpan(3, 1).setFill(GBC.HORIZONTAL));
			_pnl.add(_txtText);
			// row - 3
			_chkPasswordShow = new JCheckBox();
			_gbl.setConstraints(_chkPasswordShow, new GBC(0,3).setIns(15).setGridSpan(3, 1).setFill(GBC.HORIZONTAL));
			_pnl.add(_chkPasswordShow);
			_isShowPasswod(false);
			// row - 1
			JLabel lbl = new JLabel();
			_gbl.setConstraints(lbl, new GBC(0,1).setIns(15));
			_pnl.add(lbl);
			_lblPassword = new JLabel();
			_gbl.setConstraints(_lblPassword, new GBC(1,1).setIns(15));
			_pnl.add(_lblPassword);
			if (_txtPasswordRepeat != null)
			{
				// row - 2
				lbl = new JLabel();
				_gbl.setConstraints(lbl, new GBC(0,2).setIns(15));
				_pnl.add(lbl);
				_lblPasswordRepeat = new JLabel();
				_gbl.setConstraints(_lblPasswordRepeat, new GBC(1,2).setIns(15));
				_pnl.add(_lblPasswordRepeat);
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
		
		_txtText.setText(_bnd.getString("dPassword.Label.Text"));
		
		_lblPassword.setText(_bnd.getString("dPassword.Label.Password"));
		if (_lblPasswordRepeat != null)
			_lblPasswordRepeat.setText(_bnd.getString("dPassword.Label.PasswordRepeat"));
		_chkPasswordShow.setText(_bnd.getString("dPassword.CheckBox.ShowPassword"));
		
		this.getRootPane().setDefaultButton(_btnOk);
		
		_chkPasswordShow.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				_isShowPasswod(_chkPasswordShow.isSelected());
			}
		});
		
		this.addWindowListener( new WindowAdapter() 
		{
		    public void windowOpened( WindowEvent e )
		    {
				_txtPassword.requestFocusInWindow();
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
				_txtText.setText(_bnd.getString("dPassword.Message.TwoPasswordNotEqual"));
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
