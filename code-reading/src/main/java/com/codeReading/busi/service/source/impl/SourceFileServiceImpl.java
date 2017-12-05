package com.codeReading.busi.service.source.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.codeReading.busi.dal.iface.source.ISourceFileDao;
import com.codeReading.busi.dal.model.SourceFile;
import com.codeReading.busi.service.source.ISourceFileService;
import com.codeReading.core.framework.exception.ParameterNotAllowException;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.util.DateUtil;
import com.codeReading.core.util.SeqUtil;

@Service
public class SourceFileServiceImpl extends BaseService implements ISourceFileService {
	private Logger log = LoggerFactory.getLogger(SourceFileServiceImpl.class);
	
	@Autowired ISourceFileDao sourceFileDao;
	
	/**
	 * @see ISourceFileService.addFile
	 */
	@Override
	public InnerResultData<SourceFile> addFile(SourceFile sourceFile) throws Exception{
		log.info("### [内部服务] 开始 添加源码文件 sourceFile={}", sourceFile);
		InnerResultData<SourceFile> result = new InnerResultData<SourceFile>();
		
		if(StringUtils.isEmpty(sourceFile.getOwner()) || 
				null == sourceFile.getPath() ||
				null == sourceFile.getFilename()){
			throw new ParameterNotAllowException();
		}
		SourceFile sf = new SourceFile();
		sf.setOwner(sourceFile.getOwner());
		sf.setFilename(sourceFile.getFilename());
		sf.setPath(sourceFile.getPath());
		List<SourceFile> hasAny = sourceFileDao.find(sf);
		
		//This file already exsit, update
		if(hasAny.size() > 0){
			sourceFile.setFileid(hasAny.get(0).getFileid());
			sourceFile.setModtime(DateUtil.currentTimestamp());
			sourceFileDao.update(sourceFile);
		}else{
			//This is an new file.
			sourceFile.setFileid(SeqUtil.produceSouceFileid());
			sourceFileDao.insert(sourceFile);
		}
		
		result.setData(sourceFile);
		log.info("### [内部服务] 完成 添加源码文件 result={}", result);
		return result;
	}
	
	@Override
	public SourceFile getSourceFileByPath(String projectid, String path) throws Exception{
		log.info("### [内部服务] 开始 通过工程id和文件路径查找源码文件 projectid={}, path={}", projectid, path);
		SourceFile sf = new SourceFile();
		sf.setOwner(projectid);
		sf.setPath(path);
		List<SourceFile> files = sourceFileDao.find(sf);
		if(files.size()>0){
			sf = files.get(0);
		}else{
			sf = null;
		}
		log.info("### [内部服务] 开始 通过工程id和文件路径查找源码文件 file={}", sf);
		return sf;
	}
}
