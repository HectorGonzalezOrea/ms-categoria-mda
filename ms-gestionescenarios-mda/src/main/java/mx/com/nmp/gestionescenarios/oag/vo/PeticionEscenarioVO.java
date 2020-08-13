package mx.com.nmp.gestionescenarios.oag.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "iniciarEjecucionEscenarioRequest" })
public class PeticionEscenarioVO {

	@JsonProperty("iniciarEjecucionEscenarioRequest")
	private IniciarEjecucionEscenarioRequestVO iniciarEjecucionEscenarioRequest;
	
	@JsonProperty("iniciarEjecucionEscenarioRequest")
	public IniciarEjecucionEscenarioRequestVO getIniciarEjecucionEscenarioRequest() {
		return iniciarEjecucionEscenarioRequest;
	}

	@JsonProperty("iniciarEjecucionEscenarioRequest")
	public void setIniciarEjecucionEscenarioRequest(IniciarEjecucionEscenarioRequestVO iniciarEjecucionEscenarioRequest) {
		this.iniciarEjecucionEscenarioRequest = iniciarEjecucionEscenarioRequest;
	}

}
