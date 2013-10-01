package JCommonTools;

public class Convert 
{

	public static Integer ToIntegerOrZero(String arg)
	{
		Integer ret;
		try
		{
			ret = Integer.parseInt(arg);
		}
		catch (Exception ex) {ret = 0;}
		
		return ret;
	}
	public static Integer ToIntegerOrZero(Object arg)
	{
		return ToIntegerOrZero(arg.toString());
	}
	
	public static Long ToLongOrZero(String arg)
	{
		Long ret;
		try
		{
			ret = Long.parseLong(arg);
		}
		catch (Exception ex) {ret = 0L;}
		
		return ret;
	}
	
}
