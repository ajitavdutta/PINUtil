package application.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import application.Cryptography.DES.DES;

public class Utility {
	public static final int OddParity = 0;
	public static final int EvenParity = 1;
	public static final int NoParity = -1;

	public static final int SingleLength = 16;
	public static final int DoubleLength = 32;
	public static final int TripleLength = 48;

	/**
	 * Converts a string from hexadecimal to binary form.
	 * 
	 * @param hex
	 * @return binary
	 */
	public static String HexToBinary(String hex) {
		return String.format("%4s", new BigInteger(hex, 16).toString(2)).replace(" ", "0");
	}

	/**
	 * Converts a string from Binary to Hexadecimal form.
	 * 
	 * @param binary
	 * @return hexString
	 */
	public static String BinaryToHex(String binary) {
		ArrayList<String> binaryArray = new ArrayList<>();
		String hexString = "";
		int digitNumber = 1;
		int sum = 0;
		int len = binary.length();
		int idx = binary.length();

		if (len > 4) {
			while (len > 0) {
				String subStr = binary.substring(idx - 4, idx);
				binaryArray.add(subStr);
				len = len - 4;
				idx = idx - 4;
				if (len > 0 && len < 4) {
					binary = String.format("%4s", StringUtils.left(binary, len)).replace(" ", "0");
					len = binary.length();
					idx = len;
				}
			}
		} else {
			binaryArray.add(binary);
		}

		idx = binaryArray.size();

		while (idx > 0) {
			String bin = binaryArray.get(idx - 1);
			for (int i = 0; i < bin.length(); i++) {
				if (digitNumber == 1)
					sum += Integer.parseInt(bin.charAt(i) + "") * 8;
				else if (digitNumber == 2)
					sum += Integer.parseInt(bin.charAt(i) + "") * 4;
				else if (digitNumber == 3)
					sum += Integer.parseInt(bin.charAt(i) + "") * 2;
				else if (digitNumber == 4 || i < bin.length() + 1) {
					sum += Integer.parseInt(bin.charAt(i) + "") * 1;
					digitNumber = 0;

					switch (sum) {
					case 10:
						hexString = hexString + "A";
						break;

					case 11:
						hexString = hexString + "B";
						break;

					case 12:
						hexString = hexString + "C";
						break;

					case 13:
						hexString = hexString + "D";
						break;

					case 14:
						hexString = hexString + "E";
						break;

					case 15:
						hexString = hexString + "F";
						break;

					default:
						if (sum < 10) {
							hexString = hexString + new Integer(sum).toString();
						}
						break;
					}

					// if (sum < 10)
					// System.out.print(sum);
					// else if (sum == 10)
					// System.out.print("A");
					// else if (sum == 11)
					// System.out.print("B");
					// else if (sum == 12)
					// System.out.print("C");
					// else if (sum == 13)
					// System.out.print("D");
					// else if (sum == 14)
					// System.out.print("E");
					// else if (sum == 15)
					// System.out.print("F");
					sum = 0;
				}
				digitNumber++;
			}
			idx--;
		}

		return hexString;
	}

	/**
	 * Converts a hexadecimal string to a byte array.
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] HexToByteArray(String hex) {
		int len = hex.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * Converts a byte array to a hexadecimal string.
	 * 
	 * @param bytes
	 * @return hex
	 */
	public static String ByteArrayToHex(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}

	/**
	 * Determines whether a string is hexadecimal.
	 * 
	 * @param str
	 * @return true / false
	 */
	public static boolean IsHex(String str) {
		return str.matches("\\p{XDigit}+");
	}

