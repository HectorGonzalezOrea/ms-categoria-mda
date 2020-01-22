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
		return "HistoricoPreciosRequestDTO [sku=" + sku + ", folioPartida=" + folioPartida + ", precioActual="
				+ precioActual + ", precioModificado=" + precioModificado + ", fecha=" + fecha + "]";
	}
	
}
