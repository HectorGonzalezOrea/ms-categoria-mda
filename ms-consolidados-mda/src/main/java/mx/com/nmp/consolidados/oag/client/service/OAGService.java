package mx.com.nmp.consolidados.oag.client.service;

import static mx.com.nmp.consolidados.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_OAUTH_BEARER;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.consolidados.utils.Constantes.SCOPE;
import static mx.com.nmp.consolidados.utils.Constantes.STATUS_CODE_OK;
import static mx.com.nmp.consolidados.utils.Constantes.BASIC;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
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

import mx.com.nmp.consolidados.api.ApiException;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasRequestVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasResponseVO;
import mx.com.nmp.consolidados.oag.vo.GetTokenResponseVO;
import mx.com.nmp.consolidados.utils.Constantes;
import mx.com.nmp.consolidados.utils.ConvertStringToBase64;
import mx.com.nmp.consolidados.utils.ConverterUtil;
import mx.com.nmp.consolidados.oag.vo.EnviarNotificacionRequestVO;

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
			log.info("Response {} " , response.getBody());
			if (statusCode == STATUS_CODE_OK) {
				GetTokenResponseVO resp = ConverterUtil.stringJsonToObjectGetTokenResponseVO(response.getBody());
				accessToken = resp.getAccess_token();
			}
		} catch (UnirestException ue) {
			log.error("Ocurrio un error al obtener el token : {} " , ue.getMessage());
		}
		return accessToken;
	}
	
	@Retryable(value= {UnirestException.class},maxAttempts=3, backoff=@Backoff(delay=2000))
	public Boolean eliminarCalendarizacion(String idPeticion) throws UnirestException {
		log.info("eliminarCalendarizacion");
		Boolean eliminado = false;
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		String oauthBearer = this.getToken();
		Unirest.setTimeouts(0, 0);
			HttpResponse<String> response = Unirest.delete(urlBase + servicioEliminarCalendarizacion + "/" + idPeticion)
			  .header(HEADER_USUARIO, headerUsuario)
			  .header(HEADER_OAUTH_BEARER, oauthBearer)
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

		return eliminado;
	}
	
	
	public ArbitrajePreciosPartidasResponseVO validarArbitrajePreciosPartidas(ArbitrajePreciosPartidasRequestVO request) {
		log.info("validarArbitrajePreciosPartidas");
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		String oauthBearer = this.getToken();
		
		String iRequest = ConverterUtil.messageToJson(request);
		
		log.info("iRequest: {} " , iRequest);
		
		ArbitrajePreciosPartidasResponseVO respVo = null;
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicioValidarArbitrajePreciosPartidas)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HEADER_USUARIO, headerUsuario)
			  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
			  .header(HEADER_ID_DESTINO, headerIdDestino)
			  .header(HEADER_OAUTH_BEARER, oauthBearer)
			  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
			  .body(iRequest)
			  .asString();
			
			int estatus = response.getStatus();
			log.info("Status: {} " , response.getStatus());
			log.info("Body Response: {} " , response.getBody());
			
			if(estatus == STATUS_CODE_OK) {
				respVo = ConverterUtil.stringJsonToObjectArbitrajePreciosPartidasResponseVO(response.getBody());
			}
			
		} catch (UnirestException ue) {
			log.error("Error en servicio validar arbitraje: {} " , ue.getMessage());
		}
		
		return respVo;
	}
	
	
	@Retryable(value= {UnirestException.class,ApiException.class},maxAttempts=3, backoff=@Backoff(delay=3000))
	@Async
	public void enviarNotificacion(EnviarNotificacionRequestVO request) throws ApiException, UnirestException{
		log.info("::: Entrando a enviarNotificacion ::::");
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		String oauthBearer = this.getToken();
		
		String gpRequestJson = ConverterUtil.messageToJson(request);
		
		Unirest.setTimeouts(0, 0);
		
			HttpResponse<String> response = Unirest.post(urlBase + servicioEnviarCorreo)
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
			if(statusCode==500) {
				throw  new ApiException(500,"Error en la comunicación");
			}
			
			

	}
	
	//param producto de tipo InfoProducto
	//retorna string de tag(false/true) de servicioSoap
	//En la implementacion del query iterar los productos y por cada iteracion consultar si es arbitrado
	public String esArbitrado(InfoProducto producto) {
		CastConsolidados cast=new CastConsolidados();
		String output = null;
		StringBuilder sb = new StringBuilder(Constantes.WSDL_ARBITRADO);
		Client client = ClientBuilder.newClient((Configuration) new ClientConfig());
		WebTarget target = client.target(sb.toString());
		Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_XML);
		invocationBuilder.header(Constantes.USER, "usuario");
		invocationBuilder.header(Constantes.DESTINO, "idDestino");
		invocationBuilder.header(Constantes.CONSUMIDOR, "idConsumidor");
		Response response = invocationBuilder.put(Entity.xml(cast.jaxbObjectToXML(cast.fillVoValuesProducto(producto))));
		int statusCode = response.getStatus();
		if (statusCode ==200) {
			output = response.readEntity(String.class);
			log.info("+++++++++++++++++++++++++++++++++++++");
			log.info(output);
			log.info("+++++++++++++++++++++++++++++++++++++");
		}
		log.info("cumpleArbitraje? {}", cast.getTagValue(output,Constantes.TAG_ARBITRADO));
		return cast.getTagValue(output,Constantes.TAG_ARBITRADO);
	}
}
