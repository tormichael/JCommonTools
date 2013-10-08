package JCommonTools.DB;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWork 
{
	private Connection 		_cn;
	private DBConnectionParam _pdb;

	private void _checkHost()
	{
		if (_pdb.Host.length() > 0 &&  _pdb.Host.endsWith("/"))
		{
			_pdb.Host = _pdb.Host.substring(0, _pdb.Host.length()-1);
		}
	}
	
	public Connection  getConn() 
			throws SQLException  
	{
		if ((_cn == null || _cn.isClosed()) && _pdb != null)
		{
			if (_pdb.UserName != null && _pdb.UserName.length() > 0)
				_cn = DriverManager.getConnection(_pdb.Host + "/"+ _pdb.DBName, _pdb.UserName, _pdb.Pwd);
			else
				_cn = DriverManager.getConnection(_pdb.Host + "/"+ _pdb.DBName);
		}
		return _cn;
	}

	public DBConnectionParam getDBConnParam()
	{
		return _pdb;
	}
	
	public void setDBConnParam(DBConnectionParam aPDB) 
			throws ClassNotFoundException, MalformedURLException, SQLException, IllegalAccessException, InstantiationException
	{
		_pdb = aPDB;
		_checkHost();
		
		URLClassLoader classLoader = new URLClassLoader(
				new URL[]{new  URL("File:///"+aPDB.Driver)}, this.getClass().getClassLoader()
		);
		Driver driver = (Driver) Class.forName("org.postgresql.Driver", true, classLoader).newInstance();
		DriverManager.registerDriver(new DelegatingDriver(driver)); // register using the Delegating Driver

			//DriverManager.getDriver("jdbc:postgresql://host/db"); // checks that the driver is found

	}
	
	public DBWork (DBConnectionParam aPDB)
			throws ClassNotFoundException, MalformedURLException, SQLException, IllegalAccessException, InstantiationException
	{
		setDBConnParam(aPDB);
	}
	
	//public void CloseConnection()
	//{
	//}
	

}
