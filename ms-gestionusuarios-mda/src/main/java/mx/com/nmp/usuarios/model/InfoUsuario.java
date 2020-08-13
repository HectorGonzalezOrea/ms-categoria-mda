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

  @JsonProperty("puesto")
  private Object puesto = null;

  @JsonProperty("direccion")
  private Object direccion = null;

  @JsonProperty("subdireccion")
  private Object subdireccion = null;

  @JsonProperty("gerencia")
  private Object gerencia = null;

  @JsonProperty("departamentoArea")
  private Object departamentoArea = null;

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

  public InfoUsuario puesto(Object puesto) {
    this.puesto = puesto;
    return this;
  }

  /**
   * Catalogo seleccionable, no se tienen ejemplos en los DF's.
   * @return puesto
  **/
  @ApiModelProperty(value = "Catalogo seleccionable, no se tienen ejemplos en los DF's.")


  public Object getPuesto() {
    return puesto;
  }

  public void setPuesto(Object puesto) {
    this.puesto = puesto;
  }

  public InfoUsuario direccion(Object direccion) {
    this.direccion = direccion;
    return this;
  }

  /**
   * Catalogo seleccionable, no se tienen ejemplos en los DF's.
   * @return direccion
  **/
  @ApiModelProperty(value = "Catalogo seleccionable, no se tienen ejemplos en los DF's.")


  public Object getDireccion() {
    return direccion;
  }

  public void setDireccion(Object direccion) {
    this.direccion = direccion;
  }

  public InfoUsuario subdireccion(Object subdireccion) {
    this.subdireccion = subdireccion;
    return this;
  }

  /**
   * Catalogo seleccionable, no se tienen ejemplos en los DF's.
   * @return subdireccion
  **/
  @ApiModelProperty(value = "Catalogo seleccionable, no se tienen ejemplos en los DF's.")


  public Object getSubdireccion() {
    return subdireccion;
  }

  public void setSubdireccion(Object subdireccion) {
    this.subdireccion = subdireccion;
  }

  public InfoUsuario gerencia(Object gerencia) {
    this.gerencia = gerencia;
    return this;
  }

  /**
   * Catalogo seleccionable, no se tienen ejemplos en los DF's.
   * @return gerencia
  **/
  @ApiModelProperty(value = "Catalogo seleccionable, no se tienen ejemplos en los DF's.")


  public Object getGerencia() {
    return gerencia;
  }

  public void setGerencia(Object gerencia) {
    this.gerencia = gerencia;
  }

  public InfoUsuario departamentoArea(Object departamentoArea) {
    this.departamentoArea = departamentoArea;
    return this;
  }

  /**
   * Catalogo seleccionable, no se tienen ejemplos en los DF's.
   * @return departamentoArea
  **/
  @ApiModelProperty(value = "Catalogo seleccionable, no se tienen ejemplos en los DF's.")


  public Object getDepartamentoArea() {
    return departamentoArea;
  }

  public void setDepartamentoArea(Object departamentoArea) {
    this.departamentoArea = departamentoArea;
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
        Objects.equals(this.puesto, infoUsuario.puesto) &&
        Objects.equals(this.direccion, infoUsuario.direccion) &&
        Objects.equals(this.subdireccion, infoUsuario.subdireccion) &&
        Objects.equals(this.gerencia, infoUsuario.gerencia) &&
        Objects.equals(this.departamentoArea, infoUsuario.departamentoArea) &&
        Objects.equals(this.perfil, infoUsuario.perfil) &&
        Objects.equals(this.activo, infoUsuario.activo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idUsuario, nombre, apellidoPaterno, apellidoMaterno, usuario, puesto, direccion, subdireccion, gerencia, departamentoArea, perfil, activo);
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
    sb.append("    puesto: ").append(toIndentedString(puesto)).append("\n");
    sb.append("    direccion: ").append(toIndentedString(direccion)).append("\n");
    sb.append("    subdireccion: ").append(toIndentedString(subdireccion)).append("\n");
    sb.append("    gerencia: ").append(toIndentedString(gerencia)).append("\n");
    sb.append("    departamentoArea: ").append(toIndentedString(departamentoArea)).append("\n");
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

