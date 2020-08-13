package mx.com.nmp.consolidados.oag.vo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "para", "de", "asunto", "contenidoHTML", "adjuntos" })
public class EnviarNotificacionRequestVO {
	@JsonProperty("para")
	private String para;
	@JsonProperty("de")
	private String de;
	@JsonProperty("asunto")
	private String asunto;
	@JsonProperty("contenidoHTML")
	private String contenidoHTML;
	@JsonProperty("adjuntos")
	private AdjuntosVO adjuntos;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("para")
	public String getPara() {
		return para;
	}

	@JsonProperty("para")
	public void setPara(String para) {
		this.para = para;
	}

	@JsonProperty("de")
	public String getDe() {
		return de;
	}

	@JsonProperty("de")
	public void setDe(String de) {
		this.de = de;
	}

	@JsonProperty("asunto")
	public String getAsunto() {
		return asunto;
	}

	@JsonProperty("asunto")
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	@JsonProperty("contenidoHTML")
	public String getContenidoHTML() {
		return contenidoHTML;
	}

	@JsonProperty("contenidoHTML")
	public void setContenidoHTML(String contenidoHTML) {
		this.contenidoHTML = contenidoHTML;
	}

	@JsonProperty("adjuntos")
	public AdjuntosVO getAdjuntos() {
		return adjuntos;
	}

	@JsonProperty("adjuntos")
	public void setAdjuntos(AdjuntosVO adjuntos) {
		this.adjuntos = adjuntos;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
