package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * ConsultaUsuarioRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

public class ConsultaUsuarioRes   {
  @JsonProperty("usuarios")
  @Valid
  private List<InfoUsuario> usuarios = null;

  public ConsultaUsuarioRes usuarios(List<InfoUsuario> usuarios) {
    this.usuarios = usuarios;
    return this;
  }

  public ConsultaUsuarioRes addUsuariosItem(InfoUsuario usuariosItem) {
    if (this.usuarios == null) {
      this.usuarios = new ArrayList<InfoUsuario>();
    }
    this.usuarios.add(usuariosItem);
    return this;
  }

  /**
   * Respuesta a la peticion de consulta de los usuarios actualmente registrados.
   * @return usuarios
  **/
  @ApiModelProperty(value = "Respuesta a la peticion de consulta de los usuarios actualmente registrados.")

  @Valid

  public List<InfoUsuario> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<InfoUsuario> usuarios) {
    this.usuarios = usuarios;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsultaUsuarioRes consultaUsuarioRes = (ConsultaUsuarioRes) o;
    return Objects.equals(this.usuarios, consultaUsuarioRes.usuarios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuarios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsultaUsuarioRes {\n");
    
    sb.append("    usuarios: ").append(toIndentedString(usuarios)).append("\n");
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

