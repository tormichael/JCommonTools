
package JCommonTools.RefBook;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import JCommonTools.CC;

/**
 * Hierarchical structure reference book. 
 * For person, auto, place etc. 
 * 
 * @author M.Tor
 *	29.10.2013
 */
public class rbNode  implements MutableTreeNode
{
	
	private int _id;
	private String _name;
	private String _alias;
	
	@XmlTransient
	private rbNode _owner;

	@XmlElementWrapper (name = "Nodes")
    @XmlElement (name = "Node")
	private ArrayList<rbNode> _nodes;

	
	//==============================================================
	
    public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}
	
	public String getAlias() {
		return _alias;
	}

	public void setAlias(String _alias) {
		this._alias = _alias;
	}

	public ArrayList<rbNode> getNodes() 
	{
		return _nodes;
	}

	//public rbNode getOwner() 
	//{
	//	return _owner;
	//}
	//public void setOwner(rbNode aOwner)
	//{
	//	_owner=aOwner;
	//}
	
	public rbNode(int aId, rbNode aOwner, String aName, String aAliase)
	{
		_id = aId;
		_owner = aOwner;
		_name = aName;
		_alias = aAliase;
		_nodes = new ArrayList<rbNode>();
	}

	public rbNode(int aId, rbNode aOwner, String aName)
	{
		this (aId, aOwner, aName, aName);
	}
	
	public rbNode()
	{
		this (0, null, CC.STR_EMPTY, CC.STR_EMPTY);
	}

	@Override
	public String toString() 
	{
		return _name;
	}
	
	public boolean IsNew()
	{
		return _id == 0;
	}

	
	@Override
	public TreeNode getChildAt(int childIndex) 
	{
		return _nodes.get(childIndex);
	}

	@Override
	public int getChildCount() 
	{
		return _nodes.size();
	}

	@Override
	public TreeNode getParent() 
	{
		
		return _owner;
	}

	@Override
	public int getIndex(TreeNode node) 
	{
		return _nodes.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeaf() 
	{
		return (_nodes.size() == 0);
	}

	@Override
	public Enumeration children() 
	{
		if (_nodes == null) 
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
                return count < _nodes.size();
            }

            public rbNode nextElement() 
            {
                synchronized (rbNode.this) 
                {
                    if (count < _nodes.size()) {
                        return _nodes.get(count++);
                    }
                }
                throw new NoSuchElementException("Vector Enumeration");
            }
        };
    }

    // MutableTreeNode Interface:
    
	@Override
	public void insert(MutableTreeNode child, int index) 
	{
		rbNode node = (rbNode)child;
		node._owner = this;
		_nodes.add(index, node);
	}

	@Override
	public void remove(int index) 
	{
		_nodes.remove(index);
	}

	@Override
	public void remove(MutableTreeNode node) 
	{
		_nodes.remove(node);
	}

	@Override
	public void setUserObject(Object object) 
	{
		rbNode node = (rbNode) object;
		if (node != null)
		{
			setId(node.getId());
			setName(node.getName());
			setAlias(node.getAlias());
		}
	}

	@Override
	public void removeFromParent() 
	{
		_owner.getNodes().remove(this);
	}

	@Override
	public void setParent(MutableTreeNode newParent) 
	{
		_owner = (rbNode) newParent;
	}

	public rbNode findByAlias(String aSearchText)
	{
		return rbNode.findByAlias(this, aSearchText);
	}

	public static rbNode findByAlias(rbNode aRBN, String aSearchText)
	{
		rbNode ret = null;
		for (rbNode rbn : aRBN.getNodes())
		{
			if (rbn.getAlias().equals(aSearchText))
				return rbn;

			if (rbn.getNodes().size() > 0)
			{
				ret = findByAlias(rbn, aSearchText);
				if (ret != null)
					break;
			}
		}
		return ret;	
	}
}
