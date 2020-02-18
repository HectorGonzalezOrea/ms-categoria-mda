package mx.com.nmp.valormonte.service;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.com.nmp.valormonte.model.CalculoValorMonteReq;
import mx.com.nmp.valormonte.model.CalculoValorMonteReqInner;
import mx.com.nmp.valormonte.model.CalculoValorMonteRes;
import mx.com.nmp.valormonte.model.CalculoValorMonteResInner;


@Service
public class ValorMonteService {

	
	private static final Logger log = LoggerFactory.getLogger(ValorMonteService.class);
	
	public CalculoValorMonteRes calcularValorMonte(CalculoValorMonteReq vm) {
		log.info("ValorMonteService.CalcularValorMonte");
		
		Float vma = null;
		CalculoValorMonteRes cvmResp = null;
		CalculoValorMonteResInner cvmRespInner = null;
		
		if(vm != null) {
			cvmResp = new CalculoValorMonteRes();
			for(CalculoValorMonteReqInner vmri : vm) {
				if(vmri.getValorAncla() != null && vmri.getDesplazamiento() != null && vmri.getGramaje() != null && vmri.getIncremento() != null && vmri.getKilataje() != null && vmri.getAvaluoComplementario() != null) {
					vma = this.valorMonteActualizado(vmri);
					cvmRespInner = new CalculoValorMonteResInner();
					
					cvmRespInner.setIdPartida(vmri.getIdPartida());
					cvmRespInner.setSKU(vmri.getSKU());
					cvmRespInner.setValorMonteActualizado(vma);
					
					cvmResp.add(cvmRespInner);
				}
			}
			
		}
		
		return cvmResp;
		
	}

	
	private Float valorMonteActualizado(CalculoValorMonteReqInner producto) {
		
		float k24 =  producto.getKilataje()/24;
		float id100 = 1 + ((producto.getIncremento() + producto.getDesplazamiento()) / 100);
		float vma = (producto.getValorAncla() * producto.getGramaje() * k24 * id100) + producto.getAvaluoComplementario();
		
		return Float.valueOf(vma);
	}

}
