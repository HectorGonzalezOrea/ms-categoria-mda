package mx.com.nmp.consolidados.mongodb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.utils.Constantes;

@Service
public class MongoService {

	private static final Logger log = LoggerFactory.getLogger(MongoService.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	// Constantes

	
	/*
	 * Insert del consolidado en Mongo
	 */
	@Async
	public void insertarConsolidado(ArchivoEntity consolidado) {
		log.info("insertarConsolidado en mongo");
		long inicio = System.currentTimeMillis();
		
		mongoTemplate.insert(consolidado);
		
		long fin = System.currentTimeMillis();
        
        long tiempo = ((fin - inicio)/1000);
		
        log.info("insertarConsolidado: {} segundos" , tiempo);
	}
	
	/*
	 * Consulta consolidados por fecha
	 */
	@SuppressWarnings("deprecation")
	public List<ArchivoEntity> consultaArhivoConsolidadoByDate(Date vigencia) {
		log.info("consultaArhivoConsolidadoByDate");

		long inicio = System.currentTimeMillis();

		Date fechaAplicacionInicioDia = CastConsolidados.resetTimeToDown(vigencia);
		log.info("fecha inicio dia: {}", fechaAplicacionInicioDia);
		Date fechaAplicacionFinDia = CastConsolidados.resetTimeToUp(vigencia);
		log.info("fecha fin dia: {}", fechaAplicacionFinDia);
		Query q = new Query();
		q.addCriteria(Criteria.where(Constantes.FECHA).gte(fechaAplicacionInicioDia).lt(fechaAplicacionFinDia));
		
		

		log.info("q: {}", q);
		
		List<ArchivoEntity> busquedaList = mongoTemplate.find(q, ArchivoEntity.class);
		log.info("query size(): {}", busquedaList.size());
		long fin = System.currentTimeMillis();
		
	    long tiempo =((fin - inicio) / 1000);
		log.info("consultaArhivoConsolidadoByDate: {} segundos" , tiempo);
		
		return busquedaList;
	}
	
	/*
	 * Consulta consolidados por fecha
	 */
	@SuppressWarnings("deprecation")
	public Integer countConsultaArhivoConsolidadoByDate(Date vigencia) {
		log.info("countConsultaArhivoConsolidadoByDate");

		long inicio = System.currentTimeMillis();

		Date fechaAplicacionInicioDia = CastConsolidados.resetTimeToDown(vigencia);
		log.info("fecha inicio dia: {}", fechaAplicacionInicioDia);
		Date fechaAplicacionFinDia = CastConsolidados.resetTimeToUp(vigencia);
		log.info("fecha fin dia: {}", fechaAplicacionFinDia);
		Query q = new Query();
		q.addCriteria(Criteria.where(Constantes.FECHA).gte(fechaAplicacionInicioDia).lt(fechaAplicacionFinDia));
		


		log.info("q: {}", q);
		
		Long count = mongoTemplate.count(q, ArchivoEntity.class);
		log.info("query size(): {}", count);
		long fin = System.currentTimeMillis();
		
		long tiempo = ((fin - inicio) / 1000);
		log.info("countConsultaArhivoConsolidadoByDate: {} segundos" , tiempo);
		
		return count.intValue();
	}
}
