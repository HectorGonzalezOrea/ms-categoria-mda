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
 * InfoProducto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

public class InfoProducto   {
  @JsonProperty("idProducto")
  private Integer idProducto = null;

  @JsonProperty("ubicacionActual")
  private String ubicacionActual = null;

  @JsonProperty("folio_Sku")
  private Integer folioSku = null;

  @JsonProperty("prestamoCosto")
  private Float prestamoCosto = null;

  @JsonProperty("precioFinal")
  private Float precioFinal = null;

  public InfoProducto idProducto(Integer idProducto) {
    this.idProducto = idProducto;
    return this;
  }

  /**
   * Consecutivo para tener un conteo del número de partidas/productos a ajustar.
   * @return idProducto
  **/
  @ApiModelProperty(value = "Consecutivo para tener un conteo del número de partidas/productos a ajustar.")


  public Integer getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Integer idProducto) {
    this.idProducto = idProducto;
  }

  public InfoProducto ubicacionActual(String ubicacionActual) {
    this.ubicacionActual = ubicacionActual;
    return this;
  }

  /**
   * Hace referencia a la sucursal actual de la partida, o al canal de venta alterno en caso de Compra Cumplido.
   * @return ubicacionActual
  **/
  @ApiModelProperty(value = "Hace referencia a la sucursal actual de la partida, o al canal de venta alterno en caso de Compra Cumplido.")


  public String getUbicacionActual() {
    return ubicacionActual;
  }

  public void setUbicacionActual(String ubicacionActual) {
    this.ubicacionActual = ubicacionActual;
  }

  public InfoProducto folioSku(Integer folioSku) {
    this.folioSku = folioSku;
    return this;
  }

  /**
   * Es el número de la partida para canales de ingreso diferente a Compra Cumplido, o el SKU para Compra Cumplido.
   * @return folioSku
  **/
  @ApiModelProperty(value = "Es el número de la partida para canales de ingreso diferente a Compra Cumplido, o el SKU para Compra Cumplido.")


  public Integer getFolioSku() {
    return folioSku;
  }

  public void setFolioSku(Integer folioSku) {
    this.folioSku = folioSku;
  }

  public InfoProducto prestamoCosto(Float prestamoCosto) {
    this.prestamoCosto = prestamoCosto;
    return this;
  }

  /**
   * Es el importe prestado al cliente en el caso de canales de ingreso distintos a Compra Cumplido, o el importe al cual NMP compró las partidas para Compra Cumplido.
   * @return prestamoCosto
  **/
  @ApiModelProperty(value = "Es el importe prestado al cliente en el caso de canales de ingreso distintos a Compra Cumplido, o el importe al cual NMP compró las partidas para Compra Cumplido.")


  public Float getPrestamoCosto() {
    return prestamoCosto;
  }

  public void setPrestamoCosto(Float prestamoCosto) {
    this.prestamoCosto = prestamoCosto;
  }

  public InfoProducto precioFinal(Float precioFinal) {
    this.precioFinal = precioFinal;
    return this;
  }

  /**
   * Precio de venta al dia de consulta.
   * @return precioFinal
  **/
  @ApiModelProperty(value = "Precio de venta al dia de consulta.")


  public Float getPrecioFinal() {
    return precioFinal;
  }

  public void setPrecioFinal(Float precioFinal) {
    this.precioFinal = precioFinal;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoProducto infoProducto = (InfoProducto) o;
    return Objects.equals(this.idProducto, infoProducto.idProducto) &&
        Objects.equals(this.ubicacionActual, infoProducto.ubicacionActual) &&
        Objects.equals(this.folioSku, infoProducto.folioSku) &&
        Objects.equals(this.prestamoCosto, infoProducto.prestamoCosto) &&
        Objects.equals(this.precioFinal, infoProducto.precioFinal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idProducto, ubicacionActual, folioSku, prestamoCosto, precioFinal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoProducto {\n");
    
    sb.append("    idProducto: ").append(toIndentedString(idProducto)).append("\n");
    sb.append("    ubicacionActual: ").append(toIndentedString(ubicacionActual)).append("\n");
    sb.append("    folioSku: ").append(toIndentedString(folioSku)).append("\n");
    sb.append("    prestamoCosto: ").append(toIndentedString(prestamoCosto)).append("\n");
    sb.append("    precioFinal: ").append(toIndentedString(precioFinal)).append("\n");
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

