package JCommonTools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.*;


public class StringHandler extends StreamHandler
{
	private String _text;
	
	public String getString()
	{
		return _text;
	}
	
	public StringHandler()
	{
	      setOutputStream(new
	         OutputStream() {
				@Override
				public void write(int arg0) throws IOException {
					// not called
				} 
	            public void write(byte[] b, int off, int len)
	            {
	            	_text = new String(b, off, len);	            	
	            }
			});
	}

	public void publish(LogRecord record)
	{
      super.publish(record);
      flush();
   }
	
}
