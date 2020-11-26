package mx.com.nmp.categorias.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Lista de categorias y otros configuradas
 */
@ApiModel(description = "Lista de categorias y otros configuradas")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-11-12T23:53:03.246Z")




public class ResponseGetCategorias   {
  @JsonProperty("categorias")
  @Valid
  private List<Categoria> categorias = null;

  @JsonProperty("otros")
  @Valid
  private List<Categoria> otros = null;

  public ResponseGetCategorias categorias(List<Categoria> categorias) {
    this.categorias = categorias;
    return this;
  }

  public ResponseGetCategorias addCategoriasItem(Categoria categoriasItem) {
    if (this.categorias == null) {
      this.categorias = new ArrayList<Categoria>();
    }
    this.categorias.add(categoriasItem);
    return this;
  }

  /**
   * Get categorias
   * @return categorias
  **/
  @ApiModelProperty(value = "")


  public List<Categoria> getCategorias() {
    return categorias;
  }

  public void setCategorias(List<Categoria> categorias) {
    this.categorias = categorias;
  }

  public ResponseGetCategorias otros(List<Categoria> otros) {
    this.otros = otros;
    return this;
  }

  public ResponseGetCategorias addOtrosItem(Categoria otrosItem) {
    if (this.otros == null) {
      this.otros = new ArrayList<Categoria>();
    }
    this.otros.add(otrosItem);
    return this;
  }

  /**
   * Get otros
   * @return otros
  **/
  @ApiModelProperty(value = "")


  public List<Categoria> getOtros() {
    return otros;
  }

  public void setOtros(List<Categoria> otros) {
    this.otros = otros;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseGetCategorias responseGetCategorias = (ResponseGetCategorias) o;
    return Objects.equals(this.categorias, responseGetCategorias.categorias) &&
        Objects.equals(this.otros, responseGetCategorias.otros);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categorias, otros);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseGetCategorias {\n");
    
    sb.append("    categorias: ").append(toIndentedString(categorias)).append("\n");
    sb.append("    otros: ").append(toIndentedString(otros)).append("\n");
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

