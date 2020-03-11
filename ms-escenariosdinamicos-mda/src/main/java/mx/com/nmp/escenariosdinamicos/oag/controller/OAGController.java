package mx.com.nmp.escenariosdinamicos.oag.controller;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import mx.com.nmp.escenariosdinamicos.oag.vo.EnviarNotificacionRequestVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.GetTokenResponseVO;
import mx.com.nmp.escenariosdinamicos.utils.ConvertStringToBase64;
import mx.com.nmp.escenariosdinamicos.utils.ConverterUtil;

@RestController
public class OAGController extends OAGBaseController {
	
	private static final Logger log = LoggerFactory.getLogger(OAGController.class);
	
	@PostMapping(path = "/getToken")
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
			log.error("UnirestException: {} " , ue);
		}
		return accessToken;
	}
	
	public void enviarNotificacion(EnviarNotificacionRequestVO request) {
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		String oauthBearer = this.getToken();
		
		String gpRequestJson = ConverterUtil.messageToJson(request);
		
		Unirest.setTimeouts(0, 0);
		
		try {
			HttpResponse<String> response = Unirest.post("https://iamdr.montepiedad.com.mx:4444/NMP/Utileria/EnvioCorreo/v2/")
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
			
		} catch (UnirestException e) {
			log.error("Exception {} " , e);
		}
	}

}
