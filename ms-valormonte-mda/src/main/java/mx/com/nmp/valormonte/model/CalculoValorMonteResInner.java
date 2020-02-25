package mx.com.nmp.valormonte.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CalculoValorMonteResInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-30T17:34:52.695Z")

public class CalculoValorMonteResInner   {
  @JsonProperty("idPartida")
  private Integer idPartida = null;

  @JsonProperty("SKU")
  private String sku = null;

  @JsonProperty("valorMonteActualizado")
  private Float valorMonteActualizado = null;

  public CalculoValorMonteResInner idPartida(Integer idPartida) {
    this.idPartida = idPartida;
    return this;
  }

  /**
   * Numero de la partida.
   * @return idPartida
  **/
  @ApiModelProperty(example = "1234", value = "Numero de la partida.")


  public Integer getIdPartida() {
    return idPartida;
  }

  public void setIdPartida(Integer idPartida) {
    this.idPartida = idPartida;
  }

  public CalculoValorMonteResInner sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Identificador comercial de la partida.
   * @return SKU
  **/
  @ApiModelProperty(example = "4321", value = "Identificador comercial de la partida.")


  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public CalculoValorMonteResInner valorMonteActualizado(Float valorMonteActualizado) {
    this.valorMonteActualizado = valorMonteActualizado;
    return this;
  }

  /**
   * Resultado del cálculo con la fórmula de MIDAS.
   * @return valorMonteActualizado
  **/
  @ApiModelProperty(example = "456.72", value = "Resultado del cálculo con la fórmula de MIDAS.")


  public Float getValorMonteActualizado() {
    return valorMonteActualizado;
  }

  public void setValorMonteActualizado(Float valorMonteActualizado) {
    this.valorMonteActualizado = valorMonteActualizado;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CalculoValorMonteResInner calculoValorMonteResInner = (CalculoValorMonteResInner) o;
    return Objects.equals(this.idPartida, calculoValorMonteResInner.idPartida) &&
        Objects.equals(this.sku, calculoValorMonteResInner.sku) &&
        Objects.equals(this.valorMonteActualizado, calculoValorMonteResInner.valorMonteActualizado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPartida, sku, valorMonteActualizado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalculoValorMonteResInner {\n");
    
    sb.append("    idPartida: ").append(toIndentedString(idPartida)).append("\n");
    sb.append("    SKU: ").append(toIndentedString(sku)).append("\n");
    sb.append("    valorMonteActualizado: ").append(toIndentedString(valorMonteActualizado)).append("\n");
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

