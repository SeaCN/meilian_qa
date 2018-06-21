package qhw.wechat.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;

import sun.misc.BASE64Decoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class OpenSSHUtil {
	public static PrivateKey getPrivateKey() throws Exception{
		String path = "C:\\Users\\Administrator.USER-20170815MG\\Desktop\\newkey\\key.ppk";
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String keyinfo = "";
		String line = null;
		//去掉文件头尾的注释信息
		while ((line = br.readLine()) != null) {
			if (line.indexOf("---") == -1) {
			keyinfo += line;
			}
		}
		//密钥信息用 BASE64 编码加密过，需要先解密
		byte[] decodeKeyinfo = (new BASE64Decoder()).decodeBuffer(keyinfo);
		//使用 DerInputStream 读取密钥信息
		DerInputStream dis = new DerInputStream(decodeKeyinfo);
		//密钥不含 otherPrimeInfos 信息，故只有 9 段
		DerValue[] ders = dis.getSequence(9);
		//依次读取 RSA 因子信息
		int version = ders[0].getBigInteger().intValue();
		BigInteger modulus = ders[1].getBigInteger();
		BigInteger publicExponent = ders[2].getBigInteger();
		BigInteger privateExponent = ders[3].getBigInteger();
		BigInteger primeP = ders[4].getBigInteger();
		BigInteger primeQ = ders[5].getBigInteger();
		BigInteger primeExponentP = ders[6].getBigInteger();
		BigInteger primeExponentQ = ders[7].getBigInteger();
		BigInteger crtCoefficient = ders[8].getBigInteger();        
		//generate public key and private key
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec rsaPublicKeySpec = 
		new RSAPublicKeySpec(modulus, publicExponent);
		PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);              
		RSAPrivateCrtKeySpec rsaPrivateKeySpec = 
		new RSAPrivateCrtKeySpec(modulus,publicExponent,privateExponent,
		primeP,primeQ,primeExponentP,primeExponentQ,crtCoefficient);
		PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
		
		return privateKey;
	}
}
