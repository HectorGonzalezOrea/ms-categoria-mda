package mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo;

public class HistoricoPreciosRequestVO {

	private String sku;
	private String folioPartida;
	private Float precioActual;
	private Float precioModificado;
	private String fecha;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getFolioPartida() {
		return folioPartida;
	}
	public void setFolioPartida(String folioPartida) {
		this.folioPartida = folioPartida;
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class HistoricoPreciosRequestDTO {\n");
		sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
		sb.append("    folioPartida: ").append(toIndentedString(folioPartida)).append("\n");
		sb.append("    precioActual: ").append(toIndentedString(precioActual)).append("\n");
		sb.append("    precioModificado: ").append(toIndentedString(precioModificado)).append("\n");
		sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
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
