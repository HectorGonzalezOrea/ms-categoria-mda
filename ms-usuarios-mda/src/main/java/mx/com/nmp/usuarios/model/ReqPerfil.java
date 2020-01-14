package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ReqPerfil
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ReqPerfil   {
  @JsonProperty("idPerfil")
  private Integer idPerfil = null;

  public ReqPerfil idPerfil(Integer idPerfil) {
    this.idPerfil = idPerfil;
    return this;
  }

  /**
   * Identificador del perfil.
   * @return idPerfil
  **/
  @ApiModelProperty(example = "3", value = "Identificador del perfil.")


  public Integer getIdPerfil() {
    return idPerfil;
  }

  public void setIdPerfil(Integer idPerfil) {
    this.idPerfil = idPerfil;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReqPerfil reqPerfil = (ReqPerfil) o;
    return Objects.equals(this.idPerfil, reqPerfil.idPerfil);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPerfil);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReqPerfil {\n");
    
    sb.append("    idPerfil: ").append(toIndentedString(idPerfil)).append("\n");
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

