package mx.com.nmp.gestionescenarios.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.gestionescenarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.gestionescenarios.oag.vo.CalendarizarEscanarioResponseVO;
import mx.com.nmp.gestionescenarios.oag.vo.ConsultarConsolidadoResponseVO;

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
			log.error("IOException: {} " , ioe);
		}
		return obj;
	}
	
	public static CalendarizarEscanarioResponseVO stringJsonToObjectCalendarizarEscanarioResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		CalendarizarEscanarioResponseVO obj = null;
		try {
			obj = mapper.readValue(json, CalendarizarEscanarioResponseVO.class);

		} catch (IOException ioe) {
			log.error("IOException: {} " , ioe);
		}
		return obj;
	}

	public static ConsultarConsolidadoResponseVO stringJsonToObjectConsultarConsolidadoResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ConsultarConsolidadoResponseVO obj = null;
		try {
			obj = mapper.readValue(json, ConsultarConsolidadoResponseVO.class);

		} catch (IOException ioe) {
			log.error("IOException: {} " , ioe);
		}
		return obj;
	}
	
	public static void convertMultipartFileToFile(MultipartFile file) {
		log.info("convertMultipartFileToFile");
		
		File convFile = new File("tmp/" + file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException ioe) {
			log.error("IOException: {} " , ioe);
		}
		//return convFile;
	}

}
