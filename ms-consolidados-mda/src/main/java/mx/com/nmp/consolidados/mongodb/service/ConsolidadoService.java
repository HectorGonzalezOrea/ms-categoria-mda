package mx.com.nmp.consolidados.mongodb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import mx.com.nmp.consolidados.model.Consolidados;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.model.InlineResponse200;
import mx.com.nmp.consolidados.model.ModificarPrioridadArchivoConsolidadoReq;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.msestablecimientoprecios.controller.EstablecimientoPreciosController;
import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePrecio;
import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePreciosRequestVO;
import mx.com.nmp.consolidados.oag.controller.OAGController;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasRequestVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasResponseVO;
import mx.com.nmp.consolidados.oag.vo.EnviarNotificacionRequestVO;
import mx.com.nmp.consolidados.oag.vo.PartidaResponseVO;
import mx.com.nmp.consolidados.oag.vo.PartidaVO;

@Service
public class ConsolidadoService {
	private static final Logger log = LoggerFactory.getLogger(ConsolidadoService.class);

	public static final String FECHA = "fechaAplicacion";
	private static final String SEQUENCE = "consolidado_sequence";
	public static final String ID_ARCHIVO = "idArchivo";
	public static final String PRIORIDAD = "prioridad";
	public static final String CONSOLIDADOS = "consolidados";

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private OAGController oAGController;
	
	@Autowired
	private EstablecimientoPreciosController establecimientoPreciosController;

	public Boolean crearConsolidado(Consolidados request) {
		log.info("ConsolidadoService.crearConsolidado");
		Boolean insertado = false;
		if (request != null) {
			CastConsolidados castConsolidados = new CastConsolidados();
			ArchivoEntity consolidado = new ArchivoEntity();
			File archivo = null;
			FileReader targetReader = null;

			try {
				consolidado.setVigencia(request.getVigencia());
				consolidado.setNombreAjuste(request.getNombreAjuste());
				consolidado.setEmergente(request.getEmergente());
				consolidado.setFechaAplicacion(request.getFechaAplicacion());
				consolidado.setIdArchivo(sequenceGeneratorService.generateSequence(SEQUENCE));
				archivo = request.getAdjunto();
				targetReader = new FileReader(archivo);
				BufferedReader b = new BufferedReader(targetReader);
				List<InfoProducto> lst = castConsolidados.cvsLectura(b);
				consolidado.setAdjunto(castConsolidados.lstToJson(lst));
				consolidado.setNombreArchivo(archivo.getName());
				consolidado.setPrioridad(consultaArhivoConsolidadoByDate(request.getFechaAplicacion()).size() + 1);
				log.info(castConsolidados.lstToJson(lst));
			} catch (FileNotFoundException e) {
				log.info("FileNotFoundException : {} ", e);
			}
			mongoTemplate.insert(consolidado);
			insertado = true;
		}
		return insertado;
	}

	public ArrayList<ConsultarArchivoConsolidadoResInner> getConsolidados(String fechaAplicacion) {
		log.info("entrando a ConsolidadoService.getConsolidados");
		ArrayList<ConsultarArchivoConsolidadoResInner> lstConsolidados = new ArrayList<>();

		Date fechaAplicaciondate = null;
		try {
			fechaAplicaciondate = new SimpleDateFormat("dd/MM/yyyy").parse(fechaAplicacion);
		} catch (ParseException e) {
			log.info("ParseException : {}", e);
		}

		List<ArchivoEntity> busquedaList = this.consultaArhivoConsolidadoByDate(fechaAplicaciondate);

		if (!busquedaList.isEmpty()) {
			CastConsolidados castConsolidados = new CastConsolidados();
			lstConsolidados = castConsolidados.toVOs(busquedaList);
		}
		log.info("saliendo a ConsolidadoService.getConsolidados");
		return lstConsolidados;
	}

