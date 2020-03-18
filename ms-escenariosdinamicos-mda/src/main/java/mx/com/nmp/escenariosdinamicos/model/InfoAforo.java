package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Información del Aforo mínimo y máximo
 */
@ApiModel(description = "Información del Aforo mínimo y máximo")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class InfoAforo   {
  @JsonProperty("minimo")
  private Integer minimo = null;

  @JsonProperty("maximo")
  private Integer maximo = null;

  public InfoAforo minimo(Integer minimo) {
    this.minimo = minimo;
    return this;
  }

  /**
   * Aforo mínimo
   * @return minimo
  **/
  @ApiModelProperty(example = "2", value = "Aforo mínimo")


  public Integer getMinimo() {
    return minimo;
  }

  public void setMinimo(Integer minimo) {
    this.minimo = minimo;
  }

  public InfoAforo maximo(Integer maximo) {
    this.maximo = maximo;
    return this;
  }

  /**
   * Aforo máximo
   * @return maximo
  **/
  @ApiModelProperty(example = "100", value = "Aforo máximo")


  public Integer getMaximo() {
    return maximo;
  }

  public void setMaximo(Integer maximo) {
    this.maximo = maximo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoAforo infoAforo = (InfoAforo) o;
    return Objects.equals(this.minimo, infoAforo.minimo) &&
        Objects.equals(this.maximo, infoAforo.maximo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minimo, maximo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoAforo {\n");
    
    sb.append("    minimo: ").append(toIndentedString(minimo)).append("\n");
    sb.append("    maximo: ").append(toIndentedString(maximo)).append("\n");
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

