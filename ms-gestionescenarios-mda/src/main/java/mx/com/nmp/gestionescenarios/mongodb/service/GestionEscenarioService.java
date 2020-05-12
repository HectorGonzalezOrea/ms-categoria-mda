package mx.com.nmp.gestionescenarios.mongodb.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import mx.com.nmp.gestionescenarios.mongodb.service.SequenceGeneratorService;
import mx.com.nmp.gestionescenarios.vo.GestionReglasVO;
import mx.com.nmp.gestionescenarios.cast.CastObjectGeneric;
import mx.com.nmp.gestionescenarios.model.EstatusRegla;
import mx.com.nmp.gestionescenarios.model.InfoGeneralRegla;
import mx.com.nmp.gestionescenarios.model.InfoRegla;
import mx.com.nmp.gestionescenarios.model.ListaInfoGeneralRegla;
import mx.com.nmp.gestionescenarios.model.ListaMonedas;
import mx.com.nmp.gestionescenarios.model.ListaMonedasInner;
import mx.com.nmp.gestionescenarios.model.Moneda;
import mx.com.nmp.gestionescenarios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.gestionescenarios.mongodb.entity.AnclaOroDolarEntity;
import mx.com.nmp.gestionescenarios.mongodb.entity.BolsasEntity;

import mx.com.nmp.gestionescenarios.mongodb.entity.GestionEscenarioEntity;
import mx.com.nmp.gestionescenarios.mongodb.entity.MonedasEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.ConsolidadoEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.EscenariosRepository;
import mx.com.nmp.gestionescenarios.mongodb.repository.OrigenRepository;

@Service
public class GestionEscenarioService {

	private static final Logger log = LoggerFactory.getLogger(GestionEscenarioService.class);
	
	private static final String GESTIONESCENARIO_SEQ_KEY = "gestionEscenario_sequence";
	private static final String MONEDAS_SEQ_KEY = "monedas_sequence";
	public static final String ID = "_id";

	public static final String NOMBRE = "nombre";
	public static final String RAMO = "ramo.id";
	public static final String SUBRAMO = "subramo.id";
	public static final String FACTOR = "factor.id";
	public static final String ORIGEN = "origen.id";
	public static final String CLASIF_CLIENTES = "clasificacionClientes.id";
	public static final String ESTATUS = "estatus.id";
	public static final String CANAL = "canalComercializacion.idCanal";
	public static final String FECHA_APLICACION = "fechaAplicacion.fechas";

	public static final String ID_ARCHIVO = "idArchivo";
	public static final String ID_ANCLA = "_id";
	public static final String ID_BOLSA_BOLSA = "_id";
	public static final String REQUEST_ID_CALENDARIZACION = "requestIdCalendarizacion";
	public static final String ID_MONEDA = "_id";

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private OrigenRepository origenRepository;
	@Autowired
	private EscenariosRepository escenariosRepository;

	/*
	 * Almacenar Regla POST /escenarios/reglas
	 */

	public Boolean almacenarRegla(InfoRegla peticion) {
		log.info("GestionEscenarioService.almacenarRegla");

		Boolean almacenado = false;
		GestionEscenarioEntity ges = new GestionEscenarioEntity();
		if (peticion != null) {
			ges.setNombre(peticion.getNombre());
			ges.setOrigen(peticion.getOrigen());
			ges.setRamo(peticion.getRamo());
			ges.setSubramo(peticion.getSubramo());
			ges.setFactor(peticion.getFactor());
			ges.setClasificacionClientes(peticion.getClasificacionClientes());
			ges.setBolsas(peticion.getBolsas());
			ges.setSucursales(peticion.getSucursales());
			ges.setCanalComercializacion(peticion.getCanalComercializacion());
			ges.setFechaAplicacion(peticion.getFechaAplicacion());
			ges.setEstatus(peticion.getEstatus());
			ges.setCompraCumplido(peticion.isCompraCumplido());
			ges.setAforo(peticion.getAforo());
			ges.setEstatusPartida(peticion.getEstatusPartida());
			ges.setMonedas(peticion.getMonedas());
			ges.setCanalIngresoActual(peticion.getCanalIngresoActual());
			ges.setDiasAlmoneda(peticion.getDiasAlmoneda());
			ges.setNivelAgrupacion(peticion.getNivelAgrupacion());
			ges.setReglasDescuento(peticion.getReglasDescuento());
			ges.setCandadoInferior(peticion.getCandadoInferior());

			Integer id = (int) sequenceGeneratorService.generateSequence(GESTIONESCENARIO_SEQ_KEY);
			ges.setIdRegla(id);

			try {
				
				mongoTemplate.insert(ges);
				almacenado = true;
			} catch (Exception e) {
				log.error("Exception : {}", e);
			}
		}
		return almacenado;
	}
	
	
	/*
	 * Validacion de regla
	 */
	
