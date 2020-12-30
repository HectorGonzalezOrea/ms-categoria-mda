package mx.com.nmp.usuarios.mongodb.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "perfil-capacidad")
public class PerfilCapacidadEntity {

	@Id
	private ObjectId _id;
	private Integer perfil;
	private Integer idCapacidad;
	private String grupo;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Integer getPerfil() {
		return perfil;
	}
	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}
	public Integer getIdCapacidad() {
		return idCapacidad;
	}
	public void setIdCapacidad(Integer idCapacidad) {
		this.idCapacidad = idCapacidad;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
}


