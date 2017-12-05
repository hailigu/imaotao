package com.codeReading.busi.service.article.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.article.IArticleTagDao;
import com.codeReading.busi.dal.iface.info.ITagDao;
import com.codeReading.busi.dal.model.RelationArticleTag;
import com.codeReading.busi.service.article.IArticleTagService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.util.SeqUtil;

@Service
public class ArticleTagService extends BaseService implements IArticleTagService{
	private Logger log = LoggerFactory.getLogger(ArticleTagService.class);
	
	@Autowired private ITagDao tagDao;
	@Autowired private IArticleTagDao articleTagDao;
	
	@Override
	public InnerResultData<Boolean> save(String articleid, String... tagids) throws Exception {
		log.debug("[服务]开始保存文章相关的标签 articleid={}, tagid", articleid, Arrays.toString(tagids));
		InnerResultData<Boolean> result = new InnerResultData<Boolean>();
		//TODO 处理成存储过程
		RelationArticleTag rst = new RelationArticleTag();
		rst.setArticleid(articleid);
		List<RelationArticleTag> rsts = articleTagDao.find(rst); //查询现有文章标签
		
		List<String> readyToAdd = new LinkedList<String>();
		List<String> readyToDelete = new LinkedList<String>();
		if(null != rsts && rsts.size()>0){
			//如果匹配到则不做处理；如果没有匹配到则将原有文章标签删除，将新标签写入数据库
			for(String tagid: tagids){
				boolean match = false;
				for(RelationArticleTag r: rsts){
					if(r.getTagid().equals(tagid)){
						match = true;
					}
				}
				if(!match)readyToAdd.add(tagid);
			}
			for(RelationArticleTag r: rsts){
				boolean match = false;
				for(String tagid: tagids){
					if(r.getTagid().equals(tagid)){
						match = true;
					}
				}
				if(!match)readyToDelete.add(r.getAtrelationid());
			}
		}else{
			//新增所有文章标签
			for(String tagid: tagids) readyToAdd.add(tagid);
		}
		//删除移除标签
		for(String atrelationid: readyToDelete){
			articleTagDao.delete(atrelationid);
		}
		//新增标签
		RelationArticleTag model = new RelationArticleTag();
		model.setArticleid(articleid);
		for(String tagid: readyToAdd){
			model.setAtrelationid(SeqUtil.produceRelationArticleTagid());
			model.setTagid(tagid);
			articleTagDao.insert(model);
		}
		
		//返回最终标签
		result.setData(true);
		log.debug("[服务]完成保存文章相关标签, result={}", result);
		return result;
	}

	@Override
	public InnerResultData<List<Map<String, Object>>> findByArticle(String articleid) throws Exception {
		log.debug("[服务] 开始获取文章的标签信息 articleid={}", articleid);
		InnerResultData<List<Map<String,Object>>> result = new InnerResultData<List<Map<String,Object>>>();
		
		//查询标签
		List<Map<String, Object>> data = articleTagDao.findByArticle(articleid);
		result.setData(data);
		
		//返回结果
		log.info("[服务] 完成获取文章的标签信息 result={}", result);
		return result;
	}
}
