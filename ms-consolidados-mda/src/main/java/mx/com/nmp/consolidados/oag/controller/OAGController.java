package mx.com.nmp.consolidados.oag.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.consolidados.oag.vo.GetTokenResponseVO;
import mx.com.nmp.consolidados.utils.ConvertStringToBase64;
import mx.com.nmp.consolidados.utils.ConverterUtil;

import static mx.com.nmp.consolidados.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.consolidados.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.consolidados.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.consolidados.utils.Constantes.SCOPE;
import static mx.com.nmp.consolidados.utils.Constantes.OAUTH_BEARER;

import javax.ws.rs.core.MediaType;

@RestController
public class OAGController extends OAGBaseController {

	private static final Logger log = LoggerFactory.getLogger(OAGController.class);
	
	@PostMapping(path = "/getToken")
	public String getToken() {
		log.info("getToken");
		
		String accessToken = "";
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		
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

			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO resp = ConverterUtil.stringJsonToObjectGetTokenResponseVO(response.getBody());
				accessToken = resp.getAccess_token();
			}
		} catch (UnirestException ue) {
			log.error("UnirestException: {} " , ue);
		}
		
		return accessToken;
	}
	
	public Boolean eliminarCalendarizacion(String idPeticion) {
		
		log.info("eliminarCalendarizacion");
		
		Boolean eliminado = false;
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		
		String oauthBearer = this.getToken();
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.delete(urlBase + servicioEliminarCalendarizacion + "/" + idPeticion)
			  .header(HEADER_USUARIO, headerUsuario)
			  .header(OAUTH_BEARER, oauthBearer)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .asString();
			
			int statusCode = response.getStatus();

			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				eliminado = true;
			}
			
		} catch (UnirestException ue) {
			log.error("UnirestException: {} " , ue);
		}
		
		return eliminado;
	}
	
}
