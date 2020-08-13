package mx.com.nmp.escenariosdinamicos.cast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.CalculoValorVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexVentasVO;
import mx.com.nmp.escenariosdinamicos.model.CommonAjuste;
import mx.com.nmp.escenariosdinamicos.model.CommonBaseAjuste;
import mx.com.nmp.escenariosdinamicos.model.InfoRegla;
import mx.com.nmp.escenariosdinamicos.model.InformacionAjusteVO;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestActualizarPrecioPartidaDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseReglasArbitrajeOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.ReglasArbitrajeVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;

@Repository
public class CastObjectGeneric {
	private static final Logger log = LoggerFactory.getLogger(CastObjectGeneric.class);
	
	public IndexGarantiaVO jsonFieldToObject(String jsonField) {
		ObjectMapper mapper = new ObjectMapper();
		IndexGarantiaVO participantJsonList = null;
			
		try {
			participantJsonList = mapper.readValue(jsonField,IndexGarantiaVO.class);
		} catch (IOException ioe) {
			log.error("{0}", ioe);
		}

		return participantJsonList;
	}
	
	public String lstToJson(List<CalculoValorVO> lst) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(lst);
	}
	
	public List<PartidaPrecioFinal> castJsonToList(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		List<PartidaPrecioFinal> participantJsonList = null;
			try {
				participantJsonList = mapper.readValue(jsonString, new TypeReference<List<PartidaPrecioFinal>>() {
				});
			} catch (IOException e) {
				log.error("{0}", e);
			}
		return participantJsonList;
	}
	//cast con lamda
	public List<CalculoValorVO> castGarantiasToCalculoValor(List<IndexGarantiaVO> lstCalculoValor) {
		List<CalculoValorVO> lstCalculoValors = lstCalculoValor.stream().map(calculoValor -> {
			log.info("Cast con lamda----");
			log.info(calculoValor.toString());
			return new CalculoValorVO(calculoValor.getPartida() != null ? calculoValor.getPartida() : 0,
					calculoValor.getSku(),
					calculoValor.getValorMonteAct() != null ? calculoValor.getValorMonteAct() : 0,
					calculoValor.getAlhajasGramaje() != null ? calculoValor.getAlhajasGramaje() : 0,
					calculoValor.getAlhajasKilates() != null ? calculoValor.getAlhajasKilates() : 0,
					calculoValor.getAlhajasIIncremento() != null ? calculoValor.getAlhajasIIncremento() : 0,
					calculoValor.getAlhajasDesplComer() != null ? calculoValor.getAlhajasDesplComer() : 0,
					calculoValor.getAlhajasAvaluoCompl() != null ? calculoValor.getAlhajasAvaluoCompl() : 0);
		}).collect(Collectors.toList());
		return lstCalculoValors;
	}
	
	public ResponseOAGDto convertJsonToReponseOAGDto(String jsonString) {
		ResponseOAGDto response= new ResponseOAGDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			response=mapper.readValue(jsonString, ResponseOAGDto.class);
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return response;
	}
	
	public ResponseReglasArbitrajeOAGDto convertJsonToReglasArbitraje(String jsonString) {
		ResponseReglasArbitrajeOAGDto response=new ResponseReglasArbitrajeOAGDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			response=mapper.readValue(jsonString, ResponseReglasArbitrajeOAGDto.class);
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return response;
	}
	
	public List<PartidaVO> castPartidasToPartidaValorMonte(List<IndexGarantiaVO> lstResultServiceValorMonte,InfoRegla infoRegla){
		List<PartidaVO> lstPartidas  = new ArrayList<>();
		if (lstResultServiceValorMonte != null && !lstResultServiceValorMonte.isEmpty()) {
			for (IndexGarantiaVO entity : lstResultServiceValorMonte) {
				lstPartidas.add(fillValues(entity,infoRegla));
			}
		}
		return lstPartidas;
	}
	public PartidaVO fillValues(IndexGarantiaVO index,InfoRegla infoRegla){
		PartidaVO partida=null;
		if(index!=null){
			partida=new PartidaVO();
			partida.setIdPartida(String.valueOf(index.getPartida()));
			partida.setSku(index.getSku());
			partida.setVentasDiaUno(null);
			partida.setVentasDiaDos(null);
			partida.setVentasDiaTres(null);
			validarPrimerBaseAjusteNull(infoRegla, partida, index);
			validarSegundoBaseAjusteNull(infoRegla, partida, index);
			validarCandadoInferiorNull(infoRegla, partida, index);
			partida.setPrecioFinal(null);
			partida.setPrecioEtiqueta(null);
			partida.setCriterio(infoRegla.getReglasDescuento().getCriterio().getDescripcion());

			partida.setPrecioVenta(index.getPrecioVentaAct());
			partida.setMontoPrestamo(index.getImportePrestamo());
		}
		return partida;
	}
	
	
	private PartidaVO validarPrimerBaseAjusteNull(InfoRegla infoRegla,PartidaVO partida,IndexGarantiaVO index) {
		if(infoRegla!=null&&infoRegla.getReglasDescuento()!=null&&infoRegla.getReglasDescuento().getPrimerBaseAjuste()!=null){//Primer Ajuste
			for (CommonBaseAjuste item : infoRegla.getReglasDescuento().getPrimerBaseAjuste()) {
				if(item.getTipoPrecio().equals(Constantes.PRECIO_ALTO)){
					partida.setBaseAjusteUnoPA(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_MEDIO)){
					partida.setBaseAjusteUnoPM(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_BAJO)){
					partida.setBaseAjusteUnoPB(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
				}
			}
		}
		return partida;
	}
	
	private PartidaVO validarSegundoBaseAjusteNull(InfoRegla infoRegla,PartidaVO partida,IndexGarantiaVO index) {
		if(infoRegla!=null&&infoRegla.getReglasDescuento()!=null&&infoRegla.getReglasDescuento().getSegundaBaseAjuste()!=null){//Segungo Ajuste
			for (CommonBaseAjuste item : infoRegla.getReglasDescuento().getSegundaBaseAjuste()) {
				if(item.getTipoPrecio().equals(Constantes.PRECIO_ALTO)){
					partida.setBaseAjusteDosPA(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_MEDIO)){
					partida.setBaseAjusteDosPM(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_BAJO)){
					partida.setBaseAjusteDosPB(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
				}
			}
		}
		return partida;
	}
	
	private PartidaVO validarCandadoInferiorNull(InfoRegla infoRegla,PartidaVO partida,IndexGarantiaVO index) {
		if(infoRegla!=null&&infoRegla.getCandadoInferior()!=null){//Candado inferior
			for (InformacionAjusteVO factor : infoRegla.getCandadoInferior()) {
				if(factor.getTipoPrecio().equals(Constantes.PRECIO_ALTO)){
					partida.setCandadoPA(calcularBaseAjuste(Double.valueOf(factor.getFactorAjuste()), retornaPrecioPorTipoBaseAjuste(index, factor.getBaseAjuste())));
				}
				if(factor.getTipoPrecio().equals(Constantes.PRECIO_MEDIO)){
					partida.setCandadoPM(calcularBaseAjuste(Double.valueOf(factor.getFactorAjuste()), retornaPrecioPorTipoBaseAjuste(index, factor.getBaseAjuste())));		
				}
				if(factor.getTipoPrecio().equals(Constantes.PRECIO_BAJO)){
					partida.setCandadoPB(calcularBaseAjuste(Double.valueOf(factor.getFactorAjuste()), retornaPrecioPorTipoBaseAjuste(index, factor.getBaseAjuste())));
				}
			}
		}
		return partida;
	}
	
	public IndexVentasVO jsonFieldToObjectVenta(String jsonField) {
		ObjectMapper mapper = new ObjectMapper();
		IndexVentasVO participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonField,IndexVentasVO.class);
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return participantJsonList;
	}
	
	private  Double calcularBaseAjuste(Double factorAjuste, Double precio  ) {
		
		Double total;
		Double porcentaje= (factorAjuste/100);
		total=precio*porcentaje;
		return total;
	}
	
	private Double retornaPrecioPorTipoBaseAjuste(IndexGarantiaVO index,CommonAjuste tipoAjuste){
		Double valorARetornar=null;
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.AVALUO_TECNICO){
			valorARetornar=index.getAvaluoTecOrigen();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.VALOR_MONTE_ACTUALIZADO){
			valorARetornar=index.getValorMonteAct();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRECIO_ETIQUETA){
			valorARetornar=(Double.parseDouble(index.getPrecioVentaInicial()));
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRECIO_ACTUAL){
			valorARetornar=index.getPrecioVentaAct();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRESTAMO){
			valorARetornar=index.getImportePrestamo();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.AVALUO_COMERCIAL){
			valorARetornar=index.getAvaluoComerc();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRECIO_MERCADO){
			valorARetornar=(null);
		}
		return valorARetornar;
	}
	
	public RequestReglaEscenarioDinamicoDto convertJsonToRequestEscenario(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		RequestReglaEscenarioDinamicoDto request= new RequestReglaEscenarioDinamicoDto();
		try {
			request=mapper.readValue(jsonString, RequestReglaEscenarioDinamicoDto.class);
		} catch (IOException e) {
			log.error("Error request escenario dinamico: {0}", e);
		}

		return request;
	}

	public RequestActualizarPrecioPartidaDto convertJsonToRequestActualizarPrecioPartida(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		RequestActualizarPrecioPartidaDto newRequest= new RequestActualizarPrecioPartidaDto();
		
		try {
			ResponseReglasArbitrajeOAGDto request=mapper.readValue(jsonString, ResponseReglasArbitrajeOAGDto.class);
			for (ReglasArbitrajeVO vo : request.getPartida()) {
				newRequest.setEnLinea(true);
				newRequest.setEscenario("PM");
				newRequest.setFolioPartida(vo.getIdPartida());
				newRequest.setSku(vo.getSku());
				newRequest.setPrecioActual(1800D);
				newRequest.setPrecioModificado(1200D);
			}
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return newRequest;
	}
}


