package mx.com.nmp.gestionbolsas.mongodb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.com.nmp.gestionbolsas.model.Bolsa;
import mx.com.nmp.gestionbolsas.model.ListaBolsas;
import mx.com.nmp.gestionbolsas.model.ListaBolsasInner;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsas;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsasInner;
import mx.com.nmp.gestionbolsas.mongodb.entity.BolsasEntity;
import mx.com.nmp.gestionbolsas.mongodb.entity.SequenceGeneratorService;
import mx.com.nmp.gestionbolsas.mongodb.entity.TipoBolsaEntity;
import mx.com.nmp.gestionbolsas.mongodb.repository.BolsaRepository;

@Service
public class BolsasService {
	private static final Logger log = LoggerFactory.getLogger(BolsasService.class);
	
	public static final String ID_TIPO = "idTipo";
	public static final String NOMBRE = "nombre";
	public static final String RAMO = "ramo";
	public static final String SUBRAMO = "subramo";
	public static final String FACTOR = "factor";
	
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private BolsaRepository bolsaRepository;
	
	private static final String BOLSA_SEQ_KEY ="bolsas_sequence";
	
	
	//Crear Bolsa.
	
	public Boolean crearBolsa (Bolsa peticion) {
		log.info("crear bolsa");
		Boolean insertado = false;
		if(peticion != null) {
			BolsasEntity bolsa = new BolsasEntity();
			bolsa.setTipo(peticion.getTipo());
			bolsa.setNombre(peticion.getNombre());
			bolsa.setRamo(peticion.getRamo());
			bolsa.setSubramo(peticion.getSubramo());
			bolsa.setFactor(peticion.getFactor());
			bolsa.setSucursales(peticion.getSucursales());
			bolsa.setAutor(peticion.getAutor());
			
			Integer id = (int) sequenceGeneratorService.generateSequence(BOLSA_SEQ_KEY);
			bolsa.setidBolsa(id);
			
			mongoTemplate.insert(bolsa);
			insertado =true;
		
			
			
		}
		
		return insertado;
	}
	
	
	//Consulta de bolsas.
	public List<ListaBolsas> getBolsas(String idTipo, String nombre, String ramo, String subramo, String factor ){
		log.info("BolsasService.getBolsas");
		Query query = this.busquedaBolsaNull(idTipo, nombre, ramo, subramo, factor);
		
		List<BolsasEntity> busquedaList = mongoTemplate.find(query, BolsasEntity.class);
		List<ListaBolsas> bolsas = null;
		List<ListaBolsasInner> bolsasIn= null;
		if (CollectionUtils.isNotEmpty(busquedaList)) {
			bolsas = new ArrayList<ListaBolsas>();
			ListaBolsas listaBolsas = null;
			
			
			for (BolsasEntity aux : busquedaList) {
				listaBolsas = new ListaBolsas();
			
			}
			
			
		}
		
		return null;
		
	}
	
	
	//Eliminar una bolsa.
	
	public Boolean deleteBolsa(Integer idBolsa) {
		log.info("BolsasService.deleteBolsa");
		Boolean eliminado = false;
		
		if(idBolsa != null) {
			BolsasEntity bolsa = (BolsasEntity) bolsaRepository.findByIdBolsa(idBolsa);
			if(bolsa != null) {
				bolsaRepository.delete(bolsa);
				eliminado =true;
				
			}
		}
		
		return eliminado;
		
	}
	
	//Actualiza bolsas
	//****************
	
	public Boolean updateBolsa(Bolsa peticion) {
		log.info("BolsasService.updateBolsa");
		Boolean actualizado =false;
		BolsasEntity bolsa = null;
		bolsa =mongoTemplate.findOne(
				Query.query(Criteria.where("_id").is(peticion.getId())), BolsasEntity.class);
		bolsa.setNombre(peticion.getNombre());
		bolsa.setRamo(peticion.getRamo());
		bolsa.setSubramo(peticion.getSubramo());
		bolsa.setFactor(peticion.getFactor());
		bolsa.setTipo(peticion.getTipo());
		bolsa.setSucursales(peticion.getSucursales());
		bolsa.setAutor(peticion.getAutor());
		mongoTemplate.save(bolsa);
		actualizado = true;
		
		
		return actualizado;
		
	}
	
	//Consulta tipo de Bolsa.
	//***********************
	public ListaTipoBolsasInner consultaTipoBolsa () {
		log.info("BolsasService.consultaTipoBolsa");
		Query query = new Query().with(new Sort("id", "-1"));
		List<TipoBolsaEntity> tipoBolsa = null;
		tipoBolsa = mongoTemplate.find(query, TipoBolsaEntity.class);
		ListaTipoBolsasInner lista =new ListaTipoBolsasInner();
		log.info("respuesta: " + tipoBolsa);
		if(tipoBolsa!= null) {
			log.info("Respuesta: " + tipoBolsa);
			lista.setTipo(tipoBolsa);
		}
		
		return lista;
		
	}
	
	
	
	
	
	
	
	//Armado de Busqueda de Bolsas
	private Query busquedaBolsaNull(String idTipo, String nombre, String ramo, String subramo, String factor) {
		log.info("BolsasService.busquedaBolsaNull");
		
		Query query = new Query();

		if (idTipo != null) {
			Criteria aux = Criteria.where(ID_TIPO).is(idTipo);

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}

			if (ramo != null) {
				aux.and(RAMO).is(ramo);
			}

			if (subramo != null) {
				aux.and(SUBRAMO).is(subramo);
			}

			if (factor != null) {
				aux.and(FACTOR).is(factor);
			}
			query.addCriteria(aux);
		}
		
		if (nombre != null) {
			Criteria aux = Criteria.where(NOMBRE).is(nombre);

			if (idTipo != null) {
				aux.and(ID_TIPO).is(idTipo);
			}

			if (ramo != null) {
				aux.and(RAMO).is(ramo);
			}

			if (subramo != null) {
				aux.and(SUBRAMO).is(subramo);
			}

			if (factor != null) {
				aux.and(FACTOR).is(factor);
			}
			query.addCriteria(aux);
		}
		
		if (ramo != null) {
			Criteria aux = Criteria.where(RAMO).is(ramo);

			if (idTipo != null) {
				aux.and(ID_TIPO).is(idTipo);
			}

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}

			if (subramo != null) {
				aux.and(SUBRAMO).is(subramo);
			}

			if (factor != null) {
				aux.and(FACTOR).is(factor);
			}
			query.addCriteria(aux);
		}
		
		if (subramo != null) {
			Criteria aux = Criteria.where(SUBRAMO).is(subramo);

			if (idTipo != null) {
				aux.and(ID_TIPO).is(idTipo);
			}

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}

			if (ramo != null) {
				aux.and(RAMO).is(ramo);
			}

			if (factor != null) {
				aux.and(FACTOR).is(factor);
			}
			query.addCriteria(aux);
		}
		
		if (factor != null) {
			Criteria aux = Criteria.where(FACTOR).is(factor);

			if (idTipo != null) {
				aux.and(ID_TIPO).is(idTipo);
			}

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}

			if (ramo != null) {
				aux.and(RAMO).is(ramo);
			}

			if (subramo != null) {
				aux.and(SUBRAMO).is(subramo);
			}
			query.addCriteria(aux);
		}
		
		log.info("Query: " + query.toString());
		return query;
	}
	
}
