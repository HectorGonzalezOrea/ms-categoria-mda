package mx.com.nmp.consolidados.oag.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.consolidados.constantes.Constantes.Common;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasRequestVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasResponseVO;
import mx.com.nmp.consolidados.oag.vo.GetTokenResponseVO;
import mx.com.nmp.consolidados.utils.ConvertStringToBase64;
import mx.com.nmp.consolidados.utils.ConverterUtil;
import mx.com.nmp.consolidados.oag.vo.EnviarNotificacionRequestVO;

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
	
	public Boolean eliminarCalendarizacion(String idPeticion) {
		log.info("eliminarCalendarizacion");
		Boolean eliminado = false;
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		String oauthBearer = this.getToken();
		Unirest.setTimeouts(0, 0);
		try {
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
		} catch (UnirestException ue) {
			log.error("UnirestException: {} " , ue);
		}
		return eliminado;
	}
	
	public ArbitrajePreciosPartidasResponseVO validarArbitrajePreciosPartidas(ArbitrajePreciosPartidasRequestVO request) {
		log.info("validarArbitrajePreciosPartidas");
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		String oauthBearer = this.getToken();
		
		String iRequest = ConverterUtil.messageToJson(request);
		
		log.info("iRequest: {} " , iRequest.toString());
		
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
			log.error("UnirestException: {} " , ue);
		}
		
		return respVo;
	}
	
	public void enviarNotificacion(EnviarNotificacionRequestVO request) {
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = BASIC + ConvertStringToBase64.encode(credenciales);
		
		String oauthBearer = this.getToken();
		
		String gpRequestJson = ConverterUtil.messageToJson(request);
		
		Unirest.setTimeouts(0, 0);
		
		try {
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
			
		} catch (UnirestException e) {
			log.error("Exception {} " , e);
		}
	}
	
	//param producto de tipo InfoProducto
	//retorna string de tag(false/true) de servicioSoap
	//En la implementacion del query iterar los productos y por cada iteracion consultar si es arbitrado
	public String esArbitrado(InfoProducto producto) {
		CastConsolidados cast=new CastConsolidados();
		String output = null;
		StringBuilder sb = new StringBuilder(Common.WSDL_ARBITRADO);
		Client client = ClientBuilder.newClient((Configuration) new ClientConfig());
		WebTarget target = client.target(sb.toString());
		Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_XML);
		invocationBuilder.header(Common.USER, "usuario");
		invocationBuilder.header(Common.DESTINO, "idDestino");
		invocationBuilder.header(Common.CONSUMIDOR, "idConsumidor");
		Response response = invocationBuilder.put(Entity.xml(cast.jaxbObjectToXML(cast.fillVoValuesProducto(producto))));
		int statusCode = response.getStatus();
		if (statusCode ==200) {
			output = response.readEntity(String.class);
			log.info("+++++++++++++++++++++++++++++++++++++");
			log.info(output);
			log.info("+++++++++++++++++++++++++++++++++++++");
		}
		log.info("cumpleArbitraje? {}", cast.getTagValue(output,Common.TAG_ARBITRADO));
		return cast.getTagValue(output,Common.TAG_ARBITRADO);
	}
}
