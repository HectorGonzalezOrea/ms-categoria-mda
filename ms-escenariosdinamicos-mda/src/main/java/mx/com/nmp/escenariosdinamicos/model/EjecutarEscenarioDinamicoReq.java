package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * EjecutarEscenarioDinamicoReq
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class EjecutarEscenarioDinamicoReq   {
  @JsonProperty("infoRegla")
  private InfoRegla infoRegla = null;

  public EjecutarEscenarioDinamicoReq infoRegla(InfoRegla infoRegla) {
    this.infoRegla = infoRegla;
    return this;
  }

  /**
   * Get infoRegla
   * @return infoRegla
  **/
  @ApiModelProperty(value = "")

  @Valid

  public InfoRegla getInfoRegla() {
    return infoRegla;
  }

  public void setInfoRegla(InfoRegla infoRegla) {
    this.infoRegla = infoRegla;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EjecutarEscenarioDinamicoReq ejecutarEscenarioDinamicoReq = (EjecutarEscenarioDinamicoReq) o;
    return Objects.equals(this.infoRegla, ejecutarEscenarioDinamicoReq.infoRegla);
  }

  @Override
  public int hashCode() {
    return Objects.hash(infoRegla);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EjecutarEscenarioDinamicoReq {\n");
    
    sb.append("    infoRegla: ").append(toIndentedString(infoRegla)).append("\n");
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

