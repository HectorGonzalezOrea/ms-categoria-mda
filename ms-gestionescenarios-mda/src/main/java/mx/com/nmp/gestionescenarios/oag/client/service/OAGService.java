package mx.com.nmp.gestionescenarios.oag.client.service;

import static mx.com.nmp.gestionescenarios.utils.Constantes.BASIC;
import static mx.com.nmp.gestionescenarios.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_USUARIO_OAG;
import static mx.com.nmp.gestionescenarios.utils.Constantes.SCOPE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_OAUTH_BEARER;
import static mx.com.nmp.gestionescenarios.utils.Constantes.SLASH;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscenarioRequestVO;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscenarioResponseVO;
import mx.com.nmp.gestionescenarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.gestionescenarios.utils.ConvertStringToBase64;
import mx.com.nmp.gestionescenarios.utils.ConverterUtil;

@Service
public class OAGService extends OAGBaseService {

	private static final Logger log = LoggerFactory.getLogger(OAGService.class);
	
	/*
	 * Cliente del servicio de obtiene el access token
	 */
	public String getToken() {
		log.info("getToken");
		
		String accessToken = "";
		String credenciales = usuarioAuth + ":" + passwordAuth;
		
		log.info("credenciales {}", credenciales);
		
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		Unirest.setTimeouts(0, 0);
		
		try {
			
			String url = urlBase + servicioGetToken;
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
			log.error("Error al obtener el token: {0} " , ue);
		}
		return accessToken;
	}
	
	/*
	 * Cliente del servicio utilizado para calendarizar el escenario
	 */
	public CalendarizarEscenarioResponseVO calendarizarEscenario(CalendarizarEscenarioRequestVO request) {
		log.info("calendarizarEscenario");
		
		
		String credenciales = usuarioAuth + ":" + passwordAuth;
		
		log.info("credenciales : {}" , credenciales);
		
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		String accessToken = this.getToken();
		
		String requestJson = ConverterUtil.messageToJson(request);
		
		CalendarizarEscenarioResponseVO responseVO = null;
		
		Unirest.setTimeouts(0, 0);
		try {
			
			String url = urlBase + servicioCalendarizacion;
			
			log.info("url : {}" , url);
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
				responseVO = ConverterUtil.stringJsonToObjectCalendarizarEscenarioResponseVO(response.getBody());
			}
			
		} catch (UnirestException e) {
			log.error("Error calendarizarEscenario: {0}" ,e);
		}

		return responseVO;
	}
	
	/*
	 * Cliente del servicio utilizado para eliminar la calendarizacion
	 */
	public Boolean eliminarCalendarizacionEscenario(Integer idRequestCalendarizacion) {
		log.info("eliminarCalendarizacionEscenario");

		
		String credenciales = usuarioAuth + ":" + passwordAuth;
		
		Boolean eliminado = false;
		
		log.info("credenciales : {}" , credenciales);
		
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		String accessToken = this.getToken();
		
		Unirest.setTimeouts(0, 0);
		try {
			
			String url = urlBase + servicioCalendarizacion + SLASH + idRequestCalendarizacion;
			
			log.info("url : {}" , url);
			
			HttpResponse<String> response = Unirest.delete(url)
			  .header(HEADER_USUARIO_OAG, headerUsuario)
			  .header(HEADER_OAUTH_BEARER, accessToken)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .asString();

			int statusCode = response.getStatus();
			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				eliminado = true;
			}
		} catch (UnirestException e) {
			log.error("Error eliminarCalendarizacionEscenario: {0}" ,e);
		}
		
		return eliminado;
	}
	
}