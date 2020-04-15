package mx.com.nmp.usuarios.mongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javassist.expr.Instanceof;
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
import mx.com.nmp.usuarios.model.GeneralResponse;
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
import mx.com.nmp.usuarios.oag.controller.OAGController;
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
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	public static final String NOMBRE = "nombre";
	public static final String APELLIDO_PATERNO = "apellidoPaterno";
	public static final String APELLIDO_MATERNO = "apellidoMaterno";
	public static final String ACTIVO = "activo";
	public static final String USUARIO = "usuario";
	public static final String ID_USUARIO = "idUsuario";
	public static final String ID_CAPACIDAD = "idCapacidad";
	public static final String DESCRIPCION = "descripcion";
	public static final String ACCION = "accion";
	public static final String FECHA = "fecha";
	public static final String ID = "_id";
	public static final String PERFIL = "perfil";
	public static final String ID_PERFIL = "idPerfil";

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
	private OAGController oagController;
	
	private static final String HISTORICO_SEQ_KEY = "historico_sequence";
	private static final String USUARIO_SEQ_KEY = "usuario_sequence";
	
	/*
	 * Crear Historico
	 */
	//public CrearHistoricoRes crearHistorico(AccionEnum accion, Date fecha, Integer idPerfil, String usuario) {
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
			//he.set_id(sequenceGeneratorService.generateSequence(HISTORICO_SEQ_KEY));
			he.setIdHistorialUsuario(sequenceGeneratorService.generateSequence(HISTORICO_SEQ_KEY));
			mongoTemplate.insert(he);
			insertado = true;
		}

		if (Boolean.TRUE.equals(insertado)) {
			resp = new CrearHistoricoRes();
			Query query = new Query();
			Criteria aux = Criteria.where(ID).is(he.get_id());
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
				log.info("resp {} " , resp.toString());
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
			UsuarioEntity user = (UsuarioEntity) usuarioRepository.findByIdUsuario(idUsuario);

			if (user != null) {
				log.info("usuario encontrado");
				Query query = new Query();
				Criteria aux = Criteria.where(USUARIO).is(user.getUsuario());
				query.addCriteria(aux);

				List<HistorialEntity> listHistorico = mongoTemplate.find(query, HistorialEntity.class);

				log.info("listHistorico " + listHistorico.size());

				chres = new ConsultaHistoricoRes();
				List<ResHistorico> historial = null;
				if (CollectionUtils.isNotEmpty(listHistorico)) {
					log.info("lista de historico");
					historial = new ArrayList<ResHistorico>();

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
					chres.setHistorial(historial);
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

	/*
	 * Actualizar Estatus de Usuario
	 */
	public ResEstatus actualizarEstatusUsuario(Integer idUsuario, Boolean activo) {
		log.info("UsuarioService.actualizarEstatusUsuario");
		Boolean actualizar = false;

		if(idUsuario != null && activo != null) {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_USUARIO).is(idUsuario));
			Update update = new Update();
			update.set(ACTIVO, activo);
			UsuarioEntity user = mongoTemplate.findAndModify(query, update, UsuarioEntity.class);

			if (user != null) {
				actualizar = true;
			}
		}

		ResEstatus resp = null;
		if(Boolean.TRUE.equals(actualizar)) {
			resp = new ResEstatus();
			resp.setIdUsuario(idUsuario);
			if(Boolean.TRUE.equals(activo)) {
				resp.setDescripcion(DescripcionEnum.ACTIVO);
			} else {
				resp.setDescripcion(DescripcionEnum.INACTIVO);
			}
		}
		
		return resp;
	}

	/*
	 * Eliminar usuario
	 */
	public Boolean deleteUsuario(Integer idUsuario) {
		log.info("UsuarioService.deleteUsuario");
		Boolean eliminado = false;

		if (idUsuario != null) {
			UsuarioEntity usuario = usuarioRepository.findByIdUsuario(idUsuario);
			if (usuario != null) {
				usuarioRepository.delete(usuario);
				eliminado = true;
			}
		}

		return eliminado;
	}

	/*
	 * Consulta de usuarios
	 */
	public List<InfoUsuario> getUsuarios(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		log.info("UsuarioService.getUsuarios");
		
		Query query = this.busquedaUsuarioNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario, perfil);

		List<UsuarioEntity> busquedaList = mongoTemplate.find(query, UsuarioEntity.class);

		List<InfoUsuario> usuarios = null;

		if (CollectionUtils.isNotEmpty(busquedaList)) {
			usuarios = new ArrayList<InfoUsuario>();
			InfoUsuario infoUsuario = null;

			for (UsuarioEntity aux : busquedaList) {
				infoUsuario = new InfoUsuario();

				infoUsuario.setActivo(aux.getActivo());
				infoUsuario.apellidoMaterno(aux.getApellidoMaterno());
				infoUsuario.setApellidoPaterno(aux.getApellidoPaterno());
				infoUsuario.setIdUsuario(aux.getIdUsuario().intValue());
				infoUsuario.setNombre(aux.getNombre());
				infoUsuario.setUsuario(aux.getUsuario());
				
				CapacidadUsuariosRes perfilc = this.buscarPerfilConCapacidades(aux.getPerfil());
				
				if(perfilc != null) {
					infoUsuario.setPerfil(perfilc);
				}
				
				DepartamentoAreaEntity dae = departamentoAreaRepository.findByIdDepartamento(aux.getDepartamentoArea());
				
				if(dae != null) {
					DepatamentoAreaVO daevo = new DepatamentoAreaVO();
					daevo.setId(dae.getIdDepartamento());
					daevo.setDescripcion(dae.getDescripcion());
					infoUsuario.setDepartamentoArea(daevo);
				}
				
				DireccionEntity de = direccionRepository.findByIdDireccion(aux.getDireccion());
				
				if(de != null) {
					DireccionVO devo = new DireccionVO();
					devo.setId(de.getIdDireccion());
					devo.setDescripcion(de.getDescripcion());
					infoUsuario.setDireccion(devo);
				}
				
				GerenciaEntity ge = gerenciaRepository.findByIdGerencia(aux.getGerencia());
				
				if(ge != null) {
					GerenciaVO gvo = new GerenciaVO();
					gvo.setId(ge.getIdGerencia());
					gvo.setDescripcion(ge.getDescripcion());
					infoUsuario.setGerencia(gvo);
				}
				
				PuestoEntity pe = puestoRepository.findByIdPuesto(aux.getPuesto());
				
				if(pe != null) {
					PuestoVO pvo = new PuestoVO();
					pvo.setId(pe.getIdPuesto());
					pvo.setDescripcion(pe.getDescripcion());
					infoUsuario.setPuesto(pvo);
				}
				
				SubdireccionEntity sde = subdireccionRepository.findByIdSubdireccion(aux.getSubdireccion());
				
				if(sde != null) {
					SubdireccionVO sdvo = new SubdireccionVO();
					
					sdvo.setId(sde.getIdSubdireccion());
					sdvo.setDescripcion(sde.getDescripcion());
					
					infoUsuario.setSubdireccion(sdvo);
				}
				
				usuarios.add(infoUsuario);
			}
		}
		return usuarios;
	}
	
	/*
	 * Consulta usuarios sin filtro
	 */
	
	public List<InfoUsuario> getUsuariosSinFiltro() {
		log.info("UsuarioService.getUsuarios");

		List<UsuarioEntity> busquedaList = mongoTemplate.findAll(UsuarioEntity.class);

		List<InfoUsuario> usuarios = null;

		if (CollectionUtils.isNotEmpty(busquedaList)) {
			usuarios = new ArrayList<InfoUsuario>();
			InfoUsuario infoUsuario = null;

			for (UsuarioEntity aux : busquedaList) {
				infoUsuario = new InfoUsuario();

				infoUsuario.setActivo(aux.getActivo());
				infoUsuario.apellidoMaterno(aux.getApellidoMaterno());
				infoUsuario.setApellidoPaterno(aux.getApellidoPaterno());
				infoUsuario.setIdUsuario(aux.getIdUsuario().intValue());
				infoUsuario.setNombre(aux.getNombre());
				infoUsuario.setUsuario(aux.getUsuario());
				
				CapacidadUsuariosRes perfilc = this.buscarPerfilConCapacidades(aux.getPerfil());
				
				if(perfilc != null) {
					infoUsuario.setPerfil(perfilc);
				}
				
				DepartamentoAreaEntity dae = departamentoAreaRepository.findByIdDepartamento(aux.getDepartamentoArea());
				
				if(dae != null) {
					DepatamentoAreaVO daevo = new DepatamentoAreaVO();
					daevo.setId(dae.getIdDepartamento());
					daevo.setDescripcion(dae.getDescripcion());
					infoUsuario.setDepartamentoArea(daevo);
				}
				
				DireccionEntity de = direccionRepository.findByIdDireccion(aux.getDireccion());
				
				if(de != null) {
					DireccionVO devo = new DireccionVO();
					devo.setId(de.getIdDireccion());
					devo.setDescripcion(de.getDescripcion());
					infoUsuario.setDireccion(devo);
				}
				
				GerenciaEntity ge = gerenciaRepository.findByIdGerencia(aux.getGerencia());
				
				if(ge != null) {
					GerenciaVO gvo = new GerenciaVO();
					gvo.setId(ge.getIdGerencia());
					gvo.setDescripcion(ge.getDescripcion());
					infoUsuario.setGerencia(gvo);
				}
				
				PuestoEntity pe = puestoRepository.findByIdPuesto(aux.getPuesto());
				
				if(pe != null) {
					PuestoVO pvo = new PuestoVO();
					pvo.setId(pe.getIdPuesto());
					pvo.setDescripcion(pe.getDescripcion());
					infoUsuario.setPuesto(pvo);
				}
				
				SubdireccionEntity sde = subdireccionRepository.findByIdSubdireccion(aux.getSubdireccion());
				
				if(sde != null) {
					SubdireccionVO sdvo = new SubdireccionVO();
					
					sdvo.setId(sde.getIdSubdireccion());
					sdvo.setDescripcion(sde.getDescripcion());
					
					infoUsuario.setSubdireccion(sdvo);
				}
				
				usuarios.add(infoUsuario);
			}
		}
		return usuarios;
	}
	

	/*
	 * Crear Perfil con Capacidad
	 */
	public CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil) {
		log.info("UsuarioService.crearPerfilCapacidad");
		
		Boolean creado = false;
		PerfilEntity perfil = null;
		
		if (idPerfil != null && informacionPerfil != null) {
			perfil = (PerfilEntity) perfilRepository.findByIdPerfil(idPerfil);

			log.info("perfil: " + perfil);

			if (perfil != null) {

				Query queryPerfilCapacidad = null;
				Criteria aux = null;
				Update updatePerfilCapacidad = new Update();

				for (CapacidadUsuariosReqInner capacidad : informacionPerfil) {
					aux = null;
					aux = Criteria.where(PERFIL).is(idPerfil);
					IdCapacidadEnum idEnum = capacidad.getIdCapacidad();
					DescripcinCapEnum descEnum = capacidad.getDescripcinCap();

					CapacidadEntity cap = (CapacidadEntity) capacidadRepository
							.findByIdCapacidad(new Integer(idEnum.toString()), descEnum.toString());

					if (cap != null) {
						log.info("Capacidad");
						// Insert o Update del Perfil-Capacidad

						queryPerfilCapacidad = new Query();
						aux.and(ID_CAPACIDAD).is(new Integer(idEnum.toString()));
						queryPerfilCapacidad.addCriteria(aux);
						updatePerfilCapacidad.set(ID_CAPACIDAD, new Integer(idEnum.toString()));
						mongoTemplate.upsert(queryPerfilCapacidad, updatePerfilCapacidad, PerfilCapacidadEntity.class);
					}
				}

				creado = true;
			}
		}
		
		CapacidadUsuariosRes resp = null;
		
		if(creado) {
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
					
					//sb.append(capacidad.toString().replaceAll("\n", ""));
					
					ise.setMensaje(sb.toString());
				} else {
					Query query = new Query();
					Criteria aux = Criteria.where(ID_CAPACIDAD).is(new Integer(idEnum.toString()));
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

					//sb.append(capacidad.toString().replaceAll("\n", ""));
					
					ise.setMensaje(sb.toString());
				} else {
					Query query = new Query();
					Criteria aux = Criteria.where(ID_CAPACIDAD).is(new Integer(idEnum.toString()));
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
	
			perfil = (PerfilEntity) perfilRepository.findByIdPerfil(idPerfil);

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
					aux = Criteria.where(PERFIL).is(idPerfil);
					mx.com.nmp.usuarios.model.ModCapacidadUsuarioInner.IdCapacidadEnum idEnum = capacidad.getIdCapacidad();

					CapacidadEntity cap = (CapacidadEntity) capacidadRepository.findByIdCapacidad(new Integer(idEnum.toString()));

					if (cap != null) {
						log.info("Capacidad");
						// Insert o Update del Perfil-Capacidad

						queryPerfilCapacidad = new Query();
						aux.and(ID_CAPACIDAD).is(new Integer(idEnum.toString()));
						queryPerfilCapacidad.addCriteria(aux);
						updatePerfilCapacidad.set(ID_CAPACIDAD, new Integer(idEnum.toString()));
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
			query.addCriteria(Criteria.where(ID_USUARIO).is(idUsuario));
			Update update = new Update();
			update.set(PERFIL, idPerfil);
			UsuarioEntity user = mongoTemplate.findAndModify(query, update, UsuarioEntity.class);

			if (user != null) {
				actualizar = true;
			}	
		}
		
		if(actualizar) {
			resp = this.buscarPerfilConCapacidades(idPerfil);
		}
		
		return resp;
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
				}
			}
		}

		return cureps;
	}
	
	/*
	 * Consulta Perfil
	 */
	public Object consultaPrefil(String usuario, String token) {
		log.info("consultaPrefil");
		
		log.info("usuario: {}" , usuario);
		
		PerfilUsuario pf = null;
		
		if(usuario != null && !usuario.equals("")) {
			UsuarioEntity user = usuarioRepository.findByUsuario(usuario);
			
			log.info("Concidencias del usuario en Mongo: {}" , user);
			
			if(user != null) {
				IdentidadUsuarioRequestVO iuRequest = new IdentidadUsuarioRequestVO();
				
				FiltroVO filtro = new FiltroVO();
				filtro.setNombre(user.getNombre());
				filtro.setApellidos(user.getApellidoPaterno() + " " + user.getApellidoMaterno());
				
				iuRequest.setFiltro(filtro);				
				
				Object oAux = oagController.identidadUsuario(iuRequest, token, usuario);
				
				IdentidadUsuarioResponseVO identidadUsuario = null;
				TokenProviderErrorVO tpeVo = null;
				
				if(oAux != null) {
					if(oAux instanceof IdentidadUsuarioResponseVO)
						identidadUsuario = (IdentidadUsuarioResponseVO) oAux;
					else if(oAux instanceof TokenProviderErrorVO) {
						tpeVo = (TokenProviderErrorVO) oAux;
					
						return tpeVo;
					}
					
					if (identidadUsuario != null && this.validarIdentidadUsuario(identidadUsuario)) {
						UsuarioVO uvo = identidadUsuario.getUsuario().get(0);

						pf = this.validarDatosPerfil(uvo);

						List<InfoUsuario> listUsuarios = this.getUsuarios(user.getNombre(), user.getApellidoPaterno(),
								user.getApellidoMaterno(), null, user.getUsuario(), null);

						if (!listUsuarios.isEmpty()) {

							pf.setActivo(listUsuarios.get(0).isActivo());
							pf.setApellidoMaterno(listUsuarios.get(0).getApellidoMaterno());
							pf.setApellidoPaterno(listUsuarios.get(0).getApellidoPaterno());
							pf.setDepartamentoArea(listUsuarios.get(0).getDepartamentoArea());
							pf.setDireccion(listUsuarios.get(0).getDireccion());
							pf.setGerencia(listUsuarios.get(0).getGerencia());
							pf.setIdUsuario(listUsuarios.get(0).getIdUsuario());
							pf.setNombre(listUsuarios.get(0).getNombre());
							pf.setPerfil(listUsuarios.get(0).getPerfil());
							pf.setPuesto(listUsuarios.get(0).getPuesto());
							pf.setSubdireccion(listUsuarios.get(0).getSubdireccion());
							pf.setUsuario(listUsuarios.get(0).getUsuario());
						}
					}
				}
			}
		}
		
		return pf;
	}
	
	/*
	 * Consulta Perfil por token
	 */
	public Object consultaPrefilByToken(String token) {
		Object oAux = oagController.identidadUsuario(token);
		
		PerfilUsuario pf = null;
		
		ProfileVO identidadUsuario = null;
		TokenProviderErrorVO tpeVo = null;
		
		if(oAux != null) {
			if(oAux instanceof ProfileVO)
				identidadUsuario = (ProfileVO) oAux;
			else if(oAux instanceof TokenProviderErrorVO) {
				tpeVo = (TokenProviderErrorVO) oAux;
			
				return tpeVo;
			}
		}
		
		if(identidadUsuario != null && identidadUsuario.getSamaccountname() != null) {
			UsuarioEntity user = usuarioRepository.findByUsuario(identidadUsuario.getSamaccountname());
			if(user != null) {
				
				pf = this.validarDatosPerfil2(identidadUsuario);

				List<InfoUsuario> listUsuarios = this.getUsuarios(user.getNombre(), user.getApellidoPaterno(),
						user.getApellidoMaterno(), null, user.getUsuario(), null);

				log.info("{}" , listUsuarios);
				
				if (!listUsuarios.isEmpty() && listUsuarios.get(0) != null) {
					
					InfoUsuario iu = listUsuarios.get(0);
					pf.setActivo(iu.isActivo());
					pf.setApellidoMaterno(iu.getApellidoMaterno());
					pf.setApellidoPaterno(iu.getApellidoPaterno());
					pf.setDepartamentoArea(iu.getDepartamentoArea());
					pf.setDireccion(iu.getDireccion());
					pf.setGerencia(iu.getGerencia());
					pf.setIdUsuario(iu.getIdUsuario());
					pf.setNombre(iu.getNombre());
					pf.setPerfil(iu.getPerfil());
					pf.setPuesto(iu.getPuesto());
					pf.setSubdireccion(iu.getSubdireccion());
					pf.setUsuario(iu.getUsuario());
				}
			}
		}
		
		return pf;
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
		
		/*
		if(uvo.getNombreDistintivo() != null) 
			pf.setUniqueName(uvo.getNombreDistintivo());
		
		// No viene "miembroDe" en la respuesta del OAG
		List<String> miembroDe = new ArrayList<>();
		pf.setMemberOf(miembroDe);
		
		if(uvo.getIdUsuario() != null)
			pf.setName(uvo.getIdUsuario());
		if(uvo.getNombreDominio() != null)
			pf.setRealmName(uvo.getNombreDominio());
		if(uvo.getNombreCompleto() != null)
			pf.setDisplayName(uvo.getNombreCompleto());
		if(uvo.getDescripcion() != null)
			pf.setDescription(uvo.getDescripcion());
		if(uvo.getGuid() != null)
			pf.setGuid(uvo.getGuid());
		if(uvo.getTipoIdentidad() != null)
			pf.setIdentityType(uvo.getTipoIdentidad());
		if(uvo.getCorreo() != null)
			pf.setEmail(uvo.getCorreo());
		if(uvo.getPuesto() != null)
			pf.setTitle(uvo.getPuesto());
		if(uvo.getNombre() != null)
			pf.setFirstName(uvo.getNombre());
		if(uvo.getSegundoNombre() != null)
			pf.setMiddleName(uvo.getSegundoNombre());
		if(uvo.getApellidos() != null)
			pf.setLastName(uvo.getApellidos());
		if(uvo.getTelefonoOficina() != null)
			pf.setWorkPhone(uvo.getTelefonoOficina());
		if(uvo.getTelefonoTrabajo() != null)
			pf.setWorkNumber(uvo.getTelefonoTrabajo());
		if(uvo.getTelefono() != null)
			pf.setHomePhone(uvo.getTelefono());
		if(uvo.getTelefonoMovil() != null)
			pf.setMobile(uvo.getTelefonoMovil());
		if(uvo.getLocalizador() != null)
			pf.setPager(uvo.getLocalizador());
		if(uvo.getFax() != null)
			pf.setFax(uvo.getFax());
		if(uvo.getJefe() != null)
			pf.setManager(uvo.getJefe());
		if(uvo.getZona() != null)
			pf.setTimeZone(uvo.getZona());
		if(uvo.getPreferenciasIdioma() != null)
			pf.setLanguagePreference(uvo.getPreferenciasIdioma());
		if(uvo.getPreferenciasNotificacion() != null)
			pf.setNotificationPreferences(uvo.getPreferenciasNotificacion());
		*/
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
			
			Criteria aux = Criteria.where(PERFIL).is(perfil.getIdPerfil());

			query.addCriteria(aux);

			UsuarioEntity usuario = mongoTemplate.findOne(query, UsuarioEntity.class);

			if (usuario != null) {
				existente = true;
			}
		}

		return existente;
	}
	
	/*
	 * crear Usuario
	 */
	public Boolean crearUsuario(InfoUsuario request) {
		log.info("crearUsuario");
		Boolean insertado = false;
		
		if(request != null) {
			
			List<InfoUsuario> listUsuarios = this.getUsuarios(request.getNombre(), request.getApellidoPaterno(), request.getApellidoMaterno(), null, request.getUsuario(), null);
			
			if(listUsuarios == null ) {
				UsuarioEntity usuario = new UsuarioEntity();
				usuario.setActivo(request.isActivo());
				usuario.setApellidoMaterno(request.getApellidoMaterno());
				usuario.setApellidoPaterno(request.getApellidoPaterno());
				
				String davoJson = ConverterUtil.messageToJson(request.getDepartamentoArea());
				DepatamentoAreaVO davo = ConverterUtil.stringJsonToObjectDepatamentoAreaVO(davoJson);
				usuario.setDepartamentoArea(davo.getId());
				
				String dvoJson = ConverterUtil.messageToJson(request.getDireccion());
				DireccionVO dvo = ConverterUtil.stringJsonToObjectDireccionVO(dvoJson);
				usuario.setDireccion(dvo.getId());
				
				String gvoJson = ConverterUtil.messageToJson(request.getDepartamentoArea());
				GerenciaVO gvo = ConverterUtil.stringJsonToObjectGerenciaVO(gvoJson);
				usuario.setGerencia(gvo.getId());
				
				String pvoJson = ConverterUtil.messageToJson(request.getDepartamentoArea());
				PuestoVO pvo = ConverterUtil.stringJsonToObjectPuestoVO(pvoJson);
				usuario.setPuesto(pvo.getId());
				
				String sdvoJson = ConverterUtil.messageToJson(request.getDepartamentoArea());
				SubdireccionVO sdvo = ConverterUtil.stringJsonToObjectSubdireccionVO(sdvoJson);
				usuario.setSubdireccion(sdvo.getId());
				
				usuario.setUsuario(request.getUsuario());
				usuario.setNombre(request.getNombre());
				usuario.setPerfil(request.getPerfil().getIdPerfil());
				
				Long id = sequenceGeneratorService.generateSequence(USUARIO_SEQ_KEY);
				usuario.setIdUsuario(id);
				
				mongoTemplate.insert(usuario);
				
				insertado =  true;
			}
		}
		
		return insertado;
	}
	
	/*
	 * armado de busqueda de usuario
	 */
	private Query busquedaUsuarioNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		log.info("UsuarioService.busquedaUsuarioNull");
		
		Query query = new Query();

		if (nombre != null) {
			Criteria aux = Criteria.where(NOMBRE).is(nombre);

			if (apellidoPaterno != null) {
				aux.and(APELLIDO_PATERNO).is(apellidoPaterno);
			}

			if (apellidoMaterno != null) {
				aux.and(APELLIDO_MATERNO).is(apellidoMaterno);
			}

			if (activo != null) {
				aux.and(ACTIVO).is(activo);
			}

			if (usuario != null) {
				aux.and(USUARIO).is(usuario);
			}
			if (perfil != null) {
				aux.and(PERFIL).is(perfil);
			}
			query.addCriteria(aux);
		}

		if (apellidoPaterno != null) {
			Criteria aux = Criteria.where(APELLIDO_PATERNO).is(apellidoPaterno);

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}

			if (apellidoMaterno != null) {
				aux.and(APELLIDO_MATERNO).is(apellidoMaterno);
			}

			if (activo != null) {
				aux.and(ACTIVO).is(activo);
			}

			if (usuario != null) {
				aux.and(USUARIO).is(usuario);
			}
			if (perfil != null) {
				aux.and(PERFIL).is(perfil);
			}
			query.addCriteria(aux);
		}

		if (apellidoMaterno != null) {
			Criteria aux = Criteria.where(APELLIDO_MATERNO).is(apellidoMaterno);

			if (apellidoPaterno != null) {
				aux.and(APELLIDO_PATERNO).is(apellidoPaterno);
			}

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}

			if (activo != null) {
				aux.and(ACTIVO).is(activo);
			}

			if (usuario != null) {
				aux.and(USUARIO).is(usuario);
			}
			if (perfil != null) {
				aux.and(PERFIL).is(perfil);
			}
			query.addCriteria(aux);
		}

		if (activo != null) {
			Criteria aux = Criteria.where(ACTIVO).is(activo);

			if (apellidoPaterno != null) {
				aux.and(APELLIDO_PATERNO).is(apellidoPaterno);
			}

			if (apellidoMaterno != null) {
				aux.and(APELLIDO_MATERNO).is(apellidoMaterno);
			}

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}

			if (usuario != null) {
				aux.and(USUARIO).is(usuario);
			}
			if (perfil != null) {
				aux.and(PERFIL).is(perfil);
			}
			query.addCriteria(aux);
		}

		if (usuario != null) {
			Criteria aux = Criteria.where(USUARIO).is(usuario);

			if (apellidoPaterno != null) {
				aux.and(APELLIDO_PATERNO).is(apellidoPaterno);
			}

			if (apellidoMaterno != null) {
				aux.and(APELLIDO_MATERNO).is(apellidoMaterno);
			}

			if (activo != null) {
				aux.and(ACTIVO).is(activo);
			}

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}
			if (perfil != null) {
				aux.and(PERFIL).is(perfil);
			}
			query.addCriteria(aux);
		}
		
		if (perfil != null) {
			Criteria aux = Criteria.where(PERFIL).is(perfil);
			
			if (usuario != null) {
				aux.and(USUARIO).is(usuario);
			}

			if (apellidoPaterno != null) {
				aux.and(APELLIDO_PATERNO).is(apellidoPaterno);
			}

			if (apellidoMaterno != null) {
				aux.and(APELLIDO_MATERNO).is(apellidoMaterno);
			}

			if (activo != null) {
				aux.and(ACTIVO).is(activo);
			}

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}
			query.addCriteria(aux);
		}
		
		log.info("Query: {}" , query.toString());
		return query;
	}
	
	/*
	 * ConsultarPerfil
	 */
	public Boolean consultarPerfil(Integer idPerfil) {
		log.info("consultarPerfil");
		
		Boolean encontrado = false;
		if(idPerfil != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(ID_PERFIL).is(idPerfil);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, PerfilEntity.class);
		}
		
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}
	
	/*
	 * Consultar usuario
	 */
	public Boolean consultarUsuario(String usuario) {
		log.info("consultarUsuario");
		
		Boolean encontrado = false;
		if(usuario != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(USUARIO).is(usuario);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, UsuarioEntity.class);
		}
		
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}
	
	public Boolean consultarIdUsuario(Integer idUsuario) {
		log.info("consultarUsuario");
		
		Boolean encontrado = false;
		if(idUsuario != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(ID_USUARIO).is(idUsuario);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, UsuarioEntity.class);
		}
		
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}
}
