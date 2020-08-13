package mx.com.nmp.historicoprecios.elastic.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.nmp.historicoprecios.elastic.vo.HistoricoPreciosVO;

@Service
public class ElasticService {

	private static final Logger log = LoggerFactory.getLogger(ElasticService.class);
	
	@Value("${spring.elasticsearch.host}")
	private String host;
	
	@Value("${spring.elasticsearch.port}")
	private String port;
	
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
	
	public synchronized RestHighLevelClient getConnectionElastic() {

		RestClientBuilder builder = null;

		try {
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

			builder = RestClient.builder(new HttpHost(host, Integer.valueOf(port), scheme))
					.setHttpClientConfigCallback(httpClientBuilder -> {
						httpClientBuilder.disableAuthCaching();
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					});
		} catch (Exception e) {
			log.error("Error getConnectionElastic: {0}", e);
		}

		return new RestHighLevelClient(builder);
	}
	
	public synchronized void closeConnection(RestHighLevelClient restHighLevelClient) throws IOException {
        restHighLevelClient.close();
    }
	
	
	
	@Retryable(value={IOException.class,Exception.class}, maxAttempts=3 ,backoff=@Backoff( delay=3000))
	public Boolean insertHistoricoPreciosVO(HistoricoPreciosVO documento, String indice, RestHighLevelClient restHighLevelClient) throws IOException {
		log.info("ElasticService.insertHistoricoPreciosVO");
		
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> dataMap = oMapper.convertValue(documento, Map.class);
		Map<String, Object> dataMapFinal = new HashMap<>();
		Boolean insertado = false;
		
		for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			if (entry.getValue() != null) {
				dataMapFinal.put(entry.getKey(), entry.getValue());
			}
		}

		IndexRequest indexRequest = new IndexRequest(indice, type).source(dataMapFinal).timeout(timeoutElastic);
		try {
			restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
			log.info("Producto Insertado");
			insertado = true;
		}catch (ElasticsearchException ee) {
			log.error("Ocurrio un error el elasticsearch {0}", ee);
		}
		return insertado;
	}
}
