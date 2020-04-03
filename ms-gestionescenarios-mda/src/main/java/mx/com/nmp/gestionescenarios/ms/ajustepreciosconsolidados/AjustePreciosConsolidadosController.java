package mx.com.nmp.gestionescenarios.ms.ajustepreciosconsolidados;

import java.io.File;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ACCEPT_KEY;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ACCEPT_VALUE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ORIGEN;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_DESTINO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_VIGENCIA;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_NOMBRE_AJUSTE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_EMERGENTE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.BODY_ADJUNTO;

@RestController
public class AjustePreciosConsolidadosController extends AjustePreciosConsolidadosBase {

	private static final Logger log = LoggerFactory.getLogger(AjustePreciosConsolidadosController.class);
	
	
	public Boolean registrarConsolidado(String usuario, String origen, String destino, String vigencia, String ajuste, Boolean emergente, MultipartFile adjunto) {
		
		Boolean registrado = false;
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(urlBase + servicio)
			  .header(HEADER_USUARIO, usuario)
			  .header(HEADER_ORIGEN, origen)
			  .header(HEADER_DESTINO, destino)
			  .header(HEADER_VIGENCIA, vigencia)
			  .header(HEADER_NOMBRE_AJUSTE, ajuste)
			  .header(HEADER_EMERGENTE, emergente.toString())
			  .header(HEADER_ACCEPT_KEY, apiKey)
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
			  .field(BODY_ADJUNTO, adjunto)
			  .asString();
			  //.field(BODY_ADJUNTO, new File("/path/to/file"))
			
			log.info("Estatus {}" , response.getStatus());
			log.info("Response {}" , response.getBody());
			
			if(response.getStatus() == 200)
				registrado = true;
			
		} catch (UnirestException e) {
			log.error("UnirestException {}" , e);
		}
		
		return registrado;
	}
	
	public void procesarConsolidados() {
		
		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post("http://localhost:8080/NMP/MotorDescuentosAPI/v1/consolidados/_procesar?fechaAplicacion=11/03/2020")
			  .header("Accept", "application/json")
			  .header("usuario", "user")
			  .header("origen", "portalMotorDescuentosAutomatizados")
			  .header("destino", "bluemix")
			  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			  .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
			  .asString();
		} catch (UnirestException e) {
			log.error("UnirestException {}" , e);
		}
		
	}
	
	
}
