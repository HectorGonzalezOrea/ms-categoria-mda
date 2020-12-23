package mx.com.nmp.usuarios.mongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes.DescripcionPerfilEnum;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.InternalServerError;
import mx.com.nmp.usuarios.model.ModCapacidadUsuario;
import mx.com.nmp.usuarios.model.ModCapacidadUsuarioInner;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.model.ResEstatus;
import mx.com.nmp.usuarios.model.CrearHistoricoRes.AccionesEnum;
import mx.com.nmp.usuarios.model.ReqHistorico.AccionEnum;
import mx.com.nmp.usuarios.model.ResEstatus.DescripcionEnum;
import mx.com.nmp.usuarios.model.ResHistorico;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.DescripcinCapEnum;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.IdCapacidadEnum;
import mx.com.nmp.usuarios.mongodb.entity.CapacidadEntity;
import mx.com.nmp.usuarios.mongodb.entity.DepartamentoAreaEntity;
import mx.com.nmp.usuarios.mongodb.entity.DireccionEntity;
import mx.com.nmp.usuarios.mongodb.entity.GerenciaEntity;
import mx.com.nmp.usuarios.mongodb.entity.HistorialEntity;
import mx.com.nmp.usuarios.mongodb.entity.PerfilEntity;
import mx.com.nmp.usuarios.mongodb.entity.PuestoEntity;
import mx.com.nmp.usuarios.mongodb.entity.SequenceGeneratorService;
import mx.com.nmp.usuarios.mongodb.entity.SubdireccionEntity;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.mongodb.entity.PerfilCapacidadEntity;
import mx.com.nmp.usuarios.mongodb.repository.PerfilRepository;
import mx.com.nmp.usuarios.mongodb.repository.PuestoRepository;
import mx.com.nmp.usuarios.mongodb.repository.SubdireccionRepository;
import mx.com.nmp.usuarios.mongodb.repository.UsuarioRepository;
import mx.com.nmp.usuarios.mongodb.vo.DepatamentoAreaVO;
import mx.com.nmp.usuarios.mongodb.vo.DireccionVO;
import mx.com.nmp.usuarios.mongodb.vo.GerenciaVO;
import mx.com.nmp.usuarios.mongodb.vo.PuestoVO;
import mx.com.nmp.usuarios.mongodb.vo.SubdireccionVO;
import mx.com.nmp.usuarios.oag.client.service.OAGService2Impl;
import mx.com.nmp.usuarios.oag.vo.FiltroVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioRequestVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioResponseVO;
import mx.com.nmp.usuarios.oag.vo.ProfileVO;
import mx.com.nmp.usuarios.oag.vo.TokenProviderErrorVO;
import mx.com.nmp.usuarios.oag.vo.UsuarioVO;
import mx.com.nmp.usuarios.utils.Constantes;
import mx.com.nmp.usuarios.utils.ConverterUtil;
import mx.com.nmp.usuarios.mongodb.repository.PerfilCapacidadRepository;
import mx.com.nmp.usuarios.mongodb.repository.CapacidadRepository;
import mx.com.nmp.usuarios.mongodb.repository.DepartamentoAreaRepository;
import mx.com.nmp.usuarios.mongodb.repository.DireccionRepository;
import mx.com.nmp.usuarios.mongodb.repository.GerenciaRepository;

@Service
public class UsuarioService2Impl {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService2Impl.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private PerfilCapacidadRepository perfilCapacidadRepository;
	@Autowired
	private CapacidadRepository capacidadRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private DepartamentoAreaRepository departamentoAreaRepository;
	@Autowired
	private DireccionRepository direccionRepository;
	@Autowired
	private GerenciaRepository gerenciaRepository;
	@Autowired
	private PuestoRepository puestoRepository;
	@Autowired
	private SubdireccionRepository subdireccionRepository;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private OAGService2Impl oagController;
	
	private static final String HISTORICO_SEQ_KEY = "historico_sequence";
	private static final String USUARIO_SEQ_KEY = "usuario_sequence";
	
