package com.codeReading.busi.service.article.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codeReading.busi.dal.iface.article.IArticleDao;
import com.codeReading.busi.dal.iface.article.IArticleReviewDao;
import com.codeReading.busi.dal.iface.article.IArticleTagDao;
import com.codeReading.busi.dal.iface.info.ITagDao;
import com.codeReading.busi.dal.iface.source.ISourceProjectDao;
import com.codeReading.busi.dal.iface.source.ISupportDao;
import com.codeReading.busi.dal.iface.user.IUserDao;
import com.codeReading.busi.dal.model.Article;
import com.codeReading.busi.dal.model.ArticleReview;
import com.codeReading.busi.dal.model.RelationArticleTag;
import com.codeReading.busi.dal.model.SourceProject;
import com.codeReading.busi.dal.model.Support;
import com.codeReading.busi.dal.model.Tag;
import com.codeReading.busi.dal.model.User;
import com.codeReading.busi.dpn.enums.ArticleState;
import com.codeReading.busi.dpn.enums.PageSizeType;
import com.codeReading.busi.dpn.enums.SupportTargetType;
import com.codeReading.busi.dpn.exception.user.UserPermissionDeniedException;
import com.codeReading.busi.po.ArticlePO;
import com.codeReading.busi.po.ArticleSearchPO;
import com.codeReading.busi.service.article.IArticleService;
import com.codeReading.busi.service.article.IArticleTagService;
import com.codeReading.busi.service.source.ISourceWatchService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.exception.ParameterNotAllowException;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.DateUtil;
import com.codeReading.core.util.PageBeanUtil;
import com.codeReading.core.util.SeqUtil;

@Component
public class ArticleService extends BaseService implements IArticleService{
	private Logger log = LoggerFactory.getLogger(ArticleService.class);
	
	@Autowired 
	private IUserDao userDao;
	@Autowired
	private IArticleDao articleDao;
	@Autowired
	private IArticleReviewDao reviewDao;
	@Autowired
	private ITagDao tagDao;
	@Autowired
	private IArticleTagDao articleTagDao;
	@Autowired
	private IArticleTagService articleTagService;
	@Autowired
	private ISupportDao supportDao;
	@Autowired
	private ISourceProjectDao sourceProjectDao;
	@Autowired 
	private ISourceWatchService sourceWatchService;
	
	@Override
	public ResultData findArticles(ArticleSearchPO articleSearch, PageBean page) throws Exception {
		log.debug("[服务] 开始搜索文章信息 article={}, page={}", articleSearch, page);
		
		ResultData result = ResultData.init();
		page.setOrderKey("weight");
		page.setAscend("desc");
		List<Article> articleList =null;
		List<Map<String, Object>> articleSearchList = new LinkedList<Map<String, Object>>();
		if(articleSearch.getTag() != null && articleSearch.getTag() != ""){ //按标签搜索
			Tag tag = new Tag();
			tag.setTagname(articleSearch.getTag());
			List<Tag> tagList = tagDao.find(tag);
			if(tagList.size() > 0){
				articleList = new LinkedList<Article>();
				Tag searchtag = tagList.get(0);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("tagid", searchtag.getTagid());
				List<RelationArticleTag> articleTagList = articleTagDao.findArticleTagByPage(new QueryBean<Map<String, Object>>(page, params));
				for(int i=0; i<articleTagList.size(); i++){
					RelationArticleTag ratag = articleTagList.get(i);
					articleList.add(articleDao.get(ratag.getArticleid()));
				}
			}
		}else if(articleSearch.getKeywords() != null && !"".equals(articleSearch.getKeywords())){ //按关键字搜索
			articleList = new LinkedList<Article>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("keywords", articleSearch.getKeywords());
			articleList = articleDao.findArticleByPage(new QueryBean<Map<String, Object>>(page,params));
		}else if (articleSearch.getProjectid() != null &&  !"".equals(articleSearch.getProjectid())) {
			articleList = new LinkedList<Article>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projectid", articleSearch.getProjectid());
			articleList = articleDao.findProjectArticlesByPage(new QueryBean<Map<String, Object>>(page,params));
		}else{ //查询所有
			articleList = articleDao.findAllArticleByPage(new QueryBean<Map<String, Object>>(page, null));
		}
		
		if(null != articleList){
			//组织数据
			for(int j=0; j<articleList.size(); j++){
				//文章信息
				Article articletemp = articleList.get(j);
				Map<String, Object> _article = getFieldValueMap(articletemp);
				
				//文章评论数
				ArticleReview review = new ArticleReview();
				review.setArticleid(articletemp.getArticleid());
				review.setBusistate(ArticleState.BUSISTATE_AUDIT_PASS);
				review.setState(ArticleState.STATE_NORMAL);
				_article.put("reviewcnt", reviewDao.count(review));
				
				//用户信息
				User user = userDao.get(articletemp.getUserid());
				_article.put("user", user);
				
				//标签信息
				List<Map<String, Object>> articleTagMapList =  articleTagDao.findByArticle(articletemp.getArticleid());
				_article.put("tags", articleTagMapList);
				
				articleSearchList.add(_article);
			}
			log.debug("通过搜索引擎获取搜索文章信息，结果{}条，page={}", articleList.size(), page);
		}
		
		result.setData("articles", articleSearchList);
		result.setData("page", page);
		
		log.info("[服务] 完成搜索文章信息  result={}", result);
		return result;
	}
	
