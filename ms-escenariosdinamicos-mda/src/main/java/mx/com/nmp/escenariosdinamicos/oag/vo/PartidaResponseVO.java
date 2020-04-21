package mx.com.nmp.escenariosdinamicos.oag.vo;



public class PartidaResponseVO {
	
	private String idPartida;
	private String sku;
	private String reglaAplicada;
	private String precio;
	private DetalleReglasVO detalleReglas;
	
	public String getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getReglaAplicada() {
		return reglaAplicada;
	}
	public void setReglaAplicada(String reglaAplicada) {
		this.reglaAplicada = reglaAplicada;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public DetalleReglasVO getDetalleReglas() {
		return detalleReglas;
	}
	public void setDetalleReglas(DetalleReglasVO detalleReglas) {
		this.detalleReglas = detalleReglas;
	}

}
