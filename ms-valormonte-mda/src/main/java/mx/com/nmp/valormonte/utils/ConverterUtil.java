package mx.com.nmp.valormonte.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.valormonte.elastic.vo.ResponseElasticVO;

public class ConverterUtil {

	public static String messageToJson(Object ohj) {
		Gson gson = new Gson();
		return gson.toJson(ohj);
	}
	
	public static ResponseElasticVO StringJsonToObjectElasticVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ResponseElasticVO obj = null;
		try {
			obj = mapper.readValue(json, ResponseElasticVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
