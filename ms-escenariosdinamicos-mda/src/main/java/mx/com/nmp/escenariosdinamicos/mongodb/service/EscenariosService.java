package mx.com.nmp.escenariosdinamicos.mongodb.service;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import mx.com.nmp.escenariosdinamicos.constantes.Constantes.Common;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosResInner;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.EliminarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.InternalServerError;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.NotFound;
import mx.com.nmp.escenariosdinamicos.mongodb.entity.EscenarioEntity;
import mx.com.nmp.escenariosdinamicos.mongodb.repository.EscenarioRepository;
import mx.com.nmp.escenariosdinamicos.oag.controller.OAGController;
import mx.com.nmp.escenariosdinamicos.oag.vo.EnviarNotificacionRequestVO;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.ASUNTO_MESSAGE;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.CONTENIDO;

import java.util.ArrayList;
import java.util.List;



@Service
public class EscenariosService {
	
	private static final Logger log = LoggerFactory.getLogger(EscenariosService.class);
	
	@Value("${oag.resource.oauth.sendmail.from}")
	protected String De;
	@Value("${oag.resource.oauth.sendmail.to}")
	protected String Para;
	
	private static final String ESCENARIO_SEQ_KEY = "escenario_sequence";
	public static final String ID = "_id";

	
	
	@Autowired
	private OAGController oAGController;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private EscenarioRepository escenarioRepository;
	
	
	/*
	 * Creacion de Escenario Dinamico
	 */
	
	public CrearEscenariosRes crearEscenario (CrearEscenariosReq crearEscenariosRequest) {
		log.info("EscenariosService.crearEscenario");
		Boolean insertado =false;
		CrearEscenariosRes cer = new CrearEscenariosRes();
		EscenarioEntity escenario = new EscenarioEntity();
		if (crearEscenariosRequest !=null) {
			
			escenario.setDiaUno(crearEscenariosRequest.getDiaUno());
			escenario.setDiaDos(crearEscenariosRequest.getDiaDos());
			escenario.setDiaTres(crearEscenariosRequest.getDiaTres());
			escenario.setIdRegla(crearEscenariosRequest.getIdRegla());
			
			LocalDate locateDate = LocalDate.now();
			
			escenario.setFechaCreacion(locateDate);
			
			Integer id = (int) sequenceGeneratorService.generateSequence(ESCENARIO_SEQ_KEY);
			escenario.setIdEscenario(id);
			
			try {
				mongoTemplate.insert(escenario);
				insertado = true;
			} catch(Exception e) {
				log.error("Exception : {}", e);
			}
			if (Boolean.TRUE.equals(insertado)) {
				
				cer.setIdEscenario(escenario.getIdEscenario());
				cer.setFechaCreacion(escenario.getFechaCreacion());	
			}
			if(cer.getIdEscenario()!=null) {
				
				EnviarNotificacionRequestVO r = new EnviarNotificacionRequestVO();
				
				r.setDe(De);
				r.setPara(Para);
				r.setAsunto(ASUNTO_MESSAGE);
				r.setContenidoHTML(CONTENIDO);
				log.info("Asunto: {}", r.getAsunto());
				
				oAGController.enviarNotificacion(r);
				
			}
		}
			
		return cer;
		
	}
	
	/*
	 * Consulta de escenarios
	 */
	
	public List<ConsultarEscenariosResInner> consultaEscenario() {
		log.info("EscenariosService.consultaEscenario");
		
		List<EscenarioEntity> busquedaList = mongoTemplate.findAll(EscenarioEntity.class);
		List<ConsultarEscenariosResInner> cer = null;
		
		if (CollectionUtils.isNotEmpty(busquedaList)) {
			cer = new ArrayList<ConsultarEscenariosResInner>();
			ConsultarEscenariosResInner ceri = null; 
			
			for(EscenarioEntity aux:busquedaList) {
				ceri = new ConsultarEscenariosResInner();
				
				ceri.setIdRegla(aux.getIdRegla());
				ceri.setIdEscenario(aux.getIdEscenario());
				ceri.setDiaUno(aux.getDiaUno());
				ceri.setDiaDos(aux.getDiaDos());
				ceri.setDiaTres(aux.getDiaTres());
				
				cer.add(ceri);
				
			}
			
		}
		
		
		
		return cer;
	}
	
