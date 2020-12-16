package mx.com.nmp.escenariosdinamicos.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.elastic.properties.ElasticProperties;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexVentasVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;
import mx.com.nmp.escenariosdinamicos.utils.FechasComunes;

@Service
public class ElasticService {

	@Autowired
	private ElasticProperties connectionProperties;
	@Autowired
	private CastObjectGeneric castObject;
	@Autowired
	private FechasComunes formmatDate;

	private static final Logger LOG = LoggerFactory.getLogger(ElasticService.class);

	private static Date fechaActual = new Date();

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

	// scroll pc_garantias
	public List<IndexGarantiaVO> scrollElasticGarantias(String criterioWhere, String index) throws IOException {
		LOG.info("Entrando a metodo elastic");
		List<IndexGarantiaVO> lstIndexGarantia = new ArrayList<>();
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));// el seteo del intervalo
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(index);// se agrega index de elastic
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder query = QueryBuilders.queryStringQuery(criterioWhere);
		//QueryBuilder qb = QueryBuilders.boolQuery();
				//.must(QueryBuilders.termsQuery(Constantes.CUO_PARTIDA, extraerCuos(ventasUltimosDias)));
		//BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(query).filter(qb);
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(query);
		searchSourceBuilder.query(boolQuery);
		searchSourceBuilder.size(Constantes.NUMERO_MAXIMO_SCROLL);// cuantos resultados se recuperan?
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = getConnectionElastic().search(searchRequest, RequestOptions.DEFAULT); // Inicialice
																												// el
																												// contexto
																												// de
																												// búsqueda
																												// enviando
																												// el
																												// SearchRequest
																												// inicial
		String scrollId = searchResponse.getScrollId();// contexto de búsqueda que se mantiene vivo y que se necesitará
														// en la siguiente
		// llamada de desplazamiento de búsqueda
		SearchHit[] searchHits = searchResponse.getHits().getHits();// recupera el primer lote de resultados de la
																	// busqueda

		LOG.info("antes del for");
		LOG.info("tamanio {}", searchHits.length);
		for (SearchHit hit : searchHits) {
			LOG.info("entrando a bucle");
		
			// procesar los datos devueltos
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); // se crea el objeto seteandole el id
																					// del scroll e intervalo, se crea
																					// una solicitud con el ultimo id
																					// generado
			scrollRequest.scroll(scroll);
			searchResponse = getConnectionElastic().scroll(scrollRequest, RequestOptions.DEFAULT);
			scrollId = searchResponse.getScrollId();
			String response = hit.getSourceAsString();
			LOG.info(Constantes.S_LOG);
			LOG.info(response);
			lstIndexGarantia.add(castObject.jsonFieldToObject(response));
			LOG.info(Constantes.S_LOG);
		}
		LOG.info("ListaObjetosJava {}", lstIndexGarantia.size());
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); // limpia el contexto cuando se completa
		clearScrollRequest.addScrollId(scrollId);
		getConnectionElastic().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);

		return lstIndexGarantia;
	}

	// scroll ventas
	public List<IndexVentasVO> scrollElasticVentas(String index, List<String> lstCuos) throws IOException {
		LOG.info("Entrando a metodo elastic");
		List<IndexVentasVO> lstIndexGarantia = new ArrayList<>();
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.termsQuery(Constantes.COU_VENTA, lstCuos));
		RangeQueryBuilder filter = QueryBuilders.rangeQuery(Constantes.CAMPO_FECHA_INDEX)
				.gte(formmatDate.resetTimeToDown(fechaActual, Constantes.DIFERENCIA_DIAS))
				.lte(formmatDate.resetTimeToUp(fechaActual));
		searchSourceBuilder.query(filter).query(qb);

		//searchSourceBuilder.size(Constantes.NUMERO_MAXIMO_SCROLL);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = getConnectionElastic().search(searchRequest, RequestOptions.DEFAULT);
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		LOG.info("antes del for");
		LOG.info("tamanio {}", searchHits.length);
		for (SearchHit hit : searchHits) {
			LOG.info("entrando a bucle");
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
			scrollRequest.scroll(scroll);
			searchResponse = getConnectionElastic().scroll(scrollRequest, RequestOptions.DEFAULT);
			scrollId = searchResponse.getScrollId();
			String response = hit.getSourceAsString();
			LOG.info(Constantes.S_LOG);
			LOG.info(response);
			lstIndexGarantia.add(castObject.jsonFieldToObjectVenta(response));
			LOG.info(Constantes.S_LOG);
		}
		LOG.info("ListaObjetosJava {}", lstIndexGarantia.size());
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
		clearScrollRequest.addScrollId(scrollId);
		getConnectionElastic().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
		return lstIndexGarantia;
	}
}
