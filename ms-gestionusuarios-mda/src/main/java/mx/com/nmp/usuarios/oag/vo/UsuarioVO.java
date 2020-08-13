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
		
		 StringBuilder sb = new StringBuilder();
		 sb.append("UsuarioVO {\n");
		 sb.append("idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
		 sb.append("nombreDominio: ").append(toIndentedString(nombreDominio)).append("\n");
		 sb.append("nombreCompleto: ").append(toIndentedString(nombreCompleto)).append("\n");
		 sb.append("descripcion: ").append(toIndentedString(descripcion)).append("\n");
		 sb.append("guid: ").append(toIndentedString(guid)).append("\n");
		 sb.append("nombreDistintivo: ").append(toIndentedString(nombreDistintivo)).append("\n");
		 sb.append("tipoIdentidad: ").append(toIndentedString(tipoIdentidad)).append("\n");
		 sb.append("correo: ").append(toIndentedString(correo)).append("\n");
		 sb.append("puesto: ").append(toIndentedString(puesto)).append("\n");
		 sb.append("nombre: ").append(toIndentedString(nombre)).append("\n");
		 sb.append("segundoNombre: ").append(toIndentedString(segundoNombre)).append("\n");
		 sb.append("apellidos: ").append(toIndentedString(apellidos)).append("\n");
		 sb.append("telefonoOficina: ").append(toIndentedString(telefonoOficina)).append("\n");
		 sb.append("telefonoTrabajo: ").append(toIndentedString(telefonoTrabajo)).append("\n");
		 sb.append("telefono: ").append(toIndentedString(telefono)).append("\n");
		 sb.append("telefonoMovil: ").append(toIndentedString(telefonoMovil)).append("\n");
		 sb.append("localizador: ").append(toIndentedString(localizador)).append("\n");
		 sb.append("fax: ").append(toIndentedString(fax)).append("\n");
		 sb.append("jefe: ").append(toIndentedString(jefe)).append("\n");
		 sb.append("zona: ").append(toIndentedString(zona)).append("\n");
		 sb.append("preferenciasIdioma: ").append(toIndentedString(preferenciasIdioma)).append("\n");
		 sb.append("preferenciasNotificacion: ").append(toIndentedString(preferenciasNotificacion)).append("\n");
		 sb.append("}");
		 
		return sb.toString();
	}
	
	 private String toIndentedString(java.lang.Object o) {
		    if (o == null) {
		      return "null";
	   }
	   return o.toString().replace("\n", "\n    ");
	 }
	
}
