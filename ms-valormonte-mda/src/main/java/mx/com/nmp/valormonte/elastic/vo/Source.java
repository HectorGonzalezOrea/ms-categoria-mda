package mx.com.nmp.valormonte.elastic.vo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "valor_monte_act", "sku", "partida" })
public class Source {

	@JsonProperty("valor_monte_act")
	private String valorMonteAct;
	@JsonProperty("sku")
	private String sku;
	@JsonProperty("partida")
	private String partida;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("valor_monte_act")
	public String getValorMonteAct() {
		return valorMonteAct;
	}

	@JsonProperty("valor_monte_act")
	public void setValorMonteAct(String valorMonteAct) {
		this.valorMonteAct = valorMonteAct;
	}

	@JsonProperty("sku")
	public String getSku() {
		return sku;
	}

	@JsonProperty("sku")
	public void setSku(String sku) {
		this.sku = sku;
	}

	@JsonProperty("partida")
	public String getPartida() {
		return partida;
	}

	@JsonProperty("partida")
	public void setPartida(String partida) {
		this.partida = partida;
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
