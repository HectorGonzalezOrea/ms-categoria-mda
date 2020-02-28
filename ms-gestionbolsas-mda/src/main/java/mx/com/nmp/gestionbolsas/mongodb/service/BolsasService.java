package mx.com.nmp.gestionbolsas.mongodb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.com.nmp.gestionbolsas.model.Bolsa;
import mx.com.nmp.gestionbolsas.model.ListaBolsas;
import mx.com.nmp.gestionbolsas.model.ListaBolsasInner;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsas;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsasInner;
import mx.com.nmp.gestionbolsas.model.TipoBolsa;
import mx.com.nmp.gestionbolsas.mongodb.VO.TipoBolsaVO;
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
	public static final String ID = "_id";
	private static final String BOLSA_SEQ_KEY = "bolsas_sequence";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private BolsaRepository bolsaRepository;


	/*
	 * Crear Bolsa.
	 */
	public Boolean crearBolsa(Bolsa peticion) {
		
		
		Boolean insertado = false;
		
		if (peticion != null) {
			BolsasEntity bolsa = new BolsasEntity();
			bolsa.setTipo(peticion.getTipo().getId());
			bolsa.setNombre(peticion.getNombre());
			bolsa.setRamo(peticion.getRamo());
			bolsa.setSubramo(peticion.getSubramo());
			bolsa.setFactor(peticion.getFactor());
			bolsa.setSucursales(peticion.getSucursales());
			bolsa.setAutor(peticion.getAutor());

			Integer id = (int) sequenceGeneratorService.generateSequence(BOLSA_SEQ_KEY);
			bolsa.setidBolsa(id);

			try {
				mongoTemplate.insert(bolsa);
				insertado = true;
			} catch(Exception e) {
				log.error("Exception : {}", e);
			}
		}

		return insertado;
	}

	/*
	 * Consulta de bolsas
	 */
	
	public ListaBolsas getBolsas(String idTipo, String nombre, String ramo, String subramo, String factor) {
		log.info("BolsasService.getBolsas");
		 
		Query query = this.busquedaBolsaNull(idTipo, nombre, ramo, subramo, factor);
		log.info("resultado: {}", query );
		List<BolsasEntity> busquedaList = mongoTemplate.find(query, BolsasEntity.class);
		
		ListaBolsas lista = new ListaBolsas();
		ListaBolsasInner listaInner = null;
		if (!busquedaList.isEmpty()) {
			Bolsa bolsa = null;
			for(BolsasEntity aux : busquedaList) {
				bolsa = new Bolsa();
				listaInner = new ListaBolsasInner();
				
				bolsa.setId(aux.getidBolsa());
				bolsa.setNombre(aux.getNombre());
				bolsa.setRamo(aux.getRamo());
				bolsa.setSubramo(aux.getSubramo());
				bolsa.setFactor(aux.getFactor());
				bolsa.setSucursales(aux.getSucursales());
				bolsa.setAutor(aux.getAutor());
				
				listaInner.fechaCreacion(null);
				listaInner.fechaModificacion(null);
				listaInner.equals(bolsa);
				
				lista.add(listaInner);
				
				
			}
			
		}
		
		
		
		return lista;

	}
	
	

	// Eliminar una bolsa.
	public Boolean deleteBolsa(Integer idBolsa) {
		log.info("BolsasService.deleteBolsa");
		Boolean eliminado = false;

		if (idBolsa != null) {
			BolsasEntity bolsa = (BolsasEntity) bolsaRepository.findByIdBolsa(idBolsa);
			if (bolsa != null) {
				
				try {
					bolsaRepository.delete(bolsa);
					eliminado = true;
				} catch(Exception e) {
					log.error("Exception : {}", e);
				}
			}
		}

		return eliminado;

	}

	/*
	 * Actualiza bolsas
	 */
	public Boolean updateBolsa(Bolsa peticion) {
		log.info("BolsasService.updateBolsa");
		
		Boolean actualizado = false;
		BolsasEntity bolsa = null;
		
		if(peticion != null) {
			bolsa = mongoTemplate.findOne(Query.query(Criteria.where(ID).is(peticion.getId())), BolsasEntity.class);
			bolsa.setNombre(peticion.getNombre());
			bolsa.setRamo(peticion.getRamo());
			bolsa.setSubramo(peticion.getSubramo());
			bolsa.setFactor(peticion.getFactor());
			bolsa.setTipo(peticion.getTipo().getId());
			bolsa.setSucursales(peticion.getSucursales());
			bolsa.setAutor(peticion.getAutor());
			
			try {
				mongoTemplate.save(bolsa);
				actualizado = true;
			} catch(Exception e) {
				log.error("Exception : {}", e);
			}
		}
		
		return actualizado;
	}

	/*
	 * Consulta tipo de Bolsa.
	 */
	public ListaTipoBolsas consultaTipoBolsa() {
		log.info("BolsasService.consultaTipoBolsa");
		
		List<TipoBolsaEntity> listaTipoBolsa = mongoTemplate.findAll(TipoBolsaEntity.class);
		
		ListaTipoBolsas lista = new ListaTipoBolsas();
		ListaTipoBolsasInner listaInner = null;
		
		log.info("respuesta: {}", listaTipoBolsa);
		
		if (!listaTipoBolsa.isEmpty()) {
			TipoBolsa tb = null;
			for(TipoBolsaEntity tbEntity : listaTipoBolsa) {
				tb = new TipoBolsa();
				listaInner = new ListaTipoBolsasInner();
				
				tb.setId(tbEntity.getid());
				tb.setDescripcion(tbEntity.getDescripcion());
				
				listaInner.setTipo(tb);
				
				lista.add(listaInner);
			}
		}

		return lista;

	}

	/*
	 *  Armado de Busqueda de Bolsas
	 */
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

		log.info("Query: {}", query);
		return query;
	}
	
	
	

}
