package JCommonTools.RefBook;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name = "RefBook")
public class RefBook 
{
	protected rbNode	mRBNodes;
	
	public rbNode getRefBookNodes() 
	{
		return mRBNodes;
	}
	public void setRefBookNodes(rbNode aRBNodes) 
	{
		this.mRBNodes = aRBNodes;
	}
	
	public RefBook()
	{
		_init();
	}


	/**
	 * Default refbook item. 
	 * 
	 * !!! ID from 1 to 999 reserved for system needs !!!
	 * 
	 * @param aCetus
	 */
	private void _init()
	{
		mRBNodes = new rbNode(1, "RefBook", "rb");
	}
	
	public static RefBook Load(String aFN)
	{
		RefBook ret = null;
		
		if (aFN != null && aFN.length() > 0)
		{
	    	try
	    	{
	    		JAXBContext context = JAXBContext.newInstance(RefBook.class);
	    		Unmarshaller um = context.createUnmarshaller();
	    		Object obj = um.unmarshal(new File(aFN));
	    		ret = (RefBook) obj;
	    	}
	    	catch (JAXBException ex)
	    	{
	    		//ex.printStackTrace();
	    	}
		}
		return ret;
	}

	public String Save(String aFN)
	{
		String ret = null;
		
		if (aFN != null && aFN.length() > 0)
		{
	    	try
	    	{
	    		JAXBContext context = JAXBContext.newInstance(RefBook.class);
	    		Marshaller m = context.createMarshaller();
	    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    		m.marshal(this, new File(aFN));
	    	}
	    	catch (JAXBException ex)
	    	{
	    		ret = ex.getMessage();
	    	}
		}
		return ret;
	}
}
