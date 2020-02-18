package mx.com.nmp.valormonte.elastic.service;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;



public class ElasticService {
	
private static final Logger log = LoggerFactory.getLogger(ElasticService.class);
	
	@Value("${spring.elasticsearch.host}")
	private String host;
	
	@Value("${spring.elasticsearch.port}")
	private int port;
	
	@Value("${spring.elasticsearch.scheme}")
	private String scheme;
	
	@Value("${spring.elasticsearch.username}")
	private String username;
	
	@Value("${spring.elasticsearch.password}")
	private String password;
	
	@Value("${spring.elasticsearch.typeIndex}")
	private String type;
	
	@Value("${spring.elasticsearch.timeoutElastic}")
	private String timeoutElastic;
	
	
	
	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder ();

}
