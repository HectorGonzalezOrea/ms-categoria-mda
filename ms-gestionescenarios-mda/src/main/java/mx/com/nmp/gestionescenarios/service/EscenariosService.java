package mx.com.nmp.gestionescenarios.service;

import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_CODE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_MESSAGE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.FORMATO_FECHA;
import static mx.com.nmp.gestionescenarios.utils.Constantes.FORMATO_FECHA_HORA;

import static mx.com.nmp.gestionescenarios.utils.Constantes.ID_ESCENARIO_CONSOLIDADO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ID_PETICION_ESCENARIO_CONSOLIDADO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ID_ESCENARIO_ANCLA_ORO_DOLAR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ID_PETICION_ESCENARIO_ANCLA_ORO_DOLAR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ID_PETICION_ESCENARIO_MONEDA_ORO;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ID_ESCENARIO_MONEDA_ORO;



import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import mx.com.nmp.gestionescenarios.model.InternalServerError;
import mx.com.nmp.gestionescenarios.model.ListaMonedas;
import mx.com.nmp.gestionescenarios.model.ListaMonedasInner;
import mx.com.nmp.gestionescenarios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.gestionescenarios.model.ValorAnclaOroDolar;
import mx.com.nmp.gestionescenarios.mongodb.entity.AnclaOroDolarEntity;
import mx.com.nmp.gestionescenarios.mongodb.entity.MonedasEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.EscenariosRepository;
import mx.com.nmp.gestionescenarios.mongodb.service.GestionEscenarioService;
import mx.com.nmp.gestionescenarios.mongodb.service.SequenceGeneratorService;
import mx.com.nmp.gestionescenarios.ms.ajustepreciosconsolidados.AjustePreciosConsolidadosController;
import mx.com.nmp.gestionescenarios.oag.controller.OAGController;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscenarioRequestVO;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscenarioResponseVO;
import mx.com.nmp.gestionescenarios.oag.vo.IniciarEjecucionEscenarioRequestVO;
import mx.com.nmp.gestionescenarios.oag.vo.PeticionEscenarioVO;

@Service
public class EscenariosService {

	private static final Logger log = LoggerFactory.getLogger(EscenariosService.class);
	
	public static final String ORO = "oro";
	public static final String ID = "_id";
	
	private static final String GESTIONESCENARIO_SEQ_KEY = "gestionEscenario_sequence";
	private static final String MONEDAS_SEQ_KEY = "monedas_sequence";
	
	@Autowired
	private AjustePreciosConsolidadosController ajustePreciosConsolidadosController;
	
