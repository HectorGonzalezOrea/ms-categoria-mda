package mx.com.nmp.consolidados.mongodb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import mx.com.nmp.consolidados.mongodb.service.sequenceGeneratorService;

public class ConsolidadoService {
	private static final Logger log = LoggerFactory.getLogger(ConsolidadoService.class);
	
	public static final String FECHA = "fechaAplicacion";
	private static final String USUARIO_SEQ_KEY = "usuario_sequence";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private sequenceGeneratorService SequenceGeneratorService;
	
}
