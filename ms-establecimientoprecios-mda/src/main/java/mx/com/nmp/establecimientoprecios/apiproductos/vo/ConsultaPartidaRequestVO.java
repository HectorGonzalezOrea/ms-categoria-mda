package mx.com.nmp.establecimientoprecios.apiproductos.vo;

public class ConsultaPartidaRequestVO {

	private String sku;
	private Long mercancia;
	private Long folio;
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public Long getMercancia() {
		return mercancia;
	}
	
	public void setMercancia(Long mercancia) {
		this.mercancia = mercancia;
	}
	
	public Long getFolio() {
		return folio;
	}
	
	public void setFolio(Long folio) {
		this.folio = folio;
	}
	
	@Override
	public String toString() {
		return "ConsultaPartidaRequest [sku=" + sku + ", mercancia=" + mercancia + ", folio=" + folio + "]";
	}
}
