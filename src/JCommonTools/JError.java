package JCommonTools;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

public class JError {

	private int _code;
	private String _text;
	private Boolean _traceMode;
	private PrintStream _out;

	public Boolean getTraceMode()
	{
		return _traceMode;
	}
	public void setTraceMode (Boolean arg)
	{
		_traceMode = arg;
	}
	
	public JError()
	{
		_text = CC.STR_EMPTY;
		_code = 0;
		
		_traceMode = false;
		_out = System.err;
	}
	
	@Override
	public String toString() 
	{
		return "err: " + (_code != 0 ? "["+Integer.toString(_code)+"] " : CC.STR_EMPTY) +_text;
	}
	
	public void setError(String aText)
	{
		setError(aText, 0);
	}
	public void setError(String aText, int aCode)
	{
		_text = aText;
		_code = aCode;
		if (_traceMode && _text.length() > 0)
			_out.println(this.toString());
	}
	public void setError(Exception ex)
	{
		_text = ex.getMessage();
		if (_traceMode)
			ex.printStackTrace();

	}
	public void setError(SQLException ex)
	{
		_text = ex.getMessage();
		if (_traceMode)
			while (ex != null)
			{
				ex.printStackTrace();
				ex = ex.getNextException();
			}
	}

	public void Clear()
	{
		_text = CC.STR_EMPTY;
		_code = 0;
	}
	
	//private void _trace()
	//{
	//}
}
