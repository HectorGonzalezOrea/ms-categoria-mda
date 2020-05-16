package mx.com.nmp.gestionbolsas.mongodb.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.jsr310.LocalDateCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import mx.com.nmp.gestionbolsas.model.Bolsa;
import mx.com.nmp.gestionbolsas.model.ListaBolsas;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsas;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsasInner;
import mx.com.nmp.gestionbolsas.model.TipoBolsa;
import mx.com.nmp.gestionbolsas.mongodb.entity.BolsasEntity;
import mx.com.nmp.gestionbolsas.mongodb.entity.TipoBolsaEntity;
import mx.com.nmp.gestionbolsas.mongodb.repository.BolsaRepository;
import mx.com.nmp.gestionbolsas.utils.BolsaUtils;

@Service
public class BolsasService {
	private static final Logger log = LoggerFactory.getLogger(BolsasService.class);

	public static final String ID_TIPO = "tipo";
	public static final String TIPO = "tipo";
	public static final String NOMBRE = "nombre";
	public static final String RAMO = "ramo";
	public static final String SUBRAMO = "subramo";
	public static final String FACTOR = "factor";
	public static final String ID = "_id";
	private static final String BOLSA_SEQ_KEY = "bolsas_sequence";
	private static final String ID_TIPO_BOLSA = "_id";
	private static final String SUCURSALES="sucursales";
	
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
		log.info("BolsasService.crearBolsa");
		BolsaUtils utils=new BolsaUtils();
		Boolean insertado = false;
		
		if (peticion != null) {
			BolsasEntity bolsa = new BolsasEntity();
			bolsa.setTipo(peticion.getTipo().getId());
			bolsa.setNombre(peticion.getNombre());
			bolsa.setRamo(peticion.getRamo());
			bolsa.setSubramo(peticion.getSubramo());
			bolsa.setFactor(peticion.getFactor());
			bolsa.setSucursales(utils.paseraLista(peticion.getSucursales()));
			bolsa.setAutor(peticion.getAutor());

			LocalDate locateDate = LocalDate.now();
			
			bolsa.setFechaCreacion(locateDate);
			bolsa.setFechaModificacion(locateDate);
			
			Integer id = (int) sequenceGeneratorService.generateSequence(BOLSA_SEQ_KEY);
			bolsa.setIdBolsa(id);

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
	 * Validacion de tipoBolsa
	 */
	
	public Boolean consultaTipoBolsa(Integer idTipo) {
		log.info("consultaTipoBolsa");
		
		Boolean encontrado = false;
		if(idTipo!=null) {
			log.info("Tipo a buscar es {}", idTipo);
			Query query = new Query();
			Criteria aux = Criteria.where(ID_TIPO_BOLSA).is(idTipo);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, TipoBolsaEntity.class);
		}
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}
	
	/*
	 * Validacion de Nombre
	 */
	
