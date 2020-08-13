package mx.com.nmp.usuarios.model;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * ReqHistorico
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ReqHistorico   {
  @JsonProperty("fecha")
  private Date fecha = null;

  @JsonProperty("usuario")
  private String usuario = null;

  @JsonProperty("idPerfil")
  private Integer idPerfil = null;

  /**
   * Acciones realizadas dentro del portal por el usuario.
   */
  public enum AccionEnum {
	    ALTA_USUARIO("Alta Usuario."),
	    
	    BAJA_USUARIO("Baja Usuario."),
	    
	    ACTIVACION_USUARIO("Activacion Usuario."),
	    
	    INACTIVIDAD_USUARIO("Inactividad Usuario."),
	    
	    ASIGNACION_PERFIL("Asignacion Perfil."),
	    
	    MODIFICACION_PERFIL("Modificacion Perfil.");

    private String value;

    AccionEnum(String value) {
      this.value = value;
    }
    
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
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

  public ReqHistorico fecha(Date fecha) {
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

  public ReqHistorico usuario(String usuario) {
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

  public ReqHistorico idPerfil(Integer idPerfil) {
    this.idPerfil = idPerfil;
    return this;
  }

  /**
   * Identificador del perfil asignado al usuario por el administrador.
   * @return idPerfil
  **/
  @ApiModelProperty(example = "3", value = "Identificador del perfil asignado al usuario por el administrador.")


  public Integer getIdPerfil() {
    return idPerfil;
  }

  public void setIdPerfil(Integer idPerfil) {
    this.idPerfil = idPerfil;
  }

  public ReqHistorico accion(AccionEnum accion) {
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
    ReqHistorico reqHistorico = (ReqHistorico) o;
    return Objects.equals(this.fecha, reqHistorico.fecha) &&
        Objects.equals(this.usuario, reqHistorico.usuario) &&
        Objects.equals(this.idPerfil, reqHistorico.idPerfil) &&
        Objects.equals(this.accion, reqHistorico.accion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fecha, usuario, idPerfil, accion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReqHistorico {\n");
    
    sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
    sb.append("    idPerfil: ").append(toIndentedString(idPerfil)).append("\n");
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

