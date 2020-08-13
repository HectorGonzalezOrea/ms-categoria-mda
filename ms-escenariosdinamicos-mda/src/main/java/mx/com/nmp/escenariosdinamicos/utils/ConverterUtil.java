package mx.com.nmp.escenariosdinamicos.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.escenariosdinamicos.oag.vo.GetTokenResponseVO;


public class ConverterUtil {
	
	private ConverterUtil() {
		throw new UnsupportedOperationException();
	}

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
			log.info("IOException {0} " , ioe);
		}
		return obj;
	}

	
}
