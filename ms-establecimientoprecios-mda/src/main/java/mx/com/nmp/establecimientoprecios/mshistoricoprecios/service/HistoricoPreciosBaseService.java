package mx.com.nmp.establecimientoprecios.mshistoricoprecios.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class HistoricoPreciosBaseService {

	@Value("${ms.historicoprecios.urlBase}")
	protected String urlBase;
	
	@Value("${ms.historicoprecios.servicio.historicoPrecios}")
	protected String servicio;
	
	@Value("${ms.historicoprecios.apikey.value}")
	protected String apivalue;
	
}
