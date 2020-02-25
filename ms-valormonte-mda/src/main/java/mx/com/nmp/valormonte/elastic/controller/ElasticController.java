package mx.com.nmp.valormonte.elastic.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.valormonte.elastic.entity.ElasticEntity;
import mx.com.nmp.valormonte.elastic.vo.ElasticVO;
import mx.com.nmp.valormonte.model.CalculoValorMonteReqInner;
import mx.com.nmp.valormonte.utils.ConvertStringToBase64;




@RestController
public class ElasticController {
	
private static final Logger log = LoggerFactory.getLogger(ElasticController.class);
	
	@Value("${spring.elasticsearch.urlbase}")
	private String urlbase;
	
	@Value("${spring.elasticsearch.context}")
	private String service;
	
	@Value("${spring.elasticsearch.username}")
	private String username;
	
	@Value("${spring.elasticsearch.password}")
	private String password;
	
	public synchronized RestHighLevelClient getConnectionElastic() {
		RestClientBuilder builder = null;

		try {
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

			builder = RestClient.builder(new HttpHost(urlbase + service))
					.setHttpClientConfigCallback(httpClientBuilder -> {
						httpClientBuilder.disableAuthCaching();
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					});
		} catch (Exception e) {
			log.error("Error getConnectionElastic: {}", e);
		}

		return new RestHighLevelClient(builder);
		
	}
	
	public synchronized void closeConnection(RestHighLevelClient restHighLevelClient) throws IOException {
        restHighLevelClient.close();
    }
	
	public Boolean ConsultaElastic (ElasticEntity busq, RestHighLevelClient restHighLevelClient) {
		
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("q", "_id:" + busq.getSku());
	    Boolean consultado = false;
	    
		return null;
		
		
		
		
		
		
		/*Unirest.setTimeouts(0, 0);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("q", "_id:" + sku);
	    
		try {
			HttpResponse<String> response = Unirest.get(urlbase + service )
			  .header("Content-Type", "application/json")
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .queryString(map)
			  .asString();
			int statusCode = response.getStatus();

			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
	}
	
	
	
}
