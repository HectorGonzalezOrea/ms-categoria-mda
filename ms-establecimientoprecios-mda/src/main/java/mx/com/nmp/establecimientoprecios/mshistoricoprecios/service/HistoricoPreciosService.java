package mx.com.nmp.establecimientoprecios.mshistoricoprecios.service;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_APIKEY_KEY;


import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.utils.ConverterUtil;

@Service
public class HistoricoPreciosService extends HistoricoPreciosBaseService {

	private static final Logger log = LoggerFactory.getLogger(HistoricoPreciosService.class);
	

	public Boolean insertaHistoricoPrecios( String usuario,  HistoricoPreciosRequestVO request) {
		
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
				resp = ConverterUtil.stringJsonToObjectHistoricoPreciosResponseVO(response.getBody());
				log.info("resp {} " , resp);
			}
			
		} catch (UnirestException e) {
			log.error("Exception {0} " , e);
		}
		
		return insertado;
	}
	
}
