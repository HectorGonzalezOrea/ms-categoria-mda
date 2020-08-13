package mx.com.nmp.escenariosdinamicos.elastic.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


import com.google.gson.Gson;

import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexVentasVO;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.component.ProducerMessageComponent;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;

@Component
public class ElasticSearchAsynComponent {
	@Autowired
	private ElasticService elasticService;
	@Autowired
	private ProducerMessageComponent producerMessage;
	@Autowired
	private CastObjectGeneric castObjectGeneric;
	
	@Async
	public void consultarElasticSearch(EjecutarEscenarioDinamicoReq crearEscenariosRequest,String indexVenta,
			String indexGarantia) throws IOException {
		
		List<IndexGarantiaVO> lstIndexGarantia = null;
		RequestReglaEscenarioDinamicoDto requestReglaEscenarioDinamico = new RequestReglaEscenarioDinamicoDto();
		List<PartidaVO> lstPartidaVO = null;
		List<IndexVentasVO> scrollElasticVentas = null;

		// primero obtenemos las ventas de los ultimos tres dias
		scrollElasticVentas = elasticService.scrollElasticVentas(indexVenta);
		// despues consultamos las partidas a partir de las ventas
		lstIndexGarantia = elasticService.scrollElasticGarantias(indexGarantia,
				crearEscenariosRequest.getInfoRegla().getRamo(),
				crearEscenariosRequest.getInfoRegla().getSubramo().get(0), scrollElasticVentas);
		
		lstPartidaVO = castObjectGeneric.castPartidasToPartidaValorMonte(lstIndexGarantia,
				crearEscenariosRequest.getInfoRegla());
		if(!lstPartidaVO.isEmpty()) {
    		requestReglaEscenarioDinamico.setPartida(lstPartidaVO);
    	 	String jsonMessage=new Gson().toJson(requestReglaEscenarioDinamico);
    	 	producerMessage.producerReglaEscenarioDinamico(jsonMessage);
    	}
		
	}
}