	@Autowired
	private OAGController oAGController;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
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
				// Se calendariza la ejecución de los escenarios de consolidados
				Integer requestId = this.calendarizarEjecucion(ID_PETICION_ESCENARIO_CONSOLIDADO, ID_ESCENARIO_CONSOLIDADO);
				
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
	private Integer calendarizarEjecucion(Integer idPeticion, Integer idEjecutarEscenario) {
		log.info("calendarizarEjecucion");
		
		IniciarEjecucionEscenarioRequestVO ieerVo = new IniciarEjecucionEscenarioRequestVO();
		// Escenario de Consolidados
		ieerVo.setId(Integer.valueOf(idEjecutarEscenario));
		
		PeticionEscenarioVO peVo = new PeticionEscenarioVO();
		peVo.setIniciarEjecucionEscenarioRequest(ieerVo);
		
		CalendarizarEscenarioRequestVO requestVo = new CalendarizarEscenarioRequestVO();
		
		// Escenario de Consolidados
		requestVo.setId(Integer.valueOf(idPeticion));
		requestVo.setPeticionEscenario(peVo);
		
		Date fechaActual = new Date();
		String dt = new SimpleDateFormat(FORMATO_FECHA_HORA).format(fechaActual);
		requestVo.setFechaInicioPeticion(dt);
		
		CalendarizarEscenarioResponseVO cerVo = oAGController.calendarizarEscenario(requestVo);
		
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
	
	
	/*
	 *  Registrar valores de monedas
	 */
	
	public Boolean registrarMonedas (ListaMonedas peticion) {
		
		Boolean insertado = null;
		MonedasEntity moneda =null;		
		
		if (peticion!= null) {
			moneda = new MonedasEntity ();
			
			for(ListaMonedasInner lmi:peticion) {
			moneda.setTipo(lmi.getTipo());
			moneda.setOro(lmi.isOro());
			moneda.setPrecio(lmi.getPrecio());
			LocalDate locateDate = LocalDate.now();
			moneda.setFechaCreacion(locateDate);
			moneda.setActualizadoPor(lmi.getActualizadoPor());
			
			Integer requestId = this.calendarizarEjecucion(ID_PETICION_ESCENARIO_MONEDA_ORO, ID_ESCENARIO_MONEDA_ORO);
			moneda.setRequestId(requestId);
			
			Long id = sequenceGeneratorService.generateSequence(MONEDAS_SEQ_KEY);
			moneda.setId(id);
			}
			
			
			mongoTemplate.insert(moneda);
			insertado = true;

		}
		
		
		return insertado;
	}
	
	/*
	 * Consulta Monedas
	 */
	
	public List<ListaMonedasInner> consultarMonedas (Boolean oro) {
		
		
		List<MonedasEntity> busquedaList = this.buscarMonedas(oro);
		List<ListaMonedasInner> monedas = null;
		if(CollectionUtils.isNotEmpty(busquedaList)) {
			log.info("buscarMonedas");
			monedas= new ArrayList<>();
			ListaMonedasInner lm = null;
			for(MonedasEntity me:busquedaList) {
				lm = new ListaMonedasInner();
				lm.setId(me.getId());
				lm.setTipo(me.getTipo());
				lm.setPrecio(me.getPrecio());
				lm.setOro(oro);
				lm.setFechaCreacion(me.getFechaCreacion());
				lm.setActualizadoPor(me.getActualizadoPor());
				monedas.add(lm);
			}
			
						
			
			
		}
		
		return monedas;
		
	}
		
	/*
	 * Consultar ancla oro dolar
	 */
	public ValorAnclaOroDolar consultarAnclaOroDolar() {
		log.info("consultarCambioAnclaOroDolar");
		
		AnclaOroDolarEntity ancla = gestionEscenarioMongoService.consultarAnclaOroDolar();
		ValorAnclaOroDolar vaod = null;
		if(ancla != null) {
			
			vaod = new ValorAnclaOroDolar();
			vaod.setValorAnclaDolar(ancla.getValorAnclaDolar());
			vaod.setValorAnclaOro(ancla.getValorAnclaOro());
		}
		
		return vaod;
	}
	
	/*
	 * Solicitar el cambio de ancla oro dolar con los datos enviados
	 */
	public Boolean solictarCambioAnclaOroDolar(ModificarValorAnclaOroDolar peticion) {
		log.info("solictarCambioAnclaOroDolar");
		
		Boolean procesado = false;
		
		if(peticion != null) {
			ObjectId idAncla = gestionEscenarioMongoService.solictarCambioAnclaOroDolar(peticion);
			
			if(idAncla != null) {
				AnclaOroDolarEntity anclaOroDolar = gestionEscenarioMongoService.consultarRequestIdAnclaOroDolar(idAncla);
				if(anclaOroDolar != null ) {
					if(anclaOroDolar.getRequestId() != null) {
						procesado = this.existeRequestIdAnclaOroDolar(anclaOroDolar.getRequestId());
						if(Boolean.TRUE.equals(procesado)) {
							log.info("Se elimino de manera correcta el requestId.");
						} else {
							log.info("No se pudo eliminar el requestId.");
						}
						// ya no existe el requestId, hay que generarlo y persistirlo
						procesado = this.noExisteRequestIdAnclaOroDolar(idAncla);
					} else {
						// no existe el requestId, hay que generarlo y persistirlo
						procesado = this.noExisteRequestIdAnclaOroDolar(idAncla);
					}
				}
			}
		}
		
		return procesado;
	}
	
	/*
	 * Solicitar el cambio de ancla oro dolar con los datos enviados
	 */
	public Boolean solictarCambioMoneda(ListaMonedas peticion) {
		log.info("solictarCambioMoneda");
		
		Boolean procesado = false;
		
		if(peticion != null) {
			Long id = gestionEscenarioMongoService.solictarCambioMoneda(peticion);
			
			if(id != null) {
				MonedasEntity moneda = gestionEscenarioMongoService.consultarRequestIdMoneda(id);
				if(moneda != null ) {
					if(moneda.getRequestId() != null) {
						procesado = this.existeRequestIdMonedas(moneda.getRequestId());
						log.info("RequestId {}", procesado);
						if(Boolean.TRUE.equals(procesado)) {
							log.info("Se ha eliminado el requestId correctamente");
						}else {
							log.info("No se ha podido eliminar el requestId");
						}
						procesado = this.noExisteRequestIdMonedas(id);
					
					} else {
						procesado = this.noExisteRequestIdMonedas(id);
						log.info("RequestId {}", procesado);
					}
				}
			}
		}
		
		return procesado;
	}
	
	/*
	 * Existe el request id de ancla oro valor
	 */
	private Boolean existeRequestIdAnclaOroDolar(Integer requestId) {
		log.info("existeRequestIdAnclaOroDolar");
		
		log.info("requestId: {}", requestId);
		
		return oAGController.eliminarCalendarizacionEscenario(requestId);
	}
	

	
	/*
	 * Existe el request id de Monedas
	 */
	private Boolean existeRequestIdMonedas(Integer requestId) {
		log.info("existeRequestIdMoneda");
		
		log.info("requestId: {}", requestId);
		
		return oAGController.eliminarCalendarizacionEscenario(requestId);
	}
	
	/*
	 * No existe el request id de Monedas
	 */
	private Boolean noExisteRequestIdAnclaOroDolar(ObjectId id) {
		log.info("noExisteRequestIdAnclaOroDolar");
		
		Boolean procesado = false;
		
		// Se calendariza la ejecución de los escenarios de ancla oro dolar
		Integer requestId = this.calendarizarEjecucion(ID_PETICION_ESCENARIO_ANCLA_ORO_DOLAR, ID_ESCENARIO_ANCLA_ORO_DOLAR);
		if(requestId != null) {
			procesado = gestionEscenarioMongoService.updateRequestIdAnclaOroDolar(id, requestId);
		}
		
		return procesado;
	}
	
	/*
	 * No existe el request id de Monedas
	 */
	private Boolean noExisteRequestIdMonedas(Long id) {
		log.info("noExisteRequestIdMoneda");
		
		Boolean procesado = false;
		
		// Se calendariza la ejecución de los escenarios de ancla oro dolar
		Integer requestId = this.calendarizarEjecucion(ID_PETICION_ESCENARIO_MONEDA_ORO, ID_ESCENARIO_MONEDA_ORO);
		if(requestId != null) {
			procesado = gestionEscenarioMongoService.updateRequestIdMonedas(id, requestId);
		}
		
		return procesado;
	}
	
	/*
	 * Buscar moneda
	 */
	
	public List<MonedasEntity> buscarMonedas(Boolean oro) {
		log.info("buscarMonedas");
		
		List<MonedasEntity> moneda = null;
		
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ORO).in(oro));
			
			moneda = mongoTemplate.find(query, MonedasEntity.class);
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
		
		return moneda;
	}
}