package mx.com.nmp.gestionescenarios.clientservice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.gestionescenarios.cast.CastObjectGeneric;
import mx.com.nmp.gestionescenarios.model.client.dto.RequestInformacionEscenarioDto;
import mx.com.nmp.gestionescenarios.utils.Constantes;
import mx.com.nmp.gestionescenarios.utils.PropertiesVaribles;
import mx.com.nmp.gestionescenarios.vo.AnclaOroDolarVO;
import mx.com.nmp.gestionescenarios.vo.GestionMonedasVO;
import mx.com.nmp.gestionescenarios.vo.ResponseClientVO;
import mx.com.nmp.gestionescenarios.vo.ResponseEscenarioVO;

@Service
public class ClientService {
	private static final Logger log = LoggerFactory.getLogger(ClientService.class);

	@Autowired
	PropertiesVaribles properties= new PropertiesVaribles();
	ObjectMapper mapper = new ObjectMapper();
	CastObjectGeneric cast= new CastObjectGeneric();
	
	public ResponseClientVO enviarConsolidados(LocalDate fecha) {
		log.info("::: Entrando al método enviarConsolidados ::::");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(Constantes.HEADER_ORIGEN, properties.getOrigen());
		headers.add(Constantes.HEADER_DESTINO, properties.getDestino());
		headers.add(Constantes.HEADER_APIKEY_KEY, properties.getClientId());
		headers.add(Constantes.HEADER_USUARIO, properties.getUsuario());
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add(Constantes.FECHA_APLICACION, cast.convertLocalDateToString(fecha));
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		String url=properties.getUrlService()+properties.getEndPointConsolidados()+"?"+Constantes.FECHA_APLICACION+"="+cast.convertLocalDateToString(fecha);
		ResponseEntity<String> response = restTemplate.postForEntity(url,requestEntity,String.class);
		ResponseClientVO vo=null;
		if(response.getStatusCode()== HttpStatus.OK) {
			if(response.getBody() !=null) {
				vo= new ResponseClientVO();
				try {
					vo=mapper.readValue(response.getBody(), ResponseClientVO.class);
					log.info("El codigo es "+response.getBody());
				} catch (IOException e) {
				  log.error("Error enviarConsolidados {}"+e.getMessage());
				}
			}
		}
		
		
		return vo;
	}
	
	public ResponseEscenarioVO enviarInformacionReglaEscenario(RequestInformacionEscenarioDto request) {
		log.info("::: Entrando al método enviarInformacionReglaEscenario ::::");
		String jsonRequest=new Gson().toJson(request);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(Constantes.HEADER_ORIGEN, properties.getOrigen());
		headers.add(Constantes.HEADER_DESTINO, properties.getDestino());
		headers.add(Constantes.HEADER_APIKEY_KEY, properties.getClientIdEscenarioDinamico());
		List<MediaType> acceptableMediaTypes= new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		headers.add(Constantes.HEADER_USUARIO, properties.getUsuario());
		HttpEntity<String> entity = new HttpEntity<>(jsonRequest,headers);
		ResponseEntity<String> response = restTemplate.postForEntity(properties.getUrlEscenarioDinamico()+properties.getEndPointEscenarioDinamico(), entity,String.class);
		ResponseEscenarioVO vo=null;
		 if(response.getStatusCode() == HttpStatus.OK) {
			 if(response.getBody() !=null) {
				 
					 vo= new ResponseEscenarioVO();
					try {
						vo=mapper.readValue(response.getBody(), ResponseEscenarioVO.class);
					} catch (IOException e) {
						log.info("Error en el metodo enviarInformacionReglaEscenario {} "+e.getMessage());
					}
			 }
		 }
		
		
		
		return vo;
	}
	
	public String  enviarValoresAncla(AnclaOroDolarVO request) {
		log.info("::: Entrado al método enviarValoresAncla :::");
		String respuesta=null;
		String jsonRequest=new Gson().toJson(request);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(Constantes.HEADER_ORIGEN, properties.getOrigen());
		headers.add(Constantes.HEADER_DESTINO, properties.getDestino());
		headers.add(Constantes.HEADER_APIKEY_KEY, properties.getClientIdAnclaDolar());
		List<MediaType> acceptableMediaTypes= new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		headers.add(Constantes.HEADER_USUARIO, properties.getUsuario());
		HttpEntity<String> entity = new HttpEntity<>(jsonRequest,headers);
		ResponseEntity<String> response = restTemplate.postForEntity(properties.getUrlAnclaOro()+properties.getEndPointAnclaDolar(),entity,String.class);
		if(response.getStatusCode()==HttpStatus.OK) {
			if(response.getBody() !=null) {
				respuesta=response.getBody();
				log.info("response "+response.getBody());
			}
		}
		return respuesta;
	}
	
	public void enviarValoresMonedas(List<GestionMonedasVO> request) {
		log.info("::: Entrando al método enviarValoresMonedas :::");
		String jsonRequest=new Gson().toJson(request);
		log.info("Request ::: "+jsonRequest);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(Constantes.HEADER_ORIGEN, properties.getOrigen());
		headers.add(Constantes.HEADER_DESTINO, properties.getDestino());
		headers.add(Constantes.HEADER_APIKEY_KEY, properties.getClientIdMonedas());
		List<MediaType> acceptableMediaTypes= new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		headers.add(Constantes.HEADER_USUARIO, properties.getUsuario());
		HttpEntity<String> entity = new HttpEntity<>(jsonRequest,headers);
		ResponseEntity<String> response = restTemplate.postForEntity(properties.getUrlMonedas()+properties.getEndPointMonedas(),entity,String.class);
		if(response.getStatusCode()==HttpStatus.OK) {
			if(response.getBody() !=null) {
				log.info("La respuesta "+response.getBody());
			}
		}
		
		
	}


}
