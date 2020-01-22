package mx.com.nmp.usuarios.model;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;

import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ResHistorico
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ResHistorico   {
  @JsonProperty("idRegistro")
  private Integer idRegistro = null;

  @JsonProperty("fecha")
  private Date fecha = null;

  @JsonProperty("usuario")
  private String usuario = null;

  @JsonProperty("perfil")
  private CapacidadUsuariosRes perfil = null;

  /**
   * Acciones realizadas dentro del portal por el usuario.
   */
  public enum AccionEnum {
    ALTA_USUARIO_("Alta Usuario."),
    
    BAJA_USUARIO_("Baja Usuario."),
    
    ACTIVACION_USUARIO_("Activacion Usuario."),
    
    INACTIVIDAD_USUARIO_("Inactividad Usuario."),
    
    ASIGNACION_PERFIL_("Asignacion Perfil."),
    
    MODIFICACION_PERFIL_("Modificacion Perfil.");

    private String value;

    AccionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AccionEnum fromValue(String text) {
      for (AccionEnum b : AccionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("accion")
  private AccionEnum accion = null;

  public ResHistorico idRegistro(Integer idRegistro) {
    this.idRegistro = idRegistro;
    return this;
  }

  /**
   * Identificador del registro.
   * @return idRegistro
  **/
  @ApiModelProperty(example = "1", value = "Identificador del registro.")


  public Integer getIdRegistro() {
    return idRegistro;
  }

  public void setIdRegistro(Integer idRegistro) {
    this.idRegistro = idRegistro;
  }

  public ResHistorico fecha(Date fecha) {
    this.fecha = fecha;
    return this;
  }

  /**
   * Fecha en que se registro la acción.
   * @return fecha
  **/
  @ApiModelProperty(value = "Fecha en que se registro la acción.")

  @Valid

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public ResHistorico usuario(String usuario) {
    this.usuario = usuario;
    return this;
  }

  /**
   * Username registrado en el AD.
   * @return usuario
  **/
  @ApiModelProperty(example = "sgonzalez", value = "Username registrado en el AD.")


  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public ResHistorico perfil(CapacidadUsuariosRes perfil) {
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

  public ResHistorico accion(AccionEnum accion) {
    this.accion = accion;
    return this;
  }

  /**
   * Acciones realizadas dentro del portal por el usuario.
   * @return accion
  **/
  @ApiModelProperty(value = "Acciones realizadas dentro del portal por el usuario.")


  public AccionEnum getAccion() {
    return accion;
  }

  public void setAccion(AccionEnum accion) {
    this.accion = accion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResHistorico resHistorico = (ResHistorico) o;
    return Objects.equals(this.idRegistro, resHistorico.idRegistro) &&
        Objects.equals(this.fecha, resHistorico.fecha) &&
        Objects.equals(this.usuario, resHistorico.usuario) &&
        Objects.equals(this.perfil, resHistorico.perfil) &&
        Objects.equals(this.accion, resHistorico.accion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idRegistro, fecha, usuario, perfil, accion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResHistorico {\n");
    
    sb.append("    idRegistro: ").append(toIndentedString(idRegistro)).append("\n");
    sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
    sb.append("    perfil: ").append(toIndentedString(perfil)).append("\n");
    sb.append("    accion: ").append(toIndentedString(accion)).append("\n");
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

