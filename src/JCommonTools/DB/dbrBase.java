package JCommonTools.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbrBase 
{
	protected DBWork mWDB;
	protected int mCode;
	protected String mTabName;
	protected String mFldPrefix;
	
	public dbrBase(DBWork aDbWork, String aTableName, String aFieldPrefix)
	{
		mWDB = aDbWork;
		mTabName = aTableName;
		mFldPrefix = aFieldPrefix;
		
		Init();
	}
	
	public void Init()
	{
		mCode = 0;
	}
	
	public boolean Load (int aCode)
	{
		boolean ret = true;
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			Statement stm = mWDB.getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			StringBuilder strSelect = new StringBuilder("SELECT * FROM ");
			strSelect.append(mTabName);
			strSelect.append(" WHERE ");
			strSelect.append(mFldPrefix);
			strSelect.append("Id = ");
			strSelect.append(mCode);
			rs = stm.executeQuery(strSelect.toString());
			if (rs.next())
			{
				_loadFlds(rs);
			}
			mCode = aCode;
		}
		catch (Exception ex)
		{
			//errorNewLine(ex.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (sqlCmd != null)
					sqlCmd.close();
			}
			catch (Exception ex){}
		}	
		return ret;
	}
	
	protected void _loadFlds(ResultSet aRS) throws SQLException
	{
		
	}

	public  boolean Save ()
	{
		boolean ret = true;
		
		return ret;
	}
}
