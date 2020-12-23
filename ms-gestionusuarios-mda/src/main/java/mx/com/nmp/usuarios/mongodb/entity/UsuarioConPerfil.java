package mx.com.nmp.usuarios.mongodb.entity;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuariosConPerfil")
public class UsuarioConPerfil {

	@Id
	private ObjectId _id;
	private Long idUsuario;
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
	
	private String uid;
	private String mail;
	private String physicaldeliveryofficename;
	private String department;
	private String lastname;
	private String firstname;
	private String uri;
	private List<String> memberof = null;
	private String title;
	private String distinguishedname;
	private String description;
	private String samaccountname;
	private String commonname;
	private Boolean activo;
	private PuestoEntity[] puestoO;
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhysicaldeliveryofficename() {
		return physicaldeliveryofficename;
	}
	public void setPhysicaldeliveryofficename(String physicaldeliveryofficename) {
		this.physicaldeliveryofficename = physicaldeliveryofficename;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public List<String> getMemberof() {
		return memberof;
	}
	public void setMemberof(List<String> memberof) {
		this.memberof = memberof;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDistinguishedname() {
		return distinguishedname;
	}
	public void setDistinguishedname(String distinguishedname) {
		this.distinguishedname = distinguishedname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSamaccountname() {
		return samaccountname;
	}
	public void setSamaccountname(String samaccountname) {
		this.samaccountname = samaccountname;
	}
	public String getCommonname() {
		return commonname;
	}
	public void setCommonname(String commonname) {
		this.commonname = commonname;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public PuestoEntity[] getPuestoO() {
		return puestoO;
	}
	public void setPuestoO(PuestoEntity[] puestoO) {
		this.puestoO = puestoO;
	}

	@Override
	public String toString() {
		return "UsuarioConPerfil [_id=" + _id + ", idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellidoPaterno="
				+ apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", usuario=" + usuario + ", puesto="
				+ puesto + ", direccion=" + direccion + ", subdireccion=" + subdireccion + ", gerencia=" + gerencia
				+ ", departamentoArea=" + departamentoArea + ", perfil=" + perfil + ", uid=" + uid + ", mail=" + mail
				+ ", physicaldeliveryofficename=" + physicaldeliveryofficename + ", department=" + department
				+ ", lastname=" + lastname + ", firstname=" + firstname + ", uri=" + uri + ", memberof=" + memberof
				+ ", title=" + title + ", distinguishedname=" + distinguishedname + ", description=" + description
				+ ", samaccountname=" + samaccountname + ", commonname=" + commonname + ", activo=" + activo
				+ ", puestoO=" + Arrays.toString(puestoO) + "]";
	}
	
}
