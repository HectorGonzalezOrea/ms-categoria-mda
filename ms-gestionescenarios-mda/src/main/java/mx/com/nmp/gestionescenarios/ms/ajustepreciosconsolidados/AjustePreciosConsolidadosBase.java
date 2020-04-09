package mx.com.nmp.gestionescenarios.ms.ajustepreciosconsolidados;

import org.springframework.beans.factory.annotation.Value;

public abstract class AjustePreciosConsolidadosBase {

	@Value("${ms.ajusteprecios.urlBase}")
	protected String urlBase;
	
	@Value("${ms.ajusteprecios.servicio.consolidados.registrar}")
	protected String servicioRegsitrarConsolidados;

	@Value("${ms.ajusteprecios.servicio.consolidados.consultar}")
	protected String servicioConsultaronsolidados;
	
	@Value("${ms.ajusteprecios.servicio.consolidados.procesar}")
	protected String servicioProcesarConsolidados;
	
	@Value("${ms.ajusteprecios.apikey.value}")
	protected String apiKey;
	
	@Value("${ms.ajusteprecios.header.origen}")
	protected String origen;
	
	@Value("${ms.ajusteprecios.header.destino}")
	protected String destino;
	
}
