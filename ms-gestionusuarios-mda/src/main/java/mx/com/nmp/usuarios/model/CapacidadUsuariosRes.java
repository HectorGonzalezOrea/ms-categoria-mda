package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;


import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * CapacidadUsuariosRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class CapacidadUsuariosRes   {
  @JsonProperty("idPerfil")
  private Integer idPerfil = null;

  /**
   * Descripcion del perfil de un usuario actual.
   */
  public enum DescripcionPerfilEnum {
	ADMINISTRADOR("Administrador"),
	  
    CONSULTOR("Consultor"),
    
    OPERADOR("Operador");

    private String value;

    DescripcionPerfilEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DescripcionPerfilEnum fromValue(String text) {
      for (DescripcionPerfilEnum b : DescripcionPerfilEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("descripcionPerfil")
  private DescripcionPerfilEnum descripcionPerfil = null;

  @JsonProperty("informacionPerfil")
  private CapacidadUsuariosReq informacionPerfil = null;

  public CapacidadUsuariosRes idPerfil(Integer idPerfil) {
    this.idPerfil = idPerfil;
    return this;
  }

  /**
   * Identificador del perfil.
   * @return idPerfil
  **/
  @ApiModelProperty(example = "3", value = "Identificador del perfil.")


  public Integer getIdPerfil() {
    return idPerfil;
  }

  public void setIdPerfil(Integer idPerfil) {
    this.idPerfil = idPerfil;
  }

  public CapacidadUsuariosRes descripcionPerfil(DescripcionPerfilEnum descripcionPerfil) {
    this.descripcionPerfil = descripcionPerfil;
    return this;
  }

  /**
   * Descripcion del perfil de un usuario actual.
   * @return descripcionPerfil
  **/
  @ApiModelProperty(value = "Descripcion del perfil de un usuario actual.")


  public DescripcionPerfilEnum getDescripcionPerfil() {
    return descripcionPerfil;
  }

  public void setDescripcionPerfil(DescripcionPerfilEnum descripcionPerfil) {
    this.descripcionPerfil = descripcionPerfil;
  }

  public CapacidadUsuariosRes informacionPerfil(CapacidadUsuariosReq informacionPerfil) {
    this.informacionPerfil = informacionPerfil;
    return this;
  }

  /**
   * Get informacionPerfil
   * @return informacionPerfil
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CapacidadUsuariosReq getInformacionPerfil() {
    return informacionPerfil;
  }

  public void setInformacionPerfil(CapacidadUsuariosReq informacionPerfil) {
    this.informacionPerfil = informacionPerfil;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CapacidadUsuariosRes capacidadUsuariosRes = (CapacidadUsuariosRes) o;
    return Objects.equals(this.idPerfil, capacidadUsuariosRes.idPerfil) &&
        Objects.equals(this.descripcionPerfil, capacidadUsuariosRes.descripcionPerfil) &&
        Objects.equals(this.informacionPerfil, capacidadUsuariosRes.informacionPerfil);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPerfil, descripcionPerfil, informacionPerfil);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CapacidadUsuariosRes {\n");
    
    sb.append("    idPerfil: ").append(toIndentedString(idPerfil)).append("\n");
    sb.append("    descripcionPerfil: ").append(toIndentedString(descripcionPerfil)).append("\n");
    sb.append("    informacionPerfil: ").append(toIndentedString(informacionPerfil)).append("\n");
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

