package mx.com.nmp.gestionescenarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;


/**
 * InfoGeneralReglaCanalComercializacion
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class InfoGeneralReglaCanalComercializacion   {
  @JsonProperty("idCanal")
  private String idCanal = null;

  @JsonProperty("canal")
  private String canal = null;

  public InfoGeneralReglaCanalComercializacion idCanal(String idCanal) {
    this.idCanal = idCanal;
    return this;
  }

  /**
   * Get idCanal
   * @return idCanal
  **/
  @ApiModelProperty(example = "1", value = "")


  public String getIdCanal() {
    return idCanal;
  }

  public void setIdCanal(String idCanal) {
    this.idCanal = idCanal;
  }

  public InfoGeneralReglaCanalComercializacion canal(String canal) {
    this.canal = canal;
    return this;
  }

  /**
   * Get canal
   * @return canal
  **/
  @ApiModelProperty(example = "Amazon", value = "")


  public String getCanal() {
    return canal;
  }

  public void setCanal(String canal) {
    this.canal = canal;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoGeneralReglaCanalComercializacion infoGeneralReglaCanalComercializacion = (InfoGeneralReglaCanalComercializacion) o;
    return Objects.equals(this.idCanal, infoGeneralReglaCanalComercializacion.idCanal) &&
        Objects.equals(this.canal, infoGeneralReglaCanalComercializacion.canal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCanal, canal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoGeneralReglaCanalComercializacion {\n");
    
    sb.append("    idCanal: ").append(toIndentedString(idCanal)).append("\n");
    sb.append("    canal: ").append(toIndentedString(canal)).append("\n");
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

