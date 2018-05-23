package qhw.wechat.controller.meilian;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qhw.wechat.constant.MessageConst;
import qhw.wechat.entity.meilian.FeedbackBean;
import qhw.wechat.service.IFeedbackService;
import qhw.wechat.service.ITemplateMsgService;
import qhw.wechat.util.Result;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
	@Autowired
	private IFeedbackService feedbackService;
	@Resource
	private ITemplateMsgService templateServiceImpl;
	/**
	 * 回复建议	
	 * @param request
	 * @param response
	 * @param feedback
	 * @return
	 */
	@RequestMapping(value = "/addFeedback")
	@ResponseBody
	public Result addFeedback(HttpServletRequest request, HttpServletResponse response,
			@RequestBody FeedbackBean feedback){
		if (feedback.getUserid() == null || feedback.getSuggestionid() == null) {
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		int effect = 0;
		try {
			effect = this.feedbackService.addFeedback(feedback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("occur error when addSuggestion()", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		if (effect > 0) {
			return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT);
		}
		return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_FAIL_SUBMIT);
	}
	
	/**
	 * 查询自己所有的回复信息
	 * @param request
	 * @param response
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/selectByUserid")
	@ResponseBody
	public Result selectByUserid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer userid){
		List<FeedbackBean> list = null;
		try {
			list = this.feedbackService.selectByUserid(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("occur error when addSuggestion()", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		if (list == null) {
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_FAIL_SUBMIT);
		}
		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, list);
	}
	
}
