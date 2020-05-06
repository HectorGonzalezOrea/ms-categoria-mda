package mx.com.nmp.valormonte.elastic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "bool" })
public class QueryVO {
	@JsonProperty("bool")
	private BoolVO bool;

	@JsonProperty("bool")
	public BoolVO getBool() {
		return bool;
	}

	@JsonProperty("bool")
	public void setBool(BoolVO bool) {
		this.bool = bool;
	}
}
