package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * ConsultarEscenariosResInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-04-08T15:50:03.199Z")

public class ConsultarEscenariosResInner   {
  @JsonProperty("idEscenario")
  private Integer idEscenario = null;

  /**
   * Comportamiento para el día uno basado en la demanda
   */
  @JsonProperty("diaUno")
  private String diaUno = null;

  /**
   * Comportamiento para el día dos basado en la demanda
   */

  @JsonProperty("diaDos")
  private String diaDos = null;

  /**
   * Comportamiento para el día tres basado en la demanda
   */

  @JsonProperty("diaTres")
  private String diaTres = null;

  /**
   * Regla a aplicar a la suma de comportamiento de los 3 últimos días
   */

  @JsonProperty("idRegla")
  private String idRegla = null;

  public ConsultarEscenariosResInner idEscenario(Integer idEscenario) {
    this.idEscenario = idEscenario;
    return this;
  }

  /**
   * Identificador del escenario
   * @return idEscenario
  **/
  @ApiModelProperty(example = "10002", value = "Identificador del escenario")


  public Integer getIdEscenario() {
    return idEscenario;
  }

  public void setIdEscenario(Integer idEscenario) {
    this.idEscenario = idEscenario;
  }

  public ConsultarEscenariosResInner diaUno(String diaUno) {
    this.diaUno = diaUno;
    return this;
  }

  /**
   * Comportamiento para el día uno basado en la demanda
   * @return diaUno
  **/
  @ApiModelProperty(required = true, value = "Comportamiento para el día uno basado en la demanda")
  @NotNull


  public String getDiaUno() {
    return diaUno;
  }

  public void setDiaUno(String diaUno) {
    this.diaUno = diaUno;
  }

  public ConsultarEscenariosResInner diaDos(String diaDos) {
    this.diaDos = diaDos;
    return this;
  }

  /**
   * Comportamiento para el día dos basado en la demanda
   * @return diaDos
  **/
  @ApiModelProperty(required = true, value = "Comportamiento para el día dos basado en la demanda")
  @NotNull


  public String getDiaDos() {
    return diaDos;
  }

  public void setDiaDos(String diaDos) {
    this.diaDos = diaDos;
  }

  public ConsultarEscenariosResInner diaTres(String diaTres) {
    this.diaTres = diaTres;
    return this;
  }

  /**
   * Comportamiento para el día tres basado en la demanda
   * @return diaTres
  **/
  @ApiModelProperty(required = true, value = "Comportamiento para el día tres basado en la demanda")
  @NotNull


  public String getDiaTres() {
    return diaTres;
  }

  public void setDiaTres(String diaTres) {
    this.diaTres = diaTres;
  }

  public ConsultarEscenariosResInner idRegla(String idRegla) {
    this.idRegla = idRegla;
    return this;
  }

  /**
   * Regla a aplicar a la suma de comportamiento de los 3 últimos días
   * @return idRegla
  **/
  @ApiModelProperty(required = true, value = "Regla a aplicar a la suma de comportamiento de los 3 últimos días")
  @NotNull


  public String getIdRegla() {
    return idRegla;
  }

  public void setIdRegla(String idRegla) {
    this.idRegla = idRegla;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsultarEscenariosResInner consultarEscenariosResInner = (ConsultarEscenariosResInner) o;
    return Objects.equals(this.idEscenario, consultarEscenariosResInner.idEscenario) &&
        Objects.equals(this.diaUno, consultarEscenariosResInner.diaUno) &&
        Objects.equals(this.diaDos, consultarEscenariosResInner.diaDos) &&
        Objects.equals(this.diaTres, consultarEscenariosResInner.diaTres) &&
        Objects.equals(this.idRegla, consultarEscenariosResInner.idRegla);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEscenario, diaUno, diaDos, diaTres, idRegla);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsultarEscenariosResInner {\n");
    
    sb.append("    idEscenario: ").append(toIndentedString(idEscenario)).append("\n");
    sb.append("    diaUno: ").append(toIndentedString(diaUno)).append("\n");
    sb.append("    diaDos: ").append(toIndentedString(diaDos)).append("\n");
    sb.append("    diaTres: ").append(toIndentedString(diaTres)).append("\n");
    sb.append("    idRegla: ").append(toIndentedString(idRegla)).append("\n");
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

