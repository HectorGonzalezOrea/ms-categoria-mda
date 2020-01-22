package mx.com.nmp.usuarios.mongodb.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gerencia_TMP")
public class GerenciaEntity {
	
	@Id
	private ObjectId _id;
	private Integer idGerencia;
	private String nombre;
	private String descripcion;
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Integer getIdGerencia() {
		return idGerencia;
	}
	public void setIdGerencia(Integer idGerencia) {
		this.idGerencia = idGerencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
