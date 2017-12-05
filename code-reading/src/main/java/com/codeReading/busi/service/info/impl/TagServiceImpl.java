package com.codeReading.busi.service.info.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.info.ITagDao;
import com.codeReading.busi.dal.model.Tag;
import com.codeReading.busi.po.TagPO;
import com.codeReading.busi.service.info.ITagService;
import com.codeReading.busi.service.user.IUserService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.SeqUtil;

/**
  * @commonts: 服务接口实现
  * @vision 1.0.1
  */
@Service
public class TagServiceImpl extends BaseService implements ITagService{
	private Logger log = LoggerFactory.getLogger(TagServiceImpl.class);
	
	@Autowired
	private ITagDao tagDao;
	@Autowired
	private IUserService userService;
	@Override
	public ResultData get(String tagid) throws Exception {
		log.debug("#[服务] 开始查询标签信息 tagid=[{}]. ", tagid);
		ResultData resData = ResultData.init();
		Map<String, Object> _data = new HashMap<String, Object>();
		Tag tag = tagDao.get(tagid);
		_data.put("tag", getFieldValueMap(tag));
		resData.setData(_data);
		log.info("#[服务] 完成查询标签信息 resData=[{}]", resData);
		return resData;
	}
	
	@Override
	public ResultData filterByTagname(String tagname, int pageSize) throws Exception {
		log.debug("#[服务] 开始查询标签信息 tagname={}, pageSize={}. ", tagname, pageSize);
		ResultData result = ResultData.init();
		
		//查询
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tagname", tagname);
		param.put("pageSize", pageSize);
		List<Tag> tagList = tagDao.filterByTagname(param);
		result.setData("tags", getFieldValueList(tagList));
		
		//返回结果
		log.info("#[服务] 完成查询标签信息 result={}", result);
		return result;
	}

	@Override
	public ResultData findAll() throws Exception {
		log.debug("#[服务] 开始查找所有标签信息");
		ResultData result = ResultData.init();
		List<Tag> tags = tagDao.findAll();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("tags", getFieldValueList(tags));
		result.setData(data);
		log.info("#[服务] 完成查找所有标签信息 共查找到数据{}条", tags.size());
		return result;
	}
	
	@Override
	public InnerResultData<List<Tag>> findMany(String tagids) throws Exception {
		log.debug("#[服务] 开始查询标签集信息 tagids={}", tagids);
		InnerResultData<List<Tag>> result = new InnerResultData<List<Tag>>();
		List<Tag> tagList = tagDao.findWhereIn(tagids.split(","));
		result.setData(tagList);
		log.info("#[服务] 完成查询标签集信息 result=[{}]", result);
		return result;
	}
	
	@Override
	public ResultData add(TagPO tagpo, String currentUserid) throws Exception {
		log.debug("#[服务] 开始添加新标签信息, 操作用户:{}", currentUserid);
		ResultData result = ResultData.init();
		
		//标签内的空格用“-”替换
		String tagname = StringUtils.trim(tagpo.getTagname()).replaceAll(" +", "-");
		
		//添加标签
		Tag tag = new Tag();
		tag.setTagname(tagname);
		List<Tag> list = tagDao.find(tag);
		if(list.isEmpty()){
			tag.setTagname(tagname);
			tag.setTagid(SeqUtil.produceTagid());
			tag.setDescription(tagpo.getDescription());
			tag.setInuser(currentUserid);
			tagDao.insert(tag);
		}else{
			tag = list.get(0);
		}
		
		//返回结果
		result.setData("tag", getFieldValueMap(tag));
		log.info("#[服务] 完成添加新标签信息，result={}", result);
		return result;
	}
	
	@Override
	public ResultData delete(String tagid, String currentUserid) throws Exception {
		log.debug("#[服务] 开始删除标签信息, 操作用户:{}", currentUserid);
		ResultData result = ResultData.init();
		
		//删除标签
		Tag tag = tagDao.get(tagid);
		if(tag.getCount() == 1 && tag.getInuser().equals(currentUserid)){ //只能删除自己添加的并且别人没有使用的标签并且没有被已有服务使用
			tagDao.delete(tagid);
		}else if(tag.getCount()>1){ //去掉自己使用的标签计数(只修改usercount不修改标签历史总共使用次数totalcount)
			tagDao.decreaseUseCount(tagid);
		}
		
		//返回结果
		log.info("#[服务] 完成删除标签信息，result=[{}]", result);
		return result;
	}
	
	@Override
	public ResultData addHistoryTag(String currentUserid, String tagId) throws Exception {
		log.debug("#[服务] 添加历史标签 开始服务 userId={}, tagId={}]", currentUserid, tagId);
		ResultData result = ResultData.init();
		log.info("#[服务] 完成添加历史标签服务，result:{}", result);
		return result;
	}

	@Override
	public InnerResultData<List<Map<String, Object>>> getByExpertid(
			String expertid) throws Exception {
		InnerResultData<List<Map<String, Object>>> result = new InnerResultData<List<Map<String, Object>>>();
		List<Tag> tagList = tagDao.findTagByUser(expertid);
		result.setData(getFieldValueList(tagList));
		return result;		
	}
}
