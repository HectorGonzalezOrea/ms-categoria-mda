package mx.com.nmp.valormonte.elastic.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"should",
"minimum_should_match"
})
public class Bool_VO {
	@JsonProperty("should")
	private List<ShouldVO> should = null;
	@JsonProperty("minimum_should_match")
	private Integer minimum_should_match;

	@JsonProperty("should")
	public List<ShouldVO> getShould() {
		return should;
	}

	@JsonProperty("should")
	public void setShould(List<ShouldVO> should) {
		this.should = should;
	}

	@JsonProperty("minimum_should_match")
	public Integer getMinimum_should_match() {
		return minimum_should_match;
	}

	@JsonProperty("minimum_should_match")
	public void setMinimum_should_match(Integer minimum_should_match) {
		this.minimum_should_match = minimum_should_match;
	}

	
}
