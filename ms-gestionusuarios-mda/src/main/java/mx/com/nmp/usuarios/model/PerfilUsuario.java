package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PerfilUsuario
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-14T18:11:19.533Z")

public class PerfilUsuario extends InfoUsuario {
	@JsonProperty("nombreDistinguido")
	private String nombreDistinguido = null;

	@JsonProperty("miembroDe")
	@Valid
	private List<String> miembroDe = null;

	@JsonProperty("nombreDominio")
	private String nombreDominio = null;

	@JsonProperty("nombreCompleto")
	private String nombreCompleto = null;

	@JsonProperty("descripcion")
	private String descripcion = null;

	@JsonProperty("guid")
	private String guid = null;

	@JsonProperty("tipoIdentidad")
	private String tipoIdentidad = null;

	@JsonProperty("correo")
	private String correo = null;

	@JsonProperty("telefonoOficina")
	private String telefonoOficina = null;

	@JsonProperty("telefonoTrabajo")
	private String telefonoTrabajo = null;

	@JsonProperty("telefono")
	private String telefono = null;

	@JsonProperty("telefonoMovil")
	private String telefonoMovil = null;

	@JsonProperty("localizador")
	private String localizador = null;

	@JsonProperty("fax")
	private String fax = null;

	@JsonProperty("jefe")
	private String jefe = null;

	@JsonProperty("zona")
	private String zona = null;

	@JsonProperty("preferenciasIdioma")
	private String preferenciasIdioma = null;

	@JsonProperty("preferenciasNotificacion")
	private String preferenciasNotificacion = null;

	public PerfilUsuario nombreDistinguido(String nombreDistinguido) {
		this.nombreDistinguido = nombreDistinguido;
		return this;
	}

	/**
	 * Nombre distinguido del usuario
	 * 
	 * @return nombreDistinguido
	 **/
	@ApiModelProperty(example = "CN=Juan Perez Hernandez,OU=SPS,OU=Consultores,OU=Usuarios,DC=nmp,DC=com,DC=mx", value = "Nombre distinguido del usuario")

	public String getNombreDistinguido() {
		return nombreDistinguido;
	}

	public void setNombreDistinguido(String nombreDistinguido) {
		this.nombreDistinguido = nombreDistinguido;
	}

	public PerfilUsuario miembroDe(List<String> miembroDe) {
		this.miembroDe = miembroDe;
		return this;
	}

	public PerfilUsuario addMiembroDeItem(String miembroDeItem) {
		if (this.miembroDe == null) {
			this.miembroDe = new ArrayList<String>();
		}
		this.miembroDe.add(miembroDeItem);
		return this;
	}

	/**
	 * Información de los grupos a los que pertenece el usuario
	 * 
	 * @return miembroDe
	 **/
	@ApiModelProperty(value = "Información de los grupos a los que pertenece el usuario")

	public List<String> getMiembroDe() {
		return miembroDe;
	}

	public void setMiembroDe(List<String> miembroDe) {
		this.miembroDe = miembroDe;
	}

	public PerfilUsuario nombreDominio(String nombreDominio) {
		this.nombreDominio = nombreDominio;
		return this;
	}

	/**
	 * Nombre de Dominio del usuario
	 * 
	 * @return nombreDominio
	 **/
	@ApiModelProperty(example = "CN=Juan Perez Hernandez,OU=SPS,OU=Consultores,OU=Usuarios,DC=nmp,DC=com,DC=mx", value = "Nombre de Dominio del usuario")

	public String getNombreDominio() {
		return nombreDominio;
	}

	public void setNombreDominio(String nombreDominio) {
		this.nombreDominio = nombreDominio;
	}

