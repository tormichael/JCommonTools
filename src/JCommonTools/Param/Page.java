package JCommonTools.Param;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

public class Page implements MutableTreeNode
{
	private String _title;
	private String _prefsNode;
	private int _inset;
	private ArrayList<Page> _pages;
	private ArrayList<Param> _params;

	@XmlTransient
	private Page _owner;
	
	public String getTitle() {
		return _title;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}

    public String getPrefsNode() {
		return _prefsNode;
	}
	public void setPrefsNode(String _prefsNode) {
		this._prefsNode = _prefsNode;
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

	public Page(String aTitle, String aPrefsNode, int aIns, Page aOwner)
	{
		_title = aTitle;
		_prefsNode = aPrefsNode;
		_inset = aIns;
		_owner = aOwner;
		_pages = new ArrayList<Page>();
		_params = new ArrayList<Param>();
	}
	
	public Page()
	{
		this(null, null, 0, null);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() 
	{
		return _title;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) 
	{
		return _pages.get(childIndex);
	}

	@Override
	public int getChildCount() 
	{
		return _pages.size();
	}

	@Override
	public TreeNode getParent() 
	{
		
		return _owner;
	}

	@Override
	public int getIndex(TreeNode node) 
	{
		return _pages.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeaf() 
	{
		return (_pages.size() == 0);
	}

	@Override
	public Enumeration children() 
	{
		if (_pages == null) 
		{
            return DefaultMutableTreeNode.EMPTY_ENUMERATION;
        } 
		else 
        {
            return elements();
        }
		
	}
	
    public Enumeration elements() 
    {
        return new Enumeration() {
            int count = 0;

            public boolean hasMoreElements() 
            {
                return count < _pages.size();
            }

            public Page nextElement() 
            {
                synchronized (Page.this) 
                {
                    if (count < _pages.size()) {
                        return _pages.get(count++);
                    }
                }
                throw new NoSuchElementException("Vector Enumeration");
            }
        };
    }

	///////////////////////////////////////////////////////////////////////////////////////////////////
    // MutableTreeNode Interface:
    
	@Override
	public void insert(MutableTreeNode child, int index) 
	{
		Page pg = (Page)child;
		pg._owner = this;
		_pages.add(index, pg);
	}

	@Override
	public void remove(int index) 
	{
		_pages.remove(index);
	}

	@Override
	public void remove(MutableTreeNode node) 
	{
		_pages.remove(node);
	}

	@Override
	public void setUserObject(Object object) 
	{
		Page pg = (Page) object;
		if (pg != null)
		{
			setTitle(pg.getTitle());
			setInset(pg.getInset());
			setPages(pg.getPages());
			setParameters(pg.getParameters());
		}
	}

	@Override
	public void removeFromParent() 
	{
		_owner.getPages().remove(this);
	}

	@Override
	public void setParent(MutableTreeNode newParent) 
	{
		_owner = (Page) newParent;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
}
