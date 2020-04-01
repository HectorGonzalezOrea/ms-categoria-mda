package mx.com.nmp.gestionescenarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Actualizar estatus de una regla
 */
@ApiModel(description = "Actualizar estatus de una regla")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class EstatusRegla   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("estatus")
  private Object estatus = null;

  public EstatusRegla id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Identificador de la regla
   * @return id
  **/
  @ApiModelProperty(value = "Identificador de la regla")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EstatusRegla estatus(Object estatus) {
    this.estatus = estatus;
    return this;
  }

  /**
   * Get estatus
   * @return estatus
  **/
  @ApiModelProperty(value = "")


  public Object getEstatus() {
    return estatus;
  }

  public void setEstatus(Object estatus) {
    this.estatus = estatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EstatusRegla estatusRegla = (EstatusRegla) o;
    return Objects.equals(this.id, estatusRegla.id) &&
        Objects.equals(this.estatus, estatusRegla.estatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, estatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstatusRegla {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    estatus: ").append(toIndentedString(estatus)).append("\n");
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

