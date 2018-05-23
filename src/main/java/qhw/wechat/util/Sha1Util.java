package qhw.wechat.util;

import java.security.MessageDigest;

public class Sha1Util {
	
	public static String encry(String raw) throws Exception{
		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		byte[] srcBytes = raw.getBytes();  
		  //ʹ��srcBytes����ժҪ  
		sha1.update(srcBytes);  
        //��ɹ�ϣ���㣬�õ�result  
        byte[] resultBytes = sha1.digest();  
        return bytesToHexString(resultBytes);
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

}
