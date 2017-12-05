package com.codeReading.busi.dpn.search;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codeReading.busi.dpn.exception.OperationNotSupportException;
import com.codeReading.busi.dpn.exception.search.SearchOperationRuntimeException;
import com.codeReading.busi.dpn.search.repository.UserRepository;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.exception.AbsBusiException;
import com.codeReading.core.framework.exception.ParameterNotEnoughException;
import com.codeReading.core.search.sip.SearchHelper;

@Component
public class UserSearchHelper extends SearchHelper<UserRepository> {
	private Logger log = LoggerFactory.getLogger(UserSearchHelper.class);
	
	private final String type = "user";
	
	@Override
	public boolean add(UserRepository u) throws AbsBusiException {
		if(log.isTraceEnabled())log.trace("索引新增用户, user={}", u);
		if(u.canIndex()){
			try {
				IndexRequest request = new IndexRequest(getIndex(), getSearchType(), u.getUserid())
						.source(u.toSourceBuilder());
				
				if(log.isTraceEnabled())log.trace("Index user repository, request={}", request.toString());
				
				IndexResponse response = client().index(request).get();
				
				if(response.isCreated()){
					//create
					if(log.isTraceEnabled())log.trace("The user [{}:{}] created immediatly.", u.getUserid(), u.getNickname());
				}else{
					//update
					if(log.isTraceEnabled())log.trace("The user [{}:{}] updated with the new source.", u.getUserid(), u.getNickname());
				}
				
				return true;
			} catch(Exception e) {
				log.warn("索引新增用户操作异常:{}", e.getMessage());
				throw new SearchOperationRuntimeException();
			}
			
		}else{
			throw new ParameterNotEnoughException("索引新增用户过程参数不足");
		}
	}
	
	@Override
	public boolean add(List<UserRepository> ms, boolean throwError) throws AbsBusiException {
		try {
			if(ms.isEmpty()){
				if(log.isTraceEnabled())log.trace("Ignore empty user repository list to index.");
				return true;
			}
			BulkRequestBuilder bulk = client().prepareBulk();
			for(UserRepository u: ms){
				if(u.canIndex()){
					IndexRequest request = new IndexRequest(getIndex(), getSearchType(), u.getUserid())
							.source(u.toSourceBuilder());
					if(log.isTraceEnabled())log.trace("Index user repository, request={}", request.toString());
					bulk.add(request);
				}else{
					if(throwError)throw new ParameterNotEnoughException("索引新增用户过程参数不足");
				}
			}
			BulkResponse response = bulk.get();
			if(log.isTraceEnabled())log.trace("Create user index total={}, hasFail={}", response.getItems().length, response.hasFailures());
			return true;
		} catch (Exception e) {
			log.warn("索引新增用户操作异常:{}", e.getMessage());
			throw new SearchOperationRuntimeException();
		}
	}
	
	@Override
	public boolean update(UserRepository u) throws AbsBusiException {
		if(log.isTraceEnabled())log.trace("索引修改用户, user={}", u);
		if(u.canUpdate()){
			try {
				UpdateRequest request = new UpdateRequest(getIndex(), getSearchType(), u.getUserid())
					.doc(u.toSourceBuilder());
				
				client().update(request).get();
				return true;
			} catch(Exception e) {
				log.warn("索引修改用户操作异常:{}", e.getMessage());
				throw new SearchOperationRuntimeException();
			}
		}else{
			throw new ParameterNotEnoughException("索引修改用户过程参数不足");
		}
	}
	
	@Override
	public boolean upsert(UserRepository u) throws AbsBusiException {
		if(log.isTraceEnabled())log.trace("索引修改或新增用户, user={}", u);
		if(u.canUpdate()){
			try {
				XContentBuilder doc = u.toSourceBuilder();
				UpdateRequest request = new UpdateRequest(getIndex(), getSearchType(), u.getUserid())
					.doc(doc)
					.upsert(doc);
				
				UpdateResponse response = client().update(request).get();
				if(log.isTraceEnabled())log.trace("索引修改或新增用户完成，用户是否新创建 isCreated={}.", response.isCreated());
				return true;
			} catch(Exception e) {
				log.warn("索引修改或新增用户操作异常:{}", e.getMessage());
				throw new SearchOperationRuntimeException();
			}
		}else{
			throw new ParameterNotEnoughException("索引修改或新增用户过程参数不足");
		}
	}
	
	@Override
	public List<Map<String, Object>> search(UserRepository m, PageBean page) throws AbsBusiException {
		throw new OperationNotSupportException();
	}
	
	@Override
	public String getSearchType() {
		return type;
	}
}
