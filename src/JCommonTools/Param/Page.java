package JCommonTools.Param;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Page 
{
	private String _title;
	private int _inset;
	private ArrayList<Page> _pages;
	private ArrayList<Param> _params;
	
	public String getTitle() {
		return _title;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}

    public int getInset() {
		return _inset;
	}
	public void setInset(int _inset) {
		this._inset = _inset;
	}
	
	@XmlElementWrapper (name = "Pages")
    @XmlElement (name = "Page")
	public ArrayList<Page> getPages() {
		return _pages;
	}
	public void setPages(ArrayList<Page> _pages) {
		this._pages = _pages;
	}

    @XmlElementWrapper (name = "Parameters")
    @XmlElement (name = "Param")
	public ArrayList<Param> getParameters() {
		return _params;
	}
	public void setParameters(ArrayList<Param> _params) {
		this._params = _params;
	}

	public Page()
	{
		_title = null;
		_inset = 0;
		_pages = new ArrayList<Page>();
		_params = new ArrayList<Param>();
	}


}
