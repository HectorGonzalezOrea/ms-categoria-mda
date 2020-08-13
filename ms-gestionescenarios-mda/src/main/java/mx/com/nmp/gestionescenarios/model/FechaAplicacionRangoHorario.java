package mx.com.nmp.gestionescenarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * Rango de horario para aplicación de regla
 */
@ApiModel(description = "Rango de horario para aplicación de regla")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class FechaAplicacionRangoHorario   {
  @JsonProperty("horaInicio")
  private String horaInicio = null;

  @JsonProperty("horaFin")
  private String horaFin = null;

  public FechaAplicacionRangoHorario horaInicio(String horaInicio) {
    this.horaInicio = horaInicio;
    return this;
  }

  /**
   * Hora de inicio
   * @return horaInicio
  **/
  @ApiModelProperty(example = "12:44:08", value = "Hora de inicio")


  public String getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(String horaInicio) {
    this.horaInicio = horaInicio;
  }

  public FechaAplicacionRangoHorario horaFin(String horaFin) {
    this.horaFin = horaFin;
    return this;
  }

  /**
   * Hora de Fin
   * @return horaFin
  **/
  @ApiModelProperty(example = "23:59:59", value = "Hora de Fin")


  public String getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(String horaFin) {
    this.horaFin = horaFin;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FechaAplicacionRangoHorario fechaAplicacionRangoHorario = (FechaAplicacionRangoHorario) o;
    return Objects.equals(this.horaInicio, fechaAplicacionRangoHorario.horaInicio) &&
        Objects.equals(this.horaFin, fechaAplicacionRangoHorario.horaFin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(horaInicio, horaFin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FechaAplicacionRangoHorario {\n");
    
    sb.append("    horaInicio: ").append(toIndentedString(horaInicio)).append("\n");
    sb.append("    horaFin: ").append(toIndentedString(horaFin)).append("\n");
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