	public Boolean consultaBolsa(String nombre) {
		log.info("consultaBolsa");
		
		Boolean encontrado = false;
		if(nombre != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(NOMBRE).is(nombre);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, BolsasEntity.class);
		}
		
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}
	/*
	 * Validar Bolsas
	 */
	//public Boolean validarBolsas(String ramo, String subramo, String factor, List<String> sucursales) {
	public Boolean validarExistenciaBolsas(List<String> sucursales) {
		Boolean existe = false;
		Query query = new Query();
		// Criteria aux = Criteria.where(RAMO).is(ramo).and(SUBRAMO).is(subramo).and(FACTOR).is(factor).and(SUCURSALES).in(sucursales);
		Criteria aux = Criteria.where(SUCURSALES).in(sucursales);
		query.addCriteria(aux);

		log.info("query {}", query);
		existe = mongoTemplate.exists(query, BolsasEntity.class);
		log.info("El valor es {}", existe);
		return existe;
	}
	
	

	/**
	 * Valida al actualizar la bolsa con la 
	 * sucursal
	 * */
	public Boolean validarActualizarBolsas(String ramo, String subramo, String factor, List<String> sucursales,Integer id) {
		Boolean existe = false;
		Query query = new Query();
		Criteria aux = Criteria.where(RAMO).is(ramo).and(SUBRAMO).is(subramo).and(FACTOR).is(factor).and(SUCURSALES).in(sucursales)
				.and(ID).nin(id);
		query.addCriteria(aux);
		existe=mongoTemplate.exists(query, BolsasEntity.class);
		return existe;
	}
	
	public Boolean validarNombreActualizarBolsa(Integer id, String nombre ) {
		Boolean existe = false;
		Query query = new Query();
		Criteria aux = Criteria.where(NOMBRE).is(nombre).and(ID).nin(id); 
		query.addCriteria(aux);
		existe=mongoTemplate.exists(query, BolsasEntity.class);
		return existe;
	}
	
	
	

	/*
	 * Consulta de bolsas con filtros
	 */
	public ListaBolsas getBolsas(String idTipo, String nombre, String ramo, String subramo, String factor) {
		log.info("BolsasService.getBolsas");
		 
		Query query = this.busquedaBolsaNull(idTipo, nombre, ramo, subramo, factor);
		log.info("resultado: {}", query );
		List<BolsasEntity> busquedaList = new ArrayList<>();
		try {
			busquedaList = mongoTemplate.find(query, BolsasEntity.class);
		} catch(Exception e) {
			log.error("Exception : {}", e);
		}
		
		ListaBolsas lista = new ListaBolsas();
		
		if (!busquedaList.isEmpty()) {
			Bolsa bolsa = null;
			for(BolsasEntity aux : busquedaList) {
				bolsa = new Bolsa();
				
				if(aux.getTipo() != null) {
					Query queryT = new Query();
					Criteria cr = Criteria.where(ID).is(aux.getTipo());
					queryT.addCriteria(cr);
					
					log.info("queryT: {}", queryT);
					
					TipoBolsaEntity tipoEntity = null;
					
					try {
						tipoEntity = mongoTemplate.findOne(queryT, TipoBolsaEntity.class);
					} catch(Exception e) {
						log.error("Exception : {}", e);
					}
					
					if(tipoEntity != null) {
						TipoBolsa tipoBolsaVO = new TipoBolsa();
						tipoBolsaVO.setId(tipoEntity.getid());
						tipoBolsaVO.setDescripcion(tipoEntity.getDescripcion());
						
						bolsa.setTipo(tipoBolsaVO);
					}
				}
				
				bolsa.setId(aux.getIdBolsa());
				bolsa.setNombre(aux.getNombre());
				bolsa.setRamo(aux.getRamo());
				bolsa.setSubramo(aux.getSubramo());
				bolsa.setFactor(aux.getFactor());
				bolsa.setSucursales(aux.getSucursales());
				bolsa.setAutor(aux.getAutor());				
				bolsa.setFechaCreacion(aux.getFechaCreacion());
				bolsa.setFechaModificacion(aux.getFechaModificacion());

				lista.add(bolsa);
			}	
		}
		return lista;

	}
	
	/*
	 * Consulta Bolsas sin filtros
	 */
	
	public ListaBolsas getBolsasSinFiltro() {
		ListaBolsas lista = new ListaBolsas();
		
		List<BolsasEntity> busquedaList = new ArrayList<>();
		try {
			busquedaList = mongoTemplate.findAll(BolsasEntity.class);
		} catch(Exception e) {
			log.error("Exception : {}", e);
		}
		
		if (!busquedaList.isEmpty()) {
			Bolsa bolsa = null;
			for(BolsasEntity aux : busquedaList) {
				bolsa = new Bolsa();
				
				if(aux.getTipo() != null) {
					Query queryT = new Query();
					Criteria cr = Criteria.where(ID).is(aux.getTipo());
					queryT.addCriteria(cr);
					
					log.info("queryT: {}", queryT);
					
					TipoBolsaEntity tipoEntity = null;
					
					try {
						tipoEntity = mongoTemplate.findOne(queryT, TipoBolsaEntity.class);
					} catch(Exception e) {
						log.error("Exception : {}", e);
					}
					
					if(tipoEntity != null) {
						TipoBolsa tipoBolsaVO = new TipoBolsa();
						tipoBolsaVO.setId(tipoEntity.getid());
						tipoBolsaVO.setDescripcion(tipoEntity.getDescripcion());
						
						bolsa.setTipo(tipoBolsaVO);
					}
				}
				
				bolsa.setId(aux.getIdBolsa());
				bolsa.setNombre(aux.getNombre());
				bolsa.setRamo(aux.getRamo());
				bolsa.setSubramo(aux.getSubramo());
				bolsa.setFactor(aux.getFactor());
				bolsa.setSucursales(aux.getSucursales());
				bolsa.setAutor(aux.getAutor());				
				bolsa.setFechaCreacion(aux.getFechaCreacion());
				bolsa.setFechaModificacion(aux.getFechaModificacion());

				lista.add(bolsa);
			}	
		}
		
		return lista;
	}
	
	/*
	 * Eliminar una bolsa.
	 */
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
		BolsaUtils utils= new BolsaUtils();
		
		if(peticion != null) {
			try {
				bolsa = mongoTemplate.findOne(Query.query(Criteria.where(ID).is(peticion.getId())), BolsasEntity.class);
			} catch(Exception e) {
				log.error("Exception : {}", e);
			}
			
			if(bolsa != null) {
				bolsa.setNombre(peticion.getNombre());
				bolsa.setRamo(peticion.getRamo());
				bolsa.setSubramo(peticion.getSubramo());
				bolsa.setFactor(peticion.getFactor());
				bolsa.setTipo(peticion.getTipo().getId());
				bolsa.setSucursales(utils.paseraLista(peticion.getSucursales()));
				bolsa.setAutor(peticion.getAutor());
				
				LocalDate locateDate = LocalDate.now();
				bolsa.setFechaModificacion(locateDate);
				
				try {
					mongoTemplate.save(bolsa);
					actualizado = true;
				} catch(Exception e) {
					log.error("Exception : {}", e);
				}
			}
		}
		
		return actualizado;
	}

	/*
	 * Consulta tipo de Bolsa.
	 */
	public ListaTipoBolsas consultaTipoBolsa() {
		log.info("BolsasService.consultaTipoBolsa");
		
		List<TipoBolsaEntity> listaTipoBolsa = new ArrayList<>();
		try {
			listaTipoBolsa = mongoTemplate.findAll(TipoBolsaEntity.class);
		} catch(Exception e) {
			log.error("Exception : {}", e);
		}
		
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
	 *  Validar idBolsa 
	 */
	
	public Boolean validarIdBolsa (Integer idBolsa) {
		
		Boolean encontrado = false;
		if(idBolsa!=null) {
			log.info("IdBolsa a buscar es {}", idBolsa);
			Query query = new Query();
			Criteria aux = Criteria.where(ID).is(idBolsa);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, BolsasEntity.class);
		}
		
		log.info("Encontrado: {}", encontrado);
		
		
		return encontrado;
		
	}

	
	
	/*
	 *  Armado de Busqueda de Bolsas
	 */
	private Query busquedaBolsaNull(String idTipo, String nombre, String ramo, String subramo, String factor) {
		log.info("BolsasService.busquedaBolsaNull");

		Query query = new Query();

		if (idTipo != null) {
			Criteria aux = Criteria.where(ID_TIPO).is(new Integer(idTipo));

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
				aux.and(ID_TIPO).is(new Integer(idTipo));
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
				aux.and(ID_TIPO).is(new Integer(idTipo));
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
				aux.and(ID_TIPO).is(new Integer(idTipo));
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
				aux.and(ID_TIPO).is(new Integer(idTipo));
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
	
	/*
	 * TipoBolsa sea Regla
	 */
	
	public Boolean consultaTipoBolsaRegla(Integer idTipo) {
		log.info("consultaTipoBolsa");
		
		TipoBolsaEntity bolsaEntity = new TipoBolsaEntity();
		Boolean encontrado = false;
		if(idTipo!=null) {
			log.info("Tipo a buscar es {}", idTipo);
			Query query = new Query();
			Criteria aux = Criteria.where(ID_TIPO_BOLSA).is(idTipo);
			query.addCriteria(aux);
			
			bolsaEntity = mongoTemplate.findById(idTipo, TipoBolsaEntity.class);
			if(bolsaEntity.getDescripcion().equals("Reglas")) {
				encontrado =true;
			}
		}
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}


}
