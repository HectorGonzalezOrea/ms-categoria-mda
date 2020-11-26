package mx.com.nmp.categorias.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Categorias")
public class CategoriaEntity {

	@Transient
	public static final String SEQUENCE_NAME = "categoria_sequence";

	@Id
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
