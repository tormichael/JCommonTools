package JCommonTools;

/**
 * Элемент (какого-либо набора, списка, дерева и т.п.) содержащий код и текст.
 * @author M.Tor
 *	07.10.2009
 */
public class CodeText 
{
	protected int code;
	protected String text;
	
	public int getCode()
	{
		return code;
	}
	public void setCode(int aCode)
	{
		code=aCode;
	}
	
	public String getText()
	{
		return text;
	}
	public void setText(String aText)
	{
		text=aText;
	}
	
	public CodeText ()
	{
		this(0, CC.STR_EMPTY);
	}
	public CodeText (int aCode, String aText)
	{
		code = aCode;
		text= aText;
	}

	@Override
	public String toString() {
		return text;
	}
	@Override
	public boolean equals(Object arg0) 
	{
		boolean ret = false;
		
		if (arg0 instanceof CodeText)
		{
			CodeText ct = (CodeText) arg0;
			ret =  (code==ct.code && text.equals(ct.text));
		}
		
		return ret;
	}
}
