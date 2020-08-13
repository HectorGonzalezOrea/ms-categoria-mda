package mx.com.nmp.establecimientoprecios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;



/**
 * Configuración de precios por moneda
 */
@ApiModel(description = "Configuración de precios por moneda")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-03T00:30:35.505Z")

public class Moneda   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("tipo")
  private Object tipo = null;

  @JsonProperty("precio")
  private Float precio = null;

  @JsonProperty("oro")
  private Boolean oro = null;

  public Moneda id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Identificador de la moneda
   * @return id
  **/
  @ApiModelProperty(example = "1", value = "Identificador de la moneda")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Moneda tipo(Object tipo) {
    this.tipo = tipo;
    return this;
  }

  /**
   * Definición del tipo de moneda
   * @return tipo
  **/
  @ApiModelProperty(value = "Definición del tipo de moneda")


  public Object getTipo() {
    return tipo;
  }

  public void setTipo(Object tipo) {
    this.tipo = tipo;
  }

  public Moneda precio(Float precio) {
    this.precio = precio;
    return this;
  }

  /**
   * Precio de la moneda
   * @return precio
  **/
  @ApiModelProperty(example = "578.5", value = "Precio de la moneda")


  public Float getPrecio() {
    return precio;
  }

  public void setPrecio(Float precio) {
    this.precio = precio;
  }

  public Moneda oro(Boolean oro) {
    this.oro = oro;
    return this;
  }

  /**
   * Flag para indicar si el tipo de Moneda es Oro
   * @return oro
  **/
  @ApiModelProperty(example = "true", value = "Flag para indicar si el tipo de Moneda es Oro")


  public Boolean isOro() {
    return oro;
  }

  public void setOro(Boolean oro) {
    this.oro = oro;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Moneda moneda = (Moneda) o;
    return Objects.equals(this.id, moneda.id) &&
        Objects.equals(this.tipo, moneda.tipo) &&
        Objects.equals(this.precio, moneda.precio) &&
        Objects.equals(this.oro, moneda.oro);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tipo, precio, oro);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Moneda {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    precio: ").append(toIndentedString(precio)).append("\n");
    sb.append("    oro: ").append(toIndentedString(oro)).append("\n");
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