	/**
	 * Converts a single Hex Char to Integer representation
	 * 
	 * @param c
	 * @return num
	 */
	public static int HexToInt(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'A' && c <= 'F') {
			return c - 'A' + 10;
		}
		if (c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Converts an integer to Hex.
	 * 
	 * @param nibble
	 * @return hex
	 */
	public static char IntToHex(int nibble) {
		if (nibble < 0 || nibble > 15) {
			throw new IllegalArgumentException();
		}
		return "0123456789ABCDEF".charAt(nibble);
	}

	/**
	 * Performs a XOR operation on two hexadecimal strings.
	 * 
	 * @param hex1
	 * @param hex2
	 * @return xorHex
	 */
	public static String XORHex(String hex1, String hex2) {
		char[] chars = new char[hex1.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = IntToHex(HexToInt(hex1.charAt(i)) ^ HexToInt(hex2.charAt(i)));
		}
		return new String(chars);
	}

	/**
	 * Performs an AND operation on two hexadecimal strings.
	 * 
	 * @param hex1
	 * @param hex2
	 * @return andHex
	 */
	public static String ANDHex(String hex1, String hex2) {
		char[] chars = new char[hex1.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = IntToHex(HexToInt(hex1.charAt(i)) & HexToInt(hex2.charAt(i)));
		}
		return new String(chars);
	}

	/**
	 * Performs an OR operation on two hexadecimal strings.
	 * 
	 * @param hex1
	 * @param hex2
	 * @return andHex
	 */
	public static String ORHex(String hex1, String hex2) {
		char[] chars = new char[hex1.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = IntToHex(HexToInt(hex1.charAt(i)) | HexToInt(hex2.charAt(i)));
		}
		return new String(chars);
	}

	/**
	 * Determines if a hexadecimal string has a specified parity.
	 * 
	 * @param hexString
	 * @param parity
	 * @return True if the parity of the Hex String matched with the parity
	 *         specified else False.
	 */
	public static boolean IsParityOK(String hexString, int parity) {
		if (parity == NoParity) {
			return true;
		}

		int i = 0;
		while (i < hexString.length()) {
			String b = HexToBinary(StringUtils.mid(hexString, i, 2));
			i += 2;
			int l = 0;

			for (int j = 0; j <= b.length() - 1; j++) {
				if (StringUtils.mid(b, j, 1).equals("1")) {
					l++;
				}
			}

			if ((l % 2 == 0 && parity == OddParity) || (l % 2 == 1 && parity == EvenParity)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Determines if a hexadecimal string has odd bit parity.
	 * 
	 * @param hexString
	 * @return True if the Hex String has odd bit parity else False.
	 */
	public static boolean IsOddParity(String hexString) {
		return IsParityOK(hexString, OddParity);
	}

	/**
	 * Determines if a hexadecimal string has even bit parity.
	 * 
	 * @param hexString
	 * @return True if the Hex String has even bit parity else False.
	 */
	public static boolean IsEvenParity(String hexString) {
		return IsParityOK(hexString, EvenParity);
	}

	/**
	 * Changes a hexadecimal key to force the specified bit parity.
	 * 
	 * @param hexString
	 * @param parity
	 * @return
	 */
	public static String MakeParity(String hexString, int parity) {
		if (parity == NoParity) {
			return hexString;
		}

		int i = 0;
		String r = "";

		while (i < hexString.length()) {
			String b = HexToBinary(hexString);
			i += 2;
			int l = b.replace("0", "").length();

			if ((l % 2 == 0 && parity == OddParity) || (l % 2 == 1 && parity == EvenParity)) {
				if (StringUtils.right(b, 1) == "1") {
					r = r + StringUtils.left(b, 7) + "0";
				} else {
					r = r + StringUtils.left(b, 7) + "1";
				}
			} else {
				r = r + b;
			}
		}

		return BinaryToHex(r);
	}

	/**
	 * Changes a hexadecimal string to force odd bit parity.
	 * 
	 * @param hexString
	 * @return
	 */
	public static String MakeOddParity(String hexString) {
		return MakeParity(hexString, OddParity);
	}

	/**
	 * Changes a hexadecimal string to force even bit parity.
	 * 
	 * @param hexString
	 * @return
	 */
	public static String MakeEvenParity(String hexString) {
		return MakeParity(hexString, EvenParity);
	}

	/**
	 * Creates a random DES/3DS key of specified length.
	 * 
	 * @param KeyLength
	 * @return
	 */
	public static String CreateRandomKey(int KeyLength) {
		String randKey = null;
		switch (KeyLength) {
		case SingleLength:
			randKey = SingleLengthRandomKey(OddParity);
			break;

		case DoubleLength:
			randKey = MakeOddParity(SingleLengthRandomKey(NoParity) + SingleLengthRandomKey(NoParity));
			break;

		default:
			randKey = MakeOddParity(SingleLengthRandomKey(NoParity) + SingleLengthRandomKey(NoParity) + SingleLengthRandomKey(NoParity));
			break;
		}

		return randKey;
	}

	private static String SingleLengthRandomKey(int Parity) {
		try {
			Random rndMachine = new Random();
			String s = new String();
			StringBuilder sb = new StringBuilder();

			do {
				for (int i = 1; i <= 16; i++) {
					sb.append(String.format("%01X", rndMachine.nextInt(16)));
					//System.out.println(sb);
				}
				s = sb.toString();
				sb = new StringBuilder();

				if (Parity != NoParity) {
					s = MakeParity(s, Parity);
				}
			} while (DES.IsWeakKey(s));

			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Return last 12 PAN digits, excluding the check digit.
	 * 
	 * @param account
	 * @return
	 */
	public static String GetProperAccountDigits(String account) {
		return StringUtils.mid(account, account.length() - 12 - 1, 12);
	}

	public static void main(String args[]) {
		String hex = BinaryToHex("10100011");
		System.out.println(hex);
		System.out.println(IsOddParity(hex));
		System.out.println(IsEvenParity(hex));

		System.out.println(MakeParity(hex, EvenParity));
	}

}
