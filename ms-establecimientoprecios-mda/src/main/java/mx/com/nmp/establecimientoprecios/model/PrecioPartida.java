package mx.com.nmp.establecimientoprecios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * Información del precio de la partida
 */
@ApiModel(description = "Información del precio de la partida")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-03T00:30:35.505Z")

public class PrecioPartida   {
  @JsonProperty("folioPartida")
  private String folioPartida = null;

  @JsonProperty("sku")
  private String sku = null;

  @JsonProperty("escenario")
  private String escenario = null;

  @JsonProperty("precioActual")
  private Float precioActual = null;

  @JsonProperty("precioModificado")
  private Float precioModificado = null;

  @JsonProperty("enLinea")
  private Boolean enLinea = null;

  public PrecioPartida folioPartida(String folioPartida) {
    this.folioPartida = folioPartida;
    return this;
  }

  /**
   * Folio que identifica a la partida
   * @return folioPartida
  **/
  @ApiModelProperty(example = "124342", value = "Folio que identifica a la partida")


  public String getFolioPartida() {
    return folioPartida;
  }

  public void setFolioPartida(String folioPartida) {
    this.folioPartida = folioPartida;
  }

  public PrecioPartida sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * SKU de la partida
   * @return sku
  **/
  @ApiModelProperty(example = "21341", value = "SKU de la partida")


  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public PrecioPartida escenario(String escenario) {
    this.escenario = escenario;
    return this;
  }

  /**
   * Escenario para el ajuste de precio
   * @return escenario
  **/
  @ApiModelProperty(example = "PA - Esc6", value = "Escenario para el ajuste de precio")


  public String getEscenario() {
    return escenario;
  }

  public void setEscenario(String escenario) {
    this.escenario = escenario;
  }

  public PrecioPartida precioActual(Float precioActual) {
    this.precioActual = precioActual;
    return this;
  }

  /**
   * Precio actual de la partida
   * @return precioActual
  **/
  @ApiModelProperty(example = "120.5", value = "Precio actual de la partida")


  public Float getPrecioActual() {
    return precioActual;
  }

  public void setPrecioActual(Float precioActual) {
    this.precioActual = precioActual;
  }

  public PrecioPartida precioModificado(Float precioModificado) {
    this.precioModificado = precioModificado;
    return this;
  }

  /**
   * Precio modificado de la partida
   * @return precioModificado
  **/
  @ApiModelProperty(example = "140.5", value = "Precio modificado de la partida")


  public Float getPrecioModificado() {
    return precioModificado;
  }

  public void setPrecioModificado(Float precioModificado) {
    this.precioModificado = precioModificado;
  }

  public PrecioPartida enLinea(Boolean enLinea) {
    this.enLinea = enLinea;
    return this;
  }

  /**
   * Flag para indicar si la actualización de precios se realizará en línea
   * @return enLinea
  **/
  @ApiModelProperty(example = "true", value = "Flag para indicar si la actualización de precios se realizará en línea")


  public Boolean isEnLinea() {
    return enLinea;
  }

  public void setEnLinea(Boolean enLinea) {
    this.enLinea = enLinea;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrecioPartida precioPartida = (PrecioPartida) o;
    return Objects.equals(this.folioPartida, precioPartida.folioPartida) &&
        Objects.equals(this.sku, precioPartida.sku) &&
        Objects.equals(this.escenario, precioPartida.escenario) &&
        Objects.equals(this.precioActual, precioPartida.precioActual) &&
        Objects.equals(this.precioModificado, precioPartida.precioModificado) &&
        Objects.equals(this.enLinea, precioPartida.enLinea);
  }

  @Override
  public int hashCode() {
    return Objects.hash(folioPartida, sku, escenario, precioActual, precioModificado, enLinea);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PrecioPartida {\n");
    
    sb.append("    folioPartida: ").append(toIndentedString(folioPartida)).append("\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    escenario: ").append(toIndentedString(escenario)).append("\n");
    sb.append("    precioActual: ").append(toIndentedString(precioActual)).append("\n");
    sb.append("    precioModificado: ").append(toIndentedString(precioModificado)).append("\n");
    sb.append("    enLinea: ").append(toIndentedString(enLinea)).append("\n");
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

