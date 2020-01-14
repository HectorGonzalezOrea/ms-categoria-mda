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

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes.DescripcionPerfilEnum;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.ModCapacidadUsuario;
import mx.com.nmp.usuarios.model.ModCapacidadUsuarioInner;
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
	public static final String ID_PERFIL = "idPerfil";
	public static final String ID_CAPACIDAD = "idCapacidad";
	public static final String DESCRIPCION = "descripcion";
	public static final String ACCION = "accion";
	public static final String FECHA = "fecha";
	public static final String ID = "_id";

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
	private static final String HISTORICO_SEQ_KEY = "historico_sequence";
	
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
			he.set_id(sequenceGeneratorService.generateSequence(HISTORICO_SEQ_KEY));
			mongoTemplate.insert(he);
			insertado = true;
		}

		if (insertado) {
			resp = new CrearHistoricoRes();
			Query query = new Query();
			Criteria aux = Criteria.where(ID).is(he.get_id());
			query.addCriteria(aux);

			HistorialEntity historico = (HistorialEntity) mongoTemplate.findOne(query, HistorialEntity.class);

			if (historico != null) {
				resp.setFecha(historico.getFecha());
				resp.setUsuario(historico.getUsuario());
				resp.setIdRegistro(historico.get_id().intValue());
				resp.setAcciones(AccionesEnum.fromValue(historico.getAccion()));
				if (historico.getIdPerfil() != null) {
					CapacidadUsuariosRes cureps = this.buscarPerfilConCapacidades(historico.getIdPerfil());
					if (cureps != null) {
						resp.setPerfil(cureps);
					}
				}
				log.info("resp" + resp.toString());
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

						re.setIdRegistro(he.get_id().intValue());
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
		if(actualizar) {
			resp = new ResEstatus();
			resp.setIdUsuario(idUsuario);
			if(activo) {
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
			UsuarioEntity usuario = (UsuarioEntity) usuarioRepository.findByIdUsuario(idUsuario);
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
	public List<InfoUsuario> getUsuarios(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario) {
		log.info("UsuarioService.getUsuarios");
		
		Query query = this.busquedaUsuarioNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario);

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
				infoUsuario.setIdUsuario(aux.getIdUsuario());
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
					aux = Criteria.where(ID_PERFIL).is(idPerfil);
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

	/*
	 * Modificar Capacidades de un perfil
	 */
	public CapacidadUsuariosRes modificarPerfilCapacidad(Integer idPerfil, ModCapacidadUsuario modCapacidadReq) {
		log.info("UsuarioService.modificarPerfilCapacidad");
		
		
		Boolean modificado = false;
		PerfilEntity perfil = null;
		
		if(idPerfil != null && modCapacidadReq !=null) {
			perfil = (PerfilEntity) perfilRepository.findByIdPerfil(idPerfil);

			log.info("perfil: " + perfil);

			if (perfil != null) {
				List<PerfilCapacidadEntity> pcList = perfilCapacidadRepository.findByIdPerfil(idPerfil);
				
				// se limpia
				for(PerfilCapacidadEntity pc : pcList) {
					perfilCapacidadRepository.delete(pc);
				}
				
				Query queryPerfilCapacidad = null;
				Criteria aux = null;
				Update updatePerfilCapacidad = new Update();

				for (ModCapacidadUsuarioInner capacidad : modCapacidadReq) {
					aux = null;
					aux = Criteria.where(ID_PERFIL).is(idPerfil);
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
		
		if(modificado) {
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
			update.set(ID_PERFIL, idPerfil);
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
		
		log.info("idPerfil: " + idPerfil);
		CapacidadUsuariosRes cureps = null;

		if (idPerfil != null) {
			PerfilEntity pe = (PerfilEntity) perfilRepository.findByIdPerfil(idPerfil);
			if (pe != null) {
				cureps = new CapacidadUsuariosRes();
				cureps.setIdPerfil(pe.getIdPerfil());
				cureps.setDescripcionPerfil(DescripcionPerfilEnum.fromValue(pe.getDescripcion()));

				ArrayList<PerfilCapacidadEntity> perfilCapacidadList = (ArrayList<PerfilCapacidadEntity>) perfilCapacidadRepository
						.findByIdPerfil(idPerfil);

				if (CollectionUtils.isNotEmpty(perfilCapacidadList)) {
					CapacidadEntity ce = null;
					CapacidadUsuariosReq cureq = new CapacidadUsuariosReq();
					CapacidadUsuariosReqInner curi = null;

					for (PerfilCapacidadEntity pce : perfilCapacidadList) {
						if (pce.getIdCapacidad() != null) {
							ce = (CapacidadEntity) capacidadRepository.findByIdCapacidad(pce.getIdCapacidad());

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
	 * armado de busqueda de usuario
	 */
	private Query busquedaUsuarioNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario) {
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
			query.addCriteria(aux);
		}
		log.info("Query: " + query.toString());
		return query;
	}
	
	public InfoUsuario crearUsuario(Integer idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String usuario, Object puesto, Object direccion, Object subDireccion, Object gerencia, Object departamentoArea, CapacidadUsuariosRes perfil, Boolean activo) {
		log.info("UsuarioService.crearHistorico");
		
		InfoUsuario resp =null;
		
		Boolean insertado = false;

		UsuarioEntity ue = new UsuarioEntity();
		
		if(idUsuario != null && nombre != null && apellidoPaterno != null && apellidoMaterno != null && usuario != null && puesto != null && direccion != null && subDireccion != null && gerencia != null && departamentoArea != null && perfil != null && activo != null) {
			
			ue.setIdUsuario(idUsuario);
			ue.setNombre(nombre);
			ue.setApellidoPaterno(apellidoPaterno);
			ue.setApellidoMaterno(apellidoMaterno);
			ue.setUsuario(usuario);
			if (puesto != null) {
				
				PuestoEntity pe = (PuestoEntity) puestoRepository.findByIdPuesto((Integer) puesto);
				if (pe != null)
					ue.setPuesto((Integer) puesto);
			}
			ue.setDireccion((Integer) direccion);
			ue.setSubdireccion((Integer) subDireccion);
			ue.setGerencia((Integer) gerencia);
			ue.setDepartamentoArea((Integer) departamentoArea);
			ue.setPerfil(perfil);
			ue.setActivo(ue.getActivo());
			ue.set_id(sequenceGeneratorService.generateSequence(HISTORICO_SEQ_KEY));
			mongoTemplate.insert(ue);
			insertado =true;
		}
		if (insertado == true) {
			resp = new InfoUsuario();
			Query query = new Query();
			Criteria aux = Criteria.where(ID).is(ue.get_id());
			query.addCriteria(aux);

			UsuarioEntity insertar = (UsuarioEntity) mongoTemplate.findOne(query, UsuarioEntity.class);
			
			if (insertar != null) {
				log.info("insertados estos valores");
				resp.setIdUsuario(insertar.getIdUsuario());
				resp.setNombre(insertar.getNombre());
				resp.setApellidoPaterno(insertar.getApellidoPaterno());
				resp.setApellidoMaterno(insertar.getApellidoMaterno());
				resp.setUsuario(insertar.getUsuario());
				resp.setPuesto(insertar.getPuesto());
				resp.setDireccion(insertar.getDireccion());
				resp.setSubdireccion(insertar.getSubdireccion());
				resp.setGerencia(insertar.getGerencia());
				resp.setDepartamentoArea(insertar.getDepartamentoArea());
				resp.setActivo(insertar.getActivo());
				if (insertar.getPerfil() != null) {
					CapacidadUsuariosRes cureps = this.buscarPerfilConCapacidades(insertar.getPerfil());
					if (cureps != null) {
						resp.setPerfil(cureps);
					}
				}
				log.info("resp" + resp.toString());
			}
			
			
		}
		
		return resp;
		
	
	}


}
