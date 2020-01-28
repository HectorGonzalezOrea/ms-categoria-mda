package mx.com.nmp.consolidados.mongodb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.com.nmp.consolidados.model.Consolidados;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
@Service
public class ConsolidadoService {
	private static final Logger log = LoggerFactory.getLogger(ConsolidadoService.class);
	
	private static final String USUARIO_SEQ_KEY = "consolidado_sequence";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private sequenceGeneratorService SequenceGeneratorService;
	
	
	public Boolean crearConsolidado(Consolidados request) {
		log.info("Registrar documento");
		Boolean insertado = false;
		if(request != null) {			
				ArchivoEntity consolidado = new ArchivoEntity();
				consolidado.setAdjunto(request.getAdjunto());
				consolidado.setVigencia(request.getVigencia());
				consolidado.setNombreAjuste(request.getNombreAjuste());
				consolidado.setEmergente(request.getEmergente());
				consolidado.setFechaAplicacion(request.getFechaAplicacion());
				Long id = SequenceGeneratorService.generateSequence(USUARIO_SEQ_KEY);
				consolidado.setIdArchivo(id);
				mongoTemplate.insert(consolidado);
				insertado =  true;
		}
		return insertado;
	}
	public List<Consolidados> getConsolidados(LocalDate fechaAplicacion){
		List<Consolidados> lstConsolidados=new ArrayList<>();
		Query q=new Query();
		Criteria aux = Criteria.where("fechaAplicacion").is(fechaAplicacion);
		q.addCriteria(aux);
		List<ArchivoEntity> busquedaList = mongoTemplate.find(q, ArchivoEntity.class);
		if(busquedaList!=null) {
			CastConsolidados castConsolidados=new CastConsolidados();
			lstConsolidados=castConsolidados.toVOs(busquedaList);
		}
		return lstConsolidados;
	}
}
