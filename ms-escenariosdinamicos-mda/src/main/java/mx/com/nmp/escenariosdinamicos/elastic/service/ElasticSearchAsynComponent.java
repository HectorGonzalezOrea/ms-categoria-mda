package mx.com.nmp.escenariosdinamicos.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.escenariosdinamicos.api.EscenariosApiController;
import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexVentasVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.MdaVentasVO;
import mx.com.nmp.escenariosdinamicos.model.CatalogoVO;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.component.ProducerMessageComponent;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;
import mx.com.nmp.escenariosdinamicos.utils.EmuMacroCategoria;

@Component
public class ElasticSearchAsynComponent {
	@Autowired
	private ElasticService elasticService;
	@Autowired
	private ProducerMessageComponent producerMessage;
	@Autowired
	private CastObjectGeneric castObjectGeneric;
	@Autowired
	private EmuMacroCategoria emuMacroCategoria;
	
	private static final Logger log = LoggerFactory.getLogger(ElasticSearchAsynComponent.class);
	
	@Async
	public void consultarElasticSearch(EjecutarEscenarioDinamicoReq crearEscenariosRequest,String indexVenta,
			String indexGarantia) throws IOException {
       String criterio=null;
		List<IndexGarantiaVO> lstIndexGarantia = null;
		RequestReglaEscenarioDinamicoDto requestReglaEscenarioDinamico = new RequestReglaEscenarioDinamicoDto();
		List<PartidaVO> lstPartidaVO = null;
		List<IndexVentasVO> scrollElasticVentas = null;
		//primero consultamos el indice de garantias
		//CatalogoVO nivelAgrupacion=deserializaNivelAgrupacion(crearEscenariosRequest.getInfoRegla().getNivelAgrupacion());//converción de obj->VO
		//String criterioBusqueda =crearCriterioDeBusqueda(nivelAgrupacion, crearEscenariosRequest);//crea la sentencia where para elasticSearch
		//lstIndexGarantia = elasticService.scrollElasticGarantias(criterioBusqueda,indexVenta);
		//List<String> couosGarantias=extraeCuos(lstIndexGarantia);
		
		// primero obtenemos las ventas de los ultimos tres dias
		//scrollElasticVentas = elasticService.scrollElasticVentas(indexVenta,couosGarantias);
		// despues consultamos las partidas a partir de las ventas

		lstPartidaVO = castObjectGeneric.castPartidasToPartidaValorMonte(lstIndexGarantia,
				crearEscenariosRequest.getInfoRegla());
		if(!lstPartidaVO.isEmpty()) {
    		requestReglaEscenarioDinamico.setPartida(lstPartidaVO);
    	 	String jsonMessage=new Gson().toJson(requestReglaEscenarioDinamico);
    	 	producerMessage.producerReglaEscenarioDinamico(jsonMessage);
    	}
		
	}
	
	public List<String> extraeFolioPartida(List<MdaVentasVO> lstGarantias){
		log.info("extrayendo ids de partidas");
		List<String> cuos=new ArrayList<>();
		lstGarantias.stream().forEach(cuo->{
			log.info("idPartida Extraido->{}",cuo.getFolioPartida());
			cuos.add(cuo.getFolioPartida());
		});
		log.info("size de partidas extraidas [{}]",cuos.size());
		return cuos;
	}
	
}
