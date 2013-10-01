package JCommonTools;

/**
 * Расширение для PlaceCode, содержит текст.
 * @author M.Tor
 * 07.10.2009
 */
public class PlaceCodeText extends PlaceCode
{
	protected String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getPCText()
	{
		return place + DELIM + code + DELIM + text;
	}
	public void setPCText(String aVal)
	{
		int ps=place, cs=code;
		String ts = text;
		String ss[] = aVal.split(Character.toString(DELIM));
		try
		{
			place = Integer.parseInt(ss[0]);
			code = Integer.parseInt(ss[1]);
			if (ss.length > 2)
				text = aVal.substring(ss[0].length() + ss[1].length() + Character.SIZE * 2);
			else
				text = CC.STR_EMPTY;
		}
		catch (Exception ex)
		{
			place = ps;
			code = cs;
			text = ts;
		}
		
	}
	
	public PlaceCodeText()
	{
		this (0,0, CC.STR_EMPTY);
	}
	public PlaceCodeText(PlaceCode aPC, String aText)
	{
		this (aPC.place, aPC.code, aText);
	}
	public PlaceCodeText(int aPlace, int aCode, String aText)
	{
		super(aPlace, aCode);
		text = aText;
	}
	public PlaceCodeText(String aPCText)
	{
		this ();
		setPCText(aPCText);
	}

	@Override
	public String toString() 
	{
		return text;
	}
}
