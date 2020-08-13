package mx.com.nmp.establecimientoprecios.oag.vo;

import java.util.List;

public class GestionPreciosRequestVO {

	private Boolean enLinea;
	private List<ProductoOAGVO> informacionProductos;
	
	public Boolean getEnLinea() {
		return enLinea;
	}

	public void setEnLinea(Boolean enLinea) {
		this.enLinea = enLinea;
	}

	public List<ProductoOAGVO> getInformacionProductos() {
		return informacionProductos;
	}

	public void setInformacionProductos(List<ProductoOAGVO> informacionProductos) {
		this.informacionProductos = informacionProductos;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class GestionPreciosRequestVO {\n");
		sb.append("    enLinea: ").append(toIndentedString(enLinea)).append("\n");
		sb.append("    informacionProductos: ").append(toIndentedString(informacionProductos)).append("\n");
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
