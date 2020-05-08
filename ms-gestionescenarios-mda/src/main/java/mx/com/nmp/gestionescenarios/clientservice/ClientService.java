package mx.com.nmp.gestionescenarios.clientservice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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


import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.nmp.gestionescenarios.utils.Constantes;
import mx.com.nmp.gestionescenarios.utils.PropertiesVaribles;
import mx.com.nmp.gestionescenarios.vo.ResponseClientVO;

@Service
public class ClientService {
	private static final Logger log = LoggerFactory.getLogger(ClientService.class);

	@Autowired
	PropertiesVaribles properties= new PropertiesVaribles();
	ObjectMapper mapper = new ObjectMapper();
	
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
		params.add(Constantes.FECHA_APLICACION, convertLocalDateToString(fecha));
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		String url=properties.getUrlService()+properties.getEndPointConsolidados()+"?"+Constantes.FECHA_APLICACION+"="+convertLocalDateToString(fecha);
		ResponseEntity<String> response = restTemplate.postForEntity(url,requestEntity,String.class);
		ResponseClientVO vo=null;
		if(response.getStatusCode()== HttpStatus.OK) {
			if(response.getBody() !=null) {
				vo= new ResponseClientVO();
				try {
					vo=mapper.readValue(response.getBody(), ResponseClientVO.class);
					log.info("El codigo es "+response.getBody());
				} catch (IOException e) {
				  log.error("Error al serializar {}"+e.getMessage());
				}
			}
		}
		
		
		return vo;
	}
	
	private String convertLocalDateToString(LocalDate fecha) {
		String string = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.println("El formato de fecha es "+string);
		return string;
	}

}
