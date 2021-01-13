package mx.com.nmp.escenariosdinamicos.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * CrearEscenariosReq
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

public class CrearEscenariosReq   {
  /**
   * Comportamiento para el día uno basado en la demanda
   */   
  @JsonProperty("diaUno")
  private String diaUno = null;
  /**
   * Comportamiento para el día dos basado en la demanda
   */
  @NotNull
  @JsonProperty("diaDos")
  private String diaDos = null;
  /**
   * Comportamiento para el día tres basado en la demanda
   */
  @NotNull
  @JsonProperty("diaTres")
  private String diaTres = null;
  /**
   * Regla a aplicar a la suma de comportamiento de los 3 últimos días
   */
  @NotNull
  @JsonProperty("idRegla")
  private String idRegla = null;

  public String getDiaUno() {
	return diaUno;
}

public void setDiaUno(String diaUno) {
	this.diaUno = diaUno;
}

public String getDiaDos() {
	return diaDos;
}

public void setDiaDos(String diaDos) {
	this.diaDos = diaDos;
}

public String getDiaTres() {
	return diaTres;
}

public void setDiaTres(String diaTres) {
	this.diaTres = diaTres;
}

public String getIdRegla() {
	return idRegla;
}

public void setIdRegla(String idRegla) {
	this.idRegla = idRegla;
}

@Override
  public int hashCode() {
    return Objects.hash(diaUno, diaDos, diaTres, idRegla);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CrearEscenariosReq {\n");
    
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

