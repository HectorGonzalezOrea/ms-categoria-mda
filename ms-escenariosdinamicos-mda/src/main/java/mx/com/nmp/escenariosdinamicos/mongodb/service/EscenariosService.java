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

import com.mashape.unirest.http.exceptions.UnirestException;

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
import mx.com.nmp.escenariosdinamicos.mongodb.vo.EscenariosVO;
import mx.com.nmp.escenariosdinamicos.oag.client.service.OAGService;
import mx.com.nmp.escenariosdinamicos.oag.vo.EnviarNotificacionRequestVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;
import net.bytebuddy.implementation.bytecode.Throw;

import static mx.com.nmp.escenariosdinamicos.utils.Constantes.ASUNTO_MESSAGE;
import static mx.com.nmp.escenariosdinamicos.utils.Constantes.CONTENIDO;

import java.util.ArrayList;
import java.util.List;

@Service
public class EscenariosService {

	private static final Logger log = LoggerFactory.getLogger(EscenariosService.class);

	@Value("${oag.resource.oauth.sendmail.from}")
	protected String de;
	@Value("${oag.resource.oauth.sendmail.to}")
	protected String para;


	@Autowired
	private OAGService oAGService;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private EscenarioRepository escenarioRepository;

	/*
	 * Creacion de Escenario Dinamico
	 */
	public CrearEscenariosRes crearEscenario(List<CrearEscenariosReq> crearEscenariosRequest) {
		log.info("EscenariosService.crearEscenario");
		EnviarNotificacionRequestVO r = new EnviarNotificacionRequestVO();
		CrearEscenariosRes cer = new CrearEscenariosRes();
		StringBuilder contenido=new StringBuilder();
		contenido.append(CONTENIDO+Constantes.SALTO+Constantes.SALTO);
		contenido.append(Constantes.TABLE);
		contenido.append(Constantes.TR);
		contenido.append(Constantes.TEMPLATE_UPDATE);
		contenido.append(Constantes.CLOSE_TR);
		EscenarioEntity escenario = new EscenarioEntity();
		if (crearEscenariosRequest != null) {
			crearEscenariosRequest.stream().forEach(esc->{
				escenario.setDiaUno(esc.getDiaUno());
				escenario.setDiaDos(esc.getDiaDos());
				escenario.setDiaTres(esc.getDiaTres());
				escenario.setIdRegla(esc.getIdRegla());
				LocalDate locateDate = LocalDate.now();
				escenario.setFechaCreacion(locateDate);
				Integer id = (int) sequenceGeneratorService.generateSequence(Constantes.ESCENARIO_SEQ_KEY);
				escenario.setIdEscenario(id);
				contenido.append(Constantes.TR);
				contenido.append(Constantes.ABRIR_TD_STYLE);
				contenido.append(id);
				contenido.append(Constantes.CERRAR_TD);
				contenido.append(Constantes.ABRIR_TD_STYLE);
				contenido.append(esc.getDiaUno());
				contenido.append(Constantes.CERRAR_TD);
				contenido.append(Constantes.ABRIR_TD_STYLE);
				contenido.append(esc.getDiaDos());
				contenido.append(Constantes.CERRAR_TD);
				contenido.append(Constantes.ABRIR_TD_STYLE);
				contenido.append(esc.getDiaTres());
				contenido.append(Constantes.CERRAR_TD);
				contenido.append(Constantes.ABRIR_TD_STYLE);
				contenido.append(esc.getIdRegla());
				contenido.append(Constantes.CERRAR_TD);
				contenido.append(Constantes.CLOSE_TR);
				try {
					mongoTemplate.insert(escenario);
				} catch (Exception e) {
					log.error("{0}", e);
				}
			});
			  	contenido.append(Constantes.CLOSE_TABLE);
				r.setDe(de);
				r.setPara(para);
				r.setAsunto(ASUNTO_MESSAGE);
				r.setContenidoHTML(contenido.toString());
				log.info("Asunto {}", r.getAsunto());

				try {
					oAGService.enviarNotificacion(r);
				} catch (UnirestException e) {
					log.info("Error al enviarNotificacion {0}",e);
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
			cer = new ArrayList<>();
			ConsultarEscenariosResInner ceri = null;

			for (EscenarioEntity aux : busquedaList) {
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
	
	public ModEscenariosRes editaEscenario(List<ModEscenariosReq> modEscenariosRequest) {
		log.info("EscenariosService.editaEscenario");
		ModEscenariosRes mer = new ModEscenariosRes();
		StringBuilder contenido=new StringBuilder();
		contenido.append(CONTENIDO+Constantes.SALTO+Constantes.SALTO);
		contenido.append(Constantes.TABLE);
		contenido.append(Constantes.TR);
		contenido.append(Constantes.TEMPLATE_UPDATE);
		contenido.append(Constantes.CLOSE_TR);
		EnviarNotificacionRequestVO r = new EnviarNotificacionRequestVO();
		if (modEscenariosRequest != null) {
			modEscenariosRequest.stream().forEach(esc->{
				EscenarioEntity escenario =consultaEscenarioId(esc.getIdEscenario());
				if (escenario != null) {
					escenario.setIdEscenario(esc.getIdEscenario());
					escenario.setDiaUno(esc.getDiaUno());
					escenario.setDiaDos(esc.getDiaDos());
					escenario.setDiaTres(esc.getDiaTres());
					escenario.setIdRegla(esc.getIdRegla());
					LocalDate locateDate = LocalDate.now();
					escenario.setFechaActualizacion(locateDate);
					contenido.append(Constantes.TR);
					contenido.append(Constantes.ABRIR_TD_STYLE);
					contenido.append(esc.getIdEscenario());
					contenido.append(Constantes.CERRAR_TD);
					contenido.append(Constantes.ABRIR_TD_STYLE);
					contenido.append(esc.getDiaUno());
					contenido.append(Constantes.CERRAR_TD);
					contenido.append(Constantes.ABRIR_TD_STYLE);
					contenido.append(esc.getDiaDos());
					contenido.append(Constantes.CERRAR_TD);
					contenido.append(Constantes.ABRIR_TD_STYLE);
					contenido.append(esc.getDiaTres());
					contenido.append(Constantes.CERRAR_TD);
					contenido.append(Constantes.ABRIR_TD_STYLE);
					contenido.append(esc.getIdRegla());
					contenido.append(Constantes.CERRAR_TD);
					contenido.append(Constantes.CLOSE_TR);
					try {
						mongoTemplate.save(escenario);
					} catch (Exception e) {
						log.error("Exception : {0}", e);
					}
				}
			});
			contenido.append(Constantes.CLOSE_TABLE);
			r.setDe(de);
			r.setPara(para);
			r.setAsunto(Constantes.ASUNTO_MESSAGE_UPDATE);
			r.setContenidoHTML(contenido.toString());
			log.info("Asunto: {}", r.getAsunto());
			try {
				oAGService.enviarNotificacion(r);
			} catch (UnirestException e) {
			log.info("Error al enviar la notificación {0}",e);
			}			
		}
		return mer;

	}

	/*
	 * Eliminar escenario dinamico
	 */
	public void eliminaEscenario(List<Integer> idEscenarios) {
		log.info("BolsasService.elimina");
		EnviarNotificacionRequestVO r = new EnviarNotificacionRequestVO();
		StringBuilder contenido=new StringBuilder();
		contenido.append(CONTENIDO+Constantes.SALTO+Constantes.SALTO);
		contenido.append(Constantes.TABLE);
		contenido.append(Constantes.TR);
		contenido.append(Constantes.ABRIR_TD_STYLE);
		contenido.append(Constantes.ID_ESCENARIO);
		contenido.append(Constantes.CERRAR_TD);
		contenido.append(Constantes.CLOSE_TR);
		if (idEscenarios != null) {
			log.info("{}", idEscenarios.size());
			if (!idEscenarios.isEmpty()) {
				try {
					for (Integer idEscenario : idEscenarios) {
						contenido.append(Constantes.TR);
						contenido.append(Constantes.ABRIR_TD_STYLE);
						contenido.append(idEscenario);
						contenido.append(Constantes.CERRAR_TD);
						contenido.append(Constantes.CLOSE_TR);
						escenarioRepository.deleteById(String.valueOf(idEscenario));
					}
					contenido.append(Constantes.CLOSE_TABLE);
					r.setDe(de);
					r.setPara(para);
					r.setAsunto(Constantes.ASUNTO_MESSAGE_ELIMINA);
					r.setContenidoHTML(contenido.toString());
					log.info("Asunto: {}", r.getAsunto());
					oAGService.enviarNotificacion(r);
				} catch (UnirestException e) {
					log.error("Error al enviar la notificación {0}",e);
					log.error("Exception : {0}", e);
				}catch (Exception e) {
					log.error("[{}]",e.getMessage());
					log.error("Error al tratar de borrar el elemento");
				}
			}
		}
	}
	
	/*
	 *Consulta unicamente ids de escenarios
	 *@param Lista de escenatios
	 *return ids escenarios
	 * */
	public List<EscenarioEntity> consultaGrupoEscenarios(List<Integer> lstIdEscenarios){
		log.info("size [{}]",lstIdEscenarios.size());
		List<EscenarioEntity> escenarios=null;
		escenarios= mongoTemplate.find(Query.query(Criteria.where("idEscenario").in(lstIdEscenarios)), EscenarioEntity.class);
		return escenarios;
	}
	
	public List<Integer> transformaIds(List<EscenarioEntity> lstIdEscenarios){
		List<Integer> escenariosIds=new ArrayList<>();
		if(!lstIdEscenarios.isEmpty()){
			lstIdEscenarios.stream().forEach(escenario->escenariosIds.add(escenario.getIdEscenario()));
		}
		return escenariosIds;
	}
	
	/*
	 *Consulta escenarios por id
	 *@param ModEscenariosReq
	 *return EscenarioEntity
	 * */
	private EscenarioEntity consultaEscenarioId(Integer idEscenario){
	EscenarioEntity escenario=null;
	try {
		escenario = mongoTemplate.findOne(
				Query.query(Criteria.where(Constantes.ID).is(idEscenario)),
				EscenarioEntity.class);
	} catch (Exception e) {
		log.error("{0}", e);
	}
		return escenario;
	}
	
	/*
	 * funcion que permite convertir payloads y consultas de escenarios en strings para determinar si estan repeditos en DB
	 * */
	public List<String> existenRepetidos(List<CrearEscenariosReq> escanariosRequest){
		List<EscenarioEntity> escenariosDb= mongoTemplate.findAll(EscenarioEntity.class);
		List<String> listaA=new ArrayList<>();
		List<String> listaB=new ArrayList<>();
		escenariosDb.stream().forEach(esc->listaA.add("{"+esc.getDiaUno()+","+esc.getDiaDos()+","+esc.getDiaTres()+","+esc.getIdRegla()+"}"));
		escanariosRequest.stream().forEach(esc->listaB.add("{"+esc.getDiaUno()+","+esc.getDiaDos()+","+esc.getDiaTres()+","+esc.getIdRegla()+"}"));
		List<String> similar = new ArrayList<>(listaA); 
		similar.retainAll(listaB);
		return similar;
		
	}
}
