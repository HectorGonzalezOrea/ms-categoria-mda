package mx.com.nmp.usuarios.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;


/**
 * PerfilUsuario
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-14T18:11:19.533Z")

public class PerfilUsuario extends InfoUsuario {
	@JsonProperty("uid")
	private String uid = null;

	@JsonProperty("mail")
	private String mail = null;

	@JsonProperty("physicaldeliveryofficename")
	private String physicaldeliveryofficename = null;

	@JsonProperty("department")
	private String department = null;

	@JsonProperty("lastName")
	private String lastName = null;

	@JsonProperty("firstName")
	private String firstName = null;

	@JsonProperty("uri")
	private String uri = null;

	@JsonProperty("memberOf")
	@Valid
	private List<String> memberOf = null;

	@JsonProperty("title")
	private String title = null;

	@JsonProperty("distinguishedname")
	private String distinguishedname = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("samaccountname")
	private String samaccountname = null;

	@JsonProperty("commonname")
	private String commonname = null;

	public PerfilUsuario uid(String uid) {
		this.uid = uid;
		return this;
	}

	/**
	 * UID (oauth2/profile)
	 * 
	 * @return uid
	 **/
	@ApiModelProperty(example = "b0c21bfc537e466b6e323dcc5fe0b23f", value = "UID (oauth2/profile)")

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public PerfilUsuario mail(String mail) {
		this.mail = mail;
		return this;
	}

	/**
	 * Correo del usuario (oauth2/profile)
	 * 
	 * @return mail
	 **/
	@ApiModelProperty(example = "jperezh@montepiedad.com.mx", value = "Correo del usuario (oauth2/profile)")

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public PerfilUsuario physicaldeliveryofficename(String physicaldeliveryofficename) {
		this.physicaldeliveryofficename = physicaldeliveryofficename;
		return this;
	}

	/**
	 * Nombre de la oficina del usuario (oauth2/profile)
	 * 
	 * @return physicaldeliveryofficename
	 **/
	@ApiModelProperty(example = "Perez Hernandez", value = "Nombre de la oficina del usuario (oauth2/profile)")

	public String getPhysicaldeliveryofficename() {
		return physicaldeliveryofficename;
	}

	public void setPhysicaldeliveryofficename(String physicaldeliveryofficename) {
		this.physicaldeliveryofficename = physicaldeliveryofficename;
	}

	public PerfilUsuario department(String department) {
		this.department = department;
		return this;
	}

	/**
	 * Departamento del usuario (oauth2/profile)
	 * 
	 * @return department
	 **/
	@ApiModelProperty(example = "Colaboracion Externos", value = "Departamento del usuario (oauth2/profile)")

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public PerfilUsuario uri(String uri) {
		this.uri = uri;
		return this;
	}

	/**
	 * Uri usuario (oauth2/profile)
	 * 
	 * @return uri
	 **/
	@ApiModelProperty(example = "/ms_oauth/resources/userprofile/me/jperezh@montepiedad.com.mx", value = "Uri usuario (oauth2/profile)")

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	public PerfilUsuario distinguishedname(String distinguishedname) {
		this.distinguishedname = distinguishedname;
		return this;
	}

	/**
	 * Nombre distinguido del usuario (oauth2/profile)
	 * 
	 * @return distinguishedname
	 **/
	@ApiModelProperty(example = "CN=Juan Perez Hernandez,OU=SPS,OU=Consultores,OU=Usuarios,DC=nmp,DC=com,DC=mx", value = "Nombre distinguido del usuario (oauth2/profile)")

	public String getDistinguishedname() {
		return distinguishedname;
	}

	public void setDistinguishedname(String distinguishedname) {
		this.distinguishedname = distinguishedname;
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

	public PerfilUsuario samaccountname(String samaccountname) {
		this.samaccountname = samaccountname;
		return this;
	}

	/**
	 * Nombre del usuario (oauth2/profile)
	 * 
	 * @return samaccountname
	 **/
	@ApiModelProperty(example = "Juan Perez Hernandez", value = "Nombre del usuario (oauth2/profile)")

	public String getSamaccountname() {
		return samaccountname;
	}

	public void setSamaccountname(String samaccountname) {
		this.samaccountname = samaccountname;
	}

	public PerfilUsuario commonname(String commonname) {
		this.commonname = commonname;
		return this;
	}

	/**
	 * Nombre del usuario (oauth2/profile)
	 * 
	 * @return commonname
	 **/
	@ApiModelProperty(example = "Juan Perez Hernandez", value = "Nombre del usuario (oauth2/profile)")

	public String getCommonname() {
		return commonname;
	}

	public void setCommonname(String commonname) {
		this.commonname = commonname;
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
		return Objects.equals(this.uid, perfilUsuario.uid) && Objects.equals(this.mail, perfilUsuario.mail)
				&& Objects.equals(this.physicaldeliveryofficename, perfilUsuario.physicaldeliveryofficename)
				&& Objects.equals(this.department, perfilUsuario.department)
				&& Objects.equals(this.lastName, perfilUsuario.lastName)
				&& Objects.equals(this.firstName, perfilUsuario.firstName)
				&& Objects.equals(this.uri, perfilUsuario.uri) && Objects.equals(this.memberOf, perfilUsuario.memberOf)
				&& Objects.equals(this.title, perfilUsuario.title)
				&& Objects.equals(this.distinguishedname, perfilUsuario.distinguishedname)
				&& Objects.equals(this.description, perfilUsuario.description)
				&& Objects.equals(this.samaccountname, perfilUsuario.samaccountname)
				&& Objects.equals(this.commonname, perfilUsuario.commonname) && super.equals(o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid, mail, physicaldeliveryofficename, department, lastName, firstName, uri, memberOf,
				title, distinguishedname, description, samaccountname, commonname, super.hashCode());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PerfilUsuario {\n");
		sb.append("    ").append(toIndentedString(super.toString())).append("\n");
		sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
		sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
		sb.append("    physicaldeliveryofficename: ").append(toIndentedString(physicaldeliveryofficename)).append("\n");
		sb.append("    department: ").append(toIndentedString(department)).append("\n");
		sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
		sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
		sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
		sb.append("    memberOf: ").append(toIndentedString(memberOf)).append("\n");
		sb.append("    title: ").append(toIndentedString(title)).append("\n");
		sb.append("    distinguishedname: ").append(toIndentedString(distinguishedname)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    samaccountname: ").append(toIndentedString(samaccountname)).append("\n");
		sb.append("    commonname: ").append(toIndentedString(commonname)).append("\n");
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
