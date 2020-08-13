package mx.com.nmp.consolidados.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * InlineResponse200
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

public class InlineResponse200   {
  @JsonProperty("idPosicion")
  private Integer idPosicion = null;

  @JsonProperty("nombreArchivo")
  private String nombreArchivo = null;

  public InlineResponse200 idPosicion(Integer idPosicion) {
    this.idPosicion = idPosicion;
    return this;
  }

  /**
   * Get idPosicion
   * @return idPosicion
  **/
  @ApiModelProperty(value = "")


  public Integer getIdPosicion() {
    return idPosicion;
  }

  public void setIdPosicion(Integer idPosicion) {
    this.idPosicion = idPosicion;
  }

  public InlineResponse200 nombreArchivo(String nombreArchivo) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.idPosicion, inlineResponse200.idPosicion) &&
        Objects.equals(this.nombreArchivo, inlineResponse200.nombreArchivo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPosicion, nombreArchivo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    idPosicion: ").append(toIndentedString(idPosicion)).append("\n");
    sb.append("    nombreArchivo: ").append(toIndentedString(nombreArchivo)).append("\n");
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

