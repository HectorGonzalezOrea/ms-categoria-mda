package mx.com.nmp.usuarios.oag.client.service;

import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.usuarios.oag.vo.BusquedaGrupoVO;
import mx.com.nmp.usuarios.oag.vo.UsuariosResponseVO;

public interface OAGService {

	String getToken() throws UnirestException;
	UsuariosResponseVO getUsersByGroup(String oauthBearer, BusquedaGrupoVO busqueda) throws UnirestException;
	
}
