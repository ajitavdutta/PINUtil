package application.PIN;

import org.apache.commons.lang3.StringUtils;

import application.util.Utility;

public class PinUtility {

	public static final String PinBlockFormat_AnsiX98 = "AnsiX98";
	public static final String PinBlockFormat_ISO_1 = "ISO_1";
	public static final String PinBlockFormat_ISO_3 = "ISO_3";
	public static final String PinBlockFormat_Docutel = "Docutel";
	public static final String PinBlockFormat_Diebold = "Diebold";
	public static final String PinBlockFormat_Plus = "Plus";
	public static final String PinBlockFormat_IBM_4704 = "IBM_4704";
	public static final String PinBlockFormat_IBM_3621 = "IBM_3621";
	public static final String PinBlockFormat_IBM_3624 = "IBM_3624";

	/**
	 * Construct a PIN block.
	 * 
	 * @param PinBlockFormat
	 * @param PIN
	 * @param Account
	 * @param Padding
	 * @return PINBlock
	 */
	public static String ToPINBlock(String PinBlockFormat, String PIN, String Account, String Padding) {
		String PINBlock = null;
		switch (PinBlockFormat) {
		case PinBlockFormat_AnsiX98:
			PINBlock = FormatPINBlock_AnsiX98(PIN, Account);
			break;

		case PinBlockFormat_ISO_1:
			PINBlock = FormatPINBlock_ISO1(PIN);
			break;

		case PinBlockFormat_ISO_3:
			PINBlock = FormatPINBlock_ISO3(PIN, Account);
			break;

		case PinBlockFormat_Docutel:
			PINBlock = FormatPINBlock_Docutel(PIN, Padding);
			break;

		case PinBlockFormat_Diebold:
			PINBlock = FormatPINBlock_Diebold(PIN);
			break;

		case PinBlockFormat_Plus:
			PINBlock = FormatPINBlock_Plus(PIN, Account);
			break;

		case PinBlockFormat_IBM_4704:
			PINBlock = FormatPINBlock_IBM4704(PIN);
			break;

		case PinBlockFormat_IBM_3621:
			PINBlock = FormatPINBlock_IBM3621(PIN, Padding);
			break;

		case PinBlockFormat_IBM_3624:
			PINBlock = FormatPINBlock_Diebold(PIN);
			break;

		default:
			PINBlock = null;
			break;
		}
		
		return PINBlock;
	}
	
	/**
	 * Find a PIN from a PIN block.
	 * 
	 * @param PinBlockFormat
	 * @param PINBlock
	 * @param Account
	 * @param Padding
	 * @return PIN
	 */
	public static String ToPIN(String PinBlockFormat, String PINBlock, String Account, String Padding) {
		String PIN = null;
		
		switch (PinBlockFormat) {
		case PinBlockFormat_AnsiX98:
			PIN = GetPIN_AnsiX98(PINBlock, Account);
			break;

		case PinBlockFormat_ISO_1:
			PIN = GetPIN_ISO1(PINBlock);
			break;

		case PinBlockFormat_ISO_3:
			PIN = GetPIN_ISO3(PINBlock, Account);
			break;

		case PinBlockFormat_Docutel:
			PIN = GetPIN_Docutel(PINBlock, Padding);
			break;

		case PinBlockFormat_Diebold:
			PIN = GetPIN_Diebold(PINBlock);
			break;

		case PinBlockFormat_Plus:
			PIN = GetPIN_Plus(PINBlock, Account);
			break;

		case PinBlockFormat_IBM_4704:
			PIN = GetPIN_IBM4704(PINBlock);
			break;

		case PinBlockFormat_IBM_3621:
			PIN = GetPIN_IBM3621String(PINBlock, Padding);
			break;

		case PinBlockFormat_IBM_3624:
			PIN = GetPIN_Diebold(PINBlock);
			break;

		default:
			PIN = null;
			break;
		}
		
		return PIN;
	}
	
	/**
	 * Construct PIN block for Ansi X9.8.
	 * 
	 * @param PIN
	 * @param Account
	 * @return PINBlock
	 */
	private static String FormatPINBlock_AnsiX98(String PIN, String Account){
		String hex1 = StringUtils.rightPad(StringUtils.leftPad(new Integer(PIN.length()).toString(), 2, "0"), 16, "F");
		String hex2 = StringUtils.leftPad(Utility.GetProperAccountDigits(Account), 16, "0");
		return Utility.XORHex(hex1, hex2);
	}
	
	/**
	 * Get PIN from PIN block for Ansi X9.8.
	 * 
	 * @param PINBlock
	 * @param Account
	 * @return PIN
	 */
	private static String GetPIN_AnsiX98(String PINBlock, String Account){
		String unXor = Utility.XORHex(PINBlock, StringUtils.leftPad(Utility.GetProperAccountDigits(Account), 16, "0"));
		return StringUtils.mid(unXor, 2	, new Integer(StringUtils.mid(unXor, 0, 2)).intValue());
	}
	
	/**
	 * Construct PIN block for Diebold.
	 * 
	 * @param PIN
	 * @return PINBlock
	 */
	private static String FormatPINBlock_Diebold(String PIN){
		return StringUtils.rightPad(PIN, 16,"F");
	}
	
	/**
	 * Get PIN from PIN block for Diebold.
	 * 
	 * @param PINBlock
	 * @return PIN
	 */
	private static String GetPIN_Diebold(String PINBlock){
		return PINBlock.replace("F", "");
	}
	
	/**
	 * Construct PIN block for Docutel.
	 * 
	 * @param PIN
	 * @param Padding
	 * @return PINBlock
	 */
	private static String FormatPINBlock_Docutel(String PIN, String Padding){
		if (PIN.length() > 6){
			return null; 
		}
		
		String PINChars = new Integer(PIN.length()).toString() + StringUtils.rightPad(PIN, 6, "0");
		return PINChars + StringUtils.mid(Padding, 0, 16  - PINChars.length());
	}
	
