package mx.com.nmp.establecimientoprecios.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.establecimientoprecios.apiproductos.vo.ActualizarPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaResponseVO;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GetTokenResponseVO;

public class ConverterUtil {

	public static String messageToJson(Object ohj) {
		Gson gson = new Gson();
		return gson.toJson(ohj);
	}

	public static GetTokenResponseVO StringJsonToObjectGetTokenResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		GetTokenResponseVO obj = null;
		try {
			obj = mapper.readValue(json, GetTokenResponseVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static GestionPreciosResponseVO StringJsonToObjectGestionPreciosResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		GestionPreciosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, GestionPreciosResponseVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static ConsultaPartidaResponseVO StringJsonToObjectConsultaPartidaResponse(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ConsultaPartidaResponseVO obj = null;
		try {
			obj = mapper.readValue(json, ConsultaPartidaResponseVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static ActualizarPreciosResponseVO StringJsonToObjectActualizarPreciosResponse(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ActualizarPreciosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, ActualizarPreciosResponseVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static HistoricoPreciosResponseVO StringJsonToObjectHistoricoPreciosResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		HistoricoPreciosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, HistoricoPreciosResponseVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
