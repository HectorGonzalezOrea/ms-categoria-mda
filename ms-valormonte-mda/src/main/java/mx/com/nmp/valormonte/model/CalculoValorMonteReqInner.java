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
 * CalculoValorMonteReqInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-30T17:34:52.695Z")

public class CalculoValorMonteReqInner   {
  @JsonProperty("idPartida")
  private Integer idPartida = null;

  @JsonProperty("SKU")
  private Integer SKU = null;

  @JsonProperty("valorAncla")
  private Float valorAncla = null;

  @JsonProperty("gramaje")
  private Float gramaje = null;

  @JsonProperty("kilataje")
  private Float kilataje = null;

  @JsonProperty("incremento")
  private Float incremento = null;

  @JsonProperty("desplazamiento")
  private Float desplazamiento = null;

  @JsonProperty("avaluoComplementario")
  private Float avaluoComplementario = null;

  public CalculoValorMonteReqInner idPartida(Integer idPartida) {
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

  public CalculoValorMonteReqInner SKU(Integer SKU) {
    this.SKU = SKU;
    return this;
  }

  /**
   * Identificador comercial de la partida.
   * @return SKU
  **/
  @ApiModelProperty(example = "4321", value = "Identificador comercial de la partida.")


  public Integer getSKU() {
    return SKU;
  }

  public void setSKU(Integer SKU) {
    this.SKU = SKU;
  }

  public CalculoValorMonteReqInner valorAncla(Float valorAncla) {
    this.valorAncla = valorAncla;
    return this;
  }

  /**
   * Valor de referencia en pesos de 1 gramo de oro de 24k.
   * @return valorAncla
  **/
  @ApiModelProperty(example = "1234.23", value = "Valor de referencia en pesos de 1 gramo de oro de 24k.")


  public Float getValorAncla() {
    return valorAncla;
  }

  public void setValorAncla(Float valorAncla) {
    this.valorAncla = valorAncla;
  }

  public CalculoValorMonteReqInner gramaje(Float gramaje) {
    this.gramaje = gramaje;
    return this;
  }

  /**
   * Gramos que pesa la pieza.
   * @return gramaje
  **/
  @ApiModelProperty(example = "32.21", value = "Gramos que pesa la pieza.")


  public Float getGramaje() {
    return gramaje;
  }

  public void setGramaje(Float gramaje) {
    this.gramaje = gramaje;
  }

  public CalculoValorMonteReqInner kilataje(Float kilataje) {
    this.kilataje = kilataje;
    return this;
  }

  /**
   * Numero de quilates de la prenda.
   * @return kilataje
  **/
  @ApiModelProperty(example = "12.34", value = "Numero de quilates de la prenda.")


  public Float getKilataje() {
    return kilataje;
  }

  public void setKilataje(Float kilataje) {
    this.kilataje = kilataje;
  }

  public CalculoValorMonteReqInner incremento(Float incremento) {
    this.incremento = incremento;
    return this;
  }

  /**
   * Se refiere a los incrementos porcentuales que se aplican al valor metal, con base al valor ancla, kilataje y gramos.
   * @return incremento
  **/
  @ApiModelProperty(example = "15.26", value = "Se refiere a los incrementos porcentuales que se aplican al valor metal, con base al valor ancla, kilataje y gramos.")


  public Float getIncremento() {
    return incremento;
  }

  public void setIncremento(Float incremento) {
    this.incremento = incremento;
  }

  public CalculoValorMonteReqInner desplazamiento(Float desplazamiento) {
    this.desplazamiento = desplazamiento;
    return this;
  }

  /**
   * Indica la movilidad que tiene la prenda una vez que pasa a comercialización.
   * @return desplazamiento
  **/
  @ApiModelProperty(example = "6.11", value = "Indica la movilidad que tiene la prenda una vez que pasa a comercialización.")


  public Float getDesplazamiento() {
    return desplazamiento;
  }

  public void setDesplazamiento(Float desplazamiento) {
    this.desplazamiento = desplazamiento;
  }

  public CalculoValorMonteReqInner avaluoComplementario(Float avaluoComplementario) {
    this.avaluoComplementario = avaluoComplementario;
    return this;
  }

  /**
   * Estimación de un valor monetario de un artículo, inmueble, joya el cual está a consideración de una valuador, el cual puede incrementar el valor del préstamo original de la prenda.
   * @return avaluoComplementario
  **/
  @ApiModelProperty(example = "27.12", value = "Estimación de un valor monetario de un artículo, inmueble, joya el cual está a consideración de una valuador, el cual puede incrementar el valor del préstamo original de la prenda.")


  public Float getAvaluoComplementario() {
    return avaluoComplementario;
  }

  public void setAvaluoComplementario(Float avaluoComplementario) {
    this.avaluoComplementario = avaluoComplementario;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CalculoValorMonteReqInner calculoValorMonteReqInner = (CalculoValorMonteReqInner) o;
    return Objects.equals(this.idPartida, calculoValorMonteReqInner.idPartida) &&
        Objects.equals(this.SKU, calculoValorMonteReqInner.SKU) &&
        Objects.equals(this.valorAncla, calculoValorMonteReqInner.valorAncla) &&
        Objects.equals(this.gramaje, calculoValorMonteReqInner.gramaje) &&
        Objects.equals(this.kilataje, calculoValorMonteReqInner.kilataje) &&
        Objects.equals(this.incremento, calculoValorMonteReqInner.incremento) &&
        Objects.equals(this.desplazamiento, calculoValorMonteReqInner.desplazamiento) &&
        Objects.equals(this.avaluoComplementario, calculoValorMonteReqInner.avaluoComplementario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPartida, SKU, valorAncla, gramaje, kilataje, incremento, desplazamiento, avaluoComplementario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalculoValorMonteReqInner {\n");
    
    sb.append("    idPartida: ").append(toIndentedString(idPartida)).append("\n");
    sb.append("    SKU: ").append(toIndentedString(SKU)).append("\n");
    sb.append("    valorAncla: ").append(toIndentedString(valorAncla)).append("\n");
    sb.append("    gramaje: ").append(toIndentedString(gramaje)).append("\n");
    sb.append("    kilataje: ").append(toIndentedString(kilataje)).append("\n");
    sb.append("    incremento: ").append(toIndentedString(incremento)).append("\n");
    sb.append("    desplazamiento: ").append(toIndentedString(desplazamiento)).append("\n");
    sb.append("    avaluoComplementario: ").append(toIndentedString(avaluoComplementario)).append("\n");
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

