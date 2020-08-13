package mx.com.nmp.gestionescenarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Información de configuración de la regla
 */
@ApiModel(description = "Información de configuración de la regla")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class InfoRegla extends InfoGeneralRegla  {
  @JsonProperty("compraCumplido")
  private Boolean compraCumplido = null;

  @JsonProperty("aforo")
  private Object aforo = null;

  @JsonProperty("estatusPartida")
  @Valid
  private List<Object> estatusPartida = null;

  @JsonProperty("monedas")
  @Valid
  private List<Object> monedas = null;

  @JsonProperty("canalIngresoActual")
  @Valid
  private List<Object> canalIngresoActual = null;

  @JsonProperty("diasAlmoneda")
  private Object diasAlmoneda = null;

  @JsonProperty("nivelAgrupacion")
  private Object nivelAgrupacion = null;

  @JsonProperty("reglasDescuento")
  private Object reglasDescuento = null;

  @JsonProperty("candadoInferior")
  private Object candadoInferior = null;

  public InfoRegla compraCumplido(Boolean compraCumplido) {
    this.compraCumplido = compraCumplido;
    return this;
  }

  /**
   * Atributo para indicar si el ajuste de precio es para productos Compra Cumplido
   * @return compraCumplido
  **/
  @ApiModelProperty(example = "true", value = "Atributo para indicar si el ajuste de precio es para productos Compra Cumplido")


  public Boolean isCompraCumplido() {
    return compraCumplido;
  }

  public void setCompraCumplido(Boolean compraCumplido) {
    this.compraCumplido = compraCumplido;
  }

  public InfoRegla aforo(Object aforo) {
    this.aforo = aforo;
    return this;
  }

  /**
   * Get aforo
   * @return aforo
  **/
  @ApiModelProperty(value = "")


  public Object getAforo() {
    return aforo;
  }

  public void setAforo(Object aforo) {
    this.aforo = aforo;
  }

  public InfoRegla estatusPartida(List<Object> estatusPartida) {
    this.estatusPartida = estatusPartida;
    return this;
  }

  public InfoRegla addEstatusPartidaItem(Object estatusPartidaItem) {
    if (this.estatusPartida == null) {
      this.estatusPartida = new ArrayList<Object>();
    }
    this.estatusPartida.add(estatusPartidaItem);
    return this;
  }

  /**
   * Estatus de la partida
   * @return estatusPartida
  **/
  @ApiModelProperty(value = "Estatus de la partida")


  public List<Object> getEstatusPartida() {
    return estatusPartida;
  }

  public void setEstatusPartida(List<Object> estatusPartida) {
    this.estatusPartida = estatusPartida;
  }

  public InfoRegla monedas(List<Object> monedas) {
    this.monedas = monedas;
    return this;
  }

  public InfoRegla addMonedasItem(Object monedasItem) {
    if (this.monedas == null) {
      this.monedas = new ArrayList<Object>();
    }
    this.monedas.add(monedasItem);
    return this;
  }

  /**
   * Monedas a contemplar en la regla
   * @return monedas
  **/
  @ApiModelProperty(value = "Monedas a contemplar en la regla")


  public List<Object> getMonedas() {
    return monedas;
  }

  public void setMonedas(List<Object> monedas) {
    this.monedas = monedas;
  }

  public InfoRegla canalIngresoActual(List<Object> canalIngresoActual) {
    this.canalIngresoActual = canalIngresoActual;
    return this;
  }

  public InfoRegla addCanalIngresoActualItem(Object canalIngresoActualItem) {
    if (this.canalIngresoActual == null) {
      this.canalIngresoActual = new ArrayList<Object>();
    }
    this.canalIngresoActual.add(canalIngresoActualItem);
    return this;
  }

  /**
   * Ubicación del producto según catálogo
   * @return canalIngresoActual
  **/
  @ApiModelProperty(value = "Ubicación del producto según catálogo")


  public List<Object> getCanalIngresoActual() {
    return canalIngresoActual;
  }

  public void setCanalIngresoActual(List<Object> canalIngresoActual) {
    this.canalIngresoActual = canalIngresoActual;
  }

  public InfoRegla diasAlmoneda(Object diasAlmoneda) {
    this.diasAlmoneda = diasAlmoneda;
    return this;
  }

  /**
   * Get diasAlmoneda
   * @return diasAlmoneda
  **/
  @ApiModelProperty(value = "")


  public Object getDiasAlmoneda() {
    return diasAlmoneda;
  }

  public void setDiasAlmoneda(Object diasAlmoneda) {
    this.diasAlmoneda = diasAlmoneda;
  }

  public InfoRegla nivelAgrupacion(Object nivelAgrupacion) {
    this.nivelAgrupacion = nivelAgrupacion;
    return this;
  }

  /**
   * Nivel hasta el cual se visualizarán las ventas acumuladas para la aplicación de precios dinámicos
   * @return nivelAgrupacion
  **/
  @ApiModelProperty(value = "Nivel hasta el cual se visualizarán las ventas acumuladas para la aplicación de precios dinámicos")


  public Object getNivelAgrupacion() {
    return nivelAgrupacion;
  }

  public void setNivelAgrupacion(Object nivelAgrupacion) {
    this.nivelAgrupacion = nivelAgrupacion;
  }

  public InfoRegla reglasDescuento(Object reglasDescuento) {
    this.reglasDescuento = reglasDescuento;
    return this;
  }

  /**
   * Get reglasDescuento
   * @return reglasDescuento
  **/
  @ApiModelProperty(value = "")


  public Object getReglasDescuento() {
    return reglasDescuento;
  }

  public void setReglasDescuento(Object reglasDescuento) {
    this.reglasDescuento = reglasDescuento;
  }

  public InfoRegla candadoInferior(Object candadoInferior) {
    this.candadoInferior = candadoInferior;
    return this;
  }

  /**
   * Get candadoInferior
   * @return candadoInferior
  **/
  @ApiModelProperty(value = "")


  public Object getCandadoInferior() {
    return candadoInferior;
  }

  public void setCandadoInferior(Object candadoInferior) {
    this.candadoInferior = candadoInferior;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoRegla infoRegla = (InfoRegla) o;
    return Objects.equals(this.compraCumplido, infoRegla.compraCumplido) &&
        Objects.equals(this.aforo, infoRegla.aforo) &&
        Objects.equals(this.estatusPartida, infoRegla.estatusPartida) &&
        Objects.equals(this.monedas, infoRegla.monedas) &&
        Objects.equals(this.canalIngresoActual, infoRegla.canalIngresoActual) &&
        Objects.equals(this.diasAlmoneda, infoRegla.diasAlmoneda) &&
        Objects.equals(this.nivelAgrupacion, infoRegla.nivelAgrupacion) &&
        Objects.equals(this.reglasDescuento, infoRegla.reglasDescuento) &&
        Objects.equals(this.candadoInferior, infoRegla.candadoInferior) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(compraCumplido, aforo, estatusPartida, monedas, canalIngresoActual, diasAlmoneda, nivelAgrupacion, reglasDescuento, candadoInferior, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoRegla {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    compraCumplido: ").append(toIndentedString(compraCumplido)).append("\n");
    sb.append("    aforo: ").append(toIndentedString(aforo)).append("\n");
    sb.append("    estatusPartida: ").append(toIndentedString(estatusPartida)).append("\n");
    sb.append("    monedas: ").append(toIndentedString(monedas)).append("\n");
    sb.append("    canalIngresoActual: ").append(toIndentedString(canalIngresoActual)).append("\n");
    sb.append("    diasAlmoneda: ").append(toIndentedString(diasAlmoneda)).append("\n");
    sb.append("    nivelAgrupacion: ").append(toIndentedString(nivelAgrupacion)).append("\n");
    sb.append("    reglasDescuento: ").append(toIndentedString(reglasDescuento)).append("\n");
    sb.append("    candadoInferior: ").append(toIndentedString(candadoInferior)).append("\n");
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

