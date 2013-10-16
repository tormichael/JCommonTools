package JCommonTools.DB;

import JCommonTools.CC;

public class DBDriver 
{
	public String Name;
	/**
	 * path to JDBC driver 
	 */
	public String Path;
	/**
	 * for examples: "org.postgresql.Driver" or "com.microsoft.sqlserver.jdbc.SQLServerDriver"
	 */
	public String ClassName;
    /**
     * Connection string
     * for example: "jdbc:postgresql" or "jdbc:sqlserver"
     */
	public String PrefixConnectionURL;

    /**
     * Connection string
     * for example: "/" or ";databaseName="
     */
	public String SuffixConnectionURL;
	
	public DBDriver()
	{
		Name = "undefined";
		Path = CC.STR_EMPTY;
		ClassName = CC.STR_EMPTY;
		PrefixConnectionURL = CC.STR_EMPTY;
		SuffixConnectionURL = CC.STR_EMPTY;
	}
	
	@Override
	public String toString() 
	{
		if (Name != null)
			return Name;
		else
			return super.toString();
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		//return super.equals(obj);
		boolean ret = false;
		DBDriver drv = (DBDriver) obj;
		ret = Path.equals(drv.Path) 
				&& ClassName.equals(drv.ClassName) 
				&& PrefixConnectionURL.equals(drv.PrefixConnectionURL)
				&& SuffixConnectionURL.equals(drv.SuffixConnectionURL);
		return ret;
	}
}
