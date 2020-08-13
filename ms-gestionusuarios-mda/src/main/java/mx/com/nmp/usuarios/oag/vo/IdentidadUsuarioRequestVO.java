package mx.com.nmp.usuarios.oag.vo;

public class IdentidadUsuarioRequestVO {

	private FiltroVO filtro;
	private String grupo;
	
	public FiltroVO getFiltro() {
		return filtro;
	}
	public void setFiltro(FiltroVO filtro) {
		this.filtro = filtro;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("IdentidadUsuarioRequestVO {\n");
		 sb.append("filtro: ").append(toIndentedString(filtro)).append("\n");
		 sb.append("grupo: ").append(toIndentedString(grupo)).append("\n");
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
