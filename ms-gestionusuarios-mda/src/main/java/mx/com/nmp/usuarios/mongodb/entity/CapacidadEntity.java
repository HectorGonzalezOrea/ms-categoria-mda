package mx.com.nmp.usuarios.mongodb.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "capacidad_TMP")
public class CapacidadEntity {
	@Id
	private ObjectId _id;
	private Integer idCapacidad;
	private String nombre;
	private String descripcion;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Integer getIdCapacidad() {
		return idCapacidad;
	}
	public void setIdCapacidad(Integer idCapacidad) {
		this.idCapacidad = idCapacidad;
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
