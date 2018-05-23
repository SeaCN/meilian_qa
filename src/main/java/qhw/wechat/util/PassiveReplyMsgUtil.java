package qhw.wechat.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import qhw.wechat.constant.PassiveReplyMsgTemp;

public class PassiveReplyMsgUtil {
	
	public static void passiveReplyText(HttpServletResponse response, String to, String from, String content){
		String xml = String.format(PassiveReplyMsgTemp.PASSIVE_TEXT_MSG_REPLY_TEMPLATE, to, from, 
				System.currentTimeMillis() + "", content);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (writer != null) {
				writer.flush();
			}
		}
	}
}
