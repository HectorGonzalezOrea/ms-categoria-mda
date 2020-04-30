package mx.com.nmp.escenariosdinamicos.clienteoag.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;


import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.constantes.Constantes.Common;
import mx.com.nmp.escenariosdinamicos.model.component.ProducerMessageComponent;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseReglasArbitrajeOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;



@Service
public class ClientOAGService {
	
	private static final Logger log = LoggerFactory.getLogger(ClientOAGService.class);
	@Autowired
	ClienteCorreoService clienteCorreo;
	@Autowired
	CastObjectGeneric castObject= new CastObjectGeneric();
	@Autowired
	ProducerMessageComponent pruducerMessage;
	
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
		
	@Value("${endpoint.motorDescuentos.ajustePreciosPartidas}")
	protected String endPointAjustePrecioPartidas;
	
	@Value("${oag.resource.oauth.endPoint.reglas.arbitraje}")
	protected String endPointReglasArbitraje;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public ResponseOAGDto reglaEscenarioDinamico(RequestReglaEscenarioDinamicoDto requestDto) {
        log.info(":: Entrado al metodo  actualizarPrecioPartida ::");
        String request =  new Gson().toJson(requestDto);
		  String token=clienteCorreo.getToken();
		  RestTemplate restTemplate = new RestTemplate();
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.add(Common.HEADER_USUARIO, headerUsuario);
		  headers.add(Common.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
		  headers.add(Common.HEADER_ID_DESTINO, headerIdDestino);
		  headers.add(Common.HEADER_OAUTH_BEARER,token);
		  headers.setBasicAuth(usuario, password);	
		  HttpEntity<String> entity = new HttpEntity<>(request,headers);
		  ResponseEntity<String> result = restTemplate.postForEntity(urlBase+endPointAjustePrecioPartidas, entity,String.class);
		  
		  ResponseOAGDto response= new ResponseOAGDto();
		  if(result.getStatusCode() == HttpStatus.OK) {
			  if(result.getBody() !=null) {
				  response= castObject.convertJsonToReponseOAGDto(result.getBody());
				  pruducerMessage.producerReglaArbitraje(result.getBody());
			  }
		  }
		 
		  return response;
	}
	
	public  ResponseReglasArbitrajeOAGDto aplicarReglaArbitraje(RequestReglaEscenarioDinamicoDto requestEscenario ) {
		  log.info(":: Entrado al metodo aplicarReglaArbitraje  ::");
		  String request= new Gson().toJson(requestEscenario);
		  String token=clienteCorreo.getToken();
		  RestTemplate restTemplate = new RestTemplate();
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.add(Common.HEADER_USUARIO, headerUsuario);
		  headers.add(Common.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
		  headers.add(Common.HEADER_ID_DESTINO, headerIdDestino);
		  headers.add(Common.HEADER_OAUTH_BEARER,token);
		  headers.setBasicAuth(usuario, password);	
		  HttpEntity<String> entity = new HttpEntity<>(request,headers);
		  ResponseEntity<String> result = restTemplate.postForEntity(urlBase+endPointReglasArbitraje, entity,String.class);
		  ResponseReglasArbitrajeOAGDto response = new ResponseReglasArbitrajeOAGDto();
		  if(result.getStatusCode() == HttpStatus.OK) {
			  if(result.getBody() !=null) {
				  response=  castObject.convertJsonToReglasArbitraje(result.getBody());
				  pruducerMessage.producerCambioPrecio(result.getBody());
			  }
		  }
		  
		return response;
	}
	
	
	
	
	
	
	 
	  
	  
	  private String requestReglaArbitraje(PartidaVO vo ) {
		  log.info("Generando request json reglas arbitraje");
		  JsonNode rootNode=objectMapper.createObjectNode();
		  JsonNode childNode = objectMapper.createObjectNode();
		  String request=null;
		 try {		  
			  ((ObjectNode) childNode).put("idPartida", vo.getIdPartida());
			  ((ObjectNode) childNode).put("sku", vo.getSku());
			  ((ObjectNode) childNode).put("precioVenta", vo.getPrecioVenta());
			  ((ObjectNode) childNode).put("montoPrestamo", vo.getMontoPrestamo());
			  ((ObjectNode) rootNode).set("partida", childNode);
			  request=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
			log.info("Error al genrar el request de reglas arbitraje "+e.getMessage());
		}
		  return request;
	  }

	
	

}
