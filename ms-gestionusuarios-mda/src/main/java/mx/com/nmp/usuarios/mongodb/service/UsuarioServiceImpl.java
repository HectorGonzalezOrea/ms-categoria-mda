package mx.com.nmp.usuarios.mongodb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import com.mongodb.bulk.BulkWriteResult;

import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.mongodb.entity.SequenceGeneratorService;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.oag.vo.UsuarioVO;
import mx.com.nmp.usuarios.utils.Constantes;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	private PerfilService perfilService;
	
	private static final String USUARIO_SEQ_KEY = "usuarios_sequence";
	
	@Override
	public void addUser(UsuarioVO usuario) throws Exception {
		logger.info("addUser");
		mongoTemplate.save(usuario);
	}

	@Override
	public void upsertUsers(List<UsuarioVO> usuarios, String grupo) throws Exception {
		logger.info("upsertUsers");
		
		BulkOperations upsert = this.llenarUsuariosParaSincronizar(usuarios);
		BulkWriteResult upsertResults = upsert.execute();
		logger.info("upsertResults: {}" , upsertResults);
		
		this.actualizarInformacionParaSincronizar(usuarios, grupo);
	}

	@Override
	public List<InfoUsuario> getAllUsers(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean estatus, Integer perfil, String usuario, List<String> grupos) throws Exception {

		List<UsuarioEntity> busquedaList = null;
		List<CapacidadUsuariosRes> capList = perfilService.buscarPerfilConCapacidades();

		Map<Integer, CapacidadUsuariosRes> capMap = Maps
			      .uniqueIndex(capList, CapacidadUsuariosRes::getIdPerfil);
		
		List<InfoUsuario> usuarios = new ArrayList<>();
		
		if (nombre == null && apellidoPaterno == null && apellidoMaterno == null && estatus == null && usuario == null && perfil == null) {
			logger.info("sin filtros");
			Query query = new Query();
			query.addCriteria(Criteria.where(Constantes.GRUPOS).all(grupos));
			query.addCriteria(Criteria.where(Constantes.PERFIL).ne(Integer.valueOf(1)));

			busquedaList = mongoTemplate.find(query, UsuarioEntity.class);
		} else {
			logger.info("con filtros");
			Query query = this.busquedaUsuarioConFiltro(nombre, apellidoPaterno, apellidoMaterno, estatus, usuario, perfil, grupos);
			busquedaList = mongoTemplate.find(query, UsuarioEntity.class);
		}
		
		if (CollectionUtils.isNotEmpty(busquedaList)) {
			busquedaList
			.stream()
			.forEach( aux -> {
				try {
					usuarios.add(this.casterEntityToVo(aux, capMap));
				} catch(Exception e) {
					logger.error("Error en parseo de un usuario");
				}
			});
		}
		
		return usuarios;
	}
	
	@Override
	public Boolean actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil, List<String> grupos) throws Exception {
		logger.info("actualizarPerfilUsuario");
		
		Boolean actualizado = false;
		
		Query query = new Query();
		query.addCriteria(Criteria.where(Constantes.ID_USUARIO).is(idUsuario).and(Constantes.ACTIVO).is(Boolean.TRUE));
		query.addCriteria(Criteria.where(Constantes.GRUPOS).all(grupos));
		query.addCriteria(Criteria.where(Constantes.PERFIL).ne(Integer.valueOf(1)));
		logger.info("Query: {}" , query);
		
		Update update = new Update();
		update.set(Constantes.PERFIL, idPerfil);
		UsuarioEntity user = mongoTemplate.findAndModify(query, update, UsuarioEntity.class);
		
		if(user != null) {
			actualizado = true;
		}
		
		return actualizado;
	}
	
	@Override
	public Boolean actualizarEstatusUsuario(Integer idUsuario, Boolean estatus, List<String> grupos) throws Exception {
		logger.info("actualizarEstatusUsuario");

		Boolean actualizar = false;

		Query query = new Query();
		query.addCriteria(Criteria.where(Constantes.ID_USUARIO).is(idUsuario));
		query.addCriteria(Criteria.where(Constantes.GRUPOS).all(grupos));
		query.addCriteria(Criteria.where(Constantes.PERFIL).ne(Integer.valueOf(1)));

		Update update = new Update();
		update.set(Constantes.ACTIVO, estatus);
		UsuarioEntity user = mongoTemplate.findAndModify(query, update, UsuarioEntity.class);

		if (user != null) {
			actualizar = true;
		}

		return actualizar;
	}
	
	@Override
	public Boolean deleteUsuario(Integer idUsuario, List<String> grupos) throws Exception {
		logger.info("deleteUsuario");

		Boolean actualizar = false;

		Query query = new Query();
		query.addCriteria(Criteria.where(Constantes.ID_USUARIO).is(idUsuario));
		query.addCriteria(Criteria.where(Constantes.GRUPOS).all(grupos));
		query.addCriteria(Criteria.where(Constantes.PERFIL).ne(Integer.valueOf(1)));

		Update update = new Update();
		update.set(Constantes.ACTIVO, Boolean.FALSE);
		update.set(Constantes.PERFIL, null);
		UsuarioEntity user = mongoTemplate.findAndModify(query, update, UsuarioEntity.class);

		if (user != null) {
			actualizar = true;
		}

		return actualizar;
	}
	
	@Override
	public PerfilUsuario consultaUsuarioPerfil(Integer usuario, List<String> grupos) throws Exception {
		logger.info("consultaUsuarioPerfil por id de usuario");
		
		PerfilUsuario user = null;
		
		Query query = new Query();
		query.addCriteria(Criteria.where(Constantes.ID_USUARIO).is(usuario));
		query.addCriteria(Criteria.where(Constantes.GRUPOS).all(grupos));
		
		logger.info("query: {}", query);
		
		UsuarioEntity entity = mongoTemplate.findOne(query, UsuarioEntity.class);
		
		List<CapacidadUsuariosRes> capList = perfilService.buscarPerfilConCapacidades();

		Map<Integer, CapacidadUsuariosRes> capMap = Maps
			      .uniqueIndex(capList, CapacidadUsuariosRes::getIdPerfil);
		
		user = this.casterEntityToPerfil(entity, capMap);
		
		return user;
	}
	
	@Override
	public PerfilUsuario consultaUsuarioPerfil(Integer usuario) throws Exception {
		logger.info("consultaUsuarioPerfil por usuario");
		
		PerfilUsuario user = null;
		
		Query query = new Query();
		query.addCriteria(Criteria.where(Constantes.ID_USUARIO).is(usuario));
		
		UsuarioEntity entity = mongoTemplate.findOne(query, UsuarioEntity.class);
		
		List<CapacidadUsuariosRes> capList = perfilService.buscarPerfilConCapacidades();

		Map<Integer, CapacidadUsuariosRes> capMap = Maps
			      .uniqueIndex(capList, CapacidadUsuariosRes::getIdPerfil);
		
		user = this.casterEntityToPerfil(entity, capMap);
		
		return user;
	}
	
	@Override
	public PerfilUsuario consultaUsuarioPerfil(String usuario) throws Exception {
		logger.info("consultaUsuarioPerfil por usuario");
		
		PerfilUsuario user = null;
		
		Query query = new Query();
		query.addCriteria(Criteria.where(Constantes.USUARIO).is(usuario));

		UsuarioEntity entity = mongoTemplate.findOne(query, UsuarioEntity.class);
		
		List<CapacidadUsuariosRes> capList = perfilService.buscarPerfilConCapacidades();

		Map<Integer, CapacidadUsuariosRes> capMap = Maps
			      .uniqueIndex(capList, CapacidadUsuariosRes::getIdPerfil);
		
		user = this.casterEntityToPerfil(entity, capMap);
		
		logger.info("user: {}", user);
		
		return user;
	}
	
	@Override
	public Boolean consultarUsuario(String usuario) throws Exception {
		logger.info("consultarUsuario por usuario");
		
		Boolean encontrado = false;
		if(usuario != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.USUARIO).is(usuario);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, UsuarioEntity.class);
		}
		return encontrado;
	}

	private PerfilUsuario casterEntityToPerfil(UsuarioEntity user, Map<Integer, CapacidadUsuariosRes> capMap) {
		PerfilUsuario vo = null;
		
		if(user != null) {
			vo = new PerfilUsuario();
			
			vo.setIdUsuario(user.getIdUsuario().intValue());
			vo.setNombre(user.getNombre());
			vo.setApellidoPaterno(user.getApellidoPaterno());
			vo.setApellidoMaterno(user.getApellidoMaterno());
			vo.setUsuario(user.getUsuario());
			
			if(user.getPerfil() != null) {
				vo.setPerfil(capMap.get(user.getPerfil()));
			}
			
			vo.setCommonname(user.getCommonname());
			vo.setDescription(user.getDescription());
			vo.setDistinguishedname(user.getDistinguishedname());
			vo.setFirstName(user.getFirstname());
			vo.setLastName(user.getLastname());
			vo.setMail(user.getMail());
			vo.setMemberOf(user.getMemberof());
			vo.setSamaccountname(user.getSamaccountname());
			vo.setTitle(user.getTitle());
			vo.setUid(user.getUid());

			vo.setActivo(user.getActivo());
		}
		
		return vo;
	}
	
	private InfoUsuario casterEntityToVo(UsuarioEntity user, Map<Integer, CapacidadUsuariosRes> capMap) {
		InfoUsuario vo = null;
		
		if(user != null) {
			vo = new InfoUsuario();
			
			vo.setIdUsuario(user.getIdUsuario().intValue());
			vo.setNombre(user.getNombre());
			vo.setApellidoPaterno(user.getApellidoPaterno());
			vo.setApellidoMaterno(user.getApellidoMaterno());
			vo.setUsuario(user.getUsuario());
			if(user.getPerfil() != null) {
				vo.setPerfil(capMap.get(user.getPerfil()));
			}
			
			vo.setActivo(user.getActivo());
		}
		
		return vo;
	}
	
	private void actualizarInformacionParaSincronizar(List<UsuarioVO> usuarios, String grupo) {
		logger.info("actualizarInformacionParaSincronizar");
		
		BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, UsuarioEntity.class);

		List<String> ids = new ArrayList<>();
		
		if(!usuarios.isEmpty()) {
			for(UsuarioVO vo : usuarios) {
				ids.add(vo.getIdUsuario());
			}
			
			Query queryUpdateMulti = new Query();
			
			queryUpdateMulti
			.addCriteria(new Criteria(Constantes.USUARIO).in(ids))
			.addCriteria(new Criteria(Constantes.ACTIVO).exists(false))
			.addCriteria(new Criteria(Constantes.PERFIL).ne(Integer.valueOf(1)));

			List<UsuarioEntity> usuariosCreados = mongoTemplate.find(queryUpdateMulti, UsuarioEntity.class);
			
			logger.info("usuarios creados: {}", usuariosCreados.size());
			
			usuariosCreados
			.stream()
			.forEach( u -> {
				
				logger.info("usuario a: {}", u);
				
				Update updateMulti = new Update();
				Long id = sequenceGeneratorService.generateSequence(USUARIO_SEQ_KEY);
				
				logger.info("idUsuario: {}", id);
				
				updateMulti.set(Constantes.ID_USUARIO, id);
				updateMulti.set(Constantes.ACTIVO, false);
				updateMulti.addToSet(Constantes.GRUPOS, grupo);
				
				Query queryAux = new Query();
				queryAux.addCriteria(new Criteria(Constantes.USUARIO).is(u.getUsuario()));
				
				logger.info("queryAux: {}", queryAux);
				
				bulkOps.updateMulti(queryAux, updateMulti);
				
			});
			
			if(!usuariosCreados.isEmpty()) {
				BulkWriteResult updateResults = bulkOps.execute();
				logger.info("updateResults: {}" , updateResults);
			}
		}

	}
	
	private BulkOperations llenarUsuariosParaSincronizar(List<UsuarioVO> usuarios) {
		logger.info("llenarUsuariosParaSincronizar");

		BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, UsuarioEntity.class);

		if (!usuarios.isEmpty()) {
			usuarios
			.stream()
			.forEach(u -> {

				logger.info("usuario: {}" , u);
				Update upSert = new Update();

				Query queryUpSert = new Query()
					.addCriteria(new Criteria("usuario").in(u.getIdUsuario()));

				upSert.set("nombre", u.getNombre());

				if (u.getApellidos() != null) {
					String[] aps = u.getApellidos().split(" ");

					logger.info("aps: {}" , aps.length);
					
					if (aps.length == 2) {
						upSert.set(Constantes.APELLIDO_PATERNO, aps[0]);
						upSert.set(Constantes.APELLIDO_MATERNO, aps[1]);
					}

					if(aps.length > 2) {
						StringBuilder sb = new StringBuilder();
						
						for(int i = 0; i<= aps.length - 2; i++) {
							sb.append(aps[i]);
							sb.append(" ");
						}
						
						upSert.set(Constantes.APELLIDO_PATERNO, sb.toString().trim());
						upSert.set(Constantes.APELLIDO_MATERNO, aps[aps.length - 1]);
					}
					
					if(aps.length < 2) {
						upSert.set(Constantes.APELLIDO_PATERNO, u.getApellidos());
					}
				}

				upSert.set(Constantes.USUARIO, u.getIdUsuario());
				upSert.set(Constantes.UID, u.getGuid());
				upSert.set(Constantes.MAIL, u.getCorreo());
				upSert.set(Constantes.LAST_NAME, u.getApellidos());
				upSert.set(Constantes.FIRST_NAME, u.getNombre());
				upSert.set(Constantes.DISTINGUISHEDNAME, u.getNombreDistintivo());
				upSert.set(Constantes.DESCRIPTION, u.getDescripcion());
				upSert.set(Constantes.SAMACCOUNTNAME, u.getNombreCompleto());
				upSert.set(Constantes.COMMONNAME, u.getNombreDominio());
				upSert.set(Constantes.TITLE, u.getPuesto());
				
				bulkOps.upsert(queryUpSert, upSert);
				
			});
		}

		return bulkOps;
	}
	
	/*
	 * armado de busqueda de usuario
	 */
	private Query busquedaUsuarioConFiltro(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil, List<String> grupos) {
		logger.info("busquedaUsuarioNull");
		
		Query query = new Query();
		query.addCriteria(Criteria.where(Constantes.GRUPOS).all(grupos));
		query.addCriteria(Criteria.where(Constantes.PERFIL).ne(Integer.valueOf(1)));
		
		if (nombre != null) {
			query.addCriteria(validarNombreNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario, perfil));
		}
		if (apellidoPaterno != null) {
			query.addCriteria(validarApellidoPaternoNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario, perfil));
		}
		if (apellidoMaterno != null) {
			query.addCriteria(validarApellidoMaternoNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario, perfil));
		}
		if (activo != null) {
			query.addCriteria(validarActivoNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario, perfil));
		}
		if (usuario != null) {
			query.addCriteria(validarUsuarioNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario, perfil));
		}
		if (perfil != null) {
			query.addCriteria(validarPerfilNull(nombre, apellidoPaterno, apellidoMaterno, activo, usuario, perfil));
		}
		
		logger.info("Query: {}" , query);
		return query;
	}
	
	private Criteria validarNombreNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		
		Criteria aux = Criteria.where(Constantes.NOMBRE).is(nombre);

		if (apellidoPaterno != null) {
			aux.and(Constantes.APELLIDO_PATERNO).is(apellidoPaterno);
		}

		if (apellidoMaterno != null) {
			aux.and(Constantes.APELLIDO_MATERNO).is(apellidoMaterno);
		}

		if (activo != null) {
			aux.and(Constantes.ACTIVO).is(activo);
		}

		if (usuario != null) {
			aux.and(Constantes.USUARIO).is(usuario);
		}
		if (perfil != null) {
			aux.and(Constantes.PERFIL).is(perfil);
		}
		
		return aux;
	}
	
	private  Criteria validarApellidoPaternoNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		Criteria aux = Criteria.where(Constantes.APELLIDO_PATERNO).is(apellidoPaterno);

		if (nombre != null) {
			aux.and(Constantes.NOMBRE).is(nombre);
		}

		if (apellidoMaterno != null) {
			aux.and(Constantes.APELLIDO_MATERNO).is(apellidoMaterno);
		}

		if (activo != null) {
			aux.and(Constantes.ACTIVO).is(activo);
		}

		if (usuario != null) {
			aux.and(Constantes.USUARIO).is(usuario);
		}
		if (perfil != null) {
			aux.and(Constantes.PERFIL).is(perfil);
		}
		
		return aux;
	}
	
	private  Criteria validarApellidoMaternoNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		Criteria aux = Criteria.where(Constantes.APELLIDO_MATERNO).is(apellidoMaterno);

		if (apellidoPaterno != null) {
			aux.and(Constantes.APELLIDO_PATERNO).is(apellidoPaterno);
		}

		if (nombre != null) {
			aux.and(Constantes.NOMBRE).is(nombre);
		}

		if (activo != null) {
			aux.and(Constantes.ACTIVO).is(activo);
		}

		if (usuario != null) {
			aux.and(Constantes.USUARIO).is(usuario);
		}
		if (perfil != null) {
			aux.and(Constantes.PERFIL).is(perfil);
		}
		return aux;
	}
	
	private  Criteria validarActivoNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		Criteria aux = Criteria.where(Constantes.ACTIVO).is(activo);

		if (apellidoPaterno != null) {
			aux.and(Constantes.APELLIDO_PATERNO).is(apellidoPaterno);
		}

		if (apellidoMaterno != null) {
			aux.and(Constantes.APELLIDO_MATERNO).is(apellidoMaterno);
		}

		if (nombre != null) {
			aux.and(Constantes.NOMBRE).is(nombre);
		}

		if (usuario != null) {
			aux.and(Constantes.USUARIO).is(usuario);
		}
		if (perfil != null) {
			aux.and(Constantes.PERFIL).is(perfil);
		}
		return aux;
	}
	
	private  Criteria validarUsuarioNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		Criteria aux = Criteria.where(Constantes.USUARIO).is(usuario);

		if (apellidoPaterno != null) {
			aux.and(Constantes.APELLIDO_PATERNO).is(apellidoPaterno);
		}

		if (apellidoMaterno != null) {
			aux.and(Constantes.APELLIDO_MATERNO).is(apellidoMaterno);
		}

		if (activo != null) {
			aux.and(Constantes.ACTIVO).is(activo);
		}

		if (nombre != null) {
			aux.and(Constantes.NOMBRE).is(nombre);
		}
		if (perfil != null) {
			aux.and(Constantes.PERFIL).is(perfil);
		}
		
		return aux;
	}
	
	private Criteria validarPerfilNull(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil) {
		Criteria aux = Criteria.where(Constantes.PERFIL).is(perfil);
		
		if (usuario != null) {
			aux.and(Constantes.USUARIO).is(usuario);
		}

		if (apellidoPaterno != null) {
			aux.and(Constantes.APELLIDO_PATERNO).is(apellidoPaterno);
		}

		if (apellidoMaterno != null) {
			aux.and(Constantes.APELLIDO_MATERNO).is(apellidoMaterno);
		}

		if (activo != null) {
			aux.and(Constantes.ACTIVO).is(activo);
		}

		if (nombre != null) {
			aux.and(Constantes.NOMBRE).is(nombre);
		}
		return aux;
	}

	@Override
	public UsuarioEntity consultarUsuario(Integer usuario) throws Exception {
		logger.info("consultarUsuario");
		
		UsuarioEntity encontrado = null;
		if(usuario != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.ID_USUARIO).is(usuario.longValue());
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.findOne(query, UsuarioEntity.class);
		}
		
		logger.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}

	@Override
	public UsuarioEntity consultarUsuarios(String usuario) throws Exception {
		logger.info("consultarUsuario");
		
		UsuarioEntity encontrado = null;
		if(usuario != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.USUARIO).is(usuario);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.findOne(query, UsuarioEntity.class);
		}
		
		logger.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}

}