	/*
	 * Actualizacion de Escenario Dinamico
	 */
	
	public ModEscenariosRes editaEscenario (ModEscenariosReq modEscenariosRequest) {
		log.info("EscenariosService.editaEscenario");
		Boolean actualizado =false;
		ModEscenariosRes mer = new ModEscenariosRes();
		EscenarioEntity escenario = null;
		if(modEscenariosRequest!=null) {
			try {
				escenario = mongoTemplate.findOne(Query.query(Criteria.where(ID).is(modEscenariosRequest.getIdEscenario())), EscenarioEntity.class);
			}catch(Exception e) {
				log.error("Exception : {}", e);
			}
			if(escenario!=null) {
				
				escenario.setIdEscenario(modEscenariosRequest.getIdEscenario());
				escenario.setDiaUno(modEscenariosRequest.getDiaUno());
				escenario.setDiaDos(modEscenariosRequest.getDiaDos());
				escenario.setDiaTres(modEscenariosRequest.getDiaTres());
				escenario.setIdRegla(modEscenariosRequest.getIdRegla());
				
				LocalDate locateDate = LocalDate.now();
				escenario.setFechaActualizacion(locateDate);
				
				try {
					mongoTemplate.save(escenario);
					actualizado = true;
				}catch(Exception e) {
					log.error("Exception : {}", e);
				}
				if(actualizado) {
					
					mer.setIdEscenario(escenario.getIdEscenario());
					mer.setFechaActualizacion(escenario.getFechaActualizacion());
				}
				if(mer.getIdEscenario()!=null) {
					
					EnviarNotificacionRequestVO r = new EnviarNotificacionRequestVO();
					
					r.setDe(De);
					r.setPara(Para);
					r.setAsunto(ASUNTO_MESSAGE);
					r.setContenidoHTML(CONTENIDO);
					log.info("Asunto: {}", r.getAsunto());
					
					oAGController.enviarNotificacion(r);
					
				}
			}
			
		}
		return mer;
		
	}
	
	
	/*
	 * Eliminar escenario dinamico
	 */
	public Object eliminaEscenario(Integer idEscenario) {
		log.info("BolsasService.elimina");

		if (idEscenario != null) {
			EscenarioEntity escenario = (EscenarioEntity) escenarioRepository.findByIdEscenario(idEscenario);

			log.info("{}", escenario);

			if (escenario != null) {
				try {
					escenarioRepository.delete(escenario);
				} catch (Exception e) {
					log.error("Exception : {}", e);

					InternalServerError is = new InternalServerError();
					is.setCodigo(Common.ERROR_SERVER);
					is.setMensaje(Common.ERROR_SERVER_MSG);

					log.info("{}", is);

					return is;
				}

				EnviarNotificacionRequestVO r = new EnviarNotificacionRequestVO();

				r.setDe(De);
				r.setPara(Para);
				r.setAsunto(ASUNTO_MESSAGE);
				r.setContenidoHTML(CONTENIDO);
				log.info("Asunto: {}", r.getAsunto());

				oAGController.enviarNotificacion(r);
			} else {
				log.info("Escenario no encontrado.");
				NotFound nf = new NotFound();
				nf.setCodigo(Common.ERROR_CODE_NOT_FOUND);
				nf.setMensaje(Common.MESSAGE_ERROR_CODE_NOT_FOUND);

				log.info("{}", nf);

				return nf;
			}
		}

		EliminarEscenariosRes resp = new EliminarEscenariosRes();

		resp.setCode(Common.EXITO_ELIMINAR);
		resp.setMessage(Common.EXITO_ELIMINAR_MSG);

		log.info("{}", resp);

		return resp;
	}
}
