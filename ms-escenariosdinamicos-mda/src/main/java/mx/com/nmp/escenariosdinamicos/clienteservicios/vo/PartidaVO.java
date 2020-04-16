package mx.com.nmp.escenariosdinamicos.clienteservicios.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartidaVO {
	 @JsonProperty("idPartida")
	 private Integer idPartida;
	 @JsonProperty("SKU")
	 private String sku;
	 @JsonProperty("valorMonteActualizado")
	 private Float valorMonteActualizado;
	 
	public Integer getIdPartida() {
		return idPartida;
	}
	public String getSku() {
		return sku;
	}
	public Float getValorMonteActualizado() {
		return valorMonteActualizado;
	}
	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public void setValorMonteActualizado(Float valorMonteActualizado) {
		this.valorMonteActualizado = valorMonteActualizado;
	}
}
