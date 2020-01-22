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
		return "GestionPreciosResponseVO [codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}
	
}