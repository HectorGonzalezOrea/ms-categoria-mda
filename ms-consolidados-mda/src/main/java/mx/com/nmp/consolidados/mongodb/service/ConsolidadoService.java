package mx.com.nmp.consolidados.mongodb.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.com.nmp.consolidados.model.Consolidados;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
@Service
public class ConsolidadoService {
	private static final Logger LOG = LoggerFactory.getLogger(ConsolidadoService.class);
	
	public static final String FECHA = "fechaAplicacion";
	private static final String SEQUENCE = "consolidado_sequence";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService SequenceGeneratorService;
	
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
}
