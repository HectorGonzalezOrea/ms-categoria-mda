package mx.com.nmp.establecimientoprecios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * ListaMonedasInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-03T00:30:35.505Z")

public class ListaMonedasInner   {
  @JsonProperty("fechaCreacion")
  private LocalDate fechaCreacion = null;

  @JsonProperty("fechaModificacion")
  private LocalDate fechaModificacion = null;

  @JsonProperty("actualizadoPor")
  private String actualizadoPor = null;

  public ListaMonedasInner fechaCreacion(LocalDate fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
    return this;
  }

  /**
   * Fecha de creación
   * @return fechaCreacion
  **/
  @ApiModelProperty(example = "03/12/2019", value = "Fecha de creación")

  @Valid

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(LocalDate fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public ListaMonedasInner fechaModificacion(LocalDate fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
    return this;
  }

  /**
   * Fecha de Modificación
   * @return fechaModificacion
  **/
  @ApiModelProperty(example = "03/12/2019", value = "Fecha de Modificación")

  @Valid

  public LocalDate getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(LocalDate fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public ListaMonedasInner actualizadoPor(String actualizadoPor) {
    this.actualizadoPor = actualizadoPor;
    return this;
  }

  /**
   * Último usuario que actualizó
   * @return actualizadoPor
  **/
  @ApiModelProperty(example = "Suriel Martinez", value = "Último usuario que actualizó")


  public String getActualizadoPor() {
    return actualizadoPor;
  }

  public void setActualizadoPor(String actualizadoPor) {
    this.actualizadoPor = actualizadoPor;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListaMonedasInner listaMonedasInner = (ListaMonedasInner) o;
    return Objects.equals(this.fechaCreacion, listaMonedasInner.fechaCreacion) &&
        Objects.equals(this.fechaModificacion, listaMonedasInner.fechaModificacion) &&
        Objects.equals(this.actualizadoPor, listaMonedasInner.actualizadoPor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fechaCreacion, fechaModificacion, actualizadoPor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaMonedasInner {\n");
    
    sb.append("    fechaCreacion: ").append(toIndentedString(fechaCreacion)).append("\n");
    sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
    sb.append("    actualizadoPor: ").append(toIndentedString(actualizadoPor)).append("\n");
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

