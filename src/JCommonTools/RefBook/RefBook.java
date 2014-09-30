package JCommonTools.RefBook;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import JCommonTools.CC;


@XmlRootElement (name = "RefBook")
public class RefBook 
{
	protected rbNode	mRBNodes;
	
	public rbNode getRefBookNode() 
	{
		return mRBNodes;
	}
	public void setRefBookNode(rbNode aRBNodes) 
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
		mRBNodes = new rbNode(1, null, "RefBook", "rb");
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
	    		
	    		setOwner(ret.getRefBookNode(), null);
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
	    		setOwnerNull(this.mRBNodes);
	    		
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
	
	public static void setOwner(rbNode aNodes, rbNode aOwner)
	{
		aNodes.setParent(aOwner);
		
		for (rbNode rbn : aNodes.getNodes())
			setOwner(rbn, aNodes);
		
	}
	
	public static void setOwnerNull(rbNode aNodes)
	{
		aNodes.setParent(null);
		
		for (rbNode rbn : aNodes.getNodes())
			setOwnerNull(rbn);
		
	}

	public static String getPath(rbNode aNode)
	{
		return getPath(aNode, "~");
	}
	public static String getPath(rbNode aNode, String aDelim)
	{
		String ret = CC.STR_EMPTY;
		
		if (aNode != null)
		{
			
			ret = getPath((rbNode)aNode.getParent(), aDelim);
			if (ret.length() > 0)
				ret = ret + aDelim + aNode.getId();
			else
				ret = aNode.getId()+CC.STR_EMPTY;
			
		}
		
		return ret;
	}
}
