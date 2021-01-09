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
 * Información general de la regla
 */
@ApiModel(description = "Información general de la regla")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class InfoGeneralRegla   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("origen")
  private Object origen = null;

  @JsonProperty("ramo")
  private Object ramo = null;

  @JsonProperty("subramo")
  @Valid
  private List<Object> subramo = null;
  
  @JsonProperty("categoria")
  @Valid
  private List<String> categoria;

  @JsonProperty("factor")
  @Valid
  private List<Object> factor = null;

  @JsonProperty("clasificacionClientes")
  @Valid
  private List<Object> clasificacionClientes = null;

  @JsonProperty("bolsas")
  @Valid
  private List<Object> bolsas = null;

  @JsonProperty("sucursales")
  @Valid
  private List<Object> sucursales = null;

  @JsonProperty("canalComercializacion")
  @Valid
  private List<InfoGeneralReglaCanalComercializacion> canalComercializacion = null;

  @JsonProperty("fechaAplicacion")
  private Object fechaAplicacion = null;

  @JsonProperty("estatus")
  private Object estatus = null;
  
  private Object tipoMonedas;

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

  public InfoGeneralRegla ramo(Object ramo) {
    this.ramo = ramo;
    return this;
  }

  /**
   * Ramo de la partida
   * @return ramo
  **/
  @ApiModelProperty(value = "Ramo de la partida")


  public Object getRamo() {
    return ramo;
  }

  public void setRamo(Object ramo) {
    this.ramo = ramo;
  }

  public InfoGeneralRegla subramo(List<Object> subramo) {
    this.subramo = subramo;
    return this;
  }

  public InfoGeneralRegla addSubramoItem(Object subramoItem) {
    if (this.subramo == null) {
      this.subramo = new ArrayList<Object>();
    }
    this.subramo.add(subramoItem);
    return this;
  }

  /**
   * Subramo de la partida
   * @return subramo
  **/
  @ApiModelProperty(value = "Subramo de la partida")


  public List<Object> getSubramo() {
    return subramo;
  }

  public void setSubramo(List<Object> subramo) {
    this.subramo = subramo;
  }

  public InfoGeneralRegla factor(List<Object> factor) {
    this.factor = factor;
    return this;
  }

  public InfoGeneralRegla addFactorItem(Object factorItem) {
    if (this.factor == null) {
      this.factor = new ArrayList<Object>();
    }
    this.factor.add(factorItem);
    return this;
  }

  /**
   * Factor de la partida
   * @return factor
  **/
  @ApiModelProperty(value = "Factor de la partida")


  public List<Object> getFactor() {
    return factor;
  }

  public void setFactor(List<Object> factor) {
    this.factor = factor;
  }

  public InfoGeneralRegla clasificacionClientes(List<Object> clasificacionClientes) {
    this.clasificacionClientes = clasificacionClientes;
    return this;
  }

  public InfoGeneralRegla addClasificacionClientesItem(Object clasificacionClientesItem) {
    if (this.clasificacionClientes == null) {
      this.clasificacionClientes = new ArrayList<Object>();
    }
    this.clasificacionClientes.add(clasificacionClientesItem);
    return this;
  }

  /**
   * Clasificación de clientes
   * @return clasificacionClientes
  **/
  @ApiModelProperty(value = "Clasificación de clientes")


  public List<Object> getClasificacionClientes() {
    return clasificacionClientes;
  }

  public void setClasificacionClientes(List<Object> clasificacionClientes) {
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

  public InfoGeneralRegla sucursales(List<Object> sucursales) {
    this.sucursales = sucursales;
    return this;
  }

  public InfoGeneralRegla addSucursalesItem(Object sucursalesItem) {
    if (this.sucursales == null) {
      this.sucursales = new ArrayList<Object>();
    }
    this.sucursales.add(sucursalesItem);
    return this;
  }

  /**
   * Lista de sucursales
   * @return sucursales
  **/
  @ApiModelProperty(value = "Lista de sucursales")


  public List<Object> getSucursales() {
    return sucursales;
  }

  public void setSucursales(List<Object> sucursales) {
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

  public InfoGeneralRegla fechaAplicacion(Object fechaAplicacion) {
    this.fechaAplicacion = fechaAplicacion;
    return this;
  }

  /**
   * Get fechaAplicacion
   * @return fechaAplicacion
  **/
  @ApiModelProperty(value = "")


  public Object getFechaAplicacion() {
    return fechaAplicacion;
  }

  public void setFechaAplicacion(Object fechaAplicacion) {
    this.fechaAplicacion = fechaAplicacion;
  }

  public InfoGeneralRegla estatus(Object estatus) {
    this.estatus = estatus;
    return this;
  }

  /**
   * Estatus de la regla
   * @return estatus
  **/
  @ApiModelProperty(value = "Estatus de la regla")


  public Object getEstatus() {
    return estatus;
  }

  public void setEstatus(Object estatus) {
    this.estatus = estatus;
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
        Objects.equals(this.fechaAplicacion, infoGeneralRegla.fechaAplicacion) &&
        Objects.equals(this.estatus, infoGeneralRegla.estatus)&&
    	Objects.equals(this.categoria, infoGeneralRegla.categoria);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, origen, ramo, subramo, factor, clasificacionClientes, bolsas, sucursales, canalComercializacion, fechaAplicacion, estatus);
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
    sb.append("    fechaAplicacion: ").append(toIndentedString(fechaAplicacion)).append("\n");
    sb.append("    estatus: ").append(toIndentedString(estatus)).append("\n");
    sb.append("	   categoria: ").append(toIndentedString(categoria));
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

public Object getTipoMonedas() {
	return tipoMonedas;
}

public void setTipoMonedas(Object tipoMonedas) {
	this.tipoMonedas = tipoMonedas;
}

public List<String> getCategoria() {
	return categoria;
}

public void setCategoria(List<String> categoria) {
	this.categoria = categoria;
}
}

