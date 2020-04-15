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
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("realmName")
	private String realmName = null;

	@JsonProperty("displayName")
	private String displayName = null;

	@JsonProperty("memberOf")
	@Valid
	private List<String> memberOf = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("guid")
	private String guid = null;

	@JsonProperty("uniqueName")
	private String uniqueName = null;

	@JsonProperty("identityType")
	private String identityType = null;

	@JsonProperty("email")
	private String email = null;

	@JsonProperty("title")
	private String title = null;

	@JsonProperty("firstName")
	private String firstName = null;

	@JsonProperty("middleName")
	private String middleName = null;

	@JsonProperty("lastName")
	private String lastName = null;

	@JsonProperty("workPhone")
	private String workPhone = null;

	@JsonProperty("workNumber")
	private String workNumber = null;

	@JsonProperty("homePhone")
	private String homePhone = null;

	@JsonProperty("mobile")
	private String mobile = null;

	@JsonProperty("pager")
	private String pager = null;

	@JsonProperty("fax")
	private String fax = null;

	@JsonProperty("manager")
	private String manager = null;

	@JsonProperty("timeZone")
	private String timeZone = null;

	@JsonProperty("languagePreference")
	private String languagePreference = null;

	@JsonProperty("notificationPreferences")
	private String notificationPreferences = null;

	public PerfilUsuario name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Nombre del usuario (oauth2/profile)
	 * 
	 * @return name
	 **/
	@ApiModelProperty(example = "Juan Perez Hernandez", value = "Nombre del usuario (oauth2/profile)")

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PerfilUsuario realmName(String realmName) {
		this.realmName = realmName;
		return this;
	}

	/**
	 * Nombre de Dominio del usuario (oauth2/profile)
	 * 
	 * @return realmName
	 **/
	@ApiModelProperty(example = "CN=Juan Perez Hernandez,OU=SPS,OU=Consultores,OU=Usuarios,DC=nmp,DC=com,DC=mx", value = "Nombre de Dominio del usuario (oauth2/profile)")

	public String getRealmName() {
		return realmName;
	}

	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

	public PerfilUsuario displayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	/**
	 * Nombre completo del usuario (oauth2/profile)
	 * 
	 * @return displayName
	 **/
	@ApiModelProperty(example = "Juan Perez Hernandez", value = "Nombre completo del usuario (oauth2/profile)")

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public PerfilUsuario memberOf(List<String> memberOf) {
		this.memberOf = memberOf;
		return this;
	}

	public PerfilUsuario addMemberOfItem(String memberOfItem) {
		if (this.memberOf == null) {
			this.memberOf = new ArrayList<String>();
		}
		this.memberOf.add(memberOfItem);
		return this;
	}

	/**
	 * Información de los grupos a los que pertenece el usuario (oauth2/profile)
	 * 
	 * @return memberOf
	 **/
	@ApiModelProperty(value = "Información de los grupos a los que pertenece el usuario (oauth2/profile)")

	public List<String> getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(List<String> memberOf) {
		this.memberOf = memberOf;
	}

	public PerfilUsuario description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Descripción del usuario (oauth2/profile)
	 * 
	 * @return description
	 **/
	@ApiModelProperty(example = "Descripción", value = "Descripción del usuario (oauth2/profile)")

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PerfilUsuario guid(String guid) {
		this.guid = guid;
		return this;
	}

	/**
	 * GUID (oauth2/profile)
	 * 
	 * @return guid
	 **/
	@ApiModelProperty(example = "b0c21bfc537e466b6e323dcc5fe0b23f", value = "GUID (oauth2/profile)")

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public PerfilUsuario uniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
		return this;
	}

	/**
	 * Nombre distinguido del usuario (oauth2/profile)
	 * 
	 * @return uniqueName
	 **/
	@ApiModelProperty(example = "CN=Juan Perez Hernandez,OU=SPS,OU=Consultores,OU=Usuarios,DC=nmp,DC=com,DC=mx", value = "Nombre distinguido del usuario (oauth2/profile)")

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public PerfilUsuario identityType(String identityType) {
		this.identityType = identityType;
		return this;
	}

	/**
	 * Tipo de identidad (oauth2/profile)
	 * 
	 * @return identityType
	 **/
	@ApiModelProperty(example = "user", value = "Tipo de identidad (oauth2/profile)")

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public PerfilUsuario email(String email) {
		this.email = email;
		return this;
	}

	/**
	 * Correo del usuario (oauth2/profile)
	 * 
	 * @return email
	 **/
	@ApiModelProperty(example = "jperezh@montepiedad.com.mx", value = "Correo del usuario (oauth2/profile)")

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PerfilUsuario title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Titulo del usuario (oauth2/profile)
	 * 
	 * @return title
	 **/
	@ApiModelProperty(example = "Consultor", value = "Titulo del usuario (oauth2/profile)")

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PerfilUsuario firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * Primer nombre usuario (oauth2/profile)
	 * 
	 * @return firstName
	 **/
	@ApiModelProperty(example = "Juan", value = "Primer nombre usuario (oauth2/profile)")

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public PerfilUsuario middleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	/**
	 * Segundo nombre usuario (oauth2/profile)
	 * 
	 * @return middleName
	 **/
	@ApiModelProperty(example = "", value = "Segundo nombre usuario (oauth2/profile)")

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public PerfilUsuario lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * Apellido del usuario (oauth2/profile)
	 * 
	 * @return lastName
	 **/
	@ApiModelProperty(example = "Perez Hernandez", value = "Apellido del usuario (oauth2/profile)")

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public PerfilUsuario workPhone(String workPhone) {
		this.workPhone = workPhone;
		return this;
	}

	/**
	 * Teléfono de la Oficina del usuario (oauth2/profile)
	 * 
	 * @return workPhone
	 **/
	@ApiModelProperty(value = "Teléfono de la Oficina del usuario (oauth2/profile)")

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public PerfilUsuario workNumber(String workNumber) {
		this.workNumber = workNumber;
		return this;
	}

	/**
	 * Teléfono de trabajo del usuario (oauth2/profile)
	 * 
	 * @return workNumber
	 **/
	@ApiModelProperty(value = "Teléfono de trabajo del usuario (oauth2/profile)")

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public PerfilUsuario homePhone(String homePhone) {
		this.homePhone = homePhone;
		return this;
	}

	/**
	 * Teléfono del usuario (oauth2/profile)
	 * 
	 * @return homePhone
	 **/
	@ApiModelProperty(value = "Teléfono del usuario (oauth2/profile)")

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public PerfilUsuario mobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	/**
	 * Teléfono Móvil del usuario (oauth2/profile)
	 * 
	 * @return mobile
	 **/
	@ApiModelProperty(value = "Teléfono Móvil del usuario (oauth2/profile)")

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public PerfilUsuario pager(String pager) {
		this.pager = pager;
		return this;
	}

	/**
	 * Localizador del usuario (oauth2/profile)
	 * 
	 * @return pager
	 **/
	@ApiModelProperty(value = "Localizador del usuario (oauth2/profile)")

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public PerfilUsuario fax(String fax) {
		this.fax = fax;
		return this;
	}

	/**
	 * Fax del usuario (oauth2/profile)
	 * 
	 * @return fax
	 **/
	@ApiModelProperty(value = "Fax del usuario (oauth2/profile)")

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public PerfilUsuario manager(String manager) {
		this.manager = manager;
		return this;
	}

	/**
	 * Jefe del usuario (oauth2/profile)
	 * 
	 * @return manager
	 **/
	@ApiModelProperty(example = "jperezhe", value = "Jefe del usuario (oauth2/profile)")

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public PerfilUsuario timeZone(String timeZone) {
		this.timeZone = timeZone;
		return this;
	}

	/**
	 * Zona (oauth2/profile)
	 * 
	 * @return timeZone
	 **/
	@ApiModelProperty(value = "Zona (oauth2/profile)")

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public PerfilUsuario languagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
		return this;
	}

	/**
	 * Preferencias de idioma (oauth2/profile)
	 * 
	 * @return languagePreference
	 **/
	@ApiModelProperty(value = "Preferencias de idioma (oauth2/profile)")

	public String getLanguagePreference() {
		return languagePreference;
	}

	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}

	public PerfilUsuario notificationPreferences(String notificationPreferences) {
		this.notificationPreferences = notificationPreferences;
		return this;
	}

	/**
	 * Preferencias de notificaciones (oauth2/profile)
	 * 
	 * @return notificationPreferences
	 **/
	@ApiModelProperty(value = "Preferencias de notificaciones (oauth2/profile)")

	public String getNotificationPreferences() {
		return notificationPreferences;
	}

	public void setNotificationPreferences(String notificationPreferences) {
		this.notificationPreferences = notificationPreferences;
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
		return Objects.equals(this.name, perfilUsuario.name) && Objects.equals(this.realmName, perfilUsuario.realmName)
				&& Objects.equals(this.displayName, perfilUsuario.displayName)
				&& Objects.equals(this.memberOf, perfilUsuario.memberOf)
				&& Objects.equals(this.description, perfilUsuario.description)
				&& Objects.equals(this.guid, perfilUsuario.guid)
				&& Objects.equals(this.uniqueName, perfilUsuario.uniqueName)
				&& Objects.equals(this.identityType, perfilUsuario.identityType)
				&& Objects.equals(this.email, perfilUsuario.email) && Objects.equals(this.title, perfilUsuario.title)
				&& Objects.equals(this.firstName, perfilUsuario.firstName)
				&& Objects.equals(this.middleName, perfilUsuario.middleName)
				&& Objects.equals(this.lastName, perfilUsuario.lastName)
				&& Objects.equals(this.workPhone, perfilUsuario.workPhone)
				&& Objects.equals(this.workNumber, perfilUsuario.workNumber)
				&& Objects.equals(this.homePhone, perfilUsuario.homePhone)
				&& Objects.equals(this.mobile, perfilUsuario.mobile) && Objects.equals(this.pager, perfilUsuario.pager)
				&& Objects.equals(this.fax, perfilUsuario.fax) && Objects.equals(this.manager, perfilUsuario.manager)
				&& Objects.equals(this.timeZone, perfilUsuario.timeZone)
				&& Objects.equals(this.languagePreference, perfilUsuario.languagePreference)
				&& Objects.equals(this.notificationPreferences, perfilUsuario.notificationPreferences)
				&& super.equals(o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, realmName, displayName, memberOf, description, guid, uniqueName, identityType, email,
				title, firstName, middleName, lastName, workPhone, workNumber, homePhone, mobile, pager, fax, manager,
				timeZone, languagePreference, notificationPreferences, super.hashCode());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PerfilUsuario {\n");
		sb.append("    ").append(toIndentedString(super.toString())).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    realmName: ").append(toIndentedString(realmName)).append("\n");
		sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
		sb.append("    memberOf: ").append(toIndentedString(memberOf)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    guid: ").append(toIndentedString(guid)).append("\n");
		sb.append("    uniqueName: ").append(toIndentedString(uniqueName)).append("\n");
		sb.append("    identityType: ").append(toIndentedString(identityType)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    title: ").append(toIndentedString(title)).append("\n");
		sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
		sb.append("    middleName: ").append(toIndentedString(middleName)).append("\n");
		sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
		sb.append("    workPhone: ").append(toIndentedString(workPhone)).append("\n");
		sb.append("    workNumber: ").append(toIndentedString(workNumber)).append("\n");
		sb.append("    homePhone: ").append(toIndentedString(homePhone)).append("\n");
		sb.append("    mobile: ").append(toIndentedString(mobile)).append("\n");
		sb.append("    pager: ").append(toIndentedString(pager)).append("\n");
		sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
		sb.append("    manager: ").append(toIndentedString(manager)).append("\n");
		sb.append("    timeZone: ").append(toIndentedString(timeZone)).append("\n");
		sb.append("    languagePreference: ").append(toIndentedString(languagePreference)).append("\n");
		sb.append("    notificationPreferences: ").append(toIndentedString(notificationPreferences)).append("\n");
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