	@Override
	public ResultData getDetail(String articleid) throws Exception {
		ResultData result = ResultData.init();
		
		//文章信息
		Article article = articleDao.get(articleid);
		result.setData("article",article);
		
		//用户信息
		User user = userDao.get(article.getUserid());
		result.setData("user", user);
		
		//标签信息
		List<Map<String, Object>> articleTagMapList =  articleTagDao.findByArticle(articleid);
		result.setData("tags", articleTagMapList);
		
		//评论数
		ArticleReview review = new ArticleReview();
		review.setArticleid(articleid);
		review.setBusistate(ArticleState.BUSISTATE_AUDIT_PASS);
		review.setState(ArticleState.STATE_NORMAL);
		result.setData("reviewcnt", reviewDao.count(review));
		
		//此源码工程的其它文章列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("articleid", articleid);
		params.put("projectid", article.getProjectid());
		List<Article> otherArticles = articleDao.findOtherArticles(params);
		result.setData("others",otherArticles);
		
		//文章所属源码工程信息
		SourceProject sourceProject  = sourceProjectDao.get(article.getProjectid());
		result.setData("sourceproject", sourceProject);
			
		return result;
	}
	
	@Override
	public ResultData save(ArticlePO articlepo, String userid) throws Exception {
		log.debug("[服务] 开始保存文章{}",userid);
		
		ResultData result = ResultData.init();
		if(articlepo == null){
			result.setReason("文章内容为空");
			result.setRetCode("074001");
			return result;
		}
		
		Article article = null;
		if(articlepo.getArticleid() != null && articlepo.getArticleid().length() > 0){
			article = articleDao.get(articlepo.getArticleid());
			if(article != null){
				article.setTitle(articlepo.getTitle());
				article.setSummary(articlepo.getSummary());
				article.setContent(articlepo.getContent());
				article.setModtime(DateUtil.currentTimestamp());
				articleDao.update(article);
				saveArticleTag(article.getArticleid(),articlepo.getTagids());
			}else{
				throw new UserPermissionDeniedException("用户保存的文章不存在");
			}
		}else{
			article = new Article();
			article.setArticleid(SeqUtil.produceArticleid()); //文章编号
			article.setProjectid(articlepo.getProjectid());
			article.setUserid(userid);
			article.setTitle(articlepo.getTitle());
			article.setSummary(articlepo.getSummary());
			article.setContent(articlepo.getContent());
			articleDao.insert(article);
			saveArticleTag(article.getArticleid(),articlepo.getTagids());
		}
		
		/******************** 组织返回的数据 **********************/
		result.setData("article", article);
		
		log.info("[服务] 文章保存完成 result={}", result);
		return result;
	}

	/**
	 * 保存文章标签关联信息
	 * @param aiticleid 服务编号
	 * @param tagids 标签集
	 * @return List<Map<String,Object>>
	 */
	private boolean saveArticleTag(String articleid, String tagids) throws Exception {
		if (null != tagids && tagids.length() > 0) {
			String[] tagidarr = tagids.split(",");
			return articleTagService.save(articleid, tagidarr).getData();
		} else {
			return false;
		}
	}
	
