package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PerfilUsuario
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-14T18:11:19.533Z")

public class PerfilUsuario extends InfoUsuario  {
  @JsonProperty("nombreDistinguido")
  private String nombreDistinguido = null;

  @JsonProperty("miembroDe")
  @Valid
  private List<String> miembroDe = null;

  public PerfilUsuario nombreDistinguido(String nombreDistinguido) {
    this.nombreDistinguido = nombreDistinguido;
    return this;
  }

  /**
   * Nombre distinguido del usuario
   * @return nombreDistinguido
  **/
  @ApiModelProperty(example = "CN=Juan Perez Hernandez,OU=SPS,OU=Consultores,OU=Usuarios,DC=nmp,DC=com,DC=mx", value = "Nombre distinguido del usuario")


  public String getNombreDistinguido() {
    return nombreDistinguido;
  }

  public void setNombreDistinguido(String nombreDistinguido) {
    this.nombreDistinguido = nombreDistinguido;
  }

  public PerfilUsuario miembroDe(List<String> miembroDe) {
    this.miembroDe = miembroDe;
    return this;
  }

  public PerfilUsuario addMiembroDeItem(String miembroDeItem) {
    if (this.miembroDe == null) {
      this.miembroDe = new ArrayList<String>();
    }
    this.miembroDe.add(miembroDeItem);
    return this;
  }

  /**
   * Información de los grupos a los que pertenece el usuario
   * @return miembroDe
  **/
  @ApiModelProperty(value = "Información de los grupos a los que pertenece el usuario")


  public List<String> getMiembroDe() {
    return miembroDe;
  }

  public void setMiembroDe(List<String> miembroDe) {
    this.miembroDe = miembroDe;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PerfilUsuario perfilUsuario = (PerfilUsuario) o;
    return Objects.equals(this.nombreDistinguido, perfilUsuario.nombreDistinguido) &&
        Objects.equals(this.miembroDe, perfilUsuario.miembroDe) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombreDistinguido, miembroDe, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PerfilUsuario {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    nombreDistinguido: ").append(toIndentedString(nombreDistinguido)).append("\n");
    sb.append("    miembroDe: ").append(toIndentedString(miembroDe)).append("\n");
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

