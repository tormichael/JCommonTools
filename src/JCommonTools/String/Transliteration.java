package JCommonTools.String;

import JCommonTools.CC;

public class Transliteration 
{
	// CYRILLIC ALPHABET:
	public static final char[] AL_CYR = {
	'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц', 'ч', 'ш','щ','ъ','ы','ь','э','ю','я'
	};
	public static final String[] TRANSLIT_V1 = {
	"a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","kh","c","ch","sh","sch","_","yi","_","e","yu","ya"
	};	
	
	public static String Trans_ru(String aSrc)
	{
		String res = CC.STR_EMPTY;
		
		if (aSrc != null)
		{
			char[] src = aSrc.toLowerCase().toCharArray();
			for (int ii=0; ii < src.length ; ii++)
			{
				int jj=0;
				if (Character.isLetter(src[ii]))
				{
				 	for (; jj < AL_CYR.length; jj++)
				 		if (src[ii] == AL_CYR[jj])
				 			break;
			 	}
			 	else
			 		jj = AL_CYR.length;
			 			
			 	if (jj < AL_CYR.length)
			 		res += TRANSLIT_V1[jj];
			 	else
			 		res += String.valueOf(src[ii]);
			}
		}
		
		return res;
	}

}
