package mx.com.nmp.escenariosdinamicos.clienteoag.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.util.StringUtils;
import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.RespuestaVO;
import mx.com.nmp.escenariosdinamicos.constantes.Constantes.Common;
import mx.com.nmp.escenariosdinamicos.oag.vo.EnviarNotificacionRequestVO;
import mx.com.nmp.escenariosdinamicos.utils.ConverterUtil;


@Service
public class ClienteCorreoService {
	private static final Logger log = LoggerFactory.getLogger(ClienteCorreoService.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	ConverterUtil convertJson= new ConverterUtil();
	
	@Value("${oag.resource.oauth.getToken.header.usuario}")
	protected String headerUsuario;
	
	@Value("${oag.resource.oauth.getToken.header.idConsumidor}")
	protected String headerIdConsumidor;
	
	@Value("${oag.resource.oauth.getToken.header.idDestino}")
	protected String headerIdDestino;
	
	@Value("${oag.urlBase}")
	protected String urlBase;
	
	@Value("${oag.usuario}")
	protected String usuario;
	
	@Value("${oag.password}")
	protected String password;
	
	@Value("${oag.servicio.oauth.getToken}")
	protected String servicioGetToken;
	
	@Value("${oag.resource.oauth.sendmail.endpoint}")
	protected String servicioEnviarCorreo;
	
	public RespuestaVO sendEmailUser(EnviarNotificacionRequestVO request) {
		log.info("Entrando al metodo sendEmailUser ");
		RespuestaVO respuesta=new RespuestaVO();
		String token= getToken();
		JsonNode root;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(Common.HEADER_USUARIO,headerUsuario);
		headers.add(Common.HEADER_ID_CONSUMIDOR,headerIdConsumidor);
		headers.add(Common.HEADER_ID_DESTINO, headerIdDestino);
		headers.add(Common.HEADER_OAUTH_BEARER,token);
		headers.setBasicAuth(usuario, password);		
		String requestJson = convertJson.messageToJson(request);
		HttpEntity<String> entity = new HttpEntity<>(requestJson,headers);
	    String response = restTemplate.postForObject(urlBase+servicioEnviarCorreo, entity,String.class);
	    try {
	    	if(StringUtils.isNotBlank(response)) {
	    		root=objectMapper.readTree(response);
				respuesta.setCodigo(root.path("respuesta").path("codigo").textValue());
				respuesta.setMensaje(root.path("respuesta").path("mensaje").textValue());	    		
	    	}
	    	
	    } catch (JsonProcessingException e) {
	    	log.info("Error al procesar el reponse "+e.getMessage());
		} catch (IOException e) {
			log.info("Error al obtener el response "+e.getMessage());
		}
	
		return respuesta;
	}
	
	public String getToken() {
		log.info("Entrando al metodo getToken ");
		String accessToken = "";
		JsonNode root;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(usuario, password);
		headers.add(Common.HEADER_USUARIO,headerUsuario);
		headers.add(Common.HEADER_ID_CONSUMIDOR,headerIdConsumidor);
		headers.add(Common.HEADER_ID_DESTINO, headerIdDestino);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.add(Common.GRANT_TYPE, "client_credentials");
	    params.add(Common.SCOPE, "UserProfile.me");
	    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
	    String result = restTemplate.postForObject(urlBase+servicioGetToken, requestEntity,String.class);
	    try {
			root=objectMapper.readTree(result);
			accessToken =  root.path("access_token").textValue();
		} catch (JsonProcessingException e) {
			log.info("Error al procesar el reponse "+e.getMessage());
		} catch (IOException e) {
			log.info("Error al obtener el response "+e.getMessage());
		}
		return accessToken;
	}
}
	

