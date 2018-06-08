package qhw.wechat.service;

import java.util.List;
import java.util.Map;

import qhw.wechat.entity.meten.FeedbackBean;

public interface IFeedbackService {
	
	/**
	 * 添加回复
	 * @param feedback
	 * @return
	 */
	int addFeedback(FeedbackBean feedback);
	
	/**
	 * 查找所有回复记录
	 * @return
	 */
	List<FeedbackBean> selectAll();
	
	/**
	 * 根据用户ID查找回复记录
	 * @param userid
	 * @return
	 */
	List<FeedbackBean> selectByUserid(int userid);
	
	int selectTotolNum(Map<String, Object> params);
	List<Map<String, Object>> selectByPage(Map<String, Object> params);
}
