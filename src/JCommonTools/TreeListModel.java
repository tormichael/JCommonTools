package JCommonTools;

import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.tree.TreeModel;

public class TreeListModel extends AbstractListModel implements ComboBoxModel
{ 
    private TreeModel treeModel; 
    private Object selectedObject; 
 
    public TreeListModel(TreeModel treeModel)
    { 
        this.treeModel = treeModel; 
    } 
 
    public int getSize()
    { 
        int count = 0; 
        Enumeration enumer = new PreorderEnumeration(treeModel); 

        while(enumer.hasMoreElements())
        { 
            enumer.nextElement(); 
            count++; 
        } 
        return count; 
    } 
 
    public Object getElementAt(int index)
    { 
        Enumeration enumer = new PreorderEnumeration(treeModel);
        
        for(int i=0; i<index; i++) 
            enumer.nextElement(); 
        
        return enumer.nextElement(); 
    }  
 
    public void setSelectedItem(Object anObject)
    { 
        if((selectedObject!=null && !selectedObject.equals(anObject)) || selectedObject==null && anObject!=null)
        { 
            selectedObject = anObject; 
            fireContentsChanged(this, -1, -1); 
        } 
    } 
 
    public Object getSelectedItem() 
    { 
        return selectedObject; 
    } 
}  

class ChildrenEnumeration implements Enumeration
{ 
    TreeModel treeModel; 
    Object node; 
    int index = -1; 
    int depth;
 
    public ChildrenEnumeration(TreeModel treeModel, Object node)
    { 
        this.treeModel = treeModel; 
        this.node = node; 
    } 
 
    public boolean hasMoreElements()
    { 
        return index<treeModel.getChildCount(node)-1; 
    } 
 
    public Object nextElement()
    { 
        return treeModel.getChild(node, ++index); 
    } 
} 

class PreorderEnumeration implements Enumeration
{ 
    private TreeModel treeModel; 
    protected Stack stack;
    private int depth = 0; 

    public int getDepth()
    { 
        return depth; 
    } 
    
    public PreorderEnumeration(TreeModel treeModel)
    { 
        this.treeModel = treeModel; 
        Vector v = new Vector(1); 
        v.addElement(treeModel.getRoot()); 
        stack = new Stack(); 
        stack.push(v.elements()); 
    } 
 
    public boolean hasMoreElements()
    { 
        return (!stack.empty() && ((Enumeration)stack.peek()).hasMoreElements()); 
    } 

    /*
    public Object nextElement()
    {
        Enumeration enumer = (Enumeration)stack.peek(); 
        Object node = enumer.nextElement(); 
        if(!enumer.hasMoreElements()) 
            stack.pop(); 
        Enumeration children = new ChildrenEnumeration(treeModel, node); 
        if(children.hasMoreElements()) 
            stack.push(children);
        
        return node; 
    }
    */
    public Object nextElement()
    { 
        Enumeration enumer = (Enumeration)stack.peek(); 
        Object node = enumer.nextElement();
        
        depth = enumer instanceof ChildrenEnumeration 
                ? ((ChildrenEnumeration)enumer).depth 
                : 0; 
                
        if(!enumer.hasMoreElements())
        {
            stack.pop();
        }
        ChildrenEnumeration children = new ChildrenEnumeration(treeModel, node); 
        children.depth = depth+1; 
        if(children.hasMoreElements())
        { 
            stack.push(children); 
        } 
        return node; 
    } 
    
    
}  



