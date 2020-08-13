package mx.com.nmp.escenariosdinamicos.correo.vo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nombreArchivo", "contenido" })
public class AdjuntoVO {
	
	@JsonProperty("nombreArchivo")
	private String nombreArchivo;
	@JsonProperty("contenido")
	private String contenido;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();
	

	@JsonProperty("nombreArchivo")
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	@JsonProperty("nombreArchivo")
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@JsonProperty("contenido")
	public String getContenido() {
		return contenido;
	}

	@JsonProperty("contenido")
	public void setContenido(String contenido) {
		this.contenido = contenido;
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