	/*
	 * Eliminar Consolidados por id de archivo
	 */
	public Boolean eliminarConsolidado(String idArchivo) {
		log.info("eliminarConsolidado");

		Boolean eliminado = false;

		List<ArchivoEntity> consolidados = this.getConsolidados(new Integer(idArchivo));

		log.info("consolidados : {} ", consolidados.size());

		for (ArchivoEntity consolidado : consolidados) {
			String requestId = consolidado.getRequestIdCalenzarizacion();
			if (requestId != null && !requestId.equals("")) {
				Boolean eliminadoESS = oAGController.eliminarCalendarizacion(requestId);
				if (Boolean.TRUE.equals(eliminadoESS)) {
					Boolean eliminadoMongo = this.eliminarConsolidado(consolidado);
					if (Boolean.TRUE.equals(eliminadoMongo)) {
						eliminado = true;
					}
				}
			}
		}
		return eliminado;
	}

	/*
	 * Actualiza la prioridad del archivo consolidados
	 */
	public InlineResponse200 actualizarPrioridadArchivo(ModificarPrioridadArchivoConsolidadoReq request) {
		log.info("Actualizar Prioridad Archivo Consolidado");

		InlineResponse200 resp = null;

		Query query = new Query();
		query.addCriteria(Criteria.where(ID_ARCHIVO).is(request.getIdArchivo()));
		Update update = new Update();
		update.set(PRIORIDAD, request.getIdPrioridad());

		ArchivoEntity consolidado = mongoTemplate.findAndModify(query, update, ArchivoEntity.class);

		if (consolidado != null) {
			resp = new InlineResponse200();

			resp.setIdPosicion(consolidado.getPrioridad());
			resp.setNombreArchivo(consolidado.getNombreAjuste());
		}

		return resp;
	}

	/*
	 * Procesar Consolidados
	 */
	public void procesarConsolidados(String usuario, String fechaAplicacion) {
		log.info("Procesar un archivo consolidado");
		
		// obtener consolidados
		ArrayList<ConsultarArchivoConsolidadoResInner> consolidados = this.getConsolidados(fechaAplicacion);
		
		ArrayList<ArbitrajePreciosPartidasRequestVO> listaVerificarRegistrosReq = new ArrayList<>();
		// se arma los request de validar arbitrariedad
		if(consolidados !=  null) {
			listaVerificarRegistrosReq = this.armarVerificarRegistros(consolidados);
		}
		
		ArrayList<ArbitrajePreciosPartidasResponseVO> listaVerificarRegistrosResp = new ArrayList<>();
		// se arma los request de validar arbitrariedad
		if(!listaVerificarRegistrosReq.isEmpty()) {
			listaVerificarRegistrosResp = this.verificarRegistros(listaVerificarRegistrosReq);
			
			// Establecimiento de precios
			this.enviarAjustePrecios(usuario, listaVerificarRegistrosReq);
		}
		
		if(!listaVerificarRegistrosResp.isEmpty()) {
			
		}
	}
	
	/*
	 * Servicio para armar la petición para verificar la arbitrariedad
	 */
	private ArrayList<ArbitrajePreciosPartidasRequestVO> armarVerificarRegistros(ArrayList<ConsultarArchivoConsolidadoResInner> consolidados) {
		log.info("armarVerificarRegistros"); 
		ArbitrajePreciosPartidasRequestVO appRequest = null;
		ArrayList<ArbitrajePreciosPartidasRequestVO> listAppRequest = new ArrayList<>();
		
		if (!consolidados.isEmpty()) {
			ArrayList<PartidaVO> partidas =  new ArrayList<>();
			PartidaVO p = null;
			
			for (ConsultarArchivoConsolidadoResInner consolidado : consolidados) {
				List<InfoProducto> productosConsolidado = consolidado.getProducto();
				appRequest = new ArbitrajePreciosPartidasRequestVO();
				
				for(InfoProducto ip : productosConsolidado) {
					p = new PartidaVO();
					p.setIdPartida(ip.getIdProducto().toString());
					p.setSku(ip.getFolioSku());
					p.setPrecioVenta(ip.getPrecioFinal());
					p.setMontoPrestamo(ip.getPrestamoCosto());
					partidas.add(p);
				}
				
				appRequest.setPartida(partidas);
				
				listAppRequest.add(appRequest);
			}
		}
		
		return listAppRequest;
	}

