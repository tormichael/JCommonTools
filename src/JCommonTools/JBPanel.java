package JCommonTools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;


public class JBPanel extends javax.swing.JPanel 
{
	private Image 		_img;
	
	public void set_img(Image _img) 
	{
		this._img = _img;
	}
	
	public JBPanel()
	{
		_img = null;
	}
	public JBPanel(LayoutManager lm)
	{
		super(lm);
		_img = null;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		if (_img != null)
			g.drawImage(_img, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
