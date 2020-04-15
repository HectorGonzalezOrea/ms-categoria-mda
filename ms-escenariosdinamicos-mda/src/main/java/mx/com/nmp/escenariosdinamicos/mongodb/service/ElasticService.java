package mx.com.nmp.escenariosdinamicos.mongodb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.elastic.properties.ElasticProperties;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
@Service
public class ElasticService {
	
	@Autowired
	private ElasticProperties connectionProperties;
	//@Autowired
	//private QueryBuilder qbm;
	//indices a usar pc_mda_ventas_midas_dev_tmp,pc_garantias
	
	@Autowired
	private CastObjectGeneric castObject;
	 public synchronized RestHighLevelClient getConnectionElastic() {
	        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	        credentialsProvider.setCredentials(AuthScope.ANY,
	                new UsernamePasswordCredentials(connectionProperties.getUser(), connectionProperties.getPassword()));
	        
	        RestClientBuilder builder = RestClient.builder(new HttpHost(connectionProperties.getHost(), 
	        		Integer.valueOf(connectionProperties.getPort()), connectionProperties.getScheme())).setHttpClientConfigCallback(
	        				httpClientBuilder -> { 
	        					httpClientBuilder.disableAuthCaching(); 
	        					return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
	        				});
	        return new RestHighLevelClient(builder);
	    }
	    
	    public synchronized void closeConnection(RestHighLevelClient restHighLevelClient) throws IOException {
	        restHighLevelClient.close();
	        restHighLevelClient = null;
	    }
	
	public List<IndexGarantiaVO> scrollElastic(String index) throws IOException{
		System.out.println("Entrando a metodo elastic");
		List<IndexGarantiaVO>lstIndexGarantia=new ArrayList<>();
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));//el seteo del intervalo
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(index);//se agrega index de elastic
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(2);//cuantos resultados se recuperan?
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = getConnectionElastic().search(searchRequest, RequestOptions.DEFAULT); //Inicialice el contexto de búsqueda enviando el SearchRequest inicial
		String scrollId = searchResponse.getScrollId();//contexto de búsqueda que se mantiene vivo y que se necesitará en la siguiente
		//llamada de desplazamiento de búsqueda
		SearchHit[] searchHits = searchResponse.getHits().getHits();//recupera el primer lote de resultados de la busqueda
		//tratar de cambiar esta implementacion para poder extraer el contenido de cada documento
		//while (searchHits != null && searchHits.length > 0) { //creo que aqui se tiene que agregar que sea 100
			//se recuperan todos los resultados hasta que ya no se devuelvan docs
		System.out.println(searchHits.length);
			for(SearchHit hit : searchHits){
				System.out.println("entrando a bucle");
			//}
			//procesar los datos devueltos
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); //se crea el objeto seteandole el id del scroll e intervalo, se crea una solicitud con el ultimo id generado
		    scrollRequest.scroll(scroll);
		    searchResponse = getConnectionElastic().scroll(scrollRequest, RequestOptions.DEFAULT);
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();//se recupera otro grupode resultados
		    String response = hit.getSourceAsString();
		    System.out.println("*********************");
		    System.out.println(response);
		    lstIndexGarantia.add(castObject.JsonFieldToObject(response));
		    System.out.println("*********************");
		}
		System.out.println("ListaObjetosJava "+lstIndexGarantia.size());
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); //limpia el contexto cuando se completa
		clearScrollRequest.addScrollId(scrollId);
		ClearScrollResponse clearScrollResponse = getConnectionElastic().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
		//boolean succeeded = clearScrollResponse.isSucceeded();
		return lstIndexGarantia;
	}
}
