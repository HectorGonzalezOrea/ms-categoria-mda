package mx.com.nmp.establecimientoprecios.apiproductos.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class ApiProductosBaseController {

	@Value("${api.productos.urlBase}")
	protected String urlBase;
	
	@Value("${api.productos.servicio.consultaPartida}")
	protected String consultaPartida;
	
	@Value("${api.productos.servicio.asignacionPrecios}")
	protected String asignacionPrecios;
	
	@Value("${api.productos.apikey.value}")
	protected String apikey;
	
}
