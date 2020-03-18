package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;

/**
 * ReglasAjusteInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class ReglasAjusteInner   {
  /**
   * Especificación del tipo de precio
   */
  public enum TipoPrecioEnum {
    PM("PM"),
    
    PA("PA"),
    
    PB("PB");

    private String value;

    TipoPrecioEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TipoPrecioEnum fromValue(String text) {
      for (TipoPrecioEnum b : TipoPrecioEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("tipoPrecio")
  private TipoPrecioEnum tipoPrecio = null;

  @JsonProperty("baseAjuste")
  private Object baseAjuste = null;

  @JsonProperty("factorAjuste")
  private Integer factorAjuste = null;

  public ReglasAjusteInner tipoPrecio(TipoPrecioEnum tipoPrecio) {
    this.tipoPrecio = tipoPrecio;
    return this;
  }

  /**
   * Especificación del tipo de precio
   * @return tipoPrecio
  **/
  @ApiModelProperty(example = "PM", value = "Especificación del tipo de precio")


  public TipoPrecioEnum getTipoPrecio() {
    return tipoPrecio;
  }

  public void setTipoPrecio(TipoPrecioEnum tipoPrecio) {
    this.tipoPrecio = tipoPrecio;
  }

  public ReglasAjusteInner baseAjuste(Object baseAjuste) {
    this.baseAjuste = baseAjuste;
    return this;
  }

  /**
   * Especificación de la base de ajuste
   * @return baseAjuste
  **/
  @ApiModelProperty(value = "Especificación de la base de ajuste")


  public Object getBaseAjuste() {
    return baseAjuste;
  }

  public void setBaseAjuste(Object baseAjuste) {
    this.baseAjuste = baseAjuste;
  }

  public ReglasAjusteInner factorAjuste(Integer factorAjuste) {
    this.factorAjuste = factorAjuste;
    return this;
  }

  /**
   * Especificación del factor de Ajuste
   * @return factorAjuste
  **/
  @ApiModelProperty(example = "100", value = "Especificación del factor de Ajuste")


  public Integer getFactorAjuste() {
    return factorAjuste;
  }

  public void setFactorAjuste(Integer factorAjuste) {
    this.factorAjuste = factorAjuste;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReglasAjusteInner reglasAjusteInner = (ReglasAjusteInner) o;
    return Objects.equals(this.tipoPrecio, reglasAjusteInner.tipoPrecio) &&
        Objects.equals(this.baseAjuste, reglasAjusteInner.baseAjuste) &&
        Objects.equals(this.factorAjuste, reglasAjusteInner.factorAjuste);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tipoPrecio, baseAjuste, factorAjuste);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReglasAjusteInner {\n");
    
    sb.append("    tipoPrecio: ").append(toIndentedString(tipoPrecio)).append("\n");
    sb.append("    baseAjuste: ").append(toIndentedString(baseAjuste)).append("\n");
    sb.append("    factorAjuste: ").append(toIndentedString(factorAjuste)).append("\n");
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