	/*
	 * Crear Historico
	 */
	public CrearHistoricoRes crearHistorico(AccionEnum accion, Date fecha, Integer idPerfil, String usuario) {
		log.info("UsuarioService.crearHistorico");
		Boolean insertado = false;

		CrearHistoricoRes resp = null;

		HistorialEntity he = new HistorialEntity();
		
		if (accion != null && fecha != null && idPerfil != null && usuario != null) {
			he.setAccion(accion.getValue());
			he.setIdPerfil(idPerfil);
			he.setUsuario(usuario);
			he.setFecha(fecha);
			he.setIdHistorialUsuario(sequenceGeneratorService.generateSequence(HISTORICO_SEQ_KEY));
			mongoTemplate.insert(he);
			insertado = true;
		}

		if (Boolean.TRUE.equals(insertado)) {
			resp = new CrearHistoricoRes();
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.ID).is(he.get_id());
			query.addCriteria(aux);

			HistorialEntity historico = mongoTemplate.findOne(query, HistorialEntity.class);

			if (historico != null) {
				resp.setFecha(historico.getFecha());
				resp.setUsuario(historico.getUsuario());
				resp.setIdRegistro(historico.getIdHistorialUsuario().intValue());
				resp.setAcciones(AccionesEnum.fromValue(historico.getAccion()));
				if (historico.getIdPerfil() != null) {
					CapacidadUsuariosRes cureps = this.buscarPerfilConCapacidades(historico.getIdPerfil());
					if (cureps != null) {
						resp.setPerfil(cureps);
					}
				}
				log.info("respose {} ", resp);
			}
		}

