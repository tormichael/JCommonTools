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
	
}
