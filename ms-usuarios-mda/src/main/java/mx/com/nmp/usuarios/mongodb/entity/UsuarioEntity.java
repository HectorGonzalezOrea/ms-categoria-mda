package mx.com.nmp.usuarios.mongodb.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;

@Document(collection = "InfoUsuario_TMP")
public class UsuarioEntity {
	
	@Id
	private Long _id;
	private Integer idUsuario;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String usuario;
	private Integer puesto;
	private Integer direccion;
	private Integer subdireccion;
	private Integer gerencia;
	private Integer departamentoArea;
	private Integer perfil;
	private Boolean activo;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Integer getPuesto() {
		return puesto;
	}
	public void setPuesto(Integer puesto) {
		this.puesto = puesto;
	}
	public Integer getDireccion() {
		return direccion;
	}
	public void setDireccion(Integer direccion) {
		this.direccion = direccion;
	}
	public Integer getSubdireccion() {
		return subdireccion;
	}
	public void setSubdireccion(Integer subdireccion) {
		this.subdireccion = subdireccion;
	}
	public Integer getGerencia() {
		return gerencia;
	}
	public void setGerencia(Integer gerencia) {
		this.gerencia = gerencia;
	}
	public Integer getDepartamentoArea() {
		return departamentoArea;
	}
	public void setDepartamentoArea(Integer departamentoArea) {
		this.departamentoArea = departamentoArea;
	}
	public Integer getPerfil() {
		return perfil;
	}
	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public void setPerfil(CapacidadUsuariosRes perfil2) {
		// TODO Auto-generated method stub
		
	}
	
	
}
