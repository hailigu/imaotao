package com.codeReading.busi.service.annotation;

import com.codeReading.busi.po.AnnotationPO;
import com.codeReading.busi.po.AnnotationSearchPO;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.ResultData;

public interface IAnnotationService {

	/**
	 * 添加注释
	 * @param annotationpo
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData add(AnnotationPO annotationpo, String userid) throws Exception;

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public ResultData loadByFilePath(String path) throws Exception;
	
	public ResultData loadByDir(String path) throws Exception;	

	/**
	 * 注释赞踩
	 * @param userid 当前用户
	 * @param annotationid 赞踩的注释
	 * @param orientation 赞还是踩
	 * @return
	 * @throws Exception
	 */
	public ResultData addSupport(String userid, String annotationid, int orientation) throws Exception;

	/**
	 * 修改注释
	 * @param annotationpo
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData update(AnnotationPO annotationpo, String userid) throws Exception;

	/**
	 * 获取指定用户的注释列表
	 * @param userid,page
	 * @return
	 * @throws Exception
	 */
	public ResultData findUserAnnotations(String userid,PageBean page) throws Exception;

	/**
	 * 搜索注释信息
	 * @param annotationSearch 文章查询过滤信息
	 * @param page 分页信息
	 * @return 文章结果数据
	 * @throws Exception
	 */
	public ResultData findAnnotations(AnnotationSearchPO annotationSearch, PageBean page) throws Exception;

	public String downloadMine(String userid) throws Exception;
	
}
