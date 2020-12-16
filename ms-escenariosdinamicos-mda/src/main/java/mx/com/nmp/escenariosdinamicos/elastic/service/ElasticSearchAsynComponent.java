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
		CatalogoVO nivelAgrupacion=deserializaNivelAgrupacion(crearEscenariosRequest.getInfoRegla().getNivelAgrupacion());//converción de obj->VO
		String criterioBusqueda =crearCriterioDeBusqueda(nivelAgrupacion, crearEscenariosRequest);//crea la sentencia where para elasticSearch
		lstIndexGarantia = elasticService.scrollElasticGarantias(criterioBusqueda,indexVenta);
		List<String> couosGarantias=extraeCuos(lstIndexGarantia);
		
		// primero obtenemos las ventas de los ultimos tres dias
		scrollElasticVentas = elasticService.scrollElasticVentas(indexVenta,couosGarantias);
		// despues consultamos las partidas a partir de las ventas

		lstPartidaVO = castObjectGeneric.castPartidasToPartidaValorMonte(lstIndexGarantia,
				crearEscenariosRequest.getInfoRegla());
		if(!lstPartidaVO.isEmpty()) {
    		requestReglaEscenarioDinamico.setPartida(lstPartidaVO);
    	 	String jsonMessage=new Gson().toJson(requestReglaEscenarioDinamico);
    	 	producerMessage.producerReglaEscenarioDinamico(jsonMessage);
    	}
		
	}
	
	public CatalogoVO deserializaNivelAgrupacion(Object nivelAgrupacion){
		 ObjectMapper mapper = new ObjectMapper();
		 CatalogoVO nivelAgrupacionGeneric=null;
		 String json;
		try {
			json = mapper.writeValueAsString(nivelAgrupacion);
			 nivelAgrupacionGeneric=mapper.readValue(json, CatalogoVO.class);
		} catch (JsonProcessingException e) {
			log.error("Error al convertir String");
		} catch (IOException e) {
			log.error("error al convertir a pojo");
			e.printStackTrace();
		}
		return nivelAgrupacionGeneric;
	}
	
	public String crearCriterioDeBusqueda(CatalogoVO nivelAgrupacion,EjecutarEscenarioDinamicoReq crearEscenariosRequest){
		String criterioBusqueda=null;
		if(nivelAgrupacion.getDescripcion().equals(Constantes.RAMO)){
			criterioBusqueda="ramo:'"+crearEscenariosRequest.getInfoRegla().getRamo()+"'";
		}else if(nivelAgrupacion.getDescripcion().equals(Constantes.SUBRAMO)){
			criterioBusqueda="subramo:'"+crearEscenariosRequest.getInfoRegla().getSubramo().get(0)+"'";
		}else if(nivelAgrupacion.getDescripcion().equals(Constantes.FACTOR)){
			criterioBusqueda="factor:'"+crearEscenariosRequest.getInfoRegla().getFactor().get(0)+"'";
		}else if(nivelAgrupacion.getDescripcion().equals(Constantes.CATEGORIA)){
			String categoria=emuMacroCategoria.calculaCategoriaTop(crearEscenariosRequest.getInfoRegla().getCategoria());
			criterioBusqueda="categoria:'"+categoria+"'";
		}	
		return criterioBusqueda;
	}
	
	public List<String> extraeCuos(List<IndexGarantiaVO> lstGarantias){
		List<String> cuos=new ArrayList<>();
		lstGarantias.stream().forEach(cuo->cuos.add(cuo.getNumCuo()));
		return cuos;
	}
	
}
