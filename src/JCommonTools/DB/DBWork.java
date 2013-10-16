package JCommonTools.DB;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import JCommonTools.CC;

public class DBWork 
{
	private Connection 		_cn;
	private DBConnectionParam _pdb;
	private boolean _isDriverInitialized; 

	/**
	 * Generate URL connection string from sources as:
	 * 		[Driver.PrefixConnectionURL]://[Host]:[Port][Driver.SuffixConnectionURL][DBName]
	 * For example:
	 * 		Driver.PrefixConnectionURL 	= jdbc:postgresql
	 * 		Host											= csql-st
	 * 		Port											= 5432
	 * 		Driver.SuffixConnectionURL 	= /
	 * 		DBName									= pdc
	 * then generate URL connection string:
	 * 		jdbc:postgresql://csql-st:5432/pdc
	 *
	 * @return string - URL connection
	 */
	public String getConnectionURL()
	{
		//if (_pdb.Host.length() > 0 &&  _pdb.Host.endsWith("/"))
		//{
		//	_pdb.Host = _pdb.Host.substring(0, _pdb.Host.length()-1);
		//}
		
		String ret = _pdb.Driver.PrefixConnectionURL + "://"  + _pdb.Host; 
		
		if (_pdb.Port > 0)
			ret += ":"+_pdb.Port;
		
		return  ret  + _pdb.Driver.SuffixConnectionURL + _pdb.DBName; 

	}
	
	public boolean IsDBConnectionParamDefined()
	{
		boolean ret = false;
		
		if (_pdb == null)
			return ret;
		
		if (_pdb.Driver == null)
			return ret;

		if (_pdb.Driver.Path.length() * _pdb.Driver.ClassName.length() * _pdb.Driver.PrefixConnectionURL.length() == 0)
			return ret;
		
		if (_pdb.DBName.length() * _pdb.Host.length() == 0)
			return ret;
		
		return true;
	}
	
	public Connection  getConn() 
			throws ClassNotFoundException, MalformedURLException, SQLException, IllegalAccessException, InstantiationException
	{
		if ((_cn == null || _cn.isClosed()) && IsDBConnectionParamDefined())
		{
			if (!_isDriverInitialized)
				initDBDriver();

			String url = getConnectionURL();
			
			if (_pdb.UserName != null && _pdb.UserName.length() > 0)
				_cn = DriverManager.getConnection(url, _pdb.UserName, _pdb.Pwd);
			else
				_cn = DriverManager.getConnection(url);
		}
		return _cn;
	}

	public DBConnectionParam getDBConnParam()
	{
		return _pdb;
	}
	public void setDBConnParam(DBConnectionParam aPDB)
	{
		_isDriverInitialized = (aPDB != null) && _isDriverInitialized && _pdb.Driver.equals(aPDB.Driver);
		_pdb = aPDB;
	}
	
	public void initDBDriver() 
			throws ClassNotFoundException, MalformedURLException, SQLException, IllegalAccessException, InstantiationException
	{
		URLClassLoader classLoader = new URLClassLoader(
				new URL[]{new  URL("File:///"+_pdb.Driver.Path)}, this.getClass().getClassLoader()
		);
		Driver driver = (Driver) Class.forName(_pdb.Driver.ClassName, true, classLoader).newInstance();
		
		DriverManager.registerDriver(new DelegatingDriver(driver)); // register using the Delegating Driver
		//DriverManager.getDriver("jdbc:postgresql://host/db"); // checks that the driver is found
		
		_isDriverInitialized = true;
	}

	public DBWork ()
	{
		_isDriverInitialized = false;
		_pdb =new DBConnectionParam(); 
	}
	
	public DBWork (DBConnectionParam aPDB)
	{
		_isDriverInitialized = false;
		setDBConnParam(aPDB);
	}
	

	public void CloseConnection()
		throws SQLException
	{
		if (_cn != null && !_cn.isClosed())
			_cn.close();
	}
	
	public void LoadDBConnectioParam2Reg(String aRegPath)
	{
		Preferences node = Preferences.userRoot().node(aRegPath+"/DBConnectionParam" );

		_pdb.Driver.Name = node.get("DrvName", CC.STR_EMPTY);
		_pdb.Driver.Path = node.get("DrvPath", CC.STR_EMPTY);
		_pdb.Driver.ClassName = node.get("DrvClassName", CC.STR_EMPTY);
		_pdb.Driver.PrefixConnectionURL = node.get("DrvPrefixConnectionURL", CC.STR_EMPTY);
		_pdb.Driver.SuffixConnectionURL = node.get("DrvSuffixConnectionURL", CC.STR_EMPTY);
		
    	_pdb.Host = node.get("Host", CC.STR_EMPTY);
    	_pdb.Port = node.getInt("Port", 0);
    	_pdb.DBName = node.get("DBName", CC.STR_EMPTY);
    	_pdb.UserName = node.get("UserName", CC.STR_EMPTY);
    	_pdb.Pwd = node.get("P@$w00d", CC.STR_EMPTY);
	}
	
	public void SaveDBConnectioParam2Reg(String aRegPath)
	{
		if (!IsDBConnectionParamDefined())
			return;
		
		Preferences node = Preferences.userRoot().node(aRegPath+"/DBConnectionParam" );

		node.put("DrvName", _pdb.Driver.Name);
		node.put("DrvPath", _pdb.Driver.Path);
		node.put("DrvClassName", _pdb.Driver.ClassName);
		node.put("DrvPrefixConnectionURL", _pdb.Driver.PrefixConnectionURL);
		node.put("DrvSuffixConnectionURL", _pdb.Driver.SuffixConnectionURL);
		
		node.put("Host", _pdb.Host);
		node.putInt("Port", _pdb.Port);
		node.put("DBName", _pdb.DBName);
		if (_pdb.UserName.length() > 0)
			node.put("UserName", _pdb.UserName);
		if (_pdb.Pwd.length() > 0)
			node.put("P@$w00d", _pdb.Pwd);
	}
	

}
