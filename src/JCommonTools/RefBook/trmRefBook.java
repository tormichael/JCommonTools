package JCommonTools.RefBook;

import java.util.EventListener;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import JCommonTools.TreeModelSupport;

//
// NOW NOT USE !!!!! 13.12.2013 !!!!
//
public class trmRefBook extends TreeModelSupport  
{
	private RefBook _rb;
	
	
	public trmRefBook(RefBook aRB)
	{
		setRoot(aRB);
	}

	public void setRoot(RefBook aRB)
	{
		rbNode oldRoot = aRB.getRefBookNode();
		_rb = aRB;
		//fireTreeStructureChanged(oldRoot); 
	}
	
	@Override
	public Object getRoot() 
	{
		return _rb.getRefBookNode();
	}

	@Override
	public Object getChild(Object parent, int index) 
	{
		return ((rbNode)parent).getNodes().get(index);
	}

	@Override
	public int getChildCount(Object parent) 
	{
		return ((rbNode)parent).getNodes().size();
	}

	@Override
	public boolean isLeaf(Object node) 
	{
		return ((rbNode)node).getNodes().size() == 0;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) 
	{
		return ((rbNode)parent).getNodes().indexOf(child);
	}

	/*
	@Override
	public void addTreeModelListener(TreeModelListener l) 
	{
		_listenerList.add(TreeModelListener.class, l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) 
	{
		_listenerList.remove(TreeModelListener.class, l);
	}
	
	protected void fireTreeStructureChanged(Object oldRoot)
	{
		TreeModelEvent event = new TreeModelEvent(this, new Object[] {oldRoot});
		EventListener[] listener = _listenerList.getListeners(TreeModelListener.class);
		for (int ii=0; ii < listener.length; ii++)
			((TreeModelListener)listener[ii]).treeNodesChanged(event);
	}
	*/

}