	public Boolean consultaRegla(String nombre) {
		log.info("consultaRegla");
		
		Boolean encontrado = false;
		if(nombre != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(NOMBRE).is(nombre);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, GestionEscenarioEntity.class);
		}
		
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}
	
	

	/*
	 * Consulta de Reglas GET /escenarios/reglas
	 */
	public List<InfoGeneralRegla> consultaRegla(String nombre, String ramo, String subramo, String factor,
			String origen, String clasificacionClientes, String estatusPartida, String canalComercializacion,
			String fechaAplicacion) {

		log.info("GestionEscenarioService.consultaRegla");

		Query query = this.busquedaReglaNull(nombre, ramo, subramo, factor, origen, clasificacionClientes,
				estatusPartida, canalComercializacion, fechaAplicacion);

		List<GestionEscenarioEntity> busquedaList = mongoTemplate.find(query, GestionEscenarioEntity.class);
		List<InfoGeneralRegla> reglas = null;
		if (CollectionUtils.isNotEmpty(busquedaList)) {
			reglas = new ArrayList<>();
			InfoGeneralRegla infoRegla =null;
			for (GestionEscenarioEntity aux : busquedaList) {
				infoRegla= new InfoGeneralRegla();
				infoRegla.setId(aux.getIdRegla());
				infoRegla.setNombre(aux.getNombre());
				infoRegla.setOrigen(aux.getOrigen());
				infoRegla.setRamo(aux.getRamo());
				infoRegla.setSubramo(aux.getSubramo());
				infoRegla.setBolsas(aux.getBolsas());
				infoRegla.setFactor(aux.getFactor());
				infoRegla.setCanalComercializacion(aux.getCanalComercializacion());
				infoRegla.setClasificacionClientes(aux.getClasificacionClientes());
				infoRegla.setEstatus(aux.getEstatus());
				infoRegla.setFechaAplicacion(aux.getFechaAplicacion());
				infoRegla.setSucursales(aux.getSucursales());
				reglas.add(infoRegla);
			}
			
		}

		return reglas;

	}

	/*
	 * Busqueda de reglas sin filtros
	 * 
	 */
	public List<InfoGeneralRegla> consultaReglaSinFiltro() {
		log.info("consultaReglaSinFiltro");

		List<GestionEscenarioEntity> busquedaList = mongoTemplate.findAll(GestionEscenarioEntity.class);
		log.info("query size(): {}", busquedaList.size());
		List<InfoGeneralRegla> reglas = null;

		if (CollectionUtils.isNotEmpty(busquedaList)) {
			reglas = new ArrayList<>();
			InfoGeneralRegla infoRegla = null;

			for (GestionEscenarioEntity aux : busquedaList) {

				infoRegla = new InfoGeneralRegla();
				
				infoRegla.setId(aux.getIdRegla());
				infoRegla.setNombre(aux.getNombre());
				infoRegla.setOrigen(aux.getOrigen());
				infoRegla.setRamo(aux.getRamo());
				infoRegla.setSubramo(aux.getSubramo());
				infoRegla.setBolsas(aux.getBolsas());
				infoRegla.setFactor(aux.getFactor());
				infoRegla.setCanalComercializacion(aux.getCanalComercializacion());
				infoRegla.setClasificacionClientes(aux.getClasificacionClientes());
				infoRegla.setEstatus(aux.getEstatus());
				infoRegla.setFechaAplicacion(aux.getFechaAplicacion());
				infoRegla.setSucursales(aux.getSucursales());
				reglas.add(infoRegla);
			}
		}
		return reglas;
	}

	/*
	 * Actualiza Regla
	 */
	public Boolean actualizaRegla(InfoRegla peticion) {
		log.info("GestionEscenarioService.actualizaRegla");

		Boolean actualizado = false;
		GestionEscenarioEntity ges = null;
		if (peticion != null) {
			try {
				ges = mongoTemplate.findOne(Query.query(Criteria.where(ID).is(peticion.getId())),
						GestionEscenarioEntity.class);
				log.info("Resultado: {}", ges);
			} catch (Exception e) {
				log.error("Exception : {}", e);
			}
			if (ges != null) {

				ges.setNombre(peticion.getNombre());
				ges.setOrigen(peticion.getOrigen());
				ges.setRamo(peticion.getRamo());
				ges.setSubramo(peticion.getSubramo());
				ges.setFactor(peticion.getFactor());
				ges.setClasificacionClientes(peticion.getClasificacionClientes());
				ges.setBolsas(peticion.getBolsas());
				ges.setSucursales(peticion.getSucursales());
				ges.setCanalComercializacion(peticion.getCanalComercializacion());
				ges.setFechaAplicacion(peticion.getFechaAplicacion());
				ges.setEstatus(peticion.getEstatus());
				ges.setCompraCumplido(peticion.isCompraCumplido());
				ges.setAforo(peticion.getAforo());
				ges.setEstatusPartida(peticion.getEstatusPartida());
				ges.setMonedas(peticion.getMonedas());
				ges.setCanalIngresoActual(peticion.getCanalIngresoActual());
				ges.setDiasAlmoneda(peticion.getDiasAlmoneda());
				ges.setNivelAgrupacion(peticion.getNivelAgrupacion());
				ges.setReglasDescuento(peticion.getReglasDescuento());
				ges.setCandadoInferior(peticion.getCandadoInferior());

				try {
					mongoTemplate.save(ges);
					actualizado = true;
				} catch (Exception e) {
					log.error("Exception : {}", e);
				}
			}
		}
		return actualizado;

	}

	/*
	 * Actualiza el Estatus
	 * 
	 */
	public Boolean actualizaEstatus(EstatusRegla peticion) {
		log.info("GestionEscenarioService.actualizaEstatus");

		Boolean actualizado = false;

		GestionEscenarioEntity gee = null;
		if (peticion != null) {
			try {
				gee = mongoTemplate.findOne(Query.query(Criteria.where(ID).is(peticion.getId())),
						GestionEscenarioEntity.class);
				log.info("Resultado: {}", gee);
			} catch (Exception e) {
				log.error("Exception : {}", e);
			}
			if (gee != null) {
				gee.setEstatus(peticion.getEstatus());

				try {
					mongoTemplate.save(gee);
					actualizado = true;
				} catch (Exception e) {
					log.error("Exception : {}", e);
				}

			}
		}

		return actualizado;

	}

	/*
	 * Consulta de regla por IdRegla
	 */
	public InfoRegla consultaReglaId(Integer idRegla) {

		log.info("GestionEscenarioService.consultaReglaId");
		InfoRegla ir = new InfoRegla();
		GestionEscenarioEntity ges = null;
		if (idRegla != null) {
			try {
				ges = mongoTemplate.findOne(Query.query(Criteria.where(ID).is(idRegla)), GestionEscenarioEntity.class);
				log.info("Resultado: {}", ges);
			} catch (Exception e) {
				log.error("Exception : {}", e);
			}
			if (ges == null) {
				log.info("El campo es nulo");
			} else {
				ir.setId(ges.getIdRegla());
				ir.setNombre(ges.getNombre());
				ir.setOrigen(ges.getOrigen());
				ir.setRamo(ges.getRamo());
				ir.setSubramo(ges.getSubramo());
				ir.setFactor(ges.getFactor());
				ir.setClasificacionClientes(ges.getClasificacionClientes());
				ir.setBolsas(ges.getBolsas());
				ir.setSucursales(ges.getSucursales());
				ir.setCanalComercializacion(ges.getCanalComercializacion());
				ir.setFechaAplicacion(ges.getFechaAplicacion());
				ir.setEstatus(ges.getEstatus());
				ir.setCompraCumplido(ges.getCompraCumplido());
				ir.setAforo(ges.getAforo());
				ir.setEstatusPartida(ges.getEstatusPartida());
				ir.setMonedas(ges.getMonedas());
				ir.setCanalIngresoActual(ges.getCanalIngresoActual());
				ir.setDiasAlmoneda(ges.getDiasAlmoneda());
				ir.setNivelAgrupacion(ges.getNivelAgrupacion());
				ir.setReglasDescuento(ges.getReglasDescuento());
				ir.setCandadoInferior(ges.getCandadoInferior());

			}

		}

		return ir;
	}

	/*
	 * Eliminar una regla
	 */
	public InfoRegla eliminaRegla(Integer idRegla) {
		log.info("GestionEscenarioService.eliminarRegla");
		Boolean eliminado = false;
		InfoRegla infoRegla = null;

		if (idRegla != null) {

			GestionEscenarioEntity escenario = (GestionEscenarioEntity) escenariosRepository.findByIdRegla(idRegla);
			if (escenario != null) {
				infoRegla = new InfoRegla();

				infoRegla.setId(escenario.getIdRegla());
				infoRegla.setNombre(escenario.getNombre());
				infoRegla.setAforo(escenario.getAforo());
				infoRegla.setBolsas(escenario.getBolsas());
				infoRegla.setCanalComercializacion(escenario.getCanalComercializacion());
				infoRegla.setCanalIngresoActual(escenario.getCanalIngresoActual());
				infoRegla.setCandadoInferior(escenario.getCandadoInferior());
				infoRegla.setClasificacionClientes(escenario.getClasificacionClientes());
				infoRegla.setCompraCumplido(escenario.getCompraCumplido());
				infoRegla.setDiasAlmoneda(escenario.getDiasAlmoneda());
				infoRegla.setEstatus(escenario.getEstatus());
				infoRegla.setEstatusPartida(escenario.getEstatusPartida());
				infoRegla.setFactor(escenario.getFactor());
				infoRegla.setFechaAplicacion(escenario.getFechaAplicacion());
				infoRegla.setMonedas(escenario.getMonedas());
				infoRegla.setNivelAgrupacion(escenario.getNivelAgrupacion());
				infoRegla.setOrigen(escenario.getOrigen());
				infoRegla.setRamo(escenario.getRamo());
				infoRegla.setReglasDescuento(escenario.getReglasDescuento());
				infoRegla.setSubramo(escenario.getSubramo());
				infoRegla.setSucursales(escenario.getSucursales());

				escenariosRepository.delete(escenario);
				eliminado = true;

			}
		}

		return infoRegla;

	}

	/*
	 * Armado de la busqueda
	 */
	private Query busquedaReglaNull(String nombre, String ramo, String subramo, String factor, String origen,
			String clasificacionClientes, String estatusPartida, String canalComercializacion, String fechaAplicacion) {
		log.info("GestionEscenarioService.busquedaReglaNull");

		Query query = new Query();
			//Criteria aux = Criteria.where(NOMBRE).is(nombre);
			if (nombre != null) {
				query.addCriteria(Criteria.where(NOMBRE).is(nombre));
			}
			if (ramo != null) {
				query.addCriteria(Criteria.where(RAMO).is(ramo));
			}
			if (subramo != null) {
				query.addCriteria(Criteria.where(SUBRAMO).is(subramo));
			}
			if (factor != null) {
				query.addCriteria(Criteria.where(FACTOR).is(factor));
			}
			if (origen != null) {
				query.addCriteria(Criteria.where(ORIGEN).is(origen));
			}
			if (clasificacionClientes != null) {
				query.addCriteria(Criteria.where(CLASIF_CLIENTES).is(clasificacionClientes));
			}
			if (estatusPartida != null) {
				query.addCriteria(Criteria.where(ESTATUS).is(estatusPartida));
			}
			if (canalComercializacion != null) {
				query.addCriteria(Criteria.where(CANAL).is(canalComercializacion));
			}
			if (fechaAplicacion != null) {
				query.addCriteria(Criteria.where(FECHA_APLICACION).is(fechaAplicacion));
			}
//		if (ramo != null) {
//			Criteria aux = Criteria.where(RAMO).is(ramo);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (subramo != null) {
//				aux.and(SUBRAMO).is(subramo);
//			}
//			if (factor != null) {
//				aux.and(FACTOR).is(factor);
//			}
//			if (origen != null) {
//				aux.and(ORIGEN).is(origen);
//			}
//			if (clasificacionClientes != null) {
//				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
//			}
//			if (estatusPartida != null) {
//				aux.and(ESTATUS).is(estatusPartida);
//			}
//			if (canalComercializacion != null) {
//				aux.and(CANAL).is(canalComercializacion);
//			}
//			if (fechaAplicacion != null) {
//				aux.and(FECHA_APLICACION).is(fechaAplicacion);
//			}
//			query.addCriteria(aux);
//		}
//
//		if (subramo != null) {
//			Criteria aux = Criteria.where(SUBRAMO).is(subramo);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (ramo != null) {
//				aux.and(RAMO).is(ramo);
//			}
//			if (factor != null) {
//				aux.and(FACTOR).is(factor);
//			}
//			if (origen != null) {
//				aux.and(ORIGEN).is(origen);
//			}
//			if (clasificacionClientes != null) {
//				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
//			}
//			if (estatusPartida != null) {
//				aux.and(ESTATUS).is(estatusPartida);
//			}
//			if (canalComercializacion != null) {
//				aux.and(CANAL).is(canalComercializacion);
//			}
//			if (fechaAplicacion != null) {
//				aux.and(FECHA_APLICACION).is(fechaAplicacion);
//			}
//			query.addCriteria(aux);
//		}
//
//		if (factor != null) {
//			Criteria aux = Criteria.where(FACTOR).is(factor);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (ramo != null) {
//				aux.and(RAMO).is(ramo);
//			}
//			if (subramo != null) {
//				aux.and(SUBRAMO).is(subramo);
//			}
//			if (origen != null) {
//				aux.and(ORIGEN).is(origen);
//			}
//			if (clasificacionClientes != null) {
//				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
//			}
//			if (estatusPartida != null) {
//				aux.and(ESTATUS).is(estatusPartida);
//			}
//			if (canalComercializacion != null) {
//				aux.and(CANAL).is(canalComercializacion);
//			}
//			if (fechaAplicacion != null) {
//				aux.and(FECHA_APLICACION).is(fechaAplicacion);
//			}
//			query.addCriteria(aux);
//		}
//
//		if (origen != null) {
//			Criteria aux = Criteria.where(ORIGEN).is(origen);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (ramo != null) {
//				aux.and(RAMO).is(ramo);
//			}
//			if (subramo != null) {
//				aux.and(SUBRAMO).is(subramo);
//			}
//			if (factor != null) {
//				aux.and(FACTOR).is(factor);
//			}
//			if (clasificacionClientes != null) {
//				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
//			}
//			if (estatusPartida != null) {
//				aux.and(ESTATUS).is(estatusPartida);
//			}
//			if (canalComercializacion != null) {
//				aux.and(CANAL).is(canalComercializacion);
//			}
//			if (fechaAplicacion != null) {
//				aux.and(FECHA_APLICACION).is(fechaAplicacion);
//			}
//			query.addCriteria(aux);
//		}
//
//		if (estatusPartida != null) {
//			Criteria aux = Criteria.where(ESTATUS).is(estatusPartida);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (ramo != null) {
//				aux.and(RAMO).is(ramo);
//			}
//			if (subramo != null) {
//				aux.and(SUBRAMO).is(subramo);
//			}
//			if (factor != null) {
//				aux.and(FACTOR).is(factor);
//			}
//			if (origen != null) {
//				aux.and(ORIGEN).is(origen);
//			}
//			if (clasificacionClientes != null) {
//				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
//			}
//			if (canalComercializacion != null) {
//				aux.and(CANAL).is(canalComercializacion);
//			}
//			if (fechaAplicacion != null) {
//				aux.and(FECHA_APLICACION).is(fechaAplicacion);
//			}
//			query.addCriteria(aux);
//		}
//
//		if (clasificacionClientes != null) {
//			Criteria aux = Criteria.where(CLASIF_CLIENTES).is(clasificacionClientes);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (ramo != null) {
//				aux.and(RAMO).is(ramo);
//			}
//			if (subramo != null) {
//				aux.and(SUBRAMO).is(subramo);
//			}
//			if (factor != null) {
//				aux.and(FACTOR).is(factor);
//			}
//			if (origen != null) {
//				aux.and(ORIGEN).is(origen);
//			}
//			if (estatusPartida != null) {
//				aux.and(ESTATUS).is(estatusPartida);
//			}
//			if (canalComercializacion != null) {
//				aux.and(CANAL).is(canalComercializacion);
//			}
//			if (fechaAplicacion != null) {
//				aux.and(FECHA_APLICACION).is(fechaAplicacion);
//			}
//			query.addCriteria(aux);
//		}
//
//		if (canalComercializacion != null) {
//			Criteria aux = Criteria.where(CANAL).is(canalComercializacion);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (ramo != null) {
//				aux.and(RAMO).is(ramo);
//			}
//			if (subramo != null) {
//				aux.and(SUBRAMO).is(subramo);
//			}
//			if (factor != null) {
//				aux.and(FACTOR).is(factor);
//			}
//			if (origen != null) {
//				aux.and(ORIGEN).is(origen);
//			}
//			if (estatusPartida != null) {
//				aux.and(ESTATUS).is(estatusPartida);
//			}
//			if (clasificacionClientes != null) {
//				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
//			}
//			if (fechaAplicacion != null) {
//				aux.and(FECHA_APLICACION).is(fechaAplicacion);
//			}
//			query.addCriteria(aux);
//		}
//
//		if (fechaAplicacion != null) {
//			Criteria aux = Criteria.where(FECHA_APLICACION).is(fechaAplicacion);
//
//			if (nombre != null) {
//				aux.and(NOMBRE).is(nombre);
//			}
//			if (ramo != null) {
//				aux.and(RAMO).is(ramo);
//			}
//			if (subramo != null) {
//				aux.and(SUBRAMO).is(subramo);
//			}
//			if (factor != null) {
//				aux.and(FACTOR).is(factor);
//			}
//			if (origen != null) {
//				aux.and(ORIGEN).is(origen);
//			}
//			if (estatusPartida != null) {
//				aux.and(ESTATUS).is(estatusPartida);
//			}
//			if (clasificacionClientes != null) {
//				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
//			}
//			if (canalComercializacion != null) {
//				aux.and(CANAL).is(canalComercializacion);
//			}
//			query.addCriteria(aux);
//		}

		log.info("Query: {}" , query.toString());
		return query;

	}

	/*
	 * Consulta idRegla
	 */
	public Boolean consultaIdRegla(Integer idRegla) {
		log.info("consultaIdRegla");

		Boolean encontrado = false;
		Query query = new Query();
		Criteria aux = Criteria.where(ID).is(idRegla);
		query.addCriteria(aux);
		encontrado = mongoTemplate.exists(query, GestionEscenarioEntity.class);
		log.info("Resultado: {}", encontrado);

		return encontrado;
	}

	/*
	 * Actualiza idRequest en Consolidado
	 */
	public void actualizarConsolidado(List<Integer> idConsolidado, Integer idRequest) {
		log.info("actualizarConsolidado");

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_ARCHIVO).in(idConsolidado));
			Update update = new Update();
			update.set(REQUEST_ID_CALENDARIZACION, idRequest.toString());

			mongoTemplate.updateMulti(query, update, ConsolidadoEntity.class);
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
	}
	
	/*
	 *  Registrar valores de monedas
	 */
	
	public Boolean registrarMonedas (ListaMonedasInner peticion) {
		
		Boolean insertado = null;
		MonedasEntity moneda =null;
		
		if (peticion!= null) {
			
			moneda = new MonedasEntity ();
			
			moneda.setTipo(peticion.getTipo());
			moneda.setOro(peticion.isOro());
			moneda.setPrecio(peticion.getPrecio());
			moneda.setFechaCreacion(peticion.getFechaCreacion());
			moneda.setActualizadoPor(peticion.getActualizadoPor());
			
			Long id = sequenceGeneratorService.generateSequence(MONEDAS_SEQ_KEY);
			moneda.setId(id);
			
			mongoTemplate.insert(moneda);
			insertado = true;

		}
		
		
		return insertado;
	}
	
	/*
	 * Buscar Bolsa
	 */
	public BolsasEntity buscarBolsa(Integer idBolsa) {
		log.info("buscarBolsa");
		
		BolsasEntity bolsa = null;
		
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_BOLSA_BOLSA).in(idBolsa));
			
			bolsa = mongoTemplate.findOne(query, BolsasEntity.class);
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
		
		return bolsa;
	}
	
	/*
	 * Buscar Moneda
	 */
	public MonedasEntity buscarMoneda(Long id) {
		log.info("buscarMoneda");
		
		MonedasEntity moneda = null;
		
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_MONEDA).in(id));
			
			moneda = mongoTemplate.findOne(query, MonedasEntity.class);
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
		
		return moneda;
	}
	
	/*
	 * Valida la información para insertar el ancla oro dolar
	 */
	public ObjectId solictarCambioAnclaOroDolar(ModificarValorAnclaOroDolar peticion) {
		log.info("solictarCambioAnclaOroDolar");
		
		if(peticion != null && peticion.getIdBolsa() != null) {
			BolsasEntity bolsa = this.buscarBolsa(peticion.getIdBolsa());
			if(bolsa != null) {
				return this.insertarAnclaOroDolar(peticion);
			} else {
				log.info("bolsa no existente");
			}
		}
		return null;
	}

	
	/*
	 * Valida la información para insertar la Moneda
	 */
	public Long solictarCambioMoneda(ListaMonedas peticion) {
		log.info("solictarCambioMoneda");
		
		if(peticion != null ) {
			for(ListaMonedasInner lmi:peticion) {
				MonedasEntity moneda = this.buscarMoneda(lmi.getId());
				if(moneda != null) {
					return this.updateMonedas(peticion);
				} else {
					log.info("Moneda no existente");
				}
			}
		}
		return null;
	}

	/*
	 * Insertar Ancla Oror Dolar
	 */
	public ObjectId insertarAnclaOroDolar(ModificarValorAnclaOroDolar peticion) {
		log.info("insertarAnclaOroDolar");
		
		AnclaOroDolarEntity anclaOroDolar = this.consultarAnclaOroDolar();
		if(anclaOroDolar == null) {
			anclaOroDolar = new AnclaOroDolarEntity();
		}
		
		ObjectId id = null;
				
		try {
			anclaOroDolar.setFechaAplicacion(new SimpleDateFormat("yyyy-MM-dd").parse(peticion.getFechaAplicacion().toString()));
		} catch (ParseException e) {
			log.error("ParseException: {}" , e);
		}
		
		List<Integer> intList = new ArrayList<>();
		if(peticion.getIdBolsa() != null) {
			anclaOroDolar.setIdBolsa(peticion.getIdBolsa());
			for(String s : peticion.getSucursales()) {
				intList.add(Integer.valueOf(s));
			}
		}

		anclaOroDolar.setSucursales(intList);
		anclaOroDolar.setValorAnclaDolar(peticion.getValorAnclaDolar());
		anclaOroDolar.setValorAnclaOro(peticion.getValorAnclaOro());
		
		try {
			mongoTemplate.save(anclaOroDolar);
			id = anclaOroDolar.get_id();
			
		} catch (Exception e) {
			log.error("Exception: {}" , e);
		}
		
		return id;
	}
	
	/*
	 * Consulta el request id para ancla oro dolar
	 */
	public AnclaOroDolarEntity consultarRequestIdAnclaOroDolar(ObjectId id) {
		log.info("consultarRequestIdAnclaOroDolar");
		
		log.info("id ancla: {}", id);
		
		AnclaOroDolarEntity anclaOroDolar = null;
		
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_ANCLA).in(id));
			
			anclaOroDolar = mongoTemplate.findOne(query, AnclaOroDolarEntity.class);
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
		
		return anclaOroDolar;
	}
	
	/*
	 * Consulta el request id para Moneda
	 */
	public MonedasEntity consultarRequestIdMoneda(Long id) {
		log.info("consultarRequestIdMoneda");
		
		log.info("id Moneda: {}", id);
		
		MonedasEntity moneda = null;
		
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_MONEDA).in(id));
			
			moneda = mongoTemplate.findOne(query, MonedasEntity.class);
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
		
		return moneda;
	}
	
	
	/*
	 * Actualiza el request id en la collection de anclaOroDolar
	 */
	public Boolean updateRequestIdAnclaOroDolar(ObjectId id, Integer requestId) {
		log.info("consultarRequestIdAnclaOroDolar");
		
		AnclaOroDolarEntity anclaOroDolar = null;
		Boolean procesado = false;
		
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_ANCLA).in(id));
			
			anclaOroDolar = mongoTemplate.findOne(query, AnclaOroDolarEntity.class);
			
			if(anclaOroDolar != null) {
				anclaOroDolar.setRequestId(requestId);
				mongoTemplate.save(anclaOroDolar);
				
				procesado = true;
			}
			
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
		
		return procesado;
	}
	
	/*
	 * Consultar Ancla Oro Dolar
	 */
	public AnclaOroDolarEntity consultarAnclaOroDolar() {
		log.info("consultarAnclaOroDolar");
		
		List<AnclaOroDolarEntity> anclas = mongoTemplate.findAll(AnclaOroDolarEntity.class);
		
		if(!anclas.isEmpty()) {
			return anclas.get(0);
		}
		
		return null;
	}
	
	/*
	 * Actualiza Monedas
	 */
	
	public Long updateMonedas (ListaMonedas peticion) {
		
		Long id = null;
		MonedasEntity moneda = null;
		
		if(peticion!= null) {
			moneda = new MonedasEntity();
			for(ListaMonedasInner lmi:peticion) {
				
				moneda = mongoTemplate.findOne(Query.query(Criteria.where(ID).is(lmi.getId())), MonedasEntity.class);
				
				if(moneda!=null) {
					
					moneda.setPrecio(lmi.getPrecio());
					moneda.setTipo(lmi.getTipo());
					moneda.setOro(lmi.isOro());
					LocalDate locateDate = LocalDate.now();
					moneda.setFechaModificacion(locateDate);
					moneda.setActualizadoPor(lmi.getActualizadoPor());
					
					
				}
			}
			try {
				mongoTemplate.save(moneda);
				id = moneda.getId();
			} catch(Exception e) {
				log.error("Exception : {}", e);
			}
			
				
		}
		
		
		return id;
		
	}
	
	
	/*
	 * Actualiza el request id en la collection de Monedas
	 */
	public Boolean updateRequestIdMonedas(Long id, Integer requestId) {
		log.info("consultarRequestIdMonedas");
		
		MonedasEntity moneda = null;
		Boolean procesado = false;
		
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(ID_MONEDA).in(id));
			
			moneda = mongoTemplate.findOne(query, MonedasEntity.class);
			
			if(moneda != null) {
				moneda.setRequestId(requestId);
				mongoTemplate.save(moneda);
				
				procesado = true;
			}
			
		} catch (Exception e) {
			log.info("Exception: {}", e);
		}
		
		return procesado;
	}
	
	public List<GestionReglasVO> obtenerReglasByFechas(java.time.LocalDate fecha){
		log.info("Consultando las reglas por fecha");
		CastObjectGeneric cast= new CastObjectGeneric();
		List<GestionEscenarioEntity>lstEscenarioEntity=escenariosRepository.lstEscenarios(cast.convertLocalDateToString(fecha));
		List<GestionReglasVO> gestionLst= new ArrayList<GestionReglasVO>();
		if(!lstEscenarioEntity.isEmpty()) {
			lstEscenarioEntity.stream().forEach(entity->{
				GestionReglasVO vo= new GestionReglasVO();
				 vo.setId(entity.getIdRegla());
				 vo.setNombre(entity.getNombre());
				 vo.setOrigen(entity.getOrigen());
				 vo.setRamo(cast.objectToPojoVO(entity.getRamo()));
				 vo.setSubramo(cast.convertObjectToPojo(entity.getSubramo()));
				 vo.setFactor(cast.convertObjectToPojo(entity.getFactor()));
				 vo.setClasificacionClientes(cast.convertObjectToPojo(entity.getClasificacionClientes()));
				 vo.setBolsas(entity.getBolsas());
				 vo.setSucursales(cast.convertObjectToPojo(entity.getSucursales()));
				 vo.setCanalComercializacion(cast.entityListToListCanalPojo(entity.getCanalComercializacion()));
				 vo.setFechaAplicacion(entity.getFechaAplicacion());
				 vo.setFechas(entity.getFechas());
				 vo.setEstatus(entity.getEstatus());
				 vo.setCompraCumplido(entity.getCompraCumplido());
				 vo.setAforo(entity.getAforo());
				 vo.setEstatusPartida(entity.getEstatusPartida());
				 vo.setMonedas(cast.convertObjectToPojo(entity.getMonedas()));
				 vo.setCanalIngresoActual(entity.getCanalIngresoActual());
				 vo.setDiasAlmoneda(entity.getDiasAlmoneda());
				 vo.setNivelAgrupacion(entity.getNivelAgrupacion());
				 vo.setReglasDescuento(entity.getReglasDescuento());
				 vo.setCandadoInferior(entity.getCandadoInferior());
				 gestionLst.add(vo);
				
			});
		}
		log.info("El tamaño de la lista es "+gestionLst.size());
		return gestionLst;
	}
	
	public Boolean validaFormatoFecha(String StringDate){
		log.info("validando Fecha");
		Boolean flag = false;
		try {
		 new SimpleDateFormat("dd/MM/yyyy").parse(StringDate);
		 return flag=true;
		} catch (ParseException e) {
			log.info("El formato de la fecha no es el esperado");
			return flag=false;
		}
	}
}
