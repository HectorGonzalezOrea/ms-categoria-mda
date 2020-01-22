package mx.com.nmp.usuarios.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import mx.com.nmp.usuarios.mongodb.vo.DepatamentoAreaVO;
import mx.com.nmp.usuarios.mongodb.vo.DireccionVO;
import mx.com.nmp.usuarios.mongodb.vo.GerenciaVO;
import mx.com.nmp.usuarios.mongodb.vo.PuestoVO;
import mx.com.nmp.usuarios.mongodb.vo.SubdireccionVO;
import mx.com.nmp.usuarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioResponseVO;

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
	
	public static IdentidadUsuarioResponseVO StringJsonToObjectIdentidadUsuarioResponseVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		IdentidadUsuarioResponseVO obj = null;
		try {
			obj = mapper.readValue(json, IdentidadUsuarioResponseVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static DepatamentoAreaVO StringJsonToObjectDepatamentoAreaVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		DepatamentoAreaVO obj = null;
		try {
			obj = mapper.readValue(json, DepatamentoAreaVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static DireccionVO StringJsonToObjectDireccionVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		DireccionVO obj = null;
		try {
			obj = mapper.readValue(json, DireccionVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static GerenciaVO StringJsonToObjectGerenciaVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		GerenciaVO obj = null;
		try {
			obj = mapper.readValue(json, GerenciaVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static PuestoVO StringJsonToObjectPuestoVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		PuestoVO obj = null;
		try {
			obj = mapper.readValue(json, PuestoVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static SubdireccionVO StringJsonToObjectSubdireccionVO(String json) {
		ObjectMapper mapper = new ObjectMapper();
		SubdireccionVO obj = null;
		try {
			obj = mapper.readValue(json, SubdireccionVO.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
