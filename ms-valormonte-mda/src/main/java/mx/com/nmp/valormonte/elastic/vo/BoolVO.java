package mx.com.nmp.valormonte.elastic.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoolVO {

	@JsonProperty("must")
	private List<MustVO> must = null;

	@JsonProperty("must")
	public List<MustVO> getMust() {
	return must;
	}

	@JsonProperty("must")
	public void setMust(List<MustVO> must) {
	this.must = must;
	}
	
}
