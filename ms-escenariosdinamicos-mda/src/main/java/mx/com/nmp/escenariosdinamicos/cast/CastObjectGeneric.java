package mx.com.nmp.escenariosdinamicos.cast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.CalculoValorVO;
import mx.com.nmp.escenariosdinamicos.constantes.Constantes.Common;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexVentasVO;
import mx.com.nmp.escenariosdinamicos.model.CommonAjuste;
import mx.com.nmp.escenariosdinamicos.model.CommonBaseAjuste;
import mx.com.nmp.escenariosdinamicos.model.InfoRegla;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseReglasArbitrajeOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;

@Repository
public class CastObjectGeneric {
	public IndexGarantiaVO JsonFieldToObject(String jsonField) {
		ObjectMapper mapper = new ObjectMapper();
		IndexGarantiaVO participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonField,IndexGarantiaVO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return participantJsonList;
	}
	
	public String lstToJson(List<CalculoValorVO> lst) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(lst);
		return json;
	}
	
	public List<PartidaPrecioFinal> castJsonToList(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		List<PartidaPrecioFinal> participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonString, new TypeReference<List<PartidaPrecioFinal>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return participantJsonList;
	}
	//cast con lamda
	public List<CalculoValorVO> castGarantiasToCalculoValor(List<IndexGarantiaVO> lstCalculoValor){
		 List<CalculoValorVO> lstCalculoValors=lstCalculoValor.stream().map(calculoValor->{
			 System.out.println("Cast con lamda----");
			 System.out.println(calculoValor.toString());
			 return new CalculoValorVO(
					 calculoValor.getPartida()!=null?calculoValor.getPartida():0,
					 calculoValor.getSku(),
					 calculoValor.getValorMonteAct()!=null?calculoValor.getValorMonteAct():0,
					 calculoValor.getAlhajasGramaje()!=null?calculoValor.getAlhajasGramaje():0,
					 calculoValor.getAlhajasKilates()!=null?calculoValor.getAlhajasKilates():0,
					 calculoValor.getAlhajasIIncremento()!=null?calculoValor.getAlhajasIIncremento():0,
					 calculoValor.getAlhajasDesplComer()!=null?calculoValor.getAlhajasDesplComer():0,
					 calculoValor.getAlhajasAvaluoCompl()!=null?calculoValor.getAlhajasAvaluoCompl():0
					 ); 
		 }).collect(Collectors.toList());
		return lstCalculoValors;
	}
	
	public ResponseOAGDto convertJsonToReponseOAGDto(String jsonString) {
		ResponseOAGDto response= new ResponseOAGDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			response=mapper.readValue(jsonString, ResponseOAGDto.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public ResponseReglasArbitrajeOAGDto convertJsonToReglasArbitraje(String jsonString) {
		ResponseReglasArbitrajeOAGDto response=new ResponseReglasArbitrajeOAGDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			response=mapper.readValue(jsonString, ResponseReglasArbitrajeOAGDto.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public List<PartidaVO> castPartidasToPartidaValorMonte(List<IndexGarantiaVO> lstResultServiceValorMonte,InfoRegla infoRegla){
		List<PartidaVO> lstPartidas=null;
		if (lstResultServiceValorMonte != null && !lstResultServiceValorMonte.isEmpty()) {
			lstPartidas = new ArrayList<>();
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
			if(infoRegla!=null&&infoRegla.getReglasDescuento()!=null&&infoRegla.getReglasDescuento().getPrimerBaseAjuste()!=null){//primer ajuste
				for (CommonBaseAjuste item : infoRegla.getReglasDescuento().getPrimerBaseAjuste()) {
					if(item.getTipoPrecio().equals(Common.PRECIO_ALTO)){
						//partida.setBaseAjusteUnoPA(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
					}
					if(item.getTipoPrecio().equals(Common.PRECIO_MEDIO)){
						//partida.setBaseAjusteUnoPM(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
					}
					if(item.getTipoPrecio().equals(Common.PRECIO_BAJO)){
						//partida.setBaseAjusteUnoPB(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
					}
				}
			}
			if(infoRegla!=null&&infoRegla.getReglasDescuento()!=null&&infoRegla.getReglasDescuento().getSegundaBaseAjuste()!=null){//primer ajuste
				for (CommonBaseAjuste item : infoRegla.getReglasDescuento().getSegundaBaseAjuste()) {
					if(item.getTipoPrecio().equals(Common.PRECIO_ALTO)){
						//partida.setBaseAjusteDosPA(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
					}
					if(item.getTipoPrecio().equals(Common.PRECIO_MEDIO)){
						//partida.setBaseAjusteDosPM(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
					}
					if(item.getTipoPrecio().equals(Common.PRECIO_BAJO)){
						//partida.setBaseAjusteDosPB(calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste())));
					}
				}
			}
			partida.setPrecioFinal(null);
			partida.setPrecioEtiqueta(null);
			partida.setCriterio(infoRegla.getReglasDescuento().getCriterio().getDescripcion());//infoRegla.getReglasDescuento() --deserializar este objeto
			partida.setCandadoPA(null);//infoRegla.getCandadoInferior()
			partida.setCandadoPM(null);//infoRegla.getCandadoInferior()
			partida.setCandadoPB(null);//infoRegla.getCandadoInferior()
			partida.setPrecioVenta(null);
			partida.setMontoPrestamo(null);
		}
		return partida;
	}
	
	public IndexVentasVO JsonFieldToObjectVenta(String jsonField) {
		ObjectMapper mapper = new ObjectMapper();
		IndexVentasVO participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonField,IndexVentasVO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return participantJsonList;
	}
	
	private  Double calcularBaseAjuste(Double factorAjuste, Double precio  ) {
		Double total=0D;
		Double porcentaje= (factorAjuste/100);
		total=precio*porcentaje;
		return total;
	}
	
	private Double retornaPrecioPorTipoBaseAjuste(IndexGarantiaVO index,CommonAjuste tipoAjuste){
		Double valorARetornar=null;
		if(Integer.valueOf(tipoAjuste.getId())==Common.AVALUO_TECNICO){
			valorARetornar=index.getAvaluoTecOrigen();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Common.VALOR_MONTE_ACTUALIZADO){
			valorARetornar=index.getValorMonteAct();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Common.PRECIO_ETIQUETA){
			valorARetornar=(null);
		}
		if(Integer.valueOf(tipoAjuste.getId())==Common.PRECIO_ACTUAL){
			valorARetornar=index.getPrecioVentaAct();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Common.PRESTAMO){
			valorARetornar=index.getImportePrestamo();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Common.AVALUO_COMERCIAL){
			valorARetornar=index.getAvaluoComerc();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Common.PRECIO_MERCADO){
			valorARetornar=(null);
		}
		return valorARetornar;
	}
}


