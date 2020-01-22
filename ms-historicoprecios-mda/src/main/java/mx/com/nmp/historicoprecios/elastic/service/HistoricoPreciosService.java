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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.nmp.historicoprecios.api.HistoricoApiController;
import mx.com.nmp.historicoprecios.elastic.vo.HistoricoPreciosVO;
import mx.com.nmp.historicoprecios.model.HistoricoPrecios;

@Service
public class HistoricoPreciosService {

	private static final Logger log = LoggerFactory.getLogger(HistoricoPreciosService.class);
	
	@Autowired
	private ElasticService elasticService;
	
	@Value("${spring.elasticsearch.index}")
	private String index;
	
	public Boolean insertarHistoricoPrecios(HistoricoPrecios hp, String usuario) {
		log.info("HistoricoPreciosService.insertarHistoricoPrecios");
		
		Boolean insertado = false;
		HistoricoPreciosVO hpvo = null;
		
		if(hp.getSku() != null && hp.getFolioPartida() != null && hp.getPrecioActual() != null && hp.getPrecioModificado() != null && hp.getFecha() != null ) {
			
			hpvo = new HistoricoPreciosVO();
			
			RestHighLevelClient restHighLevelClient = elasticService.getConnectionElastic();
			
			hpvo.setSku(hp.getSku());
			hpvo.setPartida(hp.getFolioPartida());
			hpvo.setPrecio_actual(hp.getPrecioActual().toString());
			hpvo.setPrecio_modificado(hp.getPrecioModificado().toString());
			hpvo.setUsuario(usuario);
			
			String[] fechaAcotada = hp.getFecha().split(" ");
			
			if(fechaAcotada.length != 0) {
				hpvo.setFecha(fechaAcotada[0]);
			} else {
				hpvo.setFecha(hp.getFecha());
			}
			
			try {
			
				insertado = elasticService.insertHistoricoPreciosVO(hpvo, index, restHighLevelClient);
			
				elasticService.closeConnection(restHighLevelClient);
			} catch (IOException ioe) {
				log.error("Error insertarHistoricoPrecios: {}", ioe);
				try {
					elasticService.closeConnection(restHighLevelClient);
				} catch (IOException e) {
					log.error("Error insertarHistoricoPrecios: {}", e);
				}
			}
		}
		return insertado;
	}
	
	
	
}
