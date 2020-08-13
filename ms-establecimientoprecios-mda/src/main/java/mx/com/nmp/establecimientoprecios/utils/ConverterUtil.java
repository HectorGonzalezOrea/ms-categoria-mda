package mx.com.nmp.establecimientoprecios.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.establecimientoprecios.apiproductos.vo.ActualizarPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaResponseVO;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.oag.vo.GetTokenResponseVO;

public class ConverterUtil {
	private static final Logger log = LoggerFactory.getLogger(ConverterUtil.class);
	private ConverterUtil() {
		    throw new IllegalStateException("ConverterUtil class");
    }
	public static String messageToJson(Object ohj) {
		Gson gson = new Gson();
		return gson.toJson(ohj);
	}

	public static GetTokenResponseVO stringJsonToObjectGetTokenResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		GetTokenResponseVO obj = null;
		try {
			obj = mapper.readValue(json, GetTokenResponseVO.class);

		} catch (IOException ioe) {
			log.error("IOException {0}.",ioe);
		}
		return obj;
	}
	
	public static GestionPreciosResponseVO stringJsonToObjectGestionPreciosResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		GestionPreciosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, GestionPreciosResponseVO.class);

		} catch (IOException ioe) {
			log.error("Error stringJsonToObjectGestionPreciosResponseVO {0}",ioe);
		}
		return obj;
	}

	public static ConsultaPartidaResponseVO stringJsonToObjectConsultaPartidaResponse(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ConsultaPartidaResponseVO obj = null;
		try {
			obj = mapper.readValue(json, ConsultaPartidaResponseVO.class);

		} catch (IOException ioe) {
			log.error("IOException {0}",ioe);
		}
		return obj;
	}
	
	public static ActualizarPreciosResponseVO stringJsonToObjectActualizarPreciosResponse(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ActualizarPreciosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, ActualizarPreciosResponseVO.class);

		} catch (IOException e) {
			log.error("IOException {0}",e);
		}
		return obj;
	}
	
	public static HistoricoPreciosResponseVO stringJsonToObjectHistoricoPreciosResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		HistoricoPreciosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, HistoricoPreciosResponseVO.class);

		} catch (IOException e) {
			log.error("Error stringJsonToObjectHistoricoPreciosResponseVO {0}",e);
		}
		return obj;
	}
	
}
