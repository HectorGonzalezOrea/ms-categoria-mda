package mx.com.nmp.escenariosdinamicos.clienteoag.service;

import java.io.IOException;
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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;


import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.model.component.ProducerMessageComponent;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestActualizarPrecioPartidaDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseReglasArbitrajeOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PreciosVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;

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
	

	@Retryable(value= {IOException.class}, maxAttempts=3, backoff=@Backoff(delay=4000))
	public ResponseOAGDto reglaEscenarioDinamico(RequestReglaEscenarioDinamicoDto requestDto) {
		log.info(":: Entrado al metodo  actualizarPrecioPartida ::");
		String request = new Gson().toJson(requestDto);

		log.info("->{}" , request);
		
		ResponseOAGDto response = new ResponseOAGDto();
		
		if(request != null && !request.equals("")) {
			String token = clienteCorreo.getToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add(Constantes.HEADER_USUARIO, headerUsuario);
			headers.add(Constantes.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
			headers.add(Constantes.HEADER_ID_DESTINO, headerIdDestino);
			headers.add(Constantes.HEADER_OAUTH_BEARER, token);
			headers.setBasicAuth(usuario, password);
			
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> result = restTemplate.postForEntity(urlBase + endPointAjustePrecioPartidas, entity,
					String.class);

			if (result.getStatusCode() == HttpStatus.OK) {
					response = castObject.convertJsonToReponseOAGDto(result.getBody());
					pruducerMessage.producerReglaArbitraje(result.getBody());
				
			}
		}
		
		return response;
	}
	@Retryable(value= {IOException.class}, maxAttempts=3, backoff=@Backoff(delay=4000))
	public ResponseReglasArbitrajeOAGDto aplicarReglaArbitraje(RequestReglaEscenarioDinamicoDto requestEscenario ) {
		  log.info(":: Entrado al metodo aplicarReglaArbitraje  ::");
		  String request= new Gson().toJson(requestEscenario);
		  ResponseReglasArbitrajeOAGDto response = new ResponseReglasArbitrajeOAGDto();
		  
		  log.info("{}" , request);
		  
		  if(request != null && !request.equals("")) {
			  String token=clienteCorreo.getToken();
			  RestTemplate restTemplate = new RestTemplate();
			  HttpHeaders headers = new HttpHeaders();
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  headers.add(Constantes.HEADER_USUARIO, headerUsuario);
			  headers.add(Constantes.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
			  headers.add(Constantes.HEADER_ID_DESTINO, headerIdDestino);
			  headers.add(Constantes.HEADER_OAUTH_BEARER,token);
			  headers.setBasicAuth(usuario, password);	
			  HttpEntity<String> entity = new HttpEntity<>(request,headers);
			  ResponseEntity<String> result = restTemplate.postForEntity(urlBase+endPointReglasArbitraje, entity,String.class);
			  
			  if(result.getStatusCode() == HttpStatus.OK) {
					  response=  castObject.convertJsonToReglasArbitraje(result.getBody());
					  pruducerMessage.producerCambioPrecio(result.getBody());
			  }
		  }

		return response;
	}
	
	@Retryable(value= {IOException.class}, maxAttempts=3, backoff=@Backoff(delay=5000))
	public String actualizarPrecioPartida(RequestActualizarPrecioPartidaDto request) throws Throwable {
		String token = clienteCorreo.getToken();
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(request);

		log.info("{}" , request);

		if (jsonRequest != null && !jsonRequest.equals("")) {


			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add(Constantes.HEADER_USUARIO, headerUsuario);
			headers.add(Constantes.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
			headers.add(Constantes.HEADER_ID_DESTINO, headerIdDestino);
			headers.add(Constantes.HEADER_OAUTH_BEARER, token);
			headers.setBasicAuth(usuario, password);
			List<PreciosVO> lstPrecios = new ArrayList<>();
			clienteCorreo.sendEmailUser(lstPrecios);
			
		}
		return null;
	}
	
	public int[] obtenerVentas(List<String> listaDias) {
		String[] dias = new String[listaDias.size()];
		int[] diasaux = new int[listaDias.size()];
		dias = listaDias.toArray(dias);
		for (int i = 0; i < dias.length; i++) {
			if (dias[i].equals(Constantes.CERO)) {
				diasaux[i] = 0;
			}
			if(dias[i].equals(Constantes.X)&&i==0){
				diasaux[i] = dias.length;
			}
			if(dias[i].equals(Constantes.S)&&i>0){
				diasaux[i] = diasaux[i-1]+(dias.length)-1;
			}
			if(dias[i].equals(Constantes.B)&&i>0){
				diasaux[i]=diasaux[i-1]-1;
			}
			if(dias[i].equals(Constantes.M)&&i>0){
				diasaux[i]=diasaux[i-1];
			}
		}
		return diasaux;
	}

}
