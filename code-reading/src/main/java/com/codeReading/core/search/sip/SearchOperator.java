package com.codeReading.core.search.sip;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.support.AbstractClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchOperator {
	private Logger log = LoggerFactory.getLogger(SearchOperator.class);
	
	private AbstractClient client;
	private String index;
	
	public SearchOperator(AbstractClient client, String index) {
		this.client = client;
		this.index = index;
	}

	public Map<String, Object> getOne(String type, String id) {
		return getOne(type, id, null);
	}
	public Map<String, Object> getOne(String type, String id, String routing) {
		GetRequestBuilder request = client.prepareGet(index, type, id);
		if(null != routing) request.setRouting(routing);
		GetResponse response = request.get();
		if(log.isTraceEnabled())log.trace("Search operator [get one] exist={}, data={}", response.isExists(), response.getSource());
		if(response.isExists()){
			return response.getSource();
		}else{
			return null;
		}
	}

	public List<Map<String, Object>> getForList(String type, String[] ids) {
		long start = 0, searchEnd = 0, end = 0;
		if(log.isTraceEnabled())start = System.currentTimeMillis();
		SearchResponse response = client.prepareSearch(index)
				.setTypes(type)
				.setQuery(QueryBuilders.idsQuery(type).addIds(ids))
				.setSize(ids.length)
				.get();
		if(log.isTraceEnabled())searchEnd = System.currentTimeMillis();
		List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
		
		SearchHits hits = response.getHits();
		for(SearchHit hit: hits){
			if(log.isTraceEnabled())log.trace("Fatching data: id={}, score={}, data={}.", hit.id(), hit.getScore(), hit.getSource());
			datas.add(hit.getSource());
		}
		end = System.currentTimeMillis();
		if(log.isTraceEnabled())log.trace("Finish search for list: total hits={}, search time={}, total time={}.", hits.getTotalHits(), (searchEnd-start), (end-start));
		
		return datas;
	}

	public String getIndex() {
		return index;
	}

	public AbstractClient getClient() {
		return client;
	}
}
