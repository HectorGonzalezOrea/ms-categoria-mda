package mx.com.nmp.establecimientoprecios.apiproductos.controller;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.establecimientoprecios.apiproductos.vo.ActualizarPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ActualizarPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaRequestVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaResponseVO;
import mx.com.nmp.establecimientoprecios.utils.ConverterUtil;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.STATUS_CODE_OK;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiProductosController extends ApiProductosBaseController {

	private static final Logger log = LoggerFactory.getLogger(ApiProductosController.class);

	@PostMapping(path = "/consultarPartida")
	public ConsultaPartidaResponseVO consultaPartida(@Valid @RequestBody ConsultaPartidaRequestVO request) {
		log.info("consultaPartida");
		
		String requestJson = ConverterUtil.messageToJson(request);

		ConsultaPartidaResponseVO resp = null;

		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(urlBase + consultaPartida)
					.header(HEADER_APIKEY_KEY, apikey)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
					.body(requestJson).asString();

			int statusCode = response.getStatus();

			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());

			if (statusCode == STATUS_CODE_OK) {
				resp = ConverterUtil.StringJsonToObjectConsultaPartidaResponse(response.getBody());
				log.info("resp: {} " , resp);
			}

		} catch (UnirestException e) {
			log.error("Exception {} " , e);
		}

		return resp;
	}

	@PostMapping(path = "/actualizarPartida")
	public Boolean actualizarPartida(@Valid @RequestBody ActualizarPreciosRequestVO request) {
		log.info("actualizarPartida");
		
		Boolean actualizado = false;
		ActualizarPreciosResponseVO resp = null;
		String requestJson = ConverterUtil.messageToJson(request);
		
		Unirest.setTimeouts(0, 0);
		
		try {
			HttpResponse<String> response = Unirest
					.post(urlBase + asignacionPrecios)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
					.header(HEADER_APIKEY_KEY, apikey)
					.body(requestJson)
					.asString();
			
			int statusCode = response.getStatus();

			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				actualizado = true;
				resp = ConverterUtil.StringJsonToObjectActualizarPreciosResponse(response.getBody());
				log.info("resp: {} " , resp);
			}
			
		} catch (UnirestException e) {
			log.error("Exception {} " , e);
		}
		
		return actualizado;
	}

}