	@Override
	public ResultData publish(ArticlePO articlepo, String userid) throws Exception {
		log.debug("[服务] 开始发表文章{}",userid);
		
		ResultData result = ResultData.init();
		if(articlepo == null){
			result.setReason("文章内容为空");
			result.setRetCode("074001");
			return result;
		}
		
		Article article = null;
		if(articlepo.getArticleid() != null && articlepo.getArticleid().length() > 0){
			article = articleDao.get(articlepo.getArticleid());
			if(article != null){
				if (!article.getUserid().equals(userid)) {//防止别人利用url参数更改别人的文章
					result.setReason("非本人文章，不允许更改");
					result.setRetCode("074001");
					return result;
				}else {
					article.setTitle(articlepo.getTitle());
					article.setProjectid(articlepo.getProjectid());
					article.setSummary(articlepo.getSummary());
					article.setContent(articlepo.getContent());
					article.setBusistate(ArticleState.BUSISTATE_AUDIT_PASS); 
					article.setModtime(DateUtil.currentTimestamp());
					articleDao.update(article);
					saveArticleTag(article.getArticleid(),articlepo.getTagids());
				}
			}else{
				throw new UserPermissionDeniedException("用户保存的文章不存在");
			}
		}else{
			article = new Article();
			article.setArticleid(SeqUtil.produceArticleid()); //文章编号
			article.setProjectid(articlepo.getProjectid());
			article.setUserid(userid);
			article.setTitle(articlepo.getTitle());
			article.setSummary(articlepo.getSummary());
			article.setContent(articlepo.getContent());
			article.setBusistate(ArticleState.BUSISTATE_AUDIT_PASS); // 审核中
			article.setPublishtime(DateUtil.currentTimestamp());
			articleDao.insert(article);
			saveArticleTag(article.getArticleid(),articlepo.getTagids());
			
			//关注源码工程 
			sourceWatchService.addWatch(articlepo.getProjectid(), userid);
		}
		
		/******************** 组织返回的数据 **********************/
		result.setData("article", article);
		
		log.info("[服务] 文章发表完成 result={}", result);
		return result;
	}

	@Override
	public ResultData addArticlePV(String articleid) throws Exception {
		log.debug("[服务] 开始增加文章页面浏览数{}",articleid);
		ResultData result = ResultData.init();
		Article articleorg = articleDao.get(articleid);
		if(articleorg != null){
			Article articlenew = new Article();
			articlenew.setArticleid(articleid);
			articlenew.setPageview(articleorg.getPageview()+1);
			articleDao.update(articlenew);
			result.setData("PV", articlenew.getPageview());
		}
		log.info("[服务] 文章页面浏览数增加完成 result={}", result);
		return result;
	}

	@Override
	public ResultData findMyArticles(String userid, PageBean page) throws Exception {
		log.debug("[服务] 开始获取我的文章列表 userid={}, page={}", userid, page);
		ResultData result = ResultData.init();
		page.setOrderKey("intime");
		page.setAscend("desc");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		List<Article> articleList = articleDao.findMyArticleByPage(new QueryBean<Map<String, Object>>(page,params));
		result.setData("articlelist",articleList);
		result.setData("page", page);
		log.info("[服务] 完成获取我的文章列表  result={}", result);
		return result;
	}

	@Override
	public ResultData findTheUserArticleList(String expertid) throws Exception {
		log.debug("[服务] 开始获取专家的文章列表 expertid={}", expertid);
		ResultData result = ResultData.init();
		List<Map<String, Object>> expertArticleList = new LinkedList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", expertid);
		params.put("busistate", ArticleState.BUSISTATE_AUDIT_PASS);
		params.put("state", ArticleState.STATE_NORMAL);
		List<Article> articleList = articleDao.findTheUserArticleList(expertid);
		//组织数据
		for(int j=0; j<articleList.size(); j++){
			//文章信息
			Article articletemp = articleList.get(j);
			Map<String, Object> _article = getFieldValueMap(articletemp);
			
			//文章评论数
			ArticleReview review = new ArticleReview();
			review.setArticleid(articletemp.getArticleid());
			review.setBusistate(ArticleState.BUSISTATE_AUDIT_PASS);
			review.setState(ArticleState.STATE_NORMAL);
			_article.put("reviewcnt", reviewDao.count(review));
			
			//标签信息
			List<Map<String, Object>> articleTagMapList =  articleTagDao.findByArticle(articletemp.getArticleid());
			_article.put("tags", articleTagMapList);
			
			expertArticleList.add(_article);
		}
		result.setData("articlelist",expertArticleList);
		log.info("[服务] 完成获取专家的文章列表  result={}", result);
		return result;
	}
	