	/**
	 * Get PIN from PIN block for Docutel.
	 * 
	 * @param PINBlock
	 * @param Padding
	 * @return PIN
	 */
	private static String GetPIN_Docutel(String PINBlock, String Padding){
		return StringUtils.mid(PINBlock, 1, new Integer(StringUtils.mid(PINBlock, 0, 1)).intValue());
	}
	
	/**
	 * Construct PIN block for IBM 3621.
	 * 
	 * @param PIN
	 * @param Padding
	 * @return PINBlock
	 */
	public static String FormatPINBlock_IBM3621(String PIN, String Padding){
		if (PIN.length() <= 12 ){
			return StringUtils.rightPad(("1234" + PIN), 16, Padding.charAt(0));
		} else {
			return "1234" + StringUtils.mid(PIN, 1, 12);
		}
	}
	
	/**
	 * Get PIN from PIN block for IBM 3621.
	 * 
	 * @param PINBlock
	 * @param Padding
	 * @return PIN
	 */
	private static String GetPIN_IBM3621String(String  PINBlock, String Padding){ 
		return PINBlock.substring(4).replace(Character.toString(Padding.charAt(0)), "");
	}
	
	/**
	 * Construct PIN block for IBM 4704.
	 * 
	 * @param PIN
	 * @return PINBlock
	 */
	private static String FormatPINBlock_IBM4704(String PIN){
		String PINChars = new Integer(PIN.length()).toString() + PIN;
		return StringUtils.rightPad(PINChars, 14, "F")+ 12;
	}
	
	/**
	 * Get PIN from PIN block for IBM 4704.
	 * 
	 * @param PINBlock
	 * @return PIN
	 */
	public static String GetPIN_IBM4704(String PINBlock){
		return StringUtils.mid(PINBlock, 1, new Integer(StringUtils.mid(PINBlock, 0, 1)).intValue());
	}
	
	/**
	 * Construct PIN block for ISO1.
	 * 
	 * @param PIN
	 * @return PINBlock
	 */
	private static String FormatPINBlock_ISO1(String PIN){
		String PINChars = "1" + new Integer(PIN.length()).toString() + PIN;
		String RandomChars = Utility.CreateRandomKey(Utility.SingleLength);
		return PINChars + StringUtils.mid(RandomChars, 0, 16 - PINChars.length());
	}
	
	/**
	 * Get PIN from PIN block for ISO1.
	 * @param PINBlock
	 * @return PIN
	 */
	private static String GetPIN_ISO1(String PINBlock){
		return StringUtils.mid(PINBlock, 2, new Integer(StringUtils.mid(PINBlock, 1, 1)).intValue());
	}
	
	/**
	 * Construct PIN block for ISO3.
	 * 
	 * @param PIN
	 * @param Account
	 * @return PINBlock
	 */
	private static String FormatPINBlock_ISO3(String PIN, String Account){
		String PINChars = "3" + new Integer(PIN.length()).toString() + PIN;
		String RandomChars = Utility.CreateRandomKey(Utility.SingleLength);
		return Utility.XORHex(PINChars + StringUtils.mid(RandomChars, 0, 16 - PINChars.length()), StringUtils.leftPad(Utility.GetProperAccountDigits(Account), 16, "0"));
	}
	
	/**
	 * Get PIN from PIN block for ISO3.
	 * 
	 * @param PINBlock
	 * @param Account
	 * @return
	 */
	private static String GetPIN_ISO3(String PINBlock, String Account){
		String unXor = Utility.XORHex(PINBlock, StringUtils.leftPad(Utility.GetProperAccountDigits(Account), 16, "0"));
		return StringUtils.mid(unXor, 2, new Integer(StringUtils.mid(unXor, 1, 1)).intValue());
	}
	
	/**
	 * Construct PIN block for Plus.
	 * 
	 * @param PIN
	 * @param Account
	 * @return PINBlock
	 */
	private static String FormatPINBlock_Plus(String PIN, String Account){
		String PINChars = "0" + new Integer(PIN.length()).toString() + PIN;
		return Utility.XORHex(StringUtils.rightPad(PINChars, 16, "F"), "0000" + StringUtils.mid(Account, 0, 12));
	}
	
	/**
	 * Get PIN from PIN block for Plus.
	 * 
	 * @param PINBlock
	 * @param Account
	 * @return
	 */
	private static String GetPIN_Plus(String PINBlock, String Account){
		String unXor = Utility.XORHex(PINBlock, "0000"+ StringUtils.mid(Account, 0, 12) );
		return StringUtils.mid(unXor, 2, new Integer(StringUtils.mid(unXor, 1, 1)).intValue());
	}
	
	public static void main(String [] args){
		System.out.println("PIN BLOCK: " + ToPINBlock(PinBlockFormat_ISO_3, "9778", "5353161400259778", ""));
		System.out.println("PIN      : " +  ToPIN(PinBlockFormat_ISO_3, "349749AA8BC992BC", "5353161400259778", ""));
		
		System.out.println("PIN BLOCK: " + ToPINBlock(PinBlockFormat_ISO_1, "9778", "", ""));
		System.out.println("PIN      : " +  ToPIN(PinBlockFormat_ISO_1, "149778D3396C51D1", "", ""));
		
		System.out.println("PIN BLOCK: " + ToPINBlock(PinBlockFormat_AnsiX98, "9778", "5353161400259778", ""));
		System.out.println("PIN      : " +  ToPIN(PinBlockFormat_AnsiX98, "04FFCE9EBFFDA688", "5353161400259778", ""));
	}

}
