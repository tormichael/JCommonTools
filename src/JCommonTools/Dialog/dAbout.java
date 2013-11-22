package JCommonTools.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import JCommonTools.*;

/**
 * About dialog
 * 
 * @author M.Tor	/08.11.2013/
 *
 */
public class dAbout extends JDialog 
{
	private ResourceBundle _bnd;

	private JPanelWI		_pnl;
	
	private JLabel		_txtTitle;
	private JLabel		_txtVersion;
	private JTextArea	_txtBuild;
	private JLabel		_txtCopyright;
	private JLabel		_txtAuthor;
	
	public void setBackrounImage(Image _img) 
	{
		_pnl.set_img(_img);
		this.repaint();
	}
	public void setTextColor(Color c)
	{
		_txtTitle.setForeground(c);
		_txtVersion.setForeground(c);
		_txtBuild.setForeground(c);
		_txtCopyright.setForeground(c);
		_txtAuthor.setForeground(c);
	}
	
	public void set_txtTitle(String _txtTitle) 
	{
		this._txtTitle.setText(_txtTitle);
	}
	public void set_txtVersion(String _txtVersion) 
	{
		this._txtVersion.setText(_txtVersion);
	}
	public void set_txtBuild(String _txtBuild) 
	{
		this._txtBuild.setText(_txtBuild);
	}

	public dAbout()
	{
		_bnd = ResourceBundle.getBundle(CC.CT_RESOURCE_TEXT);

		this.setTitle(_bnd.getString("dAbout.Text"));
		this.setModal(true);
		Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(szScreen.width/3, szScreen.height/3);
		setLocation((int)(szScreen.width/2 - this.getWidth()/2), (int)(szScreen.height/2-this.getHeight()/2));
		//this.setLocationByPlatform(true);
		
		GridBagLayout gbl = new GridBagLayout();
		_pnl = new JPanelWI(gbl);
			_txtTitle = new JLabel();
			gbl.setConstraints(_txtTitle, new GBC(0,0).setIns(15).setFill(GBC.HORIZONTAL));
			_pnl.add(_txtTitle);
			JPanel pp = new JPanel();
			pp.setOpaque(false);
			gbl.setConstraints(pp, new GBC(1,0).setFill(GBC.BOTH));
			_pnl.add(pp);
			_txtVersion = new JLabel();
			gbl.setConstraints(_txtVersion, new GBC(2,0).setIns(15).setFill(GBC.HORIZONTAL));
			_pnl.add(_txtVersion);
			//
			pp = new JPanel();
			pp.setOpaque(false);
			gbl.setConstraints(pp, new GBC(0,1).setIns(2).setGridSpan(3, 1).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			_pnl.add(pp);
			//
			_txtBuild = new JTextArea();
			gbl.setConstraints(_txtBuild, new GBC(0,2).setIns(2).setGridSpan(1, 2).setFill(GBC.BOTH));
			_pnl.add(_txtBuild);
			pp = new JPanel();
			pp.setOpaque(false);
			gbl.setConstraints(pp, new GBC(1,2).setIns(2).setGridSpan(1, 2).setFill(GBC.BOTH).setWeight(1.0, 0.0));
			_pnl.add(pp);
			_txtCopyright = new JLabel();
			gbl.setConstraints(_txtCopyright, new GBC(2,2).setIns(10).setFill(GBC.BOTH));
			_pnl.add(_txtCopyright);
			_txtAuthor = new JLabel();
			gbl.setConstraints(_txtAuthor, new GBC(2,3).setIns(10).setAnchor(GBC.CENTER));
			_pnl.add(_txtAuthor);
		this.add(_pnl, BorderLayout.CENTER);
		
		JPanel pnlButton = new JPanel();
		JButton _btnOk = new JButton(actOk);
		_btnOk.setText(_bnd.getString("Button.Ok"));
		pnlButton.add(_btnOk);
		this.add(pnlButton, BorderLayout.SOUTH);
		
		
		// set default:
		_txtVersion.setText(_bnd.getString("dAbout.DefaultText.Version"));
		_txtBuild.setOpaque(false);
		_txtBuild.setEditable(false);
		_txtCopyright.setText(String.format(_bnd.getString("dAbout.DefaultText.Copyright"), "2013"));
		_txtAuthor.setText(_bnd.getString("dAbout.DefaultText.Author"));
		
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowActivated(WindowEvent e) 
			{
				dAbout.this.getContentPane().validate();
				dAbout.this.getContentPane().repaint();
				super.windowActivated(e);
			}
		});
		
	}
	
	Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dAbout.this.setVisible(false);
		}
	};
	
}
