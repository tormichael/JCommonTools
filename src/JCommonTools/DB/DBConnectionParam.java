package JCommonTools.DB;

import JCommonTools.CC;

public class DBConnectionParam
{
	/**
	 * JDBC 
	 * for registration: Class.forName("org.postgresql.Driver")
	 */
	public String Driver;
    /**
     * String for connect to DB server.
     */
    public String Host;
    /**
     * DB name.
     */
    public String DBName;
    /**
     * User name.
     */
    public String UserName;
    /**
     * User password.
     */
    public String Pwd;

    public DBConnectionParam()
    {
    	Driver = CC.STR_EMPTY;
    	Host = CC.STR_EMPTY;
    	DBName = CC.STR_EMPTY;
    	UserName = null;
    	Pwd = null;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException 
    {
    	DBConnectionParam pdb = (DBConnectionParam) super.clone();

    	pdb.Driver = new String(Driver);
    	pdb.Host = new String(Host);
    	pdb.DBName = new String(DBName);
    	pdb.UserName = new String(UserName);
    	pdb.Pwd = new String(Pwd);
    	
    	return pdb;
    }
}
