package mx.com.nmp.gestionescenarios.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import mx.com.nmp.gestionescenarios.cast.CastObjectGeneric;
import mx.com.nmp.gestionescenarios.mongodb.entity.MonedasEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.GestionMonedasRepository;
import mx.com.nmp.gestionescenarios.vo.GestionMonedasVO;

@Service
public class GestionMonedasService {
	private static final Logger log = LoggerFactory.getLogger(GestionMonedasService.class);
	
	@Autowired
	GestionMonedasRepository gestionRepository;
	
	public List<GestionMonedasVO> obtenerValoresMonedas(Boolean oro,LocalDate fechaAplicacion){
		log.info("::: Entrando al métod obtenerValoresMonedas :::");
		List<GestionMonedasVO>lstMonedas= new ArrayList<>();
		CastObjectGeneric cast= new CastObjectGeneric();
	    try {
			Date fechaInicial=cast.resetTimeToDown(new SimpleDateFormat("yyyy-MM-dd").parse(fechaAplicacion.toString()));
			Date fechaFin=cast.resetTimeToUp(new SimpleDateFormat("yyyy-MM-dd").parse(fechaAplicacion.toString()));
		
		List<MonedasEntity>lstMonedasEntity=gestionRepository.obtenerMonedas(oro,fechaInicial,fechaFin);
		if(!lstMonedasEntity.isEmpty()) {
			lstMonedasEntity.stream().forEach(entity->{
				GestionMonedasVO vo= new GestionMonedasVO();
				if(entity.getFechaCreacion() !=null) {
				vo.setId(entity.getId());
				vo.setTipo(entity.getTipo());
				vo.setPrecio(entity.getPrecio());
				vo.setOro(entity.getOro());
				vo.setFechaCreacion(cast.formatLocalDate(entity.getFechaCreacion()));
				vo.setActualizadoPor(entity.getActualizadoPor());
				vo.setFechaAplicacion(cast.formatLocalDate(entity.getFechaAplicacion()));
				lstMonedas.add(vo);
				}
			});
		}
	    } catch (ParseException e) {
			log.error("Error al parsear las fechas {0}",e);
		}
		log.info("Tamaño de la lista moneda :::{} ",lstMonedas.size());
		return lstMonedas;
	}

}
