package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * ConsultaHistoricoRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ConsultaHistoricoRes   {
  @JsonProperty("historial")
  @Valid
  private List<ResHistorico> historial = null;

  public ConsultaHistoricoRes historial(List<ResHistorico> historial) {
    this.historial = historial;
    return this;
  }

  public ConsultaHistoricoRes addHistorialItem(ResHistorico historialItem) {
    if (this.historial == null) {
      this.historial = new ArrayList<ResHistorico>();
    }
    this.historial.add(historialItem);
    return this;
  }

  /**
   * Respuesta consulta histórico.
   * @return historial
  **/
  @ApiModelProperty(value = "Respuesta consulta histórico.")

  @Valid

  public List<ResHistorico> getHistorial() {
    return historial;
  }

  public void setHistorial(List<ResHistorico> historial) {
    this.historial = historial;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsultaHistoricoRes consultaHistoricoRes = (ConsultaHistoricoRes) o;
    return Objects.equals(this.historial, consultaHistoricoRes.historial);
  }

  @Override
  public int hashCode() {
    return Objects.hash(historial);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsultaHistoricoRes {\n");
    
    sb.append("    historial: ").append(toIndentedString(historial)).append("\n");
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

