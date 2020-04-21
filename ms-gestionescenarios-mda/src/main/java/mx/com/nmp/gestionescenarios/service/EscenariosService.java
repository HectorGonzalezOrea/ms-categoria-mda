package mx.com.nmp.gestionescenarios.service;

import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_CODE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_MESSAGE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.FORMATO_FECHA;
import static mx.com.nmp.gestionescenarios.utils.Constantes.FORMATO_FECHA_HORA;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mx.com.nmp.gestionescenarios.model.InternalServerError;
import mx.com.nmp.gestionescenarios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.gestionescenarios.mongodb.repository.EscenariosRepository;
import mx.com.nmp.gestionescenarios.mongodb.service.GestionEscenarioService;
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
	
	@Autowired
	private GestionEscenarioService gestionEscenarioMongoService;
	
	/*
	 * Almacenar y procesar Consolidado
	 */
	public InternalServerError almacenarProcesarConsolidado(String usuario, String vigencia, Boolean emergente, String ajuste, MultipartFile adjunto) {
		log.info("+++++++++++++++++++++++++++");
		log.info("almacenarProcesarConsolidado");
		log.info("+++++++++++++++++++++++++++");
		
		List<Integer> listIdConsolidado = this.registrarConsoidado(usuario, vigencia, ajuste, emergente, adjunto);
		
		Boolean funcionoCorrectemente = true;
		InternalServerError ise = null;
		
		if(listIdConsolidado != null) {
			if(Boolean.FALSE.equals(emergente)) {
				Integer requestId = this.calendarizarEjecucion();
				
				if(requestId != null)
					this.almacenarRegla(listIdConsolidado, requestId);
				else
					funcionoCorrectemente = false;
			} else {
				Boolean procesado = this.procesarConsolidado(usuario);
				if(Boolean.FALSE.equals(procesado))
					funcionoCorrectemente = false;
			}
		} else {
			funcionoCorrectemente = false;
		}
		
		log.info("funcionoCorrectemente: {}" , funcionoCorrectemente);
		
		if(Boolean.FALSE.equals(funcionoCorrectemente)) {
			ise = new InternalServerError();
            ise.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
            ise.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);
		}
		
		return ise;
	}
	
	/*
	 * Registrar Consolidado en Mongo
	 */
	private List<Integer> registrarConsoidado(String usuario, String vigencia, String ajuste, Boolean emergente, MultipartFile adjunto) {
		log.info("registrarConsolidado");
		
		return ajustePreciosConsolidadosController.registrarConsolidado(usuario, vigencia, ajuste, emergente, adjunto);
	}
	
	/*
	 * calendarizar ejecución en OAG
	 */
	private Integer calendarizarEjecucion() {
		log.info("calendarizarEjecucion");
		
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
		String dt = new SimpleDateFormat(FORMATO_FECHA_HORA).format(fechaActual);
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
	private void almacenarRegla(List<Integer> idConsolidado, Integer idRequest) {
		log.info("almacenarRegla");
		
		gestionEscenarioMongoService.actualizarConsolidado(idConsolidado, idRequest);
	}
	
	/*
	 * procesar consolidado recien guardado
	 */
	private Boolean procesarConsolidado(String usuario) {
		log.info("procesarConsolidado");
		
		Date fecha = new Date();
		String fechaAplicacion = new SimpleDateFormat(FORMATO_FECHA).format(fecha);
		
		log.info("Date: {}" , fechaAplicacion);
		
		return ajustePreciosConsolidadosController.procesarConsolidados(usuario, fechaAplicacion);
		
	}
	
	public void solictarCambioAnclaOroDolar(ModificarValorAnclaOroDolar peticion) {
		log.info("solictarCambioAnclaOroDolar");
		
		if(peticion != null) {
			gestionEscenarioMongoService.solictarCambioAnclaOroDolar(peticion);
		}
		
	}
	
}