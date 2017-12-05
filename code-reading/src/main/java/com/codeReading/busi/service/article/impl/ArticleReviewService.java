package com.codeReading.busi.service.article.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.article.IArticleReviewDao;
import com.codeReading.busi.dal.model.ArticleReview;
import com.codeReading.busi.dpn.enums.ArticleState;
import com.codeReading.busi.po.ArticleReviewPO;
import com.codeReading.busi.service.article.IArticleReviewService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.SeqUtil;

@Service
public class ArticleReviewService extends BaseService implements IArticleReviewService {
	private Logger log = LoggerFactory.getLogger(ArticleReviewService.class);

	@Autowired
	private IArticleReviewDao reviewDao;
	
	@Override
	public ResultData save(ArticleReviewPO reviewpo, String currentUserid) throws Exception {
		log.debug("[服务] 开始评价文章 userid={}", currentUserid);
		ResultData result = ResultData.init();
		
		if(null == reviewpo || (reviewpo.getArticleid() == null || reviewpo.getArticleid().length() <= 0) 
				|| (reviewpo.getContent() == null || reviewpo.getContent().length() <= 0 )){
			result.setRetCode("074002");
			result.setReason("评价不能为NULL");
			log.error("评价不能为NULL");
			return result;
		}else{
			ArticleReview review = new ArticleReview();
			if(null != reviewpo.getReviewid()){ //回复评价
				ArticleReview reviewtarget = reviewDao.get(reviewpo.getReviewid());
				if(null != reviewtarget){
					review.setObjectid(reviewpo.getReviewid());
				}
			}else{ //评价文章
				review.setObjectid(reviewpo.getArticleid());
			}
			review.setReviewid(SeqUtil.produceReviewid());
			review.setArticleid(reviewpo.getArticleid());
			review.setContent(reviewpo.getContent());
			review.setAuthor(currentUserid);
			review.setBusistate(ArticleState.BUSISTATE_AUDIT_PASS); //评价审核中(暂时先对评论审核通过)
			reviewDao.insert(review);
			
			result.setData("review", review);
		}
		
		//返回结果
		log.info("[服务] 完成文章评价 result={}", result);
		return result;
	}

	@Override
	public ResultData getArticleReviews(String articleid,PageBean page) throws Exception {
		log.debug("[服务] 开始获取文章的评价信息 articleid={}", articleid);
		ResultData result = ResultData.init();
		
		//查询评价信息
		List<Map<String, Object>> reviewMapList = getReviewsByPage(articleid,page).getData();
		result.setData("reviews",reviewMapList);
		result.setData("page",page);
		
		//返回结果
		log.info("[服务] 完成获取文章的评价信息 result={}", result);
		return result;
	}

	private InnerResultData<List<Map<String, Object>>> getReviewsByPage(String articleid,PageBean page) throws Exception {
		InnerResultData<List<Map<String, Object>>> result = new InnerResultData<List<Map<String, Object>>>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("articleid", articleid); //文章编号
		params.put("busistate", ArticleState.BUSISTATE_AUDIT_PASS); //审核通过的
		params.put("state", ArticleState.STATE_NORMAL); //状态正常的
		List<Map<String, Object>> reviewMapList = reviewDao.findReviewsByPage(new QueryBean<Map<String, Object>>(page,params));
		for(int i=0; i<reviewMapList.size(); i++){
			Map<String, Object> reviewMap = reviewMapList.get(i);
			String reviewid = reviewMap.get("reviewid").toString();
			String artid = reviewMap.get("articleid").toString();
			Map<String, Object> searchParam = new HashMap<String, Object>();
			searchParam.put("articleid", artid);
			searchParam.put("reviewid", reviewid);
			searchParam.put("busistate", ArticleState.BUSISTATE_AUDIT_PASS); //审核通过的
			searchParam.put("state", ArticleState.STATE_NORMAL); //状态正常的
			reviewMap.put("replys", reviewDao.findReviewReplys(searchParam));
		}
		result.setData(reviewMapList);
		return result;
	}
	
	@Override
	public InnerResultData<ArticleReview> getArticleReview(String reviewid) throws Exception {
		log.debug("[内部服务] 开始获取评价信息 reviewid={}", reviewid);
		InnerResultData<ArticleReview> result = new InnerResultData<ArticleReview>();
		ArticleReview review = reviewDao.get(reviewid);
		result.setData(review);
		log.info("[内部服务] 完成获取评价信息 review={}", review);
		return result;
	}
}
