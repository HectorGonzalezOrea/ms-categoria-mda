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
@JsonPropertyOrder({
"idPartida",
"sku",
"cumpleArbitraje"
})
public class PartidaResponseVO {
	@JsonProperty("idPartida")
	private String idPartida;
	@JsonProperty("sku")
	private String sku;
	@JsonProperty("cumpleArbitraje")
	private Boolean cumpleArbitraje;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("idPartida")
	public String getIdPartida() {
		return idPartida;
	}

	@JsonProperty("idPartida")
	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}

	@JsonProperty("sku")
	public String getSku() {
		return sku;
	}

	@JsonProperty("sku")
	public void setSku(String sku) {
		this.sku = sku;
	}

	@JsonProperty("cumpleArbitraje")
	public Boolean getCumpleArbitraje() {
		return cumpleArbitraje;
	}

	@JsonProperty("cumpleArbitraje")
	public void setCumpleArbitraje(Boolean cumpleArbitraje) {
		this.cumpleArbitraje = cumpleArbitraje;
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
