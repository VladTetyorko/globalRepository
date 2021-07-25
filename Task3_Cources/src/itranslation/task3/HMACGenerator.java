package itranslation.task3;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACGenerator {
	public byte[] calcHmacSha256(String secretKey, String moveValue) {
		byte[] hmacSha256=null;
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
			mac.init(secretKeySpec);
			hmacSha256 = mac.doFinal(moveValue.getBytes());
		} catch (Exception e) {
			throw new RuntimeException("Failed to calculate hmac-sha256", e);
		}
		return hmacSha256;
	}

}
