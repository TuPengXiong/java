package com.tupengxiong.jvm.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * ClassName: RSA <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年5月31日 上午10:09:36 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public class SHA256WithRSA {

	//SHA1WithRSA(RSA) SHA256WithRSA(RSA2)
	public static final String SIGN_ALGORITHMS = "SHA256WithRSA";
	
	public static final String CHARSET = "UTF-8";

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param public_key
	 *            公钥
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign, String public_key) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(public_key);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(CHARSET));

			boolean bverify = signature.verify(Base64.decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;

		keyBytes = Base64.decode(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;
	}
	
	/**
     * 
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, Object> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, (String)value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }
    
    /**
	 * 解密
	 * 
	 * @param content
	 *            密文
	 * @param private_key
	 *            商户私钥
	 * @param input_charset
	 *            编码格式
	 * @return 解密后的字符串
	 */
	public static String decrypt(String content, String private_key) throws Exception {
		PrivateKey prikey = getPrivateKey(private_key);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, prikey);

		InputStream ins = new ByteArrayInputStream(Base64.decode(content));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		byte[] buf = new byte[128];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}

		return new String(writer.toByteArray(), CHARSET);
	}

	
	public static void main(String[] args) throws Exception {
		
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs0MZ1NrPejKB3Uq1LMq0/GBPw/Mfvb9tcAlXEAt8t89gksig8iBi/D+jXHkDFM0XqXnK0sRrIxK1qCnMNpxefqRNyC7EGC10u/pJHs+M1s6CKoONlUkwezeQikdyPkwGn19NeOXUeTBxiOQjiy7+O7FBFcW1jxuSktRGyt+eIrx9pDEPYI8Na55rbpmjbQlffZ6D4BDk6wPDccz5S8niuBKFBo29IKsLT3q4ONGCsqNPZDUqRNlrKyo0GRS4685YLM8Agz5Hipu6TlLPSwtwcPL+IZl4bTP9iThW3CmvqrpXvKj1xUiMPkGu7HRnuKGj+XjFRPe9BQ3EBem5cvGTUQIDAQAB";
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzQxnU2s96MoHdSrUsyrT8YE/D8x+9v21wCVcQC3y3z2CSyKDyIGL8P6NceQMUzRepecrSxGsjErWoKcw2nF5+pE3ILsQYLXS7+kkez4zWzoIqg42VSTB7N5CKR3I+TAafX0145dR5MHGI5COLLv47sUEVxbWPG5KS1EbK354ivH2kMQ9gjw1rnmtumaNtCV99noPgEOTrA8NxzPlLyeK4EoUGjb0gqwtPerg40YKyo09kNSpE2WsrKjQZFLjrzlgszwCDPkeKm7pOUs9LC3Bw8v4hmXhtM/2JOFbcKa+qule8qPXFSIw+Qa7sdGe4oaP5eMVE970FDcQF6bly8ZNRAgMBAAECggEAAgNe5uUOL3EhxDpyjm44Wh06yBiS4q6jq/5u299FJ0tM6lkWdaGneu28B+3T+wfSnDSh8nwCOAKdx6WwhWx8Iy/1L6pWyW65QOMurwnlqwPmslOH44VO5hNZrDPhNJHmASQw2oq6OCIzJDZrr8R3VnRHJtdxkoncu4lmwiCAxiMS8hgGfq9wJZsWx21NbLCktAMilikWlb79FF2DAwwKgzjLEpdYkg/5SkIwlRhUkVe+RqDAxd5HmRHHofN/fdEeCS1KVqqInELtM0Hy9g2Eu4021kvJBjK8SeT2O0jwhp7ACwWTSjPGJ4L5ukJlUac1NXISekBb27sCFsOw7lelgQKBgQDo06IxjX9bbk+AJ/7htuaDLzC7IFdrvGa/i8f7D/7kXkCH9zfdb+Ine2zTWb4plVAoh9xqERZQbRwtpKjJE0T0otW/Htpv7Jh63/IRE4633ZePYJor9R1vOTO2kbAEPRicwj+DTsCYXDY4AXUKenSPP5kQ5U7Zi1FLKUB7vduTCwKBgQDFGqe0iKZcwb97GKnDzRGuKk+psIiEt84ejtt4eVb5CsHi1teOgUqBqRrRpFLFDemLmebfSlo/JIg3PnaQx6EXnEuCzG85bXpkPJY7bvj4NBJBAt4k0ptBkl2gNICxJYkm4jJh2D97FoV34Tcpv2JYrDX+ABCAqW9RSfR6N6rskwKBgB1jGQXIJlsUAVTbt4Al5dKJEk2MN3yRuyZSLluyGSoZ+2st+Q8qIBF7srC6kxYMkqGLBHce0QI1w2i/b85xcDKwmuoUqt2Vr2lS+urM3Sa4AXlHaC5EMgLn5W8V1HG0hHbEzd91ATo56V4IUQ2Rh0TNcjR/vQQYYZprCoiT3jMhAoGAEAMWVKg1O5vRvmJGiE2Efi2ZwyNAM+fqqrjYQ3U4B4tELPVfFYiTUO037If44WE788dQ5hrYMgD5v+MnJqPRBmYADGQnNPcb1kDFw5ZES4WPZhChk0Q4sJ7/VCBvw/RUq//8L86teYZe2VpGbPHLP4Dd8gB3Vrxs+qGTZspW7FkCgYEA0rxeXfZ35KunCdxN/ClEta13AUEZ3e2ESUqELrfQN9NrP6VhXVKOluOVrqOV/RWSFyNeBrOcYEfbBIz+WJBKrM4M6H7LB2QPLYz6htyCOmE01Cfd4rjZfeG/39UwdCV0lNYBy+J+Ih2/sqJUGWBPBZqjhnEo1En4STuBeUyk+wI=";
	
		System.out.println(publicKey);
		System.out.println(privateKey);
		String sign = sign("a=123", privateKey);
		System.out.println(sign);
		System.out.println(verify("a=123", sign, publicKey));
		
		System.out.println(decrypt(sign, privateKey));
		
	}
}