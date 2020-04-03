package mx.com.nmp.gestionescenarios.oag.controller;

import org.springframework.beans.factory.annotation.Value;

public class OAGBase {

	@Value("${oag.urlBase}")
	protected String urlBase;
	
	@Value("$oag.usuario}")
	protected String usuarioAuth;
	
	@Value("${oag.password}")
	protected String passwordAuth;
	
	@Value("${oag.servicio.getToken}")
	protected String servicioGetToken;
	
	@Value("${oag.resource.getToken.header.usuario}")
	protected String headerUsuario;
	
	@Value("${oag.resource.getToken.header.idConsumidor}")
	protected String headerIdConsumidor;
	
	@Value("${oag.resource.getToken.header.idDestino}")
	protected String headerIdDestino;

	@Value("${oag.resource.getToken.body.grantType}")
	protected String grantType;
	
	@Value("${oag.resource.getToken.body.scope}")
	protected String scope;
	
	@Value("${oag.servicio.calendarizacion}")
	protected String servicioCalendarizacion;
	
}
