package mx.com.nmp.escenariosdinamicos.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import mx.com.nmp.escenariosdinamicos.elastic.vo.MdaVentasVO;
import mx.com.nmp.escenariosdinamicos.model.CatalogoVO;
import mx.com.nmp.escenariosdinamicos.model.InfoRegla;
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

	public synchronized RestHighLevelClient getConnectionElasticOld() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(connectionProperties.getUserOld(), connectionProperties.getPasswordOld()));

		RestClientBuilder builder = RestClient.builder(new HttpHost(connectionProperties.getHostOld(),
				Integer.valueOf(connectionProperties.getPortOld()), connectionProperties.getSchemeOld()))
				.setHttpClientConfigCallback(httpClientBuilder -> {
					httpClientBuilder.disableAuthCaching();
					return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				});
		return new RestHighLevelClient(builder);
	}

	public synchronized RestHighLevelClient getConnectionElasticNew() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(connectionProperties.getUserNew(), connectionProperties.getPasswordNew()));

		RestClientBuilder builder = RestClient.builder(new HttpHost(connectionProperties.getHostNew(),
				Integer.valueOf(connectionProperties.getPortNew()), connectionProperties.getSchemeNew()))
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
		QueryBuilder filtroFechas = QueryBuilders.rangeQuery(Constantes.CAMPO_FECHA_INDEX)
				.gte(formmatDate.resetTimeToDown(fechaActual, Constantes.DIFERENCIA_DIAS))
				.lte(formmatDate.resetTimeToUp(fechaActual));
		
		if(nivelAgrupacion.getDescripcion().equals(Constantes.SUBRAMO)){
			BoolQueryBuilder boolQuery = new BoolQueryBuilder();
			LOG.info("crear criterio rubramo");
			crearEs.getInfoRegla().getSubramo().stream().forEach(subramo->boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.SUBRAMO_DES,subramo)));
			filtroJoin=QueryBuilders.boolQuery().must(filtroFechas).should(boolQuery).minimumShouldMatch(1);
			
		}else if(nivelAgrupacion.getDescripcion().equals(Constantes.FACTOR)){
			BoolQueryBuilder boolQuery = new BoolQueryBuilder();
			LOG.info("crear criterio factor");
			crearEs.getInfoRegla().getFactor().stream().filter(Objects::nonNull).forEach(factor->boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.FACTOR_DES,factor)));
			crearEs.getInfoRegla().getSucursales().stream().filter(Objects::nonNull).forEach(suc->boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.MOV_SUCURSAL,suc)));
			crearEs.getInfoRegla().getSubramo().stream().filter(Objects::nonNull).forEach(subRamo->boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.SUBRAMO_DES,subRamo)));
			crearEs.getInfoRegla().getEstatusPartida().stream().forEach(estatus->{
				CatalogoVO cat=castObject.deserializaNivelAgrupacion(estatus);
				boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.EDO_PRENDA,cat.getDescripcion()));
			});
			crearEs.getInfoRegla().getCanalIngresoActual().stream().forEach(canal->{
				CatalogoVO canalVO=castObject.deserializaNivelAgrupacion(canal);
				boolQuery.should(QueryBuilders.matchPhraseQuery(Constantes.CANAL_INGRESO, canalVO.getDescripcion()));
			});			
			filtroJoin=QueryBuilders.boolQuery().must(filtroFechas).should(boolQuery).minimumShouldMatch(1);
		}else if(nivelAgrupacion.getDescripcion().equals(Constantes.CATEGORIA)){
			LOG.info("crear criterio categoria");
//			listaAgrupamiento=new ArrayList<>();
//			listaAgrupamiento=crearEs.getInfoRegla().getCategoria();
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
	
	public QueryBuilder crearCriteriosAgrupamiento(InfoRegla regla) {

		int i = 0;
		
		QueryBuilder filtroFechas = QueryBuilders
			.rangeQuery(Constantes.CAMPO_FECHA_INDEX)
			.gte(formmatDate.resetTimeToDown(fechaActual, Constantes.DIFERENCIA_DIAS))
			.lte(formmatDate.resetTimeToUp(fechaActual));
		
		BoolQueryBuilder generalMust = QueryBuilders.boolQuery().must(filtroFechas);

		if(regla.getRamo() != null && !regla.getRamo().isEmpty()) {
			generalMust.must(QueryBuilders
					.matchPhraseQuery(Constantes.RAMO_DES, regla.getRamo()));
		}

		if(regla.getFactor() != null && !regla.getFactor().isEmpty()) {
			i++;
			regla.getFactor().stream().forEach(factor -> generalMust.should(QueryBuilders.matchPhraseQuery(Constantes.FACTOR_DES, factor)));
		}

		if(regla.getSucursales() != null && !regla.getSucursales().isEmpty()) {
			i++;
			regla.getSucursales().stream().forEach(suc -> generalMust.should(QueryBuilders.matchPhraseQuery(Constantes.MOV_SUCURSAL, suc)));
		}
		
		if(regla.getSubramo() != null && !regla.getSubramo().isEmpty()) {
			i++;
			regla.getSubramo().stream().forEach(subramo -> generalMust.should(QueryBuilders.matchPhraseQuery(Constantes.SUBRAMO_DES, subramo)));
		}
	
		if(regla.getEstatusPartida() != null && !regla.getEstatusPartida().isEmpty()) {
			i++;
			regla.getEstatusPartida().stream().forEach(status -> generalMust.should(QueryBuilders.matchPhraseQuery(Constantes.EDO_PRENDA, status.getDescripcion())));
		}

		if(regla.getCanalIngresoActual() != null && !regla.getCanalIngresoActual().isEmpty()) {
			i++;
			regla.getCanalIngresoActual().stream().forEach(status -> generalMust.should(QueryBuilders.matchPhraseQuery(Constantes.CANAL_INGRESO, status.getDescripcion())));
		}

		/*
		if(regla.getNivelAgrupacion() != null) {
			CatalogoVO nivelAgrupacion = castObject.deserializaNivelAgrupacion(regla.getNivelAgrupacion());
			if(nivelAgrupacion.getDescripcion().equalsIgnoreCase("CATEGORIA") && !regla.getCategoria().isEmpty()) {
				// aquï¿½ se agregara el filtro por categorï¿½a en el campo descripciï¿½n de la partida en el indice de ventas
				// igual se debe de aumentar la i
			}
		}
		*/

		generalMust.minimumShouldMatch(i);
		
		return QueryBuilders.boolQuery()
				.must(QueryBuilders.matchAllQuery())
				.must(generalMust);
	}
	
	public List<MdaVentasVO> scrollElasticVentas(String index, InfoRegla regla, Boolean simulacion) {
		
		List<MdaVentasVO> lstVentas = new ArrayList<>();
		Map<String,String> mapScrollId = new HashMap<>();
		List<MdaVentasVO> lstVentasFiltradas;
		
		try {
			Integer numMaxConcidencias = Constantes.NUMERO_MAXIMO_SCROLL;
			
			if(simulacion.equals(Boolean.TRUE)) {
				// cambiar a variables de entorno de este valor
				numMaxConcidencias = 50;
			}
			
			SearchResponse searchResponse=null;
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			SearchRequest searchRequest = new SearchRequest();
			
			QueryBuilder filtro = this.crearCriteriosAgrupamiento(regla);
			LOG.info("{}" , filtro.toString());
			
			searchSourceBuilder.query(filtro);
			searchSourceBuilder.size(numMaxConcidencias);
			
			searchRequest.indices(index);
			searchRequest.source(searchSourceBuilder);

			final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
			searchRequest.scroll(scroll);
			
			searchResponse = getConnectionElasticNew().search(searchRequest, RequestOptions.DEFAULT);
			String scrollId = searchResponse.getScrollId();
			
			mapScrollId.put(scrollId, scrollId);
			
			LOG.info("scroll id token ->{}", scrollId);
			
			SearchHit[] searchHits = searchResponse.getHits().getHits();
	        LOG.info("size query elastic ventas {}", searchHits.length);
	        
	        Long totalHits = searchResponse.getHits().getTotalHits().value;
	        
	        if(totalHits != 0) {
	        	
		        // Agregamos primeras concidencias
	        	Arrays
		        .stream(searchHits)
		        .forEach(hit -> {
		        	String response = hit.getSourceAsString();
		        	lstVentas.add(castObject.jsonFieldToObjectVenta(response));
		        });
	        	
	        	Double iteracionesScrollD = (totalHits.doubleValue() / numMaxConcidencias.doubleValue());
	        	
	        	// menos uno porque ya se hizo la primera consulta
	        	Integer iteracionesScroll = iteracionesScrollD.intValue() - 1;
	        	Integer modulo =  totalHits.intValue() % numMaxConcidencias;
	        	
	        	Integer nodos = iteracionesScroll;
	        	
	        	if(modulo != 0) {
	        		nodos++;
	        	}
	        	
				for (int i = 0; i < nodos; i++) {
					SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
					scrollRequest.scroll(scroll);
					searchResponse = getConnectionElasticNew().scroll(scrollRequest, RequestOptions.DEFAULT);
					scrollId = searchResponse.getScrollId();
					
					mapScrollId.put(scrollId, scrollId);
					
					searchHits = searchResponse.getHits().getHits();
					LOG.info("scroll id token ->{}", scrollId);
					
					Arrays
			        .stream(searchHits)
			        .forEach(hit -> {
			        	String response = hit.getSourceAsString();
			        	lstVentas.add(castObject.jsonFieldToObjectVenta(response));
			        });
				}
	        }
	        
	        //LOG.info("size: {}, lstIndexGarantia: {}" , lstVentas.size(), lstVentas);
	        LOG.info("lista antes de filtrar size: {}" , lstVentas.size());

	        LOG.info("limpiando scroll");
	        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
	        
	        mapScrollId
	        .entrySet()
	        .stream()
	        .forEach(s -> clearScrollRequest.addScrollId(s.getValue()));
	        
	       // clearScrollRequest.addScrollId(scrollId);
	        getConnectionElasticNew().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
		} catch(IOException ioe) {
			LOG.error("{}", ioe);
		}
		
		lstVentasFiltradas = this.filtroAdicional(regla, lstVentas);
		LOG.info("lista despues de filtrar size: {}" , lstVentasFiltradas.size());
		
		return lstVentasFiltradas;
	}

	private List<MdaVentasVO> filtroAdicional(InfoRegla regla, List<MdaVentasVO> lstVentas) {
		
		Predicate<MdaVentasVO> hasSameNameAsOneSubramo = 
			v -> regla.getSubramo().stream().anyMatch(s -> v.getSubramoDes().equals(s));
		
		return lstVentas
			.stream()
			.filter(hasSameNameAsOneSubramo)
			//aquí tambien se debe de agregar la clasificación del cliente
			.collect(Collectors.toList());
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
				searchResponse = getConnectionElasticNew().search(searchRequest, RequestOptions.DEFAULT);
				 String scrollId = searchResponse.getScrollId();
				 LOG.info("scroll id token ->{}",scrollId);
			        SearchHit[] searchHits = searchResponse.getHits().getHits();
			        LOG.info("size query elastic ventas {}", searchHits.length);
			        for (SearchHit hit : searchHits) {
			          SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
			          scrollRequest.scroll(scroll);
			          searchResponse=getConnectionElasticNew().scroll(scrollRequest, RequestOptions.DEFAULT);
			          scrollId = searchResponse.getScrollId();
			          searchHits = searchResponse.getHits().getHits();
			          LOG.info("scroll id token ->{}",scrollId);
			          String response = hit.getSourceAsString();
			          lstIndexGarantia.add(castObject.jsonFieldToObjectVenta(response));
			        }
			        LOG.info("limpiando scroll");
			        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			        clearScrollRequest.addScrollId(scrollId);
			        getConnectionElasticNew().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
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
				searchResponse = getConnectionElasticOld().search(searchRequest, RequestOptions.DEFAULT);
				String scrollId = searchResponse.getScrollId();
				LOG.info("scroll id token ->{}",scrollId);
				SearchHit[] searchHits = searchResponse.getHits().getHits();
				 LOG.info("size garantias {}", searchHits.length);
				for (SearchHit hit : searchHits) {
					SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
					scrollRequest.scroll(scroll);					
					searchResponse=getConnectionElasticOld().scroll(scrollRequest, RequestOptions.DEFAULT);
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
			        getConnectionElasticOld().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
				}
				LOG.info("size garantias {}", lstIndexGarantia.size());
			}catch(IOException w){
				w.printStackTrace();
			}	
		}
		return lstIndexGarantia;
	}
}
