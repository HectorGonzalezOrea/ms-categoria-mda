package mx.com.nmp.escenariosdinamicos.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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
import mx.com.nmp.escenariosdinamicos.elastic.vo.MdaVentasVO;
import mx.com.nmp.escenariosdinamicos.model.CatalogoVO;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.SimularEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;
import mx.com.nmp.escenariosdinamicos.utils.EmuMacroCategoria;
import mx.com.nmp.escenariosdinamicos.utils.FechasComunes;

@Service
public class ElasticService {

	@Autowired
	private ElasticProperties connectionProperties;
	@Autowired
	private CastObjectGeneric castObject;
	@Autowired
	private FechasComunes formmatDate;
	@Autowired
	private EmuMacroCategoria emuMacroCategoria;

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
	
	public QueryBuilder crearCriteriosAgrupamiento(CatalogoVO nivelAgrupacion,SimularEscenarioDinamicoReq crearEs){
		QueryBuilder filtroJoin=null;
		List<String> listaAgrupamiento=null;
		QueryBuilder filtroFechas = QueryBuilders.rangeQuery(Constantes.CAMPO_FECHA_INDEX)
				.gte(formmatDate.resetTimeToDown(fechaActual, Constantes.DIFERENCIA_DIAS))
				.lte(formmatDate.resetTimeToUp(fechaActual));
		
		if(nivelAgrupacion.getDescripcion().equals(Constantes.SUBRAMO)){
			BoolQueryBuilder boolQuery = new BoolQueryBuilder();
			LOG.info("crear criterio rubramo");
			listaAgrupamiento=new ArrayList<>();
			listaAgrupamiento=crearEs.getInfoRegla().getSubramo();
			listaAgrupamiento.stream()
				.forEach(subramo->boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.SUBRAMO_DES,subramo)));
			filtroJoin=QueryBuilders.boolQuery().must(filtroFechas).should(boolQuery).minimumShouldMatch(1);
		}else if(nivelAgrupacion.getDescripcion().equals(Constantes.FACTOR)){
			BoolQueryBuilder boolQuery = new BoolQueryBuilder();
			LOG.info("crear criterio factor");
			listaAgrupamiento=new ArrayList<>();
			listaAgrupamiento=crearEs.getInfoRegla().getFactor();
			listaAgrupamiento.stream().forEach(factor->
				boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.FACTOR_DES,factor)));
			filtroJoin=QueryBuilders.boolQuery().must(filtroFechas).should(boolQuery).minimumShouldMatch(1);
		}else if(nivelAgrupacion.getDescripcion().equals(Constantes.CATEGORIA)){
			LOG.info("crear criterio categoria");
			listaAgrupamiento=new ArrayList<>();
			listaAgrupamiento=crearEs.getInfoRegla().getCategoria();
			filtroJoin=QueryBuilders.boolQuery()
					.must(filtroFechas)
					.must(QueryBuilders.matchPhraseQuery(Constantes.RAMO_DES, crearEs.getInfoRegla().getRamo()))
					.must(QueryBuilders.matchPhraseQuery(Constantes.SUBRAMO_DES, crearEs.getInfoRegla().getSubramo().get(0)));
//			listaAgrupamiento=emuMacroCategoria.calculaCatetoriaPorLista(crearEs.getInfoRegla().getCategoria());
//			qbNivelAgru = QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("categoria", listaAgrupamiento));
		}
		else if(nivelAgrupacion.getDescripcion().equals(Constantes.RAMO)){
			LOG.info("crear criterio ramo");
			filtroJoin=QueryBuilders.boolQuery()
					.must(filtroFechas).must(QueryBuilders.matchPhraseQuery(Constantes.RAMO_DES, crearEs.getInfoRegla().getRamo()));
		}	
		return filtroJoin;
	}
	
	public List<MdaVentasVO> scrollElasticVentas(String index, SimularEscenarioDinamicoReq request){
		LOG.info("consultando mda_Ventas");
		List<MdaVentasVO> lstIndexGarantia = new ArrayList<>();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		SearchResponse searchResponse=null;
		if(request.getInfoRegla().getNivelAgrupacion()!=null){
			CatalogoVO nivelAgrupacion=castObject.deserializaNivelAgrupacion(request.getInfoRegla().getNivelAgrupacion());
			LOG.info("nivel de agrupacion mdaVentas -> {}",nivelAgrupacion.getDescripcion());
			QueryBuilder filtro=crearCriteriosAgrupamiento(nivelAgrupacion, request);
			LOG.info(filtro.queryName());
			LOG.info(filtro.toString());
			searchSourceBuilder.query(filtro);
			searchSourceBuilder.size(Constantes.NUMERO_MAXIMO_SCROLL);
			searchRequest.indices(index);
			searchRequest.source(searchSourceBuilder);
			final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
			searchRequest.scroll(scroll);
			try {
				searchResponse = getConnectionElastic().search(searchRequest, RequestOptions.DEFAULT);
				 String scrollId = searchResponse.getScrollId();
				 LOG.info("scroll id token ->{}",scrollId);
			        SearchHit[] searchHits = searchResponse.getHits().getHits();
			        LOG.info("size query elastic ventas {}", searchHits.length);
			        for (SearchHit hit : searchHits) {
			          SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
			          scrollRequest.scroll(scroll);
			          searchResponse=getConnectionElastic().scroll(scrollRequest, RequestOptions.DEFAULT);
			          scrollId = searchResponse.getScrollId();
			          searchHits = searchResponse.getHits().getHits();
			          LOG.info("scroll id token ->{}",scrollId);
			          String response = hit.getSourceAsString();
			          lstIndexGarantia.add(castObject.jsonFieldToObjectVenta(response));
			        }
			        LOG.info("limpiando scroll");
			        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			        clearScrollRequest.addScrollId(scrollId);
			        getConnectionElastic().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lstIndexGarantia;
	}
	// scroll pc_garantias
	public List<IndexGarantiaVO> scrollElasticGarantias(SimularEscenarioDinamicoReq request, String index,List<String> partidas) throws IOException {
		LOG.info("consultando pc_garantias");
		List<IndexGarantiaVO> lstIndexGarantia = new ArrayList<>();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		SearchResponse searchResponse=null;
		if(!partidas.isEmpty()){
			QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termsQuery(Constantes.PARTIDA, partidas));
			LOG.info(query.queryName());
			LOG.info(query.toString());
			searchSourceBuilder.query(query);
			searchSourceBuilder.size(Constantes.NUMERO_MAXIMO_SCROLL);
			searchRequest.indices(index);
			searchRequest.source(searchSourceBuilder);
			final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
			searchRequest.scroll(scroll);
			try{
				searchResponse = getConnectionElastic().search(searchRequest, RequestOptions.DEFAULT);
				String scrollId = searchResponse.getScrollId();
				LOG.info("scroll id token ->{}",scrollId);
				SearchHit[] searchHits = searchResponse.getHits().getHits();
				 LOG.info("size garantias {}", searchHits.length);
				for (SearchHit hit : searchHits) {
					SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
					scrollRequest.scroll(scroll);					
					searchResponse=getConnectionElastic().scroll(scrollRequest, RequestOptions.DEFAULT);
					scrollId = searchResponse.getScrollId();
					searchHits = searchResponse.getHits().getHits();
					LOG.info("scroll id token ->{}",scrollId);
					String response = hit.getSourceAsString();
					LOG.info(Constantes.S_LOG);
					LOG.info(response);
					lstIndexGarantia.add(castObject.jsonFieldToObject(response));
					LOG.info(Constantes.S_LOG);
					 LOG.info("limpiando scroll garantias");
					ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			        clearScrollRequest.addScrollId(scrollId);
			        getConnectionElastic().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
				}
				LOG.info("size garantias {}", lstIndexGarantia.size());
			}catch(IOException w){
				w.printStackTrace();
			}	
		}
		return lstIndexGarantia;
	}
}
