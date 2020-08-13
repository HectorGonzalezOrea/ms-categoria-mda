package mx.com.nmp.escenariosdinamicos.clienteoag.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.escenariosdinamicos.api.ApiException;
import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.RespuestaVO;
import mx.com.nmp.escenariosdinamicos.correo.vo.AdjuntoVO;
import mx.com.nmp.escenariosdinamicos.correo.vo.AdjuntosVO;
import mx.com.nmp.escenariosdinamicos.correo.vo.NotificationVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.PreciosVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;
import mx.com.nmp.escenariosdinamicos.utils.ExcelUtils;
import mx.com.nmp.escenariosdinamicos.utils.TemplateHtmlEmail;

@Service
public class ClienteCorreoService {
	private static final Logger log = LoggerFactory.getLogger(ClienteCorreoService.class);
	private final ObjectMapper objectMapper = new ObjectMapper();

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
	@Value("${file.name.excel}")
	protected String nombreArchivo;

	@Value("${oag.resource.oauth.sendmail.to}")
	protected String para;

	@Value("${oag.resource.oauth.sendmail.from}")
	protected String de;

	@Retryable(value = {IOException.class,ApiException.class },maxAttempts=3, backoff=@Backoff(delay=5000))
	@Async
	public void sendEmailUser(List<PreciosVO> lstPrecios) throws ApiException, IOException  {
		log.info("Entrando al metodo sendEmailUser ");
		RespuestaVO respuesta = new RespuestaVO();
		String token = getToken();
		JsonNode root;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(Constantes.HEADER_USUARIO, headerUsuario);
		headers.add(Constantes.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
		headers.add(Constantes.HEADER_ID_DESTINO, headerIdDestino);
		headers.add(Constantes.HEADER_OAUTH_BEARER, token);
		headers.setBasicAuth(usuario, password);
		HttpEntity<String> entity = new HttpEntity<>(formatMessage(lstPrecios), headers);
		ResponseEntity<String> response = restTemplate.postForEntity(urlBase + servicioEnviarCorreo, entity,
				String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
				root = objectMapper.readTree(response.getBody());
				respuesta.setCodigo(root.path("respuesta").path("codigo").textValue());
				respuesta.setMensaje(root.path("respuesta").path("mensaje").textValue());
				
		}else if(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
			throw  new ApiException(500,"Error en la comunicación");
		}
		log.info("response sendEmailUser {}",response.getBody());

		
	}

	public String getToken() {
		log.info("Entrando al metodo getToken ");
		String accessToken = "";
		JsonNode root;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(usuario, password);
		headers.add(Constantes.HEADER_USUARIO, headerUsuario);
		headers.add(Constantes.HEADER_ID_CONSUMIDOR, headerIdConsumidor);
		headers.add(Constantes.HEADER_ID_DESTINO, headerIdDestino);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add(Constantes.GRANT_TYPE, "client_credentials");
		params.add(Constantes.SCOPE, "UserProfile.me");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(urlBase + servicioGetToken, requestEntity,
				String.class);
		try {
			if (result.getStatusCode() == HttpStatus.OK) {
				root = objectMapper.readTree(result.getBody());
				accessToken = root.path("access_token").textValue();
			}
		} catch (IOException e) {
			log.error("{0}", e);
		}
		return accessToken;
	}

	/**
	 * @param List PreciosVO
	 * @return archivo en base 64
	 */
	private String getFileBase64(List<PreciosVO> lstPreciosVO) {
		String base64File = null;
		ExcelUtils excel = new ExcelUtils();
		byte[] file = excel.crearExcelNotificaciones(lstPreciosVO);
		if (file != null) {
			base64File = Base64.getEncoder().encodeToString(file);
		}
		return base64File;
	}

	/**
	 * @param List           PreciosVO
	 * @param NotificationVO
	 * @return file excel base64 or table html in json
	 */
	private String formatMessage(List<PreciosVO> lstPreciosVO) {
		NotificationVO vo = new NotificationVO();
		vo.setPara(para);
		vo.setDe(de);
		vo.setAsunto(Constantes.ASUNTO_AJUSTE_PRECIOS_FALSE);
		Gson gson = new Gson();
		AdjuntosVO abjuntosVO = new AdjuntosVO();
		AdjuntoVO adjuntoVO = new AdjuntoVO();
		if (lstPreciosVO.size() > 10) {
			adjuntoVO.setNombreArchivo(nombreArchivo);
			adjuntoVO.setContenido(getFileBase64(lstPreciosVO));
			List<AdjuntoVO> lstAdjunto = new ArrayList<>();
			lstAdjunto.add(adjuntoVO);
			abjuntosVO.setAdjunto(lstAdjunto);
			vo.setAdjuntos(abjuntosVO);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(Constantes.TEMPLATE_NOTIFICACION_EMAIL_HEAD);
			TemplateHtmlEmail html = new TemplateHtmlEmail();
			sb.append(html.tablePrecios(lstPreciosVO).toString());
			sb.append(Constantes.TEMPLATE_NOTIFICACION_EMAIL_FOOTER);
			vo.setContenidoHTML(sb.toString());
		}

		return gson.toJson(vo);
	}
}
