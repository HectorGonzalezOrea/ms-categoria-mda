package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * ReqEstatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ReqEstatus   {
  @JsonProperty("activo")
  private Boolean activo = null;

  public ReqEstatus activo(Boolean activo) {
    this.activo = activo;
    return this;
  }

  /**
   * estatus registrado por el administrador.
   * @return activo
  **/
  @ApiModelProperty(value = "estatus registrado por el administrador.")


  public Boolean isActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReqEstatus reqEstatus = (ReqEstatus) o;
    return Objects.equals(this.activo, reqEstatus.activo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReqEstatus {\n");
    
    sb.append("    activo: ").append(toIndentedString(activo)).append("\n");
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

