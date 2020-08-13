package mx.com.nmp.valormonte.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.valormonte.elastic.properties.ElasticProperties;
import mx.com.nmp.valormonte.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.valormonte.utils.Constantes;
import mx.com.nmp.valormonte.utils.ConverterUtil;

@Service
public class ElasticService {
	
	@Autowired
	ElasticProperties connectionProperties;
	
	private static final Logger log = LoggerFactory.getLogger(ElasticService.class);
	
	public synchronized RestHighLevelClient getConnectionElastic() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(connectionProperties.getUser(), connectionProperties.getPassword()));

		RestClientBuilder builder = RestClient.builder(new HttpHost(connectionProperties.getHost(),
				Integer.valueOf(connectionProperties.getPort()), connectionProperties.getScheme()))
				.setHttpClientConfigCallback(httpClientBuilder -> {
					httpClientBuilder.disableAuthCaching();
					return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				});
		return new RestHighLevelClient(builder);
	}

	public synchronized void closeConnection(RestHighLevelClient restHighLevelClient) throws IOException {
		restHighLevelClient.close();
	}
	
	public List<IndexGarantiaVO> getListIndexGarantia(List<String>lstSku,List<Integer> lstPartidas) throws IOException{
		ConverterUtil caster= new ConverterUtil();
		SearchRequest searchRequest = new SearchRequest();
		RestHighLevelClient conexion=getConnectionElastic();
		searchRequest.indices(connectionProperties.getIndexGarantia());// se agrega index de elastic
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder qb = QueryBuilders.termsQuery(Constantes.INDEX_GARANTIA_PARTIDA, lstPartidas);
		QueryBuilder query = QueryBuilders.termsQuery(Constantes.INDEX_GARANTIA_SKU, lstSku);

		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(qb).filter(query);
		searchSourceBuilder.query(boolQuery);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = conexion.search(searchRequest, RequestOptions.DEFAULT); 
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		List<IndexGarantiaVO> lstIndexGarantiaVO= new ArrayList<>();
		if(searchHits.length >0) {
			for (SearchHit hit : searchHits) {
				IndexGarantiaVO garantiaVO=caster.jsonFieldToObject(hit.getSourceAsString());
				lstIndexGarantiaVO.add(garantiaVO);
				
			}
		}
		log.info("El tamaño de la lista es {}",lstIndexGarantiaVO.size());
		closeConnection(conexion);
		return lstIndexGarantiaVO;
	}

}
