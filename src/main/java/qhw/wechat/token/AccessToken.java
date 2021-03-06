package qhw.wechat.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import qhw.wechat.util.HttpUtil;
import qhw.wechat.util.PropertyPlaceholder;

import com.alibaba.fastjson.JSONObject;

public class AccessToken {
	private static final Logger logger = LoggerFactory.getLogger(AccessToken.class);
	
	public static String appid = "";
	public static String secret = "";
	private static final String grant_type = "client_credential";
	private static final String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s";
	
	static {
		appid = PropertyPlaceholder.getContextProperty("appid");
		secret = PropertyPlaceholder.getContextProperty("secret");
	}
	
	/**
	 * 获取accessToken,
	 * 判断如果已过有效期，则更新	
	 * @return
	 */
	public static String getAccessToken() throws Exception{
		String update_time = PropertyPlaceholder.getContextProperty("token_update_time");
		String expires_in = PropertyPlaceholder.getContextProperty("token_expires_in");
		long updateTime = Long.parseLong(StringUtils.isEmpty(update_time)? "0" : update_time);
		long expiresIn = Long.parseLong(StringUtils.isEmpty(expires_in)? "0" : expires_in);
		long currentTime = System.currentTimeMillis();
		if (currentTime > updateTime + expiresIn) {//token失效
			return updateAccessToken();
		}
		return PropertyPlaceholder.getContextProperty("access_token");
	}
	/**
	 * 在线获取access_token
	 * @return
	 */
	public static String getAccessTokenOnline(){
		String url = String.format(token_url, grant_type, appid, secret);
		String json = null;
		try {
			json = HttpUtil.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("occur error when fetching accessToken", e);
		}
		return json;
	}
	
	/**
	 * 同步获取access_token，并更新存值
	 * @return
	 */
	public static synchronized String updateAccessToken() throws Exception{
		String updateTime = StringUtils.isEmpty(PropertyPlaceholder.getContextProperty("token_update_time")) ? "0" : PropertyPlaceholder.getContextProperty("token_update_time");

		long currentTime = System.currentTimeMillis();
		long subtract = currentTime - Long.parseLong(updateTime);
		if (subtract < 600000) {//小于10分钟，刚更新的access_token,表明是别的线程已经更新，不需再次更新
			return PropertyPlaceholder.getContextProperty("access_token");
		}else {
			String json = getAccessTokenOnline();
			if (json != null) {
				JSONObject object = JSONObject.parseObject(json);
				String access_token = object.getString("access_token");
				String expires_in = object.getString("expires_in");
				//修改缓存中的值
				PropertyPlaceholder.update("access_token", access_token);
				PropertyPlaceholder.update("token_expires_in", expires_in);
				PropertyPlaceholder.update("token_update_time", currentTime + "");
				
				//修改properties文件中的值
				Properties prop = new Properties();
				FileInputStream in = new FileInputStream(new File(AccessToken.class.getResource("token.properties").toURI()));
				prop.load(in);
				in.close();
				prop.setProperty("access_token", access_token);
				prop.setProperty("token_expires_in", expires_in);
				prop.setProperty("token_update_time", currentTime + "");
				FileOutputStream out = new FileOutputStream(new File(AccessToken.class.getResource("token.properties").toURI()));
				prop.store(out, "cc");
				out.close();
				return access_token;
			}else {
				logger.error("updateAccessToken() get a null json");
				return null;
			}
		}
	}
}