	@Override
	public ResultData delete(String userid, String articleid) throws Exception {
		log.debug("[服务] 开始删除文章{}",articleid);
		ResultData result = ResultData.init();
		Article targetArticle = articleDao.get(articleid);
		if(null != targetArticle){
			Article article = new Article();
			article.setArticleid(articleid);
			article.setUserid(userid);
			article.setState(ArticleState.STATE_DELETE);
			articleDao.update(article);
		}
		log.info("[服务] 删除文章完成 result={}", result);
		return result;
	}

	@Override
	public ResultData addArticleSP(String userid,String articleid, Integer opt)
			throws Exception {
		log.debug("[服务] 点赞操作",articleid);
		ResultData result = ResultData.init();
		
		//获取点赞信息记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("targetid", articleid);
		Support supportorg = supportDao.getByUserAndTarget(params);
		
		Article articleorg = articleDao.get(articleid);
		
		if (supportorg != null) {
			Integer orientation = supportorg.getOrientation();
			if (!orientation.equals(opt)) {
				
				Support supportupdate = new Support();
				supportupdate.setSupportid(supportorg.getSupportid());
				supportupdate.setOrientation(opt);
				supportDao.update(supportupdate);
				
				Article articleupdate = new Article();
				articleupdate.setArticleid(articleid);
				Integer supportadd = 0;
				if (opt > 0) {
					supportadd= 2;
				}else {
					supportadd = -2;
				}
				articleupdate.setSupport(articleorg.getSupport() + supportadd);
				articleDao.update(articleupdate);
				result.setData("SPCount", articleupdate.getSupport());
			}
			
			
		}else {//首次点赞操作，记录文章表和点赞记录表
			if(articleorg != null){
				Article articlenew = new Article();
				articlenew.setArticleid(articleid);
				articlenew.setSupport(articleorg.getSupport()+opt);
				articleDao.update(articlenew);
				result.setData("SPCount", articlenew.getSupport());
			}
			
			Support supportnew = new Support();
			supportnew.setSupportid(SeqUtil.produceSupportid());
			supportnew.setUserid(userid);
			supportnew.setTargetid(articleid);
			supportnew.setTargettype(SupportTargetType.ARTICLE.getType());
			supportnew.setOrientation(opt);
			supportDao.insert(supportnew);
		}
		result.setData("spResult",opt);
		log.info("[服务] 文章页面点赞数增加/减少完成 result={}", result);
		return result;
	}

	@Override
	public ResultData getProjectDetail(String projectid) throws Exception {
		log.debug("访问文章列表",projectid);
		ResultData result = ResultData.init();
		SourceProject sourceProject  = sourceProjectDao.get(projectid);
		result.setData("sourceproject", sourceProject);
		log.info("访问文章列表，返回所属工程信息 result={}", result);
		return result;
	}
	
	@Override
	public InnerResultData<List<Map<String, Object>>> findDetailByPage(Article article, PageBean page) throws Exception {
		log.debug("###[内部服务] 开始 分页查询文章article={}, PageBean={}",article, page);
		InnerResultData<List<Map<String, Object>>> result = new InnerResultData<List<Map<String, Object>>>();
		if(null == article){
			throw new ParameterNotAllowException();
		}
		if(null==page){
			page = new PageBean();
			PageBeanUtil.initialize(page, PageSizeType.ARTICLE);
			page.setCurrentPage(1);
			page.setAscend("DESC");
			page.setOrderKey("intime");
		}
		List<Article> articles = articleDao.findByPage(new QueryBean<Article>(page, article));
		List<Map<String, Object>> articlesMap = getFieldValueList(articles);
		for(Map<String, Object> item : articlesMap){
			User user = userDao.get((String)item.get("userid"));
			user.setPassword("");
			item.put("user", user);
		}
		result.setData(articlesMap);
		
		log.debug("###[内部服务] 完成 分页查询文章result={}", result);
		return result;
	}
}
