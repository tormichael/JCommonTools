package JCommonTools;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

public class AsRegister 
{
	Preferences _node;
	
	public Preferences get_node() 
	{
		return _node;
	}
	public void set_node(Preferences _node) 
	{
		this._node = _node;
	}
	
	public AsRegister()
	{
		
	}
	
	public void LoadFrameStateSizeLocation(JFrame aFrm)
	{
		if (_node != null)
			LoadFrameStateSizeLocation(_node, aFrm);
	}
	public void LoadWindowSize(Window aWnd)
	{
		if (_node != null)
			LoadWindowSize(_node, aWnd);
	}
	public void LoadWindowLocation(Window aWnd)
	{
		if (_node != null)
			LoadWindowLocation(_node, aWnd);
	}

	public void SaveFameStateSizeLocation(JFrame aFrm)
	{
		if (_node != null)
			SaveFrameStateSizeLocation(_node, aFrm);
	}
	public void SaveWindowSize(Window aWnd)
	{
		if (_node != null)
			SaveWindowSize(_node, aWnd);
	}
	public void SaveWindowLocation(Window aWnd)
	{
		if (_node != null)
			SaveWindowLocation(_node, aWnd);
	}
	
	public static void LoadFrameStateSizeLocation(Preferences aNode, JFrame aFrm)
	{
		int frameState = aNode.getInt("FormState", Frame.NORMAL);
		aFrm.setState(frameState);
		if  (frameState != Frame.MAXIMIZED_BOTH)
		{
			LoadWindowSize(aNode, aFrm);
			LoadWindowLocation(aNode, aFrm);
		}
	}

	public static void LoadWindowSize(Preferences aNode, Window aWnd)
	{
		LoadWindowSize(aNode, aWnd, "FormSize");
	}
	public static void LoadWindowSize(Preferences aNode, Window aWnd, String aKey)
	{
		String st = aNode.get(aKey, CC.STR_EMPTY);
		if (st.length() > 0)
		{
			String [] ss = st.split(";");
			aWnd.setSize(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
		}
		else
		{
			Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
			aWnd.setSize(szScreen.width/2, szScreen.height/2);
		}
	}

	public static void LoadWindowLocation(Preferences aNode, Window aWnd)
	{
		LoadWindowLocation(aNode, aWnd, "FormLocation");
	}
	public static void LoadWindowLocation(Preferences aNode, Window aWnd, String aKey)
	{
		String st = aNode.get(aKey, CC.STR_EMPTY);
		if (st.length() > 0)
		{
			String [] ss = st.split(";");
			aWnd.setLocation(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
		}
		else
		{
			Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
			aWnd.setLocation(szScreen.width/4, szScreen.height/4);
		}
	}
	
	public static void SaveFrameStateSizeLocation(Preferences aNode, JFrame aFrm)
	{
		int frameState = aFrm.getState();
		aNode.putInt("FormState", frameState == Frame.ICONIFIED ? Frame.NORMAL : frameState);
		if (frameState == Frame.NORMAL)
		{
			SaveWindowSize(aNode, aFrm);
			SaveWindowLocation(aNode, aFrm);
		}
	}

	public static void SaveWindowSize(Preferences aNode, Window aWnd)
	{
		SaveWindowSize(aNode, aWnd, "FormSize");
	}
	public static void SaveWindowSize(Preferences aNode, Window aWnd, String aKey)
	{
		Dimension sz = 	aWnd.getSize();
		aNode.put(aKey, sz.width + ";" + sz.height);
	}
	
	public static void SaveWindowLocation(Preferences aNode, Window aWnd)
	{
		SaveWindowLocation(aNode, aWnd, "FormLocation");
	}
	public static void SaveWindowLocation(Preferences aNode, Window aWnd, String aKey)
	{
		Point pt = aWnd.getLocation();
		aNode.put(aKey, pt.x + ";" + pt.y);
	}
	
}
