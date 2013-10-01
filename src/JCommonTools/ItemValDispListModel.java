package JCommonTools;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

class ItemValDispListModel extends AbstractListModel
{
	private ArrayList<ItemValDisp> _items;
	
	public ItemValDispListModel (ArrayList<ItemValDisp> aItems)
	{
		_items = aItems;
	}
	
	@Override
	public int getSize() 
	{
		return _items.size();
	}
	
	@Override
	public Object getElementAt(int index) 
	{
		return _items.get(index);
	}
}
