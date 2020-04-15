package mx.com.nmp.usuarios.oag.vo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uid", "mail", "physicaldeliveryofficename", "department", "lastname", "firstname", "uri",
		"memberof", "title", "distinguishedname", "description", "samaccountname", "commonname" })
public class ProfileVO {
	@JsonProperty("uid")
	private String uid;
	@JsonProperty("mail")
	private String mail;
	@JsonProperty("physicaldeliveryofficename")
	private String physicaldeliveryofficename;
	@JsonProperty("department")
	private String department;
	@JsonProperty("lastname")
	private String lastname;
	@JsonProperty("firstname")
	private String firstname;
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("memberof")
	private List<String> memberof = null;
	@JsonProperty("title")
	private String title;
	@JsonProperty("distinguishedname")
	private String distinguishedname;
	@JsonProperty("description")
	private String description;
	@JsonProperty("samaccountname")
	private String samaccountname;
	@JsonProperty("commonname")
	private String commonname;

	@JsonProperty("uid")
	public String getUid() {
		return uid;
	}

	@JsonProperty("uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

	@JsonProperty("mail")
	public String getMail() {
		return mail;
	}

	@JsonProperty("mail")
	public void setMail(String mail) {
		this.mail = mail;
	}

	@JsonProperty("physicaldeliveryofficename")
	public String getPhysicaldeliveryofficename() {
		return physicaldeliveryofficename;
	}

	@JsonProperty("physicaldeliveryofficename")
	public void setPhysicaldeliveryofficename(String physicaldeliveryofficename) {
		this.physicaldeliveryofficename = physicaldeliveryofficename;
	}

	@JsonProperty("department")
	public String getDepartment() {
		return department;
	}

	@JsonProperty("department")
	public void setDepartment(String department) {
		this.department = department;
	}

	@JsonProperty("lastname")
	public String getLastname() {
		return lastname;
	}

	@JsonProperty("lastname")
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@JsonProperty("firstname")
	public String getFirstname() {
		return firstname;
	}

	@JsonProperty("firstname")
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@JsonProperty("uri")
	public String getUri() {
		return uri;
	}

	@JsonProperty("uri")
	public void setUri(String uri) {
		this.uri = uri;
	}

	@JsonProperty("memberof")
	public List<String> getMemberof() {
		return memberof;
	}

	@JsonProperty("memberof")
	public void setMemberof(List<String> memberof) {
		this.memberof = memberof;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("distinguishedname")
	public String getDistinguishedname() {
		return distinguishedname;
	}

	@JsonProperty("distinguishedname")
	public void setDistinguishedname(String distinguishedname) {
		this.distinguishedname = distinguishedname;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("samaccountname")
	public String getSamaccountname() {
		return samaccountname;
	}

	@JsonProperty("samaccountname")
	public void setSamaccountname(String samaccountname) {
		this.samaccountname = samaccountname;
	}

	@JsonProperty("commonname")
	public String getCommonname() {
		return commonname;
	}

	@JsonProperty("commonname")
	public void setCommonname(String commonname) {
		this.commonname = commonname;
	}
}
