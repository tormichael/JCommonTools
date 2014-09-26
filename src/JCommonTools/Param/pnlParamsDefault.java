package JCommonTools.Param;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

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
	
	public pnlParamsDefault(ArrayList<Param> aParams)
	{
		super (aParams);
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		if (aParams != null)
		{
			int iRow=0;
			int _distance = 5;
			for (Param prm : aParams)
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
								   int result = fontChooser.showDialog(pnlParamsDefault.this);
								   if (result == JFontChooser.OK_OPTION)
								   {
								   		Font font = fontChooser.getSelectedFont();
								   		pnl.setTextFont(font);
								   		pnl.setText(CC.STR_EMPTY + font); 
								   }
							}
						});
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

				JLabel lbl = new JLabel(prm.getTitle());
				gbl.setConstraints(lbl, new GBC(0,iRow).setAnchor(GBC.WEST).setIns(_distance));
				this.add(lbl);
				gbl.setConstraints(comp, new GBC(1,iRow++).setFill(GBC.HORIZONTAL).setIns(_distance).setWeight(1.0, 0.0));
				this.add(comp);
			}
			JLabel lbl = new JLabel();
			gbl.setConstraints(lbl, new GBC(0,iRow).setGridSpan(2, 1).setWeight(1.0, 1.0));
			this.add(lbl);
		}
	}

}
