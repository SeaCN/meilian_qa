package qhw.wechat.dao;

import java.util.List;

import qhw.wechat.entity.meilian.FeedbackBean;

public interface IFeedbackDao {
	int addFeedback(FeedbackBean feedback);
	List<FeedbackBean> selectAll();
	List<FeedbackBean> selectByUserid(int userid);
}
