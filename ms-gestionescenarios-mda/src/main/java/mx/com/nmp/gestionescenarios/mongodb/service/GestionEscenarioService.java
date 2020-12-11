package mx.com.nmp.gestionescenarios.mongodb.service;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.nmp.gestionescenarios.cast.CastObjectGeneric;
import mx.com.nmp.gestionescenarios.model.Catalogo;
import mx.com.nmp.gestionescenarios.model.EstatusRegla;
import mx.com.nmp.gestionescenarios.model.InfoGeneralRegla;
import mx.com.nmp.gestionescenarios.model.InfoRegla;
import mx.com.nmp.gestionescenarios.model.ListaMonedas;
import mx.com.nmp.gestionescenarios.model.ListaMonedasInner;
import mx.com.nmp.gestionescenarios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.gestionescenarios.mongodb.entity.AnclaOroDolarEntity;
import mx.com.nmp.gestionescenarios.mongodb.entity.BolsasEntity;
import mx.com.nmp.gestionescenarios.mongodb.entity.GestionEscenarioEntity;
import mx.com.nmp.gestionescenarios.mongodb.entity.MonedasEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.ConsolidadoEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.EscenariosRepository;
import mx.com.nmp.gestionescenarios.oag.vo.ConsultaReglaVO;
import mx.com.nmp.gestionescenarios.utils.Constantes;
import mx.com.nmp.gestionescenarios.vo.CommonVO;
import mx.com.nmp.gestionescenarios.vo.GestionReglasVO;

@Service
public class GestionEscenarioService {

	private static final Logger log = LoggerFactory.getLogger(GestionEscenarioService.class);
	


	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private EscenariosRepository escenariosRepository;
	@Autowired
	private CastObjectGeneric castObjectGeneric;

	/*
	 * Almacenar Regla POST /escenarios/reglas
	 */

