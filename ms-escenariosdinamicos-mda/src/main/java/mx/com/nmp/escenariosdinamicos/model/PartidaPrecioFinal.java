package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * PartidaPrecioFinal
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class PartidaPrecioFinal   {
  @JsonProperty("idPartida")
  private String idPartida = null;

  @JsonProperty("SKU")
  private String sku = null;

  @JsonProperty("escenario")
  private String escenario = null;

  @JsonProperty("valorMonteActualizado")
  private Double precioFinal = null;

  public PartidaPrecioFinal idPartida(String idPartida) {
    this.idPartida = idPartida;
    return this;
  }

  /**
   * Identificador de la partida
   * @return idPartida
  **/
  @ApiModelProperty(value = "Identificador de la partida")


  public String getIdPartida() {
    return idPartida;
  }

  public void setIdPartida(String idPartida) {
    this.idPartida = idPartida;
  }

  public PartidaPrecioFinal sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Stock keeping unit
   * @return sku
  **/
  @ApiModelProperty(value = "Stock keeping unit")


  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public PartidaPrecioFinal escenario(String escenario) {
    this.escenario = escenario;
    return this;
  }

  /**
   * Escenario y regla ejecutada para la partida
   * @return escenario
  **/
  @ApiModelProperty(value = "Escenario y regla ejecutada para la partida")


  public String getEscenario() {
    return escenario;
  }

  public void setEscenario(String escenario) {
    this.escenario = escenario;
  }

  public PartidaPrecioFinal precioFinal(Double precioFinal) {
    this.precioFinal = precioFinal;
    return this;
  }

  /**
   * Precio final resultado de la ejecución de las reglas de escenario dinámico
   * @return precioFinal
  **/
  @ApiModelProperty(value = "Precio final resultado de la ejecución de las reglas de escenario dinámico")


  public Double getPrecioFinal() {
    return precioFinal;
  }

  public void setPrecioFinal(Double precioFinal) {
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
    PartidaPrecioFinal partidaPrecioFinal = (PartidaPrecioFinal) o;
    return Objects.equals(this.idPartida, partidaPrecioFinal.idPartida) &&
        Objects.equals(this.sku, partidaPrecioFinal.sku) &&
        Objects.equals(this.escenario, partidaPrecioFinal.escenario) &&
        Objects.equals(this.precioFinal, partidaPrecioFinal.precioFinal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPartida, sku, escenario, precioFinal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartidaPrecioFinal {\n");
    
    sb.append("    idPartida: ").append(toIndentedString(idPartida)).append("\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    escenario: ").append(toIndentedString(escenario)).append("\n");
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

