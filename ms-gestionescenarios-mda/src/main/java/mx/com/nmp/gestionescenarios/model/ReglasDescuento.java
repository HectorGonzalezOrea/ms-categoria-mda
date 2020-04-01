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
 * Información correspondiente a las reglas de descuento
 */
@ApiModel(description = "Información correspondiente a las reglas de descuento")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class ReglasDescuento   {
  @JsonProperty("primerBaseAjuste")
  private Object primerBaseAjuste = null;

  @JsonProperty("segundaBaseAjuste")
  private Object segundaBaseAjuste = null;

  @JsonProperty("factorPrecioFinal")
  private Integer factorPrecioFinal = null;

  @JsonProperty("criterio")
  private Object criterio = null;

  public ReglasDescuento primerBaseAjuste(Object primerBaseAjuste) {
    this.primerBaseAjuste = primerBaseAjuste;
    return this;
  }

  /**
   * Especificacion de las primeras reglas de ajuste
   * @return primerBaseAjuste
  **/
  @ApiModelProperty(value = "Especificacion de las primeras reglas de ajuste")


  public Object getPrimerBaseAjuste() {
    return primerBaseAjuste;
  }

  public void setPrimerBaseAjuste(Object primerBaseAjuste) {
    this.primerBaseAjuste = primerBaseAjuste;
  }

  public ReglasDescuento segundaBaseAjuste(Object segundaBaseAjuste) {
    this.segundaBaseAjuste = segundaBaseAjuste;
    return this;
  }

  /**
   * Especificacion de las segundas reglas de ajuste
   * @return segundaBaseAjuste
  **/
  @ApiModelProperty(value = "Especificacion de las segundas reglas de ajuste")


  public Object getSegundaBaseAjuste() {
    return segundaBaseAjuste;
  }

  public void setSegundaBaseAjuste(Object segundaBaseAjuste) {
    this.segundaBaseAjuste = segundaBaseAjuste;
  }

  public ReglasDescuento factorPrecioFinal(Integer factorPrecioFinal) {
    this.factorPrecioFinal = factorPrecioFinal;
    return this;
  }

  /**
   * Valor de ajuste que se puede aplicar después de las reglas para modificar el precio final
   * @return factorPrecioFinal
  **/
  @ApiModelProperty(example = "100", value = "Valor de ajuste que se puede aplicar después de las reglas para modificar el precio final")


  public Integer getFactorPrecioFinal() {
    return factorPrecioFinal;
  }

  public void setFactorPrecioFinal(Integer factorPrecioFinal) {
    this.factorPrecioFinal = factorPrecioFinal;
  }

  public ReglasDescuento criterio(Object criterio) {
    this.criterio = criterio;
    return this;
  }

  /**
   * Cuando hay varios cálculos en la regla de descuento con éste dato se selecciona el tomar el número mayor o menor
   * @return criterio
  **/
  @ApiModelProperty(value = "Cuando hay varios cálculos en la regla de descuento con éste dato se selecciona el tomar el número mayor o menor")


  public Object getCriterio() {
    return criterio;
  }

  public void setCriterio(Object criterio) {
    this.criterio = criterio;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReglasDescuento reglasDescuento = (ReglasDescuento) o;
    return Objects.equals(this.primerBaseAjuste, reglasDescuento.primerBaseAjuste) &&
        Objects.equals(this.segundaBaseAjuste, reglasDescuento.segundaBaseAjuste) &&
        Objects.equals(this.factorPrecioFinal, reglasDescuento.factorPrecioFinal) &&
        Objects.equals(this.criterio, reglasDescuento.criterio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primerBaseAjuste, segundaBaseAjuste, factorPrecioFinal, criterio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReglasDescuento {\n");
    
    sb.append("    primerBaseAjuste: ").append(toIndentedString(primerBaseAjuste)).append("\n");
    sb.append("    segundaBaseAjuste: ").append(toIndentedString(segundaBaseAjuste)).append("\n");
    sb.append("    factorPrecioFinal: ").append(toIndentedString(factorPrecioFinal)).append("\n");
    sb.append("    criterio: ").append(toIndentedString(criterio)).append("\n");
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

