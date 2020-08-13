package mx.com.nmp.usuarios.oag.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "codigoError", "descripcionError", "tipoError", "severidad" })
public class TokenProviderErrorVO {

	@JsonProperty("codigoError")
	private String codigoError;
	@JsonProperty("descripcionError")
	private String descripcionError;
	@JsonProperty("tipoError")
	private String tipoError;
	@JsonProperty("severidad")
	private String severidad;

	@JsonProperty("codigoError")
	public String getCodigoError() {
		return codigoError;
	}

	@JsonProperty("codigoError")
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	@JsonProperty("descripcionError")
	public String getDescripcionError() {
		return descripcionError;
	}

	@JsonProperty("descripcionError")
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	@JsonProperty("tipoError")
	public String getTipoError() {
		return tipoError;
	}

	@JsonProperty("tipoError")
	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	@JsonProperty("severidad")
	public String getSeveridad() {
		return severidad;
	}

	@JsonProperty("severidad")
	public void setSeveridad(String severidad) {
		this.severidad = severidad;
	}
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("TokenProviderErrorVO {\n");
		 sb.append("codigoError: ").append(toIndentedString(codigoError)).append("\n");
		 sb.append("descripcionError: ").append(toIndentedString(descripcionError)).append("\n");
		 sb.append("tipoError: ").append(toIndentedString(tipoError)).append("\n");
		 sb.append("severidad: ").append(toIndentedString(severidad)).append("\n");
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
