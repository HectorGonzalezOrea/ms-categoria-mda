package mx.com.nmp.gestionescenarios.oag.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "idPeticion" })
public class CalendarizarEscenarioResponseVO {

	@JsonProperty("idPeticion")
	private Integer idPeticion;

	@JsonProperty("idPeticion")
	public Integer getIdPeticion() {
		return idPeticion;
	}

	@JsonProperty("idPeticion")
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

}
