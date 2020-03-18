package mx.com.nmp.consolidados.msestablecimientoprecios.controller;

import static mx.com.nmp.consolidados.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.consolidados.utils.Constantes.STATUS_CODE_OK;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePreciosRequestVO;
import mx.com.nmp.consolidados.utils.ConverterUtil;

@RestController
public class EstablecimientoPreciosController extends EstablecimientoPreciosBaseController {

	private static final Logger log = LoggerFactory.getLogger(EstablecimientoPreciosController.class);
	
	@PostMapping(path = "/ajuste/precios")
	public Boolean ajustePrecios(@PathVariable String usuario, AjustePreciosRequestVO request) {
		
		String requestJson = ConverterUtil.messageToJson(request);

		Boolean exitoso = false;
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicio)
			  .header(HEADER_USUARIO, usuario)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
			  .header(HEADER_APIKEY_KEY, apivalue)
			  .body(requestJson)
			  .asString();
			
			int statusCode = response.getStatus();

			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				exitoso = true;
			}
		} catch (UnirestException e) {
			log.error("UnirestException: {} " , e);
		}
		
		return exitoso;
	}
	
}
