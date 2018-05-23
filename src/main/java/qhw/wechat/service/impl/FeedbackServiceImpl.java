package qhw.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qhw.wechat.dao.IFeedbackDao;
import qhw.wechat.entity.meilian.FeedbackBean;
import qhw.wechat.service.IFeedbackService;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	@Autowired
	private IFeedbackDao feedbackDao;
	@Override
	public int addFeedback(FeedbackBean feedback) {
		// TODO Auto-generated method stub
		return this.feedbackDao.addFeedback(feedback);
	}

	@Override
	public List<FeedbackBean> selectAll() {
		// TODO Auto-generated method stub
		return this.feedbackDao.selectAll();
	}

	@Override
	public List<FeedbackBean> selectByUserid(int userid) {
		// TODO Auto-generated method stub
		return this.feedbackDao.selectByUserid(userid);
	}

}
