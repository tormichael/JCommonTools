package JCommonTools;

import java.lang.reflect.TypeVariable;

//import com.sun.org.apache.bcel.internal.generic.Type;

/**
 * Item set, list, tree or other structure consist of two elements:
 * first generic type (int, long, String) field [Value] 
 * and second String type field [Display].
 * 
 * � ItemValDisp<T> ���� �� ���������� �� ���� ��� ���������� ��� �� ��������� <T> !!!! 
 * 
 * @author M.Tor
 * @since 20.05.2010
 */
//public class ItemValDisp<T> 
public class ItemValDisp 
{
	public final static String Delim = ":";
	
	protected Object Value;
	protected String Display;
	
	public Object getValue() 
	{
		return Value;
	}
	public void setValue(Object value) 
	{
		Value = value;
	}
	
	public String getDisplay() 
	{
		return Display;
	}
	public void setDisplay(String display) 
	{
		Display = display;
	}
	
	public String getValueDisplay()
	{
		return Value.toString() + Delim + Display;
	}
	public void setValueDisplay(String aValDisp, String aTypeName)
	{
		String ss[] = aValDisp.split(Delim);
		if (ss.length == 2)
		{
			if (Integer.class.getName().equals(aTypeName)) 
				Value = Convert.ToIntegerOrZero(ss[0]);
			else if (String.class.getName().equals(aTypeName))
				Value = ss[0];
			else
				Value = null;
			
			Display = ss[1];
		}
	}
	
	public ItemValDisp()
	{
		this (null , CC.STR_EMPTY);
	}
	public ItemValDisp(Object aVal, String aDisp)
	{
		Value = aVal;
		Display = aDisp;
	}
	
	@Override
	public String toString() 
	{
		return Display;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
//		ItemValDisp<T> ti = (ItemValDisp<T>) obj;
		ItemValDisp ti = (ItemValDisp) obj;
		if (ti != null)
			return ti.Value == Value && ti.Display == Display;
		else
			return false;
	}
	
}
