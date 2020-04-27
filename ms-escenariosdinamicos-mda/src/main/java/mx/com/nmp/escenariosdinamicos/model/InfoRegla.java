package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Información de configuración de la regla
 */
@ApiModel(description = "Información de configuración de la regla")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class InfoRegla extends InfoGeneralRegla  {
  @JsonProperty("nivelAgrupacion")
  private Object nivelAgrupacion = null;

  @JsonProperty("reglasDescuento")
  private ReglasDescuentoVO reglasDescuento = null;

  @JsonProperty("candadoInferior")
  private Object candadoInferior = null;

  public InfoRegla nivelAgrupacion(Object nivelAgrupacion) {
    this.nivelAgrupacion = nivelAgrupacion;
    return this;
  }

  /**
   * Nivel hasta el cual se visualizarán las ventas acumuladas para la aplicación de precios dinámicos
   * @return nivelAgrupacion
  **/
  @ApiModelProperty(value = "Nivel hasta el cual se visualizarán las ventas acumuladas para la aplicación de precios dinámicos")


  public Object getNivelAgrupacion() {
    return nivelAgrupacion;
  }

  public void setNivelAgrupacion(Object nivelAgrupacion) {
    this.nivelAgrupacion = nivelAgrupacion;
  }

  public InfoRegla reglasDescuento(ReglasDescuentoVO reglasDescuento) {
    this.reglasDescuento = reglasDescuento;
    return this;
  }

  /**
   * Get reglasDescuento
   * @return reglasDescuento
  **/
  @ApiModelProperty(value = "")


  public Object getReglasDescuento() {
    return reglasDescuento;
  }

  public void setReglasDescuento(ReglasDescuentoVO reglasDescuento) {
    this.reglasDescuento = reglasDescuento;
  }

  public InfoRegla candadoInferior(Object candadoInferior) {
    this.candadoInferior = candadoInferior;
    return this;
  }

  /**
   * Get candadoInferior
   * @return candadoInferior
  **/
  @ApiModelProperty(value = "")


  public Object getCandadoInferior() {
    return candadoInferior;
  }

  public void setCandadoInferior(Object candadoInferior) {
    this.candadoInferior = candadoInferior;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoRegla infoRegla = (InfoRegla) o;
    return Objects.equals(this.nivelAgrupacion, infoRegla.nivelAgrupacion) &&
      //  Objects.equals(this.reglasDescuento, infoRegla.reglasDescuento) &&
        Objects.equals(this.candadoInferior, infoRegla.candadoInferior) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nivelAgrupacion, reglasDescuento, candadoInferior, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoRegla {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    nivelAgrupacion: ").append(toIndentedString(nivelAgrupacion)).append("\n");
   // sb.append("    reglasDescuento: ").append(toIndentedString(reglasDescuento)).append("\n");
    sb.append("    candadoInferior: ").append(toIndentedString(candadoInferior)).append("\n");
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

