package mx.com.nmp.establecimientoprecios.oag.controller;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.SCOPE;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_OAUTH_BEARER;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.STATUS_CODE_OK;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.establecimientoprecios.utils.ConvertStringToBase64;
import mx.com.nmp.establecimientoprecios.utils.ConverterUtil;

@RestController
public class OAGController extends OAGBaseController {

	private static final Logger log = LoggerFactory.getLogger(OAGController.class);
	private static final String BASIC = "Basic ";
	
	
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
			
			log.info("Status Code Response: {}" , statusCode);
			log.info("Body Response: {}" , response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO resp = ConverterUtil.StringJsonToObjectGetTokenResponseVO(response.getBody());
				accessToken = resp.getAccess_token();
			}
		} catch (UnirestException e) {
			log.error("Exception {} " , e);
		}
		
		return accessToken;
	}
	
	@PostMapping(path = "/gestionPrecios/v1/_ajustar")
	public Boolean gestionPrecios(@Valid @RequestBody GestionPreciosRequestVO request) {
		log.info("gestionPrecios");
		
		Boolean insertado = false;
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
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
				GestionPreciosResponseVO resp = ConverterUtil.StringJsonToObjectGestionPreciosResponseVO(response.getBody());
				
				log.info("resp: {}" , resp);
				
				insertado = true;
			}
			
		} catch (UnirestException e) {
			log.error("Exception {} " , e);
		}
		
		return insertado;
	}
	
	@PostMapping(path = "/valorAncla")
	public Boolean valorAncla() {
		log.info("valorAncla");
		
		Boolean insertado = true;
		
		return insertado;
	}
	
	@PostMapping(path = "/actualizarMonedas")
	public Boolean actualizarMonedas() {
		log.info("valorAncla");
		
		Boolean insertado = true;
		
		return insertado;
	}
	
	@PostMapping(path = "/almacenarMonedas")
	public Boolean almacenarMonedas() {
		log.info("valorAncla");
		
		Boolean insertado = true;
		
		return insertado;
	}
	
}
