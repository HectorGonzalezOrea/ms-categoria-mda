package mx.com.nmp.valormonte.elastic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "bool" })
public class MustVO {
	@JsonProperty("bool")
	private Bool_VO bool;

	@JsonProperty("bool")
	public Bool_VO getBool() {
		return bool;
	}

	@JsonProperty("bool")
	public void setBool(Bool_VO bool) {
		this.bool = bool;
	}
}
