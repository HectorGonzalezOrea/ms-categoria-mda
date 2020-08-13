package mx.com.nmp.establecimientoprecios.oag.client.service;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.SCOPE;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_OAUTH_BEARER;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.STATUS_CODE_OK;


import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.establecimientoprecios.utils.Constantes;
import mx.com.nmp.establecimientoprecios.utils.ConvertStringToBase64;
import mx.com.nmp.establecimientoprecios.utils.ConverterUtil;

@Service
public class OAGService extends OAGBaseService {

	private static final Logger log = LoggerFactory.getLogger(OAGService.class);
	
	
	
	
	public String getToken() {
		log.info("getToken");
		
		String accessToken = "";
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = Constantes.BASIC + ConvertStringToBase64.encode(credenciales);
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicioGetToken)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
			  .header(HEADER_USUARIO, headerUsuario)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .field(GRANT_TYPE, grantType)
			  .field(SCOPE, scope)
			  .asString();
			
			int statusCode = response.getStatus();
			
			log.info("Status Code Response: {}" , statusCode);
			log.info("Body Response: {}" , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO resp = ConverterUtil.stringJsonToObjectGetTokenResponseVO(response.getBody());
				accessToken = resp.getAccess_token();
			}
		} catch (UnirestException e) {
			log.error("Error al obtener el token {0} ", e);
		}
		
		return accessToken;
	}
	
	
	public Boolean gestionPrecios(GestionPreciosRequestVO request) {
		log.info("gestionPrecios");
		
		Boolean insertado = false;
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = Constantes.BASIC + ConvertStringToBase64.encode(credenciales);
		
		String oauthBearer = this.getToken();
		
		String gpRequestJson = ConverterUtil.messageToJson(request);
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicioAjustarPrecios)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HEADER_USUARIO, headerUsuario)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HEADER_OAUTH_BEARER, oauthBearer)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .body(gpRequestJson)
			  .asString();
			
			int statusCode = response.getStatus();
			
			log.info("Status Code Response: {}" , statusCode);
			log.info("Body Response: {}" , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GestionPreciosResponseVO resp = ConverterUtil.stringJsonToObjectGestionPreciosResponseVO(response.getBody());
				
				log.info("resp: {}" , resp);
				
				insertado = true;
			}
			
		} catch (UnirestException e) {
			log.error("Error gestionPrecios {0} " , e);
		}
		
		return insertado;
	}
	
	
	public Boolean valorAncla() {
		log.info("valorAncla");
		return true;
	}
	
	
	public Boolean actualizarMonedas() {
		log.info("actualizarMonedas");
		
		
		
		return true;
	}
	
	
	public Boolean almacenarMonedas() {
		log.info("almacenarMonedas");
		
		
		
		return true;
	}
	
}
