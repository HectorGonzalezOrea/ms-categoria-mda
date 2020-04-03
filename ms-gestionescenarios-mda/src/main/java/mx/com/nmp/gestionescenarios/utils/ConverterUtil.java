package mx.com.nmp.gestionescenarios.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.gestionescenarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscanarioResponseVO;

public class ConverterUtil {

	private static final Logger log = LoggerFactory.getLogger(ConverterUtil.class);

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
			log.info("IOException: {} " , ioe);
		}
		return obj;
	}
	
	public static CalendarizarEscanarioResponseVO stringJsonToObjectCalendarizarEscanarioResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		CalendarizarEscanarioResponseVO obj = null;
		try {
			obj = mapper.readValue(json, CalendarizarEscanarioResponseVO.class);

		} catch (IOException ioe) {
			log.info("IOException: {} " , ioe);
		}
		return obj;
	}
	
}