		return resp;
	}

	/*
	 * Consulta Historico
	 */
	public ConsultaHistoricoRes getHistorico(Integer idUsuario) {
		log.info("UsuarioService.getHistorico");

		ConsultaHistoricoRes chres = null;

		if (idUsuario != null) {
			log.info("id distinto de null");
			UsuarioEntity user = usuarioRepository.findByIdUsuario(idUsuario);

			if (user != null) {
				log.info("usuario encontrado");
				Query query = new Query();
				Criteria aux = Criteria.where(Constantes.USUARIO).is(user.getUsuario());
				query.addCriteria(aux);

				List<HistorialEntity> listHistorico = mongoTemplate.find(query, HistorialEntity.class);

				log.info("listHistorico {}", listHistorico.size());

				chres = new ConsultaHistoricoRes();
				
				if (CollectionUtils.isNotEmpty(listHistorico)) {
					log.info("lista de historico");
					chres.setHistorial(addListHistorico(listHistorico));
				}
			}
		}

		if(chres == null) {
			chres = new ConsultaHistoricoRes();
			List<ResHistorico> listaHist = new ArrayList<>();
			chres.setHistorial(listaHist);
		}
		
		return chres;
	}
	
	private List<ResHistorico> addListHistorico(List<HistorialEntity> listHistorico){
		List<ResHistorico> historial = new ArrayList<>();

		ResHistorico re = null;
		for (HistorialEntity he : listHistorico) {
			re = new ResHistorico();

			re.setIdRegistro(he.getIdHistorialUsuario().intValue());
			re.setFecha(he.getFecha());
			re.setUsuario(he.getUsuario());

			if (he.getIdPerfil() != null) {
				CapacidadUsuariosRes cureps = this.buscarPerfilConCapacidades(he.getIdPerfil());
				if (cureps != null) {
					re.setPerfil(cureps);
				}
			}
			re.setAccion(mx.com.nmp.usuarios.model.ResHistorico.AccionEnum.fromValue(he.getAccion()));

			historial.add(re);
		}
		return historial;
	}

	
	/*
	 * Crear Perfil con Capacidad
	 */
	public CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil) {
		log.info("UsuarioService.crearPerfilCapacidad");
		
		Boolean creado = false;
		PerfilEntity perfil = null;
		
		if (idPerfil != null && informacionPerfil != null) {
			perfil =perfilRepository.findByIdPerfil(idPerfil);

			log.info("perfil:{} ", perfil);

			if (perfil != null) {

				Query queryPerfilCapacidad = null;
				Criteria aux = null;
				Update updatePerfilCapacidad = new Update();

				for (CapacidadUsuariosReqInner capacidad : informacionPerfil) {
					aux = Criteria.where(Constantes.PERFIL).is(idPerfil);
					IdCapacidadEnum idEnum = capacidad.getIdCapacidad();
					DescripcinCapEnum descEnum = capacidad.getDescripcinCap();

					CapacidadEntity cap = capacidadRepository
							.findByIdCapacidad(new Integer(idEnum.toString()), descEnum.toString());

					if (cap != null) {
						log.info("Capacidad");
						// Insert o Update del Perfil-Capacidad

						queryPerfilCapacidad = new Query();
						aux.and(Constantes.ID_CAPACIDAD).is(new Integer(idEnum.toString()));
						queryPerfilCapacidad.addCriteria(aux);
						updatePerfilCapacidad.set(Constantes.ID_CAPACIDAD, new Integer(idEnum.toString()));
						mongoTemplate.upsert(queryPerfilCapacidad, updatePerfilCapacidad, PerfilCapacidadEntity.class);
					}
				}

				creado = true;
			}
		}
		
		CapacidadUsuariosRes resp = null;
		
		if(Boolean.TRUE.equals(creado)) {
			resp = this.buscarPerfilConCapacidades(idPerfil);
		}
		
		return resp;
	}

	public InternalServerError validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq) {
		log.info("validarCapacidades");
		
		InternalServerError ise = null;
		
		if(!capacidadUsuarioReq.isEmpty()) {
			
			StringBuilder sb = new StringBuilder();
			sb.append(Constantes.ERROR_MESSAGE_INTERNAL_ERROR_CAP_NO_VALIDAS);
			
			for(CapacidadUsuariosReqInner capacidad : capacidadUsuarioReq) {
				mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.IdCapacidadEnum idEnum = capacidad.getIdCapacidad();
				
				if(idEnum == null) {
					ise = new InternalServerError();
					ise.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
					ise.setMensaje(sb.toString());
				} else {
					Query query = new Query();
					Criteria aux = Criteria.where(Constantes.ID_CAPACIDAD).is(new Integer(idEnum.toString()));
					query.addCriteria(aux);
					
					Boolean cap = mongoTemplate.exists(query, CapacidadEntity.class);
					
					if(Boolean.FALSE.equals(cap)) {
						
						ise = new InternalServerError();
						ise.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
						sb.append(capacidad.toString().replaceAll("\n", ""));
						
						ise.setMensaje(sb.toString());
					}
				}
			}
		}
		
		return ise;
	}
	
	public InternalServerError validarCapacidadesMod(ModCapacidadUsuario modCapacidadReq) {
		log.info("validarCapacidades");
		
		InternalServerError ise = null;
		
		if(!modCapacidadReq.isEmpty()) {
			
			StringBuilder sb = new StringBuilder();
			sb.append(Constantes.ERROR_MESSAGE_INTERNAL_ERROR_CAP_NO_VALIDAS);
			
			for(ModCapacidadUsuarioInner capacidad : modCapacidadReq) {
				
				mx.com.nmp.usuarios.model.ModCapacidadUsuarioInner.IdCapacidadEnum idEnum = capacidad.getIdCapacidad();
			
				if(idEnum == null) {
					ise = new InternalServerError();
					ise.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
					ise.setMensaje(sb.toString());
				} else {
					Query query = new Query();
					Criteria aux = Criteria.where(Constantes.ID_CAPACIDAD).is(new Integer(idEnum.toString()));
					query.addCriteria(aux);
					
					Boolean cap = mongoTemplate.exists(query, CapacidadEntity.class);
					
					if(Boolean.FALSE.equals(cap)) {
						
						ise = new InternalServerError();
						ise.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);

						sb.append(capacidad.toString().replaceAll("\n", ""));
						
						ise.setMensaje(sb.toString());
					}
				}
			}
		}
		
		return ise;
	}
	
	/*
	 * Modificar Capacidades de un perfil
	 */
	public CapacidadUsuariosRes modificarPerfilCapacidad(Integer idPerfil, ModCapacidadUsuario modCapacidadReq) {
		log.info("UsuarioService.modificarPerfilCapacidad");
		
		Boolean modificado = false;
		PerfilEntity perfil = null;
		
		if(idPerfil != null && !modCapacidadReq.isEmpty()) {
	
			perfil = perfilRepository.findByIdPerfil(idPerfil);

			log.info("perfil: {}" , perfil);

			if (perfil != null) {
				List<PerfilCapacidadEntity> pcList = perfilCapacidadRepository.findByPerfil(idPerfil);
				
				// se limpia
				for(PerfilCapacidadEntity pc : pcList) {
					perfilCapacidadRepository.delete(pc);
				}
				
				Query queryPerfilCapacidad = null;
				Criteria aux = null;
				Update updatePerfilCapacidad = new Update();

				for (ModCapacidadUsuarioInner capacidad : modCapacidadReq) {
					aux = Criteria.where(Constantes.PERFIL).is(idPerfil);
					mx.com.nmp.usuarios.model.ModCapacidadUsuarioInner.IdCapacidadEnum idEnum = capacidad.getIdCapacidad();

					CapacidadEntity cap = capacidadRepository.findByIdCapacidad(new Integer(idEnum.toString()));

					if (cap != null) {
						log.info("Capacidad");
						// Insert o Update del Perfil-Capacidad

						queryPerfilCapacidad = new Query();
						aux.and(Constantes.ID_CAPACIDAD).is(new Integer(idEnum.toString()));
						queryPerfilCapacidad.addCriteria(aux);
						updatePerfilCapacidad.set(Constantes.ID_CAPACIDAD, new Integer(idEnum.toString()));
						mongoTemplate.upsert(queryPerfilCapacidad, updatePerfilCapacidad, PerfilCapacidadEntity.class);
					}
				}
				modificado = true;
			}
		}
		
		CapacidadUsuariosRes resp = null;
		
		if(Boolean.TRUE.equals(modificado)) {
			resp = this.buscarPerfilConCapacidades(idPerfil);
		}
		
		return resp;
	}
	
	/*
	 * Actualizar Perfil de un usuario
	 */
	public CapacidadUsuariosRes actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil) {
		log.info("UsuarioService.actualizarPerfilUsuario");
		
		Boolean actualizar = false;
		CapacidadUsuariosRes resp = null;
		if(idUsuario != null && idPerfil != null) {
			Query query = new Query();
			query.addCriteria(Criteria.where(Constantes.ID_USUARIO).is(idUsuario));
			Update update = new Update();
			update.set(Constantes.PERFIL, idPerfil);
			UsuarioEntity user = mongoTemplate.findAndModify(query, update, UsuarioEntity.class);

			if (user != null) {
				actualizar = true;
			}	
		}
		
		if(Boolean.TRUE.equals(actualizar)) {
			resp = this.buscarPerfilConCapacidades(idPerfil);
		}
		
		return resp;
	}
	
	private List<CapacidadUsuariosRes> buscarPerfilConCapacidades() {
		List<PerfilEntity> listPetfil = mongoTemplate.findAll(PerfilEntity.class);
	
		List<CapacidadUsuariosRes> resp = new ArrayList<CapacidadUsuariosRes>();
		
		listPetfil
		.stream()
		.forEach( p -> {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.PERFIL).is(p.getIdPerfil());
			query.addCriteria(aux);
		
			CapacidadUsuariosRes cureps = new CapacidadUsuariosRes();
			
			cureps.setIdPerfil(p.getIdPerfil());
			cureps.setDescripcionPerfil(DescripcionPerfilEnum.fromValue(p.getDescripcion()));
			
			List<PerfilCapacidadEntity> perfilCapacidadList = mongoTemplate.find(query, PerfilCapacidadEntity.class);
			
			log.info("{}" , perfilCapacidadList.size());
			if (CollectionUtils.isNotEmpty(perfilCapacidadList)) {
				List<CapacidadEntity> ceList = mongoTemplate.findAll(CapacidadEntity.class);
				capacidadUsuarioNew(perfilCapacidadList, cureps, ceList);
				resp.add(cureps);
			}
			
		});
		
		return resp;
	}
	
	private CapacidadUsuariosRes capacidadUsuarioNew(List<PerfilCapacidadEntity> perfilCapacidadList, CapacidadUsuariosRes cureps, List<CapacidadEntity> ceList) {
		
		CapacidadUsuariosReq cureq = new CapacidadUsuariosReq();
		
		Map<Integer, CapacidadEntity> ceMap = Maps
			      .uniqueIndex(ceList, CapacidadEntity::getIdCapacidad);

		perfilCapacidadList
		.stream()
		.forEach( pce -> {
			if (pce.getIdCapacidad() != null) {
				CapacidadEntity ce = ceMap.get(pce.getIdCapacidad());// capacidadRepository.findByIdCapacidad(pce.getIdCapacidad());
				
				if (ce != null) {
					CapacidadUsuariosReqInner curi = new CapacidadUsuariosReqInner();
					curi.setDescripcinCap(DescripcinCapEnum.fromValue(ce.getDescripcion()));
					curi.setIdCapacidad(IdCapacidadEnum.fromValue(ce.getIdCapacidad().toString()));

					cureq.add(curi);
				}
			}
		});
		
		cureps.setInformacionPerfil(cureq);
	
	return cureps; 
}
	
	/*
	 * Buscar Perfil con capacidades
	 */
	private CapacidadUsuariosRes buscarPerfilConCapacidades(Integer idPerfil) {
		log.info("UsuarioService.buscarPerfilConCapacidades");
		
		log.info("idPerfil: {}" , idPerfil);
		CapacidadUsuariosRes cureps = null;

		if (idPerfil != null) {
			PerfilEntity pe = perfilRepository.findByIdPerfil(idPerfil);
			if (pe != null) {
				
				log.info("{}" , pe);
				
				cureps = new CapacidadUsuariosRes();
				cureps.setIdPerfil(pe.getIdPerfil());
				cureps.setDescripcionPerfil(DescripcionPerfilEnum.fromValue(pe.getDescripcion()));

				ArrayList<PerfilCapacidadEntity> perfilCapacidadList = (ArrayList<PerfilCapacidadEntity>) perfilCapacidadRepository
						.findByPerfil(idPerfil);

				log.info("{}" , perfilCapacidadList.size());
				if (CollectionUtils.isNotEmpty(perfilCapacidadList)) {
					capacidadUsuario(perfilCapacidadList, cureps);
				}
			}
		}

		return cureps;
	}
	
	private CapacidadUsuariosRes capacidadUsuario(ArrayList<PerfilCapacidadEntity> perfilCapacidadList, CapacidadUsuariosRes cureps) {
	
			
			CapacidadEntity ce = null;
			CapacidadUsuariosReq cureq = new CapacidadUsuariosReq();
			CapacidadUsuariosReqInner curi = null;

			for (PerfilCapacidadEntity pce : perfilCapacidadList) {
				if (pce.getIdCapacidad() != null) {
					ce = capacidadRepository.findByIdCapacidad(pce.getIdCapacidad());

					if (ce != null) {
						curi = new CapacidadUsuariosReqInner();
						curi.setDescripcinCap(DescripcinCapEnum.fromValue(ce.getDescripcion()));
						curi.setIdCapacidad(IdCapacidadEnum.fromValue(ce.getIdCapacidad().toString()));

						cureq.add(curi);
					}
				}
			}
			cureps.setInformacionPerfil(cureq);
		
		return cureps; 
	}
	
	/*
	 * Validar identidadUsuario
	 */
	private Boolean validarIdentidadUsuario(IdentidadUsuarioResponseVO identidadUsuario) {
		log.info("validarIdentidadUsuario");
		
		return !identidadUsuario.getUsuario().isEmpty();
	}
	
	private PerfilUsuario validarDatosPerfil2(ProfileVO uvo) {
		log.info("validarDatosPerfil2");
		
		PerfilUsuario pf = new PerfilUsuario();
		
		if(uvo.getCommonname() != null) 
			pf.setCommonname(uvo.getCommonname());
		if(uvo.getDepartment() != null) 
			pf.setDepartment(uvo.getDepartment());
		if(uvo.getDescription() != null) 
			pf.setDescription(uvo.getDescription());
		if(uvo.getDistinguishedname() != null) 
			pf.setDistinguishedname(uvo.getDistinguishedname());
		if(uvo.getFirstname() != null) 
			pf.setFirstName(uvo.getFirstname());
		if(uvo.getLastname() != null) 
			pf.setLastName(uvo.getLastname());
		if(uvo.getMail() != null) 
			pf.setMail(uvo.getMail());
		if(uvo.getMemberof() != null) 
			pf.setMemberOf(uvo.getMemberof());
		
		if(uvo.getPhysicaldeliveryofficename() != null) 
			pf.setPhysicaldeliveryofficename(uvo.getPhysicaldeliveryofficename());
		if(uvo.getSamaccountname() != null) 
			pf.setSamaccountname(uvo.getSamaccountname());
		if(uvo.getTitle() != null) 
			pf.setTitle(uvo.getTitle());
		if(uvo.getUid() != null) 
			pf.setUid(uvo.getUid());
		if(uvo.getUri() != null) 
			pf.setUri(uvo.getUri());
		
		return pf;
	}
	
	/*
	 * Validar datos del perfil
	 */
	private PerfilUsuario validarDatosPerfil(UsuarioVO uvo) {
		log.info("validarDatosPerfil");
		
		PerfilUsuario pf = new PerfilUsuario();
		log.info("el usuario {}",uvo);
		return pf;
	}
	
	/*
	 * Validar usuario Admin existente
	 */
	public Boolean validarUsuarioAdmin(CapacidadUsuariosRes perfil) {
		log.info("validarUsuarioAdmin");
		
		Boolean existente = false;
		if (perfil != null && perfil.getIdPerfil() == 1) {

			Query query = new Query();
			
			Criteria aux = Criteria.where(Constantes.PERFIL).is(perfil.getIdPerfil());

			query.addCriteria(aux);

			UsuarioEntity usuario = mongoTemplate.findOne(query, UsuarioEntity.class);

			if (usuario != null) {
				existente = true;
			}
		}

		return existente;
	}
	
}
