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
	public CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil, List<String> grupos) {
		log.info("UsuarioService.crearPerfilCapacidad");
		
		PerfilEntity perfil = null;
		
		if (idPerfil != null && informacionPerfil != null) {
			perfil = perfilRepository.findByIdPerfil(idPerfil);

			log.info("perfil: {} ", perfil);

			if (perfil != null) {

				if(!informacionPerfil.isEmpty()) {
					Query query = new Query();
					query.addCriteria(Criteria.where(Constantes.PERFIL).is(idPerfil));
					mongoTemplate.findAllAndRemove(query, PerfilCapacidadEntity.class);
				}
				
				informacionPerfil
				.stream()
				.forEach(ip -> {
					
					log.info("ip: {} ", ip);
					
					Query query = new Query();
					Update update = new Update();
					
					IdCapacidadEnum idEnum = ip.getIdCapacidad();
					
					query.addCriteria(Criteria.where(Constantes.ID_CAPACIDAD).is(Integer.valueOf(idEnum.toString())));
					query.addCriteria(Criteria.where(Constantes.PERFIL).is(idPerfil));
					query.addCriteria(Criteria.where(Constantes.GRUPO).is(grupos.get(0)));
					
					update.set(Constantes.ID_CAPACIDAD,Integer.valueOf(idEnum.toString()));
					update.set(Constantes.PERFIL, idPerfil);
					update.set(Constantes.GRUPO, grupos.get(0));
					
					log.info("query: {} ", query);
					log.info("update: {} ", update);
					
					mongoTemplate.upsert(query, update, PerfilCapacidadEntity.class);
				});
			}
		}

		return this.buscarPerfilConCapacidades(idPerfil, grupos);
	}

	@Override
	public Boolean validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq) throws Exception{
		log.info("validarCapacidades");
		
		Boolean fallido = false;
		
		if(!capacidadUsuarioReq.isEmpty()) {
			
			StringBuilder sb = new StringBuilder();
			sb.append(Constantes.ERROR_MESSAGE_INTERNAL_ERROR_CAP_NO_VALIDAS);
			
			List<Integer> ids = new ArrayList<>();
			List<String> caps = new ArrayList<>();
			
			for(CapacidadUsuariosReqInner capacidad : capacidadUsuarioReq) {
				mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.IdCapacidadEnum idEnum = capacidad.getIdCapacidad();
				mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.DescripcinCapEnum descEnum = capacidad.getDescripcinCap();
				
				if(idEnum != null && descEnum != null) {
					ids.add(Integer.valueOf(idEnum.toString()));
					caps.add(descEnum.toString());
				} else {
					fallido = true;
					
					break;
				}
			}
			//Consultor
			if(fallido.equals(Boolean.TRUE)) {
				return fallido;
			} else {
				Query query = new Query();
				query.addCriteria(Criteria.where(Constantes.ID_CAPACIDAD).all(ids));
				query.addCriteria(Criteria.where(Constantes.DESCRIPCION).all(caps));
				
				return mongoTemplate.exists(query, CapacidadEntity.class);
			}
		}
		return true;
	}
		
	/*
	 * Modificar Capacidades de un perfil
	 */
	@Override
	public CapacidadUsuariosRes modificarPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq modCapacidadReq, List<String> grupos) throws Exception {
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

				for (CapacidadUsuariosReqInner capacidad : modCapacidadReq) {
					aux = Criteria.where(Constantes.PERFIL).is(idPerfil);
					IdCapacidadEnum idEnum = capacidad.getIdCapacidad();

					CapacidadEntity cap = capacidadRepository.findByIdCapacidad(new Integer(idEnum.toString()));

					if (cap != null) {
						log.info("Capacidad");
						// Insert o Update del Perfil-Capacidad

						queryPerfilCapacidad = new Query();
						aux.and(Constantes.ID_CAPACIDAD).is(Integer.valueOf(idEnum.toString()));
						queryPerfilCapacidad.addCriteria(aux);
						updatePerfilCapacidad.set(Constantes.ID_CAPACIDAD, Integer.valueOf(idEnum.toString()));
						mongoTemplate.upsert(queryPerfilCapacidad, updatePerfilCapacidad, PerfilCapacidadEntity.class);
					}
				}
				modificado = true;
			}
		}
		
		CapacidadUsuariosRes resp = null;
		
		if(Boolean.TRUE.equals(modificado)) {
			resp = this.buscarPerfilConCapacidades(idPerfil, grupos);
		}
		
		return resp;
	}
	
	/*
	 * Actualizar Perfil de un usuario
	 */
	@Override
	public CapacidadUsuariosRes actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil, List<String> grupos) throws Exception {
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
			resp = this.buscarPerfilConCapacidades(idPerfil, grupos);
		}
		
		return resp;
	}
	
	/*
	 * Buscar Perfil con capacidades
	 */
	private CapacidadUsuariosRes buscarPerfilConCapacidades(Integer idPerfil, List<String> grupos) {
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

				Query query = new Query();
				query.addCriteria(Criteria.where(Constantes.PERFIL).is(idPerfil));
				query.addCriteria(Criteria.where(Constantes.GRUPO).all(grupos));
				
				List<PerfilCapacidadEntity> list = mongoTemplate.find(query, PerfilCapacidadEntity.class);
				
				log.info("{}" , list.size());
				if (CollectionUtils.isNotEmpty(list)) {
					capacidadUsuario(list, cureps);
				}
			}
		}

		return cureps;
	}
	
	private CapacidadUsuariosRes capacidadUsuario(List<PerfilCapacidadEntity> perfilCapacidadList, CapacidadUsuariosRes cureps) {
	
			
			CapacidadEntity ce = null;
			CapacidadUsuariosReq cureq = new CapacidadUsuariosReq();
			CapacidadUsuariosReqInner curi = null;

			for (PerfilCapacidadEntity pce : perfilCapacidadList) {
				if (pce.getIdCapacidad() != null) {
					ce = capacidadRepository.findByIdCapacidad(pce.getIdCapacidad());

					if (ce != null) {
						curi = new CapacidadUsuariosReqInner();
						curi.setDescripcinCap(DescripcinCapEnum.fromValue(ce.getDescripcion()));
						curi.setIdCapacidad(IdCapacidadEnum.fromValue(ce.getIdCapacidad()));

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
