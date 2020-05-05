package mx.com.nmp.escenariosdinamicos.oag.dto;

public class RequestActualizarPrecioPartidaDto {
	
	private String folioPartida;
	private String sku;
	private String escenario;
	private Double precioActual;
	private Double precioModificado;
	private Boolean enLinea;
	
	public String getFolioPartida() {
		return folioPartida;
	}
	public String getSku() {
		return sku;
	}
	public String getEscenario() {
		return escenario;
	}
	public Double getPrecioActual() {
		return precioActual;
	}
	public Double getPrecioModificado() {
		return precioModificado;
	}
	public Boolean getEnLinea() {
		return enLinea;
	}
	public void setFolioPartida(String folioPartida) {
		this.folioPartida = folioPartida;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public void setEscenario(String escenario) {
		this.escenario = escenario;
	}
	public void setPrecioActual(Double precioActual) {
		this.precioActual = precioActual;
	}
	public void setPrecioModificado(Double precioModificado) {
		this.precioModificado = precioModificado;
	}
	public void setEnLinea(Boolean enLinea) {
		this.enLinea = enLinea;
	}


}
