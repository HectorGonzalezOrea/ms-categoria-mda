package mx.com.nmp.gestionbolsas.model;


import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * ListaTipoBolsasInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-21T17:43:22.226Z")

public class ListaTipoBolsasInner {
  @JsonProperty("tipo")
  private TipoBolsa tipo = null;

  public ListaTipoBolsasInner tipo() {
    this.tipo = tipo;
    return this;
  }

  /**
   * Tipo de Bolsa
   * @return tipo
  **/
  @ApiModelProperty(value = "Tipo de Bolsa")


  public Object getTipo() {
    return tipo;
  }

  public void setTipo(TipoBolsa tipo) {
    this.tipo = tipo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListaTipoBolsasInner listaTipoBolsasInner = (ListaTipoBolsasInner) o;
    return Objects.equals(this.tipo, listaTipoBolsasInner.tipo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tipo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaTipoBolsasInner {\n");
    
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
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

