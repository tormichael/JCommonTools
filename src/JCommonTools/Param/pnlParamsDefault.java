package JCommonTools.Param;

import java.awt.GridBagLayout;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JCommonTools.GBC;

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
					Class<?> c = Class.forName(prm.getSwingCtrlName());
					Constructor<?> cons = c.getConstructor(String.class);
					comp = (JComponent) cons.newInstance("");
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
