package JCommonTools.Param;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import JCommonTools.CC;
import JCommonTools.GBC;
import JCommonTools.Dialog.JFontChooser;

public class pnlParamsDefault extends pnlParams 
{
	
	public pnlParamsDefault(Page aPG)
	{
		super (aPG);
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		if (aPG != null && aPG.getParameters() != null && mPrefs != null)
		{
			int iRow=0;
			for (Param prm : aPG.getParameters())
			{
				JComponent comp = null;
				try
				{
					if (prm.getSwingCtrlName().matches(".*JComboBox.*"))
					{
						if (prm.getSwingCtrlOptional().equalsIgnoreCase("String"))
						{
							JComboBox<String> cbo = new JComboBox<String>();
							String[] cont = prm.getSwingCtrlRestriction().split("\\|", -1);
							for (int ii=0; ii < cont.length; ii++)
								cbo.addItem(cont[ii]);
							cbo.setSelectedItem(mPrefs.get(prm.getPrefsKey(), CC.STR_EMPTY));
							comp = cbo;
						}
					}
					else if (prm.getSwingCtrlName().matches(".*JFileChooser.*"))
					{
						final pnlParamDlg pnl = new pnlParamDlg();
						pnl.setTextEditable(false);
						pnl.setAction(new AbstractAction() 
						{
							@Override
							public void actionPerformed(ActionEvent e) 
							{
								   JFileChooser dlg = new JFileChooser();
									//dlg.addChoosableFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
								   if (dlg.showOpenDialog(pnlParamsDefault.this) == JFileChooser.APPROVE_OPTION)
								   {
								   		pnl.setText(dlg.getSelectedFile().getPath()); 
								   }
							}
						});
						pnl.setText(mPrefs.get(prm.getPrefsKey(), CC.STR_EMPTY));
						comp = pnl;
					}
					else if (prm.getSwingCtrlName().matches(".*JFontChooser.*"))
					{
						final pnlParamDlg pnl = new pnlParamDlg();
						pnl.setTextEditable(false);
						pnl.setAction(new AbstractAction() 
						{
							@Override
							public void actionPerformed(ActionEvent e) 
							{
								   JFontChooser fontChooser = new JFontChooser();
									if (pnl.getText().length() > 0)
									{
										Font fnt = Font.decode(pnl.getText());
										fontChooser.setSelectedFont(fnt);
									}
								   int result = fontChooser.showDialog(pnlParamsDefault.this);
								   if (result == JFontChooser.OK_OPTION)
								   {
								   		Font font = fontChooser.getSelectedFont();
								   		pnl.setTextFont(font);
								   		String  strStyle;
								        if (font.isBold()) {
								            strStyle = font.isItalic() ? "bolditalic" : "bold";
								        } else {
								            strStyle = font.isItalic() ? "italic" : "plain";
								        }
								        pnl.setText(font.getFontName()+"-"+strStyle+"-"+font.getSize()); 
								   }
							}
						});
						pnl.setText(mPrefs.get(prm.getPrefsKey(), CC.STR_EMPTY));
						if (pnl.getText().length() > 0)
						{
							Font fnt = Font.decode(pnl.getText());
							pnl.setTextFont(fnt);
						}
						comp = pnl;
					}
					else
					{
							Class<?> c = Class.forName(prm.getSwingCtrlName());
							Constructor<?> cons = c.getConstructor(String.class);
							comp = (JComponent) cons.newInstance("");
					}
				}
				catch (Exception ex)
				{
					mError = ex.getMessage();
					comp = null;
				}

				if (comp == null)
				{
					comp = new JTextField();
				}

				((JComponent) comp).putClientProperty("prefs-key", prm.getPrefsKey());
				
				if (comp instanceof JTextField)
				{
					((JTextField) comp).setText(mPrefs.get(prm.getPrefsKey(), CC.STR_EMPTY));
				}

				JLabel lbl = new JLabel(prm.getTitle());
				gbl.setConstraints(lbl, new GBC(0,iRow).setAnchor(GBC.WEST).setIns(mPage.getInset()));
				this.add(lbl);
				gbl.setConstraints(comp, new GBC(1,iRow++).setFill(GBC.HORIZONTAL).setIns(mPage.getInset()).setWeight(1.0, 0.0));
				this.add(comp);
			}
			JLabel lbl = new JLabel();
			gbl.setConstraints(lbl, new GBC(0,iRow).setGridSpan(2, 1).setWeight(1.0, 1.0));
			this.add(lbl);
		}
	}

	@Override
	public void Save()
	{
		for (Component comp : this.getComponents() )
		{
			if (comp instanceof JTextField)
			{
				JTextField txf = (JTextField) comp;
				mPrefs.put(txf.getClientProperty("prefs-key").toString(), txf.getText());
			}
			else if (comp instanceof pnlParamDlg)
			{
				pnlParamDlg pnl = (pnlParamDlg) comp;
				mPrefs.put(pnl.getClientProperty("prefs-key").toString(), pnl.getText());
			}
			else if (comp instanceof JComboBox<?>)
			{
				JComboBox<String> cbo = (JComboBox<String>) comp;
				mPrefs.put(cbo.getClientProperty("prefs-key").toString(), cbo.getSelectedItem().toString());
			}
		}
	}
	
}
