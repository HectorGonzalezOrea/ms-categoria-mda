package mx.com.nmp.valormonte.elastic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static mx.com.nmp.valormonte.utils.Constantes.MAP_ID;
import static mx.com.nmp.valormonte.utils.Constantes.MAP_QUERY;
import static mx.com.nmp.valormonte.utils.Constantes.REQ_SKU;
import static mx.com.nmp.valormonte.utils.Constantes.REQ_PARTIDA;
import static mx.com.nmp.valormonte.utils.Constantes.REQ_VALOR_MONTE_ACT;

import mx.com.nmp.valormonte.elastic.vo.ResponseElasticVO;
import mx.com.nmp.valormonte.elastic.vo.Hits;
import mx.com.nmp.valormonte.elastic.vo.RequestElasticVO;
import mx.com.nmp.valormonte.elastic.vo.Source;
import mx.com.nmp.valormonte.utils.ConvertStringToBase64;
import mx.com.nmp.valormonte.utils.ConverterUtil;

@RestController
public class ElasticController extends ElasticBaseController {

	private static final Logger log = LoggerFactory.getLogger(ElasticController.class);

	public Source consultaElastic(String sku) {
		log.info("consultaElastic por Sku: {} ", sku);
		
		Unirest.setTimeouts(0, 0);

		Source producto = null;
		
		String credenciales = username + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);

		Map<String, Object> map = new HashMap<>();
		map.put(MAP_QUERY, MAP_ID + ":" + sku);

		RequestElasticVO req = new RequestElasticVO();
		List<String> source = new ArrayList<>();
		source.add(REQ_SKU);
		source.add(REQ_PARTIDA);
		source.add(REQ_VALOR_MONTE_ACT);
		
		req.setSource(source);
		
		try {
			HttpResponse<String> response = Unirest.post(urlbase + service)
					.header(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
					.header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
					.queryString(map)
					.body("{\t\t\r\n\"_source\": [\"sku\", \"partida\", \"valor_monte_act\"]\r\n}")
					.asString();
			
			int statusCode = response.getStatus();

			log.info("Status Code Response: {} ", statusCode);
			log.info("Body Response: {} ", response.getBody());
			
			if(statusCode == 200) {
				ResponseElasticVO responseVo = ConverterUtil.StringJsonToObjectElasticVO(response.getBody());
				Hits hitsPrincipal = responseVo.getHits();
				
				if(!hitsPrincipal.getHits().isEmpty()) {
					producto = hitsPrincipal.getHits().get(0).getSource();
				}
			}
			
		} catch (UnirestException e) {
			log.error("UnirestException: {}" , e);
		}

		return producto;
	}

}
