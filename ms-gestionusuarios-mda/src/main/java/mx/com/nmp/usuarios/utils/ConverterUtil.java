package mx.com.nmp.usuarios.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.usuarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioResponseVO;
import mx.com.nmp.usuarios.oag.vo.ProfileVO;
import mx.com.nmp.usuarios.oag.vo.TokenProviderErrorVO;
import mx.com.nmp.usuarios.oag.vo.UsuariosResponseVO;

public class ConverterUtil {
	private ConverterUtil() {
		throw new IllegalStateException("ConverterUtil class");
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

		} catch (IOException e) {
			log.error("Error stringJsonToObjectGetTokenResponseVO: {0}" , e);
		}
		return obj;
	}
	
	public static IdentidadUsuarioResponseVO stringJsonToObjectIdentidadUsuarioResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		IdentidadUsuarioResponseVO obj = null;
		try {
			obj = mapper.readValue(json, IdentidadUsuarioResponseVO.class);

		} catch (IOException e) {
			log.error("Error en stringJsonToObjectIdentidadUsuarioResponseVO: {0}" , e);
		}
		return obj;
	}
	
	public static ProfileVO stringJsonToObjectProfileVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		ProfileVO obj = null;
		try {
			obj = mapper.readValue(json, ProfileVO.class);

		} catch (IOException e) {
			log.error("Error en stringJsonToObjectProfileVO: {0}" , e);
		}
		return obj;
	}
	
	public static TokenProviderErrorVO stringJsonToObjectTokenProviderErrorVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		TokenProviderErrorVO obj = null;
		try {
			obj = mapper.readValue(json, TokenProviderErrorVO.class);

		} catch (IOException e) {
			log.error("Error en stringJsonToObjectTokenProviderErrorVO: {0}" , e);
		}
		return obj;
	}
	
	public static UsuariosResponseVO stringJsonToObjectUsuariosResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		UsuariosResponseVO obj = null;
		try {
			obj = mapper.readValue(json, UsuariosResponseVO.class);

		} catch (IOException e) {
			log.error("Error en stringJsonToObjectUsuarioVO: {0}" , e);
		}
		return obj;
	}
	
}
