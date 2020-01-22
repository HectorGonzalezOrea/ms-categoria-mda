package mx.com.nmp.usuarios.oag.controller;

import static mx.com.nmp.usuarios.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.usuarios.utils.Constantes.SCOPE;
import static mx.com.nmp.usuarios.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_OAUTH_BEARER;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.usuarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioRequestVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioResponseVO;
import mx.com.nmp.usuarios.utils.ConvertStringToBase64;
import mx.com.nmp.usuarios.utils.ConverterUtil;

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
			
			log.info("Status Code Response: " + statusCode);
			log.info("Body Response: " + response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO resp = ConverterUtil.StringJsonToObjectGetTokenResponseVO(response.getBody());
				accessToken = resp.getAccess_token();
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return accessToken;
	}
	
	@PostMapping(path = "/identidadUsuario")
	public IdentidadUsuarioResponseVO identidadUsuario(IdentidadUsuarioRequestVO request) {
		Unirest.setTimeouts(0, 0);
		
		IdentidadUsuarioResponseVO resp = null;
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		String oauthBearer = this.getToken();
		
		request.setGrupo(grupo);
		
		log.info("Oauth Bearer: " + oauthBearer);
		
		String iRequestJson = ConverterUtil.messageToJson(request);
		
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicioIdentidad)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HEADER_USUARIO, "usuario")
			  .header(HEADER_OAUTH_BEARER, oauthBearer)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .body(iRequestJson)
			  .asString();
			
			int statusCode = response.getStatus();
			
			log.info("Status Code Response: " + statusCode);
			log.info("Body Response: " + response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				resp = ConverterUtil.StringJsonToObjectIdentidadUsuarioResponseVO(response.getBody());
			}
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return resp;
	}
	
}
