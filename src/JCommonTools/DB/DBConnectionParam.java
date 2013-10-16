package JCommonTools.DB;

import JCommonTools.CC;

public class DBConnectionParam
{
	
	public DBDriver Driver;
	
    public String Host;
    public int Port;
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
    	Driver = new  DBDriver();
    	Host = CC.STR_EMPTY;
    	Port = 0;
    	DBName = CC.STR_EMPTY;
    	UserName = null;
    	Pwd = null;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException 
    {
    	DBConnectionParam pdb = (DBConnectionParam) super.clone();

    	pdb.Driver = new DBDriver();
    		pdb.Driver.Name = Driver.Name;
    		pdb.Driver.Path = Driver.Path;
    		pdb.Driver.ClassName = Driver.ClassName;
    		pdb.Driver.PrefixConnectionURL = Driver.PrefixConnectionURL;
    	
    	pdb.Host = new String(Host);
    	pdb.Port = Port;
    	pdb.DBName = new String(DBName);
    	pdb.UserName = new String(UserName);
    	pdb.Pwd = new String(Pwd);
    	
    	return pdb;
    }
}
