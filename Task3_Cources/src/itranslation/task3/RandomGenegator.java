package itranslation.task3;

import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

public class RandomGenegator {

	public static String getSecureKey() {
		byte[] key = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(key); 
		return DatatypeConverter.printHexBinary(key);
	}

	public static int getMove(int length) {
		SecureRandom secureRandom = new SecureRandom();
		int move = secureRandom.nextInt(length);
		return move+1;
	}

}
