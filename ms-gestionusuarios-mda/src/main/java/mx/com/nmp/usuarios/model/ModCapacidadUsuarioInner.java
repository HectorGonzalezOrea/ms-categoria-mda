package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * ModCapacidadUsuarioInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ModCapacidadUsuarioInner   {
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
    
    NUMBER_16(16),
    
    NUMBER_17(17),
    
    NUMBER_18(18);

    private Integer value;

    IdCapacidadEnum(Integer value) {
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

  public ModCapacidadUsuarioInner idCapacidad(IdCapacidadEnum idCapacidad) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModCapacidadUsuarioInner modCapacidadUsuarioInner = (ModCapacidadUsuarioInner) o;
    return Objects.equals(this.idCapacidad, modCapacidadUsuarioInner.idCapacidad);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCapacidad);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    
    sb.append("    idCapacidad: ").append(toIndentedString(idCapacidad)).append("\n");
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

