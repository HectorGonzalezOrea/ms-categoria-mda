package mx.com.nmp.consolidados.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ModificarPrioridadArchivoConsolidadoReq
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

public class ModificarPrioridadArchivoConsolidadoReq   {
  @JsonProperty("idArchivo")
  private Integer idArchivo = null;

  @JsonProperty("idPrioridad")
  private Integer idPrioridad = null;

  public ModificarPrioridadArchivoConsolidadoReq idArchivo(Integer idArchivo) {
    this.idArchivo = idArchivo;
    return this;
  }

  /**
   * Identificador del archivo
   * @return idArchivo
  **/
  @ApiModelProperty(example = "111", value = "Identificador del archivo")


  public Integer getIdArchivo() {
    return idArchivo;
  }

  public void setIdArchivo(Integer idArchivo) {
    this.idArchivo = idArchivo;
  }

  public ModificarPrioridadArchivoConsolidadoReq idPrioridad(Integer idPrioridad) {
    this.idPrioridad = idPrioridad;
    return this;
  }

  /**
   * Prioridad en la ejecución del archivo
   * @return idPrioridad
  **/
  @ApiModelProperty(example = "0", value = "Prioridad en la ejecución del archivo")


  public Integer getIdPrioridad() {
    return idPrioridad;
  }

  public void setIdPrioridad(Integer idPrioridad) {
    this.idPrioridad = idPrioridad;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModificarPrioridadArchivoConsolidadoReq modificarPrioridadArchivoConsolidadoReq = (ModificarPrioridadArchivoConsolidadoReq) o;
    return Objects.equals(this.idArchivo, modificarPrioridadArchivoConsolidadoReq.idArchivo) &&
        Objects.equals(this.idPrioridad, modificarPrioridadArchivoConsolidadoReq.idPrioridad);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idArchivo, idPrioridad);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModificarPrioridadArchivoConsolidadoReq {\n");
    
    sb.append("    idArchivo: ").append(toIndentedString(idArchivo)).append("\n");
    sb.append("    idPrioridad: ").append(toIndentedString(idPrioridad)).append("\n");
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

