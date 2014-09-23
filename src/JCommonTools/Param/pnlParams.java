package JCommonTools.Param;

import java.util.ArrayList;

import javax.swing.JPanel;

public class pnlParams extends JPanel 
{
	protected ArrayList<Param> mParams;
	protected String mError;
	
	public pnlParams(ArrayList<Param> aParams)
	{
		mParams = aParams;
		mError = null;
	}
}
