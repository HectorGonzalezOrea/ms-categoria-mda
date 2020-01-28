package mx.com.nmp.consolidados.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ConsultarArchivoConsolidadoRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

public class ConsultarArchivoConsolidadoRes   {
  @JsonProperty("nombreArchivo")
  private String nombreArchivo = null;

  @JsonProperty("fechaReporte")
  private Date fechaReporte = null;

  @JsonProperty("nombreCliente")
  private String nombreCliente = null;

  @JsonProperty("producto")
  @Valid
  private List<InfoProducto> producto = null;

  public ConsultarArchivoConsolidadoRes nombreArchivo(String nombreArchivo) {
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

  public ConsultarArchivoConsolidadoRes fechaReporte(Date fechaReporte) {
    this.fechaReporte = fechaReporte;
    return this;
  }

  /**
   * Get fechaReporte
   * @return fechaReporte
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Date getFechaReporte() {
    return fechaReporte;
  }

  public void setFechaReporte(Date fechaReporte) {
    this.fechaReporte = fechaReporte;
  }

  public ConsultarArchivoConsolidadoRes nombreCliente(String nombreCliente) {
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

  public ConsultarArchivoConsolidadoRes producto(List<InfoProducto> producto) {
    this.producto = producto;
    return this;
  }

  public ConsultarArchivoConsolidadoRes addProductoItem(InfoProducto productoItem) {
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
    ConsultarArchivoConsolidadoRes consultarArchivoConsolidadoRes = (ConsultarArchivoConsolidadoRes) o;
    return Objects.equals(this.nombreArchivo, consultarArchivoConsolidadoRes.nombreArchivo) &&
        Objects.equals(this.fechaReporte, consultarArchivoConsolidadoRes.fechaReporte) &&
        Objects.equals(this.nombreCliente, consultarArchivoConsolidadoRes.nombreCliente) &&
        Objects.equals(this.producto, consultarArchivoConsolidadoRes.producto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombreArchivo, fechaReporte, nombreCliente, producto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsultarArchivoConsolidadoRes {\n");
    
    sb.append("    nombreArchivo: ").append(toIndentedString(nombreArchivo)).append("\n");
    sb.append("    fechaReporte: ").append(toIndentedString(fechaReporte)).append("\n");
    sb.append("    nombreCliente: ").append(toIndentedString(nombreCliente)).append("\n");
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

