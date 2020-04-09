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
	private String puesto;
	private String nombre;
	private String segundoNombre;
	private String apellidos;
	private String telefonoOficina;
	private String telefonoTrabajo;
	private String telefono;
	private String telefonoMovil;
	private String localizador;
	private String fax;
	private String jefe;
	private String zona;
	private String preferenciasIdioma;
	private String preferenciasNotificacion;
	
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
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefonoOficina() {
		return telefonoOficina;
	}
	public void setTelefonoOficina(String telefonoOficina) {
		this.telefonoOficina = telefonoOficina;
	}
	public String getTelefonoTrabajo() {
		return telefonoTrabajo;
	}
	public void setTelefonoTrabajo(String telefonoTrabajo) {
		this.telefonoTrabajo = telefonoTrabajo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}
	public String getLocalizador() {
		return localizador;
	}
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getJefe() {
		return jefe;
	}
	public void setJefe(String jefe) {
		this.jefe = jefe;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getPreferenciasIdioma() {
		return preferenciasIdioma;
	}
	public void setPreferenciasIdioma(String preferenciasIdioma) {
		this.preferenciasIdioma = preferenciasIdioma;
	}
	public String getPreferenciasNotificacion() {
		return preferenciasNotificacion;
	}
	public void setPreferenciasNotificacion(String preferenciasNotificacion) {
		this.preferenciasNotificacion = preferenciasNotificacion;
	}
	
	@Override
	public String toString() {
		return "UsuarioVO [idUsuario=" + idUsuario + ", nombreDominio=" + nombreDominio + ", nombreCompleto="
				+ nombreCompleto + ", descripcion=" + descripcion + ", guid=" + guid + ", nombreDistintivo="
				+ nombreDistintivo + ", tipoIdentidad=" + tipoIdentidad + ", correo=" + correo + ", puesto=" + puesto
				+ ", nombre=" + nombre + ", segundoNombre=" + segundoNombre + ", apellidos=" + apellidos
				+ ", telefonoOficina=" + telefonoOficina + ", telefonoTrabajo=" + telefonoTrabajo + ", telefono="
				+ telefono + ", telefonoMovil=" + telefonoMovil + ", localizador=" + localizador + ", fax=" + fax
				+ ", jefe=" + jefe + ", zona=" + zona + ", preferenciasIdioma=" + preferenciasIdioma
				+ ", preferenciasNotificacion=" + preferenciasNotificacion + "]";
	}
	
}
