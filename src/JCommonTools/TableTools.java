package JCommonTools;

import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableTools 
{
	public static String GetColumnsWidthAsString(JTable aTab)
	{
		String ret = CC.STR_EMPTY;
		if (aTab != null)
		{
			TableColumnModel tcm = aTab.getColumnModel();
			for (Enumeration<TableColumn> etc = tcm.getColumns(); etc.hasMoreElements();)
				ret += etc.nextElement().getWidth() + ";";
		}
		return ret.length()>1 ? ret.substring(0, ret.length()-1) : ret;
	}
	
	public static void SetColumnsWidthFromString(JTable aTab, String aCWS)
	{
		if (aCWS != null && aCWS.length()>0 && aTab != null)
		{
			String [] ss = aCWS.split(";"); 
			for (int ii = 0; ii < ss.length && ii < aTab.getColumnModel().getColumnCount(); ii++)
				aTab.getColumnModel().getColumn(ii).setPreferredWidth(Integer.parseInt(ss[ii]));
		}
	}
}
