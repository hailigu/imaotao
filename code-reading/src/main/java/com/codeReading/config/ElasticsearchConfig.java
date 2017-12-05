package com.codeReading.config;

import org.elasticsearch.client.support.AbstractClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import com.codeReading.core.search.sip.SearchOperator;

@Configuration
@PropertySource(value="classpath:elasticsearch.properties")
public class ElasticsearchConfig {
	
	@Autowired private Environment env;
	
	@Bean
	public SearchOperator searchOperator(AbstractClient client){
		return new SearchOperator(client, env.getProperty("search.index.name", "kiford"));
	}
	
	@Bean
    public AbstractClient transportClient(){
		Builder builder = ImmutableSettings.settingsBuilder()
			.put("cluster.name", env.getProperty("cluster.name", "kifordsearch"))
			.put("reuse_address", Boolean.valueOf(env.getProperty("reuse_address", "true")))
			.put("tcp_keep_alive", Boolean.valueOf(env.getProperty("tcp_keep_alive", "true")))
			.put("minSockets", Integer.valueOf(env.getProperty("minSockets", "5")));
        TransportClient client= new TransportClient(builder);
        String[] addresses = env.getRequiredProperty("addresses").split(";");
        for(String address: addresses){
        	String[] _address = address.split(":");
        	Assert.isTrue(2 == _address.length, "给定的索引配置不支持 address= "+_address);
        	client.addTransportAddress(new InetSocketTransportAddress(_address[0], Integer.parseInt(_address[1])));
        }
        return client;
    }
}
