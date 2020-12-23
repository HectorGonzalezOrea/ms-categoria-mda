package mx.com.nmp.usuarios.mongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.ResHistorico;
import mx.com.nmp.usuarios.model.CrearHistoricoRes.AccionesEnum;
import mx.com.nmp.usuarios.model.ReqHistorico.AccionEnum;
import mx.com.nmp.usuarios.mongodb.entity.HistorialEntity;
import mx.com.nmp.usuarios.mongodb.entity.SequenceGeneratorService;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.utils.Constantes;

@Service
public class HistoricoServiceImpl implements HistoricoService {

	private static final Logger logger = LoggerFactory.getLogger(HistoricoServiceImpl.class);
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	UsuarioService usuarioService;
	
	private static final String HISTORICO_SEQ_KEY = "historico_sequence";
	
	@Override
	public CrearHistoricoRes crearHistorico(AccionEnum accion, Date fecha, Integer idPerfil, String usuario) throws Exception {

		logger.info("crearHistorico");
		
		CrearHistoricoRes resp = null;
		HistorialEntity he = new HistorialEntity();
		
		he.setAccion(accion.getValue());
		he.setIdPerfil(idPerfil);
		he.setUsuario(usuario);
		he.setFecha(fecha);
		he.setIdHistorialUsuario(sequenceGeneratorService.generateSequence(HISTORICO_SEQ_KEY));
		
		mongoTemplate.insert(he);
		
		Query query = new Query();
		Criteria aux = Criteria.where(Constantes.ID).is(he.get_id());
		query.addCriteria(aux);

		HistorialEntity historico = mongoTemplate.findOne(query, HistorialEntity.class);
		
		if (historico != null) {
			resp = new CrearHistoricoRes();
			resp.setFecha(historico.getFecha());
			resp.setUsuario(historico.getUsuario());
			resp.setIdRegistro(historico.getIdHistorialUsuario().intValue());
			resp.setAcciones(AccionesEnum.fromValue(historico.getAccion()));
			if (historico.getIdPerfil() != null) {
				CapacidadUsuariosRes cureps = perfilService.perfilById(historico.getIdPerfil());
				if (cureps != null) {
					resp.setPerfil(cureps);
				}
			}
		}
		
		return resp;
	}

	@Override
	public ConsultaHistoricoRes getHistorico(Integer idUsuario) throws Exception {
		logger.info("UsuarioService.getHistorico");

		ConsultaHistoricoRes chres = null;

		if (idUsuario != null) {
			logger.info("id distinto de null");

			UsuarioEntity user = usuarioService.consultarUsuario(idUsuario);
			
			if (user != null) {
				logger.info("usuario encontrado");
				Query query = new Query();
				Criteria aux = Criteria.where(Constantes.USUARIO).is(user.getUsuario());
				query.addCriteria(aux);

				List<HistorialEntity> listHistorico = mongoTemplate.find(query, HistorialEntity.class);

				logger.info("listHistorico {}", listHistorico.size());

				chres = new ConsultaHistoricoRes();
				
				if (CollectionUtils.isNotEmpty(listHistorico)) {
					logger.info("lista de historico");
					chres.setHistorial(addListHistorico(listHistorico));
				}
			}
		}

		if(chres == null) {
			chres = new ConsultaHistoricoRes();
			List<ResHistorico> listaHist = new ArrayList<>();
			chres.setHistorial(listaHist);
		}
		
		return chres;
	}

	private List<ResHistorico> addListHistorico(List<HistorialEntity> listHistorico) throws Exception {
		List<ResHistorico> historial = new ArrayList<>();

		ResHistorico re = null;
		for (HistorialEntity he : listHistorico) {
			re = new ResHistorico();

			re.setIdRegistro(he.getIdHistorialUsuario().intValue());
			re.setFecha(he.getFecha());
			re.setUsuario(he.getUsuario());

			if (he.getIdPerfil() != null) {
				CapacidadUsuariosRes cureps = perfilService.perfilById(he.getIdPerfil());
				if (cureps != null) {
					re.setPerfil(cureps);
				}
			}
			re.setAccion(mx.com.nmp.usuarios.model.ResHistorico.AccionEnum.fromValue(he.getAccion()));

			historial.add(re);
		}
		return historial;
	}
	
}
