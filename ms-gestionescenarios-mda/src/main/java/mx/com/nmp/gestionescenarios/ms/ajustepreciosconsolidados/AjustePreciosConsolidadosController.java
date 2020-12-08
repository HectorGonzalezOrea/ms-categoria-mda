package mx.com.nmp.gestionescenarios.ms.ajustepreciosconsolidados;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.gestionescenarios.oag.vo.ConsolidadoVO;
import mx.com.nmp.gestionescenarios.oag.vo.ConsultarConsolidadoResponseVO;
import mx.com.nmp.gestionescenarios.utils.ConverterUtil;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ORIGEN;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_DESTINO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_VIGENCIA;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_NOMBRE_AJUSTE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_EMERGENTE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.BODY_ADJUNTO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.FECHA_APLICACION;
import static mx.com.nmp.gestionescenarios.utils.Constantes.VIGENCIA;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.gestionescenarios.utils.Constantes.METHOD_POST;
import static mx.com.nmp.gestionescenarios.utils.Constantes.FORMATO_FECHA;

@RestController
public class AjustePreciosConsolidadosController extends AjustePreciosConsolidadosBase {

	private static final Logger log = LoggerFactory.getLogger(AjustePreciosConsolidadosController.class);
	
	/*
	 * Registrar Consolidado
	 *
	*/
	public List<Integer> registrarConsolidado(String usuario, String vigencia, String ajuste, Boolean emergente, MultipartFile adjunto) {

		log.info("registrarConsolidado");
		
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		// creación de archivo
		ConverterUtil.convertMultipartFileToFile(adjunto);
		
		List<Integer> listIdConsolidado = null;
		
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart(BODY_ADJUNTO, adjunto.getOriginalFilename(), RequestBody
						.create(okhttp3.MediaType.parse(MediaType.APPLICATION_OCTET_STREAM), new File(adjunto.getOriginalFilename())))
				.build();

		Request request = new Request.Builder()
				.url(urlBase + servicioRegsitrarConsolidados)
				.method(METHOD_POST, requestBody)
				.addHeader(HEADER_USUARIO, usuario)
				.addHeader(HEADER_ORIGEN, origen)
				.addHeader(HEADER_DESTINO, destino)
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
				.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
				.addHeader(HEADER_APIKEY_KEY, apiKey)
				.addHeader(HEADER_VIGENCIA, vigencia)
				.addHeader(HEADER_NOMBRE_AJUSTE, ajuste)
				.addHeader(HEADER_EMERGENTE, emergente.toString())
				.build();
		
		try {
			Response response = client.newCall(request).execute();
			
			log.info("Response Code: {}" , response.code());
			log.info("Response: {}" , response.body());
			
			if(response.code() == 200) {
				
				ConsultarConsolidadoResponseVO consolidadoList = this.consultarConsolidado(usuario, origen, destino, vigencia);
				listIdConsolidado = new ArrayList<>();
				
				for(ConsolidadoVO c : consolidadoList) {
					listIdConsolidado.add(c.getIdArchivo());
				}
			}
			
		} catch (IOException e) {
			log.error("ERROR : {0}" , e);
		}

		return listIdConsolidado;
	}
	
	
	/*
	 * Consultar Consolidado
	 */
	private ConsultarConsolidadoResponseVO consultarConsolidado(String usuario, String origen, String destino, String vigencia) {
		log.info("consultarConsolidado");
		
		Unirest.setTimeouts(0, 0);
		
		ConsultarConsolidadoResponseVO consolidadoVO = null;
		
		try {
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(VIGENCIA, vigencia);
			
			HttpResponse<String> response = Unirest.get(urlBase + servicioConsultaronsolidados)
			  .header(HEADER_USUARIO, usuario)
			  .header(HEADER_ORIGEN, origen)
			  .header(HEADER_DESTINO, destino)
			  .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
			  .header(HEADER_APIKEY_KEY, apiKey)
			  .queryString(parameters)
			  .asString();
			
			log.info("Estatus {}" , response.getStatus());
			log.info("Response {}" , response.getBody());
			
			if(response.getStatus() == 200) {
				consolidadoVO = ConverterUtil.stringJsonToObjectConsultarConsolidadoResponseVO(response.getBody());
			}
		} catch (UnirestException e) {
			log.error("Error consultarConsolidado {0}" , e);
		}

		return consolidadoVO;
	}
	
	/*
	 * Procesar consolidado
	 */
	public Boolean procesarConsolidados(String usuario, String fechaAplicacion) {
		log.info("procesarConsolidados");
		
		Boolean procesado = false;
		
		Unirest.setTimeouts(0, 0);
		try {
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(FECHA_APLICACION, fechaAplicacion);
			
			HttpResponse<String> response = Unirest.post(urlBase + servicioProcesarConsolidados)
			.header(HEADER_USUARIO, usuario)
			.header(HEADER_ORIGEN, origen)
			.header(HEADER_DESTINO, destino)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
			.header(HEADER_APIKEY_KEY, apiKey)
			.queryString(parameters)
			.asString();
			
			log.info("Estatus {}" , response.getStatus());
			log.info("Response {}" , response.getBody());
			
			if(response.getStatus() == 200) {
				procesado = true;
			}
			
		} catch (UnirestException e) {
			log.error("Error  procesarConsolidados {0}" , e);
		}
		
		return procesado;
	}
	
}
