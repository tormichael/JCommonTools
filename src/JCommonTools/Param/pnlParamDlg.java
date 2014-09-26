package JCommonTools.Param;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Action;

import JCommonTools.CC;

public class pnlParamDlg extends JPanel 
{
	private JTextField _txf;
	private JButton _btn;
	
	public void setText(String aText)
	{
		_txf.setText(aText);
	}
	public String getText()
	{
		return _txf.getText();
	}
	
	public void setTextEditable(boolean aFlg)
	{
		_txf.setEditable(aFlg);
	}
	
	public void setTextFont(Font aFont)
	{
		_txf.setFont(aFont);
	}
	
	public void setButtonText(String aText)
	{
		_btn.setText(aText);
	}
	
	public void setAction(Action aAct)
	{
		_btn.setAction(aAct);
	}

	public pnlParamDlg()
	{
		this (CC.STR_EMPTY, "...", null);
	}
	public pnlParamDlg(String aText, String aButtonText, Action aAct)
	{
		_txf = new JTextField(aText);
		_btn = new JButton(aButtonText);
		if (aAct != null)
			_btn.setAction(aAct);
		
		this.setLayout(new BorderLayout());
		this.add(_txf, BorderLayout.CENTER);
		this.add(_btn, BorderLayout.EAST);
	}
}
