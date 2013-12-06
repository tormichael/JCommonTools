package JCommonTools.RefBook;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;



public class trmRefBook implements TreeModel 
{
	RefBook _rb;
	
	public trmRefBook(RefBook aRB)
	{
		_rb = aRB;
	}

	@Override
	public Object getRoot() 
	{
		return _rb.getRefBookNodes();
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
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) 
	{
		return ((rbNode)parent).getNodes().indexOf(child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

}
