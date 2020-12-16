package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ModificadoResponse {
	  @JsonProperty("code")
	  private String code = null;

	  @JsonProperty("message")
	  private String message = null;

	  public ModificadoResponse code(String code) {
	    this.code = code;
	    return this;
	  }

	  /**
	   * Get code
	   * @return code
	  **/
	  @ApiModelProperty(example = "200", value = "")


	  public String getCode() {
	    return code;
	  }

	  public void setCode(String code) {
	    this.code = code;
	  }

	  public ModificadoResponse message(String message) {
	    this.message = message;
	    return this;
	  }

	  /**
	   * Get message
	   * @return message
	  **/
	  @ApiModelProperty(example = "Operación ejecutada satisfactoriamente", value = "")


	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }


	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    ModificadoResponse eliminarEscenariosRes = (ModificadoResponse) o;
	    return Objects.equals(this.code, eliminarEscenariosRes.code) &&
	        Objects.equals(this.message, eliminarEscenariosRes.message);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(code, message);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class EliminarEscenariosRes {\n");
	    
	    sb.append("    code: ").append(toIndentedString(code)).append("\n");
	    sb.append("    message: ").append(toIndentedString(message)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
}
