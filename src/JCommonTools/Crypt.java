package JCommonTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author tor
 * 		28.12.2015
 */
public class Crypt 
{
	/**
	 * Шифрование/дешифрование данных, полученных из входного потока, и
	 * запись их в выходной поток.
	 * @param in - входной поток
	 * @param out - выходной поток
	 * @param cipher - шифр
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public static void Run(InputStream in, OutputStream out, Cipher cipher)
		throws IOException, GeneralSecurityException
	{
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		byte [] inBytes = new byte[blockSize];
		byte [] outBytes = new byte[outputSize];
		
		int inLength = 0;
		boolean more = true;
		
		while (more)
		{
			inLength = in.read(inBytes);
			if (inLength == blockSize)
			{
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			}
			else
			{
				more = false;
			}
		}
		
		if (inLength > 0)
		{
			outBytes = cipher.doFinal(inBytes, 0, inLength);
		}
		else
		{
			outBytes = cipher.doFinal();
		}
		
		out.write(outBytes);
	}

	public static SecretKey getSecretKey(String aPassword)
	{
		byte[] sk = new byte[16];
		byte[] pwd = aPassword.getBytes();
		for (byte ii = 0; ii < 16; ii++)
		{
			if (ii < pwd.length)
				sk[ii] = pwd[ii];
			else
				sk[ii] = ii;
		}
		return new SecretKeySpec(sk, "AES");
	}
	
}
