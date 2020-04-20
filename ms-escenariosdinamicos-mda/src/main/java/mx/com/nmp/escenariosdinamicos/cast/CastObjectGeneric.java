package mx.com.nmp.escenariosdinamicos.cast;

import java.io.IOException;
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
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;

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
	
	
	public ResponseOAGDto convertJsonToReponseOAFDto(String jsonString) {
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

}
