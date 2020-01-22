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
		return "IdentidadUsuarioRequestVO [filtro=" + filtro + ", grupo=" + grupo + "]";
	}
	
}
