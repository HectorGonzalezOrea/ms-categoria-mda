package mx.com.nmp.valormonte.elastic.vo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"_source",
"query"
})
public class RequestElasticVO {

	@JsonProperty("_source")
	private List<String> _source = null;
	@JsonProperty("query")
	private QueryVO query;

	@JsonProperty("_source")
	public List<String> get_source() {
		return _source;
	}

	@JsonProperty("_source")
	public void set_source(List<String> _source) {
		this._source = _source;
	}

	@JsonProperty("query")
	public QueryVO getQuery() {
		return query;
	}

	@JsonProperty("query")
	public void setQuery(QueryVO query) {
		this.query = query;
	}

}
