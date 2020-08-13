package mx.com.nmp.gestionescenarios.model;

import java.time.LocalDate;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * Información del escenario a ejecutar
 */
@ApiModel(description = "Información del escenario a ejecutar")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class Escenarios   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("fecha")
  private LocalDate fecha = null;

  public Escenarios id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identificador del escenario
   * @return id
  **/
  @ApiModelProperty(example = "Dinamico", value = "Identificador del escenario")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Escenarios fecha(LocalDate fecha) {
    this.fecha = fecha;
    return this;
  }

  /**
   * Fecha de ejecución del escenario
   * @return fecha
  **/
  @ApiModelProperty(value = "Fecha de ejecución del escenario")

  @Valid

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Escenarios escenarios = (Escenarios) o;
    return Objects.equals(this.id, escenarios.id) &&
        Objects.equals(this.fecha, escenarios.fecha);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fecha);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Escenarios {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
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

