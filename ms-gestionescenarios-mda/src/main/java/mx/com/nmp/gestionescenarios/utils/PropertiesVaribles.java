package mx.com.nmp.gestionescenarios.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesVaribles {
	@Value("${consolidados.header.origen}")
	private String origen;
	@Value("${consolidados.header.destino}")
	private String destino;
	@Value("${consolidados.header.usuario}")
	private String usuario;
	@Value("${consolidados.header.clientId}")
	private String clientId;
	@Value("${consolidos.base_url}")
	private String urlService;
	@Value("${consolidados_endpoint}")
	private String endPointConsolidados;

	@Value("${escenario.dinamico.resource.base_url}")
	private String urlEscenarioDinamico;
	@Value("${escenario.dinamico.resource.endpointejecutar}")
	private String endPointEscenarioDinamico;
	@Value("${escenario.dinamico.resource.header.clientId}")
	private String clientIdEscenarioDinamico;
	
	public String getOrigen() {
		return origen;
	}
	public String getDestino() {
		return destino;
	}
	public String getUsuario() {
		return usuario;
	}
	public String getClientId() {
		return clientId;
	}
	public String getUrlService() {
		return urlService;
	}
	public String getEndPointConsolidados() {
		return endPointConsolidados;
	}
	public String getUrlEscenarioDinamico() {
		return urlEscenarioDinamico;
	}
	public String getEndPointEscenarioDinamico() {
		return endPointEscenarioDinamico;
	}
	public String getClientIdEscenarioDinamico() {
		return clientIdEscenarioDinamico;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public void setUrlService(String urlService) {
		this.urlService = urlService;
	}
}
