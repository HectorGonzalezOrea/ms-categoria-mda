package mx.com.nmp.gestionescenarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Información de los valores Ancla y Dolar
 */
@ApiModel(description = "Información de los valores Ancla y Dolar")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class ValorAnclaOroDolar   {
  @JsonProperty("valorAnclaOro")
  private Float valorAnclaOro = null;

  @JsonProperty("valorAnclaDolar")
  private Float valorAnclaDolar = null;

  public ValorAnclaOroDolar valorAnclaOro(Float valorAnclaOro) {
    this.valorAnclaOro = valorAnclaOro;
    return this;
  }

  /**
   * Valor Ancla Oro
   * @return valorAnclaOro
  **/
  @ApiModelProperty(example = "800.02", value = "Valor Ancla Oro")


  public Float getValorAnclaOro() {
    return valorAnclaOro;
  }

  public void setValorAnclaOro(Float valorAnclaOro) {
    this.valorAnclaOro = valorAnclaOro;
  }

  public ValorAnclaOroDolar valorAnclaDolar(Float valorAnclaDolar) {
    this.valorAnclaDolar = valorAnclaDolar;
    return this;
  }

  /**
   * Valor Ancla Dolar
   * @return valorAnclaDolar
  **/
  @ApiModelProperty(example = "19.4523", value = "Valor Ancla Dolar")


  public Float getValorAnclaDolar() {
    return valorAnclaDolar;
  }

  public void setValorAnclaDolar(Float valorAnclaDolar) {
    this.valorAnclaDolar = valorAnclaDolar;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValorAnclaOroDolar valorAnclaOroDolar = (ValorAnclaOroDolar) o;
    return Objects.equals(this.valorAnclaOro, valorAnclaOroDolar.valorAnclaOro) &&
        Objects.equals(this.valorAnclaDolar, valorAnclaOroDolar.valorAnclaDolar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valorAnclaOro, valorAnclaDolar);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValorAnclaOroDolar {\n");
    
    sb.append("    valorAnclaOro: ").append(toIndentedString(valorAnclaOro)).append("\n");
    sb.append("    valorAnclaDolar: ").append(toIndentedString(valorAnclaDolar)).append("\n");
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

