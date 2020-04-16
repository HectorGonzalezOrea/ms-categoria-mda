package mx.com.nmp.escenariosdinamicos.cast;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.CalculoValorVO;
import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;

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
	
	public List<PartidaVO> castJsonToList(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		List<PartidaVO> participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonString, new TypeReference<List<PartidaVO>>() {
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
}
