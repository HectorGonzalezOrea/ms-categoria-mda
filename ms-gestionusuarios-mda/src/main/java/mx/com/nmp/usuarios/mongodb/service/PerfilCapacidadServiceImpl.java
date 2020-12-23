package mx.com.nmp.usuarios.mongodb.service;

import java.util.ArrayList;
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
import mx.com.nmp.usuarios.model.InternalServerError;
import mx.com.nmp.usuarios.model.ModCapacidadUsuario;
import mx.com.nmp.usuarios.model.ModCapacidadUsuarioInner;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.DescripcinCapEnum;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.IdCapacidadEnum;
import mx.com.nmp.usuarios.mongodb.entity.CapacidadEntity;
import mx.com.nmp.usuarios.mongodb.entity.PerfilEntity;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.mongodb.entity.PerfilCapacidadEntity;
import mx.com.nmp.usuarios.mongodb.repository.PerfilRepository;
import mx.com.nmp.usuarios.utils.Constantes;
import mx.com.nmp.usuarios.mongodb.repository.PerfilCapacidadRepository;
import mx.com.nmp.usuarios.mongodb.repository.CapacidadRepository;

@Service
public class PerfilCapacidadServiceImpl implements PerfilCapacidadService {

	private static final Logger log = LoggerFactory.getLogger(PerfilCapacidadServiceImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private PerfilCapacidadRepository perfilCapacidadRepository;
	@Autowired
	private CapacidadRepository capacidadRepository;
	
	/*
	 * Crear Perfil con Capacidad
	 */
	@Override
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

	@Override
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
	
	@Override
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
	@Override
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
	@Override
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
	 * Validar usuario Admin existente
	 */
	@Override
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
