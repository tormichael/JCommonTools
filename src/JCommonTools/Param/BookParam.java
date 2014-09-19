package JCommonTools.Param;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

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
	
}
