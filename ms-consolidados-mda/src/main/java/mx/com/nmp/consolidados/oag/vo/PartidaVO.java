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
"precioVenta", 
"montoPrestamo"
})
public class PartidaVO {

	@JsonProperty("idPartida")
	private String idPartida;
	@JsonProperty("sku")
	private String sku;
	@JsonProperty("precioVenta")
	private Float precioVenta;
	@JsonProperty("montoPrestamo")
	private Float montoPrestamo;
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

	@JsonProperty("precioVenta")
	public Float getPrecioVenta() {
		return precioVenta;
	}

	@JsonProperty("precioVenta")
	public void setPrecioVenta(Float precioVenta) {
		this.precioVenta = precioVenta;
	}

	@JsonProperty("montoPrestamo")
	public Float getMontoPrestamo() {
		return montoPrestamo;
	}

	@JsonProperty("montoPrestamo")
	public void setMontoPrestamo(Float montoPrestamo) {
		this.montoPrestamo = montoPrestamo;
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
