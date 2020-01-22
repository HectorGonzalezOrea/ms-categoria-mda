package mx.com.nmp.usuarios.oag.vo;

public class UsuarioVO {

	private String idUsuario;
	private String nombreDominio;
	private String nombreCompleto;
	private String descripcion;
	private String guid;
	private String nombreDistintivo;
	private String tipoIdentidad;
	private String correo;
	private String nombre;
	private String apellidos;
	private String jefe;
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreDominio() {
		return nombreDominio;
	}
	public void setNombreDominio(String nombreDominio) {
		this.nombreDominio = nombreDominio;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getNombreDistintivo() {
		return nombreDistintivo;
	}
	public void setNombreDistintivo(String nombreDistintivo) {
		this.nombreDistintivo = nombreDistintivo;
	}
	public String getTipoIdentidad() {
		return tipoIdentidad;
	}
	public void setTipoIdentidad(String tipoIdentidad) {
		this.tipoIdentidad = tipoIdentidad;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getJefe() {
		return jefe;
	}
	public void setJefe(String jefe) {
		this.jefe = jefe;
	}
	
	@Override
	public String toString() {
		return "UsuarioVO [idUsuario=" + idUsuario + ", nombreDominio=" + nombreDominio + ", nombreCompleto="
				+ nombreCompleto + ", descripcion=" + descripcion + ", guid=" + guid + ", nombreDistintivo="
				+ nombreDistintivo + ", tipoIdentidad=" + tipoIdentidad + ", correo=" + correo + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", jefe=" + jefe + "]";
	}
	
}
