package mx.com.nmp.usuarios.oag.vo;

public class BusquedaGrupoVO {

	private String grupo;
	private Boolean directo;
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Boolean getDirecto() {
		return directo;
	}

	public void setDirecto(Boolean directo) {
		this.directo = directo;
	}

	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("BusquedaGrupoVo {\n");
		 sb.append("grupo: ").append(toIndentedString(grupo)).append("\n");
		 sb.append("directo: ").append(toIndentedString(directo)).append("\n");
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
