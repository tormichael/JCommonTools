package JCommonTools.String;

import JCommonTools.CC;

public class Transliteration 
{
	// CYRILLIC ALPHABET:
	public static final char[] AL_CYR = {
	'à','á','â','ã','ä','å','¸','æ','ç','è','é','ê','ë','ì','í','î','ï','ð','ñ','ò','ó','ô','õ','ö', '÷', 'ø','ù','ú','û','ü','ý','þ','ÿ'
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
