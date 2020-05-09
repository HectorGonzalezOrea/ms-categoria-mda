package mx.com.nmp.gestionescenarios.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import mx.com.nmp.gestionescenarios.cast.CastObjectGeneric;
import mx.com.nmp.gestionescenarios.mongodb.entity.AnclaOroDolarEntity;
import mx.com.nmp.gestionescenarios.mongodb.repository.AnclaOroDolarRepository;
import mx.com.nmp.gestionescenarios.vo.AnclaOroDolarVO;

@Service
public class AnclOroDolarService {
	private static final Logger log = LoggerFactory.getLogger(AnclOroDolarService.class);
	@Autowired
	AnclaOroDolarRepository anclaOroEntity;
	CastObjectGeneric cast= new CastObjectGeneric();
	public List<AnclaOroDolarVO> obtenerValoresAncla(LocalDate date){
		log.info("::: Entrando al método obtenerValoresAncla :::");
		List<AnclaOroDolarVO>  lstAnclaOroDolar= new ArrayList<AnclaOroDolarVO>();
		
		List<AnclaOroDolarEntity> lstEntity=anclaOroEntity.getListAnclaOroDolar(date);
		if(!lstEntity.isEmpty()) {
			lstEntity.stream().forEach(entity->{
				AnclaOroDolarVO vo= new AnclaOroDolarVO();
				vo.setValorAnclaOro(entity.getValorAnclaOro());
				vo.setValorAnclaDolar(entity.getValorAnclaDolar());
				vo.setFechaAplicacion(cast.covertDateToEstring(entity.getFechaAplicacion()));
				vo.setIdBolsa(entity.getIdBolsa());
				vo.setSucursales(entity.getSucursales());
				lstAnclaOroDolar.add(vo);
			});
		}
		log.info("El tamaño de la lista "+ lstAnclaOroDolar.size());
		return lstAnclaOroDolar;
	}

}
