package mx.com.nmp.consolidados.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.nmp.consolidados.model.InfoProducto;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ConsultarArchivoConsolidadoResInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-27T18:43:33.330Z")

public class ConsultarArchivoConsolidadoResInner   {
  @JsonProperty("idArchivo")
  private Integer idArchivo = null;

  @JsonProperty("nombreArchivo")
  private String nombreArchivo = null;

  @JsonProperty("fechaReporte")
  private OffsetDateTime fechaReporte = null;

  @JsonProperty("nombreCliente")
  private String nombreCliente = null;

  @JsonProperty("idPrioridad")
  private Integer idPrioridad = null;

  @JsonProperty("producto")
  @Valid
  private List<InfoProducto> producto = null;

  public ConsultarArchivoConsolidadoResInner idArchivo(Integer idArchivo) {
    this.idArchivo = idArchivo;
    return this;
  }

  /**
   * Identificador del archivo
   * @return idArchivo
  **/
  @ApiModelProperty(example = "111", value = "Identificador del archivo")


  public Integer getIdArchivo() {
    return idArchivo;
  }

  public void setIdArchivo(Integer idArchivo) {
    this.idArchivo = idArchivo;
  }

  public ConsultarArchivoConsolidadoResInner nombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
    return this;
  }

  /**
   * Get nombreArchivo
   * @return nombreArchivo
  **/
  @ApiModelProperty(value = "")


  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public ConsultarArchivoConsolidadoResInner fechaReporte(OffsetDateTime fechaReporte) {
    this.fechaReporte = fechaReporte;
    return this;
  }

  /**
   * Get fechaReporte
   * @return fechaReporte
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getFechaReporte() {
    return fechaReporte;
  }

  public void setFechaReporte(OffsetDateTime fechaReporte) {
    this.fechaReporte = fechaReporte;
  }

  public ConsultarArchivoConsolidadoResInner nombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
    return this;
  }

  /**
   * Get nombreCliente
   * @return nombreCliente
  **/
  @ApiModelProperty(value = "")


  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public ConsultarArchivoConsolidadoResInner idPrioridad(Integer idPrioridad) {
    this.idPrioridad = idPrioridad;
    return this;
  }

  /**
   * Prioridad en la ejecución del archivo
   * @return idPrioridad
  **/
  @ApiModelProperty(example = "0", value = "Prioridad en la ejecución del archivo")


  public Integer getIdPrioridad() {
    return idPrioridad;
  }

  public void setIdPrioridad(Integer idPrioridad) {
    this.idPrioridad = idPrioridad;
  }

  public ConsultarArchivoConsolidadoResInner producto(List<InfoProducto> producto) {
    this.producto = producto;
    return this;
  }

  public ConsultarArchivoConsolidadoResInner addProductoItem(InfoProducto productoItem) {
    if (this.producto == null) {
      this.producto = new ArrayList<InfoProducto>();
    }
    this.producto.add(productoItem);
    return this;
  }

  /**
   * Get producto
   * @return producto
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<InfoProducto> getProducto() {
    return producto;
  }

  public void setProducto(List<InfoProducto> producto) {
    this.producto = producto;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsultarArchivoConsolidadoResInner consultarArchivoConsolidadoResInner = (ConsultarArchivoConsolidadoResInner) o;
    return Objects.equals(this.idArchivo, consultarArchivoConsolidadoResInner.idArchivo) &&
        Objects.equals(this.nombreArchivo, consultarArchivoConsolidadoResInner.nombreArchivo) &&
        Objects.equals(this.fechaReporte, consultarArchivoConsolidadoResInner.fechaReporte) &&
        Objects.equals(this.nombreCliente, consultarArchivoConsolidadoResInner.nombreCliente) &&
        Objects.equals(this.idPrioridad, consultarArchivoConsolidadoResInner.idPrioridad) &&
        Objects.equals(this.producto, consultarArchivoConsolidadoResInner.producto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idArchivo, nombreArchivo, fechaReporte, nombreCliente, idPrioridad, producto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsultarArchivoConsolidadoResInner {\n");
    
    sb.append("    idArchivo: ").append(toIndentedString(idArchivo)).append("\n");
    sb.append("    nombreArchivo: ").append(toIndentedString(nombreArchivo)).append("\n");
    sb.append("    fechaReporte: ").append(toIndentedString(fechaReporte)).append("\n");
    sb.append("    nombreCliente: ").append(toIndentedString(nombreCliente)).append("\n");
    sb.append("    idPrioridad: ").append(toIndentedString(idPrioridad)).append("\n");
    sb.append("    producto: ").append(toIndentedString(producto)).append("\n");
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

