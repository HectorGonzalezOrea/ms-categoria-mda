package mx.com.nmp.usuarios.oag.client.service;

import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.usuarios.oag.vo.BusquedaGrupoVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioRequestVO;
import mx.com.nmp.usuarios.oag.vo.UsuariosResponseVO;

public interface OAGService {

	String getToken() throws UnirestException;
	Object identidadUsuario(IdentidadUsuarioRequestVO request, String oauthBearer, String userLoggeado) throws UnirestException;
	Object identidadUsuario(String oauthBearer) throws UnirestException;
	UsuariosResponseVO getUsersByGroup(String oauthBearer, BusquedaGrupoVO busqueda) throws UnirestException;
	
}
