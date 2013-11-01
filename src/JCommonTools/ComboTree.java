package JCommonTools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ComboTree extends JPanel 
{
	private JTextField _txt;
	private JButton _cmd;
	private JFrame _frm;
	protected JTree mTree;
	
	private boolean _unlockCmd = true;
	private TreePath _selectedPath = null;
	
	private Action	_actSelected;
	
	public TreeModel getTreeModel()
	{
		return mTree.getModel();
	}
	public void setTreeModel(TreeModel aTM)
	{
		mTree.setModel(aTM);
	}

	public Action getActionSelected() 
	{
		return _actSelected;
	}
	public void setActionSelected(Action _actSelected) 
	{
		this._actSelected = _actSelected;
	}
	
	public void setSelectedPath(TreePath aPath)
	{
		_selectedPath = aPath;
		if (aPath != null)
			_txt.setText(_selectedPath.getLastPathComponent().toString());
	}
	
	
	public ComboTree()
	{
		_actSelected = null;
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());

		_txt = new JTextField();
		_txt.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		_cmd = new JButton(actCmd);
		_cmd.setText("\u25BC");
		_cmd.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));
		//Dimension dm = new Dimension(_cmd.getPreferredSize().width/2, _cmd.getPreferredSize().height);
		//_cmd.setPreferredSize(dm);
		add(_txt, BorderLayout.CENTER);
		add(_cmd, BorderLayout.EAST);
	
		_frm = new JFrame();
		_frm.setAlwaysOnTop(true);
		_frm.setUndecorated(true);
		mTree = new JTree();
		mTree.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_frm.add(new JScrollPane(mTree), BorderLayout.CENTER);
		
		
		_txt.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) 
			{
				e.setSource(ComboTree.this);
				processFocusEvent(e);
			}
			
			@Override
			public void focusGained(FocusEvent e) 
			{
				e.setSource(ComboTree.this);
				processFocusEvent(e);
			}
		});
		
		mTree.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) 
			{
				UnvisibleFrame();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mTree.addMouseListener(msTreeEv);
	}

	private void VisibleFrame()
	{
		_frm.setVisible(true);
		_cmd.setText("\u25B2");
		_frm.setLocation(getLocationOnScreen().x, getLocationOnScreen().y + getHeight());
		_frm.setSize(getWidth(), getHeight()*7);
		_cmd.addMouseListener(msCmdEv);
		if (_selectedPath != null)
			mTree.setSelectionPath(_selectedPath);
		_unlockCmd = false;
	}
	
	private void UnvisibleFrame()
	{
		_frm.setVisible(false);
		_cmd.setText("\u25BC");
		_cmd.removeMouseListener(msCmdEv);
		if (_actSelected != null)
		{
			_actSelected.actionPerformed(new ActionEvent(mTree.getSelectionPath(), 0, _txt.getText()));
		}
	}
	
	Action actCmd = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_unlockCmd)
				VisibleFrame();
			else
				_unlockCmd = true;
		}
	};
	
	MouseListener msCmdEv = new MouseAdapter() 
	{
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			_unlockCmd = true;
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			_unlockCmd = false;
		}
	}; 

	MouseListener msTreeEv = new MouseAdapter() 
	{
//		@Override
//		public void mouseReleased(MouseEvent e) {}
//		@Override
//		public void mousePressed(MouseEvent e) {}
//		@Override
//		public void mouseExited(MouseEvent e) {}
//		@Override
//		public void mouseEntered(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if (e.getClickCount() == 2 && mTree.getSelectionPath()!= null)
			{
				_selectedPath = mTree.getSelectionPath();
				_txt.setText(_selectedPath.getLastPathComponent().toString());
				if (mTree.getModel().getChildCount(_selectedPath.getLastPathComponent()) == 0)
					UnvisibleFrame();
			}
			
		}
	};
	
}

