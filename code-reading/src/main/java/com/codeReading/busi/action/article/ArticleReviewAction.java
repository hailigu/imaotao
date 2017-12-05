package com.codeReading.busi.action.article;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.enums.PageSizeType;
import com.codeReading.busi.po.ArticleReviewPO;
import com.codeReading.busi.service.article.IArticleReviewService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.PageBeanUtil;

/**
 * 文章评价
 */
@Controller
public class ArticleReviewAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(ArticleReviewAction.class);
	
	@Autowired
	private IArticleReviewService articleReviewService;
	
	/**
	 * 保存文章评价
	 * @param reviewpo 评价信息
	 * @param principal
	 * @param result 返回值(评价结果信息)
	 */
	@RequestMapping(value="article/save-review", method={RequestMethod.POST})
	public void save(ArticleReviewPO reviewpo,HttpServletRequest hsr, Map<String, Map<String, Object>> result){
		try {
			log.info("#开始评价文章， articleReview=[{}].", reviewpo);
			ResultData data = articleReviewService.save(reviewpo, hsr.getSession().getAttribute("userid").toString());
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	/**
	 * 获取文章的评价信息
	 * @param articleid 文章编号
	 * @param result 返回值(文章的评价结果信息)
	 */
	@RequestMapping(value="article/get-reviews", method={RequestMethod.GET})
	public void get(String articleid, PageBean page, Map<String, Map<String, Object>> result){
		try {
			PageBeanUtil.initialize(page, PageSizeType.DEFAULT);
			log.info("#开始获取文章的评价信息， articleid={}", articleid);
			ResultData data = articleReviewService.getArticleReviews(articleid,page);
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
}
