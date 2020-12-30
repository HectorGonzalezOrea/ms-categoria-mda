package mx.com.nmp.usuarios.mongodb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.DescripcinCapEnum;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.IdCapacidadEnum;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes.DescripcionPerfilEnum;
import mx.com.nmp.usuarios.mongodb.entity.CapacidadEntity;
import mx.com.nmp.usuarios.mongodb.entity.PerfilCapacidadEntity;
import mx.com.nmp.usuarios.mongodb.entity.PerfilEntity;
import mx.com.nmp.usuarios.utils.Constantes;

@Service
public class PerfilServiceImpl implements PerfilService {

	private static final Logger logger = LoggerFactory.getLogger(PerfilServiceImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<CapacidadUsuariosRes> buscarPerfilConCapacidades() {
		List<PerfilEntity> listPetfil = mongoTemplate.findAll(PerfilEntity.class);

		List<CapacidadUsuariosRes> resp = new ArrayList<>();

		listPetfil
		.stream()
		.forEach(p -> {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.PERFIL).is(p.getIdPerfil());
			query.addCriteria(aux);

			CapacidadUsuariosRes cureps = new CapacidadUsuariosRes();

			cureps.setIdPerfil(p.getIdPerfil());
			cureps.setDescripcionPerfil(DescripcionPerfilEnum.fromValue(p.getDescripcion()));

			List<PerfilCapacidadEntity> perfilCapacidadList = mongoTemplate.find(query, PerfilCapacidadEntity.class);
			
			if (CollectionUtils.isNotEmpty(perfilCapacidadList)) {
				List<CapacidadEntity> ceList = mongoTemplate.findAll(CapacidadEntity.class);
				capacidadUsuarioNew(perfilCapacidadList, cureps, ceList);
				resp.add(cureps);
			}

		});

		return resp;
	}

	@Override
	public CapacidadUsuariosRes perfilById(Integer perfil) throws Exception {
		logger.info("perfilById");
		
		Query query = new Query();
		Criteria aux = Criteria.where(Constantes.ID_PERFIL).is(perfil);
		
		query.addCriteria(aux);
		
		PerfilEntity p = mongoTemplate.findOne(query, PerfilEntity.class);
		
		Query query2 = new Query();
		Criteria aux2 = Criteria.where(Constantes.PERFIL).is(p.getIdPerfil());
		query2.addCriteria(aux2);

		CapacidadUsuariosRes cureps = new CapacidadUsuariosRes();

		cureps.setIdPerfil(p.getIdPerfil());
		cureps.setDescripcionPerfil(DescripcionPerfilEnum.fromValue(p.getDescripcion()));

		List<PerfilCapacidadEntity> perfilCapacidadList = mongoTemplate.find(query2, PerfilCapacidadEntity.class);
		
		if (CollectionUtils.isNotEmpty(perfilCapacidadList)) {
			List<CapacidadEntity> ceList = mongoTemplate.findAll(CapacidadEntity.class);
			capacidadUsuarioNew(perfilCapacidadList, cureps, ceList);
		}
		
		return cureps;
	}
	
	private CapacidadUsuariosRes capacidadUsuarioNew(List<PerfilCapacidadEntity> perfilCapacidadList,
			CapacidadUsuariosRes cureps, List<CapacidadEntity> ceList) {

		CapacidadUsuariosReq cureq = new CapacidadUsuariosReq();

		Map<Integer, CapacidadEntity> ceMap = Maps.uniqueIndex(ceList, CapacidadEntity::getIdCapacidad);

		perfilCapacidadList.stream().forEach(pce -> {
			if (pce.getIdCapacidad() != null) {
				CapacidadEntity ce = ceMap.get(pce.getIdCapacidad());

				if (ce != null) {
					CapacidadUsuariosReqInner curi = new CapacidadUsuariosReqInner();
					curi.setDescripcinCap(DescripcinCapEnum.fromValue(ce.getDescripcion()));
					//curi.setIdCapacidad(IdCapacidadEnum.fromValue(ce.getIdCapacidad().toString()));
					curi.setIdCapacidad(IdCapacidadEnum.fromValue(ce.getIdCapacidad()));

					cureq.add(curi);
				}
			}
		});

		cureps.setInformacionPerfil(cureq);

		return cureps;
	}

	@Override
	public Boolean consultarPerfil(Integer idPerfil) throws Exception {
		logger.info("consultarPerfil");
		
		Boolean encontrado = false;
		if(idPerfil != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.ID_PERFIL).is(idPerfil);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, PerfilEntity.class);
		}
		
		logger.info("Encontrado {}", encontrado);
		
		return encontrado;
	}

	@Override
	public Boolean consultarCapacidad(Integer capacidad) throws Exception {
		logger.info("consultarCapacidad");
		
		Boolean encontrado = false;
		if(capacidad != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.ID_CAPACIDAD).is(capacidad);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, CapacidadEntity.class);
		}
		
		logger.info("Encontrado {}", encontrado);
		
		return encontrado;
		
	}

	@Override
	public Boolean consultarCapacidad(List<Integer> capacidades) throws Exception {
		logger.info("consultarCapacidad");
		
		Boolean encontrados = false;
		
		if(!capacidades.isEmpty()) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.ID_CAPACIDAD).in(capacidades);
			query.addCriteria(aux);
			
			encontrados  = mongoTemplate.exists(query, CapacidadEntity.class);
		}
		
		return encontrados;
	}

	@Override
	public PerfilEntity consultarPerfilEntity(Integer idPerfil) throws Exception {
		logger.info("consultarPerfil");
		
		PerfilEntity encontrado = null;
		if(idPerfil != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.ID_PERFIL).is(idPerfil);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.findOne(query, PerfilEntity.class);
		}
		
		logger.info("Encontrado {}", encontrado);
		
		return encontrado;
	}
	
}
