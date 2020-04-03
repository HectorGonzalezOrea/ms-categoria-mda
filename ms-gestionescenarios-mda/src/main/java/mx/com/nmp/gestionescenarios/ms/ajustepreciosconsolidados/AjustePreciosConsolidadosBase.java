package mx.com.nmp.gestionescenarios.ms.ajustepreciosconsolidados;

import org.springframework.beans.factory.annotation.Value;

public abstract class AjustePreciosConsolidadosBase {

	@Value("${ms.ajusteprecios.urlBase}")
	protected String urlBase;
	
	@Value("${ms.ajusteprecios.servicio.ajustePrecios}")
	protected String servicio;
	
	@Value("${ms.ajusteprecios.apikey.value}")
	protected String apiKey;
	
}
