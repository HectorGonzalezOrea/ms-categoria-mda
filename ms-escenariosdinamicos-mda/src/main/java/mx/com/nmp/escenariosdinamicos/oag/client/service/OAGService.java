package mx.com.nmp.escenariosdinamicos.oag.client.service;


import static mx.com.nmp.escenariosdinamicos.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.SCOPE;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.STATUS_CODE_OK;



import static mx.com.nmp.escenariosdinamicos.utils.Constantes.BASIC;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.HEADER_OAUTH_BEARER;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.HEADER_USUARIO;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import mx.com.nmp.escenariosdinamicos.oag.vo.EnviarNotificacionRequestVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.GetTokenResponseVO;
import mx.com.nmp.escenariosdinamicos.utils.ConvertStringToBase64;
import mx.com.nmp.escenariosdinamicos.utils.ConverterUtil;

@Service
public class OAGService extends OAGBaseService {
	
	private static final Logger log = LoggerFactory.getLogger(OAGService.class);
	
	
	public String getToken() {
		log.info("getToken");
		String accessToken = "";
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
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
			log.error("UnirestException: {0} " , ue);
		}
		return accessToken;
	}
	
	
	@Retryable(value = {UnirestException.class },maxAttempts=3, backoff=@Backoff(delay=3000))
	@Async
	public void enviarNotificacion(EnviarNotificacionRequestVO request) throws UnirestException {
		log.info("Entrando al metodo enviarNotificacion ");
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		String oauthBearer = this.getToken();
		
		String gpRequestJson = ConverterUtil.messageToJson(request);
		
		Unirest.setTimeouts(0, 0);
		
			HttpResponse<String> response = Unirest.post(urlBase+servicioEnviarCorreo)
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
			
		
	}

}
