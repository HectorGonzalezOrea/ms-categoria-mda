package mx.com.nmp.gestionescenarios.oag.controller;

import static mx.com.nmp.gestionescenarios.utils.Constantes.BASIC;
import static mx.com.nmp.gestionescenarios.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_USUARIO_OAG;
import static mx.com.nmp.gestionescenarios.utils.Constantes.SCOPE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_OAUTH_BEARER;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscanarioRequestVO;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscanarioResponseVO;
import mx.com.nmp.gestionescenarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.gestionescenarios.utils.ConvertStringToBase64;
import mx.com.nmp.gestionescenarios.utils.ConverterUtil;

@RestController
public class OAGController extends OAGBase {

	private static final Logger log = LoggerFactory.getLogger(OAGController.class);
	
	public String getToken() {
		log.info("getToken");
		
		String accessToken = "";
		String credenciales = usuarioAuth + ":" + passwordAuth;
		
		log.info("credenciales : {}" , credenciales);
		
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		Unirest.setTimeouts(0, 0);
		
		try {
			
			String url = urlBase + servicioGetToken;
			
			log.info("url : {}" , url);
			log.info("{} : {}" , HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
			log.info("{} : {}" , HEADER_USUARIO_OAG, headerUsuario);
			log.info("{} : {}" , HEADER_ID_CONSUMIDOR, headerIdConsumidor);
			log.info("{} : {}" , HEADER_ID_DESTINO, headerIdDestino);
			log.info("{} : {}" , HttpHeaders.AUTHORIZATION, autenticacionBasica);
			log.info("{} : {}" , GRANT_TYPE, grantType);
			log.info("{} : {}" , SCOPE, scope);
			
			HttpResponse<String> response = Unirest.post(url)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
			  .header(HEADER_USUARIO_OAG, headerUsuario)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .field(GRANT_TYPE, grantType)
			  .field(SCOPE, scope)
			  .asString();
			
			int statusCode = response.getStatus();
			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO resp = ConverterUtil.stringJsonToObjectGetTokenResponseVO(response.getBody());
				accessToken = resp.getAccessToken();
			}
		} catch (UnirestException ue) {
			log.error("UnirestException: {} " , ue);
		}
		return accessToken;
	}
	
	public CalendarizarEscanarioResponseVO calendarizarEscenario(CalendarizarEscanarioRequestVO request) {
		log.info("calendarizarEscenario");
		
		String credenciales = usuarioAuth + ":" + passwordAuth;
		
		log.info("credenciales : {}" , credenciales);
		
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		String accessToken = this.getToken();
		
		String requestJson = ConverterUtil.messageToJson(request);
		
		CalendarizarEscanarioResponseVO responseVO = null;
		
		Unirest.setTimeouts(0, 0);
		try {
			
			String url = urlBase + servicioCalendarizacion;
			
			log.info("url : {}" , url);
			log.info("{} : {}" , HEADER_USUARIO_OAG, headerUsuario);
			log.info("{} : {}" , HEADER_ID_CONSUMIDOR, headerIdConsumidor);
			log.info("{} : {}" , HEADER_ID_DESTINO, headerIdDestino);
			log.info("{} : {}" , HEADER_OAUTH_BEARER, accessToken);
			log.info("{} : {}" , HttpHeaders.AUTHORIZATION, autenticacionBasica);
			log.info("{} : {}" , HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
			log.info("Body : {}" , requestJson);
			
			HttpResponse<String> response = Unirest.post(url)
			  .header(HEADER_USUARIO_OAG, headerUsuario)
			  .header(HEADER_OAUTH_BEARER, accessToken)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .body(requestJson)
			  .asString();
			
			log.info("Estatus: {}" , response.getStatus());
			log.info("Estatus: {}" , response.getBody());
			
			if(response.getStatus() == 200) {
				responseVO = ConverterUtil.stringJsonToObjectCalendarizarEscanarioResponseVO(response.getBody());
			}
			
		} catch (UnirestException e) {
			log.error("UnirestException {}" ,e);
		}

		return responseVO;
	}
	
}