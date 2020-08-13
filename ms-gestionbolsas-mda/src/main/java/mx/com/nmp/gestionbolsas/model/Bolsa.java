package mx.com.nmp.gestionbolsas.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * Información de la configuración de una bolsa
 */
@ApiModel(description = "Información de la configuración de una bolsa")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-21T17:43:22.226Z")

public class Bolsa extends ListaBolsasInner {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("tipo")
  private TipoBolsa tipo = null;

  @JsonProperty("ramo")
  private String ramo = null;

  @JsonProperty("subramo")
  private String subramo = null;

  @JsonProperty("factor")
  private String factor = null;

  @JsonProperty("sucursales")
  @Valid
  private List<String> sucursales = null;

  @JsonProperty("autor")
  private String autor = null;

  public Bolsa id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Identificador de la Bolsa
   * @return id
  **/
  @ApiModelProperty(example = "1", value = "Identificador de la Bolsa")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Bolsa nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Nombre de la Bolsa
   * @return nombre
  **/
  @ApiModelProperty(example = "Anlca1_Bolsa1_A_G", value = "Nombre de la Bolsa")


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Bolsa tipo(TipoBolsa tipo) {
    this.tipo = tipo;
    return this;
  }

  /**
   * Tipo de Bolsa
   * @return tipo
  **/
  @ApiModelProperty(value = "Tipo de Bolsa")


  public TipoBolsa getTipo() {
    return tipo;
  }

  public void setTipo(TipoBolsa tipo) {
    this.tipo = tipo;
  }

  public Bolsa ramo(String ramo) {
    this.ramo = ramo;
    return this;
  }

  /**
   * Ramo a configurar en la bolsa
   * @return ramo
  **/
  @ApiModelProperty(example = "AL", value = "Ramo a configurar en la bolsa")


  public String getRamo() {
    return ramo;
  }

  public void setRamo(String ramo) {
    this.ramo = ramo;
  }

  public Bolsa subramo(String subramo) {
    this.subramo = subramo;
    return this;
  }

  /**
   * Subramo a configurar en la bolsa
   * @return subramo
  **/
  @ApiModelProperty(example = "AL", value = "Subramo a configurar en la bolsa")


  public String getSubramo() {
    return subramo;
  }

  public void setSubramo(String subramo) {
    this.subramo = subramo;
  }

  public Bolsa factor(String factor) {
    this.factor = factor;
    return this;
  }

  /**
   * Factor a configurar en la bolsa
   * @return factor
  **/
  @ApiModelProperty(example = "F1", value = "Factor a configurar en la bolsa")


  public String getFactor() {
    return factor;
  }

  public void setFactor(String factor) {
    this.factor = factor;
  }

  public Bolsa sucursales(List<String> sucursales) {
    this.sucursales = sucursales;
    return this;
  }

  public Bolsa addSucursalesItem(String sucursalesItem) {
    if (this.sucursales == null) {
      this.sucursales = new ArrayList<String>();
    }
    this.sucursales.add(sucursalesItem);
    return this;
  }

  /**
   * Lista de sucursales pertenecientes a la Bolsa
   * @return sucursales
  **/
  @ApiModelProperty(value = "Lista de sucursales pertenecientes a la Bolsa")


  public List<String> getSucursales() {
    return sucursales;
  }

  public void setSucursales(List<String> sucursales) {
    this.sucursales = sucursales;
  }

  public Bolsa autor(String autor) {
    this.autor = autor;
    return this;
  }

  /**
   * Usuario que da de alta la bolsa
   * @return autor
  **/
  @ApiModelProperty(example = "Jesus Martinez", value = "Usuario que da de alta la bolsa")


  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Bolsa bolsa = (Bolsa) o;
    return Objects.equals(this.id, bolsa.id) &&
        Objects.equals(this.nombre, bolsa.nombre) &&
        Objects.equals(this.tipo, bolsa.tipo) &&
        Objects.equals(this.ramo, bolsa.ramo) &&
        Objects.equals(this.subramo, bolsa.subramo) &&
        Objects.equals(this.factor, bolsa.factor) &&
        Objects.equals(this.sucursales, bolsa.sucursales) &&
        Objects.equals(this.autor, bolsa.autor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, tipo, ramo, subramo, factor, sucursales, autor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Bolsa {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    ramo: ").append(toIndentedString(ramo)).append("\n");
    sb.append("    subramo: ").append(toIndentedString(subramo)).append("\n");
    sb.append("    factor: ").append(toIndentedString(factor)).append("\n");
    sb.append("    sucursales: ").append(toIndentedString(sucursales)).append("\n");
    sb.append("    autor: ").append(toIndentedString(autor)).append("\n");
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

