package mx.com.nmp.consolidados.oag.client.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class OAGBaseService {

		@Value("${oag.urlBase}")
		protected String urlBase;
		
		@Value("${oag.usuario}")
		protected String usuario;
		
		@Value("${oag.password}")
		protected String password;
		
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
		
		@Value("${oag.servicio.oauth.eliminarCalendarizacion}")
		protected String servicioEliminarCalendarizacion;
		
		@Value("${oag.servicio.oauth.validarArbitrajePreciosPartidas}")
		protected String servicioValidarArbitrajePreciosPartidas;
		
		@Value("${oag.servicio.oauth.enviarCorreo}")
		protected String servicioEnviarCorreo;
				
}
