package mx.com.nmp.consolidados.msestablecimientoprecios.vo;

public class AjustePrecio {

	private String folioPartida;
	private String sku;
	private String escenario;
	private Float precioActual;
	private Float precioModificado;
	private Boolean enLinea;
	
	public String getFolioPartida() {
		return folioPartida;
	}
	public void setFolioPartida(String folioPartida) {
		this.folioPartida = folioPartida;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getEscenario() {
		return escenario;
	}
	public void setEscenario(String escenario) {
		this.escenario = escenario;
	}
	public Float getPrecioActual() {
		return precioActual;
	}
	public void setPrecioActual(Float precioActual) {
		this.precioActual = precioActual;
	}
	public Float getPrecioModificado() {
		return precioModificado;
	}
	public void setPrecioModificado(Float precioModificado) {
		this.precioModificado = precioModificado;
	}
	public Boolean getEnLinea() {
		return enLinea;
	}
	public void setEnLinea(Boolean enLinea) {
		this.enLinea = enLinea;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AjustePrecio {\n");

		sb.append("    folioPartida: ").append(toIndentedString(folioPartida)).append("\n");
		sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
		sb.append("    escenario: ").append(toIndentedString(escenario)).append("\n");
		sb.append("    precioActual: ").append(toIndentedString(precioActual)).append("\n");
		sb.append("    precioModificado: ").append(toIndentedString(precioModificado)).append("\n");
		sb.append("    enLinea: ").append(toIndentedString(enLinea)).append("\n");
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
