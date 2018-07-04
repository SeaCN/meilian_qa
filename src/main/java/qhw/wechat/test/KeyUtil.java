package qhw.wechat.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class KeyUtil {
	public static PrivateKey getPrivateKey() {
        String file = "C:\\Users\\Administrator\\Desktop\\newkey\\key.ppk";
        try {
            Map<String, String> keyMap = parseKV(file);
            //获取公钥信息
            String publicKeyInfo = keyMap.get("Public-Lines");
            //密钥信息被 BASE64 加密过，需要先解密
            byte[] decodedPubKey = (new BASE64Decoder())
                    .decodeBuffer(publicKeyInfo);
/*            System.out.println("publick key : "
                    + bytesToHexString(decodedPubKey));*/
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                    decodedPubKey));
            //读取前 4 个字节，获得该元素数据长度
            int leng = dis.readInt();
            //根据长度读入字节信息
            //公钥第一段为固定字符串信息，所以单独解析
            byte[] tmpBytes = new byte[leng];
            dis.readFully(tmpBytes);
            String keyAlgo = new String(tmpBytes);
            BigInteger publicExponent = readInt(dis);
            BigInteger modulus = readInt(dis);
            dis.close();
            //用 BASE64 编码解密私钥
            byte[] decodedPriKey =
            (new BASE64Decoder()).decodeBuffer(keyMap.get("Private-Lines"));
            dis = new DataInputStream(new ByteArrayInputStream(decodedPriKey));
            BigInteger privateExponent = readInt(dis);
            BigInteger primeP = readInt(dis);
            BigInteger primeQ = readInt(dis);
            BigInteger iqmp = readInt(dis);
            BigInteger primeExponentP = privateExponent.mod(primeP.subtract(BigInteger.ONE));
            BigInteger primeExponentQ = privateExponent.mod(primeQ.subtract(BigInteger.ONE));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus,
                    publicExponent);
            PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
            RSAPrivateCrtKeySpec rsaPrivateKeySpec = new RSAPrivateCrtKeySpec(
            modulus, publicExponent, privateExponent, primeP, primeQ,
            primeExponentP, primeExponentQ, iqmp);
            PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
            printKey(new BigInteger("0"), modulus, publicExponent, privateExponent, primeP, primeQ, primeExponentP, primeExponentQ, new BigInteger("1"));
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static PublicKey getPublicKey() {
        String file = "C:\\Program Files\\PuTTY\\privatekey.ppk";
        try {
            Map<String, String> keyMap = parseKV(file);
            //获取公钥信息
            String publicKeyInfo = keyMap.get("Public-Lines");
            //密钥信息被 BASE64 加密过，需要先解密
            byte[] decodedPubKey = (new BASE64Decoder())
                    .decodeBuffer(publicKeyInfo);
/*            System.out.println("publick key : "
                    + bytesToHexString(decodedPubKey));*/
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                    decodedPubKey));
            //读取前 4 个字节，获得该元素数据长度
            int leng = dis.readInt();
            //根据长度读入字节信息
            //公钥第一段为固定字符串信息，所以单独解析
            byte[] tmpBytes = new byte[leng];
            dis.readFully(tmpBytes);
            String keyAlgo = new String(tmpBytes);
            BigInteger publicExponent = readInt(dis);
            BigInteger modulus = readInt(dis);
            dis.close();
            //用 BASE64 编码解密私钥
            byte[] decodedPriKey =
            (new BASE64Decoder()).decodeBuffer(keyMap.get("Private-Lines"));
            dis = new DataInputStream(new ByteArrayInputStream(decodedPriKey));
            BigInteger privateExponent = readInt(dis);
            BigInteger primeP = readInt(dis);
            BigInteger primeQ = readInt(dis);
            BigInteger iqmp = readInt(dis);
            BigInteger primeExponentP = privateExponent.mod(primeP.subtract(BigInteger.ONE));
            BigInteger primeExponentQ = privateExponent.mod(primeQ.subtract(BigInteger.ONE));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus,
                    publicExponent);
            PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
            RSAPrivateCrtKeySpec rsaPrivateKeySpec = new RSAPrivateCrtKeySpec(
            modulus, publicExponent, privateExponent, primeP, primeQ,
            primeExponentP, primeExponentQ, iqmp);
            PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
            printKey(new BigInteger("0"), modulus, publicExponent, privateExponent, primeP, primeQ, primeExponentP, primeExponentQ, new BigInteger("1"));
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     //读入前 4 个字节获得元素长度，然后读取该元素字节信息并转换为 BigInteger
    public static BigInteger readInt(DataInputStream dis) throws IOException {
        int leng = dis.readInt();
        byte[] tmpBytes = new byte[leng];
        dis.readFully(tmpBytes);
        return new BigInteger(1, tmpBytes);
    }
    //将密钥信息解析为键值对
    private static Map<String, String> parseKV(String file) throws IOException {
        HashMap<String, String> kv = new HashMap<String, String>();
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(file));
            String k = null;
            String line;
            while ((line = r.readLine()) != null) {
                int idx = line.indexOf(": ");
            if (idx > 0) {
                    k = line.substring(0, idx);
                    if ((!"Public-Lines".equals(k))
                            && (!"Private-Lines".equals(k))) {
                        kv.put(k, line.substring(idx + 2));
                    }
                } else {
                    String s = (String) kv.get(k);
                    if (s == null) {
                        s = "";
                    }
                    s = s + line;
                    kv.put(k, s);
                }
            }
        } finally {
            r.close();
        }
        return kv;
    }
    
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    
    public static void printKey(BigInteger version, BigInteger modulus, BigInteger publicExponent, BigInteger privateExponent, BigInteger primeP,
    		BigInteger primeQ, BigInteger primeExponentP, BigInteger primeExponentQ, BigInteger crtCoefficient) throws Exception{
    	DerOutputStream deros = new DerOutputStream();
    	deros.putInteger(version);
    	deros.putInteger(modulus);
    	deros.putInteger(publicExponent);
    	deros.putInteger(privateExponent);
    	deros.putInteger(primeP);
    	deros.putInteger(primeQ);
    	deros.putInteger(primeExponentP);
    	deros.putInteger(primeExponentQ);
    	deros.putInteger(crtCoefficient);
    	deros.flush();              
    	byte[] rsaInfo = deros.toByteArray();
    	deros.close();
    	deros = new DerOutputStream();
    	//写入信息时需要写一个 Sequence 的 tag
    	deros.write(DerValue.tag_Sequence, rsaInfo);
    	deros.flush();
    	//写入时需要用 BASE64 编码进行加密
    	String keyInfo = (new BASE64Encoder()).encode(deros.toByteArray());
    	deros.close();
    	
    }
}