	public Integer almacenarRegla(InfoRegla peticion) {
		log.info("GestionEscenarioService.almacenarRegla");
		Catalogo subramoObj=null;
		Catalogo ramoObj=null;
		Boolean almacenado = false;
		ObjectMapper mapper = new ObjectMapper();
		GestionEscenarioEntity ges = new GestionEscenarioEntity();
		Integer id = (int) sequenceGeneratorService.generateSequence(Constantes.GESTIONESCENARIO_SEQ_KEY);
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
			try {
				List<Object> categoria=new ArrayList<>();
				categoria.add(Constantes.NA);
				ges.setCategoria(categoria);
				if(peticion.getRamo()!=null){
					String ramoAux = mapper.writeValueAsString(peticion.getRamo());
					ramoObj=mapper.readValue(ramoAux, Catalogo.class);
				}
				if(peticion.getSubramo()!=null){
					String subramo=mapper.writeValueAsString(peticion.getSubramo().get(0));
					subramoObj=mapper.readValue(subramo, Catalogo.class);
				}
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}
			if(ramoObj!=null&&ramoObj.getDescripcion().equals(Constantes.ALAJA)&&
					subramoObj!=null&&subramoObj.getDescripcion().equals(Constantes.ALAJA)){
				ges.setCategoria(peticion.getCategoria());
			}else{
				List<Object> categotia=new ArrayList<>();
				categotia.add(Constantes.NA);
				ges.setCategoria(categotia);
			}
			ges.setIdRegla(id);
			try {
				mongoTemplate.insert(ges);
				almacenado = true;
			} catch (Exception e) {
				log.error("Error almacenarRegla : {0}", e);
			}
		}
		return id;
	}
	
	
	/*
	 * Validacion de regla
	 */
	
	public Boolean consultaRegla(String nombre) {
		log.info("consultaRegla");
		
		Boolean encontrado = false;
		if(nombre != null) {
			Query query = new Query();
			Criteria aux = Criteria.where(Constantes.NOMBRE).is(nombre);
			query.addCriteria(aux);
			
			encontrado = mongoTemplate.exists(query, GestionEscenarioEntity.class);
		}
		
		log.info("Encontrado: {}", encontrado);
		
		return encontrado;
	}
	
	

	/*
	 * Consulta de Reglas GET /escenarios/reglas
	 */
	public List<InfoGeneralRegla> consultaRegla(ConsultaReglaVO consultaRegla) {

		log.info("GestionEscenarioService.consultaRegla");

		Query query = this.busquedaReglaNull(consultaRegla);

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
				infoRegla.setTipoMonedas(aux.getMonedas());
				infoRegla.setCategoria(aux.getCategoria());
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
				infoRegla.setCategoria(aux.getCategoria());
				reglas.add(infoRegla);
			}
		}
		return reglas;
	}

	/*Consulta regla por ID*/
	public GestionEscenarioEntity consultarReglaEscenario(Integer id){
		GestionEscenarioEntity ges = null;
		try {
			ges = mongoTemplate.findOne(Query.query(Criteria.where(Constantes.ID).is(id)),
					GestionEscenarioEntity.class);
			log.info("Resultado {}", ges);
		} catch (Exception e) {
			log.error("Error al obtener la regla: {0}", e);
		}
		return ges;
	}
	
	/*
	 * Actualiza Regla
	 */
	public Boolean actualizaRegla(InfoRegla peticion) {
		log.info("GestionEscenarioService.actualizaRegla");
		ObjectMapper mapper=new ObjectMapper();
		Boolean actualizado = false;
		GestionEscenarioEntity ges = null;
		Catalogo ramoObj=new Catalogo(); 
		Catalogo subramoObj=new Catalogo();
			ges=consultarReglaEscenario(peticion.getId());
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
					List<Object> categoria=new ArrayList<>();
					categoria.add(Constantes.NA);
					ges.setCategoria(categoria);
					if(peticion.getRamo()!=null){
						String ramoAux = mapper.writeValueAsString(peticion.getRamo());
						ramoObj=mapper.readValue(ramoAux, Catalogo.class);
					}
					if(peticion.getSubramo()!=null){
						String subramo=mapper.writeValueAsString(peticion.getSubramo().get(0));
						subramoObj=mapper.readValue(subramo, Catalogo.class);
					}
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}catch (IOException e){
					e.printStackTrace();
				}
				if(ramoObj!=null&&ramoObj.getDescripcion().equals(Constantes.ALAJA)&&
						subramoObj!=null&&subramoObj.getDescripcion().equals(Constantes.ALAJA)){
					ges.setCategoria(peticion.getCategoria());
				}else{
					List<Object> categotia=new ArrayList<>();
					categotia.add(Constantes.NA);
					ges.setCategoria(categotia);
				}
				try {
					mongoTemplate.save(ges);
					actualizado = true;
				} catch (Exception e) {
					log.error("Error actualizaRegla: {0}", e);
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
			InfoRegla id=new InfoRegla();
			id.setId(peticion.getId());
			gee=consultarReglaEscenario(peticion.getId());
			if (gee != null) {
				gee.setEstatus(peticion.getEstatus());
				try {
					mongoTemplate.save(gee);
					actualizado = true;
				} catch (Exception e) {
					log.error("Error al actualizar estatus: {0}", e);
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
				ges = mongoTemplate.findOne(Query.query(Criteria.where(Constantes.ID).is(idRegla)), GestionEscenarioEntity.class);
				log.info("Resultado: {}", ges);
			} catch (Exception e) {
				log.error("Error consultaReglaId: {0}", e);
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
				ir.setCategoria(ges.getCategoria());
			}

		}

		return ir;
	}

	/*
	 * Eliminar una regla
	 */
	public InfoRegla eliminaRegla(Integer idRegla) {
		log.info("GestionEscenarioService.eliminarRegla");
		
		InfoRegla infoRegla = null;
		if (idRegla != null) {
			GestionEscenarioEntity escenario = escenariosRepository.findByIdRegla(idRegla);
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
			}
		}
		return infoRegla;
	}

	/*
	 * Armado de la busqueda
	 */
	private Query busquedaReglaNull(ConsultaReglaVO consultaRegla) {
		log.info("GestionEscenarioService.busquedaReglaNull");

		Query query = new Query();
			if (consultaRegla.getNombre() != null) {
				query.addCriteria(Criteria.where(Constantes.NOMBRE).is(consultaRegla.getNombre()));
			}
			if (consultaRegla.getRamo() != null) {
				query.addCriteria(Criteria.where(Constantes.RAMO).is(consultaRegla.getRamo()));
			}
			if (consultaRegla.getSubramo() != null) {
				query.addCriteria(Criteria.where(Constantes.SUBRAMO).is(consultaRegla.getSubramo()));
			}
			if (consultaRegla.getFactor() != null) {
				query.addCriteria(Criteria.where(Constantes.FACTOR).is(consultaRegla.getFactor()));
			}
			if(consultaRegla.getCategoria()!=null){
				query.addCriteria(Criteria.where(Constantes.CATEGORIA).is(consultaRegla.getCategoria()));
			}
			if (consultaRegla.getOrigen() != null) {
				query.addCriteria(Criteria.where(Constantes.ORIGEN).is(consultaRegla.getOrigen()));
			}
			if (consultaRegla.getClasificacionClientes() != null) {
				query.addCriteria(Criteria.where(Constantes.CLASIF_CLIENTES).is(consultaRegla.getClasificacionClientes()));
			}
			if (consultaRegla.getEstatusPartida() != null) {
				query.addCriteria(Criteria.where(Constantes.ESTATUS).is(consultaRegla.getEstatusPartida()));
			}
			if (consultaRegla.getCanalComercializacion() != null) {
				query.addCriteria(Criteria.where(Constantes.CANAL).is(consultaRegla.getCanalComercializacion()));
			}
			if (consultaRegla.getFechaAplicacion() != null) {
				query.addCriteria(Criteria.where(Constantes.FECHA_APLICACION_FECHAS).is(consultaRegla.getFechaAplicacion()));
			}


		log.info("Query: {}" , query);
		return query;

	}

	/*
	 * Consulta idRegla
	 */
	public Boolean consultaIdRegla(Integer idRegla) {
		log.info("consultaIdRegla");

		Boolean encontrado = false;
		Query query = new Query();
		Criteria aux = Criteria.where(Constantes.ID).is(idRegla);
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
			query.addCriteria(Criteria.where(Constantes.ID_ARCHIVO).in(idConsolidado));
			Update update = new Update();
			update.set(Constantes.REQUEST_ID_CALENDARIZACION, idRequest.toString());

			mongoTemplate.updateMulti(query, update, ConsolidadoEntity.class);
		} catch (Exception e) {
			log.info("Error actualizarConsolidado: {0}", e);
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
			
			Long id = sequenceGeneratorService.generateSequence(Constantes.MONEDAS_SEQ_KEY);
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
			query.addCriteria(Criteria.where(Constantes.ID_BOLSA_BOLSA).in(idBolsa));
			
			bolsa = mongoTemplate.findOne(query, BolsasEntity.class);
		} catch (Exception e) {
			log.info("Error buscarBolsa: {0}", e);
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
			query.addCriteria(Criteria.where(Constantes.ID_MONEDA).in(id));
			
			moneda = mongoTemplate.findOne(query, MonedasEntity.class);
		} catch (Exception e) {
			log.info("Eror buscarMoneda: {0}", e);
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
			log.error("ParseException: {0}" , e);
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
			log.error("Error insertarAnclaOroDolar: {0}" , e);
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
			query.addCriteria(Criteria.where(Constantes.ID_ANCLA).in(id));
			
			anclaOroDolar = mongoTemplate.findOne(query, AnclaOroDolarEntity.class);
		} catch (Exception e) {
			log.info("Exception: {0}", e);
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
			query.addCriteria(Criteria.where(Constantes.ID_MONEDA).in(id));
			
			moneda = mongoTemplate.findOne(query, MonedasEntity.class);
		} catch (Exception e) {
			log.info("Exception: {0}", e);
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
			query.addCriteria(Criteria.where(Constantes.ID_ANCLA).in(id));
			
			anclaOroDolar = mongoTemplate.findOne(query, AnclaOroDolarEntity.class);
			
			if(anclaOroDolar != null) {
				anclaOroDolar.setRequestId(requestId);
				mongoTemplate.save(anclaOroDolar);
				
				procesado = true;
			}
			
		} catch (Exception e) {
			log.info("Error updateRequestIdAnclaOroDolar: {0}", e);
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
				
				moneda = mongoTemplate.findOne(Query.query(Criteria.where(Constantes.ID).is(lmi.getId())), MonedasEntity.class);
				
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
				if(moneda !=null) {
					mongoTemplate.save(moneda);
					id = moneda.getId();
				}
			} catch(Exception e) {
				log.error("Error updateMonedas : {0}", e);
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
			query.addCriteria(Criteria.where(Constantes.ID_MONEDA).in(id));
			
			moneda = mongoTemplate.findOne(query, MonedasEntity.class);
			
			if(moneda != null) {
				moneda.setRequestId(requestId);
				mongoTemplate.save(moneda);
				
				procesado = true;
			}
			
		} catch (Exception e) {
			log.info("Error updateRequestIdMonedas: {0}", e);
		}
		
		return procesado;
	}
	
	public List<GestionReglasVO> obtenerReglasByFechas(java.time.LocalDate fecha){
		log.info("Consultando las reglas por fecha");
		CastObjectGeneric cast= new CastObjectGeneric();
		List<GestionEscenarioEntity>lstEscenarioEntity=escenariosRepository.lstEscenarios(cast.convertLocalDateToString(fecha));
		List<GestionReglasVO> gestionLst= new ArrayList<>();
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
		log.info("El tamaño de la lista es {} ", gestionLst.size());
		return gestionLst;
	}
	
	public Boolean validaFormatoFecha(String stringDate){
		log.info("validando Fecha");
		Boolean flag = false;
		try {
		 new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
		 flag=true;
		} catch (ParseException e) {
			log.info("El formato de la fecha no es el esperado");
		}
		
		return flag;
	}
	
	public Boolean estaVacio(InfoRegla regla){
    	Boolean flag=false;
    	if(regla.getId()==null) {flag=true;}
    	if(regla.getOrigen()==null)flag=true;
    	if(regla.getBolsas()==null)flag=true;
    	if(regla.getEstatus()==null)flag=true;
    	if(regla.getEstatusPartida()==null)flag=true;
    	if(regla.getCanalIngresoActual()==null)flag=true;
    	if(regla.getNivelAgrupacion()==null)flag=true;
    	if(regla.getNombre()==null)flag=true;
    	if(regla.getRamo()==null)flag=true;
    	if(regla.getSucursales()==null)flag=true;
    	if(regla.getCanalComercializacion()==null)flag=true;
    	if(regla.getFechaAplicacion()==null)flag= true;
    	Boolean banderaSubramo=validarSubramoNull(regla);
        if(Boolean.TRUE.equals(banderaSubramo)) {
        	flag=true;
        }
        Boolean banderaFactor=validarFactornull(regla);
        if(Boolean.TRUE.equals(banderaFactor)) {
        	flag=true;
        }
        Boolean banderaClientes=validarClientes(regla);
        if(Boolean.TRUE.equals(banderaClientes)) {
        	flag=true;
        }

    	return flag;
    }
	
	private Boolean validarSubramoNull(InfoRegla regla) {
		Boolean flag=false;
		if(regla.isCompraCumplido()==null) {flag=true;}
    	if(regla.getAforo()==null) {flag=true;}
    	if(regla.getDiasAlmoneda()==null) {flag=true;}
    	if(regla.getReglasDescuento()==null) {flag=true;}
    	if(regla.getCandadoInferior()==null) {flag=true;}
    	if(regla.getSubramo()!=null){
    		List<CommonVO> lst =castObjectGeneric.convertObjectToPojo(regla.getSubramo());
    		for (CommonVO commonVO : lst) {
    			if(commonVO.getDescripcion()==null||commonVO.getId()==null)flag=true;
			}
    	}
		return flag;
	}
	
	private Boolean validarFactornull(InfoRegla regla) {
		Boolean flag=false;
    	if(regla.getFactor()!=null){
    		List<CommonVO> factor=castObjectGeneric.convertObjectToPojo(regla.getFactor());
    		for (CommonVO fac : factor) {
    			if(fac.getDescripcion()==null||fac.getId()==null)flag=true;
			}
    	}
    	return flag;
	}
	
	private Boolean validarClientes(InfoRegla regla) {
		Boolean flag=false;
    	if(regla.getClasificacionClientes()!=null){
    		List<CommonVO> cl=castObjectGeneric.convertObjectToPojo(regla.getClasificacionClientes());
    		for (CommonVO cliente : cl) {
    			if(cliente.getDescripcion()==null||cliente.getId()==null)flag=true;
			}
    	}
    	return flag;
	}
	
	public void actualizaRequestIdRegla(Integer idEscenario, Integer idRequestIdRegla) {
		log.info("actualizarConsolidado idEscenario [{}] IdCalendarizacion[{}]",idEscenario,idRequestIdRegla);
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(Constantes.ID_REGLA).is(idEscenario));
			Update update = new Update();
			update.set(Constantes.REQUEST_ID_CALENDARIZACION_REGLA, idRequestIdRegla);
			mongoTemplate.upsert(query, update, GestionEscenarioEntity.class);
		} catch (Exception e) {
			log.info("Error actualizar idRequest Regla: {0}", e);
		}
	}
}
