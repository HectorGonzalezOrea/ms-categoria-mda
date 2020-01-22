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
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	@PostMapping(path = "/gestionPrecios/v1/_ajustar")
	public Boolean gestionPrecios(@Valid @RequestBody GestionPreciosRequestVO request) {
		log.info("gestionPrecios");
		
		Boolean insertado = false;
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		
		String oauthBearer = this.getToken();
		
		log.info("Oauth Bearer: " + oauthBearer);
		
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
			
			log.info("Status Code Response: " + statusCode);
			log.info("Body Response: " + response.getBody());
			
			if (statusCode == STATUS_CODE_OK) {
				GestionPreciosResponseVO resp = ConverterUtil.StringJsonToObjectGestionPreciosResponseVO(response.getBody());
				
				log.info("resp: " + resp.toString());
				
				insertado = true;
			}
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return insertado;
	}
	
	/*
	@PostMapping(path = "/getToken2")
	public String getToken2() {
		
		log.info("Get Token OAG");
		
		Client client = ClientBuilder.newClient((Configuration) new ClientConfig());
		
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		headers.add(HttpHeaders.AUTHORIZATION,"Basic cG9ydGFsaW50ZWxpZ2VuY2lhOnNpZVRFSXVrZHlZRG8zMTRRUA==");
		headers.add(HEADER_USUARIO,headerUsuario);
		headers.add(HEADER_ID_CONSUMIDOR,headerIdConsumidor);
		headers.add(HEADER_ID_DESTINO,headerIdDestino);
		
		Form form = new Form();
		
		form.param(GRANT_TYPE, grantType);
		form.param(SCOPE, scope);
		
		Response response = client.target(urlBase + servicioGetToken)
				.request()
				.headers(headers)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
				
		String accessToken = "";
		
		log.info("response " + response);
		
		
		int statusCode = response.getStatus();
		
		try {
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO respVO = response.readEntity(GetTokenResponseVO.class);
				
				log.info(respVO.toString());
				
				Map rawJson = response.readEntity(Map.class);
				
				log.info(rawJson.toString());
				
				accessToken = respVO.getAccess_token();
				
			} else {
				accessToken = "";
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			  response.close();
		}
		
		return accessToken;
	}
	*/
	
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
