package com.codeReading.busi.action.article;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.enums.PageSizeType;
import com.codeReading.busi.po.ArticlePO;
import com.codeReading.busi.po.ArticleSearchPO;
import com.codeReading.busi.service.article.IArticleService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.PageBeanUtil;

/**
 * 文章相关内容
 */
@Controller
public class ArticleAction  extends BaseAction {
	private Logger log = LoggerFactory.getLogger(ArticleAction.class);
	
	@Autowired private IArticleService articleService;
	
	/**
	 * 文章列表页
	 */
	@RequestMapping(value="articles/{id}", method=RequestMethod.GET)
	public String articles(@PathVariable String id, Map<String, Map<String, Object>> result) {
		try {
			log.info("#访问工程相关的所有文章，projectid={}",id);
			ResultData data = articleService.getProjectDetail(id);
			collect(data, result);
			return "article/articles";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	/**
	 * 开放资源，文章详情页
	 * @param id 文章编号
	 * @param result 包含文章信息、用户基本、评论信息
	 * @return
	 */
	@RequestMapping(value="a/{id}", method=RequestMethod.GET)
	public String articleShow(@PathVariable String id, Map<String, Map<String, Object>> result) {
		try {
			log.info("#访问文章详情页， articleid={}", id);
			ResultData data = articleService.getDetail(id);
			collect(data, result);
			return "article/article-detail";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	/**
	 * 开放资源，获取指定用户的文章列表
	 * @param expertid 专家编号
	 * @param result 文章列表信息
	 * @return
	 */
	@RequestMapping(value="article/get-the-user-article-list", method=RequestMethod.GET)
	public void expertArticleList(String expertid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#获取专家的文章列表， expertid={}", expertid);
			ResultData data = articleService.findTheUserArticleList(expertid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * 获取文章信息
	 * @param articleid 文章编号
	 * @param result 文章信息
	 * @return
	 */
	@RequestMapping(value="article/get", method=RequestMethod.GET)
	public void get(Principal principal, String articleid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#获取文章信息， articleid={}", articleid);
			ResultData data = articleService.getDetail(articleid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * 查找文章
	 */
	@RequestMapping(value="article/find-article",method=RequestMethod.GET)
	public void findArticle(ArticleSearchPO articleSearchpo,PageBean page,Map<String, Map<String, Object>> result){
		try {
			PageBeanUtil.initialize(page, PageSizeType.ARTICLE);
			ResultData data = articleService.findArticles(articleSearchpo, page);
			collect(data, result);
		} catch(Exception e) {
			collect(e, result);
		}
	}
	
	/**
	 * 增加文章页面浏览数
	 */
	@RequestMapping(value="article/add-pageview", method=RequestMethod.POST)
	public void addArticlePV(String articleid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#阅读文章 article={}",articleid);
			ResultData data = articleService.addArticlePV(articleid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * 开始写文章
	 */
	@RequestMapping(value="article/write", method=RequestMethod.GET)
	public String writeArticle( String projectid, HttpServletRequest hsr, Map<String, Map<String, Object>> result) {
		try {
			log.info("#开始写文章， userid={}",hsr.getSession().getAttribute("userid").toString());
			ResultData data = articleService.getProjectDetail(projectid);
			collect(data, result);			
			return "article/writeArticle";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	/**
	 * 我的文章
	 */
	@RequestMapping(value="my-articles", method=RequestMethod.GET)
	public String myArticles(HttpServletRequest hsr, Map<String, Map<String, Object>> result) {
		try {
			log.info("#我的文章， userid={}",hsr.getSession().getAttribute("userid").toString());
			return "usercenter/my-articles";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	/**
	 * 获取我的文章列表
	 */
	@RequestMapping(value="article/find-myarticles", method=RequestMethod.GET)
	public void findMyArticles(HttpServletRequest htr, PageBean page,Map<String, Map<String, Object>> result) {
		try {
			PageBeanUtil.initialize(page, PageSizeType.ARTICLE);
			
			
			ResultData data = articleService.findMyArticles(htr.getSession().getAttribute("userid").toString(), page);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	
	/**
	 * 获取指定用户的文章列表
	 */
	@RequestMapping(value="article/find-theuserarticles", method=RequestMethod.POST)
	public void findTheUserArticles(String userid, PageBean page,Map<String, Map<String, Object>> result) {
		try {
			PageBeanUtil.initialize(page, PageSizeType.ARTICLE);
			ResultData data = articleService.findMyArticles(userid, page);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * 保存文章
	 */
	@RequestMapping(value="article/save", method=RequestMethod.POST)
	public void saveArticle(HttpServletRequest hsr, ArticlePO articlepo, Map<String, Map<String, Object>> result) {
		try {
			log.info("#保存文章，userid={}",hsr.getSession().getAttribute("userid").toString());
			ResultData data = articleService.save(articlepo,hsr.getSession().getAttribute("userid").toString());
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * 发表文章
	 */
	@RequestMapping(value="article/publish", method=RequestMethod.POST)
	public void publishArticle(HttpServletRequest hsr, ArticlePO articlepo, Map<String, Map<String, Object>> result) {
		try {
			log.info("#发表文章，userid={}",hsr.getSession().getAttribute("userid").toString());
			ResultData data = articleService.publish(articlepo, hsr.getSession().getAttribute("userid").toString());
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * 删除文章
	 */
	@RequestMapping(value="article/delete", method=RequestMethod.POST)
	public void deleteArticle(HttpServletRequest hsr, String articleid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#删除文章，userid={}，aricleid={}",hsr.getSession().getAttribute("userid").toString(),articleid);
			ResultData data = articleService.delete(hsr.getSession().getAttribute("userid").toString(),articleid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * 点赞
	 */
	@RequestMapping(value="article/support-article", method=RequestMethod.POST)
	public void supportArticle(HttpServletRequest  hsr, String articleid, Integer optcnt,Map<String, Map<String, Object>> result) {
		try {
			log.info("#文章点赞 userid={}，article={}",hsr.getSession().getAttribute("userid").toString(),articleid);
			ResultData data = articleService.addArticleSP(hsr.getSession().getAttribute("userid").toString(),articleid, optcnt);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
}
