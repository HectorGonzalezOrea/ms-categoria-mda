package mx.com.nmp.gestionescenarios.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mx.com.nmp.gestionescenarios.ms.ajustepreciosconsolidados.AjustePreciosConsolidadosController;
import mx.com.nmp.gestionescenarios.oag.controller.OAGController;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscanarioRequestVO;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscanarioResponseVO;
import mx.com.nmp.gestionescenarios.oag.vo.IniciarEjecucionEscenarioRequestVO;
import mx.com.nmp.gestionescenarios.oag.vo.PeticionEscenarioVO;

@Service
public class EscenariosService {

	private static final Logger log = LoggerFactory.getLogger(EscenariosService.class);
	
	@Autowired
	private AjustePreciosConsolidadosController ajustePreciosConsolidadosController;
	
	@Autowired
	private OAGController oAGController;
	
	/*
	 * Almacenar y procesar Consolidado
	 */
	public void almacenarProcesarConsolidado(String usuario, String origen, String destino, String vigencia, String ajuste, Boolean emergente, MultipartFile adjunto) {
		
		Boolean registrado = this.registrarConsoidado(usuario, origen, destino, vigencia, ajuste, emergente, adjunto);
		
		if(Boolean.TRUE.equals(registrado)) {
			if(Boolean.FALSE.equals(emergente)) {
				Integer requestId = this.calendarizarEjecucion();
				
				
				if(requestId != null) {
					this.almacenarRegla(requestId);
				} else {
					
				}
			} else {
				this.procesarConsolidado();
			}
		}
	}
	
	/*
	 * Registrar Consolidado en Mongo
	 */
	private Boolean registrarConsoidado(String usuario, String origen, String destino, String vigencia, String ajuste, Boolean emergente, MultipartFile adjunto) {
		log.info("registrarConsoidado");
		
		return ajustePreciosConsolidadosController.registrarConsolidado(usuario, origen, destino, vigencia, ajuste, emergente, adjunto);
	}
	
	/*
	 * calendarizar ejecución en OAG
	 */
	private Integer calendarizarEjecucion() {
		
		IniciarEjecucionEscenarioRequestVO ieerVo = new IniciarEjecucionEscenarioRequestVO();
		// Escenario de Consolidados
		ieerVo.setId(Integer.valueOf(2));
		
		PeticionEscenarioVO peVo = new PeticionEscenarioVO();
		peVo.setIniciarEjecucionEscenarioRequest(ieerVo);
		
		CalendarizarEscanarioRequestVO requestVo = new CalendarizarEscanarioRequestVO();
		
		// Escenario de Consolidados
		requestVo.setId(Integer.valueOf(2));
		requestVo.setPeticionEscenario(peVo);
		
		Date fechaActual = new Date();
		String dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(fechaActual);
		requestVo.setFechaInicioPeticion(dt);
		
		CalendarizarEscanarioResponseVO cerVo = oAGController.calendarizarEscenario(requestVo);
		
		if(cerVo != null) {
			return cerVo.getIdPeticion();
		}
		
		return null;
	}
	
	/*
	 * almacenar regla en mongo
	 */
	private void almacenarRegla(Integer idRequest) {
		
	}
	
	/*
	 * procesar consolidado recien guardado
	 */
	private void procesarConsolidado() {
		
	}
	
}