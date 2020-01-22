package mx.com.nmp.usuarios.oag.controller;

import org.springframework.beans.factory.annotation.Value;

public class OAGBaseController {

	@Value("${oag.urlBase}")
	protected String urlBase;
	
	@Value("${oag.usuario}")
	protected String usuario;
	
	@Value("${oag.password}")
	protected String password;
	
	@Value("${oag.servicio.identidad.grupo.filtro}")
	protected String servicioIdentidad;
	
	@Value("${oag.resource.identidad.grupo}")
	protected String grupo;
	
	@Value("${oag.servicio.oauth.getToken}")
	protected String servicioGetToken;
	
	@Value("${oag.resource.oauth.getToken.header.usuario}")
	protected String headerUsuario;
	
	@Value("${oag.resource.oauth.getToken.header.idConsumidor}")
	protected String headerIdConsumidor;
	
	@Value("${oag.resource.oauth.getToken.header.idDestino}")
	protected String headerIdDestino;
	
	@Value("${oag.resource.oauth.getToken.body.grantType}")
	protected String grantType;
	
	@Value("${oag.resource.oauth.getToken.body.scope}")
	protected String scope;
	
}
