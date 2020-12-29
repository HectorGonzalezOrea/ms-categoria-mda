package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * InfoUsuario
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class InfoUsuario   {
  @JsonProperty("idUsuario")
  private Integer idUsuario = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("apellidoPaterno")
  private String apellidoPaterno = null;

  @JsonProperty("apellidoMaterno")
  private String apellidoMaterno = null;

  @JsonProperty("usuario")
  private String usuario = null;
  /*
  @JsonProperty("puesto")
  private CatalogoVO puesto = null;

  @JsonProperty("direccion")
  private CatalogoVO direccion = null;

  @JsonProperty("subdireccion")
  private CatalogoVO subdireccion = null;

  @JsonProperty("gerencia")
  private CatalogoVO gerencia = null;

  @JsonProperty("departamentoArea")
  private CatalogoVO departamentoArea = null;
  */
  @JsonProperty("perfil")
  private CapacidadUsuariosRes perfil = null;

  @JsonProperty("activo")
  private Boolean activo = null;

  public InfoUsuario idUsuario(Integer idUsuario) {
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

  public InfoUsuario nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Nombre del usuario registrado.
   * @return nombre
  **/
  @ApiModelProperty(example = "Scarlett", value = "Nombre del usuario registrado.")


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public InfoUsuario apellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
    return this;
  }

  /**
   * Apellido paterno del usuario.
   * @return apellidoPaterno
  **/
  @ApiModelProperty(example = "González", value = "Apellido paterno del usuario.")


  public String getApellidoPaterno() {
    return apellidoPaterno;
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }

  public InfoUsuario apellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
    return this;
  }

  /**
   * Apellido materno del usuario.
   * @return apellidoMaterno
  **/
  @ApiModelProperty(example = "Hernández", value = "Apellido materno del usuario.")


  public String getApellidoMaterno() {
    return apellidoMaterno;
  }

  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }

  public InfoUsuario usuario(String usuario) {
    this.usuario = usuario;
    return this;
  }

  /**
   * Username con el que es registrado el usuario en AD.
   * @return usuario
  **/
  @ApiModelProperty(example = "sgonzalez", value = "Username con el que es registrado el usuario en AD.")


  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public InfoUsuario perfil(CapacidadUsuariosRes perfil) {
    this.perfil = perfil;
    return this;
  }

  /**
   * Get perfil
   * @return perfil
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CapacidadUsuariosRes getPerfil() {
    return perfil;
  }

  public void setPerfil(CapacidadUsuariosRes perfil) {
    this.perfil = perfil;
  }

  public InfoUsuario activo(Boolean activo) {
    this.activo = activo;
    return this;
  }

  /**
   * estatus registrado por el administrador.
   * @return activo
  **/
  @ApiModelProperty(value = "estatus registrado por el administrador.")


  public Boolean isActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoUsuario infoUsuario = (InfoUsuario) o;
    return Objects.equals(this.idUsuario, infoUsuario.idUsuario) &&
        Objects.equals(this.nombre, infoUsuario.nombre) &&
        Objects.equals(this.apellidoPaterno, infoUsuario.apellidoPaterno) &&
        Objects.equals(this.apellidoMaterno, infoUsuario.apellidoMaterno) &&
        Objects.equals(this.usuario, infoUsuario.usuario) &&
        Objects.equals(this.perfil, infoUsuario.perfil) &&
        Objects.equals(this.activo, infoUsuario.activo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idUsuario, nombre, apellidoPaterno, apellidoMaterno, usuario, perfil, activo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoUsuario {\n");
    
    sb.append("    idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    apellidoPaterno: ").append(toIndentedString(apellidoPaterno)).append("\n");
    sb.append("    apellidoMaterno: ").append(toIndentedString(apellidoMaterno)).append("\n");
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
    sb.append("    perfil: ").append(toIndentedString(perfil)).append("\n");
    sb.append("    activo: ").append(toIndentedString(activo)).append("\n");
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

