package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Rango de días en almoneda
 */
@ApiModel(description = "Rango de días en almoneda")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class DiasAlmoneda   {
  @JsonProperty("rangoMinimo")
  private Integer rangoMinimo = null;

  @JsonProperty("rangoMaximo")
  private Integer rangoMaximo = null;

  public DiasAlmoneda rangoMinimo(Integer rangoMinimo) {
    this.rangoMinimo = rangoMinimo;
    return this;
  }

  /**
   * Rango mínimo
   * @return rangoMinimo
  **/
  @ApiModelProperty(example = "11", value = "Rango mínimo")


  public Integer getRangoMinimo() {
    return rangoMinimo;
  }

  public void setRangoMinimo(Integer rangoMinimo) {
    this.rangoMinimo = rangoMinimo;
  }

  public DiasAlmoneda rangoMaximo(Integer rangoMaximo) {
    this.rangoMaximo = rangoMaximo;
    return this;
  }

  /**
   * Rango máximo
   * @return rangoMaximo
  **/
  @ApiModelProperty(example = "11", value = "Rango máximo")


  public Integer getRangoMaximo() {
    return rangoMaximo;
  }

  public void setRangoMaximo(Integer rangoMaximo) {
    this.rangoMaximo = rangoMaximo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiasAlmoneda diasAlmoneda = (DiasAlmoneda) o;
    return Objects.equals(this.rangoMinimo, diasAlmoneda.rangoMinimo) &&
        Objects.equals(this.rangoMaximo, diasAlmoneda.rangoMaximo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rangoMinimo, rangoMaximo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiasAlmoneda {\n");
    
    sb.append("    rangoMinimo: ").append(toIndentedString(rangoMinimo)).append("\n");
    sb.append("    rangoMaximo: ").append(toIndentedString(rangoMaximo)).append("\n");
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

