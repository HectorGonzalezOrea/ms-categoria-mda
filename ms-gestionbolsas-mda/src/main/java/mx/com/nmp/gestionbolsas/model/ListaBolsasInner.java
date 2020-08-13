package mx.com.nmp.gestionbolsas.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * ListaBolsasInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-21T17:43:22.226Z")

public class ListaBolsasInner  {
  @JsonProperty("fechaCreacion")
  private LocalDate fechaCreacion = null;

  @JsonProperty("fechaModificacion")
  private LocalDate fechaModificacion = null;

  public ListaBolsasInner fechaCreacion(LocalDate fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
    return this;
  }

  /**
   * Fecha de creación de la bolsa
   * @return fechaCreacion
  **/
  @ApiModelProperty(example = "03/12/2019", value = "Fecha de creación de la bolsa")

  @Valid

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(LocalDate fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public ListaBolsasInner fechaModificacion(LocalDate fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
    return this;
  }

  /**
   * Fecha de modificación de la bolsa
   * @return fechaModificacion
  **/
  @ApiModelProperty(example = "04/12/2019", value = "Fecha de modificación de la bolsa")

  @Valid

  public LocalDate getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(LocalDate fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListaBolsasInner listaBolsasInner = (ListaBolsasInner) o;
    return Objects.equals(this.fechaCreacion, listaBolsasInner.fechaCreacion) &&
        Objects.equals(this.fechaModificacion, listaBolsasInner.fechaModificacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fechaCreacion, fechaModificacion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaBolsasInner {\n");
    
    sb.append("    fechaCreacion: ").append(toIndentedString(fechaCreacion)).append("\n");
    sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
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

