package mx.com.nmp.establecimientoprecios.apiproductos.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "data" })
public class ActualizarPreciosRequestVO /* extends ArrayList<ProductoMidasVO> */ {
	@JsonProperty("data")
	private List<DatumVO> data = null;

	@JsonProperty("data")
	public List<DatumVO> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<DatumVO> data) {
		this.data = data;
	}
}
