package mx.com.nmp.escenariosdinamicos.clienteoag.service;

import java.util.ArrayList;
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
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestActualizarPrecioPartidaDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseReglasArbitrajeOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.PreciosVO;



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
	
	public String  actualizarPrecioPartida(RequestActualizarPrecioPartidaDto request) {
		 String token=clienteCorreo.getToken();
		 Gson gson = new Gson();
		 String jsonRequest=gson.toJson(request);
		 RestTemplate restTemplate = new RestTemplate();
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.add(Common.HEADER_USUARIO, headerUsuario);
		  headers.add(Common.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
		  headers.add(Common.HEADER_ID_DESTINO, headerIdDestino);
		  headers.add(Common.HEADER_OAUTH_BEARER,token);
		  headers.setBasicAuth(usuario, password);
		  HttpEntity<String> entity = new HttpEntity<>(jsonRequest,headers);
			//String url="https://dev1775-ms-establecimientoprecios-mda.mybluemix.net/NMP/MotorDescuentosAPI/v1/precios";
			 //ResponseEntity<String> response = restTemplate.postForEntity(url, entity,String.class);
		  List<PreciosVO> lstPrecios= new ArrayList<PreciosVO>();
		  clienteCorreo.sendEmailUser(lstPrecios);
		  return null;
	}
	
	
	
	
	
	 
	  

	

}
