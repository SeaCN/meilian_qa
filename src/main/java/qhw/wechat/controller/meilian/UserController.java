package qhw.wechat.controller.meilian;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import qhw.wechat.constant.MessageConst;
import qhw.wechat.entity.meilian.UserBean;
import qhw.wechat.service.IUserService;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUserService userService;
	
	/**
	 * 用户注册	
	 * @param request
	 * @param response
	 * @param openid
	 * @param nickname
	 */
	@RequestMapping(value = "/regist")
	@ResponseBody
	public Map<String, Object> regist(HttpServletRequest request, HttpServletResponse response,
			@RequestBody UserBean user){
		int effect = 0;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			effect = userService.addUser(user);
		} catch (Exception e) {
			logger.info("occur error when regist()", e);
			result.put(MessageConst.MSG_CODE, MessageConst.MSG_FAIL_STATUS);
			result.put(MessageConst.MSG_MESSAGE, MessageConst.MSG_ERROR);
			return result;
		}
		if (effect > 0) {
			result.put(MessageConst.MSG_CODE, MessageConst.MSG_SUCCESS_STATUS);
			result.put(MessageConst.MSG_MESSAGE, MessageConst.MSG_SUCCESS_REGIST);
		}else {
			result.put(MessageConst.MSG_CODE, MessageConst.MSG_FAIL_STATUS);
			result.put(MessageConst.MSG_MESSAGE, MessageConst.MSG_FAIL_REGIST);
		}
		return result;
	}
}
