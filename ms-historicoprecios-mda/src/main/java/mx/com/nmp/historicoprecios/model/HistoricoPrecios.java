package mx.com.nmp.historicoprecios.model;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Información de histórico de precios
 */
@ApiModel(description = "Información de histórico de precios")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-19T17:57:16.669Z")

public class HistoricoPrecios   {
  @JsonProperty("sku")
  private String sku = null;

  @JsonProperty("folioPartida")
  private String folioPartida = null;

  @JsonProperty("precioActual")
  private Float precioActual = null;

  @JsonProperty("precioModificado")
  private Float precioModificado = null;

  @JsonProperty("fecha")
  private String fecha = null;

  public HistoricoPrecios sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Identificador comercial de la partida
   * @return sku
  **/
  @ApiModelProperty(example = "NMP_AL_AL_123314", value = "Identificador comercial de la partida")


  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public HistoricoPrecios folioPartida(String folioPartida) {
    this.folioPartida = folioPartida;
    return this;
  }

  /**
   * Identificador de la partida
   * @return folioPartida
  **/
  @ApiModelProperty(example = "231432", value = "Identificador de la partida")


  public String getFolioPartida() {
    return folioPartida;
  }

  public void setFolioPartida(String folioPartida) {
    this.folioPartida = folioPartida;
  }

  public HistoricoPrecios precioActual(Float precioActual) {
    this.precioActual = precioActual;
    return this;
  }

  /**
   * Precio actual de la partida
   * @return precioActual
  **/
  @ApiModelProperty(example = "157.19", value = "Precio actual de la partida")


  public Float getPrecioActual() {
    return precioActual;
  }

  public void setPrecioActual(Float precioActual) {
    this.precioActual = precioActual;
  }

  public HistoricoPrecios precioModificado(Float precioModificado) {
    this.precioModificado = precioModificado;
    return this;
  }

  /**
   * Precio modificado de la partida
   * @return precioModificado
  **/
  @ApiModelProperty(example = "190.67", value = "Precio modificado de la partida")


  public Float getPrecioModificado() {
    return precioModificado;
  }

  public void setPrecioModificado(Float precioModificado) {
    this.precioModificado = precioModificado;
  }

  public HistoricoPrecios fecha(String fecha) {
    this.fecha = fecha;
    return this;
  }

  /**
   * Fecha de registro
   * @return fecha
  **/
  @ApiModelProperty(example = "2020-01-09 13:32:56.776", value = "Fecha de registro")

  @Valid

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
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
    HistoricoPrecios historicoPrecios = (HistoricoPrecios) o;
    return Objects.equals(this.sku, historicoPrecios.sku) &&
        Objects.equals(this.folioPartida, historicoPrecios.folioPartida) &&
        Objects.equals(this.precioActual, historicoPrecios.precioActual) &&
        Objects.equals(this.precioModificado, historicoPrecios.precioModificado) &&
        Objects.equals(this.fecha, historicoPrecios.fecha);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, folioPartida, precioActual, precioModificado, fecha);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HistoricoPrecios {\n");
    
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    folioPartida: ").append(toIndentedString(folioPartida)).append("\n");
    sb.append("    precioActual: ").append(toIndentedString(precioActual)).append("\n");
    sb.append("    precioModificado: ").append(toIndentedString(precioModificado)).append("\n");
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

