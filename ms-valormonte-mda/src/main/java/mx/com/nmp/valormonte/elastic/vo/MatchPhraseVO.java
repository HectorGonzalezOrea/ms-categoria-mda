package mx.com.nmp.valormonte.elastic.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "sku", "mercancia" })
public class MatchPhraseVO {
	@JsonProperty("sku")
	private String sku;
	@JsonProperty("partida")
	private Integer partida;

	@JsonProperty("sku")
	public String getSku() {
		return sku;
	}

	@JsonProperty("sku")
	public void setSku(String sku) {
		this.sku = sku;
	}

	@JsonProperty("partida")
	public Integer getPartida() {
		return partida;
	}

	@JsonProperty("partida")
	public void setPartida(Integer partida) {
		this.partida = partida;
	}
}