	public PerfilUsuario nombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
		return this;
	}

	/**
	 * Nombre completo del usuario
	 * 
	 * @return nombreCompleto
	 **/
	@ApiModelProperty(example = "Juan Perez Hernandez", value = "Nombre completo del usuario")

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public PerfilUsuario descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	/**
	 * Descripción del usuario
	 * 
	 * @return descripcion
	 **/
	@ApiModelProperty(example = "Descripción", value = "Descripción del usuario")

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public PerfilUsuario guid(String guid) {
		this.guid = guid;
		return this;
	}

	/**
	 * GUID
	 * 
	 * @return guid
	 **/
	@ApiModelProperty(example = "b0c21bfc537e466b6e323dcc5fe0b23f", value = "GUID")

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public PerfilUsuario tipoIdentidad(String tipoIdentidad) {
		this.tipoIdentidad = tipoIdentidad;
		return this;
	}

	/**
	 * Tipo de identidad
	 * 
	 * @return tipoIdentidad
	 **/
	@ApiModelProperty(example = "user", value = "Tipo de identidad")

	public String getTipoIdentidad() {
		return tipoIdentidad;
	}

	public void setTipoIdentidad(String tipoIdentidad) {
		this.tipoIdentidad = tipoIdentidad;
	}

	public PerfilUsuario correo(String correo) {
		this.correo = correo;
		return this;
	}

	/**
	 * Correo del usuario
	 * 
	 * @return correo
	 **/
	@ApiModelProperty(example = "jperezh@montepiedad.com.mx", value = "Correo del usuario")

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public PerfilUsuario telefonoOficina(String telefonoOficina) {
		this.telefonoOficina = telefonoOficina;
		return this;
	}

	/**
	 * Teléfono de la Oficina del usuario
	 * 
	 * @return telefonoOficina
	 **/
	@ApiModelProperty(value = "Teléfono de la Oficina del usuario")

	public String getTelefonoOficina() {
		return telefonoOficina;
	}

	public void setTelefonoOficina(String telefonoOficina) {
		this.telefonoOficina = telefonoOficina;
	}

	public PerfilUsuario telefonoTrabajo(String telefonoTrabajo) {
		this.telefonoTrabajo = telefonoTrabajo;
		return this;
	}

	/**
	 * Teléfono de trabajo del usuario
	 * 
	 * @return telefonoTrabajo
	 **/
	@ApiModelProperty(value = "Teléfono de trabajo del usuario")

	public String getTelefonoTrabajo() {
		return telefonoTrabajo;
	}

	public void setTelefonoTrabajo(String telefonoTrabajo) {
		this.telefonoTrabajo = telefonoTrabajo;
	}

	public PerfilUsuario telefono(String telefono) {
		this.telefono = telefono;
		return this;
	}

	/**
	 * Teléfono del usuario
	 * 
	 * @return telefono
	 **/
	@ApiModelProperty(value = "Teléfono del usuario")

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public PerfilUsuario telefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
		return this;
	}

	/**
	 * Teléfono Móvil del usuario
	 * 
	 * @return telefonoMovil
	 **/
	@ApiModelProperty(value = "Teléfono Móvil del usuario")

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public PerfilUsuario localizador(String localizador) {
		this.localizador = localizador;
		return this;
	}

	/**
	 * Localizador del usuario
	 * 
	 * @return localizador
	 **/
	@ApiModelProperty(value = "Localizador del usuario")

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	public PerfilUsuario fax(String fax) {
		this.fax = fax;
		return this;
	}

	/**
	 * Fax del usuario
	 * 
	 * @return fax
	 **/
	@ApiModelProperty(value = "Fax del usuario")

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public PerfilUsuario jefe(String jefe) {
		this.jefe = jefe;
		return this;
	}

	/**
	 * Jefe del usuario
	 * 
	 * @return jefe
	 **/
	@ApiModelProperty(example = "jperezhe", value = "Jefe del usuario")

	public String getJefe() {
		return jefe;
	}

	public void setJefe(String jefe) {
		this.jefe = jefe;
	}

	public PerfilUsuario zona(String zona) {
		this.zona = zona;
		return this;
	}

	/**
	 * Zona
	 * 
	 * @return zona
	 **/
	@ApiModelProperty(value = "Zona")

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public PerfilUsuario preferenciasIdioma(String preferenciasIdioma) {
		this.preferenciasIdioma = preferenciasIdioma;
		return this;
	}

	/**
	 * Preferencias de idioma
	 * 
	 * @return preferenciasIdioma
	 **/
	@ApiModelProperty(value = "Preferencias de idioma")

	public String getPreferenciasIdioma() {
		return preferenciasIdioma;
	}

	public void setPreferenciasIdioma(String preferenciasIdioma) {
		this.preferenciasIdioma = preferenciasIdioma;
	}

	public PerfilUsuario preferenciasNotificacion(String preferenciasNotificacion) {
		this.preferenciasNotificacion = preferenciasNotificacion;
		return this;
	}

	/**
	 * Preferencias de notificaciones
	 * 
	 * @return preferenciasNotificacion
	 **/
	@ApiModelProperty(value = "Preferencias de notificaciones")

	public String getPreferenciasNotificacion() {
		return preferenciasNotificacion;
	}

	public void setPreferenciasNotificacion(String preferenciasNotificacion) {
		this.preferenciasNotificacion = preferenciasNotificacion;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PerfilUsuario perfilUsuario = (PerfilUsuario) o;
		return Objects.equals(this.nombreDistinguido, perfilUsuario.nombreDistinguido)
				&& Objects.equals(this.miembroDe, perfilUsuario.miembroDe)
				&& Objects.equals(this.nombreDominio, perfilUsuario.nombreDominio)
				&& Objects.equals(this.nombreCompleto, perfilUsuario.nombreCompleto)
				&& Objects.equals(this.descripcion, perfilUsuario.descripcion)
				&& Objects.equals(this.guid, perfilUsuario.guid)
				&& Objects.equals(this.tipoIdentidad, perfilUsuario.tipoIdentidad)
				&& Objects.equals(this.correo, perfilUsuario.correo)
				&& Objects.equals(this.telefonoOficina, perfilUsuario.telefonoOficina)
				&& Objects.equals(this.telefonoTrabajo, perfilUsuario.telefonoTrabajo)
				&& Objects.equals(this.telefono, perfilUsuario.telefono)
				&& Objects.equals(this.telefonoMovil, perfilUsuario.telefonoMovil)
				&& Objects.equals(this.localizador, perfilUsuario.localizador)
				&& Objects.equals(this.fax, perfilUsuario.fax) && Objects.equals(this.jefe, perfilUsuario.jefe)
				&& Objects.equals(this.zona, perfilUsuario.zona)
				&& Objects.equals(this.preferenciasIdioma, perfilUsuario.preferenciasIdioma)
				&& Objects.equals(this.preferenciasNotificacion, perfilUsuario.preferenciasNotificacion)
				&& super.equals(o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreDistinguido, miembroDe, nombreDominio, nombreCompleto, descripcion, guid,
				tipoIdentidad, correo, telefonoOficina, telefonoTrabajo, telefono, telefonoMovil, localizador, fax,
				jefe, zona, preferenciasIdioma, preferenciasNotificacion, super.hashCode());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PerfilUsuario {\n");
		sb.append("    ").append(toIndentedString(super.toString())).append("\n");
		sb.append("    nombreDistinguido: ").append(toIndentedString(nombreDistinguido)).append("\n");
		sb.append("    miembroDe: ").append(toIndentedString(miembroDe)).append("\n");
		sb.append("    nombreDominio: ").append(toIndentedString(nombreDominio)).append("\n");
		sb.append("    nombreCompleto: ").append(toIndentedString(nombreCompleto)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    guid: ").append(toIndentedString(guid)).append("\n");
		sb.append("    tipoIdentidad: ").append(toIndentedString(tipoIdentidad)).append("\n");
		sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
		sb.append("    telefonoOficina: ").append(toIndentedString(telefonoOficina)).append("\n");
		sb.append("    telefonoTrabajo: ").append(toIndentedString(telefonoTrabajo)).append("\n");
		sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
		sb.append("    telefonoMovil: ").append(toIndentedString(telefonoMovil)).append("\n");
		sb.append("    localizador: ").append(toIndentedString(localizador)).append("\n");
		sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
		sb.append("    jefe: ").append(toIndentedString(jefe)).append("\n");
		sb.append("    zona: ").append(toIndentedString(zona)).append("\n");
		sb.append("    preferenciasIdioma: ").append(toIndentedString(preferenciasIdioma)).append("\n");
		sb.append("    preferenciasNotificacion: ").append(toIndentedString(preferenciasNotificacion)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
