package mx.com.nmp.usuarios.mongodb.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "historialUsuario_TMP")
public class HistorialEntity {
	
	@Transient
    public static final String SEQUENCE_NAME = "historico_sequence";
	
	@Id
	private ObjectId _id;
	private Long idHistorialUsuario; 
	private Date fecha;
	private String usuario;
	private Integer idPerfil;
	private String accion;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Long getIdHistorialUsuario() {
		return idHistorialUsuario;
	}
	public void setIdHistorialUsuario(Long idHistorialUsuario) {
		this.idHistorialUsuario = idHistorialUsuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Integer getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
}
