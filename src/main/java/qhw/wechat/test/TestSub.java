package qhw.wechat.test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class TestSub {
	public static void main(String[] args) {
		try {
			new TestSub().test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void test() throws Exception{
		// TODO Auto-generated method stub
		String json = "{" + 
				"\"iss\": \"7579e07c7bf3258dd868c7a9e7f3bcb7\", " + 
				"\"exp\": \"1988326400\", " + 
				"\"sub\": \"hdcb\", " + 
				"\"aud\": \"Fortumo\"," + 
				"\"nbf\": \"1485256642\"," + 
				"\"iat\": \"1485256645\"," + 
				"\"jti\": \"00001\"," + 
				"\"country_code\": \"EE\"," + 
				"\"channel_code\": \"sandbox-ee\"," + 
				"\"urls\": {" + 
				"\"subscription_callback\": \"https://example.com/subscriptions\"," + 
				"\"redirect\": \"https://example.com/payment-complete\"," + 
				"\"unsubscription_redirect\": \"https://example.com/unsubscribe-complete\"" + 
				"    }," + 
				"\"item_description\": \"Premium Joy Subscription\"," + 
				"\"operation_reference\": \"se1fassion001dhfdqw\"," + 
				"\"subscription\": {" + 
				"\"price\": {" + 
				"\"amount\": 4.99," + 
				"\"currency\": \"EUR\"" + 
				"}," + 
				"\"duration\": 30," + 
				"\"unit\": \"days\"" + 
				"}" + 
				"}";
		
		PrivateKey key = OpenSSHUtil.getPrivateKey();
		Map<String, Object> header = new HashMap<>();
		header.put("alg", "RS256");
		header.put("typ", "JWT");
		String compactJws = Jwts.builder()
					.setHeader(header)
					.setPayload(json)
					.signWith(SignatureAlgorithm.RS256, key)
					.compact();
		System.out.println("json:"+json);
		System.out.println("result:"+compactJws);
		
/*		try {
			PublicKey key = KeyUtil.getPublicKey();
			Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiAiNzU3OWUwN2M3YmYzMjU4ZGQ4NjhjN2E5ZTdmM2JjYjciLCAiZXhwIjogIjE5ODgzMjY0MDAiLCAic3ViIjogImhkY2IiLCAiYXVkIjogIkZvcnR1bW8iLCJuYmYiOiAiMTQ4NTI1NjY0MiIsImlhdCI6ICIxNDg1MjU2NjQ1IiwianRpIjogIjAwMDAxIiwiY291bnRyeV9jb2RlIjogIkVFIiwiY2hhbm5lbF9jb2RlIjogInNhbmRib3gtZWUiLCJ1cmxzIjogeyJzdWJzY3JpcHRpb25fY2FsbGJhY2siOiAiaHR0cHM6Ly9leGFtcGxlLmNvbS9zdWJzY3JpcHRpb25zIiwicmVkaXJlY3QiOiAiaHR0cHM6Ly9leGFtcGxlLmNvbS9wYXltZW50LWNvbXBsZXRlIiwidW5zdWJzY3JpcHRpb25fcmVkaXJlY3QiOiAiaHR0cHM6Ly9leGFtcGxlLmNvbS91bnN1YnNjcmliZS1jb21wbGV0ZSIgICAgfSwiaXRlbV9kZXNjcmlwdGlvbiI6ICJQcmVtaXVtIEpveSBTdWJzY3JpcHRpb24iLCJvcGVyYXRpb25fcmVmZXJlbmNlIjogInNlc3Npb24wMDEiLCJzdWJzY3JpcHRpb24iOiB7InByaWNlIjogeyJhbW91bnQiOiA0Ljk5LCJjdXJyZW5jeSI6ICJFVVIifSwiZHVyYXRpb24iOiAzMCwidW5pdCI6ICJkYXlzIn19.OI_RQZfezal9__kOWGCtXKHgHan_DyY5muu50cw2vSrJoUl1BZeFEZbTYz3K63Icf4jdEl_RFYjK3W2jq4KIaBoDpM803Im7GVSWL3j6TiVwciRpbHVyI-UTTQuvGRhfafdHdmApxNyzDaaEPiMEoL6KZQyHuhWoGhukueZSqOr0CI7eR_WyruNgyIuAN07EtHcHDB8OXy_YHvrFPfHn1q8HZdDW4wQEK0VujuWeXImbpyMnABXYdlgY6S0WxqL-aYaTO1gBAjuRy7F46lMrEEtCd-jRk8Pc5q37k-edWQAJ-bGs2P_a5aO-cadX2eNOcNd-ZhwXQB5xbCFKLqadlwa1UWihXTxCZqDiZILh-5yxCZYzbk6Ovqjsq_n85DdLAHgC3W8OSlqzqxP0-Zd-RfqzKvtrq1f5AWzbe75PDJJu9fC_HbokOzHroNhz3fc0si2woC-VyhE2ZcS8ftXcElaj11q-z91Q2qDQ_H0j_2oNaAg6uKL1CUxuvkAK6zbOY-2T5VxN14QnElrN3Y2tPbsDhS2S1RQqWVLnZ4NdMD8Ou5mAsTj5CPmqvVC0LZgr0jIFw0Kl9Y6j5-6e9WeX16rsmLkwr67kiuSdWkKc4ntd-Ya2B2231pFM6z4QwivOtwufuqnpl4e9ng-qZEWAXaVaopkwjH8vzXji7HeFnv8");
			System.out.println(jws.getBody().getIssuer());
		    //OK, we can trust this JWT

		} catch (SignatureException e) {

		    e.printStackTrace();
		}*/
	}
}
