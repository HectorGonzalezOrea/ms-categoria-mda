package mx.com.nmp.gestionescenarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.nmp.gestionescenarios.model.FechaAplicacionRangoHorario;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Información de aplicación de la regla
 */
@ApiModel(description = "Información de aplicación de la regla")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class FechaAplicacion   {
  @JsonProperty("rangoHorario")
  private FechaAplicacionRangoHorario rangoHorario = null;

  @JsonProperty("fechas")
  @Valid
  private List<String> fechas = null;

  public FechaAplicacion rangoHorario(FechaAplicacionRangoHorario rangoHorario) {
    this.rangoHorario = rangoHorario;
    return this;
  }

  /**
   * Get rangoHorario
   * @return rangoHorario
  **/
  @ApiModelProperty(value = "")

  @Valid

  public FechaAplicacionRangoHorario getRangoHorario() {
    return rangoHorario;
  }

  public void setRangoHorario(FechaAplicacionRangoHorario rangoHorario) {
    this.rangoHorario = rangoHorario;
  }

  public FechaAplicacion fechas(List<String> fechas) {
    this.fechas = fechas;
    return this;
  }

  public FechaAplicacion addFechasItem(String fechasItem) {
    if (this.fechas == null) {
      this.fechas = new ArrayList<String>();
    }
    this.fechas.add(fechasItem);
    return this;
  }

  /**
   * Fechas en las que será aplicada la regla
   * @return fechas
  **/
  @ApiModelProperty(value = "Fechas en las que será aplicada la regla")


  public List<String> getFechas() {
    return fechas;
  }

  public void setFechas(List<String> fechas) {
    this.fechas = fechas;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FechaAplicacion fechaAplicacion = (FechaAplicacion) o;
    return Objects.equals(this.rangoHorario, fechaAplicacion.rangoHorario) &&
        Objects.equals(this.fechas, fechaAplicacion.fechas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rangoHorario, fechas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FechaAplicacion {\n");
    
    sb.append("    rangoHorario: ").append(toIndentedString(rangoHorario)).append("\n");
    sb.append("    fechas: ").append(toIndentedString(fechas)).append("\n");
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

