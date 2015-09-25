package JCommonTools;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;


public class JPanelWI extends javax.swing.JPanel 
{
	private Image 		_img;
	
	public void set_img(Image img) 
	{
		this._img = img;
	}
	
	public JPanelWI()
	{
		super();
		
		_img = null;
	}
	public JPanelWI(LayoutManager lm)
	{
		super(lm);
		_img = null;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		if (_img != null)
		{
			//g.drawImage(_img, 0, 0, this.getWidth(), this.getHeight(), null);
			BufferedImage bi = null;
			if (_img instanceof BufferedImage)
			{
				bi = ResizeImage((BufferedImage) _img, this.getWidth(), this.getHeight());
				Point pnt = getCenterImagePoint(bi);
				g.drawImage(bi, pnt.x, pnt.y, this);
			}
			else
			{
				Point pnt = getCenterImagePoint(_img);
				g.drawImage(_img, pnt.x, pnt.y, this);
			}
		}
	}
	
	public Point getCenterImagePoint(Image aImg)
	{
		Point pnt = new Point(0, 0);
		if (aImg.getWidth(null) < this.getWidth())
			pnt.x = (this.getWidth()-aImg.getWidth(null))/2;
		if (aImg.getHeight(null) < this.getHeight())
			pnt.y = (this.getHeight()-aImg.getHeight(null))/2;
		
		return pnt;
	}
	
	public static BufferedImage ResizeImage(BufferedImage aSrcImg, int aWidth, int aHeight) 
	{
		BufferedImage resizedImage = null;		
        try 
        {
            int type = aSrcImg.getType() == 0? BufferedImage.TYPE_INT_ARGB : aSrcImg.getType();

            //*Special* if the width or height is 0 use image src dimensions
            if (aWidth == 0) {
                aWidth = aSrcImg.getWidth();
            }
            if (aHeight == 0) {
                aHeight = aSrcImg.getHeight();
            }

            int fHeight = aHeight;
            int fWidth = aWidth;

            //Work out the resized width/height
            if (aSrcImg.getHeight() > aHeight || aSrcImg.getWidth() > aWidth) {
                fHeight = aHeight;
                int wid = aWidth;
                float sum = (float)aSrcImg.getWidth() / (float)aSrcImg.getHeight();
                fWidth = Math.round(fHeight * sum);

                if (fWidth > wid) {
                    //resize again for the width this time
                    fHeight = Math.round(wid/sum);
                    fWidth = wid;
                }
            }
            else
            {
            	fWidth = aSrcImg.getWidth();
            	fHeight = aSrcImg.getHeight();
            	float sc =  aHeight/fHeight > aWidth/fWidth ? aWidth/fWidth : aHeight/fHeight; 
            	fWidth = Math.round(fWidth * sc);
            	fHeight = Math.round(fHeight * sc);
            }

            resizedImage = new BufferedImage(fWidth, fHeight, type);
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(aSrcImg, 0, 0, fWidth, fHeight, null);
            g.dispose();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return null;
        }

        return resizedImage;
    }
}
