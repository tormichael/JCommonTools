package JCommonTools.Param;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import JCommonTools.CC;
import JCommonTools.RefBook.rbNode;

@XmlRootElement (name = "BookParam")
public class BookParam 
{
	private Page _page;

	public Page getBookPage() {
		return _page;
	}

	public void setBookPage(Page _page) {
		this._page = _page;
	}

	public BookParam()
	{
		_page = new Page("root", null, 0, null);
	}
	
 	public static BookParam Load(String aFileName)
	{
		BookParam ret = null;

		if (aFileName != null && aFileName.length() > 0)
		{
	    	try
	    	{
	    		JAXBContext context = JAXBContext.newInstance(BookParam.class);
	    		Unmarshaller um = context.createUnmarshaller();
	    		Object obj = um.unmarshal(new File(aFileName));
	    		ret = (BookParam) obj;
	    		
	    		setOwner(ret.getBookPage(), null);
	    	}
	    	catch (JAXBException ex)
	    	{
	    		ex.printStackTrace();
	    	}
		}
    	
    	return ret;
		
	}
	
	public String Save (String aFileName)
	{
		String ret = null;
		
		if (aFileName != null && aFileName.length() > 0)
		{
	    	try
	    	{
	    		setOwnerNull(this._page);
	    		
	    		JAXBContext context = JAXBContext.newInstance(BookParam.class);
	    		Marshaller m = context.createMarshaller();
	    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    		m.marshal(this, new File(aFileName));
	    	}
	    	catch (JAXBException ex)
	    	{
	    		ret = ex.getMessage();
	    	}
		}
    	
    	return ret;
	}

	public static void setOwner(Page aNodes, Page aOwner)
	{
		aNodes.setParent(aOwner);
		
		for (Page rbn : aNodes.getPages())
			setOwner(rbn, aNodes);
		
	}
	
	public static void setOwnerNull(Page aNodes)
	{
		aNodes.setParent(null);
		
		for (Page rbn : aNodes.getPages())
			setOwnerNull(rbn);
		
	}
	
	public static String getPathPrefs(Page aNode, String aDelim)
	{
		String ret = CC.STR_EMPTY;
		
		if (aNode != null)
		{
			
			ret = getPathPrefs((Page)aNode.getParent(), aDelim);
			if (ret.length() > 0)
				ret = ret + aDelim + aNode.getPrefsNode();
			else
				ret = aNode.getPrefsNode()+CC.STR_EMPTY;
			
		}
		
		return ret;
	}

}
