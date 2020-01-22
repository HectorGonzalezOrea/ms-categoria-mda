package mx.com.nmp.establecimientoprecios.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ListaPrecioPartidasInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-03T00:30:35.505Z")

public class ListaPrecioPartidasInner {

	@JsonProperty("folioPartida")
	private String folioPartida = null;
	@JsonProperty("sku")
	private String sku = null;
	@JsonProperty("escenario")
	private String escenario = null;
	@JsonProperty("precioActual")
	private Float precioActual = null;
	@JsonProperty("precioModificado")
	private Float precioModificado = null;
	@JsonProperty("enLinea")
	private Boolean enLinea = null;

	@ApiModelProperty(example = "124342", value = "Folio que identifica a la partida")
	public String getFolioPartida() {
		return folioPartida;
	}

	public void setFolioPartida(String folioPartida) {
		this.folioPartida = folioPartida;
	}

	@ApiModelProperty(example = "21341", value = "SKU de la partida")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@ApiModelProperty(example = "PA - Esc6", value = "Escenario para el ajuste de precio")
	public String getEscenario() {
		return escenario;
	}

	public void setEscenario(String escenario) {
		this.escenario = escenario;
	}

	@ApiModelProperty(example = "120.5", value = "Precio actual de la partida")
	public Float getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(Float precioActual) {
		this.precioActual = precioActual;
	}

	@ApiModelProperty(example = "140.5", value = "Precio modificado de la partida")
	public Float getPrecioModificado() {
		return precioModificado;
	}

	public void setPrecioModificado(Float precioModificado) {
		this.precioModificado = precioModificado;
	}

	@ApiModelProperty(example = "true", value = "Flag para indicar si la actualización de precios se realizará en línea")
	public Boolean getEnLinea() {
		return enLinea;
	}

	public void setEnLinea(Boolean enLinea) {
		this.enLinea = enLinea;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ListaPrecioPartidasInner listaPrecioPartidasInner = (ListaPrecioPartidasInner) o;
		return Objects.equals(this.folioPartida, listaPrecioPartidasInner.folioPartida)
				&& Objects.equals(this.sku, listaPrecioPartidasInner.sku)
				&& Objects.equals(this.escenario, listaPrecioPartidasInner.escenario)
				&& Objects.equals(this.precioActual, listaPrecioPartidasInner.precioActual)
				&& Objects.equals(this.precioModificado, listaPrecioPartidasInner.precioModificado)
				&& Objects.equals(this.enLinea, listaPrecioPartidasInner.enLinea);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ListaPrecioPartidasInner {\n");

		sb.append("    folioPartida: ").append(toIndentedString(folioPartida)).append("\n");
		sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
		sb.append("    escenario: ").append(toIndentedString(escenario)).append("\n");
		sb.append("    precioActual: ").append(toIndentedString(precioActual)).append("\n");
		sb.append("    precioModificado: ").append(toIndentedString(precioModificado)).append("\n");
		sb.append("    enLinea: ").append(toIndentedString(enLinea)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(folioPartida, sku, escenario, precioActual, precioModificado, enLinea);
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
