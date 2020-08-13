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
		StringBuilder sb = new StringBuilder();
		sb.append("class Producto {\n");
		sb.append("    folio: ").append(toIndentedString(folio)).append("\n");
		sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
		sb.append("    precio: ").append(toIndentedString(precio)).append("\n");
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
