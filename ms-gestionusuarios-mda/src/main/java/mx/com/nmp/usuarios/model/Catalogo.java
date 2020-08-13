package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * Catalogo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class Catalogo   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("descripcion")
  private String descripcion = null;

  public Catalogo id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identificador del objeto
   * @return id
  **/
  @ApiModelProperty(example = "1", value = "Identificador del objeto")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Catalogo descripcion(String descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  /**
   * Descripción del objeto
   * @return descripcion
  **/
  @ApiModelProperty(example = "Descripción", value = "Descripción del objeto")


  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Catalogo catalogo = (Catalogo) o;
    return Objects.equals(this.id, catalogo.id) &&
        Objects.equals(this.descripcion, catalogo.descripcion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, descripcion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Catalogo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
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

