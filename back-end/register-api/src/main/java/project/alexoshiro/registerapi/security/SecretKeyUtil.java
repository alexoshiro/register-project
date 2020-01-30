package project.alexoshiro.registerapi.security;

import javax.crypto.SecretKey;

public class SecretKeyUtil {
	private static SecretKey secretKey;

	public static SecretKey getSecretKey() {
		return secretKey;
	}

	public static void setSecretKey(SecretKey secretKey) {
		if (SecretKeyUtil.secretKey == null) {
			SecretKeyUtil.secretKey = secretKey;
		}
	}

}
