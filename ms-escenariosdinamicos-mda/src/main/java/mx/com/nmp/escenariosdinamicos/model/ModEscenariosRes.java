package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ModEscenariosRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class ModEscenariosRes   {
  @JsonProperty("idEscenario")
  private Integer idEscenario = null;

  @JsonProperty("fechaActualizacion")
  private LocalDate fechaActualizacion = null;

  public ModEscenariosRes idEscenario(Integer idEscenario) {
    this.idEscenario = idEscenario;
    return this;
  }

  /**
   * Identificador del escenario
   * @return idEscenario
  **/
  @ApiModelProperty(example = "10002", required = true, value = "Identificador del escenario")
  @NotNull


  public Integer getIdEscenario() {
    return idEscenario;
  }

  public void setIdEscenario(Integer idEscenario) {
    this.idEscenario = idEscenario;
  }

  public ModEscenariosRes fechaActualizacion(LocalDate fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
    return this;
  }

  /**
   * Fecha de actualización del escenario
   * @return fechaActualizacion
  **/
  @ApiModelProperty(example = "2019-12-12T08:56:12", value = "Fecha de actualización del escenario")

  @Valid

  public LocalDate getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(LocalDate localDate) {
    this.fechaActualizacion = localDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModEscenariosRes modEscenariosRes = (ModEscenariosRes) o;
    return Objects.equals(this.idEscenario, modEscenariosRes.idEscenario) &&
        Objects.equals(this.fechaActualizacion, modEscenariosRes.fechaActualizacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEscenario, fechaActualizacion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModEscenariosRes {\n");
    
    sb.append("    idEscenario: ").append(toIndentedString(idEscenario)).append("\n");
    sb.append("    fechaActualizacion: ").append(toIndentedString(fechaActualizacion)).append("\n");
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

