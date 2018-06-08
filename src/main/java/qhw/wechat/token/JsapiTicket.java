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

public class JsapiTicket {
	private static final Logger logger = LoggerFactory.getLogger(JsapiTicket.class);
	
	private static final String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	
	/**
	 * 获取accessToken,
	 * 判断如果已过有效期，则更新	
	 * @return
	 */
	public static String getJsapi() throws Exception{
		String update_time = PropertyPlaceholder.getContextProperty("ticket_update_time");
		String expires_in = PropertyPlaceholder.getContextProperty("ticket_expires_in");
		long updateTime = Long.parseLong(StringUtils.isEmpty(update_time)? "0" : update_time);
		long expiresIn = Long.parseLong(StringUtils.isEmpty(expires_in) ? "0" : expires_in);
		long currentTime = System.currentTimeMillis();
		if (currentTime > updateTime + expiresIn) {//token失效
			return updateJsapiTicket();
		}
		return PropertyPlaceholder.getContextProperty("jsapi_ticket");
	}
	/**
	 * 在线获取access_token
	 * @return
	 */
	public static String getJsapiTicketOnline() throws Exception{
		String access_token = AccessToken.getAccessToken();
		String url = String.format(ticket_url, access_token);
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
	public static synchronized String updateJsapiTicket() throws Exception{
		String updateTime = StringUtils.isEmpty(PropertyPlaceholder.getContextProperty("ticket_update_time")) ? "0" : PropertyPlaceholder.getContextProperty("ticket_update_time");

		long currentTime = System.currentTimeMillis();
		long subtract = currentTime - Long.parseLong(updateTime);
		if (subtract < 600000) {//小于10分钟，刚更新的access_token,表明是别的线程已经更新，不需再次更新
			return PropertyPlaceholder.getContextProperty("jsapi_ticket");
		}else {
			String json = getJsapiTicketOnline();
			if (json != null) {
				JSONObject object = JSONObject.parseObject(json);
				String ticket = object.getString("ticket");
				String expires_in = object.getString("expires_in");
				//修改缓存中的值
				PropertyPlaceholder.update("jsapi_ticket", ticket);
				PropertyPlaceholder.update("expires_in", expires_in);
				PropertyPlaceholder.update("ticket_update_time", currentTime + "");
				
				//修改properties文件中的值
				Properties prop = new Properties();
				FileInputStream in = new FileInputStream(new File(JsapiTicket.class.getResource("token.properties").toURI()));
				prop.load(in);
				in.close();
				prop.setProperty("jsapi_ticket", ticket);
				prop.setProperty("ticket_expires_in", expires_in);
				prop.setProperty("ticket_update_time", currentTime + "");
				FileOutputStream out = new FileOutputStream(new File(JsapiTicket.class.getResource("token.properties").toURI()));
				prop.store(out, "cc");
				out.close();
				return ticket;
			}else {
				logger.error("updateAccessToken() get a null json");
				return null;
			}
		}
	}
}
