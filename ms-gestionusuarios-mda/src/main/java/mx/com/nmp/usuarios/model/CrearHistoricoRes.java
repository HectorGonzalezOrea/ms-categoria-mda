package mx.com.nmp.usuarios.model;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CrearHistoricoRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class CrearHistoricoRes   {
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
  public enum AccionesEnum {
    ALTA_USUARIO("Alta Usuario."),
    
    BAJA_USUARIO("Baja Usuario."),
    
    ACTIVACION_USUARIO("Activacion Usuario."),
    
    INACTIVIDAD_USUARIO("Inactividad Usuario."),
    
    ASIGNACION_PERFIL("Asignacion Perfil."),
    
    MODIFICACION_PERFIL("Modificacion Perfil.");

    private String value;

    AccionesEnum(String value) {
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
    public static AccionesEnum fromValue(String text) {
    	System.out.println(text);
      for (AccionesEnum b : AccionesEnum.values()) {
    	  System.out.println(String.valueOf(b.value));
        if (String.valueOf(b.value).equals(text)) {
        	System.out.println(b);
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("acciones")
  private AccionesEnum acciones = null;

  public CrearHistoricoRes idRegistro(Integer idRegistro) {
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

  public CrearHistoricoRes fecha(Date fecha) {
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

  public CrearHistoricoRes usuario(String usuario) {
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

  public CrearHistoricoRes perfil(CapacidadUsuariosRes perfil) {
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

  public CrearHistoricoRes acciones(AccionesEnum acciones) {
    this.acciones = acciones;
    return this;
  }

  /**
   * Acciones realizadas dentro del portal por el usuario.
   * @return acciones
  **/
  @ApiModelProperty(value = "Acciones realizadas dentro del portal por el usuario.")


  public AccionesEnum getAcciones() {
    return acciones;
  }

  public void setAcciones(AccionesEnum acciones) {
    this.acciones = acciones;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CrearHistoricoRes crearHistoricoRes = (CrearHistoricoRes) o;
    return Objects.equals(this.idRegistro, crearHistoricoRes.idRegistro) &&
        Objects.equals(this.fecha, crearHistoricoRes.fecha) &&
        Objects.equals(this.usuario, crearHistoricoRes.usuario) &&
        Objects.equals(this.perfil, crearHistoricoRes.perfil) &&
        Objects.equals(this.acciones, crearHistoricoRes.acciones);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idRegistro, fecha, usuario, perfil, acciones);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CrearHistoricoRes {\n");
    
    sb.append("    idRegistro: ").append(toIndentedString(idRegistro)).append("\n");
    sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
    sb.append("    perfil: ").append(toIndentedString(perfil)).append("\n");
    sb.append("    acciones: ").append(toIndentedString(acciones)).append("\n");
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

