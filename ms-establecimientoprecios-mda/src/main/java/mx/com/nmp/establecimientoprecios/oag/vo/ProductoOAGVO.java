package mx.com.nmp.establecimientoprecios.oag.vo;

public class ProductoOAGVO {

	private Long folio;
	private String sku;
	private Float precio;
	
	public Long getFolio() {
		return folio;
	}
	
	public void setFolio(Long folio) {
		this.folio = folio;
	}
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public Float getPrecio() {
		return precio;
	}
	
	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [folio=" + folio + ", sku=" + sku + ", precio=" + precio + "]";
	}
	
}
