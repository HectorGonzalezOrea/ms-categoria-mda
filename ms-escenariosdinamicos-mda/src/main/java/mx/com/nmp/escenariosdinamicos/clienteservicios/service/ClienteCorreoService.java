package mx.com.nmp.escenariosdinamicos.clienteservicios.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	public RespuestaVO sendEmailUser(EnviarNotificacionRequestVO request) {
		log.info("Entrando al metodo sendEmailUser ");
		RespuestaVO respuesta=new RespuestaVO();
		String token= getToken();
		JsonNode root;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(Common.HEADER_USUARIO, "nmp");
		headers.add(Common.HEADER_ID_CONSUMIDOR, "46");
		headers.add(Common.HEADER_ID_DESTINO, "100");
		headers.add(Common.HEADER_OAUTH_BEARER,token);
		headers.setBasicAuth( Common.OAG_USUARIO, Common.OAG_PASSWORD);		
		String requestJson = convertJson.messageToJson(request);
		HttpEntity<String> entity = new HttpEntity<>(requestJson,headers);
	    String response = restTemplate.postForObject(Common.URL_OAG_BASE+Common.ENDPOINT_ENVIAR_CORREO, entity,String.class);
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
		headers.setBasicAuth( Common.OAG_USUARIO, Common.OAG_PASSWORD);
		headers.add(Common.HEADER_USUARIO, "nmp");
		headers.add(Common.HEADER_ID_CONSUMIDOR, "46");
		headers.add(Common.HEADER_ID_DESTINO, "100");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.add(Common.GRANT_TYPE, "client_credentials");
	    params.add(Common.SCOPE, "UserProfile.me");
	    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
	    String result = restTemplate.postForObject(Common.URL_OAG_BASE+Common.ENDPOINT_GETTOKEN, requestEntity,String.class);
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
	

