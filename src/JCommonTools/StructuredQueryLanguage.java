package JCommonTools;

import java.util.regex.Pattern;

public class StructuredQueryLanguage 
{
	public static boolean isSelectClause(String aText)
	{
		Pattern ptnSelect = Pattern.compile("\\bselect\\b");
		return ptnSelect.matcher(aText.toLowerCase()).find();
	}

}
