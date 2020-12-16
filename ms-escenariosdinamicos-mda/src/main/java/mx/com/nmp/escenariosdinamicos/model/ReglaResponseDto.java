package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ReglaResponseDto {
	  @JsonProperty("codigo")
	  private String codigo = null;

	  @JsonProperty("existe")
	  private String existe = null;

	  public ReglaResponseDto codigo(String codigo) {
	    this.codigo = codigo;
	    return this;
	  }

	  /**
	   * Get codigo
	   * @return codigo
	  **/
	  @ApiModelProperty(example = "NMP-MDA-000", value = "")


	  public String getCodigo() {
	    return codigo;
	  }

	  public void setCodigo(String codigo) {
	    this.codigo = codigo;
	  }

	  public ReglaResponseDto existe(String existe) {
	    this.existe = existe;
	    return this;
	  }

	  /**
	   * Get mensaje
	   * @return mensaje
	  **/
	  @ApiModelProperty(example = "Operación ejecutada satisfactoriamente", value = "")


	  public String getExiste() {
	    return existe;
	  }

	  public void setExiste(String existe) {
	    this.existe = existe;
	  }


	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    ReglaResponseDto successfulResponse = (ReglaResponseDto) o;
	    return Objects.equals(this.codigo, successfulResponse.codigo) &&
	        Objects.equals(this.existe, successfulResponse.existe);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(codigo, existe);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SuccessfulResponse {\n");
	    
	    sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
	    sb.append("    mensaje: ").append(toIndentedString(existe)).append("\n");
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
