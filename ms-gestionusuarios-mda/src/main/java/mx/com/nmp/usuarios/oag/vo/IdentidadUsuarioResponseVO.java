package mx.com.nmp.usuarios.oag.vo;

import java.util.List;

public class IdentidadUsuarioResponseVO {

	private List<UsuarioVO> usuario;

	public List<UsuarioVO> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<UsuarioVO> usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("IdentidadUsuarioResponseVO {\n");
		 sb.append("usuario: ").append(toIndentedString(usuario)).append("\n");
		 sb.append("}");
		return sb.toString();
	}
	
  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}
