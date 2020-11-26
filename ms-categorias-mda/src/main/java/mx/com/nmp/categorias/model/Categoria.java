package mx.com.nmp.categorias.model;

public class Categoria {
	private Long idCategoria;
	private String descripcion;
	private String tipo;

	public Long getIdCategoria() {
		return idCategoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
