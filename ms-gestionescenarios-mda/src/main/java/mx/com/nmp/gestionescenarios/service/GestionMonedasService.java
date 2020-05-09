package mx.com.nmp.gestionescenarios.service;

import java.time.LocalDate;
import java.util.ArrayList;
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
	
	CastObjectGeneric cast= new CastObjectGeneric();
	
	//LocalDate fecha
	public List<GestionMonedasVO> obtenerValoresMonedas(Boolean oro){
		log.info("::: Entrando al métod obtenerValoresMonedas :::");
		List<MonedasEntity>lstMonedasEntity=gestionRepository.obtenerMonedas(oro);
		List<GestionMonedasVO>lstMonedas= new ArrayList<GestionMonedasVO>();
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
				lstMonedas.add(vo);
				}
			});
		}
		log.info("Tamaño de la lista moneda ::: "+lstMonedas.size());
		return lstMonedas;
	}

}
