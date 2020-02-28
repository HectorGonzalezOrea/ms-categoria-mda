package mx.com.nmp.establecimientoprecios.mshistoricoprecios.controller;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_APIKEY_KEY;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.utils.ConverterUtil;

@RestController
public class HistoricoPreciosController extends HistoricoPreciosBaseController {

	private static final Logger log = LoggerFactory.getLogger(HistoricoPreciosController.class);
	
	@PostMapping(path = "/insertaHistoricoPrecios/{usuario}")
	public Boolean insertaHistoricoPrecios(@PathVariable String usuario, @RequestBody HistoricoPreciosRequestVO request) {
		
		Boolean insertado = false;
		
		String requestJson = ConverterUtil.messageToJson(request);
		HistoricoPreciosResponseVO resp = null;
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicio)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
			  .header(HEADER_APIKEY_KEY, apivalue)
			  .header(HEADER_USUARIO, usuario)
			  .body(requestJson)
			  .asString();
			
			int statusCode = response.getStatus();

			log.info("Status Code Response: {}" , statusCode);
			log.info("Body Response: {}" , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				insertado =  true;
				resp = ConverterUtil.StringJsonToObjectHistoricoPreciosResponseVO(response.getBody());
				log.info("resp {} " , resp);
			}
			
		} catch (UnirestException e) {
			log.error("Exception {} " , e);
		}
		
		return insertado;
	}
	
}
