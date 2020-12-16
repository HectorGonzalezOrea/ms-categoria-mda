package mx.com.nmp.usuarios.oag.client.service;

import static mx.com.nmp.usuarios.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.usuarios.utils.Constantes.SCOPE;
import static mx.com.nmp.usuarios.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_OAUTH_BEARER;
import static mx.com.nmp.usuarios.utils.Constantes.STATUS_CODE_NA;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.usuarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioRequestVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioResponseVO;
import mx.com.nmp.usuarios.oag.vo.ProfileVO;
import mx.com.nmp.usuarios.utils.ConvertStringToBase64;
import mx.com.nmp.usuarios.utils.ConverterUtil;

@Service
public class OAGService extends OAGBaseService {

	private static final Logger log = LoggerFactory.getLogger(OAGService.class);
	
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
			
			log.info("Status Code Response: {}" , statusCode);
			log.info("Body Response: {}" , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO resp = ConverterUtil.stringJsonToObjectGetTokenResponseVO(response.getBody());
				accessToken = resp.getAccess_token();
			}
		} catch (UnirestException e) {
			log.error("Error al obtener el token: {0}", e);
		}
		
		return accessToken;
	}
	
	public Object identidadUsuario(IdentidadUsuarioRequestVO request, String oauthBearer, String userLoggeado) {
		log.info("identidadUsuario");
		
		Unirest.setTimeouts(0, 0);
		
		IdentidadUsuarioResponseVO resp = null;
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		
		request.setGrupo(grupo);
		//request.setGrupo("GrupoMDA");
		log.info("HttpHeaders.CONTENT_TYPE: {}" , MediaType.APPLICATION_JSON);
		log.info("usuario: {}" , userLoggeado);
		log.info("Oauth Bearer: {}" , oauthBearer);
		log.info("headerIdDestinor: {}" , headerIdDestino);
		log.info("headerIdConsumidor: {}" , headerIdConsumidor);
		log.info("autenticacionBasica: {}" , autenticacionBasica);
		
		String iRequestJson = ConverterUtil.messageToJson(request);
		
		log.info("iRequestJson: {}" , iRequestJson);
		
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicioIdentidad)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HEADER_USUARIO, usuario)
			  .header(HEADER_OAUTH_BEARER, oauthBearer)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .body(iRequestJson)
			  .asString();
			
			int statusCode = response.getStatus();
			
			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				return ConverterUtil.stringJsonToObjectIdentidadUsuarioResponseVO(response.getBody());
			}
			
			if (statusCode == STATUS_CODE_NA) {
				return ConverterUtil.stringJsonToObjectTokenProviderErrorVO(response.getBody());
			}
			
		} catch (UnirestException e) {
			log.error("Error en identidadUsuario: {0}", e);
		}
		return resp;
	}
	
	public Object identidadUsuario(String oauthBearer) {
		log.info("identidadUsuario");
		
		Unirest.setTimeouts(0, 0);
		
		ProfileVO resp = null;
		
		String autenticacionBearer = "Bearer " + oauthBearer;
		String url=urlBase + servicioProfile;
		log.info("endpoint: {}" ,url);
		log.info("headerUsuario: {}" , headerUsuario);
		log.info("headerIdDestinor: {}" , headerIdDestino);
		log.info("headerIdConsumidor: {}" , headerIdConsumidor);
		log.info("autenticacionBearer: {}" , autenticacionBearer);
		
		try {
			HttpResponse<String> response =  Unirest.get(urlBase + servicioProfile)
			  .header(HEADER_USUARIO, headerUsuario)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBearer)
			  .asString();
			
			int statusCode = response.getStatus();
			
			log.info("Status Code Response: {} " , statusCode);
			log.info("Body Response: {} " , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				return ConverterUtil.stringJsonToObjectProfileVO(response.getBody());
			}
			
			if (statusCode == STATUS_CODE_NA) {
				return ConverterUtil.stringJsonToObjectTokenProviderErrorVO(response.getBody());
			}
			
		} catch (UnirestException e) {
			log.error("Error en identidadUsuario: {0}", e);
		}
		return resp;
	}
}
