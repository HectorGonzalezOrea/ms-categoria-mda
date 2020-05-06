package mx.com.nmp.valormonte.elastic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "match_phrase" })
public class ShouldVO {
	@JsonProperty("match_phrase")
	private MatchPhraseVO match_phrase;

	@JsonProperty("match_phrase")
	public MatchPhraseVO getMatch_phrase() {
		return match_phrase;
	}

	@JsonProperty("match_phrase")
	public void setMatch_phrase(MatchPhraseVO match_phrase) {
		this.match_phrase = match_phrase;
	}
}
