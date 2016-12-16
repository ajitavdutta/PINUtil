package application.Cryptography.DES;

import application.util.Utility;

import java.security.InvalidKeyException;

import javax.crypto.spec.DESKeySpec;

public class DES {
	/**
	 * This method encrypts a byte array of 16 bytes.
	 * 
	 * @param bKey
	 * @param bData
	 * @return
	 */
	public static byte[] byteDESEncrypt(byte[] bKey, byte[] bData){
		byte [] bResult = null;
		
		
		
		return bResult;
	}
	
	/**
	 * This method decrypts a byte array of 16 bytes.
	 * 
	 * @param bKey
	 * @param bData
	 * @return
	 */
	public static byte[] byteDESDecrypt(byte[] bKey, byte[] bData){
		byte [] bResult = null;
		
		
		
		return bResult;
	}
	
	/**
	 * This method encrypts hex data under a hex key and returns the result.
	 * 
	 * @param sKey
	 * @param sData
	 * @return
	 */
	public static String DESEncrypt(String sKey, String sData){
		byte[] bKey = Utility.HexToByteArray(sKey);
		byte[] bData = Utility.HexToByteArray(sData);
		
		return Utility.ByteArrayToHex(byteDESEncrypt(bKey, bData));
	}
	
	/**
	 * This method decrypts hex data using a hex key and returns the result.
	 * 
	 * @param sKey
	 * @param sData
	 * @return
	 */
	public static String DESDecrypt(String sKey, String sData){
		byte[] bKey = Utility.HexToByteArray(sKey);
		byte[] bData = Utility.HexToByteArray(sData);
		
		return Utility.ByteArrayToHex(byteDESDecrypt(bKey, bData));
	}
	
	/**
	 * Determines whether a key is weak or semi-weak.
	 * 
	 * @param hexKey
	 * @return
	 * @throws InvalidKeyException
	 */
	public static boolean IsWeakKey(String hexKey) throws InvalidKeyException{
		byte[] bKey = Utility.HexToByteArray(hexKey);
		return DESKeySpec.isWeak(bKey, 0) ;
	}

}
