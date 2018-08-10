package com.tupengxiong.jvm.security;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSA {

	public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQgEoj3z9JrdPNI23DbMQkl3gkGuDke7iBr5yrYyqolkTyxuBLWFwHNuGv4VKOj9fXg61QxpaJ/fxDBvMvmkBSRowHBloGFceVTx8wV/8u0DcjvTCu0IZ1zp6wjG6xBn5j66Sg/q+9hvaY2p7fkKmsvcW6VoNPgQHU1Cf01DLZmQIDAQAB+oXcINOiE3AsuZ4VJmwNZg9Y/7fY+OFRS2JAh5YMsrv2qyoGP+Z9ksre26NYR+Lt91B2lhdwJHLpQpziaANZm/ONb31fj/lwIDAQAB";
	public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANCASiPfP0mt080jbcNsxCSXeCQa4OR7uIGvnKtjKqiWRPLG4EtYXAc24a/hUo6P19eDrVDGlon9/EMG8y+aQFJGjAcGWgYVx5VPHzBX/y7QNyO9MK7QhnXOnrCMbrEGfmPrpKD+r72G9pjant+Qqay9xbpWg0+BAdTUJ/TUMtmZAgMBAAECgYBSozY/Z4FW+31h5fPgK+DFu/8TGFAgXuTvCaJnz2Md9IkZTDejxT6cYWUr53toI5zhvz/XLw6FXNQ54KxMJq/s9PiZYUgq/PMrnyU4gBSTm5BmiWjdaGicVEZ1lofHjpkAchPNW/CzwxD8AeKI7QaObE+EkWbLAi6sa+nRdHKgrQJBAOwYLD2DncU15XCKS0RNzTrNohdBQcisOPHdtQO0CGZlxx3xjuU4WL6/EpdmbjTeYbOSDKCmY5vyVbYZdOWfEs8CQQDiFIwWpvW2WLxLVw3i2P55WmMMXuecwEzg++ae3Ht7nW0zNcWSsyvHh40sM8XqEzmWOzMY6JOePbkuVfWTc4cXAkBRzf5mQhiEoKwjVofF3v9hhKbJT/8vPR1uENgLtHHEqTdZFL3ihqeZUDNs6jz9bKCFy/E8KOsSueEg+6kZdwjZAkEAj2RW4fstd2VasDJb5ViaNqAEmJENOBej60L6KCJR07qqy0M8t+oaR2iLOtDvo6Jj8QxFQXQqRMCDVodAxjANKwJAL3KuaqA6kdy9RxdV3uP8nRXLY7C/1ZIK6U0pyZqKXEwpD+7Ar3hwwhPz9TeuoqjB/cCknZjw70BQFQ0/VUHW2g==";

	private static String rsaPrivateKey = "rsaPrivateKey";
	private static String rsaPublicKey = "rsaPublicKey";

	private static String algorithm = "RSA"; //$NON-NLS-1$
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;
	

	public static void main(String[] args) throws Exception {
		String data = "111"; //$NON-NLS-1$

		String test1 = encrypt(privateKey, data);
		String testDecrypt1 = decrypt(publicKey, test1);
		System.out.println(testDecrypt1);
		
		Map<String, Object> keyMap = initKey();
		String test = encrypt(getPrivateKey(keyMap), data);
		String testDecrypt = decrypt(getPublicKey(keyMap), test);
		System.out.println(testDecrypt);

	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(rsaPrivateKey, privateKey);
		keyMap.put(rsaPublicKey, publicKey);
		return keyMap;
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {

		Key key = (Key) keyMap.get(rsaPrivateKey);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {

		Key key = (Key) keyMap.get(rsaPublicKey);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * 加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String key, String data) throws Exception {
		byte[] decode = new BASE64Decoder().decodeBuffer(key);
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		PrivateKey generatePrivate = kf.generatePrivate(pkcs8EncodedKeySpec);
		Cipher ci = Cipher.getInstance(algorithm);
		ci.init(Cipher.ENCRYPT_MODE, generatePrivate);

		byte[] bytes = data.getBytes();
		int inputLen = bytes.length;
		int offLen = 0;// 偏移量
		int i = 0;
		ByteArrayOutputStream bops = new ByteArrayOutputStream();
		while (inputLen - offLen > 0) {
			byte[] cache;
			if (inputLen - offLen > MAX_ENCRYPT_BLOCK) {
				cache = ci.doFinal(bytes, offLen, MAX_ENCRYPT_BLOCK);
			} else {
				cache = ci.doFinal(bytes, offLen, inputLen - offLen);
			}
			bops.write(cache);
			i++;
			offLen = MAX_ENCRYPT_BLOCK * i;
		}
		bops.close();
		byte[] encryptedData = bops.toByteArray();
		String encodeToString = new BASE64Encoder().encode(encryptedData);
		return encodeToString;
	}

	/**
	 * 解密
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String key, String data) throws Exception {
		byte[] decode = new BASE64Decoder().decodeBuffer(key);
		// PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new
		// PKCS8EncodedKeySpec(decode); //java底层 RSA公钥只支持X509EncodedKeySpec这种格式
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		PublicKey generatePublic = kf.generatePublic(x509EncodedKeySpec);
		Cipher ci = Cipher.getInstance(algorithm);
		ci.init(Cipher.DECRYPT_MODE, generatePublic);

		byte[] bytes = new BASE64Decoder().decodeBuffer(data);
		int inputLen = bytes.length;
		int offLen = 0;
		int i = 0;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		while (inputLen - offLen > 0) {
			byte[] cache;
			if (inputLen - offLen > MAX_DECRYPT_BLOCK) {
				cache = ci.doFinal(bytes, offLen, MAX_DECRYPT_BLOCK);
			} else {
				cache = ci.doFinal(bytes, offLen, inputLen - offLen);
			}
			byteArrayOutputStream.write(cache);
			i++;
			offLen = MAX_DECRYPT_BLOCK * i;

		}
		byteArrayOutputStream.close();
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		return new String(byteArray);
	}

}
