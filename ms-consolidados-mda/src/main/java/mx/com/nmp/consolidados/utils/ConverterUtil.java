package mx.com.nmp.consolidados.utils;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePreciosFailedVO;
import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePreciosResponseVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasResponseVO;
import mx.com.nmp.consolidados.oag.vo.GetTokenResponseVO;

public class ConverterUtil {

	private static final Logger log = LoggerFactory.getLogger(ConverterUtil.class);
	private ConverterUtil() {
		    throw new IllegalStateException("ConverterUtil class");
     }

	public static String messageToJson(Object ohj) {
		Gson gson = new Gson();
		return gson.toJson(ohj);
	}
	
	public static AjustePreciosResponseVO stringJsonToObjectAjustePreciosResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		AjustePreciosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, AjustePreciosResponseVO.class);

		} catch (IOException ioe) {
			log.info("Error {0} ", ioe);
		}
		return obj;
	}
	
	public static AjustePreciosFailedVO stringJsonToObjectAjustePreciosFailedVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		AjustePreciosFailedVO obj = null;
		try {
			obj = mapper.readValue(json, AjustePreciosFailedVO.class);

		} catch (IOException ioe) {
			log.info("IOException: {0}. ", ioe);
		}
		return obj;
	}
	
	public static GetTokenResponseVO stringJsonToObjectGetTokenResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		GetTokenResponseVO obj = null;
		try {
			obj = mapper.readValue(json, GetTokenResponseVO.class);

		} catch (IOException ioe) {
			log.info("IOException: {0} " , ioe);
		}
		return obj;
	}

	public static ArbitrajePreciosPartidasResponseVO stringJsonToObjectArbitrajePreciosPartidasResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ArbitrajePreciosPartidasResponseVO obj = null;
		try {
			obj = mapper.readValue(json, ArbitrajePreciosPartidasResponseVO.class);

		} catch (IOException ioe) {
			log.info("IOException: {0} " , ioe);
		}
		return obj;
	}
}