	/*
	 * Servicio para verificar la arbitrariedad
	 */
	private ArrayList<ArbitrajePreciosPartidasResponseVO> verificarRegistros(ArrayList<ArbitrajePreciosPartidasRequestVO> listaVerificarRegistros) {
		log.info("verificarRegistros"); 
		
		ArrayList<ArbitrajePreciosPartidasResponseVO> listaResponse = new ArrayList<>();
		if(!listaVerificarRegistros.isEmpty()) {
			ArbitrajePreciosPartidasResponseVO response = null;
			for(ArbitrajePreciosPartidasRequestVO request : listaVerificarRegistros) {
				try {
					response = oAGController.validarArbitrajePreciosPartidas(request);
					if(response != null) {
						listaResponse.add(response);
					}
				} catch (Exception e) {
					log.error("Exception {}" , e);
				}
			}
		}
		return listaResponse;
	}
	
	/*
	 * Servicio para enviar el ajuste de precios
	 */
	private void enviarAjustePrecios(String usuario, ArrayList<ArbitrajePreciosPartidasRequestVO> listaVerificarRegistrosReq) {
		
		if(!listaVerificarRegistrosReq.isEmpty()) {
			for(ArbitrajePreciosPartidasRequestVO req : listaVerificarRegistrosReq) {
				if(!req.getPartida().isEmpty()) {
					AjustePrecio ap = null;
					AjustePreciosRequestVO apRequest = new AjustePreciosRequestVO();
					for(PartidaVO p : req.getPartida()) {
						
						ap = new AjustePrecio();
						
						ap.setEnLinea(false);
						ap.setEscenario(CONSOLIDADOS);
						ap.setFolioPartida(p.getIdPartida());
						ap.setSku(p.getSku());
						ap.setPrecioActual(p.getPrecioVenta());
						ap.setPrecioModificado(p.getMontoPrestamo());
						
						apRequest.add(ap);
					}
					
					Boolean estatus = establecimientoPreciosController.ajustePrecios(usuario, apRequest);
					
					if(Boolean.FALSE.equals(estatus)) {
						EnviarNotificacionRequestVO r = new EnviarNotificacionRequestVO();
						
						oAGController.enviarNotificacion(r);
					}
				}
			}
		}
	}
	
	/*
	 * Consulta consolidados por fecha
	 */
	@SuppressWarnings("deprecation")
	private List<ArchivoEntity> consultaArhivoConsolidadoByDate(Date fechaAplicacion) {
		Date fechaAplicacionInicioDia = CastConsolidados.resetTimeToDown(fechaAplicacion);
		log.info("fecha inicio dia: {}", fechaAplicacionInicioDia);
		Date fechaAplicacionFinDia = CastConsolidados.resetTimeToUp(fechaAplicacion);
		log.info("fecha fin dia: {}", fechaAplicacionFinDia);
		Query q = new Query();
		q.addCriteria(Criteria.where(FECHA).gte(fechaAplicacionInicioDia).lt(fechaAplicacionFinDia));
		q.with(new Sort(new Order(Direction.ASC, PRIORIDAD)));
		
		List<ArchivoEntity> busquedaList = mongoTemplate.find(q, ArchivoEntity.class);
		log.info("query size(): {}", busquedaList.size());
		return busquedaList;
	}

	private List<ArchivoEntity> getConsolidados(Integer idArchivo) {
		log.info("getConsolidadosPorIdArchivo");

		Query query = new Query();
		Criteria aux = Criteria.where(ID_ARCHIVO).is(idArchivo);
		query.addCriteria(aux);

		return mongoTemplate.find(query, ArchivoEntity.class);
	}

	/*
	 * elimina consolidado en Mongo
	 */
	private Boolean eliminarConsolidado(ArchivoEntity consolidado) {
		log.info("eliminarConsolidadoMongo");

		Boolean eliminado = false;

		if (consolidado != null) {
			mongoTemplate.remove(consolidado);
			eliminado = true;
		}

		return eliminado;
	}
}
