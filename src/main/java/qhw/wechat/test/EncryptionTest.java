package qhw.wechat.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import sun.security.util.DerInputStream;
import  sun.security.util.DerValue;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;

public class EncryptionTest {
	public static void main(String[] args) throws Exception{
		try {
			new EncryptionTest().test1();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void test1() throws Exception{
		PrivateKey key = KeyUtil.getPrivateKey();
		Map<String, Object> header = new HashMap<>();
		header.put("alg", "RS256");
		header.put("typ", "JWT");
		String payload = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}";
		String compactJws = Jwts.builder()
				  .setPayload(payload).setHeader(header)
				  .signWith(SignatureAlgorithm.RS256, key)
				  .compact();
		System.out.println(compactJws);
		
	}
}
