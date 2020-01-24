package mx.com.nmp.consolidados.mongodb.service;

import java.io.File;
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
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
import mx.com.nmp.consolidados.mongodb.service.sequenceGeneratorService;
import src.main.java.mx.com.nmp.usuarios.mongodb.service.String;


public class ConsolidadoService {
	private static final Logger log = LoggerFactory.getLogger(ConsolidadoService.class);
	
	public static final String FECHA = "fechaAplicacion";
	private static final String USUARIO_SEQ_KEY = "usuario_sequence";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private sequenceGeneratorService SequenceGeneratorService;
	
	public Boolean crearUsuario(ArchivoEntity request) {
		log.info("Registrar documento");
		Boolean insertado = false;
		
		if(request != null) {
			
			List<?> listConsolidado = this.getConsolidado(request.getAdjunto(), request.getEmergente(), request.getFechaAplicacion(), request.getNombreAjuste(), request.getVigencia());
			
			if(listConsolidado == null ) {
				ArchivoEntity consolidado = new ArchivoEntity();
				consolidado.setEmergente(request.getEmergente());
				consolidado.setAdjunto(request.getAdjunto());
				consolidado.setFechaAplicacion(request.getFechaAplicacion());
				consolidado.setNombreAjuste(request.getNombreAjuste());
				consolidado.setVigencia(request.getVigencia());
				
				Long id = SequenceGeneratorService.generateSequence(USUARIO_SEQ_KEY);
				consolidado.setIdArchivo(id);
				
				mongoTemplate.insert(consolidado);
				
				insertado =  true;
			}
		}
		
		return insertado;
	}
	
	
	
	public List<?> getConsolidado(File adjunto, Boolean emergente, Date fechaAplicacion, String nombreAjuste, Date vigencia) {
		log.info("UsuarioService.getConsolidado");
		
		Query query = this.busquedaConsolidadoNull(fechaAplicacion);

		List<ArchivoEntity> busquedaList = mongoTemplate.find(query, ArchivoEntity.class);

		List<?> usuarios = null;

		if (CollectionUtils.isNotEmpty(busquedaList)) {
			usuarios = new ArrayList<?>();
			InfoUsuario infoUsuario = null;

			for (ArchivoEntity aux : busquedaList) {
				infoUsuario = new InfoUsuario();

				infoUsuario.setActivo(aux.getActivo());
				infoUsuario.apellidoMaterno(aux.getApellidoMaterno());
				infoUsuario.setApellidoPaterno(aux.getApellidoPaterno());
				infoUsuario.setIdUsuario(aux.getIdUsuario().intValue());
				infoUsuario.setNombre(aux.getNombre());
				infoUsuario.setUsuario(aux.getUsuario());
				
				CapacidadUsuariosRes perfilc = this.buscarPerfilConCapacidades(aux.getPerfil());
				
				if(perfilc != null) {
					infoUsuario.setPerfil(perfilc);
				}
				
				DepartamentoAreaEntity dae = departamentoAreaRepository.findByIdDepartamento(aux.getDepartamentoArea());
				
				if(dae != null) {
					DepatamentoAreaVO daevo = new DepatamentoAreaVO();
					daevo.setId(dae.getIdDepartamento());
					daevo.setDescripcion(dae.getDescripcion());
					infoUsuario.setDepartamentoArea(daevo);
				}
				
				DireccionEntity de = direccionRepository.findByIdDireccion(aux.getDireccion());
				
				if(de != null) {
					DireccionVO devo = new DireccionVO();
					devo.setId(de.getIdDireccion());
					devo.setDescripcion(de.getDescripcion());
					infoUsuario.setDireccion(devo);
				}
				
				GerenciaEntity ge = gerenciaRepository.findByIdGerencia(aux.getGerencia());
				
				if(ge != null) {
					GerenciaVO gvo = new GerenciaVO();
					gvo.setId(ge.getIdGerencia());
					gvo.setDescripcion(ge.getDescripcion());
					infoUsuario.setGerencia(gvo);
				}
				
				PuestoEntity pe = puestoRepository.findByIdPuesto(aux.getPuesto());
				
				if(pe != null) {
					PuestoVO pvo = new PuestoVO();
					pvo.setId(pe.getIdPuesto());
					pvo.setDescripcion(pe.getDescripcion());
					infoUsuario.setPuesto(pvo);
				}
				
				SubdireccionEntity sde = subdireccionRepository.findByIdSubdireccion(aux.getSubdireccion());
				
				if(sde != null) {
					SubdireccionVO sdvo = new SubdireccionVO();
					
					sdvo.setId(sde.getIdSubdireccion());
					sdvo.setDescripcion(sde.getDescripcion());
					
					infoUsuario.setSubdireccion(sdvo);
				}
				
				usuarios.add(infoUsuario);
			}
		}
		return usuarios;
	}
	
	private Query busquedaConsolidadoNull(Date fechaAplicacion) {
		log.info("ConsolidadoService.busquedaConsolidadoNull");
		
		Query query = new Query();

		if (fechaAplicacion != null) {
			Criteria aux = Criteria.where(FECHA).is(fechaAplicacion);
			query.addCriteria(aux);
		}
		log.info("Query: " + query.toString());
		return query;
	}

	
}
