package JCommonTools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.*;
import javax.swing.*;

public class ShowMessageHandler  extends StreamHandler 
{
	public ShowMessageHandler()
	{
	      setOutputStream(new
	         OutputStream() {
				@Override
				public void write(int arg0) throws IOException {
					// not called
				} 
	            public void write(byte[] b, int off, int len)
	            {
	            	JOptionPane.showMessageDialog(null, new String(b, off, len));	            	
	               //output.append();
	            }
			});
	}

	public void publish(LogRecord record)
	{
      super.publish(record);
      flush();
   }
}
