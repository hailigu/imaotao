package com.codeReading.busi.dal.iface.source;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

import com.codeReading.busi.dal.model.Annotation;
import com.codeReading.busi.dal.model.UserAnnotation;
import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.db.QueryBean;
    
/**
  * @commons: IAnnotationDao 数据访问对象
  * @vision: 1.0.1
  */
@Repository
public interface IAnnotationDao extends BaseDao<Annotation>{
	public Annotation get(String annotationid) throws PersistenceException;
	
	/**
	 * 根据源码文件查找注释，附带一些用户信息
	 * @param fileid
	 * @return
	 * @throws PersistenceException
	 */
	public List<Map<String, Object>> findByFileWithUserInfo(String fileid) throws PersistenceException;
	
	/**
	 * @param superpath
	 * @return
	 * @throws PersistenceException
	 */
	public List<Map<String, Object>> findByDirWithUserInfo(String superpath) throws PersistenceException;

	/**
	 * 增加赞数
	 * @param params annotationid--不能为空；supports--可以为负数
	 * @throws PersistenceException
	 */
	public void addSupport(Map<String, Object> params) throws PersistenceException;
	
	/**
	 * 查找用户的注释信息
	 * @param annotationid
	 * @return
	 * @throws PersistenceException
	 */
	public List<UserAnnotation> findByUserid(String userid) throws PersistenceException;
	
	/**
	 * 分页查找注释详细，额外包括 projectname, projectpath,filepath, filename
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	public List<Map<String, Object>> findDetailByPage(QueryBean<Map<String, Object>> query)throws PersistenceException;
}