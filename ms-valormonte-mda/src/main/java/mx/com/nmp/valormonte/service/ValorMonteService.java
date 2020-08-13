package mx.com.nmp.valormonte.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.com.nmp.valormonte.elastic.service.ElasticService;
import mx.com.nmp.valormonte.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.valormonte.model.CalculoValorMonteReq;
import mx.com.nmp.valormonte.model.CalculoValorMonteReqInner;
import mx.com.nmp.valormonte.model.CalculoValorMonteRes;
import mx.com.nmp.valormonte.model.CalculoValorMonteResInner;
import mx.com.nmp.valormonte.model.NotFound;
import mx.com.nmp.valormonte.utils.Constantes;

@Service
public class ValorMonteService {

	@Autowired
	ElasticService elasticService;

	private static final Logger log = LoggerFactory.getLogger(ValorMonteService.class);

	public Object calcularValorMonte(CalculoValorMonteReq vm) throws IOException {
		log.info("ValorMonteService.CalcularValorMonte");
		List<String> lstSku = new ArrayList<>();
		List<Integer> lstPartidas = new ArrayList<>();
		Object response = new Object();
		
			if (vm != null) {
				for (CalculoValorMonteReqInner vmri : vm) {
					lstSku.add(vmri.getSKU());
					lstPartidas.add(vmri.getIdPartida());
				}

				List<IndexGarantiaVO> lstIndexGarantiaVO = elasticService.getListIndexGarantia(lstSku, lstPartidas);
				if (!lstIndexGarantiaVO.isEmpty()) {
					CalculoValorMonteRes cvmResp = new CalculoValorMonteRes();
					for (IndexGarantiaVO indexGarantia : lstIndexGarantiaVO) {
						response = getCalcularValor(vm, indexGarantia,cvmResp);
					}

				} else {
					NotFound nf = new NotFound();
					nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND_ERROR);
					nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND_ERROR + lstSku.toString());
					return nf;
				}

			}
		

		return response;
	}

	private Object getCalcularValor(CalculoValorMonteReq vm, IndexGarantiaVO indexGarantia,CalculoValorMonteRes cvmResp) {
		CalculoValorMonteResInner cvmRespInner = null;
		Float vma = null;
		
		for (CalculoValorMonteReqInner vmri : vm) {

			if (indexGarantia != null && indexGarantia.getSku().equals(vmri.getSKU()) && indexGarantia.getPartida().equals(vmri.getIdPartida()) ) {
				if (vmri.getValorAncla() != null && vmri.getDesplazamiento() != null && vmri.getGramaje() != null
						&& vmri.getIncremento() != null && vmri.getKilataje() != null
						&& vmri.getAvaluoComplementario() != null) {

					vma = this.valorMonteActualizado(vmri);
					cvmRespInner = new CalculoValorMonteResInner();

					cvmRespInner.setIdPartida(vmri.getIdPartida());
					cvmRespInner.setSku(vmri.getSKU());
					cvmRespInner.setValorMonteActualizado(vma);

					cvmResp.add(cvmRespInner);
				} else {
					calcularValor(cvmResp, vmri, indexGarantia);
				}
			}
		}
		return cvmResp;
	}
	
	private CalculoValorMonteRes calcularValor(CalculoValorMonteRes cvmResp,CalculoValorMonteReqInner vmri,IndexGarantiaVO indexGarantia ) {
		
		CalculoValorMonteResInner cvmRespInner=null;
		if (indexGarantia != null && indexGarantia.getSku().equals(vmri.getSKU())) {
			cvmRespInner = new CalculoValorMonteResInner();

			cvmRespInner.setIdPartida(indexGarantia.getPartida());
			cvmRespInner.setSku(indexGarantia.getSku());

			if (indexGarantia.getValorMonteAct() != null) {
				cvmRespInner.setValorMonteActualizado(
						Float.valueOf(indexGarantia.getValorMonteAct().toString()));
			}
			cvmResp.add(cvmRespInner);
		}
		return cvmResp;
	}


	private Float valorMonteActualizado(CalculoValorMonteReqInner producto) {

		float k24 = producto.getKilataje() / 24;
		float id100 = 1 + ((producto.getIncremento() + producto.getDesplazamiento()) / 100);
		float vma = (producto.getValorAncla() * producto.getGramaje() * k24 * id100)
				+ producto.getAvaluoComplementario();

		return Float.valueOf(vma);
	}

}
