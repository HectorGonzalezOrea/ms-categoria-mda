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
 * CrearEscenariosRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class CrearEscenariosRes   {
  @JsonProperty("idEscenario")
  private Integer idEscenario = null;

  @JsonProperty("fechaCreacion")
  private LocalDate fechaCreacion = null;

  public CrearEscenariosRes idEscenario(Integer idEscenario) {
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

  public CrearEscenariosRes fechaCreacion(LocalDate fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
    return this;
  }

  /**
   * Fecha de registro del escenario
   * @return fechaCreacion
  **/
  @ApiModelProperty(example = "2019-12-12T08:56:12", value = "Fecha de registro del escenario")

  @Valid

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(LocalDate localDate) {
    this.fechaCreacion = localDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CrearEscenariosRes crearEscenariosRes = (CrearEscenariosRes) o;
    return Objects.equals(this.idEscenario, crearEscenariosRes.idEscenario) &&
        Objects.equals(this.fechaCreacion, crearEscenariosRes.fechaCreacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEscenario, fechaCreacion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CrearEscenariosRes {\n");
    
    sb.append("    idEscenario: ").append(toIndentedString(idEscenario)).append("\n");
    sb.append("    fechaCreacion: ").append(toIndentedString(fechaCreacion)).append("\n");
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

