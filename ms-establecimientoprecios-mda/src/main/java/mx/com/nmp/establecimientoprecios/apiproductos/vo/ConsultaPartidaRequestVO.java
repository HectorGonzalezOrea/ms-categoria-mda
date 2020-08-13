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
		StringBuilder sb = new StringBuilder();
		sb.append("class ConsultaPartidaRequest {\n");
		sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
		sb.append("    mercancia: ").append(toIndentedString(mercancia)).append("\n");
		sb.append("    folio: ").append(toIndentedString(folio)).append("\n");
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
