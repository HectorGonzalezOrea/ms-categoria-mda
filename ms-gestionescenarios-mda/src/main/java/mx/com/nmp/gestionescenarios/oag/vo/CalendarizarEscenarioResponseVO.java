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
	
	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("CalendarizarEscenarioResponseVO {\n");
		 sb.append("idPeticion: ").append(toIndentedString(idPeticion)).append("\n");
		 sb.append("}");
		return sb.toString();
	}
	
	 private String toIndentedString(java.lang.Object o) {
		    if (o == null) {
		      return "null";
	   }
	   return o.toString().replace("\n", "\n    ");
	 }

}
