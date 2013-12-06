
package JCommonTools.RefBook;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import JCommonTools.CC;

/**
 * Hierarchical structure reference book. 
 * For person, auto, place etc. 
 * 
 * @author M.Tor
 *	29.10.2013
 */
public class rbNode 
{
	
	private int _id;
	private String _name;
	private String _alias;

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
	
	public rbNode(int aId, String aName, String aAliase)
	{
		_id = aId;
		_name = aName;
		_alias = aAliase;
		_nodes = new ArrayList<rbNode>();
	}
	
	public rbNode()
	{
		this (0, CC.STR_EMPTY, CC.STR_EMPTY);
	}

	@Override
	public String toString() 
	{
		return _name;
	}
}
