package mx.com.nmp.gestionescenarios.oag.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "idProducto", "ubicacionActual", "folioSku", "prestamoCosto", "precioFinal" })
public class Producto {

	@JsonProperty("idProducto")
	private Integer idProducto;
	@JsonProperty("ubicacionActual")
	private String ubicacionActual;
	@JsonProperty("folioSku")
	private String folioSku;
	@JsonProperty("prestamoCosto")
	private Double prestamoCosto;
	@JsonProperty("precioFinal")
	private Double precioFinal;

	@JsonProperty("idProducto")
	public Integer getIdProducto() {
		return idProducto;
	}

	@JsonProperty("idProducto")
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	@JsonProperty("ubicacionActual")
	public String getUbicacionActual() {
		return ubicacionActual;
	}

	@JsonProperty("ubicacionActual")
	public void setUbicacionActual(String ubicacionActual) {
		this.ubicacionActual = ubicacionActual;
	}

	@JsonProperty("folioSku")
	public String getFolioSku() {
		return folioSku;
	}

	@JsonProperty("folioSku")
	public void setFolioSku(String folioSku) {
		this.folioSku = folioSku;
	}

	@JsonProperty("prestamoCosto")
	public Double getPrestamoCosto() {
		return prestamoCosto;
	}

	@JsonProperty("prestamoCosto")
	public void setPrestamoCosto(Double prestamoCosto) {
		this.prestamoCosto = prestamoCosto;
	}

	@JsonProperty("precioFinal")
	public Double getPrecioFinal() {
		return precioFinal;
	}

	@JsonProperty("precioFinal")
	public void setPrecioFinal(Double precioFinal) {
		this.precioFinal = precioFinal;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Producto {\n");
		sb.append("    idProducto: ").append(toIndentedString(idProducto)).append("\n");
		sb.append("    ubicacionActual: ").append(toIndentedString(ubicacionActual)).append("\n");
		sb.append("    folioSku: ").append(toIndentedString(folioSku)).append("\n");
		sb.append("    prestamoCosto: ").append(toIndentedString(prestamoCosto)).append("\n");
		sb.append("    precioFinal: ").append(toIndentedString(precioFinal)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}