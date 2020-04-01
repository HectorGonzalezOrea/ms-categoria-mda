package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CapacidadUsuariosReqInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class CapacidadUsuariosReqInner   {
  /**
   * Gets or Sets idCapacidad
   */
  public enum IdCapacidadEnum {
    NUMBER_1(1),
    
    NUMBER_2(2),
    
    NUMBER_3(3),
    
    NUMBER_4(4),
    
    NUMBER_5(5),
    
    NUMBER_6(6),
    
    NUMBER_7(7),
    
    NUMBER_8(8),
    
    NUMBER_9(9),
    
    NUMBER_10(10),
    
    NUMBER_11(11),
    
    NUMBER_12(12),
    
    NUMBER_13(13),
    
    NUMBER_14(14),
    
    NUMBER_15(15),
    
    NUMBER_16(16);

    private Integer value;

    IdCapacidadEnum(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
    
    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static IdCapacidadEnum fromValue(String text) {
      for (IdCapacidadEnum b : IdCapacidadEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("idCapacidad")
  private IdCapacidadEnum idCapacidad = null;

  /**
   * Gets or Sets descripcinCap
   */
  public enum DescripcinCapEnum {
    CONFIGURACI_N_DE_REGLAS_Y_AJUSTE_DE_PRECIOS_("Configuración de reglas y ajuste de precios."),
    
    REPORTEADOR_("Reporteador."),
    
    ADMINISTRACI_N_DE_USUARIOS_("Administración de usuarios."),
    
    CONSULTAR_USUARIOS_("Consultar usuarios."),
    
    ALTA_USUARIOS_("Alta de usuarios."),
    
    BAJA_USUARIOS_("Baja de usuarios."),
    
    ACTIVAR_INACTIVAR_USUARIO("Activar/Inactivar usuario."),
    
    BUSCAR_REGLAS_("Buscar reglas."),
    
    GENERAR_REGLAS_("Generar reglas."),
    
    CONFIGURAR_SUCURSAL_("Configurar sucursal."),
    
    VALIDAR_AJUSTES_DE_PRECIOS_("Validar ajustes de precios."),
    
    PROGRAMAR_REGLAS_("Programar reglas."),
    
    AJUSTAR_PRECIOS_("Ajustar precios."),
    
    GENERACI_N_DE_REPORTES("Generación de reportes."),
    
    GENERACI_N_DE_REPORTES_AD_HOC_("Generación de reportes Ad-Hoc."),
    
    DESCARGAR_REPORTES_("Descargar Reportes.");

    private String value;

    DescripcinCapEnum(String value) {
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
    public static DescripcinCapEnum fromValue(String text) {
      for (DescripcinCapEnum b : DescripcinCapEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("descripcionCap")
  private DescripcinCapEnum descripcinCap = null;

  public CapacidadUsuariosReqInner idCapacidad(IdCapacidadEnum idCapacidad) {
    this.idCapacidad = idCapacidad;
    return this;
  }

  /**
   * Get idCapacidad
   * @return idCapacidad
  **/
  @ApiModelProperty(value = "")


  public IdCapacidadEnum getIdCapacidad() {
    return idCapacidad;
  }

  public void setIdCapacidad(IdCapacidadEnum idCapacidad) {
    this.idCapacidad = idCapacidad;
  }

  public CapacidadUsuariosReqInner descripcinCap(DescripcinCapEnum descripcinCap) {
    this.descripcinCap = descripcinCap;
    return this;
  }

  /**
   * Get descripcinCap
   * @return descripcinCap
  **/
  @ApiModelProperty(value = "")


  public DescripcinCapEnum getDescripcinCap() {
    return descripcinCap;
  }

  public void setDescripcinCap(DescripcinCapEnum descripcinCap) {
    this.descripcinCap = descripcinCap;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CapacidadUsuariosReqInner capacidadUsuariosReqInner = (CapacidadUsuariosReqInner) o;
    return Objects.equals(this.idCapacidad, capacidadUsuariosReqInner.idCapacidad) &&
        Objects.equals(this.descripcinCap, capacidadUsuariosReqInner.descripcinCap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCapacidad, descripcinCap);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    
    sb.append("    idCapacidad: ").append(toIndentedString(idCapacidad)).append("\n");
    sb.append("    descripcinCap: ").append(toIndentedString(descripcinCap)).append("\n");
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

