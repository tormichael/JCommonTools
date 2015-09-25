package JCommonTools.DB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "JDBCDrivers")
public class DBDriverList 
{
    @XmlElementWrapper (name = "DriverList")
    @XmlElement (name = "DriverList")
	public ArrayList<DBDriver> arrayDBD;
	
	public DBDriverList()
	{
		arrayDBD = new ArrayList<DBDriver>();
	}
}
