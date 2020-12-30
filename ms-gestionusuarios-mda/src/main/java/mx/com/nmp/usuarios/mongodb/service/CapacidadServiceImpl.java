package mx.com.nmp.usuarios.mongodb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner;
import mx.com.nmp.usuarios.mongodb.entity.CapacidadEntity;
import mx.com.nmp.usuarios.utils.Constantes;

@Service
public class CapacidadServiceImpl implements CapacidadService {

	private static final Logger log = LoggerFactory.getLogger(CapacidadServiceImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Boolean validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq) throws Exception {
		log.info("validarCapacidadesCreacion");
		
		Boolean valido = true;
		
		if(!capacidadUsuarioReq.isEmpty()) {
			
			List<Integer> ids = new ArrayList<>();
			List<String> caps = new ArrayList<>();
			
			for(CapacidadUsuariosReqInner capacidad : capacidadUsuarioReq) {
				mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.IdCapacidadEnum idEnum = capacidad.getIdCapacidad();
				mx.com.nmp.usuarios.model.CapacidadUsuariosReqInner.DescripcinCapEnum descEnum = capacidad.getDescripcinCap();
				
				log.info("idEnum: {}", idEnum);
				log.info("descEnum: {}", descEnum);
				
				if(idEnum != null && descEnum != null) {
					ids.add(new Integer(idEnum.toString()));
					caps.add(descEnum.toString());
				} else {
					valido = false;
					
					break;
				}
			}
			
			if(valido.equals(Boolean.FALSE)) {
				return valido;
			} else {
				Query query = new Query();
				query.addCriteria(Criteria.where(Constantes.ID_CAPACIDAD).in(ids));
				query.addCriteria(Criteria.where(Constantes.DESCRIPCION).in(caps));
				
				log.info("query: {}", query);
				
				return mongoTemplate.exists(query, CapacidadEntity.class);
			}
		}
		return false;
	}

}
