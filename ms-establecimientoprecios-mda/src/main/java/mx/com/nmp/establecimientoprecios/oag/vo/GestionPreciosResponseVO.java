package mx.com.nmp.establecimientoprecios.oag.vo;

public class GestionPreciosResponseVO {

	private String codigo;
	private String descripcion;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class GestionPreciosResponseVO {\n");
		sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
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