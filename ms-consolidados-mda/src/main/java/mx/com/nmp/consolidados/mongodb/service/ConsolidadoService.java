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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import mx.com.nmp.consolidados.oag.controller.OAGController;

@Service
public class ConsolidadoService {
	private static final Logger LOG = LoggerFactory.getLogger(ConsolidadoService.class);
	
	public static final String FECHA = "fechaAplicacion";
	private static final String SEQUENCE = "consolidado_sequence";
	public static final String ID_ARCHIVO = "idArchivo";
	public static final String PRIORIDAD = "prioridad";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService SequenceGeneratorService;
	
	@Autowired
	private OAGController oAGController;
	
	public Boolean crearConsolidado(Consolidados request) {
		LOG.info("ConsolidadoService.crearConsolidado");
		Boolean insertado = false;
		if(request != null) {
				CastConsolidados castConsolidados=new CastConsolidados();
				ArchivoEntity consolidado = new ArchivoEntity();
				try {
				consolidado.setVigencia(request.getVigencia());
				consolidado.setNombreAjuste(request.getNombreAjuste());
				consolidado.setEmergente(request.getEmergente());
				consolidado.setFechaAplicacion(request.getFechaAplicacion());
				consolidado.setIdArchivo(SequenceGeneratorService.generateSequence(SEQUENCE));
				File archivo=request.getAdjunto();
				FileReader targetReader = new FileReader(archivo);
	    		BufferedReader b=new BufferedReader(targetReader);
	    		List<InfoProducto> lst=castConsolidados.cvsLectura(b);
				consolidado.setAdjunto(castConsolidados.lstToJson(lst));
				consolidado.setNombreArchivo(archivo.getName());
				consolidado.setPrioridad(consultaArhivoConsolidadoByDate(request.getFechaAplicacion()).size()+1);
				LOG.info(castConsolidados.lstToJson(lst));
				} catch (FileNotFoundException e) {
				e.printStackTrace();
				}
				mongoTemplate.insert(consolidado);
				insertado =  true;
		}
		return insertado;
	}
	
	public ArrayList<ConsultarArchivoConsolidadoResInner> getConsolidados(String fechaAplicacion){
		LOG.info("entrando a ConsolidadoService.getConsolidados");
		ArrayList<ConsultarArchivoConsolidadoResInner> lstConsolidados=new ArrayList<>();
		Date fechaAplicaciondate = null;
	    try {
	    	fechaAplicaciondate=new SimpleDateFormat("dd/MM/yyyy").parse(fechaAplicacion);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    List<ArchivoEntity> busquedaList=consultaArhivoConsolidadoByDate(fechaAplicaciondate);
		if(busquedaList!=null) {
			CastConsolidados castConsolidados=new CastConsolidados();
			lstConsolidados=castConsolidados.toVOs(busquedaList);
		}
		LOG.info("saliendo a ConsolidadoService.getConsolidados");
		return lstConsolidados;
	}
	
	private List<ArchivoEntity> consultaArhivoConsolidadoByDate(Date fechaAplicacion){
	    Date fechaAplicacionInicioDia=CastConsolidados.resetTimeToDown(fechaAplicacion);
	    LOG.info("fecha inicio dia "+fechaAplicacionInicioDia);
	    Date fechaAplicacionFinDia=CastConsolidados.resetTimeToUp(fechaAplicacion);
	    LOG.info("fecha fin dia "+fechaAplicacionFinDia);
		Query q=new Query();
		q.addCriteria(Criteria.where("fechaAplicacion").gte(fechaAplicacionInicioDia).lt(fechaAplicacionFinDia));
		List<ArchivoEntity> busquedaList = mongoTemplate.find(q, ArchivoEntity.class);
		LOG.info("query size() "+busquedaList.size());
		return busquedaList;
	}
	
	private List<ArchivoEntity> getConsolidados(Integer idArchivo) {
		LOG.info("getConsolidadosPorIdArchivo");
		
		Query query = new Query();
		Criteria aux = Criteria.where(ID_ARCHIVO).is(idArchivo);
		query.addCriteria(aux);
		
		return mongoTemplate.find(query, ArchivoEntity.class);
	}
	
	private Boolean eliminarConsolidado(ArchivoEntity consolidado) {
		LOG.info("eliminarConsolidadoMongo");
		
		Boolean eliminado = false;
		
		if(consolidado != null) {
			mongoTemplate.remove(consolidado);
			eliminado = true;
		}
		
		return eliminado;
	}
	
	public Boolean eliminarConsolidado(String idArchivo) {
		LOG.info("eliminarConsolidado");
		
		Boolean eliminado = false;
		
		List<ArchivoEntity> consolidados = this.getConsolidados(new Integer(idArchivo));
		
		LOG.info("consolidados : {} " , consolidados.size());
		
		for(ArchivoEntity consolidado : consolidados) {
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
	
	public InlineResponse200 actualizarPrioridadArchivo(ModificarPrioridadArchivoConsolidadoReq request) {
		LOG.info("Actualizar Prioridad Archivo Consolidado");
		
		Boolean modificado = false;
		InlineResponse200 resp = null;
		
		
		Query query = new Query();
		query.addCriteria(Criteria.where(ID_ARCHIVO).is(request.getIdArchivo()));
		Update update = new Update();
		update.set(PRIORIDAD, request.getIdPrioridad());

		ArchivoEntity consolidado = mongoTemplate.findAndModify(query, update, ArchivoEntity.class);
		
		if(consolidado != null) {
			resp = new InlineResponse200();
			
			resp.setIdPosicion(consolidado.getPrioridad());
			resp.setNombreArchivo(consolidado.getNombreAjuste());
		}
		
		return resp;
	}
	
}
