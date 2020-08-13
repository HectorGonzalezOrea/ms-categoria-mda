package mx.com.nmp.escenariosdinamicos.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Información general de la regla
 */
@ApiModel(description = "Información general de la regla")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class InfoGeneralRegla   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("origen")
  private Object origen = null;

  @JsonProperty("ramo")
  private String ramo = null;

  @JsonProperty("subramo")
  @Valid
  private List<String> subramo = null;

  @JsonProperty("factor")
  @Valid
  private List<String> factor = null;

  @JsonProperty("clasificacionClientes")
  @Valid
  private List<String> clasificacionClientes = null;

  @JsonProperty("bolsas")
  @Valid
  private List<Object> bolsas = null;

  @JsonProperty("sucursales")
  @Valid
  private List<String> sucursales = null;

  @JsonProperty("canalComercializacion")
  @Valid
  private List<InfoGeneralReglaCanalComercializacion> canalComercializacion = null;

  @JsonProperty("compraCumplido")
  private Boolean compraCumplido = null;

  @JsonProperty("aforo")
  private Object aforo = null;

  @JsonProperty("estatusPartida")
  @Valid
  private List<Catalogo> estatusPartida = null;

  @JsonProperty("canalIngresoActual")
  @Valid
  private List<Catalogo> canalIngresoActual = null;

  @JsonProperty("diasAlmoneda")
  private Object diasAlmoneda = null;

  @JsonProperty("tipoMonedas")
  @Valid
  private List<Object> tipoMonedas = null;

  public InfoGeneralRegla id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Identificados de la regla
   * @return id
  **/
  @ApiModelProperty(example = "1", value = "Identificados de la regla")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public InfoGeneralRegla nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Nombre de la regla
   * @return nombre
  **/
  @ApiModelProperty(example = "Relojes R1 Plata", value = "Nombre de la regla")


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public InfoGeneralRegla origen(Object origen) {
    this.origen = origen;
    return this;
  }

  /**
   * Orgien de la partida
   * @return origen
  **/
  @ApiModelProperty(value = "Orgien de la partida")


  public Object getOrigen() {
    return origen;
  }

  public void setOrigen(Object origen) {
    this.origen = origen;
  }

  public InfoGeneralRegla ramo(String ramo) {
    this.ramo = ramo;
    return this;
  }

  /**
   * Ramo de la partida
   * @return ramo
  **/
  @ApiModelProperty(example = "AL", value = "Ramo de la partida")


  public String getRamo() {
    return ramo;
  }

  public void setRamo(String ramo) {
    this.ramo = ramo;
  }

  public InfoGeneralRegla subramo(List<String> subramo) {
    this.subramo = subramo;
    return this;
  }

  public InfoGeneralRegla addSubramoItem(String subramoItem) {
    if (this.subramo == null) {
      this.subramo = new ArrayList<String>();
    }
    this.subramo.add(subramoItem);
    return this;
  }

  /**
   * Subramo de la partida
   * @return subramo
  **/
  @ApiModelProperty(value = "Subramo de la partida")


  public List<String> getSubramo() {
    return subramo;
  }

  public void setSubramo(List<String> subramo) {
    this.subramo = subramo;
  }

  public InfoGeneralRegla factor(List<String> factor) {
    this.factor = factor;
    return this;
  }

  public InfoGeneralRegla addFactorItem(String factorItem) {
    if (this.factor == null) {
      this.factor = new ArrayList<String>();
    }
    this.factor.add(factorItem);
    return this;
  }

  /**
   * Factor de la partida
   * @return factor
  **/
  @ApiModelProperty(value = "Factor de la partida")


  public List<String> getFactor() {
    return factor;
  }

  public void setFactor(List<String> factor) {
    this.factor = factor;
  }

  public InfoGeneralRegla clasificacionClientes(List<String> clasificacionClientes) {
    this.clasificacionClientes = clasificacionClientes;
    return this;
  }

  public InfoGeneralRegla addClasificacionClientesItem(String clasificacionClientesItem) {
    if (this.clasificacionClientes == null) {
      this.clasificacionClientes = new ArrayList<String>();
    }
    this.clasificacionClientes.add(clasificacionClientesItem);
    return this;
  }

  /**
   * Clasificación de clientes
   * @return clasificacionClientes
  **/
  @ApiModelProperty(value = "Clasificación de clientes")


  public List<String> getClasificacionClientes() {
    return clasificacionClientes;
  }

  public void setClasificacionClientes(List<String> clasificacionClientes) {
    this.clasificacionClientes = clasificacionClientes;
  }

  public InfoGeneralRegla bolsas(List<Object> bolsas) {
    this.bolsas = bolsas;
    return this;
  }

  public InfoGeneralRegla addBolsasItem(Object bolsasItem) {
    if (this.bolsas == null) {
      this.bolsas = new ArrayList<Object>();
    }
    this.bolsas.add(bolsasItem);
    return this;
  }

  /**
   * Bolsas de sucursales
   * @return bolsas
  **/
  @ApiModelProperty(value = "Bolsas de sucursales")


  public List<Object> getBolsas() {
    return bolsas;
  }

  public void setBolsas(List<Object> bolsas) {
    this.bolsas = bolsas;
  }

  public InfoGeneralRegla sucursales(List<String> sucursales) {
    this.sucursales = sucursales;
    return this;
  }

  public InfoGeneralRegla addSucursalesItem(String sucursalesItem) {
    if (this.sucursales == null) {
      this.sucursales = new ArrayList<String>();
    }
    this.sucursales.add(sucursalesItem);
    return this;
  }

  /**
   * Lista de sucursales
   * @return sucursales
  **/
  @ApiModelProperty(value = "Lista de sucursales")


  public List<String> getSucursales() {
    return sucursales;
  }

  public void setSucursales(List<String> sucursales) {
    this.sucursales = sucursales;
  }

  public InfoGeneralRegla canalComercializacion(List<InfoGeneralReglaCanalComercializacion> canalComercializacion) {
    this.canalComercializacion = canalComercializacion;
    return this;
  }

  public InfoGeneralRegla addCanalComercializacionItem(InfoGeneralReglaCanalComercializacion canalComercializacionItem) {
    if (this.canalComercializacion == null) {
      this.canalComercializacion = new ArrayList<InfoGeneralReglaCanalComercializacion>();
    }
    this.canalComercializacion.add(canalComercializacionItem);
    return this;
  }

  /**
   * Canales de venta
   * @return canalComercializacion
  **/
  @ApiModelProperty(value = "Canales de venta")

  @Valid

  public List<InfoGeneralReglaCanalComercializacion> getCanalComercializacion() {
    return canalComercializacion;
  }

  public void setCanalComercializacion(List<InfoGeneralReglaCanalComercializacion> canalComercializacion) {
    this.canalComercializacion = canalComercializacion;
  }

  public InfoGeneralRegla compraCumplido(Boolean compraCumplido) {
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

  public InfoGeneralRegla aforo(Object aforo) {
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

  public InfoGeneralRegla estatusPartida(List<Catalogo> estatusPartida) {
    this.estatusPartida = estatusPartida;
    return this;
  }

  public InfoGeneralRegla addEstatusPartidaItem(Catalogo estatusPartidaItem) {
    if (this.estatusPartida == null) {
      this.estatusPartida = new ArrayList<Catalogo>();
    }
    this.estatusPartida.add(estatusPartidaItem);
    return this;
  }

  /**
   * Estatus de la partida
   * @return estatusPartida
  **/
  @ApiModelProperty(value = "Estatus de la partida")

  @Valid

  public List<Catalogo> getEstatusPartida() {
    return estatusPartida;
  }

  public void setEstatusPartida(List<Catalogo> estatusPartida) {
    this.estatusPartida = estatusPartida;
  }

  public InfoGeneralRegla canalIngresoActual(List<Catalogo> canalIngresoActual) {
    this.canalIngresoActual = canalIngresoActual;
    return this;
  }

  public InfoGeneralRegla addCanalIngresoActualItem(Catalogo canalIngresoActualItem) {
    if (this.canalIngresoActual == null) {
      this.canalIngresoActual = new ArrayList<Catalogo>();
    }
    this.canalIngresoActual.add(canalIngresoActualItem);
    return this;
  }

  /**
   * Ubicación del producto según catálogo
   * @return canalIngresoActual
  **/
  @ApiModelProperty(value = "Ubicación del producto según catálogo")

  @Valid

  public List<Catalogo> getCanalIngresoActual() {
    return canalIngresoActual;
  }

  public void setCanalIngresoActual(List<Catalogo> canalIngresoActual) {
    this.canalIngresoActual = canalIngresoActual;
  }

  public InfoGeneralRegla diasAlmoneda(Object diasAlmoneda) {
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

  public InfoGeneralRegla tipoMonedas(List<Object> tipoMonedas) {
    this.tipoMonedas = tipoMonedas;
    return this;
  }

  public InfoGeneralRegla addTipoMonedasItem(Object tipoMonedasItem) {
    if (this.tipoMonedas == null) {
      this.tipoMonedas = new ArrayList<Object>();
    }
    this.tipoMonedas.add(tipoMonedasItem);
    return this;
  }

  /**
   * Tipo de monedas
   * @return tipoMonedas
  **/
  @ApiModelProperty(value = "Tipo de monedas")


  public List<Object> getTipoMonedas() {
    return tipoMonedas;
  }

  public void setTipoMonedas(List<Object> tipoMonedas) {
    this.tipoMonedas = tipoMonedas;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoGeneralRegla infoGeneralRegla = (InfoGeneralRegla) o;
    return Objects.equals(this.id, infoGeneralRegla.id) &&
        Objects.equals(this.nombre, infoGeneralRegla.nombre) &&
        Objects.equals(this.origen, infoGeneralRegla.origen) &&
        Objects.equals(this.ramo, infoGeneralRegla.ramo) &&
        Objects.equals(this.subramo, infoGeneralRegla.subramo) &&
        Objects.equals(this.factor, infoGeneralRegla.factor) &&
        Objects.equals(this.clasificacionClientes, infoGeneralRegla.clasificacionClientes) &&
        Objects.equals(this.bolsas, infoGeneralRegla.bolsas) &&
        Objects.equals(this.sucursales, infoGeneralRegla.sucursales) &&
        Objects.equals(this.canalComercializacion, infoGeneralRegla.canalComercializacion) &&
        Objects.equals(this.compraCumplido, infoGeneralRegla.compraCumplido) &&
        Objects.equals(this.aforo, infoGeneralRegla.aforo) &&
        Objects.equals(this.estatusPartida, infoGeneralRegla.estatusPartida) &&
        Objects.equals(this.canalIngresoActual, infoGeneralRegla.canalIngresoActual) &&
        Objects.equals(this.diasAlmoneda, infoGeneralRegla.diasAlmoneda) &&
        Objects.equals(this.tipoMonedas, infoGeneralRegla.tipoMonedas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, origen, ramo, subramo, factor, clasificacionClientes, bolsas, sucursales, canalComercializacion, compraCumplido, aforo, estatusPartida, canalIngresoActual, diasAlmoneda, tipoMonedas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoGeneralRegla {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    origen: ").append(toIndentedString(origen)).append("\n");
    sb.append("    ramo: ").append(toIndentedString(ramo)).append("\n");
    sb.append("    subramo: ").append(toIndentedString(subramo)).append("\n");
    sb.append("    factor: ").append(toIndentedString(factor)).append("\n");
    sb.append("    clasificacionClientes: ").append(toIndentedString(clasificacionClientes)).append("\n");
    sb.append("    bolsas: ").append(toIndentedString(bolsas)).append("\n");
    sb.append("    sucursales: ").append(toIndentedString(sucursales)).append("\n");
    sb.append("    canalComercializacion: ").append(toIndentedString(canalComercializacion)).append("\n");
    sb.append("    compraCumplido: ").append(toIndentedString(compraCumplido)).append("\n");
    sb.append("    aforo: ").append(toIndentedString(aforo)).append("\n");
    sb.append("    estatusPartida: ").append(toIndentedString(estatusPartida)).append("\n");
    sb.append("    canalIngresoActual: ").append(toIndentedString(canalIngresoActual)).append("\n");
    sb.append("    diasAlmoneda: ").append(toIndentedString(diasAlmoneda)).append("\n");
    sb.append("    tipoMonedas: ").append(toIndentedString(tipoMonedas)).append("\n");
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

