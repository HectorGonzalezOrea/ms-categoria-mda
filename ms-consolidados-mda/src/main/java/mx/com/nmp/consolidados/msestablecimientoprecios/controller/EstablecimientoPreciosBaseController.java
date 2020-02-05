package mx.com.nmp.consolidados.msestablecimientoprecios.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class EstablecimientoPreciosBaseController {

	@Value("${ms.establecimientoprecios.urlBase}")
	protected String urlBase;
	
	@Value("${ms.establecimientoprecios.servicio.ajustePrecios}")
	protected String servicio;
	
	@Value("${ms.establecimientoprecios.apikey.value}")
	protected String apivalue;
	
}
