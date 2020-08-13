package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * ResEstatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ResEstatus   {
  @JsonProperty("idUsuario")
  private Integer idUsuario = null;

  /**
   * Descripción del estatus del cliente (activo/inactivo).
   */
  public enum DescripcionEnum {
    ACTIVO("activo"),
    
    INACTIVO("inactivo");

    private String value;

    DescripcionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DescripcionEnum fromValue(String text) {
      for (DescripcionEnum b : DescripcionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("descripcion")
  private DescripcionEnum descripcion = null;

  public ResEstatus idUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
    return this;
  }

  /**
   * Identificador del usuario.
   * @return idUsuario
  **/
  @ApiModelProperty(example = "1", value = "Identificador del usuario.")


  public Integer getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
  }

  public ResEstatus descripcion(DescripcionEnum descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  /**
   * Descripción del estatus del cliente (activo/inactivo).
   * @return descripcion
  **/
  @ApiModelProperty(value = "Descripción del estatus del cliente (activo/inactivo).")


  public DescripcionEnum getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(DescripcionEnum descripcion) {
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
    ResEstatus resEstatus = (ResEstatus) o;
    return Objects.equals(this.idUsuario, resEstatus.idUsuario) &&
        Objects.equals(this.descripcion, resEstatus.descripcion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idUsuario, descripcion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResEstatus {\n");
    
    sb.append("    idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
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

