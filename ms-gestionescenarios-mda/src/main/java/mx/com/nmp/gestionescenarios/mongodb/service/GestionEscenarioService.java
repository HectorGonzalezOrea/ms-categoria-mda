package mx.com.nmp.gestionescenarios.mongodb.service;

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
import mx.com.nmp.gestionescenarios.mongodb.service.SequenceGeneratorService;
import mx.com.nmp.gestionescenarios.model.EstatusRegla;
import mx.com.nmp.gestionescenarios.model.InfoGeneralRegla;
import mx.com.nmp.gestionescenarios.model.InfoRegla;
import mx.com.nmp.gestionescenarios.model.ListaInfoGeneralRegla;
import mx.com.nmp.gestionescenarios.mongodb.entity.GestionEscenarioEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.ConsolidadoEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.EscenariosRepository;
import mx.com.nmp.gestionescenarios.mongodb.repository.OrigenRepository;

@Service
public class GestionEscenarioService {

	private static final Logger log = LoggerFactory.getLogger(GestionEscenarioService.class);
	
	private static final String GESTIONESCENARIO_SEQ_KEY = "gestionEscenario_sequence";
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
	public static final String REQUEST_ID_CALENDARIZACION = "requestIdCalendarizacion";

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
			InfoGeneralRegla infoRegla = new InfoGeneralRegla();

			for (GestionEscenarioEntity aux : busquedaList) {
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
			}
			reglas.add(infoRegla);
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
				ir.setClasificacionClientes(ges.getClasificacionClientes());
				ir.setBolsas(ges.getBolsas());
				ir.setSucursales(ges.getSucursales());
				ir.setCanalComercializacion(ges.getCanalComercializacion());
				ir.setFechaAplicacion(ges.getFechaAplicacion());

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

		if (nombre != null) {
			Criteria aux = Criteria.where(NOMBRE).is(nombre);

			if (ramo != null) {
				aux.and(RAMO).is(ramo);
			}
			if (subramo != null) {
				aux.and(SUBRAMO).is(subramo);
			}
			if (factor != null) {
				aux.and(FACTOR).is(factor);
			}
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (ramo != null) {
			Criteria aux = Criteria.where(RAMO).is(ramo);

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}
			if (subramo != null) {
				aux.and(SUBRAMO).is(subramo);
			}
			if (factor != null) {
				aux.and(FACTOR).is(factor);
			}
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (subramo != null) {
			Criteria aux = Criteria.where(SUBRAMO).is(subramo);

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}
			if (ramo != null) {
				aux.and(RAMO).is(ramo);
			}
			if (factor != null) {
				aux.and(FACTOR).is(factor);
			}
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (factor != null) {
			Criteria aux = Criteria.where(FACTOR).is(factor);

			if (nombre != null) {
				aux.and(NOMBRE).is(nombre);
			}
			if (ramo != null) {
				aux.and(RAMO).is(ramo);
			}
			if (subramo != null) {
				aux.and(SUBRAMO).is(subramo);
			}
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (origen != null) {
			Criteria aux = Criteria.where(ORIGEN).is(origen);

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
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (estatusPartida != null) {
			Criteria aux = Criteria.where(ESTATUS).is(estatusPartida);

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
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (clasificacionClientes != null) {
			Criteria aux = Criteria.where(CLASIF_CLIENTES).is(clasificacionClientes);

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
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (canalComercializacion != null) {
			Criteria aux = Criteria.where(CANAL).is(canalComercializacion);

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
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (fechaAplicacion != null) {
				aux.and(FECHA_APLICACION).is(fechaAplicacion);
			}
			query.addCriteria(aux);
		}

		if (fechaAplicacion != null) {
			Criteria aux = Criteria.where(FECHA_APLICACION).is(fechaAplicacion);

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
			if (origen != null) {
				aux.and(ORIGEN).is(origen);
			}
			if (estatusPartida != null) {
				aux.and(ESTATUS).is(estatusPartida);
			}
			if (clasificacionClientes != null) {
				aux.and(CLASIF_CLIENTES).is(clasificacionClientes);
			}
			if (canalComercializacion != null) {
				aux.and(CANAL).is(canalComercializacion);
			}
			query.addCriteria(aux);
		}

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

}
