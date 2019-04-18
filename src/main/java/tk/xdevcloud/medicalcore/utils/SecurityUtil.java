package tk.xdevcloud.medicalcore.utils;

import javax.crypto.*;
import java.security.SecureRandom;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
/**
 * Security Util Class 
 * @author william
 *
 */
public class SecurityUtil {
    /**
     * Encrypts some data
     * @param data to be encrypted
     * @return encrypted Aes string 
     * @throws NoSuchAlgorithm 
     * @throws InvalidKeyException
     * @throws IllegBlockSizeException
     */
	public static String aesEncrypt(String data) {

		Cipher cipher;
		String encryptedString = null;

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// generate secret key
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(1024);
			// set secure random
			SecureRandom random = new SecureRandom();
			byte randomBytes[] = new byte[20];
			random.nextBytes(randomBytes);
			SecretKey secretKey = keyGen.generateKey();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
			cipher.update(data.getBytes());
			encryptedString = new String(cipher.doFinal());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		} catch (NoSuchPaddingException e) {
			e.printStackTrace();

		} catch (InvalidKeyException e) {

			e.printStackTrace();

		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encryptedString;

	}

}
