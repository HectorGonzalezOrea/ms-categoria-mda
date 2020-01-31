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
	private static final Logger log = LoggerFactory.getLogger(ConsolidadoService.class);
	
	public static final String FECHA = "fechaAplicacion";
	private static final String USUARIO_SEQ_KEY = "consolidado_sequence";
	private static final Integer PRIORIDAD=3;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private sequenceGeneratorService SequenceGeneratorService;
	
	public Boolean crearConsolidado(Consolidados request) {
		log.info("Registrar documento");
		Boolean insertado = false;
		if(request != null) {
				CastConsolidados castConsolidados=new CastConsolidados();
				ArchivoEntity consolidado = new ArchivoEntity();
				try {
				consolidado.setVigencia(request.getVigencia());
				consolidado.setNombreAjuste(request.getNombreAjuste());
				consolidado.setEmergente(request.getEmergente());
				consolidado.setFechaAplicacion(request.getFechaAplicacion());
//				Long id = SequenceGeneratorService.generateSequence(USUARIO_SEQ_KEY);
//				consolidado.setIdArchivo(id);
				File archivo=request.getAdjunto();
				FileReader targetReader = new FileReader(archivo);
	    		BufferedReader b=new BufferedReader(targetReader);
	    		List<InfoProducto> lst=castConsolidados.cvsLectura(b);
				consolidado.setAdjunto(castConsolidados.lstToJson(lst));
				consolidado.setPrioridad(PRIORIDAD);
				consolidado.setNombreArchivo(archivo.getName());
				System.out.println(castConsolidados.lstToJson(lst));
				} catch (FileNotFoundException e) {
				e.printStackTrace();
				}
				mongoTemplate.insert(consolidado);
				insertado =  true;
		}
		return insertado;
	}
	public ArrayList<ConsultarArchivoConsolidadoResInner> getConsolidados(String fechaAplicacion){
		System.out.println("entrando a ConsolidadoService.getConsolidados");
		ArrayList<ConsultarArchivoConsolidadoResInner> lstConsolidados=new ArrayList<>();
		Date fechaAplicaciondate = null;
	    try {
	    	fechaAplicaciondate=new SimpleDateFormat("dd/MM/yyyy").parse(fechaAplicacion);
		 System.out.println("fechaActual "+fechaAplicaciondate);
		 System.out.println("***************");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    Date fechaAplicacionInicioDia=CastConsolidados.resetTimeToDown(fechaAplicaciondate);
	    System.out.println("fecha inicio dia "+fechaAplicacionInicioDia);
	    Date fechaAplicacionFinDia=CastConsolidados.resetTimeToUp(fechaAplicaciondate);
	    System.out.println("fecha fin dia "+fechaAplicacionFinDia);
	    System.out.println("armando query");
		Query q=new Query();
		q.addCriteria(Criteria.where("fechaAplicacion").gte(fechaAplicacionInicioDia).lt(fechaAplicacionFinDia));
		List<ArchivoEntity> busquedaList = mongoTemplate.find(q, ArchivoEntity.class);
		System.out.println("query size() "+busquedaList.size());
		if(busquedaList!=null) {
			CastConsolidados castConsolidados=new CastConsolidados();
			lstConsolidados=castConsolidados.toVOs(busquedaList);
		}
		System.out.println("saliendo a ConsolidadoService.getConsolidados");
		return lstConsolidados;
	}
}
