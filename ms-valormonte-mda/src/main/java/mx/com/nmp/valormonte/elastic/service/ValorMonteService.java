package mx.com.nmp.valormonte.elastic.service;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.com.nmp.valormonte.elastic.vo.ValorMonteVO;
import mx.com.nmp.valormonte.model.CalculoValorMonteReq;
import mx.com.nmp.valormonte.model.CalculoValorMonteReqInner;
import mx.com.nmp.valormonte.model.CalculoValorMonteRes;
import mx.com.nmp.valormonte.model.CalculoValorMonteResInner;


@Service
public class ValorMonteService {

	
	private static final Logger log = LoggerFactory.getLogger(ValorMonteService.class);
	
	public CalculoValorMonteReq calcularValorMonte (CalculoValorMonteReq vm) {
		
		if(vm != null) {
			log.info("ValorMonteService.CalcularValorMonte");
			for(CalculoValorMonteReqInner vmri:vm) {
				vmri.getIdPartida();
				vmri.getSKU();
				vmri.getValorAncla();
				vmri.getGramaje();
				vmri.getKilataje();
				vmri.getIncremento();
				vmri.getGramaje();
				vmri.getDesplazamiento();
				vmri.getAvaluoComplementario();
				
			}
			
		}
		return vm;
		
	}

	
	
	
	

}
