package JCommonTools.Param;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.JPanel;

import JCommonTools.CC;

public class pnlParams extends JPanel 
{
	protected Page mPage;
	protected String mError;
	protected Preferences mPrefs;
	
	public pnlParams(Page aPG)
	{
		mPage = aPG;
		
		String prPath = BookParam.getPathPrefs(mPage, "/");
		if (prPath.length() > 0)
			mPrefs = Preferences.userRoot().node(prPath);
		else
			mPrefs = null;
		
		mError = null;
	}
	
//	public void Load(Preferences aPrefs)
//	{
//		
//	}
	
	public void Save()
	{
		
	}
}
