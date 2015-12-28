package JCommonTools;

public class FileNameTools 
{

	public static String AddExtensionIfNone(String aFileName, String aExt)
	{
		String ret = aFileName;
		if (aExt.length()>0)
		{
			if (!aExt.startsWith("."))
				aExt = "."+aExt;
			if (!aFileName.toLowerCase().endsWith(aExt))
				ret = aFileName + aExt;
		}
		return ret;
	}
	
	public static String ReplaceExtention(String aFileName, String aOldExt, String aNewExt)
	{
		String ret = aFileName;

		if (aOldExt != null && aOldExt.length() > 0 && !aOldExt.startsWith("."))
			aOldExt = "."+aOldExt;
		if (aNewExt != null && aNewExt.length() > 0 && !aNewExt.startsWith("."))
			aNewExt = "."+aNewExt;

		if (aOldExt.length()>0 && aFileName.toLowerCase().endsWith(aOldExt))
		{
			ret = aFileName.substring(0, aFileName.length() -aOldExt.length() ) + aNewExt;
		}
		else
		{
			ret = aFileName + aNewExt;
		}
		return ret;
	}
}
